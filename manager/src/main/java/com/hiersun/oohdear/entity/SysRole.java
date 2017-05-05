package com.hiersun.oohdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 创建人/操作人
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
     * 逻辑删除状态0为未删除/可用，1为已删除/不可用
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
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取创建人/操作人
     *
     * @return operator - 创建人/操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置创建人/操作人
     *
     * @param operator 创建人/操作人
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
     * 获取逻辑删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 逻辑删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置逻辑删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 逻辑删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}