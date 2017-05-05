package com.hiersun.oohdear.article.mapper;

import java.util.Map;

import com.hiersun.oohdear.article.entity.ArticleCollection;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

public interface ArticleCollectionMapper extends Mapper<ArticleCollection> {
    int isSign(Map params);
}