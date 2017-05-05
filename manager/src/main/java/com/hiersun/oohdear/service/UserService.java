package com.hiersun.oohdear.service;

import com.hiersun.oohdear.entity.SysRole;
import com.hiersun.oohdear.entity.SysUser;

import java.util.List;
import java.util.Set;

/** 
 * �û��ӿ�
 * @author  saixing_yang@hiersun.com
 * @date ����ʱ�䣺2017��3��7�� ����8:23:10 
 * @version 1.0 
 */
public interface UserService {

	/**
	 * ��¼�ӿ�
	 * @param username �û���
	 * @param password ����
	 * @return
	 */
	public SysUser login(String username,String password);
	
	/*public getRole*/

	/**
	 * 根据名称查询用户
	 * @param name
	 * @return
	 */
	SysUser findByName(String name);

	/**
	 * 查询用户所拥有的角色
	 * @param user
	 * @return
	 */
	List<SysRole> findRoles(SysUser user);

	/**
	 * 查询角色所拥有的权限
	 * @param role
	 * @return
	 */
	List<String> findPermissions(SysRole role);
}
