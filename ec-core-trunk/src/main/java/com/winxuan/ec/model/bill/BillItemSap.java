/*
 * @(#)BillItemSap.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bill;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-11-10
 */
public class BillItemSap implements Serializable{
		
	private static final long serialVersionUID = -8546319966570610437L;
	
	private String outerOrder;
	private Long outerItem;
	private Long bill;
	private String returnFlag;
	private Long customer;
	private String sapid;
	private int quantity;
	private String order;
	private Long orderItem;
	private Long channel;
	private int deliveryQuantity;
	private int settle;
	private int historySettle;
	private BigDecimal listPrice;
	
	public String getOuterOrder() {
		return outerOrder;
	}
	public void setOuterOrder(String outerOrder) {
		this.outerOrder = outerOrder;
	}
	public Long getOuterItem() {
		return outerItem;
	}
	public void setOuterItem(Long outerItem) {
		this.outerItem = outerItem;
	}
	public Long getBill() {
		return bill;
	}
	public void setBill(Long bill) {
		this.bill = bill;
	}
	public String getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}
	public Long getCustomer() {
		return customer;
	}
	public void setCustomer(Long customer) {
		this.customer = customer;
	}
	public String getSapid() {
		return sapid;
	}
	public void setSapid(String sapid) {
		this.sapid = sapid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Long getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(Long orderItem) {
		this.orderItem = orderItem;
	}
	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	public int getSettle() {
		return settle;
	}
	public void setSettle(int settle) {
		this.settle = settle;
	}
	public int getHistorySettle() {
		return historySettle;
	}
	public void setHistorySettle(int historySettle) {
		this.historySettle = historySettle;
	}
	public BigDecimal getListPrice() {
		return listPrice;
	}
	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}
	
}
