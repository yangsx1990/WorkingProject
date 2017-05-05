package com.hiersun.oohdear.article.mapper;

import com.hiersun.oohdear.article.entity.ArticleMessage;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleMessageMapper extends Mapper<ArticleMessage> {
    int queryMessageByarticleAndmemberNo(ArticleMessage article);
}