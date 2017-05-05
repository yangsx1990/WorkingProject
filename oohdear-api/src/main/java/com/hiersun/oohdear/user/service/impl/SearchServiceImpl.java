package com.hiersun.oohdear.user.service.impl;/**
 * Created by liubaocheng on 2017/3/6.
 */

import com.hiersun.oohdear.user.entity.UserSearch;
import com.hiersun.oohdear.user.mapper.UserSearchMapper;
import com.hiersun.oohdear.user.service.SearchService;
import com.hiersun.oohdear.user.vo.UserSearchVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:搜索服务实现类
 * Author: liubaocheng
 * Create: 2017-03-06 14:28
 **/
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private UserSearchMapper userSearchMapper;

    /**
     * 查询搜索历史（按时间倒序排列）
     * @return
     */
    @Override
    public List<UserSearchVo> getUserSearch(String memberNo) {
    	UserSearch userSearch=new UserSearch();
    	userSearch.setMemberNo(memberNo);
    	Example example =new Example(UserSearch.class);
    	example.createCriteria().andEqualTo("memberNo", memberNo).andEqualTo("deleted", false);
    	example.setOrderByClause("last_modified DESC");
    	List<UserSearch> searchList=userSearchMapper.selectByExample(example);
    	List<UserSearchVo> searchVoList=new ArrayList<>();
    	searchList.forEach(action->{
    		UserSearchVo searchVo=new UserSearchVo();
    		searchVo.setId(action.getId());
    		searchVo.setSearchContent(action.getSearchContent());
    		searchVo.setSearchCount(action.getSearchCount());
    		searchVoList.add(searchVo);
    	});
        return searchVoList;
    }

    /**
     * 添加用户搜索历史
     * @param userSearch
     */
    @Override
    public int addUserSearch(String searchContent,String memberNo) {
    	UserSearch userSearch=new UserSearch();
    	userSearch.setMemberNo(memberNo);
    	userSearch.setSearchContent(searchContent);
        userSearch.setDeleted(false);
        UserSearch oldSearch = userSearchMapper.selectOne(userSearch);
        if(oldSearch!=null){//如果已经存在，将搜索次数+1
            oldSearch.setSearchCount(oldSearch.getSearchCount()+1);
            oldSearch.setLastModified(userSearch.getLastModified());//将时间更新为最新时间
             userSearchMapper.updateByPrimaryKeySelective(oldSearch);
        }else{
        	userSearch.setSearchCount(1);
        	userSearch.setLastModified(new Date());
             userSearchMapper.insert(userSearch);
        }
        
        int count=selectCount(memberNo);
        if(count>10){
        	UserSearch search=new UserSearch();
        	search.setDeleted(true);
        	Example example =new Example(UserSearch.class);
        	example.createCriteria().andEqualTo("memberNo", memberNo).andEqualTo("deleted", false);
        	example.setOrderByClause("last_modified ASC limit 0,1");
        	List<UserSearch> searchList=userSearchMapper.selectByExample(example);
        	if(searchList.size()==1){
        		search.setId(searchList.get(0).getId());
        	}
        	userSearchMapper.updateByPrimaryKeySelective(search);
        }
        return 1;
    }

    @Override
    public int deleteUserSearch(Long searchId,String memberNo) {
    	UserSearch userSearch=new UserSearch();
    	userSearch.setDeleted(true);
    	Example example=new Example(UserSearch.class);
    	Criteria criteria=example.createCriteria();	
    	if(searchId!=0){
        	userSearch.setId(searchId);
        	criteria.andEqualTo("id", searchId);
    	}else{
    		criteria.andEqualTo("memberNo", memberNo);
    	}
    	//criteria.andEqualTo("deleted", false);
        return userSearchMapper.updateByExampleSelective(userSearch, example);
    }

	@Override
	public int selectCount(String memberNo) {
		UserSearch userSearch=new UserSearch();
    	userSearch.setDeleted(false);
    	userSearch.setMemberNo(memberNo);
    	return userSearchMapper.selectCount(userSearch);
	}
}
