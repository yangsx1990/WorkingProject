package com.hiersun.oohdear.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_express_dict")
public class SysExpressDict {
    /**
     * 表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 系统物流公司编码
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 是否可用
     */
    private Boolean enable;

    /**
     * 公司名称首字母
     */
    private String initial;

    /**
     * 物流公司图标地址
     */
    @Column(name = "logo_url")
    private String logoUrl;

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
     * 获取公司名称
     *
     * @return company_name - 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称
     *
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取系统物流公司编码
     *
     * @return company_code - 系统物流公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 设置系统物流公司编码
     *
     * @param companyCode 系统物流公司编码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 获取是否可用
     *
     * @return enable - 是否可用
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否可用
     *
     * @param enable 是否可用
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取公司名称首字母
     *
     * @return initial - 公司名称首字母
     */
    public String getInitial() {
        return initial;
    }

    /**
     * 设置公司名称首字母
     *
     * @param initial 公司名称首字母
     */
    public void setInitial(String initial) {
        this.initial = initial;
    }

    /**
     * 获取物流公司图标地址
     *
     * @return logo_url - 物流公司图标地址
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * 设置物流公司图标地址
     *
     * @param logoUrl 物流公司图标地址
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}