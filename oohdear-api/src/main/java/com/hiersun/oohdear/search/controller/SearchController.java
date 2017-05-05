package com.hiersun.oohdear.search.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.article.entity.ArticleInfo;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.els.SearchEls;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-07 9:46
 **/
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchEls searchEls;

    @RequestMapping
    public ResponseEntity<ResponseMessage> search(String keyword,ResponseMessage responseMessage){
        //elasticsearch 这里使用多条件搜索引擎（文章标题，和文章内容）
//        Page<ArticleInfo> page = searchEls.search(QueryBuilders.multiMatchQuery(keyword,"articleName","articleInfo"),new PageRequest(1,10));
        Object object = searchEls.search(QueryBuilders.multiMatchQuery(keyword,"articleName","articleInfo"));
        if(object!=null ){
            responseMessage.getBody().put("content",object);
            return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
        }else{
           responseMessage.getBody().put("content","搜索历史为空");
           return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
        }
    }

}
