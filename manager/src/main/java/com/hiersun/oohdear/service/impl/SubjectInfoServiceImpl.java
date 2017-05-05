package com.hiersun.oohdear.service.impl;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.SubjectInfo;
import com.hiersun.oohdear.entity.vo.SubjectInfoVo;
import com.hiersun.oohdear.mapper.SubjectInfoMapper;
import com.hiersun.oohdear.service.SubjectInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-09 15:22
 **/
@Service
@Transactional
public class SubjectInfoServiceImpl implements SubjectInfoService {

    @Autowired
    private SubjectInfoMapper subjectInfoMapper;

    @Override
    public List<SubjectInfo> getSubjectList(SubjectInfoVo subjectInfoVo) {
        return subjectInfoMapper.getSubjectList(subjectInfoVo);
    }

    @Override
    public int maskSubjectInfo(SubjectInfo subjectInfo) {
        return subjectInfoMapper.updateByPrimaryKeySelective(subjectInfo);
    }

    @Override
    public SubjectInfo getSubjectInfo(SubjectInfo subjectInfo) {
        return subjectInfoMapper.selectOne(subjectInfo);
    }

    @Override
    public int setTop(SubjectInfo subjectInfo) {
        return subjectInfoMapper.updateByPrimaryKeySelective(subjectInfo);
    }

	@Override
	public Boolean addSubjectInfo(SubjectInfo subject) {
		subject.setCreated(new Date());
		subject.setShowStatus(false);
		return subjectInfoMapper.insertSelective(subject)==1;
	}

	@Override
	public Boolean updateSubjectInfo(SubjectInfo subject) {
		if(subject!=null){
			Example example=new Example(SubjectInfo.class);
			example.createCriteria().andEqualTo("id",subject.getId());
			return subjectInfoMapper.updateByExampleSelective(subject, example)==1;
		}
		return null;
	}
}
