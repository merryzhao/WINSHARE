/*
 * @(#)CustomerComment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.model.user.Customer;
/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-30
 */
@Entity
@Table(name = "customer_comment")
public class CustomerComment implements Serializable, Cloneable{

	private static final long serialVersionUID = -1836447500014618133L;

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

	@Column(name = "commenttime")
	private Date commentTime;

	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "rank")
	private ProductSaleRank rank;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@Column(name = "nick_name")
	private String nickName;
	
	@Column(name = "channel_comment_id")
	private Long channelCommentId = new Long(0);
	
	@Column(name = "usefulcount")
	private int usefulCount;

	@Column(name = "uselesscount")
	private int uselessCount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "comment", targetEntity = CustomerCommentReply.class)
	private Set<CustomerCommentReply> replyList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "comment", targetEntity = CustomerCommentRank.class)
	private Set<CustomerCommentRank> rankList;
	
	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "quotecomment")
	private CustomerComment quoteComment;
	
	
	public CustomerComment getQuoteComment() {
		return quoteComment;
	}
	
	public List<CustomerComment> getQuoteCommentList(CustomerComment quoteCustomerComment) throws CloneNotSupportedException{
		List<CustomerComment> customerCommentList = new ArrayList<CustomerComment>();
		customerCommentList.add(this);
		if(quoteCustomerComment != null){
			customerCommentList.add(quoteCustomerComment);
		}
		return customerCommentList;
	}
	
	public void setQuoteComment(CustomerComment quoteComment) {
		this.quoteComment = quoteComment;
	}

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getChannelCommentId() {
		return channelCommentId;
	}

	public void setChannelCommentId(Long channelCommentId) {
		this.channelCommentId = channelCommentId;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
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

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public ProductSaleRank getRank() {
		return rank;
	}

	public void setRank(ProductSaleRank rank) {
		this.rank = rank;
	}

	public int getUsefulCount() {
		return usefulCount;
	}

	public void setUsefulCount(int usefulCount) {
		this.usefulCount = usefulCount;
	}

	public int getUselessCount() {
		return uselessCount;
	}

	public void setUselessCount(int uselessCount) {
		this.uselessCount = uselessCount;
	}
	

	public Set<CustomerCommentRank> getRankList() {
		return rankList;
	}

	public void setRankList(Set<CustomerCommentRank> rankList) {
		this.rankList = rankList;
	}

	public Set<CustomerCommentReply> getReplyList() {
		return replyList;
	}

	public void setReplyList(Set<CustomerCommentReply> replyList) {
		this.replyList = replyList;
	}
	
	public void addCommentRank(boolean useful,Customer customer){
		CustomerCommentRank customerCommentRank = new CustomerCommentRank();
		customerCommentRank.setUseful(useful);
		customerCommentRank.setRankTime(new Date());
		customerCommentRank.setComment(this);
		customerCommentRank.setCustomer(customer);
		if(rankList == null){
			rankList = new HashSet<CustomerCommentRank>();
		}
		rankList.add(customerCommentRank);
	}
	
	
		
	
	public CustomerComment filterFromCustomer(){
		if(CollectionUtils.isEmpty(this.replyList)){
			return this;
		}
		CustomerComment cc = this;
		Set<CustomerCommentReply> replyList = new HashSet<CustomerCommentReply>();
		for(CustomerCommentReply ccr : this.replyList){
			if(ccr.getReplier().getSource().getId().equals(Code.USER_SOURCE_EC_CONSOLE)){
				replyList.add(ccr);
			}
		}
		cc.setReplyList(replyList);
		return cc;
	}
}
