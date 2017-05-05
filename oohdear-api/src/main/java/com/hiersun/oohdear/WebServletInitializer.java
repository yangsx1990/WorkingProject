/**
 * Copyright © 2016 hiersun Holdings Limited.
 * All rights reserved.
 * @Author      lambor(wade)
 * @Version		v1.0.0
 * @Date        2016年9月26日
 * @Decription
 * @History
 *//*
////////////////////////////////////////////////////////////////////
//							_ooOoo_                               //
//						   o8888888o                              //
//						   88" . "88                              //
//						   (| ^_^ |)                              //
//						   O\  =  /O                              //
//						____/`---'\____                           //
//					  .'  \\|     |//  `.                         //
//					 /  \\|||  :  |||//  \                        //
//				    /  _||||| -:- |||||-  \                       //
//				    |   | \\\  -  /// |   |                       //
//					| \_|  ''\---/''  |   |                       //
//					\  .-\__  `-`  ___/-. /                       //
//				  ___`. .'  /--.--\  `. . ___                     //
//				."" '<  `.___\_<|>_/___.'  >'"".                  //
//			  | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//		      \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//		========`-.____`-.___\_____/___.-`____.-'========         //
//							 `=---='                              //
//		^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//			     佛祖保佑                  永无BUG      永不修改         			      //
////////////////////////////////////////////////////////////////////
 */
package com.hiersun.oohdear;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author lambor(Wade)
 * @comment :
 * @time	: 2016年9月26日 下午6:04:55
 */
public class WebServletInitializer extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return  builder.sources(O2oOohdearApplication.class);
	}
}
