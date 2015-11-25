/*
 * @(#)Seller.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.shop.Shop;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name="seller")
@PrimaryKeyJoinColumn(name = "user")
public class Seller extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5536604317338883608L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storagetype")
	private Code storageType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="location")
	private Area location;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_shop")
	private Shop shop;
	
	@Column(name = "shopmanager")
	private boolean shopManager;

	public Code getStorageType() {
		return storageType;
	}

	public void setStorageType(Code storageType) {
		this.storageType = storageType;
	}

	public Area getLocation() {
		return location;
	}

	public void setLocation(Area location) {
		this.location = location;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public boolean isShopManager() {
		return shopManager;
	}

	public void setShopManager(boolean shopManager) {
		this.shopManager = shopManager;
	}
	
}
