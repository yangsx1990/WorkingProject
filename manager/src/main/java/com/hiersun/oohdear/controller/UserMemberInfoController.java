package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.UserMemberAddress;
import com.hiersun.oohdear.entity.UserMemberInfo;
import com.hiersun.oohdear.entity.excel.UserMemberInfoExcel;
import com.hiersun.oohdear.entity.vo.OrderInfoVo;
import com.hiersun.oohdear.entity.vo.UserInfoVo;
import com.hiersun.oohdear.entity.vo.UserMemberInfoVo;
import com.hiersun.oohdear.service.AddressService;
import com.hiersun.oohdear.service.OrderService;
import com.hiersun.oohdear.service.UserMemberServiceInfoService;
import com.hiersun.oohdear.util.DateUtil;
import com.hiersun.oohdear.util.ExcelUtils;

/**
 * Description:用户会员信息控制类
 * Author: liubaocheng
 * Create: 2017-03-07 20:19
 **/
@Controller
@RequestMapping("user")
public class UserMemberInfoController extends BaseController{

    @Autowired
    private UserMemberServiceInfoService userMemberServiceInfoService;
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AddressService addressService;
    
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("user:list")
    public String selectList(){
        return "user/list";
    }

    /**
     * 用户会员信息列表
     * @param userMemberInfoVo 页面查询帮助类
     * @param basePageModel  返回值帮助类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list",method = RequestMethod.POST)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("user:list")
    public BasePageModel selectList(UserMemberInfoVo userMemberInfoVo,BasePageModel basePageModel){
        Page<?> page = pageWapper(userMemberInfoVo,propList());
        userMemberServiceInfoService.selectUserMemberInfoList(userMemberInfoVo);
        return dataTableWapper(page,basePageModel);

    }

    /**
     * 详情页查看
     * @param model  返回值帮助类
     * @param userMemberInfo  用户会员信息
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("user:detail")
    public String detail(Model model,@RequestParam(required=true) String memberNo){  	
    	 UserInfoVo user= userMemberServiceInfoService.selectUserMemberInfo(memberNo); 
		 OrderInfoVo orderInfo=new OrderInfoVo();
	     orderInfo.setMemberNo(user.getMemberNo());
		 List<OrderInfoVo> orderList= orderService.orderList(orderInfo);
		 UserMemberAddress userMemberAddress=new UserMemberAddress();
		 userMemberAddress.setMemberNo(memberNo);
		 userMemberAddress.setDeleted(false);
		 List<UserMemberAddress> addressList=addressService.getAddressList(userMemberAddress);
		 addressService.getAddressList(userMemberAddress);
        model.addAttribute("entity",user);
        model.addAttribute("orderList",orderList);
        model.addAttribute("addressList",addressList);
        return "user/detail";
    }

    @RequestMapping("excel")
    @RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
    @RequiresPermissions("user:excel")
    public void excel(HttpServletResponse response){
        List<UserMemberInfo> userMemberInfos = userMemberServiceInfoService.getAllUserMemberInfo();
        if(userMemberInfos!=null && userMemberInfos.size()>0){
            List<UserMemberInfoExcel> excelList = new ArrayList<>();
            UserMemberInfoExcel excel = null;
            for(UserMemberInfo user:userMemberInfos){
                excel = new UserMemberInfoExcel();
                excel.setMemberNo(user.getMemberNo());
                excel.setNickName(user.getNickName());
                excel.setGender(user.getGender()==1? "男":"女");
                excel.setAvatar(user.getAvatar());
                excel.setCreated(DateUtil.formatDate(user.getCreated(),DateUtil.EN_DATETIME_FORMAT));
                excel.setMobile(user.getMobile());
                excel.setDeleted(user.getDeleted()? "不可用":"可用");
                excelList.add(excel);
            }
            ExcelUtils.listToExcel(excelList, getOrderExcelFiledMap(), "表一", response);
        }

    }

    /**
     * 定义表头列表模板
     * @return
     */
    public LinkedHashMap<String, String> getOrderExcelFiledMap(){
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("memberNo", "会员编号");
        fieldMap.put("nickName", "昵称");
        fieldMap.put("gender", "性别");
        fieldMap.put("avatar", "头像");
        fieldMap.put("created", "创建时间");
        fieldMap.put("mobile", "手机号");
        fieldMap.put("deleted", "是否可用");
        return fieldMap;
    }

    public String[] propList(){
        return new String[]{"","id","memberNo","mobile","nickName","created",""};
    }

}
