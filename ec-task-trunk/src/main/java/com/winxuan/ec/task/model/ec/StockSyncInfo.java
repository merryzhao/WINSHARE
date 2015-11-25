package com.winxuan.ec.task.model.ec;

import java.io.Serializable;

/**
 * 库存同步信息 封装product_warehouse
 * 
 * @author: HideHai
 * @date: 2013-10-16
 */
public class StockSyncInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String outerId;

	private Long productsale;

	private String location;

	private int stock;

	private boolean isChanged;

	private int changeqty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public Long getProductsale() {
		return productsale;
	}

	public void setProductsale(Long productsale) {
		this.productsale = productsale;
	}

	public int getChangeqty() {
		return changeqty;
	}

	public void setChangeqty(int changeqty) {
		this.changeqty = changeqty;
	}

}
