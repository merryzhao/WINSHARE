/*
 * @(#)CustomerBankAccount.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-22
 */
@Embeddable
public class CustomerBankAccount implements Serializable {

	private static final long serialVersionUID = 8838808014805350894L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank")
	private Code bank;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankprovince")
	private Area bankProvince;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankcity")
	private Area bankCity;

	@Column(name = "bankaccountname")
	private String bankAccountName;

	@Column(name = "bankaccountcode")
	private String bankAccountCode;

	@Column(name = "bankaccountcontactphone")
	private String bankAccountContactPhone;

	public Code getBank() {
		return bank;
	}

	public void setBank(Code bank) {
		this.bank = bank;
	}

	public Area getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Area bankProvince) {
		this.bankProvince = bankProvince;
	}

	public Area getBankCity() {
		return bankCity;
	}

	public void setBankCity(Area bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getBankAccountContactPhone() {
		return bankAccountContactPhone;
	}

	public void setBankAccountContactPhone(String bankAccountContactPhone) {
		this.bankAccountContactPhone = bankAccountContactPhone;
	}

}
