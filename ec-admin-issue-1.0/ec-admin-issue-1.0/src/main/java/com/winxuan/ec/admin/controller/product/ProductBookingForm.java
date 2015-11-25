package com.winxuan.ec.admin.controller.product;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * 
 * @author HideHai
 * @version 0.1 ,2012-7-19
 **/
public class ProductBookingForm {
	
	private Integer saleId;
	private Integer stockQuantity;
	private Integer ignore = MagicNumber.ZERO;
	private String bookStartDate;
	private String bookEndDate;
	private String saleStatus;
	private String bookDescription;
	private Long dc;
	
	public Long getDc() {
		return dc;
	}
	public void setDc(Long dc) {
		this.dc = dc;
	}
	public Integer getSaleId() {
		return saleId;
	}
	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public Integer getIgnore() {
		return ignore;
	}
	public void setIgnore(Integer ignore) {
		this.ignore = ignore;
	}
	public String getBookStartDate() {
		return bookStartDate;
	}
	public void setBookStartDate(String bookStartDate) {
		this.bookStartDate = bookStartDate;
	}
	public String getBookEndDate() {
		return bookEndDate;
	}
	public void setBookEndDate(String bookEndDate) {
		this.bookEndDate = bookEndDate;
	}
	public String getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getBookDescription() {
		return bookDescription;
	}
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	
	
}

