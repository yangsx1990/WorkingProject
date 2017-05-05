package com.hiersun.oohdear.user.service;

import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.vo.UserInfo;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 上午11:50:33 
 * @version 1.0 
 */
public interface UserMemberInfoService {
	/**
	 * 判断手机号是否存在
	 * @param mobile 手机号
	 * @return
	 */
	UserMemberInfo queryMobileExist(String mobile);
	
	/**
	 * 添加用户信息
	 * @param member
	 * @return
	 */
	UserMemberInfo addUserInfo(UserMemberInfo member);

	/**
	 * 注册
	 * @param mobile 手机号
	 * @return
	 */
	UserMemberInfo register(String mobile);
	
	/**
	 * 查询个人信息
	 * @param mobile
	 * @return
	 */
	UserInfo queryUserInfo(String memberNo);
	/**
	 * 查询个人信息
	 * @param mobile
	 * @return
	 */
	UserMemberInfo queryUserInfoByMemberNo(String memberNo);
	/**
	 * 编辑个人信息
	 * @param memberNo
	 * @param avatar
	 * @param nickname
	 * @param gender
	 * @return
	 */
	Boolean editUserInfo(String memberNo,String avatar,String nickname,String gender);
}
