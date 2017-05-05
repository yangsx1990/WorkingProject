package com.hiersun.oohdear.article.service.impl;/**
 * Created by liubaocheng on 2017/3/3.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hiersun.oohdear.vo.ArticleDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.hiersun.oohdear.article.entity.ArticleInfo;
import com.hiersun.oohdear.article.entity.ArticleLabel;
import com.hiersun.oohdear.article.mapper.ArticleInfoMapper;
import com.hiersun.oohdear.article.mapper.ArticleLabelMapper;
import com.hiersun.oohdear.article.service.ArticleCollectionService;
import com.hiersun.oohdear.article.service.ArticleMessageService;
import com.hiersun.oohdear.article.service.ArticleService;
import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.user.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.vo.ArticleInfoVo;

/**
 * Description: 文章服务实现类
 * Author: liubaocheng
 * Create: 2017-03-03 16:31
 **/
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleInfoMapper articleInfoMapper;
    @Autowired
    private ArticleLabelMapper articleLabelMapper;
    @Autowired
    private UserMemberInfoMapper userMemberInfoMapper;
    
    @Autowired
    private ArticleCollectionService articleCollectionService;
    
    @Autowired
    private ArticleMessageService articleMessageService;
    @Override
    public List<ArticleInfoVo> getArticleList(Integer labelId) {
    	List<ArticleInfoVo> articleVoList=new ArrayList<>();
    	Example example=new Example(ArticleInfo.class);
    	if(labelId == null || labelId==0){
    		example.createCriteria().andEqualTo("deleteStatus", false);
    	}else{
    		example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("labelId", labelId);
    	}    	
    	example.orderBy("topStatus").desc().orderBy("created").asc();
    	List<ArticleInfo> articles= articleInfoMapper.selectByExample(example);
    	articles.forEach(action->{
    		ArticleInfoVo vo=new ArticleInfoVo();
    		vo.setId(action.getId());
    		vo.setPublishTime(action.getCreated()!=null?new SimpleDateFormat("yyyy-MM-dd").format(action.getCreated()):"--");
    		vo.setContent(action.getArticleDesc());
    		//vo.setArticleInfo(action.getArticleInfo());
    		vo.setLabels(action.getArticleLabel());
    		vo.setTitle(action.getArticleName());
    		//vo.setMemberNo(action.getMemberNo());
    		vo.setArticleDesc(action.getArticleDesc());
    		vo.setCover(action.getPicture());
    		vo.setLabelId(action.getLabelId());
    		vo.setAvatar(action.getAvatar());
    		vo.setNickName(action.getNickName());
    		//点赞数
    		int signCount=articleCollectionService.selectCount(action.getId());
    		//收藏数
    		int messageCount=articleMessageService.selectCount(action.getId());
    		vo.setInterestedNum(signCount);
    		vo.setMessageNum(messageCount);
    		articleVoList.add(vo);
    	});
         return articleVoList;
    }


    @Override
    public ArticleDetail getArticleInfo(Long articleId){
    	ArticleInfo article=new ArticleInfo();
    	article.setId(articleId);
    	article.setDeleteStatus(false);
        article = articleInfoMapper.getArticleInfoById(article);

		ArticleDetail detail = new ArticleDetail();
		BeanUtils.copyProperties(article,detail,"created");
		detail.setCreated(article.getCreated()!=null? new SimpleDateFormat("yyyy-MM-dd").format(article.getCreated()):"--");
		String[] articleIds=article.getArticleInterest().split(",");
		if(articleIds.length==0){
			return detail;
		}
		List<ArticleInfo> articleVoList=new ArrayList<>();
		for (int i = 0; i < articleIds.length; i++) {
			//String articleName=articleInfoMapper.getArticleTitle(Long.valueOf(articleIds[i]));
			ArticleInfo articleVo=new ArticleInfo();
			articleVo=articleInfoMapper.selectByPrimaryKey(Long.valueOf(articleIds[i]));
			/*articleVo.setArticleName(articleName);
			articleVo.setId(Long.valueOf(articleIds[i]));*/
			articleVoList.add(articleVo);
		}
		detail.setArticles(articleVoList);
		return detail;
    }


	@Override
	public List<ArticleLabel> getLables() {
		return articleLabelMapper.select(new ArticleLabel());
	}


	@Override
	public List<ArticleInfoVo> getRecommendArticleList() {
		List<ArticleInfoVo> articleVoList=new ArrayList<>();
    	Example example=new Example(ArticleInfo.class);
    	example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("recommendStatus", true);
    	example.orderBy("topStatus").desc().orderBy("created").asc();
    	List<ArticleInfo> articles= articleInfoMapper.selectByExample(example);
    	articles.forEach(action->{
    		ArticleInfoVo vo=new ArticleInfoVo();
    		vo.setId(action.getId());
    		vo.setPublishTime(action.getCreated()!=null?new SimpleDateFormat("yyyy-MM-dd").format(action.getCreated()):"--");
    		vo.setLabels(action.getArticleLabel());
    		vo.setTitle(action.getArticleName());
    		vo.setCover(action.getPicture());
    		articleVoList.add(vo);
    	});
         return articleVoList;
	}


	@Override
	public Integer updateArticleInfo(Long articleId, Boolean status) {
		ArticleInfo article=new ArticleInfo();
		article.setId(articleId);
		article.setDeleteStatus(false);
		List<ArticleInfo> articles=articleInfoMapper.select(article);
		ArticleInfo articleInfo=new ArticleInfo();
		if(articles.size()>0){
			article=articles.get(0);
		}else{
			throw new OohdearException(3001, "文章信息不存在");
		}
		System.out.println("number——————————————————————"+article.getNumber());
		if(status){
			articleInfo.setNumber(article.getNumber()+1);
		}else{
			articleInfo.setNumber(article.getNumber()-1);
		}
		articleInfo.setId(articleId);
	    return articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
	}
}
