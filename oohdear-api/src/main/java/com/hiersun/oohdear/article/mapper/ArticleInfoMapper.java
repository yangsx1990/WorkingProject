package com.hiersun.oohdear.article.mapper;

import com.hiersun.oohdear.article.entity.ArticleInfo;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleInfoMapper extends Mapper<ArticleInfo> {

    ArticleInfo getArticleInfoById(ArticleInfo articleInfo);
    /**
     * 根据文章id查询文章标题
     * @param id 文章id
     * @return
     */
    String getArticleTitle(Long id);
}