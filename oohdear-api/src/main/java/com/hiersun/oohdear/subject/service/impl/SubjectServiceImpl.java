package com.hiersun.oohdear.subject.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.subject.entity.SubjectInfo;
import com.hiersun.oohdear.subject.mapper.SubjectInfoMapper;
import com.hiersun.oohdear.subject.service.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Description:专题服务实现类
 * Author: liubaocheng
 * Create: 2017-03-07 9:30
 **/
@Service
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    private SubjectInfoMapper subjectInfoMapper;

    public List<SubjectInfo> getSubjects(){
    	Example example=new Example(SubjectInfo.class);
    	example.createCriteria().andEqualTo("showStatus", false);
    	example.orderBy("topValue").desc().orderBy("created").desc();
        return subjectInfoMapper.selectByExample(example);
    }

}
