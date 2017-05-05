package com.hiersun.oohdear.user.service;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.hiersun.oohdear.user.vo.UserAddress;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 下午6:19:42 
 * @version 1.0 
 */
public interface AddressService {
	/**
	 * 查询地址列表
	 * @param memberNo 会员编号
	 * @return
	 */
	List<UserAddress> queryAddressList(String memberNo);
	/**
	 * 根据地址id查询
	 * @param id
	 * @return
	 */
	UserAddress queryAddressById(String memberNo,Long addressId);
	/**
	 * 添加或编辑地址
	 * @param id 地址id（非必填）
	 * @param consignee 收货人
	 * @param mobile 手机号
	 * @param detail 详细地址
	 * @param memberNo 会员编号
	 * @return
	 */
	String addAddress( String consignee, String mobile,
			String zone,String detail,String memberNo);
	/**
	 * 删除地址
	 * @param id 地址id
	 * @return
	 */
	Boolean deleteAddress(Long id);

	/**
	 * 编辑地址
	 * @param id
	 * @param consignee
	 * @param mobile
	 * @param zone
	 * @param detail
	 * @param defaultAddr
	 * @param memberNo
	 * @return
	 */
	Boolean editAddress(String id, String consignee,
			String mobile,String zone, String detail, Boolean defaultAddr, String memberNo);
	/**
	 * 设为默认地址
	 * @param id
	 * @param memberNo
	 * @return
	 */
	Boolean setDefault(String id,String memberNo);
}
