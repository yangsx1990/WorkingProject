package com.hiersun.oohdear.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月21日 上午9:54:12 
 * @version 1.0 
 */
/*@Configuration*/
public class WebConfig extends WebMvcConfigurerAdapter{
	 /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UrlInterceptor());
    }
}
