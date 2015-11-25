package com.winxuan.ec.task.model.ec.convert;

/**
 * @description: TODO
 * 
 * @createtime: 2013-12-9 上午11:45:50
 * 
 * @author zenghua
 * 
 * @version 1.0
 */

public class ChannelSalesInfo {
	private String outerOrder;
	private Long outeritem;
	private String order;
	private Long orderitem;
	private String type;
	private String returnflag;
	private String bill;
	private String operator;
	private String sapId;
	private int quantity;
	private String channel;
	private String enddate;
	private int deliveryquantity;
	private String settle;
	private String historysettle;
	private String listprice;
	private boolean complex;
	public String getOuterOrder() {
		return outerOrder;
	}
	public void setOuterOrder(String outerOrder) {
		this.outerOrder = outerOrder;
	}
 
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReturnflag() {
		return returnflag;
	}
	public void setReturnflag(String returnflag) {
		this.returnflag = returnflag;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSapId() {
		return sapId;
	}
	public void setSapId(String sapId) {
		this.sapId = sapId;
	}
 
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
 
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getDeliveryquantity() {
		return deliveryquantity;
	}
	public void setDeliveryquantity(int deliveryquantity) {
		this.deliveryquantity = deliveryquantity;
	}
	public String getSettle() {
		return settle;
	}
	public void setSettle(String settle) {
		this.settle = settle;
	}
	public String getHistorysettle() {
		return historysettle;
	}
	public void setHistorysettle(String historysettle) {
		this.historysettle = historysettle;
	}
	public String getListprice() {
		return listprice;
	}
	public void setListprice(String listprice) {
		this.listprice = listprice;
	}
	public boolean isComplex() {
		return complex;
	}
	public void setComplex(boolean complex) {
		this.complex = complex;
	}
	public Long getOrderitem() {
		return orderitem;
	}
	public void setOrderitem(Long orderitem) {
		this.orderitem = orderitem;
	}
	public void setOuteritem(Long outeritem) {
		this.outeritem = outeritem;
	}
	public Long getOuteritem() {
		return outeritem;
	}
}
