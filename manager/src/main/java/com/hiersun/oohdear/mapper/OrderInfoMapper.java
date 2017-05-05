package com.hiersun.oohdear.mapper;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.vo.OrderInfoVo;

public interface OrderInfoMapper extends Mapper<OrderInfo> {

	/**
	 * 查询订单列表
	 * @param orderInfoVo
	 * @return
	 */
	List<OrderInfoVo> orderList(OrderInfoVo orderInfoVo);
}