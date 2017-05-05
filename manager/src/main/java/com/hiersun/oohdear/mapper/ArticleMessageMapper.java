package com.hiersun.oohdear.mapper;

import com.hiersun.oohdear.entity.ArticleMessage;
import com.hiersun.oohdear.entity.vo.ArticleMessageVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMessageMapper extends Mapper<ArticleMessage> {
    List<ArticleMessage> getMessageList(ArticleMessageVo articleMessageVo);
}