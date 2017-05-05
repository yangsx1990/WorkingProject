package com.hiersun.oohdear.vo;


public class ArticleInfoVo {
    /**
     * 表Id
     */

    private Long id;

    /**
     * 文章名称
     */
    private String title;

    /**
     * 会员昵称
     * 
     */
    private String nickname;
    /**
     * 文章描述
     */
    private String articleDesc;
    /**
     * 会员头像
     * 
     */
    private String avatar;
    /**
     * 猜你喜欢
     */
   private String articleInterest;
    /**
     * 创建时间
     */
    private String publishTime;

    /**
     * 文章标签
     */
    private Integer labelId;
    /**
     * 文章标签显示内容
     */
    private String labels;

    private String content;
    /**
     * 首图
     */
    private String cover;

    /**
     * 文章内容
     */
    private String articleInfo;
	/**
	 * 留言数
	 */
	private int messageNum;
	/**
	 * 点赞数
	 */
	private int interestedNum;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public String getArticleInterest() {
		return articleInterest;
	}
	public void setArticleInterest(String articleInterest) {
		this.articleInterest = articleInterest;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickName() {
		return nickname;
	}
	public void setNickName(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	public Integer getLabelId() {
		return labelId;
	}
	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public int getMessageNum() {
		return messageNum;
	}
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	public int getInterestedNum() {
		return interestedNum;
	}
	public void setInterestedNum(int interestedNum) {
		this.interestedNum = interestedNum;
	}
	public String getArticleInfo() {
		return articleInfo;
	}
	public void setArticleInfo(String articleInfo) {
		this.articleInfo = articleInfo;
	}
	public String getArticleDesc() {
		return articleDesc;
	}
	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}
	
        
   
}