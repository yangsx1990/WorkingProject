package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.UserMemberAddress;
import com.hiersun.oohdear.mapper.UserMemberAddressMapper;
import com.hiersun.oohdear.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-07 21:17
 **/
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserMemberAddressMapper userMemberAddressMapper;

    public List<UserMemberAddress> getAddressList(UserMemberAddress userMemberAddress) {
        return userMemberAddressMapper.select(userMemberAddress);
    }
}
