package com.hiersun.oohdear.core;/**
 * Created by liubaocheng on 2017/3/1.
 */

/**
 * Description:HTTP 响应体
 * Author: liubaocheng
 * Create: 2017-03-01 14:58
 **/
import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
/**
 * 响应体
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = -588970587102075140L;

    /** 响应头 **/
    private Head head = new Head();
    /** 响应体 **/
    private JSONObject body = new JSONObject();

    public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ResponseBody [head=" + head + ", body=" + body + "]";
	}

	/**
	 * 响应体
	 * @author liuyang
	 * @email y_liu@hiersun.com | 745089707@qq.com
	 */
	public class Head{
		/** 响应代码 **/
    	private Integer code = 0;
    	/** 响应消息 **/
    	private String message = "操作成功";
    	/** 响应描述 **/
    	private String description = "操作成功";
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		@Override
		public String toString() {
			return "head [code=" + code + ", message=" + message + "]";
		}
		
    }
}

