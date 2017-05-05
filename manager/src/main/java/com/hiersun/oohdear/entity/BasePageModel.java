package com.hiersun.oohdear.entity;/**
 * Created by liubaocheng on 2017/3/13.
 */

import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-13 14:18
 **/
public class BasePageModel {

    /**
     * 来自客户端 sEcho 的没有变化的复制品
     */
    private String sEcho;
    /**
     * 实际的行数
     */
    private int iTotalRecords;
    /**
     * 过滤之后，实际的行数。
     */
    private int iTotalDisplayRecords;
    /**
     * 可选，以逗号分隔的列名，　
     */
    private String sColumns;
    /**
     * 数组的数组，表格中的实际数据。　
     */
    private List<?> aaData;

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public List<?> getAaData() {
        return aaData;
    }

    public void setAaData(List<?> aaData) {
        this.aaData = aaData;
    }
}
