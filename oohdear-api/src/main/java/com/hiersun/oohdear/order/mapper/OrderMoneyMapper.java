package com.hiersun.oohdear.order.mapper;

import java.math.BigDecimal;
import java.util.Map;

import com.hiersun.oohdear.order.entity.OrderMoney;

import tk.mybatis.mapper.common.Mapper;

public interface OrderMoneyMapper extends Mapper<OrderMoney> {
	Map<String,BigDecimal> getFuzzyOrderMoney(OrderMoney order);
}