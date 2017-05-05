package com.hiersun.oohdear.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	 * 实际支付金额
	 */
	@Column(name = "order_price")
	private BigDecimal orderPrice;

	@Column(name = "ship_address")
	private String shipAddress;

	private String comment;

	@Column(name = "pay_type")
	private Integer payType;

	@Column(name = "goods_no")
	private String goodsNo;

	private String lettering;

	@Column(name = "goods_style")
	private String goodsStyle;

	@Column(name = "goods_material")
	private String goodsMaterial;

	@Column(name = "goods_size")
	private String goodsSize;

	@Column(name = "cover")
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
	 * 实际支付金额
	 */
	@Column(name = "coupon_id")
	private Integer couponId;

	
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

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
	 * 获取卖家编号
	 *
	 * @return order_price - 卖家编号
	 */
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	/**
	 * 设置卖家编号
	 *
	 * @param orderPrice 卖家编号
	 */
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * @return ship_address
	 */
	public String getShipAddress() {
		return shipAddress;
	}

	/**
	 * @param shipAddress
	 */
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	/**
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return pay_type
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * @param payType
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * @return goods_no
	 */
	public String getGoodsNo() {
		return goodsNo;
	}

	/**
	 * @param goodsNo
	 */
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	/**
	 * @return lettering
	 */
	public String getLettering() {
		return lettering;
	}

	/**
	 * @param lettering
	 */
	public void setLettering(String lettering) {
		this.lettering = lettering;
	}

	/**
	 * @return goods_style
	 */
	public String getGoodsStyle() {
		return goodsStyle;
	}

	/**
	 * @param goodsStyle
	 */
	public void setGoodsStyle(String goodsStyle) {
		this.goodsStyle = goodsStyle;
	}

	/**
	 * @return goods_material
	 */
	public String getGoodsMaterial() {
		return goodsMaterial;
	}

	/**
	 * @param goodsMaterial
	 */
	public void setGoodsMaterial(String goodsMaterial) {
		this.goodsMaterial = goodsMaterial;
	}

	/**
	 * @return goods_size
	 */
	public String getGoodsSize() {
		return goodsSize;
	}

	/**
	 * @param goodsSize
	 */
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

}