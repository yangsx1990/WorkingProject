package com.hiersun.oohdear.core;


/**
 * date 2016/11/11 17:02
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class OohdearException extends RuntimeException {

	private static final long serialVersionUID = -4302737225440011503L;

	/**
	 *  代码
	 */
	private Integer code;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 描述
	 */
	private String description;

	public OohdearException(Integer code, String message) {
		super(String.format("code=[%d], description=[%s], message=[%s]", code, message, message));
		this.code = code;
		this.message = message;
		this.description = message;
	}

	public OohdearException(Integer code, String message, String description) {
		super(String.format("code=[%d], description=[%s], message=[%s]", code, description, message));
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public OohdearException(Integer code, String message, String description, Throwable cause) {
		super(String.format("code=[%d], description=[%s], message=[%s]", code, description, message), cause);
		this.code = code;
		this.message = message;
		this.description = description;
	}

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

}
