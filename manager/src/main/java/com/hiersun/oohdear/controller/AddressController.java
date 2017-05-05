package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.UserMemberAddress;
import com.hiersun.oohdear.service.AddressService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description:地址控制管理类
 * Author: liubaocheng
 * Create: 2017-03-07 21:15
 **/
@RequestMapping("address")
@Controller
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ResponseBody
    @RequestMapping("list")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("address:list")
    public List<UserMemberAddress> getList(UserMemberAddress userMemberAddress){
        return addressService.getAddressList(userMemberAddress);
    }
}
