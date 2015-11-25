/*
 * @(#)CustomerExtension.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-27
 */
@Entity
@Table(name = "customer_extension")
public class CustomerExtension implements Serializable {

	private static final long serialVersionUID = 6530247187272137653L;

	@Id
	@Column(name = "customer")
	@GeneratedValue(generator = "customerFK")
	@GenericGenerator(name = "customerFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "customer"))
	private Long id;

	@OneToOne(mappedBy = "extension", targetEntity = Customer.class,fetch=FetchType.LAZY)
	private Customer customer;

	@Column(name = "livingstatus")
	private String livingStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "careertype")
	private Code careerType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "career")
	private Code career;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salary")
	private Code salary;

	@Column
	private String interest;

	@Column
	private String favorite;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getLivingStatus() {
		return livingStatus;
	}

	public void setLivingStatus(String livingStatus) {
		this.livingStatus = livingStatus;
	}

	public Code getCareerType() {
		return careerType;
	}

	public void setCareerType(Code careerType) {
		this.careerType = careerType;
	}

	public Code getCareer() {
		return career;
	}

	public void setCareer(Code career) {
		this.career = career;
	}

	public Code getSalary() {
		return salary;
	}

	public void setSalary(Code salary) {
		this.salary = salary;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
}
