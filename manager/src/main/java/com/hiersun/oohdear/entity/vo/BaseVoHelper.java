package com.hiersun.oohdear.entity.vo;/**
 * Created by liubaocheng on 2017/3/9.
 */

import com.hiersun.oohdear.entity.PageParam;

/**
 * Description:基础页面查询辅助类
 * Author: liubaocheng
 * Create: 2017-03-09 12:06
 **/
public class BaseVoHelper extends PageParam{

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
