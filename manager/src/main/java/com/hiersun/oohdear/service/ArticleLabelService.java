package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.ArticleLabel;

import java.util.List;

/**
 * Description:内容/文章服务接口
 * Author: liubaocheng
 * Create: 2017-03-09 16:05
 **/
public interface ArticleLabelService {

    /**
     * 获取类别列表
     * @return
     */
    List<ArticleLabel> getLabelList();
}
