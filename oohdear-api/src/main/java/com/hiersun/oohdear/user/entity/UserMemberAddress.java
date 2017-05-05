package com.hiersun.oohdear.user.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_member_address")
public class UserMemberAddress {
    /**
     * 表Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 用户编号
     */
    @Column(name = "member_no")
    private String memberNo;

    /**
     * 省/市
     */
    private String zone;

    /**
     * 是否默认地址
     */
    @Column(name = "default_address")
    private Boolean defaultAddress;

    /**
     * 创建时间
     */
    private Date created;

    private String consignee;

    @Column(name = "consignee_mobile")
    private String consigneeMobile;

    private Boolean deleted;

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

   


	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	/**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取用户编号
     *
     * @return memeber_no - 用户编号
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * 设置用户编号
     *
     * @param memeberNo 用户编号
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * 获取是否默认地址
     *
     * @return default_address - 是否默认地址
     */
    public Boolean getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * 设置是否默认地址
     *
     * @param defaultAddress 是否默认地址
     */
    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * @param consignee
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * @return consignee_mobile
     */
    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    /**
     * @param consigneeMobile
     */
    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    /**
     * @return deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}