package com.hiersun.oohdear.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hiersun.oohdear.order.entity.Coupon;
import com.hiersun.oohdear.order.entity.OrderInfo;

/**
 * 订单服务
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public interface OrderService {

	/**
	 * 根据订单号查询订单
	 * @param orderNo 订单号
	 * @return
	 */
	OrderInfo queryByOrderNo(String orderNo);

	/**
	 * 获取订单列表
	 * @param memberNo 会员编号
	 * @return
	 */
	List<OrderInfo> orderListByMemberNo(String memberNo);

	/**
	 * 定制流程-保存收货地址（未登录）
	 * @param nickname 订制人
	 * @param mobile 定制人手机号
	 * @param consignee 收货人
	 * @param consigneeMobile 收货人手机号
	 * @param detail 详细地址
	 * @param itemCode 类别code
	 * @return
	 */
	Map<String,String> saveUserAndAddress(String nickname, String mobile, String consignee, String consigneeMobile,
			String zone, String detail);

	/**
	 * 去結算
	 * @param addressId 地址id
	 * @param payType 支付类型
	 * @param comment 备注
	 * @param cover 画作头图
	 * @param itemCode 类别code
	 * @param coupon 优惠券码
	 */
	Map<String,Object> settle(String memberNo, int addressId, int payType, String comment, String cover,String styleCode,String materialCode
			,String sizeCode,String style,String material,String size,String lettering,String coupon);

	/**
	 * 立即付款
	 * @param orderNo 订单号
	 * @param payAmount 支付金额
	 * @param tradeNo 流水号
	 * @param buyerLogonId 买家账号（例如：支付宝账号）
	 */
	void pay(String orderNo, String payAmount,String tradeNo,String buyerLogonId);

	/**
	 * 退款
	 * @param orderNo 订单号
	 * @param memberNo 会员编号
	 * @param reason 退款理由 
	 * @return
	 */
	Boolean refund(String orderNo, String memberNo,String reason);

	/**
	 * 确认收货
	 * @param orderNo 订单号
	 * @param memberNo 会员编号
	 * @return
	 */
	Boolean confirm(String orderNo, String memberNo);

	/**
	 * 订单详情
	 * @param orderNo
	 * @param memberNo 会员编号
	 * @return
	 */
	JSONObject detail(String orderNo, String memberNo);

	/**
	 * 获取订单金额
	 * @param itemCode 类别code
	 * @return
	 */

	BigDecimal getOrderMoney(String style,String material,String size);

	/**
	 * 模糊查询金额范围
	 * @param itemCode
	 * @return
	 */
	Map<String,BigDecimal> getFuzzyOrderMoney(String style,String material,String size);
	/**
	 * 校验优惠券码是否可用
	 * @param coupons
	 * @return
	 */
	List<Coupon>  queryCoupons(String coupons);
	/**
	 * 取消订单
	 * @param orderNo
	 * @param memberNo
	 * @return
	 */
	Boolean cancel(String orderNo,String memberNo);
}
