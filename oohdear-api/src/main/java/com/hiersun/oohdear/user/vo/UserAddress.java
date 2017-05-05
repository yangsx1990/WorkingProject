package com.hiersun.oohdear.user.vo;

import java.io.Serializable;

/** 用户地址Vo
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 下午4:16:58 
 * @version 1.0 
 */
public class UserAddress  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String consignee;
	private String mobile;
	private String zone;
	private String detail;
	private Boolean defaultAddr;
	public UserAddress(){}
	public UserAddress(Long id, String consignee, String mobile, String zone,String detail,
			Boolean defaultAddr) {
		super();
		this.id = id;
		this.consignee = consignee;
		this.mobile = mobile;
		this.zone=zone;
		this.detail = detail;
		this.defaultAddr = defaultAddr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Boolean getDefaultAddr() {
		return defaultAddr;
	}
	public void setDefaultAddr(Boolean defaultAddr) {
		this.defaultAddr = defaultAddr;
	}
	
	
	
}
