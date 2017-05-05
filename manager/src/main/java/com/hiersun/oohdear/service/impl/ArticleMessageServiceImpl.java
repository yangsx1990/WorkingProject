package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.ArticleMessage;
import com.hiersun.oohdear.entity.vo.ArticleMessageVo;
import com.hiersun.oohdear.mapper.ArticleMessageMapper;
import com.hiersun.oohdear.service.ArticleMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:文章留言服务接口实现类
 * Author: liubaocheng
 * Create: 2017-03-07 14:08
 **/
@Service
@Transactional
public class ArticleMessageServiceImpl implements ArticleMessageService {

    @Autowired
    private ArticleMessageMapper articleMessageMapper;

    public int maskDeleteStatus(ArticleMessage articleMessage) {
        return articleMessageMapper.updateByPrimaryKeySelective(articleMessage);
    }

    @Override
    public List<ArticleMessage> getMessageList(ArticleMessageVo articleMessageVo) {
        return articleMessageMapper.getMessageList(articleMessageVo);
    }

}
