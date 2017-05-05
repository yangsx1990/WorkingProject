package com.hiersun.oohdear.entity;

import java.util.Date;

import javax.persistence.*;

@Table(name = "order_express")
public class OrderExpress {
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
	 * 物流公司编号
	 */
	@Column(name = "sys_company_code")
	private String sysCompanyCode;

	/**
	 * 物流公司快递编号
	 */
	@Column(name = "express_no")
	private String expressNo;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 收货地址
	 */
	@Column(name = "shipping_address")
	private String shippingAddress;

	/**
	 * 收货人
	 */
	private String consignee;

	/**
	 * 收货人手机号
	 */
	@Column(name = "consignee_mobile")
	private String consigneeMobile;

	/**
	 * 回寄收货地址
	 */
	@Column(name = "return_addesss")
	private String returnAddesss;

	/**
	 * 回寄收货人
	 */
	@Column(name = "return_consignee")
	private String returnConsignee;

	/**
	 * 回寄收货人手机号
	 */
	@Column(name = "return_mobile")
	private String returnMobile;

	@Column(name = "shipped_time")
	private Date shippedTime;

	private String zone;
	public OrderExpress() {
	}

	public OrderExpress(String orderNo2, Date date, String zone, String address, String consignee2, String consigneeMobile2) {
		this.orderNo = orderNo2;
		this.created = date;
		this.shippingAddress = address;
		this.consignee = consignee2;
		this.consigneeMobile = consigneeMobile2;
		this.zone = zone;
	}

	public OrderExpress(String orderNo, String sysCompanyCode, String expressNo, String returnconsignee,
			String returnmobile, String address, String returnConsignee, String returnMobile, String resturnAddress) {
		this.orderNo = orderNo;
		this.sysCompanyCode = sysCompanyCode;
		this.expressNo = expressNo;
		this.consignee = returnconsignee;
		this.consigneeMobile = returnmobile;
		this.shippingAddress = address;
		this.returnAddesss = resturnAddress;
		this.returnMobile = returnMobile;
		this.returnConsignee = returnConsignee;
		this.created = new Date();
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
	 * 获取物流公司编号
	 *
	 * @return sys_company_code - 物流公司编号
	 */
	public String getSysCompanyCode() {
		return sysCompanyCode;
	}

	/**
	 * 设置物流公司编号
	 *
	 * @param sysCompanyCode 物流公司编号
	 */
	public void setSysCompanyCode(String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}

	/**
	 * 获取物流公司快递编号
	 *
	 * @return express_no - 物流公司快递编号
	 */
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * 设置物流公司快递编号
	 *
	 * @param expressNo 物流公司快递编号
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
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
	 * 获取收货地址
	 *
	 * @return shipping_address - 收货地址
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * 设置收货地址
	 *
	 * @param shippingAddress 收货地址
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * 获取收货人
	 *
	 * @return consignee - 收货人
	 */
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置收货人
	 *
	 * @param consignee 收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 获取收货人手机号
	 *
	 * @return consignee_mobile - 收货人手机号
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	/**
	 * 设置收货人手机号
	 *
	 * @param consigneeMobile 收货人手机号
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	/**
	 * 获取回寄收货地址
	 *
	 * @return return_addesss - 回寄收货地址
	 */
	public String getReturnAddesss() {
		return returnAddesss;
	}

	/**
	 * 设置回寄收货地址
	 *
	 * @param returnAddesss 回寄收货地址
	 */
	public void setReturnAddesss(String returnAddesss) {
		this.returnAddesss = returnAddesss;
	}

	/**
	 * 获取回寄收货人
	 *
	 * @return return_consignee - 回寄收货人
	 */
	public String getReturnConsignee() {
		return returnConsignee;
	}

	/**
	 * 设置回寄收货人
	 *
	 * @param returnConsignee 回寄收货人
	 */
	public void setReturnConsignee(String returnConsignee) {
		this.returnConsignee = returnConsignee;
	}

	/**
	 * 获取回寄收货人手机号
	 *
	 * @return return_mobile - 回寄收货人手机号
	 */
	public String getReturnMobile() {
		return returnMobile;
	}

	/**
	 * 设置回寄收货人手机号
	 *
	 * @param returnMobile 回寄收货人手机号
	 */
	public void setReturnMobile(String returnMobile) {
		this.returnMobile = returnMobile;
	}

	public Date getShippedTime() {
		return shippedTime;
	}

	public void setShippedTime(Date shippedTime) {
		this.shippedTime = shippedTime;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

}
