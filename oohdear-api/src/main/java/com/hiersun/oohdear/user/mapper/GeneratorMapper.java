package com.hiersun.oohdear.user.mapper;


/**
 * date 2016/11/15 14:50
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public interface GeneratorMapper {

	/**
	 * 根据不同业务No，获取该业务下的当前编号
	 * @param businessNo
	 * @return
	 */
	int selectBusinessNo(String businessNo);

}
