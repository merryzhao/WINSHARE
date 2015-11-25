/*
 * @(#)CustomerQuestion.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-30
 */
@Entity
@Table(name = "customer_question")
public class CustomerQuestion implements Serializable, Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@Column
	private String title;

	@Column
	private String content;

	@Column(name = "asktime")
	private Date askTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop")
	private Shop shop;
	
	@Column(name = "reply")
	private boolean reply;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question", targetEntity = CustomerQuestionReply.class)
	@OrderBy("id")
	private Set<CustomerQuestionReply> replyList;

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

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAskTime() {
		return askTime;
	}

	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}

	public Set<CustomerQuestionReply> getReplyList(){
		return this.replyList;
	}

	public void setReplyList(Set<CustomerQuestionReply> replyList) {
		this.replyList = replyList;
	}
	public CustomerQuestionReply getDefaultReply(){
		for(Iterator<CustomerQuestionReply> iterator = replyList.iterator();iterator.hasNext();){
			CustomerQuestionReply customerQuestionReply =  iterator.next();
			return customerQuestionReply;
		}
		return null;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public boolean isReply() {
		return reply;
	}

	public void setReply(boolean reply) {
		this.reply = reply;
	}
}
