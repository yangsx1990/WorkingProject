package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.ArticleInfo;
import com.hiersun.oohdear.entity.vo.ArticleInfoVo;

import java.util.List;

/**
 * Description:内容/文章管理服务接口
 * Author: liubaocheng
 * Create: 2017-03-09 15:40
 **/
public interface ArticleInfoService {

    /**
     *  获取内容/文章列表 (分页使用)
     * @param articleInfoVo
     * @return
     */
    List<ArticleInfo> getArticleInfoList(ArticleInfoVo articleInfoVo);

    /**
     * 显示或者隐藏内容/文章信息
     * @param articleInfo
     * @return
     */
    int maskArticleInfo(ArticleInfo articleInfo);

    /**
     * 保存内容/文章信息
     * @param articleInfo
     * @return
     */
    int saveArticleInfo(ArticleInfo articleInfo,String articleInfoELS);
    /**
     * 更新内容
     * @param articleInfo
     * @return
     */
    int updateArticleInfo(ArticleInfo articleInfo,String articleInfoELS);

    /**
     * 获取内容/文章信息
     * @param articleInfo
     * @return
     */
    ArticleInfo getArticleInfoById(ArticleInfo articleInfo);

    /**
     * 设置置顶
     * @param articleInfo
     * @return
     */
    int setTop(ArticleInfo articleInfo);

    /**
     * 根据ID查询文章/内容
     * @param str
     * @return
     */
    List<ArticleInfo> getArticleInfoByIds(String[] str);


}
