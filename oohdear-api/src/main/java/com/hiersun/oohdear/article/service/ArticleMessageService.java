package com.hiersun.oohdear.article.service;/**
 * Created by liubaocheng on 2017/3/7.
 */

import java.util.List;

import com.hiersun.oohdear.article.entity.ArticleMessage;
import com.hiersun.oohdear.vo.ArticleMessageVo;

/**
 * Description:文章留言服务接口
 * Author: liubaocheng
 * Create: 2017-03-07 14:05
 **/
public interface ArticleMessageService {


    /**
     * 删除留言信息
     * @param memberNo 用户编号
     * @param messageId 留言id(ID)
     * @return
     */
    Boolean deleteMessage(String memberNo,Long messageId);

	/**
	 * 添加留言信息
	 * @param memberNo 会员编号
	 * @param articleId 文章id
	 * @param content 评论内容
	 * @return
	 */
    Boolean addMessage(String memberNo,Long articleId,String content);

    /**
     * 文章留言数
     * @param articleId 文章编号(ID)
     * @return
     */
    int selectCount(long articleId);
    /**
     * 查询留言
     * @param memberNo
     * @param articleId
     * @return
     */
	List<ArticleMessageVo> queryMessage(String memberNo,Long articleId);
}
