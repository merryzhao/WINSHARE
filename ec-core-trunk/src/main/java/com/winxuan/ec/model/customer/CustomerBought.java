/*
 * @(#)CustomerBought.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

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
import javax.persistence.Transient;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;

/**
 * 用户已购商品
 * 
 * @author liuan
 * @version 1.0,2011-9-26
 */
@Entity
@Table(name = "customer_bought")
public class CustomerBought implements Serializable{

	private static final long serialVersionUID = 6174242732020103722L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;

	@Column
	private int quantity;

	@Column(name = "buytime")
	private Date buyTime;

	@Column(name = "buyprice")
	private BigDecimal buyPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sort")
	private Code sort;
	
	/**
	 * 是否进行过评论
	 */
	@Transient
	private boolean hasComment;
	
	public CustomerBought(){
		
	}
	
	public CustomerBought(OrderItem orderItem) {
		this.customer = orderItem.getOrder().getCustomer();
		ProductSale productSale = orderItem.getProductSale();
		this.productSale = productSale;
		this.sort = productSale.getProduct().getSort();
		update(orderItem);
	}

	public void update(OrderItem orderItem) {
		this.order = orderItem.getOrder();
		this.quantity = orderItem.getPurchaseQuantity();
		this.buyPrice = orderItem.getSalePrice();
		this.buyTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Code getSort() {
		return sort;
	}

	public void setSort(Code sort) {
		this.sort = sort;
	}

	public boolean isHasComment() {
		return hasComment;
	}

	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}
	
	
}
