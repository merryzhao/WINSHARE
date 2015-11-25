/*
 * @(#)StockDocuments.java
 *
 * Copyright 2014 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.documents;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.winxuan.services.pss.enums.EnumStockDocumentsType;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0, 2014年12月31日
 */
@Entity
@Table(name = "stock_documents")
public class StockDocuments implements Serializable {
	private static final long serialVersionUID = 3489555026900147604L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * 凭证号,标示外部单据唯一的外部单据号,销售订单为订单号.credence + dc + productSale + type唯一约束
	 */
	private String credence;

	/**
	 * 仓储地址
	 */
	private long dc;

	/**
	 * 商品编码
	 */
	private long productSale;

	@Enumerated(value = EnumType.STRING)
	private EnumStockDocumentsType type;

	/**
	 * 库存量,包括实物和虚拟.只传大于等于0的整数
	 */
	private int stock = 0;

	/**
	 * 占用数量
	 */
	private int occupancy = 0;

	private Date createTime;

	private String remark;

	private short verify = 0;

	private Date verifyTime;

	/**
	 * 
	 */
	public StockDocuments() {
		super();
		this.createTime = new Date();
	}

	/**
	 * @param credence
	 * @param dc
	 * @param productSale
	 */
	public StockDocuments(String credence, long dc, long productSale) {
		this();
		this.credence = credence;
		this.dc = dc;
		this.productSale = productSale;
	}

	/**
	 * @param credence
	 * @param dc
	 * @param productSale
	 * @param type
	 */
	public StockDocuments(String credence, long dc, long productSale,
			EnumStockDocumentsType type) {
		this(credence, dc, productSale);
		this.type = type;
	}

	/**
	 * @param credence
	 * @param dc
	 * @param productSale
	 * @param type
	 * @param stock
	 * @param occupancy
	 */
	public StockDocuments(String credence, long dc, long productSale,
			EnumStockDocumentsType type, int stock, int occupancy) {
		this(credence, dc, productSale, type);
		this.stock = stock;
		this.occupancy = occupancy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCredence() {
		return credence;
	}

	public void setCredence(String credence) {
		this.credence = credence;
	}

	public long getDc() {
		return dc;
	}

	public void setDc(long dc) {
		this.dc = dc;
	}

	public long getProductSale() {
		return productSale;
	}

	public void setProductSale(long productSale) {
		this.productSale = productSale;
	}

	public EnumStockDocumentsType getType() {
		return type;
	}

	public void setType(EnumStockDocumentsType type) {
		this.type = type;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getVerify() {
		return verify;
	}

	public void setVerify(short verify) {
		this.verify = verify;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

}
