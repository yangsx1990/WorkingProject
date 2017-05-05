package com.hiersun.oohdear.entity;/**
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

    /**
     * 目前不明
     */
    private String bRegex;
    /**
     *
     */
    private String iColumns;
    /**
     * 每页显示行数
     */
    private int iDisplayLength;
    /**
     * 起始行索引
     */
    private int iDisplayStart;
    /**
     *
     */
    private String iSortingCols;
    /**
     *
     */
    private String sColumns;

    /**
     * 排序字段
     */
    private Integer iSortCol_0;
    /**
     * 数据库排序字段
     */
    private String sortPro;
    /**
     * （asc/desc）
     */
    private String sSortDir_0;

    private String sSearch;

    /**
     * 正常返回给前台
     */
    private String sEcho;
    public String getbRegex() {
        return bRegex;
    }
    public void setbRegex(String bRegex) {
        this.bRegex = bRegex;
    }
    public String getiColumns() {
        return iColumns;
    }
    public void setiColumns(String iColumns) {
        this.iColumns = iColumns;
    }
    public int getiDisplayLength() {
        return iDisplayLength;
    }
    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }
    public int getiDisplayStart() {
        return iDisplayStart;
    }
    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }
    public String getiSortingCols() {
        return iSortingCols;
    }
    public void setiSortingCols(String iSortingCols) {
        this.iSortingCols = iSortingCols;
    }
    public String getsColumns() {
        return sColumns;
    }
    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }
    public String getsEcho() {
        return sEcho;
    }
    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }
    public String getsSortDir_0() {
        return sSortDir_0;
    }
    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }
    public String getSortPro() {
        return sortPro;
    }
    public void setSortPro(String sortPro) {
        this.sortPro = sortPro;
    }
    public Integer getiSortCol_0() {
        return iSortCol_0;
    }
    public void setiSortCol_0(Integer iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }
    public String getsSearch() {
        return sSearch;
    }
    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }
}
