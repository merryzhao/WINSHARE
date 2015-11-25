/*
 * @(#)MrProductItem.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.replenishment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.support.log.AutoLogger;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-8-22
 */
@Entity
@Table(name = "mr_product_item")
public class MrProductItem implements Serializable, AutoLogger{

	private static final long serialVersionUID = 6692530674506085087L;
	
	private static final Map<String, Class<?>> LOG_MAP = new HashMap<String, Class<?>>();
	static {
		LOG_MAP.put("check", MrUpdateLog.class);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	/**
	 * 销售分级
	 */
	@Column(name = "grade")
	private String grade;
	
	/**
	 * 补货模型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model")
	private Code model;
	
	/**
	 * 批次号
	 */
	@Column(name = "num")
	private String num;
	
	/**
	 * 补货周期
	 */
	@Column(name = "replenishmentcycle")
	private int replenishmentCycle;
	
	/**
	 * 安全库存
	 */
	@Column(name = "safequantity")
	private int safeQuantity;
	
	/**
	 * 预测销售
	 */
	@Column(name = "forecastquantity")
	private int forecastQuantity;
	
	/**
	 * 可用量
	 */
	@Column(name = "availablequantity")
	private int availableQuantity;
	
	/**
	 * 补货数量
	 */
	@Column(name = "replenishmentquantity")
	private int replenishmentQuantity;
	
	/**
	 * 发货仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	/**
	 * 创建时间
	 */
	@Column(name = "createtime")
	private Date createTime;
	
	/**
	 * 审核状态
	 */
	@Column(name = "status")
	private boolean check;
	
	/**
	 * 审核时间
	 */
	@Column(name = "audittime")
	private Date auditTime;

	/**
	 * 补货类型：系统补货、人工补货
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

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

	public Code getModel() {
		return model;
	}

	public void setModel(Code model) {
		this.model = model;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getReplenishmentCycle() {
		return replenishmentCycle;
	}

	public void setReplenishmentCycle(int replenishmentCycle) {
		this.replenishmentCycle = replenishmentCycle;
	}

	public int getSafeQuantity() {
		return safeQuantity;
	}

	public void setSafeQuantity(int safeQuantity) {
		this.safeQuantity = safeQuantity;
	}

	public int getForecastQuantity() {
		return forecastQuantity;
	}

	public void setForecastQuantity(int forecastQuantity) {
		this.forecastQuantity = forecastQuantity;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public int getReplenishmentQuantity() {
		return replenishmentQuantity;
	}

	public void setReplenishmentQuantity(int replenishmentQuantity) {
		this.replenishmentQuantity = replenishmentQuantity;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	@Override
	public Map<String, Class<?>> getFieldNames() {
		return LOG_MAP;
	}
	
}
