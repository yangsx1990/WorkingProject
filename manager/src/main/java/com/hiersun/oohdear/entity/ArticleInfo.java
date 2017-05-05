package com.hiersun.oohdear.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import javax.persistence.*;

@Table(name = "article_info")
@Document(indexName = "article", type = "article", shards = 3, replicas = 0, refreshInterval = "-1")
public class ArticleInfo {
    /**
     * 表Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 文章名称
     */
    @Column(name = "article_name")
    @Field(type= FieldType.String, index = FieldIndex.analyzed)
    private String articleName;

    /**
     * 文章描述
     */
    @Column(name = "article_desc")
    private String articleDesc;

    /**
     * 会员Id
     */
    @Column(name = "member_no")
    private String memberNo;

    /**
     * 逻辑删除状态
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 文章标签
     */
    @Column(name = "label_id")
    private Integer labelId;

    /**
     * 首图
     */
    private String picture;

    /**
     * 是否置顶
     */
    @Column(name = "top_status")
    private Integer topStatus;

    @Column(name = "article_label")
    private String articleLabel;

    /**
     * 文章内容
     */
    @Column(name = "article_info")
    @Lob
    @Field(type= FieldType.String, index = FieldIndex.analyzed)
    private String articleInfo;

    /**
     * 点赞数量
     */
    private Integer number;

    /**
     * 是否推荐，是，则在M站推荐文章中显示
     */
    @Column(name = "recommend_status")
    private Boolean recommendStatus;

    /**
     * 发布人昵称
     */
    @Column(name = "nickname")
    private String nickName;
    /**
     * 猜你喜欢(文章id，用逗号间隔）
     */
    @Column(name = "article_interest")
    private String articleInterest;
    /**
     * 发布人头像d地址
     */
    private String avatar;


    public Boolean getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Boolean recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getArticleInterest() {
        return articleInterest;
    }

    public void setArticleInterest(String articleInterest) {
        this.articleInterest = articleInterest;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
     * 获取表Id
     *
     * @return id - 表Id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置表Id
     *
     * @param id 表Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取文章名称
     *
     * @return article_name - 文章名称
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * 设置文章名称
     *
     * @param articleName 文章名称
     */
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    /**
     * 获取文章描述
     *
     * @return article_desc - 文章描述
     */
    public String getArticleDesc() {
        return articleDesc;
    }

    /**
     * 设置文章描述
     *
     * @param articleDesc 文章描述
     */
    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    /**
     * 获取会员Id
     *
     * @return member_no - 会员Id
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * 设置会员Id
     *
     * @param memberNo 会员Id
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * 获取逻辑删除状态
     *
     * @return delete_status - 逻辑删除状态
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置逻辑删除状态
     *
     * @param deleteStatus 逻辑删除状态
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取文章标签
     *
     * @return label_id - 文章标签
     */
    public Integer getLabelId() {
        return labelId;
    }

    /**
     * 设置文章标签
     *
     * @param labelId 文章标签
     */
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    /**
     * 获取首图
     *
     * @return picture - 首图
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置首图
     *
     * @param picture 首图
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取是否置顶
     *
     * @return top_status - 是否置顶
     */
    public Integer getTopStatus() {
        return topStatus;
    }

    /**
     * 设置是否置顶
     *
     * @param topStatus 是否置顶
     */
    public void setTopStatus(Integer topStatus) {
        this.topStatus = topStatus;
    }

    /**
     * @return article_label
     */
    public String getArticleLabel() {
        return articleLabel;
    }

    /**
     * @param articleLabel
     */
    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    /**
     * 获取文章内容
     *
     * @return article_info - 文章内容
     */
    public String getArticleInfo() {
        return articleInfo;
    }

    /**
     * 设置文章内容
     *
     * @param articleInfo 文章内容
     */
    public void setArticleInfo(String articleInfo) {
        this.articleInfo = articleInfo;
    }
}