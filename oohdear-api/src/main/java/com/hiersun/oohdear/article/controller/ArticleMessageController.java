package com.hiersun.oohdear.article.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiersun.oohdear.article.entity.PageParam;
import com.hiersun.oohdear.article.service.ArticleMessageService;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.vo.ArticleMessageVo;

/**
 * Description:文章留言控制器
 * Author: liubaocheng
 * Create: 2017-03-07 11:24
 **/
@Controller
@RequestMapping("message")
public class ArticleMessageController {

    @Autowired
    private ArticleMessageService articleMessageService;

    /**
     * 查询留言-根据文章id
     * @param responseMessage
     * @param memberNo 会员编号（非必填）
     * @param articleId 文章id
     * @param pageParam 分页参数
     * @return
     */
    @RequestMapping(value="query",method=RequestMethod.GET)
    public ResponseEntity<ResponseMessage> queryMessage(ResponseMessage responseMessage, @RequestParam(required=false) String memberNo, @RequestParam(required=true)Long articleId,
    		 PageParam pageParam){
    	Page<?> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
    	List<ArticleMessageVo> messageList =articleMessageService.queryMessage(memberNo,articleId);
    	int pages=(int) (page.getTotal()/pageParam.getPageSize());
        if((int) (page.getTotal()%pageParam.getPageSize())>0){
        	pages++;
        }
        responseMessage.getBody().put("end",pageParam.getPageNum()>=pages?true:false);
        responseMessage.getBody().put("messageList",messageList );
        responseMessage.getBody().put("pages",pages );
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * 删除留言
     * @param responseMessage
     * @param memberNo 会员编号
     * @param messageId 留言id
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="delete",method=RequestMethod.POST)
    public ResponseEntity<ResponseMessage> deleteMessage(ResponseMessage responseMessage, @RequestParam(required=true) String memberNo, @RequestParam(required=true)Long messageId) throws Exception{
    	if(!StringUtils.hasText(memberNo)){
    		throw new Exception("会员编号为空，无法删除留言！");
    	}
       Boolean deleteStatus= articleMessageService.deleteMessage(memberNo,messageId);
       responseMessage.getBody().put("status", deleteStatus);
       return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
	/**
	 * 添加留言
	 * @param responseMessage
	 * @param articleMessage
	 * @return
	 */
    @RequestMapping(value="add",method=RequestMethod.POST)
    public ResponseEntity<ResponseMessage> addMessage(ResponseMessage responseMessage,@RequestParam(required=true) String memberNo,@RequestParam Long articleId,@RequestParam String content){
    	if(!StringUtils.hasText(memberNo)){
    		throw new RuntimeException("会员编号为空，不能评论");
    	}
    	Boolean addStatus=articleMessageService.addMessage(memberNo,articleId,content);
    	int messageCount=articleMessageService.selectCount(articleId);
    	responseMessage.getBody().put("addStatus", addStatus);
    	responseMessage.getBody().put("messageCount", messageCount);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
}
