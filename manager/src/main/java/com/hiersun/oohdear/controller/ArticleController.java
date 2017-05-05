package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/9.
 */

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.hiersun.oohdear.entity.ArticleInfo;
import com.hiersun.oohdear.entity.ArticleLabel;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.vo.ArticleInfoVo;
import com.hiersun.oohdear.service.ArticleInfoService;
import com.hiersun.oohdear.service.ArticleLabelService;

/**
 * Description:内容控制管理器
 * Author: liubaocheng
 * Create: 2017-03-09 15:39
 **/
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController{

    @Autowired
    private ArticleInfoService articleInfoService;

    @Autowired
    private ArticleLabelService articleLabelService;

    /**
     * 页面跳转
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:list")
    public String getArticleList(Model model){
        List<ArticleLabel> articleLabelList = articleLabelService.getLabelList();
        model.addAttribute("labelList",articleLabelList);
        return "article/list";
    }

    /**
     * 用户会员信息列表
     * @param articleInfoVo 页面查询帮助类
     * @param basePageModel  返回值帮助类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:list")
    public BasePageModel getArticleList(ArticleInfoVo articleInfoVo, BasePageModel basePageModel){
        Page<?> page = pageWapper(articleInfoVo,propList());
        articleInfoService.getArticleInfoList(articleInfoVo);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 隐藏/取消隐藏
     * @param articleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("mask")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:mask")
    public boolean mask(ArticleInfo article){
        return articleInfoService.maskArticleInfo(article)>0;
    }

    /**
     * 获取信息
     * @param articleInfo
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:detail")
    public String detail(ArticleInfo article,Model model){
        article = articleInfoService.getArticleInfoById(article);
        String[] str = null;
        String interest = article.getArticleInterest();
        if(article.getArticleInterest()!=null && !(article.getArticleInterest()).equals("")){
            str = interest.split(",");
        }
        model.addAttribute("article",article);
        model.addAttribute("interests",articleInfoService.getArticleInfoByIds(str));
        return "article/detail";
    }


    /**
     * 更新信息页面回显
     * @param articleInfo
     * @param  model 返回视图对象
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:update")
    public String update(ArticleInfo article,Model model){
        article = articleInfoService.getArticleInfoById(article);
        String[] str = null;
        String interest = article.getArticleInterest();
        if(article.getArticleInterest()!=null && !(article.getArticleInterest()).equals("")){
            str = interest.split(",");
        }
        List<ArticleLabel> articleLabelList = articleLabelService.getLabelList();
        model.addAttribute("labelList",articleLabelList);
        model.addAttribute("article",article);
        model.addAttribute("interests",articleInfoService.getArticleInfoByIds(str));
        return "article/update";
    }

    /**
     * 保存添加信息
     * @param articleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:update")
    public boolean update(ArticleInfo article, String[] articleIds,String articleInfoELS){
    	article.setArticleInterest(dealParameter(articleIds));
        return articleInfoService.updateArticleInfo(article,articleInfoELS)>0;
    }

    /**
     * 处理接受的数组参数
     * @param articleIds
     * @return
     */
    public String dealParameter(String[] articleIds){
        String articleInterest = null;
        if(articleIds!=null && articleIds.length>0){
            StringBuffer sb = new StringBuffer();
            for(String interest:articleIds){
                sb.append(interest).append(",");
            }
            articleInterest = sb.substring(0,sb.length()-1);
        }
        return articleInterest;
    }

    /**
     * 添加页面跳转
     * @param model
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:add")
    public String add(Model model){
        List<ArticleLabel> articleLabelList = articleLabelService.getLabelList();
        model.addAttribute("labelList",articleLabelList);
        return "article/add";
    }


    /**
     * 保存添加信息
     * @param articleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:add")
    public boolean add(ArticleInfo article, String[] articleIds,String articleInfoELS){
        article.setArticleInterest(dealParameter(articleIds));
        article.setNumber(0);
        return articleInfoService.saveArticleInfo(article,articleInfoELS)>0;
    }

    @RequestMapping(value = "select",method = RequestMethod.GET)
    public String select(Model model){
        List<ArticleLabel> articleLabelList = articleLabelService.getLabelList();
        model.addAttribute("labelList",articleLabelList);
        return "article/select";
    }

//    /**
//     * 隐藏信息（修改delete状态）
//     * @param articleInfo
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "delete")
//    public String delete(ArticleInfo articleInfo){
//        return "article/edit";
//    }


    /**
     * 设置置顶
     * @param
     * @return
     */
    @RequestMapping(value = "top",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:top")
    public String setTop(ArticleInfo article,Model model){
        article = articleInfoService.getArticleInfoById(article);
        model.addAttribute("article",article);
        return "article/top";
    }

    /**
     * 设置置顶
     * @param articleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "top",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("article:top")
    public boolean setTop(ArticleInfo article){
        return articleInfoService.setTop(article)>0;
    }

    public String[] propList(){
        return new String[]{"","id","title","articleLabel","created","number","picture","topStatus"};
    }
}
