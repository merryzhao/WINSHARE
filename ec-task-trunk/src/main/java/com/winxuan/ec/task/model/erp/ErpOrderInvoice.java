/*
 * @(#)ErpOrderInvoice.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.task.model.erp;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-8-8
 */
public class ErpOrderInvoice implements Serializable {
	private static final long serialVersionUID = 7993413522197608743L;

	private String id;

	private String deliveryCode;

	private String deliveryCompany;

	private Date deliveryTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public Integer getDeliveryCompanyId() {
		if (StringUtils.isNotBlank(this.deliveryCompany)) {
			return Integer.parseInt(this.deliveryCompany.trim());
		}
		return null;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}
