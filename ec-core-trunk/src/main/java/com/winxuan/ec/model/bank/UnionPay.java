package com.winxuan.ec.model.bank;

import java.math.BigDecimal;
/**
 * 
 * @author youwen
 *
 */
public class UnionPay extends Bank{
	
	public static final String SIGN_METHOD="MD5";
	public static final String CHARSET="UTF-8";
	public static final Long PAYMENT_ID = 127L;
	public static final String VERSION= "1.0.0";
	
	private String signKey;
	
	private String batchNo;
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		
	}
	

}
