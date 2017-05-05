package com.hiersun.oohdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "order_log")
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作类型（1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货）
     */
    @Column(name = "operator_type")
    private Integer operatorType;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 备注内容
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 角色id（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 备注类型[1：订单；2：用户备注；3:不可做原因备注]
     */
    @Column(name = "comment_type")
    private Integer commentType;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作类型（1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货）
     *
     * @return operator_type - 操作类型（1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货）
     */
    public Integer getOperatorType() {
        return operatorType;
    }

    /**
     * 设置操作类型（1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货）
     *
     * @param operatorType 操作类型（1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货）
     */
    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    /**
     * 获取用户名称
     *
     * @return nickname - 用户名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户名称
     *
     * @param nickname 用户名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取备注内容
     *
     * @return comment - 备注内容
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注内容
     *
     * @param comment 备注内容
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * 获取角色id（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     *
     * @return role_id - 角色id（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     *
     * @param roleId 角色id（1-客服；2-设计；3-商品中心；4-运营；5-管理员）
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取订单编号
     *
     * @return order_no - 订单编号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单编号
     *
     * @param orderNo 订单编号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取备注类型[1：订单；2：用户备注]
     *
     * @return comment_type - 备注类型[1：订单；2：用户备注；3:不可做原因备注]
     */
    public Integer getCommentType() {
        return commentType;
    }

    /**
     * 设置备注类型[1：订单；2：用户备注]
     *
     * @param commentType 备注类型[1：订单；2：用户备注；3:不可做原因备注]
     */
    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }
}