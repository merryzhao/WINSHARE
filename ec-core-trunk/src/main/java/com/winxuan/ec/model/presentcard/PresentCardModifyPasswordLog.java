/*
 * @(#)PresentCardModifyPasswordLog.java
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

import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-29
 */
@Entity
@Table(name = "presentcard_modifypassword_log")
public class PresentCardModifyPasswordLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963529108412462638L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User operator;
	
	@Column(name = "oldpassword")
	private byte[] oldPassword;
	
	@Column(name = "newpassword")
	private byte[] newPassword;
	
	@Column(name = "createdate")
	private Date createDate;
	
	@Column(name = "verifycode")
	private String verifyCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "presentcard")
	private PresentCard presentCard;

	public PresentCard getPresentCard() {
		return presentCard;
	}

	public void setPresentCard(PresentCard presentCard) {
		this.presentCard = presentCard;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public byte[] getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(byte[] oldPassword) {
		this.oldPassword = oldPassword;
	}

	public byte[] getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(byte[] newPassword) {
		this.newPassword = newPassword;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}