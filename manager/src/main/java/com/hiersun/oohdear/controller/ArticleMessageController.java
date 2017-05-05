package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/9.
 */

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.hiersun.oohdear.entity.ArticleMessage;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.vo.ArticleMessageVo;
import com.hiersun.oohdear.service.ArticleMessageService;

/**
 * Description:  留言控制管理器
 * Author: liubaocheng
 * Create: 2017-03-09 11:59
 **/
@Controller
@RequestMapping("message")
public class ArticleMessageController extends BaseController{

    @Autowired
    private ArticleMessageService articleMessageService;


    @RequestMapping(value = "list",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("message:list")
    public String getArticleMessage(){
        return "message/list";
    }

    /**
     * 获取列表信息
     * @param articleMessageVo
     * @param basePageModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("message:list")
    public BasePageModel getArticleMessage(ArticleMessageVo articleMessageVo,BasePageModel basePageModel){
        Page<?> page = pageWapper(articleMessageVo,propList());
        articleMessageService.getMessageList(articleMessageVo);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 屏蔽消息
     * @param articleMessage
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "mask",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("message:mask")
    public boolean mask(ArticleMessage articleMessage){
        return articleMessageService.maskDeleteStatus(articleMessage)>0;
    }


    public String[] propList(){
        return new String[]{"","id","memberName","content","created","articleName","deleted"};
    }
}
