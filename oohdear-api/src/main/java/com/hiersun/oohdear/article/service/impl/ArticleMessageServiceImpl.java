package com.hiersun.oohdear.article.service.impl;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.hiersun.oohdear.article.entity.ArticleMessage;
import com.hiersun.oohdear.article.mapper.ArticleMessageMapper;
import com.hiersun.oohdear.article.service.ArticleMessageService;
import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.util.StringUtil;
import com.hiersun.oohdear.vo.ArticleMessageVo;

/**
 * Description:文章留言服务接口实现类
 * Author: liubaocheng
 * Create: 2017-03-07 14:08
 **/
@Service
public class ArticleMessageServiceImpl implements ArticleMessageService{

    @Autowired
    private ArticleMessageMapper articleMessageMapper;
    
    @Autowired
    private UserMemberInfoMapper userMemberInfoMapper;


    @Override
    public Boolean deleteMessage(String memberNo, Long messageId) {
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setId(messageId);
        articleMessage.setMemberNo(memberNo);
        articleMessage.setDeleted(false);
        Example example=new Example(ArticleMessage.class);
    	example.createCriteria().andEqualTo("id", messageId).andEqualTo("memberNo",memberNo);
        return articleMessageMapper.updateByExampleSelective(articleMessage, example)==1;
    }

    @Override
    public Boolean addMessage(String memberNo,Long articleId,String content) {
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setArticleid(articleId);
        articleMessage.setMemberNo(memberNo);
        articleMessage.setContent(content);
        articleMessage.setCreated(new Date());
        articleMessage.setDeleted(true);//默认显示
        return articleMessageMapper.insert(articleMessage)==1;
     }

    @Override
    public int selectCount(long articleId) {
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setArticleid(articleId);
        articleMessage.setDeleted(true);
        return articleMessageMapper.queryMessageByarticleAndmemberNo(articleMessage);
    }

	@Override
	public List<ArticleMessageVo> queryMessage(String memberNo,Long articleId) {
		//1、根据文章id查询留言列表
		Example example=new Example(ArticleMessage.class);
    	example.createCriteria().andEqualTo("articleid", articleId).andEqualTo("deleted",true);
    	example.orderBy("created").desc();
    	List<ArticleMessage> messageList= articleMessageMapper.selectByExample(example);
    	//2、装配返回vo
    	List<ArticleMessageVo> messages=new ArrayList<>();
    	messageList.forEach(action->{
    		ArticleMessageVo message=new ArticleMessageVo();
    		message.setId(action.getId());
    		message.setContent(action.getContent());
    		message.setCreateTime(action.getCreated()!=null?new SimpleDateFormat("MM-dd HH:mm").format(action.getCreated()):"----");
    		message.setMemberNo(action.getMemberNo());
    		//2.1 根据用户编号查询昵称
    		UserMemberInfo user=new UserMemberInfo();
    		user.setMemberNo(action.getMemberNo());
    		user.setDeleted(false);	
    		if(memberNo.equals(action.getMemberNo())){
    			message.setIsOwnMessage(true);
    		}else{
    			message.setIsOwnMessage(false);
    		}
    		user=userMemberInfoMapper.selectOne(user);
    		message.setAvatar(user.getAvatar());
    		message.setMemberNo(action.getMemberNo());
    		if (StringUtil.isMobile(user.getNickName())) {
				message.setNickname(StringUtil.getStarMobile(user.getNickName()));
			} else {
				message.setNickname(user.getNickName());
			}	
    		messages.add(message);
    	});
    	 return messages;
	}
}
