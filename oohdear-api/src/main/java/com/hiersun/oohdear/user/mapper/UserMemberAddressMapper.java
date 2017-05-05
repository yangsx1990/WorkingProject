package com.hiersun.oohdear.user.mapper;

import com.hiersun.oohdear.user.entity.UserMemberAddress;

import tk.mybatis.mapper.common.Mapper;

public interface UserMemberAddressMapper extends Mapper<UserMemberAddress> {
	int updateAddressByNo(UserMemberAddress address);
}