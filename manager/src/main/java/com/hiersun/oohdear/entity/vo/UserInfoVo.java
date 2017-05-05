package com.hiersun.oohdear.entity.vo;import java.util.List;

import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.UserMemberAddress;

/**
 * Created by liubaocheng on 2017/3/7.
 */

/**
 * Description:用户会员信息页面查询辅助类
 * Author: liubaocheng
 * Create: 2017-03-07 20:58
 **/
public class UserInfoVo{
	private String memberNo;
	private String gender;
	private String created;
    private String mobile;
    private String avatar;
    private String nickName;

    private List<UserMemberAddress> addressList;
    
    private List<OrderInfoVo> orderList;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<UserMemberAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<UserMemberAddress> addressList) {
		this.addressList = addressList;
	}

	public List<OrderInfoVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderInfoVo> orderList) {
		this.orderList = orderList;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
  

}
