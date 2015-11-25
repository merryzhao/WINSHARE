/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.present;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.user.Employee;

/**
 * 礼券批次显示
 * 
 * @author  liyang
 * @version 1.0,2011-8-31
 */
public class PresentBatchShow implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer num;

	private Integer createNum;

	private Integer activeNum; // 激活数量

	private Integer usedNum; // 使用数量

	private Integer payNum; // 支付数量

	private BigDecimal value;

	private Boolean isGeneral;

	private String generalCode;

	private BigDecimal orderBaseAmount;

	private String productType;

	private Date presentStartDate;

	private Date presentEndDate;

	private Integer presentEffectiveDay;

	private Integer maxQuantity;

	private Code state;

	private String batchTitle;

	private String description;

	private Employee createUser;

	private Date createTime;

	private Employee assessor;

	private Date assessTime;

	public PresentBatchShow() {
		super();
	}
	
	/**
	 * @param id
	 * @param num
	 * @param createNum
	 * @param activeNum
	 * @param usedNum
	 * @param payNum
	 * @param value
	 * @param isGeneral
	 * @param generalCode
	 * @param orderBaseAmount
	 * @param productType
	 * @param presentStartDate
	 * @param presentEndDate
	 * @param presentEffectiveDay
	 * @param maxQuantity
	 * @param state
	 * @param batchTitle
	 * @param description
	 * @param createUser
	 * @param createTime
	 * @param assessor
	 * @param assessTime
	 */
	public PresentBatchShow(PresentBatch presentBatch,int activeNums,int usedNums,int payNums) {
		this.id = presentBatch.getId();
		this.num = presentBatch.getNum();
		this.createNum = presentBatch.getCreatedNum();
		this.activeNum = activeNums;
		this.usedNum = usedNums;
		this.payNum = payNums;
		this.value = presentBatch.getValue();
		this.isGeneral = presentBatch.isGeneral();
		this.generalCode = presentBatch.getGeneralCode();
		this.orderBaseAmount = presentBatch.getOrderBaseAmount();
		this.productType = getProducttypeString(presentBatch.getProductType());
		this.presentStartDate = presentBatch.getPresentStartDate();
		this.presentEndDate = presentBatch.getPresentEndDate();
		this.presentEffectiveDay = presentBatch.getPresentEffectiveDay();
		this.state = presentBatch.getState();
		this.batchTitle = presentBatch.getBatchTitle();
		this.description = presentBatch.getDescription();
		this.createUser = presentBatch.getCreateUser();
		this.createTime = presentBatch.getCreateTime();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * @return the createNum
	 */
	public Integer getCreateNum() {
		return createNum;
	}

	/**
	 * @param createNum the createNum to set
	 */
	public void setCreateNum(Integer createNum) {
		this.createNum = createNum;
	}

	/**
	 * @return the activeNum
	 */
	public Integer getActiveNum() {
		return activeNum;
	}

	/**
	 * @param activeNum the activeNum to set
	 */
	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
	}

	/**
	 * @return the usedNum
	 */
	public Integer getUsedNum() {
		return usedNum;
	}

	/**
	 * @param usedNum the usedNum to set
	 */
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	/**
	 * @return the payNum
	 */
	public Integer getPayNum() {
		return payNum;
	}

	/**
	 * @param payNum the payNum to set
	 */
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}


	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the isGeneral
	 */
	public Boolean getIsGeneral() {
		return isGeneral;
	}

	/**
	 * @param isGeneral the isGeneral to set
	 */
	public void setIsGeneral(Boolean isGeneral) {
		this.isGeneral = isGeneral;
	}

	/**
	 * @return the generalCode
	 */
	public String getGeneralCode() {
		return generalCode;
	}

	/**
	 * @param generalCode the generalCode to set
	 */
	public void setGeneralCode(String generalCode) {
		this.generalCode = generalCode;
	}

	/**
	 * @return the orderBaseAmount
	 */
	public BigDecimal getOrderBaseAmount() {
		return orderBaseAmount;
	}

	/**
	 * @param orderBaseAmount the orderBaseAmount to set
	 */
	public void setOrderBaseAmount(BigDecimal orderBaseAmount) {
		this.orderBaseAmount = orderBaseAmount;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		
		this.productType = getProducttypeString(productType);
	}

	/**
	 * @return the presentStartDate
	 */
	public Date getPresentStartDate() {
		return presentStartDate;
	}

	/**
	 * @param presentStartDate the presentStartDate to set
	 */
	public void setPresentStartDate(Date presentStartDate) {
		this.presentStartDate = presentStartDate;
	}

	/**
	 * @return the presentEndDate
	 */
	public Date getPresentEndDate() {
		return presentEndDate;
	}

	/**
	 * @param presentEndDate the presentEndDate to set
	 */
	public void setPresentEndDate(Date presentEndDate) {
		this.presentEndDate = presentEndDate;
	}

	/**
	 * @return the presentEffectiveDay
	 */
	public Integer getPresentEffectiveDay() {
		return presentEffectiveDay;
	}

	/**
	 * @param presentEffectiveDay the presentEffectiveDay to set
	 */
	public void setPresentEffectiveDay(Integer presentEffectiveDay) {
		this.presentEffectiveDay = presentEffectiveDay;
	}

	/**
	 * @return the maxQuantity
	 */
	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	/**
	 * @param maxQuantity the maxQuantity to set
	 */
	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	/**
	 * @return the state
	 */
	public Code getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Code state) {
		this.state = state;
	}

	/**
	 * @return the batchTitle
	 */
	public String getBatchTitle() {
		return batchTitle;
	}

	/**
	 * @param batchTitle the batchTitle to set
	 */
	public void setBatchTitle(String batchTitle) {
		this.batchTitle = batchTitle;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the createUser
	 */
	public Employee getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the assessor
	 */
	public Employee getAssessor() {
		return assessor;
	}

	/**
	 * @param assessor the assessor to set
	 */
	public void setAssessor(Employee assessor) {
		this.assessor = assessor;
	}

	/**
	 * @return the assessTime
	 */
	public Date getAssessTime() {
		return assessTime;
	}

	/**
	 * @param assessTime the assessTime to set
	 */
	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}

	public String getProducttypeString(String type) {
		StringBuffer sb = new StringBuffer(); 
		for(int i=0;i<type.length();i++){
			char y = type.charAt(i);
			if(y=='B'){
				sb.append("图书,");
			}else if(y=='V'){
				sb.append("音响,");
			}else{
				sb.append("百货,");
			}
			
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}

}
