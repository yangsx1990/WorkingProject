package com.hiersun.oohdear.user.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiersun.oohdear.user.mapper.GeneratorMapper;
import com.hiersun.oohdear.user.service.GeneratorService;
import com.hiersun.oohdear.util.Luhn;

/**
 * date 2016/11/15 15:04
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

	@Autowired
	private GeneratorMapper generatorMapper;

	@Override
	public String generatorMemberNo(Integer channel) {
		//获取种子
		Integer feed = generatorMapper.selectBusinessNo(GeneratorService.MEMBER_NO);
		//进行业务规则处理
		Integer length = 7;
		String feedStr = feed.toString();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append(channel);
		for (int i = 0; i < length - feedStr.length(); i++) {
			tempStr.append("0");
		}
		tempStr.append(feedStr);
		//进行luhn算法验证，并添加验证位
		return Luhn.generateLuhnNo(tempStr.toString());
	}

	@Override
	public String generatorOrderNo(Integer type) {
		//获取种子
		Integer feed = generatorMapper.selectBusinessNo(GeneratorService.ORDER_NO);
		//进行业务规则处理
		Integer length = 6;
		String feedStr = feed.toString();
		Integer feedStrLength = feedStr.length();
		StringBuffer tempStr = new StringBuffer();
		if(type == 1) tempStr.append("30");
		else tempStr.append("31");
		tempStr.append(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2,4));
		for (int i = 0; i < length - feedStrLength; i++) {
			tempStr.append("0");
		}
		tempStr.append(feedStr);
		//进行luhn算法验证，并添加验证位
		return Luhn.generateLuhnNo(tempStr.toString());
	}

	@Override
	public String generatorGoodsNo() {
		//获取种子
		Integer feed = generatorMapper.selectBusinessNo(GeneratorService.GOODS_NO);
		//进行业务规则处理
		Integer length = 5;
		String feedStr = feed.toString();
		Integer feedStrLength = feedStr.length();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("20");
		for (int i = 0; i < length - feedStrLength; i++) {
			tempStr.append("0");
		}
		tempStr.append(feedStr);
		//进行luhn算法验证，并添加验证位
		return Luhn.generateLuhnNo(tempStr.toString());
	}

}
