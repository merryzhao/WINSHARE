/*
 * @(#)UnionOrder.java
 *
 * Copyright 2008 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.union.Union;

/**
 * 联盟订单
 * 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
@Entity
@Table(name = "union_order")
public class UnionOrder implements Serializable{
	
	/**
	 * linktech联盟
	 */
	public static final Long UNION_LINKTECH = 1l;
	
	/**
	 * eqifa联盟
	 */
	public static final Long UNION_EQIFA = 2l;
	
	/**
	 * lianmark 联盟
	 */
	public static final Long UNION_LIANMARK = 3l;
	
	/**
	 * 唯一传媒联盟
	 */
	public static final Long UNION_WEIYI = 4l;
	
	/**
	 * 达闻联盟
	 */
	public static final Long UNION_DAWEN = 5l;
	
	/**
	 * 智购联盟
	 */
	public static final Long UNION_ZHIGOU = 6l;
	
	/**
	 * 成果联盟
	 */
	public static final Long UNION_CHANET = 7l;
	
	/**
	 * 	aleadpay联盟
	 */
	public static final Long UNION_ALEADPAY = 8l;
	
	/**
	 * 威购
	 */
	public static final Long UNION_VGOU = 9l;	
	
	/**
	 * 广告联盟
	 */
    public static final Long UNION_AD = 10l;
	/**
	 * 太平洋联盟
	 */
    public static final Long UNION_TPY = 11l;
    
    public static final String UNION_TPY_KEY = "d$6s5#fg%d";
    public static final String UNION_TPY_ID = "T10016";
    /**
     * 黄金联盟
     */
    public static final Long UNION_HJLM = 12l;
    /**
     * P.Cn
     */
    public static final Long UNION_PCN = 13l;
    
    /**
     * 中视联盟
     */
    public static final Long UNION_CTVWM = 14l;
    
    
    private static final long serialVersionUID = 5763827275100906844L;
    
	/**
	 * 编号
	 */
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 订单
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;
	
	/**
	 * 订单创建时间
	 */
    @Column(name="createdate")
	private Date createDate;
	
	/**
	 * 联盟的cookie信息
	 */
    @Column(name="cookieinfo")
	private String cookieInfo;
	
	/**
	 * 联盟标识 
	 *   <pre>
	 *   	1 表示linktech
	 *   	2 表示eqifa
	 *   	3 表示lianmark
	 *   </pre>
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="_union")
	private Union union;
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCookieInfo() {
		return cookieInfo;
	}

	public void setCookieInfo(String cookieInfo) {
		this.cookieInfo = cookieInfo;
	}
		

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cookieInfo == null) ? 0 : cookieInfo.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + (int)union.getId();
		return result;
	}




	public Union getUnion() {
		return union;
	}


	public void setUnion(Union union) {
		this.union = union;
	}


	public boolean equals(Object obj) {
		if (this == obj)
			{return true;}
		if (obj == null)
			{return false;}
		if (getClass() != obj.getClass())
			{return false;}
		UnionOrder other = (UnionOrder) obj;
		if (cookieInfo == null) {
			if (other.cookieInfo != null)
				{return false;}
		} else if (!cookieInfo.equals(other.cookieInfo))
			{return false;}
		if (createDate == null) {
			if (other.createDate != null)
				{return false;}
		} else if (!createDate.equals(other.createDate))
			{return false;}
		if (id == null) {
			if (other.id != null)
				{return false;}
		} else if (!id.equals(other.id))
			{return false;}
		if (order == null) {
			if (other.order != null)
				{return false;}
		} else if (!order.equals(other.order))
			{return false;}
		if (union.getId() != other.getUnion().getId())
			{return false;}
		return true;
	}



	
	
	
	
}

