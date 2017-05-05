package com.hiersun.oohdear.entity.vo;/**
 * Created by liubaocheng on 2017/3/9.
 */

/**
 * Description:内容/文章管理查询辅助类
 * Author: liubaocheng
 * Create: 2017-03-09 15:44
 **/
public class ArticleInfoVo extends BaseVoHelper {

    private String articleName;

    private String articleLabel;

    private String labelId;

//    private String number;
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleLabel() {
        return articleLabel;
    }

    public void setArticleLabel(String articleLabel) {
        this.articleLabel = articleLabel;
    }
}
