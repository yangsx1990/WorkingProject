package com.hiersun.oohdear.user.service;


/**
 * date 2016/11/15 15:04
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public interface GeneratorService {

	static final String MEMBER_NO = "memberNo";
	static final String ORDER_NO = "orderNo";
	static final String GOODS_NO = "goodsNo";

	String generatorMemberNo(Integer channel);

	String generatorOrderNo(Integer type);

	String generatorGoodsNo();

}
