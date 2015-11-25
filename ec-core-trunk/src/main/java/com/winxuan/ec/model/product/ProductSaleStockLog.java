/*
 * @(#)ProductSaleStockLog.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.product;

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

import com.winxuan.ec.model.code.Code;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-7-30
 */
@Entity
@Table(name = "product_sale_stock_log")
public class ProductSaleStockLog implements Serializable {

	private static final long	serialVersionUID	= 7402577283096057693L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long				id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale			productSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code				dc;

	@Column
	private int					originalStock;

	@Column
	private int					stock;

	@Column
	private int					sales;

	@Column
	private Date				updateTime;

	public ProductSaleStockLog() {
	}

	public ProductSaleStockLog(ProductSale productSale, ProductSaleStock productSaleStock, int newStock) {
		this.productSale = productSale;
		this.dc = productSaleStock.getDc();
		this.originalStock = productSaleStock.getStock();
		this.stock = newStock;
		this.sales = productSaleStock.getSales();
		this.updateTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public int getOriginalStock() {
		return originalStock;
	}

	public void setOriginalStock(int originalStock) {
		this.originalStock = originalStock;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
