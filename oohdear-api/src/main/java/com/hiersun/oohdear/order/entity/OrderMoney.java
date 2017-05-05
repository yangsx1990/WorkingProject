package com.hiersun.oohdear.order.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_money")
public class OrderMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品类别code
     */
    private String style;
    /**
     * 材质
     */
    private String material;
    /**
     * 尺寸
     */
    private String size;
    /**
     * 订制金额
     */
    private BigDecimal money;
    /**
     * 备注
     */
    private String comment;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

   
    
}