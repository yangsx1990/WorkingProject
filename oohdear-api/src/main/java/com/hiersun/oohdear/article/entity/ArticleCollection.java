package com.hiersun.oohdear.article.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "article_collection")
public class ArticleCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_no")
    private String memberNo;

    /**
     * 文章id
     */
    @Column(name = "articleId")
    private Long articleid;

    @Column(name = "deleted_status")
    private Boolean deletedStatus;

    private Date created;

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
     * @return member_no
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * @param memberNo
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
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
     * @return deleted_status
     */
    public Boolean getDeletedStatus() {
        return deletedStatus;
    }

    /**
     * @param deletedStatus
     */
    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    /**
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(Date created) {
        this.created = created;
    }
}