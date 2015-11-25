/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.present;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
/**
 * 礼券批次FORM
 * 
 * @author  
 * @version 1.0,2011-8-31
 */
public class PresentBatchForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private Long id;
	
    @NotNull(message="不能为空")
 	private Integer num;   //批次
	
 	private Integer createNum;  //生成数量
 	
 	@NotNull(message="不能为空")
 	private BigDecimal value;  // 面额
	
 	private Boolean isGeneral;   //	是否通用
	
 	private String generalCode;   //通用码
 	
 	@NotNull(message="不能为空")
 	private BigDecimal orderBaseAmount; //订单额基准金额
	
 	private String productTypeString;   //针对商品类别字符串
 	
 	@NotNull(message="不能为空")
 	private List<String> productType;   //针对商品类别
 	
 	@NotNull(message="不能为空")
 	private String presentStartDateString;  //礼券生效开始日期字符串

 	private String presentEndDateString;    //礼券有效截止期字符串
 	
 	private Integer presentEffectiveDay;   //礼券有效期
 	
 	@NotNull(message="不能为空")
 	private Integer maxQuantity;   //单用户最多申领数量
 	
 	private String isRebate;
 	private String isPloy;

 	
 	@NotNull(message="不能为空")
 	private String batchTitle;  //批次标题
	
 	private String description;  //批次描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCreateNum() {
		return createNum;
	}

	public void setCreateNum(Integer createNum) {
		this.createNum = createNum;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Boolean getIsGeneral() {
		return isGeneral;
	}

	public void setIsGeneral(Boolean isGeneral) {
		this.isGeneral = isGeneral;
	}

	public String getGeneralCode() {
		return generalCode;
	}

	public void setGeneralCode(String generalCode) {
		this.generalCode = generalCode;
	}

	public BigDecimal getOrderBaseAmount() {
		return orderBaseAmount;
	}

	public void setOrderBaseAmount(BigDecimal orderBaseAmount) {
		this.orderBaseAmount = orderBaseAmount;
	}

	public List<String> getProductType() {
		return productType;
	}

	public void setProductType(List<String> productType) {
		this.productType = productType;
	}


	public Integer getPresentEffectiveDay() {
		return presentEffectiveDay;
	}

	public void setPresentEffectiveDay(Integer presentEffectiveDay) {
		this.presentEffectiveDay = presentEffectiveDay;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getBatchTitle() {
		return batchTitle;
	}

	public void setBatchTitle(String batchTitle) {
		this.batchTitle = batchTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setProductTypeString(String productTypeString) {
		this.productTypeString = productTypeString;
	}

	public String getProductTypeString() {
		return productTypeString;
	}

	public void setPresentStartDateString(String presentStartDateString) {
		this.presentStartDateString = presentStartDateString;
	}

	public String getPresentStartDateString() {
		return presentStartDateString;
	}

	public void setPresentEndDateString(String presentEndDateString) {
		this.presentEndDateString = presentEndDateString;
	}

	public String getPresentEndDateString() {
		return presentEndDateString;
	}

	public String getIsRebate() {
		return isRebate;
	}

	public void setIsRebate(String isRebate) {
		this.isRebate = isRebate;
	}

	public String getIsPloy() {
		return isPloy;
	}

	public void setIsPloy(String isPloy) {
		this.isPloy = isPloy;
	}

 
 }
