package com.hiersun.oohdear.article.service;/**
 * Created by liubaocheng on 2017/3/7.
 */

/**
 * Description: 文章点赞服务接口
 * Author: liubaocheng
 * Create: 2017-03-07 11:26
 **/
public interface ArticleCollectionService {

    /**
     * 是否已经点赞
     * @param memberNo 用户编号
     * @param articleId  文章编号(ID)
     * @return ture 点赞  false 未点赞
     */
	int isSign(String memberNo,Long articleId);

    /**
     * 删除点赞信息
     * @param memberNo 用户编号
     * @param articleId 文章编号(ID)
     * @return
     */
    int deleteSign(String memberNo,Long articleId);

    /**
     * 点赞操作
     * @param memberNo 用户编号
     * @param articleId 文章编号(ID)
     * @return
     */
    int addSign(String memberNo,Long articleId);

    /**
     * 文章点赞数
     * @param articleId 文章编号(ID)
     * @return
     */
    int selectCount(long articleId);
}
