package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hiersun.oohdear.entity.UserMemberInfo;
import com.hiersun.oohdear.entity.vo.UserInfoVo;
import com.hiersun.oohdear.entity.vo.UserMemberInfoVo;
import com.hiersun.oohdear.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.service.OrderService;
import com.hiersun.oohdear.service.UserMemberServiceInfoService;

/**
 * Description:用户会员信息服务接口实现类
 * Author: liubaocheng
 * Create: 2017-03-07 20:21
 **/
@Service
@Transactional
public class UserMemberInfoServiceImpl implements UserMemberServiceInfoService {

    @Autowired
    private UserMemberInfoMapper userMemberInfoMapper;
    @Autowired
    private OrderService orderService;

    public List<UserMemberInfo> selectUserMemberInfoList(UserMemberInfoVo userMemberInfoVo) {
        return userMemberInfoMapper.selectUserMemberInfoList(userMemberInfoVo);
    }

    public UserInfoVo selectUserMemberInfo(String memberNo) {
    	UserInfoVo user=new UserInfoVo();
    	UserMemberInfo member=new UserMemberInfo();
    	member.setMemberNo(memberNo);
        member=userMemberInfoMapper.selectOne(member);
        user.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getCreated()));
        user.setGender(member.getGender()==1?"男":"女");
        user.setMemberNo(memberNo);
        user.setAvatar(member.getAvatar());
        user.setMobile(member.getMobile());
        user.setNickName(member.getNickName());
        return user; 
    }

    public List<UserMemberInfo> getAllUserMemberInfo(){
        return userMemberInfoMapper.selectAll();
    }
}
