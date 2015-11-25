/*
 * @(#)Alipay.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.math.BigDecimal;

/**
 * 支付宝
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public class Alipay extends Bank {

	protected static final String INPUT_CHARSET = "utf-8";
	protected static final String SERVICE = "create_direct_pay_by_user";
	protected static final String SIGN_TYPE = "MD5";
	protected static final String PAYMENT_TYPE = "1";
	protected static final String REFUND_SERVICE = "refund_fastpay_by_platform_nopwd";

	protected String sign;
	protected String payGateway;
	private String outTradeNo;
	private String body;
	private String totalFee;
	private String sellerEmail;
	private String subject;
	private String showUrl;
	private String key;
	private String service = SERVICE;
	private String charset = INPUT_CHARSET;
	private String refundService = REFUND_SERVICE;
	
	
	
	

	public String getRefundService() {
		return refundService;
	}

	public void setRefundService(String refundService) {
		this.refundService = refundService;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setPayGateway(String payGateway) {
		this.payGateway = payGateway;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSignType() {
		return SIGN_TYPE;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}
	
	public void setOutTradeNo(String outTradeNo){
		this.outTradeNo = outTradeNo;
	}

	public String getBody() {
		return body;
	}

	public String getPayGateway() {
		return payGateway;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public String getPaymentType() {
		return PAYMENT_TYPE;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public String getSign() {
		return sign;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		outTradeNo = tradeId;
		body = tradeId;
		subject = tradeId;
		totalFee = amount.toString();
		sign = com.alipay.util.Payment.CreateUrl(payGateway, SERVICE,
				SIGN_TYPE, outTradeNo, INPUT_CHARSET, getPartner(), key,
				showUrl, body, totalFee, PAYMENT_TYPE, sellerEmail, subject,
				getNotifyUrl(), getReturnUrl(), null, null);

	}

}
