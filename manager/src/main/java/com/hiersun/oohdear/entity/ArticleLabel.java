package com.hiersun.oohdear.entity;

import javax.persistence.*;

@Table(name = "article_label")
public class ArticleLabel {
    /**
     * 表Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签名称
     */
    @Column(name = "label_content")
    private String labelContent;

    /**
     * 获取表Id
     *
     * @return id - 表Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置表Id
     *
     * @param id 表Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取标签名称
     *
     * @return label_content - 标签名称
     */
    public String getLabelContent() {
        return labelContent;
    }

    /**
     * 设置标签名称
     *
     * @param labelContent 标签名称
     */
    public void setLabelContent(String labelContent) {
        this.labelContent = labelContent;
    }
}