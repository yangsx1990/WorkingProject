package com.hiersun.oohdear.mapper;

import com.hiersun.oohdear.entity.ArticleInfo;
import com.hiersun.oohdear.entity.vo.ArticleInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleInfoMapper extends Mapper<ArticleInfo> {
    List<ArticleInfo> getSubjectList(ArticleInfoVo articleInfoVo);

    ArticleInfo getArticleInfoById(ArticleInfo articleInfo);

    List<ArticleInfo> getArticleInfoByIds(List<String> articleIds);
}