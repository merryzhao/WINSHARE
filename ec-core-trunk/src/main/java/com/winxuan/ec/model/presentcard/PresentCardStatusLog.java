/*
 * @(#)PresentCardStatusLog.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.presentcard;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-29
 */
@Entity
@Table(name = "presentcard_status_log")
public class PresentCardStatusLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6124021107931634443L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "createdate")
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status")
	private Code status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User operator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "presentcard")
	private PresentCard presentCard;
	
	@Column(name = "verifyCode")
	private String verifyCode;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public PresentCard getPresentCard() {
		return presentCard;
	}

	public void setPresentCard(PresentCard presentCard) {
		this.presentCard = presentCard;
	}

}
