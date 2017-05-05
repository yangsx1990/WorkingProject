package com.hiersun.oohdear.vo;

import java.util.List;

import com.hiersun.oohdear.article.entity.ArticleInfo;


/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-28 21:53
 **/
public class ArticleDetail {
    /**
     * 表Id
     */
    private Long id;

    /**
     * 文章名称
     */
    private String articleName;

    /**
     * 文章描述
     */
    private String articleDesc;

    /**
     * 会员Id
     */
    private String memberNo;
    /**
     * 昵称，显示名称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 逻辑删除状态
     */
    private Boolean deleteStatus;

    /**
     * 猜你喜欢
     */
    private List<ArticleInfo> articles;
    /**
     * 创建时间
     */
    private String  created;

    /**
     * 文章标签
     */
    private String articleLabel;
    /**
     * 文章标签id
     */
    private Integer labelId;
    /**
     * 点赞数
     */
    private Integer number;

    /**
     * 首图
     */
    private String picture;

    /**
     * 是否置顶
     */
    private Boolean topStatus;
    /**
     * 是否推荐
     */
    private Boolean recommendStatus;
    /**
     * 文章内容
     */
    private String articleInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

  

	public List<ArticleInfo> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleInfo> articles) {
		this.articles = articles;
	}

	public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getTopStatus() {
        return topStatus;
    }

    public void setTopStatus(Boolean topStatus) {
        this.topStatus = topStatus;
    }

    public Boolean getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Boolean recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public String getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(String articleInfo) {
        this.articleInfo = articleInfo;
    }
}
