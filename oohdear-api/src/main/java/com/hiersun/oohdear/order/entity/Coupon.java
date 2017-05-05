package com.hiersun.oohdear.order.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 优惠码
	 */
	@Column(name = "coupon_code")
	private String couponCode;

	/**
	 * 优惠金额
	 */
	@Column(name = "coupon_price")
	private BigDecimal couponPrice;

	/**
	 * 优惠券起始时间
	 */
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 优惠券截止时间
	 */
	@Column(name = "end_time")
	private Date endTime;
	
	/**
	 * 优惠类型名称
	 */
	@Column(name = "type_name")
	private String typeName;

	/**
	 * 优惠名称
	 */
	private String name;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取优惠码
	 *
	 * @return coupon_code - 优惠码
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * 设置优惠码
	 *
	 * @param couponCode 优惠码
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * 获取优惠金额
	 *
	 * @return coupon_price - 优惠金额
	 */
	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	/**
	 * 设置优惠金额
	 *
	 * @param couponPrice 优惠金额
	 */
	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	/**
	 * 获取优惠券起始时间
	 *
	 * @return start_time - 优惠券起始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 设置优惠券起始时间
	 *
	 * @param startTime 优惠券起始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取优惠券截止时间
	 *
	 * @return end_time - 优惠券截止时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置优惠券截止时间
	 *
	 * @param endTime 优惠券截止时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
     * 获取优惠类型名称
     *
     * @return type_name - 优惠类型名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置优惠类型名称
     *
     * @param typeName 优惠类型名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取优惠名称
     *
     * @return name - 优惠名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置优惠名称
     *
     * @param name 优惠名称
     */
    public void setName(String name) {
        this.name = name;
    }
}