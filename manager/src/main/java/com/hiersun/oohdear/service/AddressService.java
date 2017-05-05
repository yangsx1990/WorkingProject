package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.UserMemberAddress;

import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-07 21:16
 **/
public interface AddressService {

    List<UserMemberAddress> getAddressList(UserMemberAddress userMemberAddress);
}
