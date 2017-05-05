package com.hiersun.oohdear.controller;/**
 * Created by liubaocheng on 2017/3/14.
 */

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.PageParam;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-14 10:25
 **/
public class BaseController  {


    /**
     * 页面查询包赚
     * @param pageParam 分页参数
     * @param sortStr 要排序的数组
     */
    public Page<?> pageWapper(PageParam pageParam,String[] sortStr){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        pageParam.setSortPro(sortStr[pageParam.getiSortCol_0()]);
        return page;
    }

    /**
     * dataTable 页面dataTable参数包装
     * @param page 分页数据
     * @param basePageModel 返回类型
     */
    public BasePageModel dataTableWapper(Page<?> page, BasePageModel basePageModel){
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int)page.getTotal());
        basePageModel.setiTotalRecords((int)page.getTotal());
        return basePageModel;
    }
}
