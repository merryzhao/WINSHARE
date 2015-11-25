/*
 * @(#)Ips.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public class Ips extends Bank {

	private static final String ORDER_ENCODE_TYPE = "2";
	private static final String RET_ENCODE_TYPE = "11";
	private static final String RET_TYPE = "1";
	private static final String CURRENCY_TYPE = "RMB";
	private static final String LANG = "GB";
	private static final String GATEWAY_TYPE = "01";
	private static final String DO_CREDIT = "1";

	private String billno;
	private String amount;
	private String dispAmount = "";
	private String date;

	private String signMD5;
	private String merchanturl;
	private String serverurl;
	private String attach;

	private String bankco;
	private String key;
	
	private String pubKeyPath;

	public String getBillno() {
		return billno;
	}

	public String getAmount() {
		return amount;
	}

	public String getDispAmount() {
		return dispAmount;
	}

	public String getDate() {
		return date;
	}

	public String getOrderEncodeType() {
		return ORDER_ENCODE_TYPE;
	}

	public String getRetEncodeType() {
		return RET_ENCODE_TYPE;
	}

	public String getRetType() {
		return RET_TYPE;
	}

	public String getSignMD5() {
		return signMD5;
	}

	public String getMerchanturl() {
		return merchanturl;
	}

	public String getServerurl() {
		return serverurl;
	}

	public String getAttach() {
		return attach;
	}

	public String getCurrencyType() {
		return CURRENCY_TYPE;
	}

	public String getLang() {
		return LANG;
	}

	public String getGatewayType() {
		return GATEWAY_TYPE;
	}

	public String getDoCredit() {
		return DO_CREDIT;
	}

	public String getBankco() {
		return bankco;
	}


	public void setKey(String key) {
		this.key = key;
	}

	public String getPubKeyPath() {
		return pubKeyPath;
	}

	public void setPubKeyPath(String pubKeyPath) throws IOException {
		this.pubKeyPath = pubKeyPath;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		billno = tradeId;
		this.amount = amount.toString();
		date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String plain = tradeId + amount + date + CURRENCY_TYPE + key;
		signMD5 = DigestUtils.md5Hex(plain).toLowerCase();
		attach = tradeId;
		dispAmount = amount.toString();
	}
}
