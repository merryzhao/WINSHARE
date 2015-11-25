/*
 * @(#)MrDeliveryRecordTemp.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.replenishment;

import java.util.Date;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-11
 */
public class MrDeliveryRecordTemp {
	private Long id;
	private Long productSale;
	private Long dc;
	private Long channel;
	private Date deliveryDate;
	private int deliveryQuantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductSale() {
		return productSale;
	}
	public void setProductSale(Long productSale) {
		this.productSale = productSale;
	}
	public Long getDc() {
		return dc;
	}
	public void setDc(Long dc) {
		this.dc = dc;
	}
	public Long getChannel() {
		return channel;
	}
	public void setChannel(Long channel) {
		this.channel = channel;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	
}
