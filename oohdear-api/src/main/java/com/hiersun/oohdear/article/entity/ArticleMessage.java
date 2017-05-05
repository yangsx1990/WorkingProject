package com.hiersun.oohdear.article.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "article_message")
public class ArticleMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 文章id
     */
    @Column(name = "articleId")
    private Long articleid;

    /**
     * 会员编号
     */
    @Column(name = "member_no")
    private String memberNo;

    /**
     * 删除状态
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private Date created;


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取留言内容
     *
     * @return content - 留言内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置留言内容
     *
     * @param content 留言内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取文章id
     *
     * @return articleId - 文章id
     */
    public Long getArticleid() {
        return articleid;
    }

    /**
     * 设置文章id
     *
     * @param articleid 文章id
     */
    public void setArticleid(Long articleid) {
        this.articleid = articleid;
    }

    /**
     * 获取会员编号
     *
     * @return member_no - 会员编号
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * 设置会员编号
     *
     * @param memberNo 会员编号
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * 获取删除状态
     *
     * @return deleted - 删除状态
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置删除状态
     *
     * @param deleted 删除状态
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}