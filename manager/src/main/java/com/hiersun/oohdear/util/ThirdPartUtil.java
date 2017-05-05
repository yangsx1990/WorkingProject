package com.hiersun.oohdear.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

/**
 * 第三方调用工具类
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@Component
public class ThirdPartUtil {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartUtil.class);

	private static final RestTemplate restTemplate = new RestTemplate();

	private static final String APP_NAME = "oohdear h5";

	/** 支付宝开发者应用私钥 */
	private static final String ALIPAY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZQo/zLnTjxSa8znes7J67cqxMdoAXfZ1kTHDa8PKZBDDTkhpnVIPqi4tPhMuj6mUUWPSafW57roNw+byfhMHPxdARxA7A98broQFagM4x9hGU/fsxvgTxKXm3ji5F5szFxGeByExZ9TWpYwCOMAqVCBGUB3KeXKTcohs9ii4qSdfmaBqwkVBCoQkBncGzWijat/GnzOl6HpM3uhkUTEEdyQny0cUiPvbpWeaEKpRlI/i27vOvyR93fhp7IoJkPeD/Jw39xHpdx2JDZ6ZvdvdHaSNgwAXv7h9q4D3Jk1bxRm+vZwHuQRTOrzsh9TrD6UY7PN1kSVPPJz6ave/4sqjlAgMBAAECggEAIHfVfzNMWCSgPEeRWvUV+6gjkF9aa5+VBHadQgThoEEfBvNg3JsvBzVg4gvECInTuCfQShpgmZszBXAi4biCRr4lgl4CsibL4YdOfhnjlPoIZ4QW/0a+TUj4HEbmzrBCmXMuanRAmJ42LBhpObY7x67Z6n5Zbes6kfNVygfIF/UQr2kMBwQxs0g7XdhIgbWi1ZEIG2m+CS0SGTFQ4EDB0GXcxk/qR4msCbeHRgg2YerSKDE3arIMzsyRAmQxiehzsKWINv7souK5FSIn4KKmVENRXFRJU/Iz+VBTz9ekbQI/WCsNiaVOqxAyuN6pzXaJHFSJfmC1mKokVGdNkOuyUQKBgQDW6j2vFXeSvivQLtAGwNki+Ud9ghDhhomWbrmjj9Dj3p6VxZSdvmxbNRAxRxkqNABGWBBOmH6XV+MEQdPuXMtVtFYmB835JKuh2d1w/a3MGyuC+qvRkbq8z5WNQ8lt/PvSOLdNrQnYkO1PgP23TuY9S3SoTwOLM1DgUq2EU/0FswKBgQC2jvpX1rCNzuns91F8ODxZFvyz77ZmJ74U70p4So4PRHtp/tW9amM/YCSEiDuYLNbq4mJzTwWnmcpwvTsI4+9hTlVtE7Od4Ud9APiGq30S2qNzalOHVj5zIutz0emxZ9CLt9c0bR7kBlC/ubj+n7WLuSx6pzDpZ2cz5ljPgIv7BwKBgE+BpsI/6dWCvwisWM82xt2WZOk8lewo7nuViN3GlZvHPtnfl2RoDwQK9GuWhuvDAxsC9z1sUuDAZb6sb6/t6ldReNFm778s3sXoqHBlmMc6gfVZsPi9tdyl2wjAvCMRhUO3MbDtxAe3TeOsr7AB41FnuceOO5xippfmXkTnIWLbAoGBAKTW5WV7DWB+fOrjRkiL3rHy96rhwCjtqcztZKjJaQyQIX/MAuNNtN/8x/yDpov5sPXWBF9p8wkq7Ihv1WZpqjSKrsgXChB84jgVMFMpdX4YvY3xbAq/7RO5bDDgUctGmiAOl9tWzJXIvhDgehIv05WeVnNJk/iszZsnWvnCcKLxAoGBAIlvyEjpHURVXTppPA4E9B8LENK0adlPSPfD6pWhGpGt/SBwDT1q8Gri7oVvWY90Jsp4q8QHcvAGyDYl164QVSB4+/bV9iWVyS4HTxYY0DWewKKJFlqr0JVyRLY+mOURnj7kPjIwfJfQgcCd8tTF3HmXkLtVpca2oGp9qpgbMPH7";
	/** 支付宝公钥 */
	private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk531E3AEL3uvTv8rRDS/F1ijZPX1yEvNnfEQ26ewazx4ximM2Pxq2eXgDT0+kZLnNVb+GZkVvu4Eq6UqeEYoskgHrjIBIXPFDyMMrWjNyMEzLu3D4gK/IZkHIxxG12gufAx/j2LAkEDuThJfdu82+9pGN29TUSKIrdPBcmU3P345ccK+F542JB2qvsvvMH2YmHpdmsT8Iglfvn74Z2qTbW3G9ws1VtWnCBkF9rOsRjnGgFgx6yWM/XbTvzWFe4f1/f0q43gFhtbfF9QyQjcVpPtRzzD7yd4H6KwV+fGrVSHtN58hQ/FltV0teo3kKrSwwvSYq0P4Uhxjng1NjCVTsQIDAQAB";
	/** 支付宝APPID */
	private static final String ALIPAY_APP_ID = "2016080300160880";
	/** 支付宝网关 */
	private static final String ALIPAY_URL = "https://openapi.alipaydev.com/gateway.do";
	private static final String JSON = "JSON";
	private static final String CHARSET = "UTF-8";
	private static final String SIGN_TYPE = "RSA2";
	/** 支付宝客户端 */
	private static final DefaultAlipayClient ALIPAY_CLIENT = new DefaultAlipayClient(ALIPAY_URL, ALIPAY_APP_ID,
			ALIPAY_PRIVATE_KEY, JSON, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

	public static final String ALI_REFUND_FAILED_REASON = "refund.failed.reason";

	/**
	 * 快递订阅
	 * @param url 订阅地址
	 * @param sysCompanyCode 官方快递编码
	 * @param expressNo 快递单号
	 * @return
	 */
	public static boolean expressSubscribe(String url, String sysCompanyCode, String expressNo) {
		logger.info("[订阅物流信息] url={}, sysCompanyCode={}, expressNo={}", url, sysCompanyCode, expressNo);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		JSONObject params = new JSONObject();//请求参数
		params.put("contentType", "json");
		params.put("companyCode", sysCompanyCode);
		params.put("expressNo", expressNo);
		params.put("appName", APP_NAME);
		params.put("version", "1.0");
		params.put("businessCase", "订阅快递信息");
		HttpEntity<JSONObject> request = new HttpEntity<>(params, headers);
		//发送HTTP请求
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if (response != null) {
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				logger.info("[订阅物流信息成功] expressNo={}", expressNo);
				return true;
			}
		}
		logger.warn("[订阅物流信息失败] expressNo={}", expressNo);
		return false;
	}

	/**
	 * 支付宝退款
	 * @param orderNo 订单号
	 * @param refundAmount 退款金额
	 * @param trade_no 三方流水号
	 * @return
	 * @throws AlipayApiException
	 */
	public static boolean refund(String orderNo, String refundAmount, String trade_no) throws AlipayApiException {
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		JSONObject bizContent = new JSONObject();
		bizContent.put("out_trade_no", orderNo);
		if (StringUtils.hasText(trade_no)) {
			bizContent.put("trade_no", trade_no);
		}
		bizContent.put("refund_amount", refundAmount);
		bizContent.put("refund_reason", "正常退款");
		request.setBizContent(bizContent.toJSONString());
		AlipayTradeRefundResponse response = ALIPAY_CLIENT.execute(request);
		if (response.isSuccess()) {
			logger.info("退款成功, response={}", JSONObject.toJSONString(response));
		} else {
			logger.error("退款失败, response{}", JSONObject.toJSONString(response));
			AlipayTradeRefundReponseEnum reponseEnum = AlipayTradeRefundReponseEnum.getEntity(response.getSubCode()
					.toUpperCase());
			if (reponseEnum == null) {
				throw new AlipayApiException(response.getSubCode(), response.getSubMsg());
			} else {
				throw new AlipayApiException(ALI_REFUND_FAILED_REASON, reponseEnum.getSubMsg() + "，"
						+ reponseEnum.getSubSolution());
			}
		}
		return response.isSuccess();
	}
	
	/**
	 * 发送短信
	 * @param url 短信服务地址
	 * @param businessCase 业务场景
	 * @param mobile 手机号
	 * @param captcha 发送的验证码
	 * @return
	 */
	public static boolean sendSms(String url, String businessCase, String mobile) {
		logger.info("[发送短信验证码] url={}, businessCase={}, mobile={}, captcha={}", url, businessCase, mobile);
		String content="";// = "您好，您的验证码是：" + captcha;
		if ("make".equals(businessCase) || "shipped".equals(businessCase)) {
			
		} else {
			throw new RuntimeException("发送短信出错，业务场景异常");
		}
		//发送短信
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		JSONObject params = new JSONObject();//请求参数
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("appName", APP_NAME);
		params.put("businessCase", businessCase);
		params.put("terminal", "h5");
		HttpEntity<String> request = new HttpEntity<>(params.toJSONString(), headers);
		//发送HTTP请求
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		if (response != null) {
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return true;
			}
		}
		logger.warn("[发送短信验证码失败] url={}, businessCase={}, mobile={}, captcha={}", url, businessCase, mobile);
		return false;
	}
}
