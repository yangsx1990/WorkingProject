package com.hiersun.oohdear.user.mapper;

import java.util.Map;

import com.hiersun.oohdear.user.entity.UserMemberInfo;

import tk.mybatis.mapper.common.Mapper;

public interface UserMemberInfoMapper extends Mapper<UserMemberInfo> {
	int updateInfoByMemberNo(Map<String,Object> param);
}