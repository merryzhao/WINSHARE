/*
 * @(#)CustomerCommentReply.java
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

import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-30
 */
@Entity
@Table(name = "customer_comment_reply")
public class CustomerCommentReply implements Serializable{

	private static final long serialVersionUID = 8076489931115857953L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment")
	private CustomerComment comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "replier")
	private User replier;

	@Column
	private String content;

	@Column(name = "replytime")
	private Date replyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getReplier() {
		return replier;
	}

	public void setReplier(User replier) {
		this.replier = replier;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public CustomerComment getComment() {
		return comment;
	}

	public void setComment(CustomerComment comment) {
		this.comment = comment;
	}

}
