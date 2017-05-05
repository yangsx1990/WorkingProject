package com.hiersun.oohdear.article.entity;/**
 * Created by liubaocheng on 2017/1/3.
 */

/**
 * Description: 分页请求参数
 * Author: liubaocheng
 * Create: 2017-01-03 15:41
 **/
public class PageParam {

    //起始页
    private int pageNum=1;
    //页面长度
    private int pageSize=10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
