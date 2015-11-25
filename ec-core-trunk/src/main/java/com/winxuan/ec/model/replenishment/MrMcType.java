/*
 * @(#)MrMcType.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.replenishment;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.code.Code;

/**
 * description 补货系统：MC类型明细
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-19 上午10:31:04
 */

@Entity
@Table(name = "mr_mc_type")
public class MrMcType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4206245380230691026L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * MC分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mccategory")
	private McCategory mcCategory;

	/**
	 * 类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

	/**
	 * 数量
	 */
	@Column(name = "quantity")
	private Integer quantity = 0;
	
	/**
	 * 边界值 上界-- 定位表
	 */
	@Column(name = "boundtop")
	private BigDecimal boundTop;
	
	/**
	 * 边界值 下界-- 定位表
	 */
	@Column(name = "boundbottom")
	private BigDecimal boundBottom;
	
	/**
	 * 边界值范围内的比率
	 */
	@Column(name = "ratio")
	private BigDecimal ratio;
	
	/**
	 * 超出边界值的默认册数 -- 定位表
	 */
	@Column(name = "defaultquantity")
	private Integer defaultQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public McCategory getMcCategory() {
		return mcCategory;
	}

	public void setMcCategory(McCategory mcCategory) {
		this.mcCategory = mcCategory;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Integer getDefaultQuantity() {
		return defaultQuantity;
	}

	public void setDefaultQuantity(Integer defaultQuantity) {
		this.defaultQuantity = defaultQuantity;
	}

	public BigDecimal getBoundTop() {
		return boundTop;
	}

	public void setBoundTop(BigDecimal boundTop) {
		this.boundTop = boundTop;
	}

	public BigDecimal getBoundBottom() {
		return boundBottom;
	}

	public void setBoundBottom(BigDecimal boundBottom) {
		this.boundBottom = boundBottom;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	
}
