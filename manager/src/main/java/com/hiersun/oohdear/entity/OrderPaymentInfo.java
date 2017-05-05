package com.hiersun.oohdear.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_payment_info")
public class OrderPaymentInfo {
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
     * 支付方式（1.支付宝；2.微信）
     */
    private Integer payment;

    /**
     * 支付金额
     */
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 第三方流水号
     */
    @Column(name = "serial_numbe")
    private String serialNumbe;

    /**
     * 支付时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 支付人
     */
    private String payer;

    /**
     * 支付结果（1.成功；2.失败）
     */
    @Column(name = "payment_result")
    private Integer paymentResult;

    /**
     * 支付失败相关信息（支付失败编号以及文字描述）
     */
    @Column(name = "payment_failure_message")
    private String paymentFailureMessage;

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
     * 获取支付方式（1.支付宝；2.微信）
     *
     * @return payment - 支付方式（1.支付宝；2.微信）
     */
    public Integer getPayment() {
        return payment;
    }

    /**
     * 设置支付方式（1.支付宝；2.微信）
     *
     * @param payment 支付方式（1.支付宝；2.微信）
     */
    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    /**
     * 获取支付金额
     *
     * @return payment_amount - 支付金额
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 设置支付金额
     *
     * @param paymentAmount 支付金额
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 获取第三方流水号
     *
     * @return serial_numbe - 第三方流水号
     */
    public String getSerialNumbe() {
        return serialNumbe;
    }

    /**
     * 设置第三方流水号
     *
     * @param serialNumbe 第三方流水号
     */
    public void setSerialNumbe(String serialNumbe) {
        this.serialNumbe = serialNumbe;
    }

    /**
     * 获取支付时间
     *
     * @return payment_time - 支付时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置支付时间
     *
     * @param paymentTime 支付时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * 获取支付人
     *
     * @return payer - 支付人
     */
    public String getPayer() {
        return payer;
    }

    /**
     * 设置支付人
     *
     * @param payer 支付人
     */
    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * 获取支付结果（1.成功；2.失败）
     *
     * @return payment_result - 支付结果（1.成功；2.失败）
     */
    public Integer getPaymentResult() {
        return paymentResult;
    }

    /**
     * 设置支付结果（1.成功；2.失败）
     *
     * @param paymentResult 支付结果（1.成功；2.失败）
     */
    public void setPaymentResult(Integer paymentResult) {
        this.paymentResult = paymentResult;
    }

    /**
     * 获取支付失败相关信息（支付失败编号以及文字描述）
     *
     * @return payment_failure_message - 支付失败相关信息（支付失败编号以及文字描述）
     */
    public String getPaymentFailureMessage() {
        return paymentFailureMessage;
    }

    /**
     * 设置支付失败相关信息（支付失败编号以及文字描述）
     *
     * @param paymentFailureMessage 支付失败相关信息（支付失败编号以及文字描述）
     */
    public void setPaymentFailureMessage(String paymentFailureMessage) {
        this.paymentFailureMessage = paymentFailureMessage;
    }
}