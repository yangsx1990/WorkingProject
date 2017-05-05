package com.hiersun.oohdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_permission")
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限名称
     */
    @Column(name = "per_name")
    private String perName;

    /**
     * 权限路径
     */
    @Column(name = "per_url")
    private String perUrl;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 删除时间/失效时间
     */
    private Date deleted;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限名称
     *
     * @return per_name - 权限名称
     */
    public String getPerName() {
        return perName;
    }

    /**
     * 设置权限名称
     *
     * @param perName 权限名称
     */
    public void setPerName(String perName) {
        this.perName = perName;
    }

    /**
     * 获取权限路径
     *
     * @return per_url - 权限路径
     */
    public String getPerUrl() {
        return perUrl;
    }

    /**
     * 设置权限路径
     *
     * @param perUrl 权限路径
     */
    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取删除时间/失效时间
     *
     * @return deleted - 删除时间/失效时间
     */
    public Date getDeleted() {
        return deleted;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param deleted 删除时间/失效时间
     */
    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}