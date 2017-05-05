package com.hiersun.oohdear.user.service;/**
 * Created by liubaocheng on 2017/3/6.
 */

import java.util.List;

import com.hiersun.oohdear.user.vo.UserSearchVo;

/**
 * Description:搜索控制服务器
 * Author: liubaocheng
 * Create: 2017-03-06 14:26
 **/
public interface SearchService {

    List<UserSearchVo> getUserSearch(String memberNo);

    int addUserSearch(String searchContent,String memberNo);

    int deleteUserSearch(Long searchId,String memberNo);
    /**
     * 查询用户搜索历史记录数
     * @param memberNo 会员编号
     * @return
     */
    int selectCount(String memberNo);
}
