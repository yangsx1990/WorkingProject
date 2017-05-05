package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.ArticleLabel;
import com.hiersun.oohdear.mapper.ArticleLabelMapper;
import com.hiersun.oohdear.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:内容/文章服务实现类
 * Author: liubaocheng
 * Create: 2017-03-09 16:06
 **/
@Service
@Transactional
public class ArticleLabelServiceImpl implements ArticleLabelService{

    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    @Override
    public List<ArticleLabel> getLabelList() {
        return articleLabelMapper.selectAll();
    }
}
