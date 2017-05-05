package com.hiersun.oohdear.subject.service;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.subject.entity.SubjectInfo;

import java.util.List;

/**
 * Description:专题服务接口
 * Author: liubaocheng
 * Create: 2017-03-07 9:29
 **/
public interface SubjectService {

	/**
	 * 获取专题列表
	 * @return
	 */
    List<SubjectInfo> getSubjects();

}
