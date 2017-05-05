package com.hiersun.oohdear.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 *
 * @author 
 * @version V1.0
 */
public class StringUtil {

	/**
	 * 校验手机号码格式是否正确
	 * 
	 * @param mobiles
	 *            手机号
	 * @return
	 * 
	 *         具体校验规则：大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数
	 *         15+除4的任意数 18+除1和4的任意数 17+除9的任意数
	 */
	public static boolean isMobile(String mobiles) {
		if (null == mobiles || mobiles.trim().isEmpty()) {
			return false;
		} else {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");

			Matcher m = p.matcher(mobiles);

			return m.matches();
		}
	}
	/**
	 * 正则表达式：验证邮箱
	 */
	public static boolean isEmail(String email) {
		String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * 校验中文字符
	 * @param name
	 * @return
	 */
	public static boolean isChineseName(String name) {
		boolean isChinese = false;
		for (int i = 0; i < name.length(); i++) {
			String perStr = name.substring(i, i + 1);
			isChinese = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", perStr);
			if (isChinese) {
				return isChinese;
			}
		}
		return isChinese;
	}

	/**
	 * 获取星标手机号
	 * 例如：18686637936转换为186****7936
	 * @param mobile 手机号
	 * @return 
	 */
	public static String getStarMobile(String mobile) {
		if (StringUtils.hasText(mobile) && mobile.length() == 11) {
			return mobile.substring(0, 3) + "****" + mobile.substring(7);
		}
		return mobile;
	}

}
