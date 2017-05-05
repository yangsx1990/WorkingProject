package com.hiersun.oohdear.util;

import java.security.MessageDigest;

import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hiersun.oohdear.util.MD5Util;

/**
 * 数字签名工具
 * @author liuyang
 * @Email y_liu@hiersun.com | 745089707@qq.com
 * @Date 2016年9月20日
 */
public class SignatureUtils {
	private static final Logger logger = LoggerFactory.getLogger(SignatureUtils.class);

	/**
	 * 验证签名是否正确
	 * @param sign 签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param userId 用户ID
	 * @param token 用户token
	 * @return
	 */
	public static boolean check(String sign, String timestamp, String nonce, String memberNo, String token) {
		logger.info("开始验签：{}+{}+{}+{}", timestamp, nonce, memberNo, token);
		if (sign != null) {
			logger.info("sign={}", sign);
			//String realSign = MD5Encoder.encode((timestamp + nonce + memberNo + token).getBytes());
			String realSign=MD5Util.encode(timestamp + nonce + memberNo + token);
			logger.info("realSign={}", realSign);
			if (sign.equals(realSign)) {
				logger.info("验签成功");
				return true;
			} else {
				logger.info("验签失败");
				return false;
			}
		} else {
			logger.info("sign不能为空");
			return false;
		}
	}
}
