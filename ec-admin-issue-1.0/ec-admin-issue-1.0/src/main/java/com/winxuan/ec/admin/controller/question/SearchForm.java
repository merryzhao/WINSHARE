package com.winxuan.ec.admin.controller.question;

import java.util.Date;


/**
 * 商品查询参数
 * 
 * @author chenlong
 * @version 1.0,2011-10-24
 */
public class SearchForm {
	
	
	private Long productSaleId;

	private String customerName;

	private String productName;

	private Date startSubmitTime;

	private Date endSubmitTime;
	
	private Date replyTimeBegin;
	
	private Date replyTimeEnd;
	
	private Long shopId;
	
	private String replierName;
	
	private Boolean reply;

	public Long getProductSaleId() {
		return productSaleId;
	}

	public void setProductSaleId(Long productSaleId) {
		this.productSaleId = productSaleId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getStartSubmitTime() {
		return startSubmitTime;
	}

	public void setStartSubmitTime(Date startSubmitTime) {
		this.startSubmitTime = startSubmitTime;
	}

	public Date getEndSubmitTime() {
		return endSubmitTime;
	}

	public void setEndSubmitTime(Date endSubmitTime) {
		this.endSubmitTime = endSubmitTime;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getReplierName() {
		return replierName;
	}

	public void setReplierName(String replierName) {
		this.replierName = replierName;
	}

	public Boolean getReply() {
		return reply;
	}

	public void setReply(Boolean reply) {
		this.reply = reply;
	}

	public Date getReplyTimeBegin() {
		return replyTimeBegin;
	}

	public void setReplyTimeBegin(Date replyTimeBegin) {
		this.replyTimeBegin = replyTimeBegin;
	}

	public Date getReplyTimeEnd() {
		return replyTimeEnd;
	}

	public void setReplyTimeEnd(Date replyTimeEnd) {
		this.replyTimeEnd = replyTimeEnd;
	}
	
	
}
