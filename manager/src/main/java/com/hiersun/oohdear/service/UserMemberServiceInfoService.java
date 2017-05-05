package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.UserMemberInfo;
import com.hiersun.oohdear.entity.vo.UserInfoVo;
import com.hiersun.oohdear.entity.vo.UserMemberInfoVo;

import java.util.List;

/**
 * Description:用户会员信息服务接口
 * Author: liubaocheng
 * Create: 2017-03-07 20:20
 **/
public interface UserMemberServiceInfoService {


    List<UserMemberInfo> selectUserMemberInfoList(UserMemberInfoVo userMemberInfoVo);


    UserInfoVo selectUserMemberInfo(String memberNo);

    List<UserMemberInfo> getAllUserMemberInfo();
}
