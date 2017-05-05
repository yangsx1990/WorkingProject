package com.hiersun.oohdear.article.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.article.service.ArticleCollectionService;
import com.hiersun.oohdear.core.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:文章点赞控制器
 * Author: liubaocheng
 * Create: 2017-03-07 11:23
 **/
@RestController
@RequestMapping("collection")
public class ArticleCollectionController {

    @Autowired
    private ArticleCollectionService articleCollectionService;

    /**
     * 是否点赞
     * @param responseMessage 返回信息
     * @param memberNo 用户编号
     * @param articleId  文章编号
     * @return
     */
    @RequestMapping(value="isSign",method=RequestMethod.GET)
    public ResponseEntity<ResponseMessage> isSign(ResponseMessage responseMessage, @RequestParam(required=true) String memberNo, @RequestParam(required=true)Long articleId){
        if(!StringUtils.hasText(memberNo)){
        	responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("会员编号不能为空");
        }
    	int sign = articleCollectionService.isSign(memberNo,articleId);
        responseMessage.getBody().put("sign",sign==1);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }



    @RequestMapping(value="add",method=RequestMethod.POST)
    public ResponseEntity<ResponseMessage> addSign(ResponseMessage responseMessage, @RequestParam(required=true) String memberNo, @RequestParam(required=true)Long articleId){
    	if(!StringUtils.hasText(memberNo)){
        	responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("会员编号不能为空");
        }  	
    	int status=articleCollectionService.addSign(memberNo,articleId);
        int sign= articleCollectionService.isSign(memberNo, articleId);
        int signCount=articleCollectionService.selectCount(articleId);
        responseMessage.getBody().put("status", status==1);
        responseMessage.getBody().put("isRecord", sign==1);
        responseMessage.getBody().put("signCount", signCount);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
/**
 * 查询文章的点赞数
 * @param responseMessage
 * @param articleId 文章id
 * @return
 */
    @RequestMapping(value="count",method=RequestMethod.GET)
    public ResponseEntity<ResponseMessage> count(ResponseMessage responseMessage, @RequestParam(required=true)Long articleId){
        int count = articleCollectionService.selectCount(articleId);
        responseMessage.getBody().put("count",count);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

}
