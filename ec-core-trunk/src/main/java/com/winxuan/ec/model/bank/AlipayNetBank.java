/*
 * @(#)AlipayNetBank.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.math.BigDecimal;

/**
 * 支付宝网银
 * 
 * @author qiaoyao
 * @version 1.0,2011-7-26
 */
public class AlipayNetBank extends Alipay {

	private String defaultBank;
	private String payMethod = "bankPay";

	public String getDefaultBank() {
		return defaultBank;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setDefaultBank(String defaultBank) {
		this.defaultBank = defaultBank;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		super.initTrade(tradeId, amount);
		super.sign = com.alipay.util.Payment.CreateUrl(payGateway, SERVICE,
				SIGN_TYPE, getOutTradeNo(), INPUT_CHARSET, getPartner(),
				getKey(), getShowUrl(), getBody(), getTotalFee(), PAYMENT_TYPE,
				getSellerEmail(), getSubject(), getNotifyUrl(), getReturnUrl(),
				payMethod, defaultBank);

	}

}
