package com.hiersun.oohdear.order.mapper;

import java.util.List;
import java.util.Map;

import com.hiersun.oohdear.order.entity.Coupon;

import tk.mybatis.mapper.common.Mapper;

public interface CouponMapper extends Mapper<Coupon> {
	/**
	 * 校验验证码是否可用
	 * @param coupons
	 * @return
	 */
	List<Coupon> queryCoupons(Map coupons);
}