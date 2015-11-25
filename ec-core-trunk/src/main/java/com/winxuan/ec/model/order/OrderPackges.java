/*
 * @(#)OrderPackges.java
 */

package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单包件日志
 * 
 * @author wenchx
 * @version 1.0, 2013-11-8 下午2:31:47
 */
@Entity
@Table(name = "order_packages")
public class OrderPackges implements Serializable {

	private static final long serialVersionUID = -3996267211515018784L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "_order")
	private String order;

	@Column(name = "channel")
	private Long channel;

//	@Column(insertable = false)
//	private Date ts;
//	
	@Column(name = "_value")
	private String value;
	
	@Column(name = "createtime")
	private Date createTime;

	public OrderPackges() {
	}

	public OrderPackges(String order, Long channel, Date createTime,String value) {
		super();
		this.order = order;
		this.channel = channel;
		this.createTime = createTime;
		this.value=value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

//	public Date getTs() {
//		return ts;
//	}
//
//	public void setTs(Date ts) {
//		this.ts = ts;
//	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
