/*
 * @(#)ShoppingcartSeparator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.shoppingcart;

import java.io.Serializable;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.shop.Shop;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-24
 */
public class ShoppingcartSeparator implements Serializable {

	private static final long serialVersionUID = -9077449089947866592L;

	private Shop shop;

	private Code supplyType;

	public ShoppingcartSeparator(Shop shop, Code supplyType) {
		super();
		this.shop = shop;
		this.supplyType = supplyType;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Code getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(Code supplyType) {
		this.supplyType = supplyType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		result = prime * result
				+ ((supplyType == null) ? 0 : supplyType.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShoppingcartSeparator other = (ShoppingcartSeparator) obj;
		if (shop == null) {
			if (other.shop != null) {
				return false;
			}
		} else if (!shop.equals(other.shop)) {
			return false;
		}
		if (supplyType == null) {
			if (other.supplyType != null) {
				return false;
			}
		} else if (!supplyType.equals(other.supplyType)) {
			return false;
		}
		return true;
	}

}
