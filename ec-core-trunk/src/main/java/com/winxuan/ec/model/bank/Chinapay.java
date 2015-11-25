/*
 * @(#)Chinapay.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public class Chinapay extends Bank {

	private static final Log LOG = LogFactory.getLog(Chinapay.class);

	/**
	 * 订单交易币种，3位长度，固定为人民币156，必填
	 */
	private static final String CURYID = "156";
	/**
	 * 交易类型，4位长度，必填
	 */
	private static final String TRANSTYPE = "0001";
	/**
	 * 支付接入版本号，必填
	 */
	private static final String VERSION = "20070129";

	private String pgpNo;
	/**
	 * 公钥所在路径
	 */
	private String pgpubkPath;
	/**
	 * 秘钥所在路径
	 */
	private String merprkPath;

	/**
	 * 后台交易接收URL，长度不要超过80个字节，必填
	 */
	private String bgRetUrl;
	/**
	 * 页面交易接收URL，长度不要超过80个字节，必填
	 */
	private String pageRetUrl;
	/**
	 * 支付网关号，可选
	 */
	private final String gateId = "";
	/**
	 * 商户私有域，长度不要超过60个字节，可选
	 */
	private String priv1 = "";

	private String chkValue = "";
	/**
	 * 商户提交给ChinaPay的交易订单号，16位长度，必填
	 */
	private String ordId;
	/**
	 * 订单交易金额，12位长度，左补0，必填,单位为分
	 */
	private String transAmt;

	private int tradeIdLength;

	/**
	 * 订单交易日期，8位长度，必填
	 */
	@SuppressWarnings("unused")
	private String transDate;
	
	/**
	 * 退款需要用的日期
	 */
	private String refunderDate;

	public String getPgpubkPath() {
		return pgpubkPath;
	}

	public String getMerprkPath() {
		return merprkPath;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public void setPgpubkPath(String pgpubkPath) throws IOException {
		this.pgpubkPath = pgpubkPath;
	}

	public void setMerprkPath(String merprkPath) throws IOException {
		this.merprkPath = merprkPath;
	}

	public String getPgpNo() {
		return pgpNo;
	}

	public void setPgpNo(String pgpNo) {
		this.pgpNo = pgpNo;
	}

	public String getOrdId() {
		return ordId;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public String getCuryId() {
		return CURYID;
	}

	public String getTransDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public String getTransType() {
		return TRANSTYPE;
	}

	public String getVersion() {
		return VERSION;
	}

	public String getBgRetUrl() {
		return bgRetUrl;
	}

	public String getPageRetUrl() {
		return pageRetUrl;
	}

	public String getGateId() {
		return gateId;
	}

	
	
	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}
	public String getPriv1() {
		return priv1;
	}

	public String getChkValue() {
		return chkValue;
	}

	public int getTradeIdLength() {
		return tradeIdLength;
	}

	public void setTradeIdLength(int tradeIdLength) {
		this.tradeIdLength = tradeIdLength;
	}
	
	

	public String getRefunderDate() {
		return refunderDate;
	}

	public void setRefunderDate(String refunderDate) {
		this.refunderDate = refunderDate;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		ordId = String.format("%-16s", tradeId).replace(' ', '0');
		transAmt = String.format("%012d", amount.movePointRight(2).intValue());
		PrivateKey privateKey = new PrivateKey();
		boolean flag = privateKey.buildKey(getPartner(), 0, getMerprkPath());
		if (flag) {
			SecureLink secureLink = new SecureLink(privateKey);
			this.chkValue = secureLink.signOrder(getPartner(), getOrdId(),
					getTransAmt(), getCuryId(), getTransDate(), getTransType());
		} else {
			LOG.warn("build key fail. Secret key: " + getMerprkPath()
					+ " not exist");
		}
	}

	/**
	 * 退款签名
	 * @return
	 */
	public String getRefundChkValue() {
		String signStr = null;
		PrivateKey privateKey = new PrivateKey();
		boolean flag = privateKey.buildKey(getPartner(), 0, getMerprkPath());
		if (flag) {
			SecureLink secureLink = new SecureLink(privateKey);
			String mesgBody = this.getPartner() + this.getRefunderDate()
					+ this.getTransType() + this.getOrdId()
					+ this.getTransAmt() + this.priv1;
			signStr = secureLink.Sign(mesgBody);
		} else {
			LOG.warn("build key fail. Secret key: " + getMerprkPath()
					+ " not exist");
		}
		return signStr;
	}

}
