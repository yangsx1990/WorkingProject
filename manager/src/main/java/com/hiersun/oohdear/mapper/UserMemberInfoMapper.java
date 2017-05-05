package com.hiersun.oohdear.mapper;

import java.util.List;
import java.util.Map;



import com.hiersun.oohdear.entity.UserMemberInfo;

import com.hiersun.oohdear.entity.vo.UserMemberInfoVo;
import tk.mybatis.mapper.common.Mapper;

public interface UserMemberInfoMapper extends Mapper<UserMemberInfo> {
	List<UserMemberInfo> selectUserMemberInfoList(UserMemberInfoVo userMemberInfoVo);
}