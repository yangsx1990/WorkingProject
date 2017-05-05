package com.hiersun.oohdear.user.vo;

import java.io.Serializable;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 下午4:16:58 
 * @version 1.0 
 */
public class UserInfo  implements Serializable {

	private static final long serialVersionUID = 1L;
	private String avatar;
	private String nickname;
	private String gender;
	private String mobile;
	public UserInfo(){}
	public UserInfo(String avatar, String nickName, Integer gender,
			String mobile) {
		this.avatar=avatar;
		this.nickname=nickName;
		this.gender=gender==1?"男":"女";
		this.mobile=mobile;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
}
