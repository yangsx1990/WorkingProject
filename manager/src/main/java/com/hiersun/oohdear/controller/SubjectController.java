package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/9.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;
import com.github.pagehelper.Page;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.SubjectInfo;
import com.hiersun.oohdear.entity.vo.SubjectInfoVo;
import com.hiersun.oohdear.service.FileService;
import com.hiersun.oohdear.service.SubjectInfoService;

/**
 * Description:专题控制管理器
 * Author: liubaocheng
 * Create: 2017-03-09 15:07
 **/
@Controller
@RequestMapping("subject")
public class SubjectController extends BaseController{
	@Value("${user.upload.path}")
	private String uploadPath;
	@Autowired
	private FileService fileService;
    @Autowired
    private SubjectInfoService subjectInfoService;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("_yyyyMMddHHmmssSSS");
    
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:list")
    public String getSubjectList(){
        return "subject/list";
    }

    /**
     * 获取列表
     * @param subjectInfoVo 页面查询帮助类
     * @param basePageModel  返回值帮助类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:list")
    public BasePageModel getSubjectList(SubjectInfoVo subjectInfoVo,BasePageModel basePageModel){
        Page<?> page = pageWapper(subjectInfoVo,propList());
        subjectInfoService.getSubjectList(subjectInfoVo);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 隐藏/取消隐藏
     * @param subjectInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "mask",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:mask")
    public boolean mask(SubjectInfo subjectInfo){
        return subjectInfoService.maskSubjectInfo(subjectInfo)>0;
    }

    /**
     * 编辑信息-页面跳转
     * @param subjectInfo
     * @return
     */
    @RequestMapping(value = "editUI")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:editUI")
    public String editUI(SubjectInfo subjectInfo, Model model){
        model.addAttribute("subject",subjectInfoService.getSubjectInfo(subjectInfo));
        return "subject/detail";
    }
    
    /**
     * 编辑信息
     * @param subjectInfo
     * @return
     */
    @RequestMapping(value = "edit")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:edit")
    public String edit(SubjectInfo subjectInfo, Model model){
    	subjectInfoService.updateSubjectInfo(subjectInfo);
        return "subject/list";
    }
    
	/**
	 * 发布专题-页面
	 * @return
	 */
    @RequestMapping(value = "addUI",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:addUI")
    public String addUI(Model model){
        model.addAttribute("subject",new SubjectInfo());
        return "subject/add";
    }
    
    /**
     * 发布专题
     * @param subjectInfo
     * @param model
     * @return TODO:上传首图
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:add")
    public ResponseEntity<Boolean> add(@RequestParam String link,@RequestParam String title,@RequestParam String picture){
    	SubjectInfo subject=new SubjectInfo();
    	subject.setLink(link);
    	subject.setTitle(title);
    	subject.setPicture(picture);
    	return new ResponseEntity<Boolean>(subjectInfoService.addSubjectInfo(subject),HttpStatus.OK);
    }
    
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:upload")
    public ResponseEntity<String> upload(@RequestParam("test")MultipartFile file){
    	System.out.println(file);
    	if(file.isEmpty()){
    		throw new RuntimeException("上传图片不能为空");
    	}
    	String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		String fileName =  SDF.format(new Date()) + suffix;
		String path = fileService.upload(file, fileName, uploadPath);
    	/*String path="http://192.168.4.87/000.jpg";*/
    	return new ResponseEntity<String>(path,HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping("ueditor")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:ueditor")
    public String load(HttpServletRequest request, HttpServletResponse response){
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type","text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
           return new ActionEnter(request,rootPath).exec();
        }catch (Exception e){
            e.printStackTrace();
            return "server error";
        }
    }

    /**
     * 设置置顶
     * @param subjectInfo
     * @return
     */
    @RequestMapping(value = "top",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:top")
    public String setTop(SubjectInfo subjectInfo,Model model){
        subjectInfo = subjectInfoService.getSubjectInfo(subjectInfo);
        model.addAttribute("subject",subjectInfo);
        return "subject/top";
    }

    /**
     * 设置置顶
     * @param subjectInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "top",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("subject:top")
    public boolean setTop(SubjectInfo subjectInfo){
        return subjectInfoService.setTop(subjectInfo)>0;
    }

    public String[] propList(){
        return new String[]{"","id","title","link","created","topValue","deleted"};
    }
}
