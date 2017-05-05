package com.hiersun.oohdear.entity.vo;
/** 
 * @author  saixing_yang@hiersun.com
 * @date 创建时间：2017年3月21日 下午7:40:55 
 * @version 1.0 
 */
public class UserPermitVo {
	
	private Long id;
	private Integer roleId;
	private Boolean userPermit;
	private Boolean orderPermit;
	private Boolean operationPermit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Boolean getUserPermit() {
		return userPermit;
	}
	public void setUserPermit(Boolean userPermit) {
		this.userPermit = userPermit;
	}
	public Boolean getOrderPermit() {
		return orderPermit;
	}
	public void setOrderPermit(Boolean orderPermit) {
		this.orderPermit = orderPermit;
	}
	public Boolean getOperationPermit() {
		return operationPermit;
	}
	public void setOperationPermit(Boolean operationPermit) {
		this.operationPermit = operationPermit;
	}
	
}
