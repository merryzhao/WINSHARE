/*
 * @(#)CustomerCommentRank.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

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

import com.winxuan.ec.model.user.Customer;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-30
 */
@Entity
@Table(name = "customer_comment_rank")
public class CustomerCommentRank implements Serializable{

	private static final long serialVersionUID = -2314628277569679053L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment")
	private CustomerComment comment;

	@Column(name = "ranktime")
	private Date rankTime;

	@Column
	private boolean useful;

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

	public CustomerComment getComment() {
		return comment;
	}

	public void setComment(CustomerComment comment) {
		this.comment = comment;
	}

	public Date getRankTime() {
		return rankTime;
	}

	public void setRankTime(Date rankTime) {
		this.rankTime = rankTime;
	}

	public boolean isUseful() {
		return useful;
	}

	public void setUseful(boolean useful) {
		this.useful = useful;
	}
}
