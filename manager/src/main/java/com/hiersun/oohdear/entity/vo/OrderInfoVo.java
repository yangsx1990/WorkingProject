package com.hiersun.oohdear.entity.vo;

import com.hiersun.oohdear.entity.OrderStatus;

/**
 * 订单查询条件
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class OrderInfoVo extends BaseVoHelper {

	private String id;
	private String orderNo;
	private String memberNo;
	private String mobile;
	private Integer orderStatus;
	private String price;
	private String payAmount;
	private String created;
	private String orderStatusName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public String getOrderStatusName() {
		if (orderStatusName == null) {
			return orderStatus == null ? "" : OrderStatus.forOrderCode(orderStatus);
		}
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
