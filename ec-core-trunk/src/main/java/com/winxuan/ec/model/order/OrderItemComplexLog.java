/*
 * @(#)OrderItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.product.ProductSale;

/**
 * 套装书销售日志
 * 
 * @author yangxinyi
 * @version 1.0,2014-3-4
 */
@Entity
@Table(name="order_item_complex_log")
public class OrderItemComplexLog implements Serializable{

	private static final long serialVersionUID = -3802886383866201612L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_order")
	private Order order;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productsale")
	private ProductSale productSale;

	@Column(name="listprice")
	private BigDecimal listPrice;
    
    @Column(name="saleprice")
	private BigDecimal salePrice;
    
    @Column(name="purchasequantity")
	private int purchaseQuantity;
	
    @Column(name="createtime")
    private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public int getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(int purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

}
