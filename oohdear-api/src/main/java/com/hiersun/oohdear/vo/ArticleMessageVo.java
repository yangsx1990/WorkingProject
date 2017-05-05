package com.hiersun.oohdear.vo;


public class ArticleMessageVo {
   
    private Long id;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 会员编号
     */
    private String memberNo;

    private String nickname;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 是否本人留言
     */
    private Boolean isOwnMessage;
    /**
     * 头像
     */
    private String avatar;
    
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Boolean getIsOwnMessage() {
		return isOwnMessage;
	}
	public void setIsOwnMessage(Boolean isOwnMessage) {
		this.isOwnMessage = isOwnMessage;
	}
	

}