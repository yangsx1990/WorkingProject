package com.hiersun.oohdear.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.core.OrderStatus;
import com.hiersun.oohdear.order.entity.Coupon;
import com.hiersun.oohdear.order.entity.OrderExpress;
import com.hiersun.oohdear.order.entity.OrderInfo;
import com.hiersun.oohdear.order.entity.OrderLog;
import com.hiersun.oohdear.order.entity.OrderMoney;
import com.hiersun.oohdear.order.entity.OrderPaymentInfo;
import com.hiersun.oohdear.order.entity.OrderWorkflow;
import com.hiersun.oohdear.order.entity.SysExpressDict;
import com.hiersun.oohdear.order.entity.TpExpressInfo;
import com.hiersun.oohdear.order.mapper.CouponMapper;
import com.hiersun.oohdear.order.mapper.OrderExpressMapper;
import com.hiersun.oohdear.order.mapper.OrderInfoMapper;
import com.hiersun.oohdear.order.mapper.OrderLogMapper;
import com.hiersun.oohdear.order.mapper.OrderMoneyMapper;
import com.hiersun.oohdear.order.mapper.OrderPaymentInfoMapper;
import com.hiersun.oohdear.order.mapper.OrderWorkflowMapper;
import com.hiersun.oohdear.order.mapper.SysExpressDictMapper;
import com.hiersun.oohdear.order.mapper.TpExpressInfoMapper;
import com.hiersun.oohdear.order.service.OrderService;
import com.hiersun.oohdear.user.entity.UserMemberAddress;
import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.mapper.UserMemberAddressMapper;
import com.hiersun.oohdear.user.service.AddressService;
import com.hiersun.oohdear.user.service.GeneratorService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.util.DateUtil;
import com.hiersun.oohdear.util.OrderStatusAxis;

/**
 * 订单服务
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private AddressService addressService;
	@Autowired
	private UserMemberAddressMapper addressMapper;
	@Autowired
	private OrderExpressMapper orderExpressMapper;
	@Autowired
	private UserMemberInfoService userMemberInfoService;
	@Autowired
	private GeneratorService generatorService;
	@Autowired
	private TpExpressInfoMapper tpExpressInfoMapper;
	@Autowired
	private SysExpressDictMapper sysExpressDictMapper;
	@Autowired
	private OrderWorkflowMapper orderWorkflowMapper;
	@Autowired
	private OrderMoneyMapper orderMoneyMapper;
	@Autowired
	private OrderPaymentInfoMapper orderPaymentInfoMapper;
	@Autowired
	private OrderLogMapper orderLogMapper;
	@Autowired
	private CouponMapper couponMapper;

	@Override
	public OrderInfo queryByOrderNo(String orderNo) {
		OrderInfo record = new OrderInfo();
		record.setOrderNo(orderNo);
		record = orderInfoMapper.selectOne(record);
		return record;
	}

	@Override
	public List<OrderInfo> orderListByMemberNo(String memberNo) {
		Example example = new Example(OrderInfo.class);
		example.setOrderByClause("created DESC");
		example.createCriteria().andEqualTo("buyerNo", memberNo);
		return orderInfoMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public Map<String, String> saveUserAndAddress(String realName, String mobile, String consignee,
			String consigneeMobile, String zone, String detail) {
		Map<String, String> paramResult = new HashMap<>();
		//1、注册/登录
		UserMemberInfo member = new UserMemberInfo();
		member.setRealName(realName);
		member.setDeleted(false);
		member.setMobile(mobile);
		member = userMemberInfoService.addUserInfo(member);
		if (member == null) {
			//注册失败
			throw new OohdearException(30008, "用户注册失败");
		}
		String memberNo = member.getMemberNo();
		//2、保存地址信息
		String addressId = addressService.addAddress(consignee, consigneeMobile, zone, detail, memberNo);
		paramResult.put("memberNo", member.getMemberNo());
		paramResult.put("nickName", member.getNickName());
		paramResult.put("mobile", member.getMobile());
		paramResult.put("addressId", addressId);
		return paramResult;
	}

	@Override
	@Transactional
	public Map<String, Object> settle(String memberNo, int addressId, int payType, String comment, String cover,
			String styleCode, String materialCode, String sizeCode, String style, String material, String size,
			String lettering, String coupons) {
		//1、生成订单信息
		String orderNo = "n" + generatorService.generatorOrderNo(1);
		UserMemberAddress address = new UserMemberAddress();
		address.setId((long) addressId);
		address = addressMapper.selectOne(address);
		if (address == null) {
			//地址信息为空
			throw new RuntimeException("地址信息不能为空");
		}
		BigDecimal money = getOrderMoney(styleCode, materialCode, sizeCode);
		OrderInfo record = new OrderInfo();
		if (StringUtils.hasText(coupons)) {
			List<Coupon> couponList = queryCoupons(coupons);
			if (couponList.size() > 0) {
				record.setCreditAmount(couponList.get(0).getCouponPrice());
				record.setPayAmount(money.subtract(couponList.get(0).getCouponPrice()));
				record.setCouponId(couponList.get(0).getId());
			} else {
				record.setCreditAmount(new BigDecimal(0.00));
				record.setPayAmount(money);
			}
		} else {
			record.setCreditAmount(new BigDecimal(0.00));
			record.setPayAmount(money);
		}
		record.setBuyerNo(memberNo);
		record.setCreated(new Date());
		record.setGoodsMaterial(material);
		record.setGoodsSize(size);
		record.setGoodsStyle(style);
		record.setCover(cover);//画作
		record.setLettering(lettering); //刻字内容
		record.setOrderNo(orderNo);
		record.setOrderPrice(money);
		record.setOrderStatus(OrderStatus.WAIT_PAY.getCode()); //待付款
		record.setPayType(payType); //支付类别
		record.setShipAddress(address.getAddress());
		record.setComment(comment);
		orderInfoMapper.insert(record);
		//2、生成工作流表
		orderWorkflowMapper.insert(new OrderWorkflow(orderNo, OrderStatus.WAIT_PAY.getCode(), memberNo,
				OrderStatus.WAIT_PAY.getName()));
		//3、生成快递表信息
		Boolean status = orderExpressMapper.insert(new OrderExpress(orderNo, new Date(), address.getZone(), address
				.getAddress(), address.getConsignee(), address.getConsigneeMobile())) == 1;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("orderNo", orderNo);
		//4、生成订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setCommentType(2);
		orderLog.setComment(comment);
		orderLog.setCreated(new Date());
		orderLog.setNickname(memberNo);//取当前操作用户名称 
		orderLog.setOperatorType(1);
		orderLog.setOrderNo(orderNo);
		orderLog.setRoleId(0);//取当前角色
		orderLogMapper.insert(orderLog);
		return params;

	}

	public void pay(String orderNo, String payAmount, String tradeNo, String buyerLogonId) {
		OrderInfo order = new OrderInfo();
		order.setOrderNo(orderNo);
		order = orderInfoMapper.selectOne(order);
		//1、判断订单状态是否符合支付条件
		if (!OrderStatus.WAIT_PAY.getCode().equals(order.getOrderStatus())) {
			throw new OohdearException(30006, "订单当前状态不能进行支付操作");
		}
		//2、修改订单状态，
		OrderInfo record = new OrderInfo();
		record.setId(order.getId());
		record.setOrderStatus(OrderStatus.WAIT_CONFIRM.getCode());
		orderInfoMapper.updateByPrimaryKeySelective(record);
		//3、保存工作流
		OrderWorkflow orderWorkflow = new OrderWorkflow();
		//存储画作url，为查询原画作做准备
		orderWorkflow.setContent(order.getCover());
		orderWorkflow.setCreated(new Date());
		orderWorkflow.setOperator(order.getBuyerNo());
		orderWorkflow.setOrderNo(orderNo);
		orderWorkflow.setOrderStatus(record.getOrderStatus());
		orderWorkflowMapper.insert(orderWorkflow);
		//4、 保存支付信息
		OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
		orderPaymentInfo.setOrderNo(orderNo);
		orderPaymentInfo.setPayer(buyerLogonId == null ? order.getBuyerNo() : buyerLogonId);
		orderPaymentInfo.setPayment(order.getPayType());
		orderPaymentInfo.setPaymentAmount(new BigDecimal(payAmount));
		orderPaymentInfo.setPaymentResult(1);
		orderPaymentInfo.setPaymentTime(new Date());
		orderPaymentInfo.setSerialNumbe(tradeNo);
		orderPaymentInfoMapper.insert(orderPaymentInfo);
	}

	@Override
	public Boolean refund(String orderNo, String memberNo, String reason) {
		OrderInfo order = new OrderInfo();
		order.setOrderNo(orderNo);
		order = orderInfoMapper.selectOne(order);
		checkOrderByMemberNo(order, memberNo);
		//1、判断订单状态是否符合支付条件
		if (!OrderStatus.WAIT_CONFIRM.getCode().equals(order.getOrderStatus())
				&& !OrderStatus.REFUSE.getCode().equals(order.getOrderStatus())) {
			throw new OohdearException(30008, "订单当前状态不能进行退款操作");
		}
		//2、修改订单状态，
		OrderInfo record = new OrderInfo();
		record.setId(order.getId());
		record.setOrderStatus(OrderStatus.WAIT_REFUND.getCode());
		orderInfoMapper.updateByPrimaryKeySelective(record);
		//3、保存工作流
		OrderWorkflow orderWorkflow = new OrderWorkflow();
		orderWorkflow.setContent(reason);
		orderWorkflow.setCreated(new Date());
		orderWorkflow.setOperator(order.getBuyerNo());
		orderWorkflow.setOrderNo(orderNo);
		orderWorkflow.setOrderStatus(record.getOrderStatus());
		return orderWorkflowMapper.insert(orderWorkflow) == 1;
	}

	@Override
	public Boolean confirm(String orderNo, String memberNo) {
		OrderInfo order = new OrderInfo();
		order.setOrderNo(orderNo);
		order = orderInfoMapper.selectOne(order);
		checkOrderByMemberNo(order, memberNo);
		//1、判断订单状态是否符合收货条件
		if (!OrderStatus.SHIPPED.getCode().equals(order.getOrderStatus())) {
			throw new OohdearException(30009, "订单当前状态不能进行确认收货操作");
		}
		//2、修改订单状态，
		OrderInfo record = new OrderInfo();
		record.setId(order.getId());
		record.setOrderStatus(OrderStatus.DONE.getCode());
		orderInfoMapper.updateByPrimaryKeySelective(record);
		//3、保存工作流
		OrderWorkflow orderWorkflow = new OrderWorkflow();
		orderWorkflow.setContent("订单号[" + orderNo + "]已进行确认收货操作");
		orderWorkflow.setCreated(new Date());
		orderWorkflow.setOperator(order.getBuyerNo());
		orderWorkflow.setOrderNo(orderNo);
		orderWorkflow.setOrderStatus(record.getOrderStatus());
		return orderWorkflowMapper.insert(orderWorkflow) == 1;
	}

	@Override
	public JSONObject detail(String orderNo, String memberNo) {
		OrderInfo order = new OrderInfo();
		order.setOrderNo(orderNo);
		order = orderInfoMapper.selectOne(order);
		checkOrderByMemberNo(order, memberNo);
		JSONObject body = new JSONObject();
		//抽取order数据
		JSONObject orderJson = new JSONObject();
		orderJson.put("no", order.getOrderNo());
		orderJson.put("cover", order.getCover());
		orderJson.put("style", order.getGoodsStyle());
		orderJson.put("material", order.getGoodsMaterial());
		orderJson.put("size", order.getGoodsSize());
		orderJson.put("payAmount", order.getPayAmount());
		orderJson.put("orderPrice", order.getOrderPrice());
		orderJson.put("creditAmount", order.getCreditAmount());
		orderJson.put("comment", order.getComment());
		orderJson.put("createTime", DateUtil.getDateStr(order.getCreated(), "yyyy-MM-dd"));
		orderJson.put("statusCode", order.getOrderStatus());
		orderJson.put("statusName", OrderStatus.forOrderCode(order.getOrderStatus()));
		if (OrderStatus.DESIGNING.getCode().equals(order.getOrderStatus())) {
			//不修改statusCode是因为前端要根据statusCode区分按钮。例如：当前状态是设计中，但是返回20待确认的话，前端按钮会显示“申请退款”按钮，实际上不应该有“申请退款”按钮
			orderJson.put("statusName", OrderStatus.WAIT_CONFIRM.getFinishedName());
		} else if (OrderStatus.MAKING.getCode().equals(order.getOrderStatus())) {
			//不修改statusCode是因为前端要根据statusCode区分按钮。例如：当前状态是设计中，但是返回20待确认的话，前端按钮会显示“申请退款”按钮，实际上不应该有“申请退款”按钮
			orderJson.put("statusName", OrderStatus.WAIT_MAKE.getName());
		}
		orderJson.put("lettering", order.getLettering());
		body.put("order", orderJson);
		//工作流
		Example workflow = new Example(OrderWorkflow.class);
		workflow.setOrderByClause("created ASC");
		workflow.createCriteria().andEqualTo("orderNo", orderNo);
		List<OrderWorkflow> workflows = orderWorkflowMapper.selectByExample(workflow);
		//状态轴
		List<String[]> statusAxis = OrderStatusAxis.get(true, workflows, order.getOrderStatus());
		body.put("statusAxis", statusAxis);
		//查询订单收货地址信息
		OrderExpress orderExpress = new OrderExpress();
		orderExpress.setOrderNo(orderNo);
		orderExpress = orderExpressMapper.selectOne(orderExpress);
		if (orderExpress == null) {
			orderExpress = new OrderExpress();
		}
		JSONObject address = new JSONObject();
		address.put("consignee", orderExpress.getConsignee() == null ? "" : orderExpress.getConsignee());
		address.put("mobile", orderExpress.getConsigneeMobile() == null ? "" : orderExpress.getConsigneeMobile());
		address.put("detail", orderExpress.getShippingAddress() == null ? "" : orderExpress.getShippingAddress());
		address.put("zone", orderExpress.getZone() == null ? "" : orderExpress.getZone());
		body.put("address", address);
		//查询快递信息
		JSONArray infos = new JSONArray();
		if (StringUtils.hasText(orderExpress.getExpressNo())) {
			TpExpressInfo tpExpressInfo = new TpExpressInfo();
			tpExpressInfo.setExpressNo(orderExpress.getExpressNo());
			List<TpExpressInfo> tpExpressInfoList = tpExpressInfoMapper.select(tpExpressInfo);
			tpExpressInfoList.forEach(expressInfo -> {
				JSONObject info = new JSONObject();
				info.put("content", expressInfo.getContent());
				info.put("createTime", expressInfo.getfTime());
				infos.add(info);
			});
		}
		SysExpressDict sysExpressDict = new SysExpressDict();
		if (StringUtils.hasText(orderExpress.getSysCompanyCode())) {
			sysExpressDict.setCompanyCode(orderExpress.getSysCompanyCode());
			sysExpressDict.setEnable(true);
			sysExpressDict = sysExpressDictMapper.selectOne(sysExpressDict);
			if (sysExpressDict == null) {
				sysExpressDict = new SysExpressDict();
			}
		}
		JSONObject express = new JSONObject();
		express.put("company", sysExpressDict.getCompanyName() == null ? "" : sysExpressDict.getCompanyName());
		express.put("no", orderExpress.getExpressNo() == null ? "" : orderExpress.getExpressNo());
		express.put("infos", infos);
		body.put("express", express);
		return body;
	}

	/**
	 * 检验是否是该会员的订单
	 * @param orderInfo 订单
	 * @param memberNo 会员编号
	 */
	private void checkOrderByMemberNo(OrderInfo orderInfo, String memberNo) {
		if (memberNo != null && orderInfo != null) {
			if (!memberNo.equals(orderInfo.getBuyerNo())) {
				throw new OohdearException(30005, "订单号不存在", "订单号不存在");
			}
		}
	}

	@Override
	public BigDecimal getOrderMoney(String style, String material, String size) {
		if (StringUtils.hasText(style) && StringUtils.hasText(material) && StringUtils.hasText(size)) {
			logger.error("去结算-调用失败。原因：商品属性参数不完整，style=" + style + ",material=" + material + ",size=" + size);
		}
		OrderMoney orderMoney = new OrderMoney();
		orderMoney.setStyle(style);
		orderMoney.setMaterial(material);
		orderMoney.setSize(size);
		List<OrderMoney> orderList = orderMoneyMapper.select(orderMoney);
		if (orderList != null && orderList.size() > 0) {
			orderMoney = orderList.get(0);
		} else {
			throw new RuntimeException("获取订单金额失败……");
		}
		return orderMoney.getMoney();
	}

	@Override
	public Map<String, BigDecimal> getFuzzyOrderMoney(String style, String material, String size) {
		OrderMoney order = new OrderMoney();
		if (StringUtils.hasText(style)) {
			order.setStyle(style.trim());
		}
		if (StringUtils.hasText(material)) {
			order.setMaterial(material.trim());
		}
		if (StringUtils.hasText(size)) {
			order.setSize(size.trim());
		}
		return orderMoneyMapper.getFuzzyOrderMoney(order);
	}

	@Override
	public List<Coupon> queryCoupons(String coupons) {
		Map<String, Object> params = new HashMap<>();
		params.put("couponCode", coupons);
		params.put("date", new Date());
		return couponMapper.queryCoupons(params);
	}

	@Override
	public Boolean cancel(String orderNo, String memberNo) {
		//1、检验订单号
		OrderInfo order = queryByOrderNo(orderNo);
		if (order == null) {
			logger.error("取消订单-订单信息有误orderNo={}", orderNo);
		}
		//2、校验订单当前状态
		if (!OrderStatus.WAIT_PAY.getCode().equals(order.getOrderStatus())) {
			throw new OohdearException(30006, "订单当前状态不能进行取消订单操作");
		}
		if (!memberNo.equals(order.getBuyerNo())) {
			throw new OohdearException(30007, "订单当前操作用户不能进行取消订单操作");
		}
		//3、修改订单状态
		OrderInfo record = new OrderInfo();
		record.setId(order.getId());
		record.setOrderStatus(OrderStatus.CLOSED.getCode());
		orderInfoMapper.updateByPrimaryKeySelective(record);
		//4、记录订单工作流表
		orderWorkflowMapper.insert(new OrderWorkflow(orderNo, OrderStatus.CLOSED.getCode(), order.getBuyerNo(),
				OrderStatus.CLOSED.getName()));
		return true;
	}
}
