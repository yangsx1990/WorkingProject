package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/7.
 */


import com.hiersun.oohdear.entity.ArticleMessage;
import com.hiersun.oohdear.entity.vo.ArticleMessageVo;

import java.util.List;

/**
 * Description:文章留言服务接口
 * Author: liubaocheng
 * Create: 2017-03-07 14:05
 **/
public interface ArticleMessageService {

    /**
     * 屏蔽信息
     * @param articleMessage 留言信息
     * @return
     */
    int maskDeleteStatus(ArticleMessage articleMessage);

    /**
     * 获取留言信息列表
     * @param articleMessageVo
     * @return
     */
    List<ArticleMessage> getMessageList(ArticleMessageVo articleMessageVo);
}
