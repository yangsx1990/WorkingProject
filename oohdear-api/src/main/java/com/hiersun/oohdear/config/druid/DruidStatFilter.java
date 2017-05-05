/**
 * Copyright © 2016 hiersun Holdings Limited.
 * All rights reserved.
 * @Author      lambor(wade)
 * @Version		v1.0.0
 * @Date        2016年9月26日
 * @Decription
 * @History
 */
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
package com.hiersun.oohdear.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author lambor(Wade)
 * @comment :
 * @time	: 2016年9月26日 上午10:42:42
 */
@WebFilter(
	filterName="druidWebStatFilter",urlPatterns="/*",
	initParams = {
		@WebInitParam(name="exclusions",
			value="*.js,*gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
	}
)
public class DruidStatFilter extends WebStatFilter {

}
