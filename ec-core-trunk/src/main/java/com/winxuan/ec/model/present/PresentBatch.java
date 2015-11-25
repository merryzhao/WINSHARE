/*
 * @(#)PresentBatch.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.present;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.present.PresentServiceImpl;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 礼券批次
 * @author  yuhu
 * @version 1.0,2011-8-30
 */
@Entity
@Table(name = "present_batch")
public class PresentBatch implements Serializable{

	/**
	 * 补差额礼券批次ID
	 */
	public static final long REUSSUE_PRESENT_ID = 1L;
	/**
	 * 礼券兑换批次ID
	 */
	public static final long EXCHANGE_PRESENT_ID = 2L;

	private static final long serialVersionUID = -5779329577582595008L;
	
	private static final Log LOG = LogFactory.getLog(PresentServiceImpl.class);


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**
	 * 礼券可分发礼券数量
	 */
	@Column
	private Integer num;

	@Column(name = "createnum")
	private Integer createdNum=MagicNumber.ZERO;

	@Column(name = "activenum")
	private Integer activeNum=MagicNumber.ZERO;

	@Column(name = "usednum")
	private Integer usedNum=MagicNumber.ZERO;

	@Column(name = "paynum")
	private Integer payNum=MagicNumber.ZERO;

	@Column(name = "_value")
	private BigDecimal value;

	@Column(name = "isgeneral")
	private boolean general;

	@Column(name = "generalcode")
	private String generalCode;

	@Column(name = "order_baseamount")
	private BigDecimal orderBaseAmount;

	@Column(name = "producttype")
	private String productType;

	@Column(name = "isrebate")
	private boolean rebate=true;

	@Column(name = "isploy")
	private boolean ploy=true;

	@Column(name = "present_startdate")
	private Date presentStartDate;

	@Column(name = "present_enddate")
	private Date presentEndDate;

	@Column(name = "present_effectiveday")
	private Integer presentEffectiveDay;

	@Column(name = "maxquantity")
	private Integer maxQuantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="state")
	private Code state;

	@Column(name = "batchtitle")
	private String batchTitle;

	@Column
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="createuser")
	private Employee createUser;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assessor")
	private Employee assessor;

	@Column(name = "assesstime")
	private Date assessTime;

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

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

	public Integer getCreatedNum() {
		return createdNum;
	}

	public void setCreatedNum(Integer createdNum) {
		this.createdNum = createdNum;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public boolean isGeneral() {
		return general;
	}

	public void setGeneral(boolean general) {
		this.general = general;
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

	public boolean isRebate() {
		return rebate;
	}

	public void setRebate(boolean rebate) {
		this.rebate = rebate;
	}

	public boolean isPloy() {
		return ploy;
	}

	public void setPloy(boolean ploy) {
		this.ploy = ploy;
	}

	public void setOrderBaseAmount(BigDecimal orderBaseAmount) {
		this.orderBaseAmount = orderBaseAmount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Date getPresentStartDate() {
		return presentStartDate;
	}

	public void setPresentStartDate(Date presentStartDate) {
		this.presentStartDate = presentStartDate;
	}

	public Date getPresentEndDate(){
		return this.presentEndDate;
	}

	public void setPresentEndDate(Date presentEndDate) {
		this.presentEndDate = presentEndDate;
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

	public Employee getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Employee getAssessor() {
		return assessor;
	}

	public void setAssessor(Employee assessor) {
		this.assessor = assessor;
	}

	public Date getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}

	public Integer getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(Integer activeNum) {
		this.activeNum = activeNum;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	public Integer getPayNum() {
		return payNum;
	}

	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}

	public Present convertPresent() throws ParseException{
		Present present = new Present();
		present.setBatch(this);
		present.setValue(this.value);
		present.setStartDate(this.presentStartDate);
		Date endDate = getEndDate();
		present.setEndDate(endDate);
		present.setState(new Code(Code.PRESENT_STATUS_GENERATE));
		return present;
	}

	/**
	 * 得到礼券批次下的礼券失效时间
	 * @return
	 */
	public Date getEndDate(){
		Date endDate = getPresentEndDate() == null ? 
				DateUtils.addDays(new Date(), this.presentEffectiveDay) : 
					getPresentEndDate();
				try {
					return com.winxuan.framework.util.DateUtils.getLateInTheDay(endDate);
				} catch (ParseException e) {
					LOG.error(e.getMessage(),e);
					return null;
				}
	}

	public String getProductTypeName(){
		StringBuffer sb = new StringBuffer();
		if(productType.indexOf("B")!=-1){
			sb.append("图书");
		}
		if(productType.indexOf("V")!=-1){
			sb.append("音像");
		}
		if(productType.indexOf("G")!=-1){
			sb.append("百货");
		}
		return sb.toString();
	}

}
