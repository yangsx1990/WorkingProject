package com.hiersun.oohdear.order.entity;

import javax.persistence.*;

@Table(name = "tp_express_info")
public class TpExpressInfo {
    /**
     * 表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 快递单号
     */
    @Column(name = "express_no")
    private String expressNo;

    /**
     * 消息体
     */
    private String message;

    /**
     * 跟踪内容
     */
    private String content;

    /**
     * 时间，原格式
     */
    @Column(name = "o_time")
    private String oTime;

    /**
     * 格式化后的时间
     */
    @Column(name = "f_time")
    private String fTime;

    /**
     * 快递单当前的状态
     */
    private String status;

    /**
     * 签收状态
     */
    @Column(name = "sign_in_status")
    private String signInStatus;

    /**
     * 行政区域编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 行政区域名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 快递公司编码
     */
    @Column(name = "sys_company_code")
    private String sysCompanyCode;

    /**
     * 获取表id
     *
     * @return id - 表id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置表id
     *
     * @param id 表id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取快递单号
     *
     * @return express_no - 快递单号
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 设置快递单号
     *
     * @param expressNo 快递单号
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
     * 获取消息体
     *
     * @return message - 消息体
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息体
     *
     * @param message 消息体
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取跟踪内容
     *
     * @return content - 跟踪内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置跟踪内容
     *
     * @param content 跟踪内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取时间，原格式
     *
     * @return o_time - 时间，原格式
     */
    public String getoTime() {
        return oTime;
    }

    /**
     * 设置时间，原格式
     *
     * @param oTime 时间，原格式
     */
    public void setoTime(String oTime) {
        this.oTime = oTime;
    }

    /**
     * 获取格式化后的时间
     *
     * @return f_time - 格式化后的时间
     */
    public String getfTime() {
        return fTime;
    }

    /**
     * 设置格式化后的时间
     *
     * @param fTime 格式化后的时间
     */
    public void setfTime(String fTime) {
        this.fTime = fTime;
    }

    /**
     * 获取快递单当前的状态
     *
     * @return status - 快递单当前的状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置快递单当前的状态
     *
     * @param status 快递单当前的状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取签收状态
     *
     * @return sign_in_status - 签收状态
     */
    public String getSignInStatus() {
        return signInStatus;
    }

    /**
     * 设置签收状态
     *
     * @param signInStatus 签收状态
     */
    public void setSignInStatus(String signInStatus) {
        this.signInStatus = signInStatus;
    }

    /**
     * 获取行政区域编码
     *
     * @return area_code - 行政区域编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置行政区域编码
     *
     * @param areaCode 行政区域编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取行政区域名称
     *
     * @return area_name - 行政区域名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置行政区域名称
     *
     * @param areaName 行政区域名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取快递公司编码
     *
     * @return sys_company_code - 快递公司编码
     */
    public String getSysCompanyCode() {
        return sysCompanyCode;
    }

    /**
     * 设置快递公司编码
     *
     * @param sysCompanyCode 快递公司编码
     */
    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }
}