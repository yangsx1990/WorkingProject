package com.hiersun.oohdear.entity.vo;/**
 * Created by liubaocheng on 2017/3/9.
 */

/**
 * Description:文章留言查询辅助类
 * Author: liubaocheng
 * Create: 2017-03-09 12:09
 **/
public class ArticleMessageVo extends BaseVoHelper {

    /**
     * 留言人
     */
    private String memberName;

    /**
     * 文章标题
     */
    private String articleName;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 删除状态
     */
    private Boolean deleted;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
