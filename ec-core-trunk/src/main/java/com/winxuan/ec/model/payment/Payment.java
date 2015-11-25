/*
 * @(#)Payment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.payment;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "payment")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment implements Serializable{

	/**
	 * 货到付款
	 */
	public static final Long COD = 1L;

	/**
	 * 现金支付
	 */
	public static final Long CASH = 2L;

	/**
	 * 暂存款
	 */
	public static final Long ACCOUNT = 15L;
	
	/**
	 * 邮局汇款
	 */
	public static final Long POST = 3L;
	
	/**
	 * 银行转账
	 */
	public static final Long BANK = 4L;
	
	/**
	 * 礼品卡
	 */
    public static final Long GIFT_CARD = 21L;
    
    /**
     * 礼券
     */
    public static final Long COUPON = 20L;
    
    /**
     * 渠道支付
     */
    public static final Long CHANNEL = 100L;
    
    /**
     * 帐期支付
     */
    public static final Long OFF_PERIOD = 81L;
    
    /**
     * 支付宝
     */
    public static final Long ALIPAY = 9L;
    /**
     * 中国银联
     */
    public static final Long CHINA_PAY = 82L;
    
    /**
	 * 财付通
	 */
	public static final Long TEN_PAY = 33L;
	/**
	 * 手机平台支付
	 */
	public static final Long ALI_WAP_PAY = 131L;
	/**
	 * 银联手机app应用支付
	 */
	public static final Long CHINA_PAY_MOBILE = 127L;
    
    private static final long serialVersionUID = -6897885036317810740L;
    
 	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private String logo;

	@Column
	private boolean available;

	@Column
	private int sort;

	@Column(name = "isshow")
	private boolean show;

	@Column(name = "allowrefund")
	private boolean allowRefund;

	@Column(name = "refundterm")
	private int refundTerm;

	@Column(name = "refundfee")
	private BigDecimal refundFee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="refundfeetype")
	private Code refundFeeType;

	@Column(name = "refundfeemin")
	private BigDecimal refundFeeMin;

	@Column(name = "payterm")
	private int payTerm;

	@Column(name = "payfee")
	private BigDecimal payFee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payfeetype")
	private Code payFeeType;

	@Column(name = "payfeemin")
	private BigDecimal payFeeMin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="type")
	private Code type;
	
	public Payment(){
		
	}
	public Payment(Long id){
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public int getPayTerm() {
		return payTerm;
	}

	public void setPayTerm(int payTerm) {
		this.payTerm = payTerm;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public int getRefundTerm() {
		return refundTerm;
	}

	public void setRefundTerm(int refundTerm) {
		this.refundTerm = refundTerm;
	}

	public BigDecimal getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	public BigDecimal getRefundFeeMin() {
		return refundFeeMin;
	}

	public void setRefundFeeMin(BigDecimal refundFeeMin) {
		this.refundFeeMin = refundFeeMin;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public Code getRefundFeeType() {
		return refundFeeType;
	}

	public void setRefundFeeType(Code refundFeeType) {
		this.refundFeeType = refundFeeType;
	}

	public Code getPayFeeType() {
		return payFeeType;
	}

	public void setPayFeeType(Code payFeeType) {
		this.payFeeType = payFeeType;
	}

	public BigDecimal getPayFeeMin() {
		return payFeeMin;
	}

	public void setPayFeeMin(BigDecimal payFeeMin) {
		this.payFeeMin = payFeeMin;
	}

	public boolean isAllowRefund() {
		return allowRefund;
	}

	public void setAllowRefund(boolean allowRefund) {
		this.allowRefund = allowRefund;
	}
	@Override
	public String toString() {
		return "Payment [getId()=" + getId() + ", getName()=" + getName() + "]";
	}
	
	/**
	 * 得到支付方式从当前日期开始的最大支付期限
	 * @return
	 */
	public Date getMaxPayDate(){
		if(payTerm != MagicNumber.ZERO){
			return DateUtils.addDays(new Date(), payTerm);
		}
		return DateUtils.addYears(new Date(), MagicNumber.HUNDRED);
	}

}
