package com.hiersun.oohdear.core;

/**
 * 定义redis缓存KEY的前缀
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class CacheKey {

	/**
	 * 会员token前缀
	 */
	public final static String MEMBER_TOKEN = "api.oohdear.member.token.";
	
	/**
	 * 登录验证码前缀
	 */
	public final static String MEMBER_CAPTCHA= "api.oohdear.member.captcha.";
}
