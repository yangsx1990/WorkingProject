package com.hiersun.oohdear.article.controller;/**
 * Created by liubaocheng on 2017/2/28.
 */

import java.util.ArrayList;
import java.util.List;












import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiersun.oohdear.article.entity.ArticleInfo;
import com.hiersun.oohdear.article.entity.ArticleLabel;
import com.hiersun.oohdear.article.entity.PageParam;
import com.hiersun.oohdear.article.service.ArticleCollectionService;
import com.hiersun.oohdear.article.service.ArticleMessageService;
import com.hiersun.oohdear.article.service.ArticleService;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.subject.entity.SubjectInfo;
import com.hiersun.oohdear.subject.service.SubjectService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.user.vo.UserInfo;
import com.hiersun.oohdear.util.DateUtil;


import com.hiersun.oohdear.vo.ArticleDetail;
import com.hiersun.oohdear.vo.ArticleInfoVo;
import com.hiersun.oohdear.vo.SubjectInfoVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Description:文章控制管理类
 * Author: liubaocheng
 * Create: 2017-02-28 9:59
 **/
@Controller
@RequestMapping("/article")
public class ArticleController {
  

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserMemberInfoService userService;
    @Autowired
    private ArticleMessageService articleMessageService;
    @Autowired
    private ArticleCollectionService articleCollectionService;
    /**
     * 首页-获取文章
     * @param responseMessage
     * @param pageParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getArticle(ResponseMessage responseMessage, PageParam pageParam,Integer labelId){
        Page<?> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<ArticleInfoVo> articleList=articleService.getArticleList(labelId);
        List<SubjectInfo> subjectList=subjectService.getSubjects();
        if(subjectList.size()>5){
        	subjectList=subjectList.subList(0, 5);
        }
        List<SubjectInfoVo> subjects=new ArrayList<>();
        subjectList.forEach(action->{
        	SubjectInfoVo subjectVo=new SubjectInfoVo();
        	subjectVo.setId(action.getId());
        	subjectVo.setCover(action.getPicture());
        	subjectVo.setTitle(action.getTitle());
        	subjectVo.setLink(action.getLink());
        	subjects.add(subjectVo);
        });
        int pages=(int) (page.getTotal()/pageParam.getPageSize());
        if((int) (page.getTotal()%pageParam.getPageSize())>0){
        	pages++;
        }
    	responseMessage.getBody().put("subjectList", subjects);
    	responseMessage.getBody().put("pages", pages);
        responseMessage.getBody().put("end",pageParam.getPageNum()>=pages?true:false);
        responseMessage.getBody().put("articles",articleList);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
    /**
     * 根据标签查询文章信息
     * @param responseMessage
     * @param pageParam 分页信息
     * @param labelId 标签id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/list/label")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getArticleByLabel(ResponseMessage responseMessage, PageParam pageParam,Integer labelId){
        Page<?> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<ArticleInfoVo> articleList=articleService.getArticleList(labelId);
        int pages=(int) (page.getTotal()/pageParam.getPageSize());
        if((int) (page.getTotal()%pageParam.getPageSize())>0){
        	pages++;
        }
        responseMessage.getBody().put("end",pageParam.getPageNum()>=pages?true:false);
        responseMessage.getBody().put("pages", pages);
        responseMessage.getBody().put("articles",articleList);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
    /**
     * 推荐文章列表
     * @param responseMessage
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/recommended")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getRecommendedArticle(ResponseMessage responseMessage){
        List<ArticleInfoVo> articleList=articleService.getRecommendArticleList();
        responseMessage.getBody().put("articles",articleList);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

   /* *//**
     * 查询标签
     * @param responseMessage
     * @param articleInfo
     * @return
     */
    @RequestMapping(value="/label",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseMessage> getLables(ResponseMessage responseMessage){
        List<ArticleLabel> labels=articleService.getLables();
        responseMessage.getBody().put("labels", labels);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
    /**
     * 获取文章的留言和点赞个数
     * @param responseMessage
     * @return
     */
    @RequestMapping(value="/count",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseMessage> getCount(ResponseMessage responseMessage,@RequestParam(required=true) Long articleId,String memberNo){
        int messageCount=articleMessageService.selectCount(articleId);
        int collectionCount=articleCollectionService.selectCount(articleId);
        boolean signStatus=true;
        if(StringUtils.hasText(memberNo)){
        	signStatus=articleCollectionService.isSign(memberNo, articleId)==1;
        }
        responseMessage.getBody().put("messageCount", messageCount); //留言数
        responseMessage.getBody().put("collectionCount", collectionCount);//点赞数
        responseMessage.getBody().put("isRecord", signStatus);//该用户是否点赞
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * 详情页面 查询 TODO
     * @param responseMessage
     * @param articleId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/detail")
    public ResponseEntity<ResponseMessage> detail(ResponseMessage responseMessage,@RequestParam Long articleId){
    	ArticleDetail articleInfo = articleService.getArticleInfo(articleId);
        responseMessage.getBody().put("content",articleInfo);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

}
