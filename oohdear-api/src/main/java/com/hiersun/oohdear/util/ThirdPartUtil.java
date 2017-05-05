package com.hiersun.oohdear.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hiersun.oohdear.core.OohdearException;
import com.hiersun.oohdear.util.alidirect.AlipayConfig;
import com.hiersun.oohdear.util.alidirect.AlipaySubmit;

/**
 * 第三方调用工具类
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@Component
public class ThirdPartUtil {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartUtil.class);

	private static final RestTemplate restTemplate = new RestTemplate();

	private static final String APP_NAME = "ooh dear h5";

	/** 支付宝开发者应用私钥 */
	private static String ALIPAY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCZQo/zLnTjxSa8znes7J67cqxMdoAXfZ1kTHDa8PKZBDDTkhpnVIPqi4tPhMuj6mUUWPSafW57roNw+byfhMHPxdARxA7A98broQFagM4x9hGU/fsxvgTxKXm3ji5F5szFxGeByExZ9TWpYwCOMAqVCBGUB3KeXKTcohs9ii4qSdfmaBqwkVBCoQkBncGzWijat/GnzOl6HpM3uhkUTEEdyQny0cUiPvbpWeaEKpRlI/i27vOvyR93fhp7IoJkPeD/Jw39xHpdx2JDZ6ZvdvdHaSNgwAXv7h9q4D3Jk1bxRm+vZwHuQRTOrzsh9TrD6UY7PN1kSVPPJz6ave/4sqjlAgMBAAECggEAIHfVfzNMWCSgPEeRWvUV+6gjkF9aa5+VBHadQgThoEEfBvNg3JsvBzVg4gvECInTuCfQShpgmZszBXAi4biCRr4lgl4CsibL4YdOfhnjlPoIZ4QW/0a+TUj4HEbmzrBCmXMuanRAmJ42LBhpObY7x67Z6n5Zbes6kfNVygfIF/UQr2kMBwQxs0g7XdhIgbWi1ZEIG2m+CS0SGTFQ4EDB0GXcxk/qR4msCbeHRgg2YerSKDE3arIMzsyRAmQxiehzsKWINv7souK5FSIn4KKmVENRXFRJU/Iz+VBTz9ekbQI/WCsNiaVOqxAyuN6pzXaJHFSJfmC1mKokVGdNkOuyUQKBgQDW6j2vFXeSvivQLtAGwNki+Ud9ghDhhomWbrmjj9Dj3p6VxZSdvmxbNRAxRxkqNABGWBBOmH6XV+MEQdPuXMtVtFYmB835JKuh2d1w/a3MGyuC+qvRkbq8z5WNQ8lt/PvSOLdNrQnYkO1PgP23TuY9S3SoTwOLM1DgUq2EU/0FswKBgQC2jvpX1rCNzuns91F8ODxZFvyz77ZmJ74U70p4So4PRHtp/tW9amM/YCSEiDuYLNbq4mJzTwWnmcpwvTsI4+9hTlVtE7Od4Ud9APiGq30S2qNzalOHVj5zIutz0emxZ9CLt9c0bR7kBlC/ubj+n7WLuSx6pzDpZ2cz5ljPgIv7BwKBgE+BpsI/6dWCvwisWM82xt2WZOk8lewo7nuViN3GlZvHPtnfl2RoDwQK9GuWhuvDAxsC9z1sUuDAZb6sb6/t6ldReNFm778s3sXoqHBlmMc6gfVZsPi9tdyl2wjAvCMRhUO3MbDtxAe3TeOsr7AB41FnuceOO5xippfmXkTnIWLbAoGBAKTW5WV7DWB+fOrjRkiL3rHy96rhwCjtqcztZKjJaQyQIX/MAuNNtN/8x/yDpov5sPXWBF9p8wkq7Ihv1WZpqjSKrsgXChB84jgVMFMpdX4YvY3xbAq/7RO5bDDgUctGmiAOl9tWzJXIvhDgehIv05WeVnNJk/iszZsnWvnCcKLxAoGBAIlvyEjpHURVXTppPA4E9B8LENK0adlPSPfD6pWhGpGt/SBwDT1q8Gri7oVvWY90Jsp4q8QHcvAGyDYl164QVSB4+/bV9iWVyS4HTxYY0DWewKKJFlqr0JVyRLY+mOURnj7kPjIwfJfQgcCd8tTF3HmXkLtVpca2oGp9qpgbMPH7";
	/** 支付宝公钥 */
	private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk531E3AEL3uvTv8rRDS/F1ijZPX1yEvNnfEQ26ewazx4ximM2Pxq2eXgDT0+kZLnNVb+GZkVvu4Eq6UqeEYoskgHrjIBIXPFDyMMrWjNyMEzLu3D4gK/IZkHIxxG12gufAx/j2LAkEDuThJfdu82+9pGN29TUSKIrdPBcmU3P345ccK+F542JB2qvsvvMH2YmHpdmsT8Iglfvn74Z2qTbW3G9ws1VtWnCBkF9rOsRjnGgFgx6yWM/XbTvzWFe4f1/f0q43gFhtbfF9QyQjcVpPtRzzD7yd4H6KwV+fGrVSHtN58hQ/FltV0teo3kKrSwwvSYq0P4Uhxjng1NjCVTsQIDAQAB";
	/** 支付宝APPID */
	private static String ALIPAY_APP_ID = "2017032706434289";
	/** 支付宝网关 */
	private static String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
	private static String ALIPAY_SELLER_ID = "2088101188244694";
	private static final String ALIPAY_PRODUCT_CODE = "QUICK_WAP_PAY";
	private static final String JSON = "JSON";
	private static final String CHARSET = "UTF-8";
	private static final String SIGN_TYPE = "RSA2";
	private static DefaultAlipayClient ALIPAY_CLIENT;

	//加载私钥和公钥
	static {
		String publicKey = "/web/alipay_public.pem";
		String privateKey = "/web/alipay_application_private.pem";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(privateKey)));
			ALIPAY_PRIVATE_KEY = in.readLine();
			in.close();
			in = new BufferedReader(new InputStreamReader(new FileInputStream(publicKey)));
			ALIPAY_PUBLIC_KEY = in.readLine();
			in.close();
			logger.info("Load private key and public key is successful.");
		} catch (IOException e) {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.warn("Load private key failed. Please check [{}].", privateKey);
			logger.warn("Load public key failed. Please check [{}] public key.", publicKey);
			logger.warn("Use alipay sandbox environment.");
			ALIPAY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCd2osnaO1m38jmX4OQUuvIKaxVsmhbl/av7tZ3XS/4LtjCPSlqCRJFc9oNm3xvz4+NKRF/++QGFacwI+qyTqdel6dKd6PL2FuJE3mHOndFOSWJDfYGaNYfnntsRG4MiUTMk53x3gPMjlYnoV5InOQGaz9J1TKjikNafBQkeO2RST9tixf94UqzXqfyIAf+D16WbKG80fBpm/zSmKf0uA/qUjfHM5KcqI+s/D0VWLFTdiAUFz8KREnKSrhBQ90JEPskATeiQGcZSx8rBSz42qMZXqcCJ1k2p9uC7fGZYcGaNoyi2mxL+KVAN8KpnTo9vLYH+N+woqTzOq7o1qbDjKk/AgMBAAECggEAByEnx8/laRASctU9i/cyzweS0QwNypVCfw+6gAmlcQAsqhk5FaDzQk/JOXQcWrB1O1rvf2hRFDVGJ9Z3czY6OPuAgsyEiWBsQfSvOHxbhrTeDSKkSgXSJftyd6FflxFcGiFQsOvmIAcNvRx8targYanWYe9PKjPEdwHDpyIPGNCz8Ri8XoAXHtu0kvZUKsHSpmxuEN7D5VpOc9HrQdND84tE7csJcdTTDoTdvF55JwlDnliXgJasRdwoX28yTJ/ISO3kcrcuahjehLw308pb6Y0BFx8QfUDK7tpehPxBB2cZU2P7S1ACJBHpe1XxAY00MoWAdImVrQnJoekVyr0JgQKBgQDQRAE5A0YS+jDeVdsPGkzq6mPaL62Vf6YPAoZ+N0TCHCNAkv+qWGEYY8gm7LrpFht9riWae8Tu/mH/6xpn1ijQ036dkdqjGSd7zNeTASuHvG4xBj7lR3guh0EIlKvuv/r1ed3d54g/BQASt4cEQovGbFVyjpmt7U7hMTdhPfEwawKBgQDCCJ12kb61vRX3Y/mhng1mgTdwm0EAnJ/OJdVbO4CkZRVTIjduw7yqMmCxCR3/HVFpAUFlfgzFpdFZPJlk9HTi/PLqRVv7PceQM7xtr4nFOvUTYOktrgmbBkmBSln5SDuHCAI0geef/11C3WFAZ0f2jPYyJVFsEmvrJhb+nkVPfQKBgQDAlHGw/4b3m2Q+45MZpw9DFpRYuEfB+vRZ4tLsuTyaZZxvZL6HDQxTmkkFPVgkmq23DKZ28EYNXSrPk92p8M1zNG5bU/ihYmwxPRyGMcnHzQTzzjuwleLa23TWc2t87QcpoJP5UtSYuY30wMJXvRU02Q42TQhsXQjYkUMNZ1K0mwKBgQCdPcoNqnK4aPGCOFy2YpVNem3pLYfx7Pt8XiCW/JN1pg0v/8yYULt12bmN/mt2sNWmmMPccdf73DLZdEiDDbAjuwVIYMj3w8sTyzlqUhT95pdxCtoskJtsLB4pTo23fJfxB8Zxssx4VsPHSgxj2s7wuK9TV/ax9APM3mNIGA7nKQKBgDU8fAHWGehDlA1byXV7xp/IdguD5drWcms9T8JBTKSevDoCmlNwvnyFNPVw9JwXXXc89fdjLSMXEZv5Iz/D1BrfMp0jVFbCbt4BBUI2GhmJoulVpa1VXFc9GNOYRpTwVmMf1Hxxy/cUk5I2iD9CWHoGcFai+fdHjpuYSSPoOd6f";
			ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk531E3AEL3uvTv8rRDS/F1ijZPX1yEvNnfEQ26ewazx4ximM2Pxq2eXgDT0+kZLnNVb+GZkVvu4Eq6UqeEYoskgHrjIBIXPFDyMMrWjNyMEzLu3D4gK/IZkHIxxG12gufAx/j2LAkEDuThJfdu82+9pGN29TUSKIrdPBcmU3P345ccK+F542JB2qvsvvMH2YmHpdmsT8Iglfvn74Z2qTbW3G9ws1VtWnCBkF9rOsRjnGgFgx6yWM/XbTvzWFe4f1/f0q43gFhtbfF9QyQjcVpPtRzzD7yd4H6KwV+fGrVSHtN58hQ/FltV0teo3kKrSwwvSYq0P4Uhxjng1NjCVTsQIDAQAB";
			ALIPAY_APP_ID = "2016080300160880";
			ALIPAY_URL = "https://openapi.alipaydev.com/gateway.do";
			ALIPAY_SELLER_ID = "2088102169829305";
		} finally {
			ALIPAY_CLIENT = new DefaultAlipayClient(ALIPAY_URL, ALIPAY_APP_ID, ALIPAY_PRIVATE_KEY, JSON, CHARSET,
					ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		}
	}

	/**
	 * 发送验证码
	 * @param url 短信服务地址
	 * @param businessCase 业务场景
	 * @param mobile 手机号
	 * @param captcha 发送的验证码
	 * @return
	 */
	public static boolean sendSmsCaptcha(String url, String businessCase, String mobile, String captcha) {
		logger.info("[发送短信验证码] url={}, businessCase={}, mobile={}, captcha={}", url, businessCase, mobile, captcha);
		String content = "";// = "您好，您的验证码是：" + captcha;
		if ("test".equals(businessCase) || "register".equals(businessCase) || "login".equals(businessCase)) {
			//content = "您好，您的验证码是：" + captcha;
			content = captcha;
		} else {
			throw new OohdearException(90001, "业务场景错误");
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
		logger.warn("[发送短信验证码失败] url={}, businessCase={}, mobile={}, captcha={}", url, businessCase, mobile, captcha);
		return false;
	}

	/**
	 * 支付宝统一下单接口
	 * @param orderNo 订单号
	 * @param orderPrice 价格
	 * @param returnUrl 同步通知地址
	 * @param notifyUrl 异步通知地址
	 * @return
	 * @throws AlipayApiException
	 */
	public static String alipayForH5OrApp(String orderNo, String orderPrice, String returnUrl, String notifyUrl)
			throws AlipayApiException {
		//支付宝统一下单
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
		alipayRequest.setReturnUrl(returnUrl);
		alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
		JSONObject params = new JSONObject();
		params.put("body", "定制商品");
		params.put("subject", "ooh dear定制商品");
		params.put("out_trade_no", orderNo);
		params.put("total_amount", orderPrice);
		params.put("product_code", ALIPAY_PRODUCT_CODE);
		//params.put("seller_id", "2088102169829305");
		params.put("goods_type", "1");
		alipayRequest.setBizContent(params.toJSONString());//填充业务参数
		//调用SDK生成表单
		String form = ALIPAY_CLIENT.pageExecute(alipayRequest).getBody();
		/*httpResponse.setContentType("text/html;charset=" + Constant.CHARSET);
		httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
		httpResponse.getWriter().flush();*/
		return form;
	}

	public static String alipayForWeb(String orderNo, String orderPrice, String returnUrl, String notifyUrl)
			throws IOException {
		////////////////////////////////////请求参数//////////////////////////////////////
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", notifyUrl);
		sParaTemp.put("return_url", returnUrl);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", orderNo);
		sParaTemp.put("subject", "ooh dear定制商品");
		sParaTemp.put("total_fee", orderPrice);
		sParaTemp.put("body", "定制商品");
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
		//如sParaTemp.put("参数名","参数值");

		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		return sHtmlText;
		//response.getOutputStream().println(sHtmlText);
	}

	/**
	 * 支付宝交易查询
	 * @param orderNo 订单号
	 * @return
	 */
	public static AlipayTradeQueryResponse alipayQuery(String orderNo) {
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizContent("{\"out_trade_no\":\"" + orderNo + "\"}");
		AlipayTradeQueryResponse response = null;
		try {
			response = ALIPAY_CLIENT.execute(request);
		} catch (AlipayApiException e) {
			logger.warn("支付宝订单支付情况查询失败", e);
		}
		return response;
	}

	/**
	 * 验签
	 * @param params
	 * @param sellerId
	 * @param appid
	 * @return
	 * @throws AlipayApiException
	 */
	public static boolean alipaySign(Map<String, String> params, String sellerId, String appid)
			throws AlipayApiException {
		if (ALIPAY_SELLER_ID.equals(sellerId) && ALIPAY_APP_ID.equals(appid)) {
			return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
		}
		return false;
	}

	/**
	 * 验签
	 * @param params
	 * @return
	 * @throws AlipayApiException
	 */
	public static boolean alipaySign(Map<String, String> params) throws AlipayApiException {
		return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
	}
}
