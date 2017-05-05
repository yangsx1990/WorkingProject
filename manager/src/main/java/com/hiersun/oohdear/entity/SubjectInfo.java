package com.hiersun.oohdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "subject_info")
public class SubjectInfo {
    /**
     * 表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 专题标题
     */
    private String title;

    /**
     * 专题链接
     */
    private String link;

    /**
     * 专题发布时间
     */
    private Date created;

    /**
     * 是否显示
     */
    @Column(name="show_status")
    private Boolean showStatus;

    /**
     * 置顶值
     */
    @Column(name = "top_value")
    private Integer topValue;

    /**
     * 发布人id
     */
    @Column(name = "sysuserId")
    private Integer sysuserid;

    /**
     * 专题头图
     */
    private String picture;

    /**
     * 获取表id
     *
     * @return id - 表id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置表id
     *
     * @param id 表id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取专题标题
     *
     * @return title - 专题标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置专题标题
     *
     * @param title 专题标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取专题链接
     *
     * @return link - 专题链接
     */
    public String getLink() {
        return link;
    }

    /**
     * 设置专题链接
     *
     * @param link 专题链接
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 获取专题发布时间
     *
     * @return created - 专题发布时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置专题发布时间
     *
     * @param created 专题发布时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

   
  

	public Boolean getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Boolean showStatus) {
		this.showStatus = showStatus;
	}

	/**
     * 获取置顶值
     *
     * @return top_value - 置顶值
     */
    public Integer getTopValue() {
        return topValue;
    }

    /**
     * 设置置顶值
     *
     * @param topValue 置顶值
     */
    public void setTopValue(Integer topValue) {
        this.topValue = topValue;
    }

    /**
     * 获取发布人id
     *
     * @return sysuserId - 发布人id
     */
    public Integer getSysuserid() {
        return sysuserid;
    }

    /**
     * 设置发布人id
     *
     * @param sysuserid 发布人id
     */
    public void setSysuserid(Integer sysuserid) {
        this.sysuserid = sysuserid;
    }

    /**
     * 获取专题头图
     *
     * @return picture - 专题头图
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置专题头图
     *
     * @param picture 专题头图
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
}