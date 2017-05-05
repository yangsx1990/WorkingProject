package com.hiersun.oohdear.user.controller;/**
 * Created by liubaocheng on 2017/3/6.
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiersun.oohdear.article.entity.PageParam;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.user.entity.UserSearch;
import com.hiersun.oohdear.user.service.SearchService;
import com.hiersun.oohdear.user.vo.UserSearchVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Description:搜索控制器
 * Author: liubaocheng
 * Create: 2017-03-06 14:26
 **/
@Controller
@RequestMapping("/user/search")
public class UserSearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 用户搜索历史 
     * @param pageParam PageNum 默认 1 PageSize 默认10
     * @param responseMessage
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value="get")
    public ResponseEntity<ResponseMessage> getUserSearch(@RequestParam String memberNo,ResponseMessage responseMessage){
        List<UserSearchVo> searchList= searchService.getUserSearch(memberNo);
        int count=searchService.selectCount(memberNo);
        if(count>10){
        	
        }
        responseMessage.getBody().put("searchs",searchList);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }


    /**
     * 添加用户搜索信息
     * @param responseMessage
     * @param userSearch
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "add")
    @ResponseBody
    public ResponseEntity<ResponseMessage> add(ResponseMessage responseMessage, @RequestParam String searchContent,@RequestParam String memberNo){
        int status=searchService.addUserSearch(searchContent,memberNo);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
/**
 * 删除搜索记录
 * @param responseMessage
 * @param userSearch
 * @return
 */
    @RequestMapping(method = RequestMethod.GET,value="delete")
    @ResponseBody
    public ResponseEntity<ResponseMessage> delete(ResponseMessage responseMessage, @RequestParam(required=false) Long searchId,@RequestParam String memberNo){
       int deleteRows= searchService.deleteUserSearch(searchId,memberNo);
       responseMessage.getBody().put("status", deleteRows>0);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }


}
