package com.hiersun.oohdear.config;/**
 * Created by liubaocheng on 2017/4/11.
 */

import com.hiersun.oohdear.controller.OrderController;
import com.hiersun.oohdear.entity.SysRole;
import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-04-11 15:57
 **/
public class MyShiroRealm  extends AuthorizingRealm {

    private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 验证完毕以后给用户添加权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        SysUser user=(SysUser) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        //到数据库查是否有此对象
//        SysUser user = userService.findByName(username);
        // Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if(user!=null){
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<SysRole> roleList = userService.findRoles(user);
            //用户的角色集合
            if(roleList!=null && roleList.size()>0){
                Set<String> roles = new HashSet<String>();
                for(SysRole role : roleList){
                    roles.add(role.getRoleName());
                }
                info.setRoles(roles);
                for (SysRole role : roleList) {
                    List<String> permissions = userService.findPermissions(role);
                    info.addStringPermissions(permissions);
                }
            }
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
            //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            return info;
        }
        return  null;
    }

    /**
     * 验证用户
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) authenticationToken;//获取用户输入的token
        String username = utoken.getUsername();
        SysUser user = userService.findByName(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }
}
