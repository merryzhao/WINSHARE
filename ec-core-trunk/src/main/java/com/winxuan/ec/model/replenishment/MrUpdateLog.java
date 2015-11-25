/*
 * @(#)MrUpdateLog.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.replenishment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.log.AutoLog;
import com.winxuan.framework.validator.Principal;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-4
 */
@Entity
@Table(name = "mr_update_log")
public class MrUpdateLog implements Serializable, AutoLog{

	private static final long serialVersionUID = 690299367028404822L;

	@Id
	@GeneratedValue
	private Long id;

	private String fieldName;

	private String originalValue;

	private String changedValue;

	private Date updateTime;

	private String targetId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getOriginalValue() {
		return originalValue;
	}


	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}


	public String getChangedValue() {
		return changedValue;
	}


	public void setChangedValue(String changedValue) {
		this.changedValue = changedValue;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getTargetId() {
		return targetId;
	}


	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setOperator(Principal operator) {
		this.user = (User) operator;
	}
	
}
