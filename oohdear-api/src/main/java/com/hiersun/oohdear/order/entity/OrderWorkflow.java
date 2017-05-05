package com.hiersun.oohdear.order.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "order_workflow")
public class OrderWorkflow {
    /**
     * 表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单编号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 订单金额
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 发货地址
     */
    private String operator;

    /**
     * 买家编号
     */
    private Date created;

    private String content;

    public OrderWorkflow(){}
    public OrderWorkflow(String orderNo,Integer orderStatus,String operator,String content){
    	this.orderNo=orderNo;
    	this.orderStatus=orderStatus;
    	this.operator=operator;
    	this.content=content;
    	this.created=new Date();
    }
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
     * 获取订单金额
     *
     * @return order_status - 订单金额
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单金额
     *
     * @param orderStatus 订单金额
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取发货地址
     *
     * @return operator - 发货地址
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置发货地址
     *
     * @param operator 发货地址
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取买家编号
     *
     * @return created - 买家编号
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置买家编号
     *
     * @param created 买家编号
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}