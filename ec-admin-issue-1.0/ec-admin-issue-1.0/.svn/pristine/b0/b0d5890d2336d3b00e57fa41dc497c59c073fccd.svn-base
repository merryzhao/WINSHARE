/*
 * @(#)
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.present;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * description
 * @author liyang
 * @version 1.0,2011-8-31
 */

public class PresentBatchSearchForm {

	private String id;
	private BigDecimal value;
	private String batchTitle;
	private String createUser;
	// 状态
	private Long[] batchState;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public Long[] getBatchState() {
		return batchState;
	}

	public void setBatchState(Long[] batchState) {
		this.batchState = batchState;
	}
	

	/**
	 * @return the batchTitle
	 */
	public String getBatchTitle() {
		if(StringUtils.isBlank(batchTitle)){
			return null;
		}
		return "%"+batchTitle.trim()+"%";
	}
	/**
	 * @param batchTitle the batchTitle to set
	 */
	public void setBatchTitle(String batchTitle) {
		this.batchTitle = batchTitle;
	}
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		if("".equals(createUser)){
			return null;
		}
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	
	
}
