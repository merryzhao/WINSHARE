/*
 * @(#)ReturnOrderItem.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.returnorder;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderItem;

/**
 * 退换货订单商品项
 * @author  liuan
 * @version 1.0,2011-9-13
 */
@Entity
@Table(name = "returnorder_item")
public class ReturnOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnorder")
	private ReturnOrder returnOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderitem")
	private OrderItem orderItem;
	
	@Column(name="appquantity")
	private int appQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason")
	private Code reason;
	
	@Column
	private int realQuantity;
	
	@Column
	private String remark;
	
	@Column(name="url")
	private String urlPath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public int getAppQuantity() {
		return appQuantity;
	}

	public void setAppQuantity(int appQuantity) {
		this.appQuantity = appQuantity;
	}

	public int getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(int realQuantity) {
		this.realQuantity = realQuantity;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Code getReason() {
		return reason;
	}

	public void setReason(Code reason) {
		this.reason = reason;
	}
		

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	/**
	 * 申请的退货总实洋
	 * @return
	 */
	public BigDecimal getDesireTotalSalePrice(){
		BigDecimal salePrice = this.orderItem.getSalePrice();
		return calculate(salePrice,appQuantity);
	}
	
	/**
	 *申请的退货总码洋
	 * @return
	 */
	public BigDecimal getDesireTotalListPrice(){
		BigDecimal listPrice = this.orderItem.getListPrice();
		return calculate(listPrice,appQuantity);
	}
	
	/**
	 * 实际退货单项的总实洋
	 * @return
	 */
	public BigDecimal getTotalSalePrice(){
		BigDecimal salePrice = this.orderItem.getSalePrice();
		return calculate(salePrice,realQuantity);
	}
	
	/**
	 *  实际退货单项的总码洋
	 * @return
	 */
	public BigDecimal getTotalListPrice(){
		BigDecimal listPrice = this.orderItem.getListPrice();
		return calculate(listPrice,realQuantity);
	}
	
	private BigDecimal calculate(BigDecimal price,int quantity){
		return price.multiply(new BigDecimal(quantity)).setScale(2);

	}
	
	

}
