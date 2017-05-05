package com.hiersun.oohdear.vo;


public class SubjectInfoVo {
    /**
     * 表id
     */

    private Long id;

    /**
     * 专题标题
     */
    private String title;
 

    /**
     * 专题头图
     */
    private String cover;
/**
 * 链接
 */
    private String link;
    
	public String getLink() {
	return link;
}


public void setLink(String link) {
	this.link = link;
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


	public String getCover() {
		return cover;
	}


	public void setCover(String cover) {
		this.cover = cover;
	}

  }