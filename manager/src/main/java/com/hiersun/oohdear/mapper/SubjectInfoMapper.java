package com.hiersun.oohdear.mapper;

import com.hiersun.oohdear.entity.SubjectInfo;
import com.hiersun.oohdear.entity.vo.SubjectInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SubjectInfoMapper extends Mapper<SubjectInfo> {
    List<SubjectInfo> getSubjectList(SubjectInfoVo subjectInfoVo);
}