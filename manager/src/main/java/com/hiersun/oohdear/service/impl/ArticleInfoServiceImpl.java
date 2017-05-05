package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.els.SearchEls;
import com.hiersun.oohdear.entity.ArticleInfo;
import com.hiersun.oohdear.entity.vo.ArticleInfoVo;
import com.hiersun.oohdear.mapper.ArticleInfoMapper;
import com.hiersun.oohdear.service.ArticleInfoService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description:内容/文章管理服务接口实现类
 * Author: liubaocheng
 * Create: 2017-03-09 15:58
 **/
@Service
@Transactional
public class ArticleInfoServiceImpl implements ArticleInfoService{


    private static final int DESC_LENGTH = 80;

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private SearchEls searchEls;

    @Override
    public List<ArticleInfo> getArticleInfoList(ArticleInfoVo articleInfoVo) {
        return articleInfoMapper.getSubjectList(articleInfoVo);
    }

    @Override
    public int maskArticleInfo(ArticleInfo articleInfo) {
        return articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
    }

    public int saveArticleInfo(ArticleInfo articleInfo,String articleInfoELS){
    	articleInfo.setNumber(0);
        articleInfo.setCreated(new Date());
        articleInfo.setDeleteStatus(false);
        articleInfo.setRecommendStatus(articleInfo.getRecommendStatus()==null? false:true);
        articleInfo.setArticleDesc(articleInfoELS.length()>DESC_LENGTH? articleInfoELS.substring(0,DESC_LENGTH-3)+"...":articleInfoELS);

        int result =  articleInfoMapper.insert(articleInfo);
        if(result > 0 ){//如果数据库保存成功，将数据添加到elasticsearch
            articleInfo.setArticleInfo(articleInfoELS);
            searchEls.save(articleInfo);
        }
        return result;
    }

    @Override
    public ArticleInfo getArticleInfoById(ArticleInfo articleInfo) {
        return articleInfoMapper.getArticleInfoById(articleInfo);
    }

    @Override
    public int setTop(ArticleInfo articleInfo) {
        return articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
    }


    public List<ArticleInfo> getArticleInfoByIds(String[] str){
        if(str==null || str.length<1) {
            return null;
        }
      return   articleInfoMapper.getArticleInfoByIds(Arrays.asList(str));
    }

	@Override
	public int updateArticleInfo(ArticleInfo articleInfo,String articleInfoELS) {
//        searchEls.delete(articleInfo);
//        searchEls.save(articleInfo);//添加索引
        articleInfo.setArticleDesc(articleInfoELS.length()>DESC_LENGTH? articleInfoELS.substring(0,DESC_LENGTH-3)+"...":articleInfoELS);
        articleInfo.setCreated(new Date());
        articleInfo.setRecommendStatus(articleInfo.getRecommendStatus()==null? false:articleInfo.getRecommendStatus());
		int result =  articleInfoMapper.updateByPrimaryKeySelective(articleInfo);
        if(result > 0 ){//如果数据库保存成功，将数据添加到elasticsearch
            searchEls.delete(articleInfo.getId());
            articleInfo.setArticleInfo(articleInfoELS);
            searchEls.save(articleInfo);
        }
        return result;
	}
}
