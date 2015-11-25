/*
 * @(#)DeliveryCompany.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.delivery;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.api.Partnet;
import com.winxuan.ec.model.api.PartnetConstant;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name="deliverycompany")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryCompany implements Serializable,Partnet{
    
    /**
     * 成都物流中心
     */
    public static final Long  CHENGDU_CENTER = 1330L;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5669521436861284760L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column
	private String company;

	@Column
	private String description;

	@Column
	private boolean available;

	@Column
	private int sort;

	@Column
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public boolean equals(Object obj){
		if (obj instanceof DeliveryCompany) {
			DeliveryCompany dest = (DeliveryCompany) obj;
			
			return dest.getId() != null && dest.getId().equals(this.getId());
		}
		return false;
	}

	@Override
	public short getType() {
		return PartnetConstant.TYPE_DELIVERY;
	}
}
