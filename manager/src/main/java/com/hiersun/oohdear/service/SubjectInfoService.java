package com.hiersun.oohdear.service;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.SubjectInfo;
import com.hiersun.oohdear.entity.vo.SubjectInfoVo;

import java.util.List;

/**
 * Description:专题信息服务接口
 * Author: liubaocheng
 * Create: 2017-03-09 15:13
 **/
public interface SubjectInfoService {

    /**
     *  获取专题列表 (分页使用)
     * @param subjectInfoVo
     * @return
     */
    List<SubjectInfo> getSubjectList(SubjectInfoVo subjectInfoVo);

    /**
     * 显示或者隐藏专题信息
     * @param subjectInfo
     * @return
     */
    int maskSubjectInfo(SubjectInfo subjectInfo);

    /**
     * 获取专题信息
     * @param subjectInfo
     * @return
     */
    SubjectInfo getSubjectInfo(SubjectInfo subjectInfo);

    /**
     * 设置置顶
     * @param subjectInfo
     * @return
     */
    int setTop(SubjectInfo subjectInfo);
    /**
     * 发布专题
     * @param subject
     * @return
     */
    Boolean addSubjectInfo(SubjectInfo subject);
    /**
     * 编辑专题
     * @param subject
     * @return
     */
    Boolean updateSubjectInfo(SubjectInfo subject);
}
