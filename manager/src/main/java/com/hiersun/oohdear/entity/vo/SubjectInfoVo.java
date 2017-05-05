package com.hiersun.oohdear.entity.vo;/**
 * Created by liubaocheng on 2017/3/9.
 */

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-09 15:17
 **/
public class SubjectInfoVo extends BaseVoHelper {

    /**
     * 标题
     */
    private String title;

     /**
     * 逻辑删除状态
     */
    private Boolean showStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public Boolean getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Boolean showStatus) {
		this.showStatus = showStatus;
	}

  
}
