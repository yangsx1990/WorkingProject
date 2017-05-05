package com.hiersun.oohdear.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.hiersun.oohdear.order.service.OrderService;
import com.hiersun.oohdear.util.ThirdPartUtil;
import com.hiersun.oohdear.util.alidirect.AlipayNotify;

/**
 * 支付宝回调控制器
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@Controller
public class AliCallBackController {
	private static final Logger logger = LoggerFactory.getLogger(AliCallBackController.class);

	@Autowired
	private OrderService orderService;
	@Value("${oohdear.return.url}")
	private String returnUrl;
	@Value("${alipay.h5app.pay}")
	private boolean h5appPay;

	/**
	 * 支付宝回调return接口
	 */
	@RequestMapping(value = "alipay/callback/return", method = RequestMethod.GET)
	public String aliReturn(HttpServletRequest request) {
		logger.info("支付宝回调return接口");
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String orderNo = request.getParameter("out_trade_no");
		//支付宝交易号
		//String trade_no = request.getParameter("trade_no");
		//交易状态
		//String trade_status = request.getParameter("trade_status");
		//String total_amount = request.getParameter("total_amount");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		try {
			boolean verify_result = false;
			if (h5appPay) {
				verify_result = ThirdPartUtil.alipaySign(params);
			} else {
				verify_result = AlipayNotify.verify(params);
			}
			if (verify_result) {//验证成功
				logger.info("验签成功");
			}
		} catch (AlipayApiException e) {
			logger.warn("验签失败", e);
		}
		return "redirect:" + returnUrl + orderNo;
	}

	/**
	 * 支付宝回调通知notify接口
	 */
	@RequestMapping(value = "alipay/callback/notify", method = RequestMethod.POST)
	public void aliNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付宝回调notify接口");
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String orderNo = request.getParameter("out_trade_no");
		//支付宝交易号
		String trade_no = request.getParameter("trade_no");
		//交易状态
		String trade_status = request.getParameter("trade_status");
		String total_amount = request.getParameter("total_fee");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		try {
			boolean verify_result = false;
			if (h5appPay) {
				logger.info("进行手机网站支付");
				total_amount = request.getParameter("total_amount");
				String seller_id = request.getParameter("seller_id");
				String app_id = request.getParameter("app_id");
				verify_result = ThirdPartUtil.alipaySign(params, seller_id, app_id);
			} else {
				logger.info("进行即时到账支付");
				verify_result = AlipayNotify.verify(params);
			}
			ServletOutputStream out = response.getOutputStream();
			if (verify_result) {//验证成功
				logger.info("验签成功,app_id和seller_id正确,trade_status={}", trade_status);
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					String buyer_logon_id = request.getParameter("buyer_logon_id");
					orderService.pay(orderNo, total_amount, trade_no, buyer_logon_id);
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序

					//注意：
					//如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					//如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
					out.println("success"); //请不要修改或删除
				}
			} else {
				out.println("fail");
			}
		} catch (IOException e) {
			logger.error("IO异常", e);
		} catch (AlipayApiException e) {
			logger.error("支付宝验签失败", e);
		}
	}

}
