package com.hiersun.oohdear.core;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hiersun.oohdear.config.redis.RedisUtil;
import com.hiersun.oohdear.util.SignatureUtils;

/**
 * 验签过滤器，可以配置是否验签和不需要验签的url（filer的执行顺序可以通过控制filter的文件名来控制，按字母顺序，所以DecryptHeadFilter在SignatureFilter之前执行）
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@WebFilter
@Component
public class SignatureFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(SignatureFilter.class);

	/** 是否启用验签 **/
	@Value("${sign.enabled}")
	private boolean sign;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 不需要验签的地址，多个url可以使用逗号分割，目前不支持表达式<br>
	 * 例如：GET /user/member,POST /user/register<br>
	 * TODO 可以参考过滤器设计成匹配表达式模式 
	 */
	@Value("${sign.without.urls}")
	private String notSignUrls;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(request.getMethod().toUpperCase());
		if(!"OPTIONS".equals(request.getMethod().toUpperCase())){
			String url = request.getMethod().toUpperCase() + " " + request.getRequestURI();
			//判断是否需要验签，如果启用验签并且当前url需要验签，则进行验签
			if (sign && sign(url)) {
				String sign = request.getHeader("sign");
				String timestamp = request.getHeader("timestamp");
				String nonce = request.getHeader("nonce");
				String memberNo = request.getHeader("memberNo");
				logger.debug("开始验签 url={}, sign={}, timestamp={}, nonce={}, memberNo={}", url, sign, timestamp, nonce,
						memberNo);
				String token = (String) redisUtil.get(CacheKey.MEMBER_TOKEN + memberNo);
				boolean result = SignatureUtils.check(sign, timestamp, nonce, memberNo, token);
				if (!result) {
					throw new OohdearException(99997, "验签失败");
				}
			} else {
				logger.debug("{} 没有进行验签", url);
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 判断url是否需要验签
	 * @param url 地址
	 * @return
	 */
	public boolean sign(String url) {
		if (notSignUrls == null) {
			return true;
		}
		String[] urls = notSignUrls.split(",");
		for (String s : urls) {
			if (url.equals(s)) {
				return false;
			}
		}
		return true;
	}
}
