package com.winxuan.ec.model.settle;

import java.math.BigDecimal;

import org.apache.commons.lang.xwork.builder.ReflectionToStringBuilder;

/**
 * @description: TODO
 * 
 * @createtime: 2014-2-10 下午1:51:23
 * 
 * @author zenghua
 * 
 * @version 1.0
 */

public class SapOrderItem {
	private String outerOrder;
	private Long outerItem;
	
	private Long bill;
	
	private String returnFlag;
	private Long customer;
	private String sapid;
	private int quantity;
	
	private String order;
	private String dc;
	
	private Long orderItem;
	private Long channel;
	private int deliveryQuantity;
	
	private int settle;
	private boolean complex;
	
	// private int historySettle;
	 

	private BigDecimal listPrice;

	public boolean isComplex() {
		return complex;
	}

	public void setComplex(boolean complex) {
		this.complex = complex;
	}

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

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public Long getBill() {
		return bill;
	}

	public void setBill(Long bill) {
		this.bill = bill;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}
	
}
