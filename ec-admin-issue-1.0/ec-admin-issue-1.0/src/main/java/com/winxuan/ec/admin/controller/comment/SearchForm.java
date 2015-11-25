package com.winxuan.ec.admin.controller.comment;

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
	
	private String content;

	private String productName;

	private Date commentTimeStart;

	private Date commentTimeEnd;
	
	private Long channel;

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

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

	public Date getCommentTimeStart() {
		return commentTimeStart;
	}

	public void setCommentTimeStart(Date commentTimeStart) {
		this.commentTimeStart = commentTimeStart;
	}

	public Date getCommentTimeEnd() {
		return commentTimeEnd;
	}

	public void setCommentTimeEnd(Date commentTimeEnd) {
		this.commentTimeEnd = commentTimeEnd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
