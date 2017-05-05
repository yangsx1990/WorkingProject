package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.ArticleLabel;
import com.hiersun.oohdear.service.ArticleLabelService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description:文章/内容 类别控制管理器
 * Author: liubaocheng
 * Create: 2017-03-09 16:03
 **/
@Controller
@RequestMapping("label")
public class ArticleLabelController {

    @Autowired
    private ArticleLabelService articleLabelService;

    @ResponseBody
    @RequestMapping("list")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("label:list")
    public List<ArticleLabel> getList(){
        return articleLabelService.getLabelList();
    }
}
