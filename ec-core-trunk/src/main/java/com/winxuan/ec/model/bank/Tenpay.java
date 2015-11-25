/*
 * @(#)Tenpay.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public class Tenpay extends Bank {

	private static final String BANK_TYPE = "DEFAULT";
	private static final String FEE_TYPE = "1";
	private static final String SERVICE_VERSION = "1.0";
	private static final String SIGN_TYPE = "MD5";
	private static final String INPUT_CHARSET = "utf-8";
	private static final String SIGN_KEY_INDEX = "1";
	private static final String PARAM_SEPARATOR = "&";

	private String sign;
	private String outTradeNo;
	private String totalFee;
	private String body;
	private String spbillCreateIp;
	private String key;

	// 退款使用属性
	private String refundNotifyUrl;
	private String refundOpUserId;
	private String refundUserPassWord;
	private String refundCaInfo;
	private String refundCertInfo;

	
	

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public String getBody() {
		return body;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public String getBankType() {
		return BANK_TYPE;
	}

	public String getFeeType() {
		return FEE_TYPE;
	}

	public String getServiceVersion() {
		return SERVICE_VERSION;
	}

	public String getSignType() {
		return SIGN_TYPE;
	}

	public String getInputCharset() {
		return INPUT_CHARSET;
	}

	public String getSignKeyIndex() {
		return SIGN_KEY_INDEX;
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

	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}

	public String getRefundOpUserId() {
		return refundOpUserId;
	}

	public void setRefundOpUserId(String refundOpUserId) {
		this.refundOpUserId = refundOpUserId;
	}

	public String getRefundUserPassWord() {
		return refundUserPassWord;
	}

	public void setRefundUserPassWord(String refundUserPassWord) {
		this.refundUserPassWord = refundUserPassWord;
	}

	public String getRefundCaInfo() {
		return refundCaInfo;
	}

	public void setRefundCaInfo(String refundCaInfo) throws IOException {
		this.refundCaInfo = refundCaInfo;
	}

	public String getRefundCertInfo() {
		return refundCertInfo;
	}

	public void setRefundCertInfo(String refundCertInfo) throws IOException {
		this.refundCertInfo = refundCertInfo;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		outTradeNo = tradeId;
		totalFee = String.valueOf(amount.movePointRight(2));
		body = tradeId;
		String signParam = ("bank_type=" + BANK_TYPE + PARAM_SEPARATOR
				+ "body=" + body + PARAM_SEPARATOR + "fee_type=" + FEE_TYPE
				+ PARAM_SEPARATOR + "input_charset=" + INPUT_CHARSET
				+ PARAM_SEPARATOR + "notify_url=" + getNotifyUrl()
				+ PARAM_SEPARATOR + "out_trade_no=" + outTradeNo
				+ PARAM_SEPARATOR + "partner=" + getPartner() + PARAM_SEPARATOR
				+ "return_url=" + getReturnUrl() + PARAM_SEPARATOR
				+ "service_version=" + SERVICE_VERSION + PARAM_SEPARATOR
				+ "sign_key_index=" + SIGN_KEY_INDEX + PARAM_SEPARATOR
				+ "sign_type=" + SIGN_TYPE + PARAM_SEPARATOR
				+ "spbill_create_ip=" + spbillCreateIp + PARAM_SEPARATOR
				+ "total_fee=" + totalFee + PARAM_SEPARATOR + "key=" + key)
				.trim();
		sign = DigestUtils.md5Hex(signParam).toUpperCase();
	}

}
