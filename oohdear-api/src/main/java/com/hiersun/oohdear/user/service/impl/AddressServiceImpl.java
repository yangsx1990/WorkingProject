package com.hiersun.oohdear.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.Page;
import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.order.entity.OrderInfo;
import com.hiersun.oohdear.user.entity.UserMemberAddress;
import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.mapper.UserMemberAddressMapper;
import com.hiersun.oohdear.user.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.user.service.AddressService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.user.vo.UserAddress;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 上午11:50:54 
 * @version 1.0 
 */
@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private UserMemberAddressMapper userMemberAddressMapper;
	
	@Override
	public List<UserAddress> queryAddressList(String memberNo) {
		UserMemberAddress userMemberAddress=new UserMemberAddress();
		userMemberAddress.setMemberNo(memberNo);
		userMemberAddress.setDeleted(false);
		Example example=new Example(UserMemberAddress.class);
		example.createCriteria().andEqualTo("memberNo", memberNo).andEqualTo("deleted", false);
		example.setOrderByClause("default_address desc");
		List<UserMemberAddress> addressList=userMemberAddressMapper.selectByExample(example);
		List<UserAddress> addressVo=new ArrayList<>();
		if(addressList.size()>0){
			   addressList.forEach(action->{
				UserAddress address=new UserAddress(action.getId(),action.getConsignee(),
						action.getConsigneeMobile(),action.getZone(),action.getAddress(),action.getDefaultAddress());
				addressVo.add(address);
			});
		}
		return addressVo;
	}

	@Override
	public String addAddress( String consignee,
			String mobile,String zone, String detail,String memberNo) {
		//1、查询该用户是否有其他地址
		UserMemberAddress address=new UserMemberAddress();
		Example example =new Example(UserMemberAddress.class);
		example.createCriteria().andEqualTo("memberNo",memberNo).andEqualTo("deleted",false);
		List<UserMemberAddress> addressList=userMemberAddressMapper.selectByExample(example);
		if(addressList.size()>0){
			address.setDefaultAddress(false);
		}else{
			address.setDefaultAddress(true);
		}
		address.setAddress(detail);
		address.setConsignee(consignee);
		address.setCreated(new Date());
		address.setDeleted(false);
		address.setConsigneeMobile(mobile);
		address.setMemberNo(memberNo);
		address.setZone(zone);
		if(userMemberAddressMapper.insert(address)!=1){
			return null;
		}
		return address.getId().toString();
		
	}

	@Override
	public Boolean editAddress(String id,String consignee, String mobile,String zone, String detail,
			Boolean defaultAddr, String memberNo) {
		UserMemberAddress address=new UserMemberAddress();
		address.setAddress(detail);
		address.setConsignee(consignee);
		address.setCreated(new Date());
		address.setDefaultAddress(defaultAddr);
		address.setDeleted(false);
		address.setConsigneeMobile(mobile);
		address.setMemberNo(memberNo);
		address.setZone(zone);
		address.setId(Long.valueOf(id));
		return userMemberAddressMapper.updateAddressByNo(address)==1;
	}
	@Override
	public Boolean deleteAddress(Long id) {
		//1、逻辑删除地址
		UserMemberAddress address=new UserMemberAddress();
		address.setId(id);
		address.setDeleted(true);
		userMemberAddressMapper.updateByPrimaryKeySelective(address);
		//2、查询是否为默认地址
		address=userMemberAddressMapper.selectByPrimaryKey(address);
		if(address.getDefaultAddress()){
			//2.1、如果是默认地址，查询是否还有其他地址
			UserMemberAddress userAddress=new UserMemberAddress();
			Example example=new Example(UserMemberAddress.class);
			example.createCriteria().andEqualTo("memberNo",address.getMemberNo()).andEqualTo("deleted",false);
			example.setOrderByClause("created desc");
			List<UserMemberAddress> addressList=userMemberAddressMapper.selectByExample(example);
			if(addressList!=null){
				//2.1.1如果该用户还有其他地址，修改最新一条地址为默认地址。
				userAddress=addressList.get(0);
			}else{
				//2.1.2如果没有其他地址，不做任何操作
				return true;
			}
			userAddress.setDefaultAddress(true);
			return userMemberAddressMapper.updateByPrimaryKeySelective(userAddress)==1;
		}
		//2.2如果该地址不是默认地址，不做操作。
		return true;
	}

	@Override
	public Boolean setDefault(String id, String memberNo) {
		//1、修改该用户的其他默认地址
		UserMemberAddress address=new UserMemberAddress();
		address.setMemberNo(memberNo);
		address.setDeleted(false);
		address.setDefaultAddress(true);
		List<UserMemberAddress> addressList=userMemberAddressMapper.select(address);
		if(addressList.size()>0){
			address.setDefaultAddress(false);
			Example example = new Example(UserMemberAddress.class);
			example.createCriteria().andEqualTo("memberNo", memberNo).andEqualTo("defaultAddress",true).andEqualTo("deleted", false);
			userMemberAddressMapper.updateByExampleSelective(address, example);
		}
		
		//2、将用户的该地址设为默认
		UserMemberAddress newAddress=new UserMemberAddress();
		newAddress.setId(Long.valueOf(id));
		newAddress.setDefaultAddress(true);
		return userMemberAddressMapper.updateByPrimaryKeySelective(newAddress)==1;
	}

	@Override
	public UserAddress queryAddressById(String memberNo,Long id) {
		UserMemberAddress address=new UserMemberAddress();
		if(id==0){
			address.setMemberNo(memberNo);
			address.setDefaultAddress(true);
			Example example=new Example(UserMemberAddress.class);
			example.createCriteria().andEqualTo("memberNo",memberNo).andEqualTo("deleted",false);
			example.orderBy("defaultAddress desc");
			example.orderBy("created desc");
			List<UserMemberAddress> addressList=userMemberAddressMapper.selectByExample(example);
			if(addressList.size()>0){
				address=addressList.get(0);
			}
		}else{
			address.setId(id);
			address=userMemberAddressMapper.selectByPrimaryKey(id);
			if(address==null){
				throw new RuntimeException("用户地址不存在");
			}
		}
		UserAddress user=new UserAddress(address.getId(),address.getConsignee(), address.getConsigneeMobile(),
				address.getZone(),address.getAddress(),address.getDefaultAddress());
		 return user;
	}

}
