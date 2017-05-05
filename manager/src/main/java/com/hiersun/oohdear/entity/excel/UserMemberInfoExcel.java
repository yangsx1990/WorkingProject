package com.hiersun.oohdear.entity.excel;/**
 * Created by liubaocheng on 2017/3/28.
 */

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-03-28 17:24
 **/
public class UserMemberInfoExcel {


    /**
     * 会员编号
     */
    private String memberNo;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private String created;


    /**
     * 手机号
     */
    private String mobile;

    /**
     * 是否可用 0是可用，1是不可用
     */
    private String deleted;

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
