/*
 * @(#)DeliveryType.java
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

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "deliverytype")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeliveryType implements Serializable{

	/**
	 * 快递
	 */
	public static final Long EXPRESS = new Long(3);

	/**
	 * 自提
	 */
	public static final Long DIY = new Long(4);

	/**
	 * 平邮
	 */
	public static final Long FLAT_POST = new Long(1);

	/**
	 * EMS
	 */
	public static final Long EMS = new Long(2);
	/**
	 * 平邮小包
	 */
	public static final Long FLAT_POST_SMALL = new Long(6);
	
	private static final long serialVersionUID = -8727473250588382809L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private boolean available;

	@Column
	private int sort;
	
	public DeliveryType(){
		
	}
	
	public DeliveryType(Long id){
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DeliveryType)) {
			return false;
		}
		DeliveryType other = (DeliveryType) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}

}
