package com.hiersun.oohdear.entity;

/**
 * 订单状态
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public enum OrderStatus {
	/** 10待付款 **/
	WAIT_PAY(10, "待付款", "已付款"),
	/** 20待确认 **/
	WAIT_CONFIRM(20, "待确认", "已确认"),
	/** 30设计中 **/
	DESIGNING(30, "设计中", "设计中"),
	/** 40待制作 **/
	WAIT_MAKE(40, "待制作", "已制作"),
	/** 50制作中 **/
	MAKING(50, "制作中", "制作中"),
	/** 60待发货 **/
	WAIT_SHIPPING(60, "待发货", "已发货"),
	/** 70已发货 **/
	SHIPPED(70, "已发货", "已发货"),
	/** 80已完成 **/
	DONE(80, "已完成", "已完成"),
	/** 31不可做 **/
	REFUSE(31, "不可做", "不可做"),
	/** 41待退款 **/
	WAIT_REFUND(41, "待退款", "待退款"),
	/** 51已退款 **/
	REFUNDED(51, "已退款", "已退款"),
	/** 21已关闭 **/
	CLOSED(21,"已关闭","已关闭");

	private Integer code;
	private String name;
	/** 完成后名称 */
	private String finishedName;

	private OrderStatus(Integer code, String name, String finishedName) {
		this.code = code;
		this.name = name;
		this.finishedName = finishedName;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinishedName() {
		return finishedName;
	}

	public void setFinishedName(String finishedName) {
		this.finishedName = finishedName;
	}

	public static String forOrderCode(int code) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.code == code)
				return status.name;
		}
		return "";
	}

	public static String forOrderFinishedName(int code) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.code == code)
				return status.getFinishedName();
		}
		return "";
	}
}
