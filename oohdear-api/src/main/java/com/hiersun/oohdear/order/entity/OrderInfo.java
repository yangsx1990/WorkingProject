package com.hiersun.oohdear.order.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_info")
public class OrderInfo {
    /**
     * 表Id
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
     * 订单状态
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 买家编号
     */
    @Column(name = "buyer_no")
    private String buyerNo;

    /**
     * 订单金额
     */
    @Column(name = "order_price")
    private BigDecimal orderPrice;

    /**
     * 邮寄地址
     */
    @Column(name = "ship_address")
    private String shipAddress;

    /**
     * 备注
     */
    private String comment;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 商品编号
     */
    @Column(name = "goods_no")
    private String goodsNo;

    /**
     * 刻字内容
     */
    private String lettering;

    /**
     * 款式
     */
    @Column(name = "goods_style")
    private String goodsStyle;

    /**
     * 材质
     */
    @Column(name = "goods_material")
    private String goodsMaterial;

    /**
     * 尺寸
     */
    @Column(name = "goods_size")
    private String goodsSize;

    /**
     * 首图
     */
    private String cover;

    /**
     * 减免金额
     */
    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    /**
     * 实际支付金额
     */
    @Column(name = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 优惠券ID
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 获取表Id
     *
     * @return id - 表Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置表Id
     *
     * @param id 表Id
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
     * 获取订单状态
     *
     * @return order_status - 订单状态
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置订单状态
     *
     * @param orderStatus 订单状态
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
     * 获取买家编号
     *
     * @return buyer_no - 买家编号
     */
    public String getBuyerNo() {
        return buyerNo;
    }

    /**
     * 设置买家编号
     *
     * @param buyerNo 买家编号
     */
    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    /**
     * 获取订单金额
     *
     * @return order_price - 订单金额
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * 设置订单金额
     *
     * @param orderPrice 订单金额
     */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 获取邮寄地址
     *
     * @return ship_address - 邮寄地址
     */
    public String getShipAddress() {
        return shipAddress;
    }

    /**
     * 设置邮寄地址
     *
     * @param shipAddress 邮寄地址
     */
    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    /**
     * 获取备注
     *
     * @return comment - 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注
     *
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取支付类型
     *
     * @return pay_type - 支付类型
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置支付类型
     *
     * @param payType 支付类型
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取商品编号
     *
     * @return goods_no - 商品编号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置商品编号
     *
     * @param goodsNo 商品编号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    /**
     * 获取刻字内容
     *
     * @return lettering - 刻字内容
     */
    public String getLettering() {
        return lettering;
    }

    /**
     * 设置刻字内容
     *
     * @param lettering 刻字内容
     */
    public void setLettering(String lettering) {
        this.lettering = lettering;
    }

    /**
     * 获取款式
     *
     * @return goods_style - 款式
     */
    public String getGoodsStyle() {
        return goodsStyle;
    }

    /**
     * 设置款式
     *
     * @param goodsStyle 款式
     */
    public void setGoodsStyle(String goodsStyle) {
        this.goodsStyle = goodsStyle;
    }

    /**
     * 获取材质
     *
     * @return goods_material - 材质
     */
    public String getGoodsMaterial() {
        return goodsMaterial;
    }

    /**
     * 设置材质
     *
     * @param goodsMaterial 材质
     */
    public void setGoodsMaterial(String goodsMaterial) {
        this.goodsMaterial = goodsMaterial;
    }

    /**
     * 获取尺寸
     *
     * @return goods_size - 尺寸
     */
    public String getGoodsSize() {
        return goodsSize;
    }

    /**
     * 设置尺寸
     *
     * @param goodsSize 尺寸
     */
    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    /**
     * 获取首图
     *
     * @return cover - 首图
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置首图
     *
     * @param cover 首图
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * 获取减免金额
     *
     * @return credit_amount - 减免金额
     */
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    /**
     * 设置减免金额
     *
     * @param creditAmount 减免金额
     */
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    /**
     * 获取实际支付金额
     *
     * @return pay_amount - 实际支付金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 设置实际支付金额
     *
     * @param payAmount 实际支付金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取优惠券ID
     *
     * @return coupon_id - 优惠券ID
     */
    public Integer getCouponId() {
        return couponId;
    }

    /**
     * 设置优惠券ID
     *
     * @param couponId 优惠券ID
     */
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }
}