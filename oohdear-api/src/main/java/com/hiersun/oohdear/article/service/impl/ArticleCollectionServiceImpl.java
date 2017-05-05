package com.hiersun.oohdear.article.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.hiersun.oohdear.article.entity.ArticleCollection;
import com.hiersun.oohdear.article.mapper.ArticleCollectionMapper;
import com.hiersun.oohdear.article.mapper.ArticleInfoMapper;
import com.hiersun.oohdear.article.service.ArticleCollectionService;
import com.hiersun.oohdear.article.service.ArticleService;

/**
 * Description:文章点赞服务接口实现类
 * Author: liubaocheng
 * Create: 2017-03-07 11:26
 **/
@Service
@Transactional
public class ArticleCollectionServiceImpl implements ArticleCollectionService{

    @Autowired
    private ArticleCollectionMapper articleCollectionMapper;
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private ArticleService articleService;
    @Override
    public int isSign(String memberNo, Long articleId) {
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("memberNo",memberNo);
    	params.put("articleId", articleId);
    	return articleCollectionMapper.isSign(params);
    }

    @Override
    public int deleteSign(String memberNo, Long articleId) {
    	
        ArticleCollection articleCollection = new ArticleCollection();
        articleCollection.setDeletedStatus(true);
        Example example =new Example(ArticleCollection.class);
        example.createCriteria().andEqualTo("memberNo", memberNo).andEqualTo("articleid", articleId);
        return articleCollectionMapper.updateByExampleSelective(articleCollection, example);
    }

    @Override
    public int addSign(String memberNo, Long articleId) {
        if(isSign(memberNo,articleId)==1){
        	//取消点赞
        	articleService.updateArticleInfo(articleId, false);
            return deleteSign(memberNo,articleId); 
        }else{
        	//增加点赞
            ArticleCollection articleCollection = new ArticleCollection();
            articleCollection.setArticleid(articleId);
            articleCollection.setMemberNo(memberNo);
            articleCollection.setDeletedStatus(false);
            articleCollection.setCreated(new Date());
            articleService.updateArticleInfo(articleId, true);
            return articleCollectionMapper.insert(articleCollection);
        }

    }

    @Override
    public int selectCount(long articleId){
        ArticleCollection articleCollection = new ArticleCollection();
        articleCollection.setArticleid(articleId);
        articleCollection.setDeletedStatus(false);
        return articleCollectionMapper.selectCount(articleCollection);
    }
}
