package com.hiersun.oohdear.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.user.service.GeneratorService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.user.vo.UserInfo;

/**
 * @author saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 上午11:50:54
 * @version 1.0
 */
@Service
public class UserMemberInfoServiceImpl implements UserMemberInfoService {
	@Value("${user.server.default.avatar.url}")
	private String defaultAvatar;
	@Autowired
	private UserMemberInfoMapper userMemberInfoMapper;
	@Autowired
	private GeneratorService generatorService;

	@Override
	public UserMemberInfo queryMobileExist(String mobile) {
		UserMemberInfo user = new UserMemberInfo();
		user.setMobile(mobile);
		return userMemberInfoMapper.selectOne(user);
	}

	@Override
	public UserMemberInfo queryUserInfoByMemberNo(String memberNo) {
		UserMemberInfo user = new UserMemberInfo();
		user.setMemberNo(memberNo);
		user.setDeleted(false);
		return userMemberInfoMapper.selectOne(user);
	}

	@Override
	public UserInfo queryUserInfo(String memberNo) {
		UserMemberInfo user = new UserMemberInfo();
		user.setMemberNo(memberNo);
		user.setDeleted(false);
		user = userMemberInfoMapper.selectOne(user);
		if(user==null){
			throw new RuntimeException("用户信息不存在");
		}
		return new UserInfo(user.getAvatar(), user.getNickName(),
				user.getGender(), user.getMobile());
	}

	@Override
	public UserMemberInfo register(String mobile) {
		// 判断手机号是否注册
		UserMemberInfo member = queryMobileExist(mobile);
		if (member != null) {
			// 手机号已注册
			return member;
		}
		// 生成会员编号
		String memberNo = generatorService.generatorMemberNo(0);
		UserMemberInfo record = new UserMemberInfo();
		Date created = new Date();
		record.setCreated(created);
		record.setLastModified(created);
		record.setDeleted(false);
		record.setMemberNo(memberNo);
		record.setMobile(mobile);
		record.setGender(1); //默认性别
		record.setAvatar(defaultAvatar); 
		record.setNickName("用户-"+mobile.substring(7)); //默认昵称
		userMemberInfoMapper.insert(record);
		return record;
	}

	@Override
	public Boolean editUserInfo(String memberNo, String avatar,
			String nickname, String gender) {
		System.out.println(memberNo+","+avatar+","+nickname+","+gender);
		UserMemberInfo user = new UserMemberInfo();
		user.setMemberNo(memberNo);
		Map<String, Object> param = new HashMap<String, Object>();

		if (StringUtils.hasText(avatar)) {
			user.setAvatar(avatar);
			param.put("avatar", avatar);
		}
		if (StringUtils.hasText(nickname)) {
			user.setNickName(nickname);
			param.put("nickname", nickname);
		}
		if (StringUtils.hasText(gender)) {
			user.setGender("男".equals(gender)? 1 : 2);
			param.put("gender", user.getGender());
		}
		param.put("memberNo", memberNo);
		param.put("date", new Date());
		return userMemberInfoMapper.updateInfoByMemberNo(param) == 1;
	}

	@Override
	public UserMemberInfo addUserInfo(UserMemberInfo member) {
		// 判断手机号是否注册
		UserMemberInfo userMember = queryMobileExist(member.getMobile());
		if (userMember != null) {
			//手机号已注册,保存订制人信息。
			UserMemberInfo user=new UserMemberInfo();
			user.setId(userMember.getId());
			user.setRealName(member.getRealName());
			userMemberInfoMapper.updateByPrimaryKeySelective(user);
			return userMember;
		}
		String memberNo = generatorService.generatorMemberNo(0);
		member.setMemberNo(memberNo);
		member.setLastModified(new Date());
		member.setCreated(new Date());
		member.setGender(1); //默认性别为男
		member.setAvatar(defaultAvatar);
		userMemberInfoMapper.insert(member);
		return member;
	}
}
