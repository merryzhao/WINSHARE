/*
 * @(#)CallBackForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.refund;

import java.util.Date;

import com.winxuan.ec.model.code.Code;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-9
 */
public class RefundCallBackForm {
	
	private String refundCredentialId;
	
	private Code status;
	
	private Date refundtime;
	
	private String outerId;

	
	
	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getRefundCredentialId() {
		return refundCredentialId;
	}

	public void setRefundCredentialId(String refundCredentialId) {
		this.refundCredentialId = refundCredentialId;
	}
	
}
