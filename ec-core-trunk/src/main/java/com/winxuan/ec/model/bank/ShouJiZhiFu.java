/*
 * @(#)ShouJiZhiFu.java
 *
 */

package com.winxuan.ec.model.bank;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hisun.iposm.HiiposmUtil;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * @author  huangyixiang
 * @version 2012-7-5
 */
public class ShouJiZhiFu extends Bank {
	
//	private static final Log LOG = LogFactory.getLog(ShouJiZhiFu.class);
	
	private String signKey;
	private String characterSet = "02";
	private String ipAddress ;
	private String requestId ;
	private String signType = "MD5";
	private String type = "DirectPayConfirm";
	private String version = "2.0.0";
	private String hmac ;
	private String amount ;
	private String currency = "00";
	private String orderDate ;
	private String orderId ;
	private String merAcDate ;
	private String period = "7";
	private String periodUnit = "02";
	private String productName = "www.winxuan.com";
	

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		this.ipAddress = "";
		this.requestId = tradeId;
		this.amount = amount.multiply(new BigDecimal(MagicNumber.HUNDRED)).toString();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		this.orderDate = df.format(new Date());
		this.orderId = tradeId;
		this.merAcDate = orderDate;
		
		String signData = characterSet + getReturnUrl() + getNotifyUrl()
				+ ipAddress + getPartner() + requestId + signType + type
				+ version + this.amount + currency + orderDate + orderId
				+ merAcDate + period + periodUnit + productName;
		
		/*try {
			this.productName = URLEncoder.encode(productName, "GBK");
		} catch (UnsupportedEncodingException e) {
			LOG.info(e.getMessage(), e);
		}*/
		
		HiiposmUtil util = new HiiposmUtil();
		this.hmac = util.MD5Sign(signData, signKey);
	}


	public String getSignKey() {
		return signKey;
	}


	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}


	public String getCharacterSet() {
		return characterSet;
	}


	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	public String getSignType() {
		return signType;
	}


	public void setSignType(String signType) {
		this.signType = signType;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getHmac() {
		return hmac;
	}


	public void setHmac(String hmac) {
		this.hmac = hmac;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getMerAcDate() {
		return merAcDate;
	}


	public void setMerAcDate(String merAcDate) {
		this.merAcDate = merAcDate;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public String getPeriodUnit() {
		return periodUnit;
	}


	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
