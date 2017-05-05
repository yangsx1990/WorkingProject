package com.hiersun.oohdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user_role")
public class SysUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 操作人/创建人
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取操作人/创建人
     *
     * @return operator - 操作人/创建人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人/创建人
     *
     * @param operator 操作人/创建人
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