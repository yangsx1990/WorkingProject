package com.hiersun.oohdear.entity.vo;/**
 * Created by liubaocheng on 2017/3/7.
 */

/**
 * Description:用户会员信息页面查询辅助类
 * Author: liubaocheng
 * Create: 2017-03-07 20:58
 **/
public class UserMemberInfoVo extends BaseVoHelper{

    private String mobile;

    private String nickName;

    private String memberNo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

}
