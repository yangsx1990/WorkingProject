package com.hiersun.oohdear.service.impl;

import com.hiersun.oohdear.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.mapper.SysUserMapper;
import com.hiersun.oohdear.service.UserService;

import java.util.List;
import java.util.Set;

/** 
 * @author  saixing_yang@hiersun.com
 * @date ����ʱ�䣺2017��3��7�� ����8:24:09 
 * @version 1.0 
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	SysUserMapper sysUserMapper;

	public SysUser login(String username, String password) {
		SysUser user=new SysUser();
		user.setUsername(username);
		user.setPassword(password);
		return sysUserMapper.selectOne(user);
	}


	/**
	 * 根据名称查询用户
	 * @param name
	 * @return
	 */
	public SysUser findByName(String name){
		SysUser user=new SysUser();
		user.setUsername(name);
		return sysUserMapper.selectOne(user);
	}

	/**
	 * 查询用户所拥有的角色
	 * @param user
	 * @return
	 */
	public List<SysRole> findRoles(SysUser user){
		return sysUserMapper.findRoles(user);
	}

	/**
	 * 查询角色所拥有的权限
	 * @param role
	 * @return
	 */
	public List<String> findPermissions(SysRole role){
		return sysUserMapper.findPermissions(role);
	}
	

}
