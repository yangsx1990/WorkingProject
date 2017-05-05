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

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author lambor(Wade)
 * @comment :
 * @time	: 2016年9月26日 上午10:56:57
 */
@WebServlet(
	urlPatterns="/druid/*",
	initParams={
		@WebInitParam(name="allow",value="127.0.0.1,192.168.4.249"),//IP 白名单
		@WebInitParam(name="deny",value=""),//IP黑名单(存在相同时，deny优先于allow)
		@WebInitParam(name="loginUsername",value="wade"),
		@WebInitParam(name="loginPassword",value="123456"),
		@WebInitParam(name="resetEnable",value="false")//禁用HTML页面上的“reset all”功能
	}
)
public class DruidStatViewServlet extends StatViewServlet {
	private static final long serialVersionUID = 1600179573217938161L;

}
