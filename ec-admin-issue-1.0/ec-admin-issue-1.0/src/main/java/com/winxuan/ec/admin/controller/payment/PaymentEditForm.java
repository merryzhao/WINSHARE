/*
 * @(#)PaymentForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.payment;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 
 * description
 * @author liyang
 * @version 1.0,2011-8-8
 */
public class PaymentEditForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int TERM = 3;

	@NotEmpty
	@NotBlank
	private String name; // 支付方式名称(输入文本框)

	private boolean available = true; // 是否有效(单选框，选项：是<默认>、否)

	private String description;; // 描述(输入文本域)
	
	private BigDecimal payFee;// 收款手续费费率(百分比)

	private BigDecimal payFeeMin;// 收款手续费最小收取金额

	private Long payFeeType;// 收款手续费最小收取方式(四舍五入、向上取整、向下取整)

	private Long type;// 支付方式类型(单选框, 选项:网上支付<默认>、线下支付)

	private boolean show = true;// 是否前台显示(单选框，选项：是<默认>、否)

	private boolean allowRefund = true;// 是否可退款(单选框，选项：是<默认>、否)

	private int refundTerm = TERM;// 退款最大期限(3个月、6个月、12个月、无期限限制)
	
	private BigDecimal refundFee;// 退款手续费率(百分比)

	private BigDecimal refundFeeMin;// 退款手续费最小金额

	private Long refundFeeType;// 退款手续费最小收取方式(四舍五入、向上取整、向下取整)

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public BigDecimal getPayFeeMin() {
		return payFeeMin;
	}

	public void setPayFeeMin(BigDecimal payFeeMin) {
		this.payFeeMin = payFeeMin;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isAllowRefund() {
		return allowRefund;
	}

	public void setAllowRefund(boolean allowRefund) {
		this.allowRefund = allowRefund;
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

	public Long getPayFeeType() {
		return payFeeType;
	}

	public void setPayFeeType(Long payFeeType) {
		this.payFeeType = payFeeType;
	}

	public Long getRefundFeeType() {
		return refundFeeType;
	}

	public void setRefundFeeType(Long refundFeeType) {
		this.refundFeeType = refundFeeType;
	}

}
