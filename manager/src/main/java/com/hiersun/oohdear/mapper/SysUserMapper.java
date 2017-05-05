package com.hiersun.oohdear.mapper;

import com.hiersun.oohdear.entity.SysRole;
import com.hiersun.oohdear.entity.SysUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface SysUserMapper extends Mapper<SysUser> {
    List<SysRole> findRoles(SysUser user);
    List<String> findPermissions(SysRole role);
}