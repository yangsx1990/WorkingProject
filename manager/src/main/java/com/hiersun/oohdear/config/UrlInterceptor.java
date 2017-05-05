package com.hiersun.oohdear.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月21日 上午9:47:12 
 * @version 1.0 
 */
public class UrlInterceptor  implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger("UrlInterceptor");
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2) throws Exception {
		String url = request.getRequestURL().toString(); 
		System.out.println(url);
		return false;
	}

}
