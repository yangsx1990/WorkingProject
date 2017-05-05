package com.hiersun.oohdear.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.hiersun.oohdear.config.redis.RedisUtil;
import com.hiersun.oohdear.core.CacheKey;
import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.core.OrderStatus;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.order.entity.Coupon;
import com.hiersun.oohdear.order.entity.OrderInfo;
import com.hiersun.oohdear.order.service.OrderService;
import com.hiersun.oohdear.user.service.AddressService;
import com.hiersun.oohdear.user.service.FileService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.util.DateUtil;
import com.hiersun.oohdear.util.ThirdPartUtil;

/**
 * 订单控制器
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@RestController
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserMemberInfoService userService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private FileService fileService;
	@Autowired
	private RedisUtil redisUtil;
	@Value("${alipay.return.url}")
	private String returnUrl;
	@Value("${alipay.notify.url}")
	private String notifyUrl;
	@Value("${oohdear.test}")
	private boolean test;
	@Value("${alipay.h5app.pay}")
	private boolean h5appPay;
	@Value("${user.switch.check}")
	private String switchCheck;

	/**
	 * 获取定制价格
	 * @param itemCode 类别Code
	 * @return
	 */
	@RequestMapping(value = "/customization/price")
	public ResponseEntity<ResponseMessage> getPrice(String style, String material, String size) {
		ResponseMessage responseMessage = new ResponseMessage();
		if ("null".equals(size))
			size = "";
		if ("null".equals(style))
			style = "";
		if ("null".equals(material))
			material = "";
		Map<String, BigDecimal> money = orderService.getFuzzyOrderMoney(style, material, size);
		if (money == null) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("定制价格异常");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		String currencySymbol = "¥";
		BigDecimal min = money.get("min").setScale(0);
		BigDecimal max = money.get("max").setScale(0);
		if (min.equals(max)) {
			responseMessage.getBody().put("money", currencySymbol + min);
		} else {
			responseMessage.getBody().put("money", currencySymbol + min + "~" + currencySymbol + max);
		}
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 提交定制
	 * @param itemCode
	 * @return 
	 * 备注：文件名称应为随机数，不可重复
	 */
	@RequestMapping(value = "/customization")
	public ResponseEntity<ResponseMessage> commit(MultipartFile file) {
		ResponseMessage responseMessage = new ResponseMessage();
		String picUrl = fileService.upload(file);
		responseMessage.getBody().put("picUrl", picUrl);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 去结算-验签
	 * @param itemCode
	 * @return
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> order(@RequestParam(required = true) String memberNo, int addressId,
			int payType, String comment, String cover, String styleCode, String materialCode, String sizeCode,
			String style, String material, String size, String lettering,String coupon) {
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(memberNo)) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("用户信息异常");
		}
		if (!StringUtils.hasText(cover)) {
			responseMessage.getHead().setCode(30004);
			responseMessage.getHead().setMessage("画作信息异常");
		}
		if (!StringUtils.hasText(styleCode) || !StringUtils.hasText(materialCode) || !StringUtils.hasText(sizeCode)) {
			responseMessage.getHead().setCode(30005);
			responseMessage.getHead().setMessage("定制信息异常");
		}
		Map<String, Object> result = orderService.settle(memberNo, addressId, payType, comment, cover, styleCode,
				materialCode, sizeCode, style, material, size, lettering,coupon);
		responseMessage.getBody().put("status", result.get("status"));
		responseMessage.getBody().put("orderNo", result.get("orderNo"));
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 定制流程-保存收货地址（未登录）
	 * @param nickname 订制人
	 * @param mobile 定制人手机号
	 * @param captcha 验证码
	 * @param consignee 收货人
	 * @param consigneeMobile 收货人手机号
	 * @param detail 详细地址
	 * @param itemCode 类别code
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> save(String nickname, @RequestParam(required = true) String mobile,
			String captcha, String consignee, String consigneeMobile, String zone, String detail) {
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(nickname)) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("定制人名称必填");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		nickname = nickname.trim();
		if (nickname.length() > 20) {
			responseMessage.getHead().setCode(30004);
			responseMessage.getHead().setMessage("定制人名称最多20个字符");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(mobile)) {
			responseMessage.getHead().setCode(30005);
			responseMessage.getHead().setMessage("定制人手机号必填");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		mobile = mobile.trim();
		if (!mobile.matches("1[0-9]{10}")) {
			responseMessage.getHead().setCode(30006);
			responseMessage.getHead().setMessage("定制人手机号为11位");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		captcha = captcha == null ? "" : captcha.trim();
		if (!captcha.matches("[0-9]{6}")) {
			responseMessage.getHead().setCode(30007);
			responseMessage.getHead().setMessage("验证码错误");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(consignee)) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("收货人名称必填");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		consignee = consignee.trim();
		if (consignee.length() > 20) {
			responseMessage.getHead().setCode(30004);
			responseMessage.getHead().setMessage("收货人名称最多20个字符");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(consigneeMobile)) {
			responseMessage.getHead().setCode(30005);
			responseMessage.getHead().setMessage("收货人手机号必填");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		consigneeMobile = consigneeMobile.trim();
		if (!consigneeMobile.matches("1[0-9]{10}")) {
			responseMessage.getHead().setCode(30006);
			responseMessage.getHead().setMessage("收货人手机号为11位数字");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		zone = zone == null ? "" : zone.trim();
		if (zone.length() == 0 || zone.length() > 50) {
			responseMessage.getHead().setCode(30007);
			responseMessage.getHead().setMessage("所在地区必填或不能超过50个字符");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		detail = detail == null ? "" : detail.trim();
		if (detail.length() == 0 || detail.length() > 50) {
			responseMessage.getHead().setCode(30008);
			responseMessage.getHead().setMessage("详细地址必填或不能超过50个字符");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if("true".equals(switchCheck)){
			//验证验证码
			String validCode = (String) redisUtil.get(CacheKey.MEMBER_CAPTCHA + mobile);
			if (!captcha.equals(validCode)) {
				responseMessage.getHead().setCode(30007);
				responseMessage.getHead().setMessage("验证码错误");
				return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
			}
		}		
		Map<String, String> paramResult = orderService.saveUserAndAddress(nickname, mobile, consignee, consigneeMobile,
				zone, detail);
		String token=RandomStringUtils.randomAlphanumeric(16);
		redisUtil.set(CacheKey.MEMBER_TOKEN + paramResult.get("memberNo"), token);
		responseMessage.getBody().put("nickName", paramResult.get("nickName"));
		responseMessage.getBody().put("mobile", paramResult.get("mobile"));
		responseMessage.getBody().put("memberNo", paramResult.get("memberNo"));
		responseMessage.getBody().put("addressId", paramResult.get("addressId"));
		responseMessage.getBody().put("token", token);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 获取订单列表-验签
	 * @param memberNo 会员编号
	 * @return
	 */
	@RequestMapping(value = "/customization/orders", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> orders(@RequestParam String memberNo) {//, Integer pageNo, Integer pageSize) {
		ResponseMessage responseMessage = new ResponseMessage();
		List<OrderInfo> orderList = orderService.orderListByMemberNo(memberNo);
		List<JSONObject> orders = new ArrayList<JSONObject>();
		orderList.forEach(order -> {
			JSONObject json = new JSONObject();
			json.put("orderNo", order.getOrderNo());
			json.put("cover", order.getCover());
			json.put("itemCode", "");
			json.put("style", order.getGoodsStyle());
			json.put("material", order.getGoodsMaterial());
			json.put("size", order.getGoodsSize());
			json.put("price",order.getPayAmount());
			json.put("createTime", DateUtil.getDateStr(order.getCreated(), "yyyy-MM-dd  HH:mm"));
			json.put("status", order.getOrderStatus());
			json.put("statusName", OrderStatus.forOrderCode(order.getOrderStatus()));
			if(OrderStatus.DESIGNING.getCode().equals(order.getOrderStatus())){
				//不修改status是因为前端要根据status区分按钮。例如：当前状态是设计中，但是返回20待确认的话，前端按钮会显示“申请退款”按钮，实际上不应该有“申请退款”按钮
				json.put("statusName", OrderStatus.WAIT_CONFIRM.getFinishedName());
			}else if(OrderStatus.MAKING.getCode().equals(order.getOrderStatus())){
				//不修改status是因为前端要根据status区分按钮。例如：当前状态是设计中，但是返回20待确认的话，前端按钮会显示“申请退款”按钮，实际上不应该有“申请退款”按钮
				json.put("statusName", OrderStatus.WAIT_MAKE.getName());
			}
			orders.add(json);
		});
		responseMessage.getBody().put("orders", orders);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 立即付款
	 * @param orderNo 订单号
	 * @param memberNo 会员编号
	 * @return
	 */
	@RequestMapping(value = "/customization/order/pay",method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> pay(@RequestHeader(required = false) String memberNo, String orderNo) {
		logger.info("h5调用支付接口");
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(orderNo)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("订单号不能为空");
		} else {
			//orderNo = "1234583";
			OrderInfo order = orderService.queryByOrderNo(orderNo);
			if (order != null && order.getPayType() == 1) {//支付宝
				try {
					String orderPrice = order.getPayAmount().toString();
					if (test) {
						orderPrice = "0.01";
					}
					//2、根据支付类型调用第三方支付接口进行支付s
					if (h5appPay) {
						String form = ThirdPartUtil.alipayForH5OrApp(orderNo, orderPrice, returnUrl, notifyUrl);
						responseMessage.getBody().put("form", form);
					} else {
						String form = ThirdPartUtil.alipayForWeb(orderNo, orderPrice, returnUrl, notifyUrl);
						responseMessage.getBody().put("form", form);
					}
				} catch (IOException | AlipayApiException e) {
					logger.warn("支付宝下单异常", e);
					throw new OohdearException(99999, "服务异常");
				}
			}
		}
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 申请退款
	 * @param orderNo 订单号
	 * @param memberNo 会员编号
	 * @return
	 */
	@RequestMapping(value = "/customization/order/refund",method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> refund(String memberNo, String orderNo,String reason) {
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(orderNo)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("订单号不能为空");
		}
		Boolean status = orderService.refund(orderNo, memberNo,reason);
		responseMessage.getBody().put("status", status);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 确认收货
	 * @param orderNo 订单号
	 * @param memberNo 会员编号
	 * @return
	 */
	@RequestMapping(value = "/customization/order/confirm", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> confirm(String memberNo, String orderNo) {
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(orderNo)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("订单号不能为空");
		}
		Boolean status = orderService.confirm(orderNo, memberNo);
		responseMessage.getBody().put("status", status);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 定制详情
	 * @param orderNo
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value = "/customization/order/detail", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> detail(String orderNo, String memberNo) {//@RequestHeader
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(orderNo)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("订单号不能为空");
		}
		JSONObject body = orderService.detail(orderNo, memberNo);
		responseMessage.setBody(body);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}
	/**
	 * 异步验证优惠券是否可用优惠券编号-验签
	 * @param coupons 优惠券码
	 * @param memberNo 会员编号
	 * @return
	 */
	@RequestMapping(value = "/order/coupons", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> querycoupons(String coupon) {
		ResponseMessage responseMessage = new ResponseMessage();
		Boolean status=false;
		if (!StringUtils.hasText(coupon)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("优惠码不能为空");
		}
		List<Coupon> coupons=orderService.queryCoupons(coupon);
		if(coupons.size()>0){
			status=true;
			responseMessage.getBody().put("coupons", coupons);
		}
		responseMessage.getBody().put("status", status);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}
	/**
	 * 取消订单
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> cancel(String orderNo,String memberNo) {
		ResponseMessage responseMessage = new ResponseMessage();
		Boolean status=false;
		if (!StringUtils.hasText(orderNo)) {
			responseMessage.getHead().setCode(30001);
			responseMessage.getHead().setMessage("订单编号不能为空");
		}
		status=orderService.cancel(orderNo,memberNo);
		responseMessage.getBody().put("status", status);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}
	
}
