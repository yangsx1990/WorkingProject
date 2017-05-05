package com.hiersun.oohdear.entity.vo;

import java.util.ArrayList;
import java.util.List;

import com.hiersun.oohdear.entity.OrderExpress;
import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.OrderLog;
import com.hiersun.oohdear.entity.OrderPaymentInfo;
import com.hiersun.oohdear.entity.OrderStatus;
import com.hiersun.oohdear.entity.TpExpressInfo;
import com.hiersun.oohdear.entity.UserMemberInfo;
/**
 * 订单详情页数据
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class OrderDetailVo {

	private List<String[]> statusAxis = new ArrayList<String[]>();
	private OrderInfo orderInfo;
	private OrderPaymentInfo orderPaymentInfo;
	private OrderExpress orderExpress;
	private List<TpExpressInfo> expressInfoList;
	private List<OrderLog> orderLogList;
	private UserMemberInfo memberInfo;
	private String companyName;
	private List<String> paintingUrls;
	private String refundReason;
	private List<OrderLog> orderCommentList;
	private List<OrderLog> userCommentList;
	private String couponTypeFullName;

	public String getOrderStatusName() {
		if (orderInfo == null || orderInfo.getOrderStatus() == null) {
			return "";
		}
		return OrderStatus.forOrderCode(orderInfo.getOrderStatus());
	}

	public List<String[]> getStatusAxis() {
		return statusAxis;
	}

	public void setStatusAxis(List<String[]> statusAxis) {
		this.statusAxis = statusAxis;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo == null ? new OrderInfo() : orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public OrderExpress getOrderExpress() {
		return orderExpress == null ? new OrderExpress() : orderExpress;
	}

	public void setOrderExpress(OrderExpress orderExpress) {
		this.orderExpress = orderExpress;
	}

	public OrderPaymentInfo getOrderPaymentInfo() {
		return orderPaymentInfo == null ? new OrderPaymentInfo() : orderPaymentInfo;
	}

	public void setOrderPaymentInfo(OrderPaymentInfo orderPaymentInfo) {
		this.orderPaymentInfo = orderPaymentInfo;
	}

	public List<OrderLog> getOrderLogList() {
		return orderLogList;
	}

	public void setOrderLogList(List<OrderLog> orderLogList) {
		this.orderLogList = orderLogList;
	}

	public List<TpExpressInfo> getExpressInfoList() {
		return expressInfoList;
	}

	public void setExpressInfoList(List<TpExpressInfo> expressInfoList) {
		this.expressInfoList = expressInfoList;
	}

	public UserMemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(UserMemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<String> getPaintingUrls() {
		return paintingUrls;
	}

	public void setPaintingUrls(List<String> paintingUrls) {
		this.paintingUrls = paintingUrls;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public List<OrderLog> getOrderCommentList() {
		return orderCommentList;
	}

	public void setOrderCommentList(List<OrderLog> orderCommentList) {
		this.orderCommentList = orderCommentList;
	}

	public List<OrderLog> getUserCommentList() {
		return userCommentList;
	}

	public void setUserCommentList(List<OrderLog> userCommentList) {
		this.userCommentList = userCommentList;
	}

	public String getCouponTypeFullName() {
		return couponTypeFullName;
	}

	public void setCouponTypeFullName(String couponTypeFullName) {
		this.couponTypeFullName = couponTypeFullName;
	}
}
