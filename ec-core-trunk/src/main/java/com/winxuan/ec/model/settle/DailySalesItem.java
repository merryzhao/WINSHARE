package com.winxuan.ec.model.settle;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 每日销售项
 * @author wenchx
 * @version 1.0, 2014-8-1 下午3:32:06
 */
public class DailySalesItem {
	private String outerOrder;
	private Long outerItem;
	private String warehouse;
	private String returnFlag;
	private Long customer;
	private String sapid;
	private int quantity;
	private BigDecimal listPrice;
	private Date deliverytime;
	private String order;
	private Long orderItem;
	private Long channel;
	private boolean complex;
	
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
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
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
	public BigDecimal getListPrice() {
		return listPrice;
	}
	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}
	public Date getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(Date deliverytime) {
		this.deliverytime = deliverytime;
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

}
