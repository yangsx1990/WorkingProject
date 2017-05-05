package com.hiersun.oohdear.subject.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiersun.oohdear.article.entity.PageParam;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.subject.entity.SubjectInfo;
import com.hiersun.oohdear.subject.service.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:专题控制管理器
 * Author: liubaocheng
 * Create: 2017-03-07 9:28
 **/
@Controller
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(method = RequestMethod.GET,value = "get")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getArticle(ResponseMessage responseMessage, PageParam pageParam){
        Page<?> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SubjectInfo> subjectList=subjectService.getSubjects();
        List<JSONObject> subjects = new ArrayList<JSONObject>();
        subjectList.forEach(subject -> {
			JSONObject json = new JSONObject();
			json.put("id", subject.getId());
			json.put("cover", subject.getPicture());
			json.put("title",subject.getTitle());
			json.put("link", subject.getLink());
			subjects.add(json);
		});
        int pages=(int) (page.getTotal()/pageParam.getPageSize());
        if((int) (page.getTotal()%pageParam.getPageSize())>0){
        	pages++;
        }
        responseMessage.getBody().put("end",pageParam.getPageNum()>=pages?true:false);
        responseMessage.getBody().put("subjects",subjects);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }

}
