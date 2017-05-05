package com.hiersun.oohdear.user.entity;

import java.util.Date;

import javax.persistence.*;

@Table(name = "user_search")
public class UserSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "search_content")
    private String searchContent;

    @Column(name = "search_count")
    private Integer searchCount;

    @Column(name = "member_no")
    private String memberNo;

    @Column(name = "last_modified")
    private Date lastModified;
    
    private Boolean deleted;

    public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
     * @return search_content
     */
    public String getSearchContent() {
        return searchContent;
    }

    /**
     * @param searchContent
     */
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    /**
     * @return search_count
     */
    public Integer getSearchCount() {
        return searchCount;
    }

    /**
     * @param searchCount
     */
    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
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
     * @return last_modified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}