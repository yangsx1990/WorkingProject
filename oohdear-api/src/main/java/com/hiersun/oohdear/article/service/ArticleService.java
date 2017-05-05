package com.hiersun.oohdear.article.service;/**
 * Created by liubaocheng on 2017/3/3.
 */

import com.hiersun.oohdear.article.entity.ArticleInfo;
import com.hiersun.oohdear.article.entity.ArticleLabel;
import com.hiersun.oohdear.vo.ArticleDetail;
import com.hiersun.oohdear.vo.ArticleInfoVo;

import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-03 16:31
 **/
public interface ArticleService {

	/**
	 * 查询文章列表
	 * @return
	 */
    List<ArticleInfoVo> getArticleList(Integer labelId);

    /**
     * 获取文章详情
     * @param articleInfo
     * @return
     */
    ArticleDetail getArticleInfo(Long articleId);
    /**
     * 获取标签集合
     * @return
     */
    List<ArticleLabel> getLables();
    /**
     * 获取推荐文章
     * @return
     */
    List<ArticleInfoVo> getRecommendArticleList();
    /**
     * 更新点赞次数
     * @param articleId 文章id
     * @param status 如果true，则点赞数+1；反之，-1.
     * @return
     */
    Integer updateArticleInfo(Long articleId,Boolean status);
}
