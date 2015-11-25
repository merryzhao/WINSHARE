/*
 * @(#)MrSupply.java
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.winxuan.ec.model.code.Code;

/**
 * description 补货系统：团购权重
 * 
 * @author wangbiao
 * @version 1.0 date 2013-8-19 上午9:43:59
 */

@Entity
@Table(name = "mr_supply")
public class MrSupply implements Serializable {

	public static BigDecimal weightsNewProduct = new BigDecimal(0.2);//新品权重默认值设为0.2
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6179628661810081265L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	/**
	 * 销售分级
	 */
	@Column(name = "grade")
	@NotEmpty
	@NotBlank
	private String grade;

	/**
	 * 权重指数
	 */
	@Column(name = "weights")
	@NotNull
	private BigDecimal weights;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public BigDecimal getWeights() {
		return weights;
	}

	public void setWeights(BigDecimal weights) {
		this.weights = weights;
	}

}
