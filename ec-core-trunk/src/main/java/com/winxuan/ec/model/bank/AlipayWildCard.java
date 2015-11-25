/*
 * @(#)AlipayWildCard.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.util.Md5Encrypt;


/**
 * 支付宝外卡支付
 * 
 * @author huangyixiang
 * @version 1.0,2012-04-24
 */
public class AlipayWildCard extends Alipay {
	
	private String defaultBank; 

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		super.initTrade(tradeId, amount); 
		this.sign = generateSign(payGateway, getService(),
				SIGN_TYPE, getOutTradeNo(), INPUT_CHARSET, getPartner(),
				getKey(), getShowUrl(), getBody(), getTotalFee(), null,
				getSellerEmail(), getSubject(), getNotifyUrl(), getReturnUrl(),
				getDefaultBank());
	}
	
	private static String generateSign(String paygateway, String service, String signType, String outTradeNo, String inputCharset, String partner, String key, String showUrl, 
            String body, String totalFee, String paymentType, String sellerEmail, String subject, String notifyUrl, String returnUrl,String defaultBank) {
		Map params = new HashMap();
		params.put("service", service);
		params.put("partner", partner);
		params.put("subject", subject);
		params.put("body", body);
		params.put("out_trade_no", outTradeNo);
		params.put("total_fee", totalFee);
		params.put("show_url", showUrl);
		params.put("payment_type", paymentType);
		params.put("seller_logon_id", sellerEmail);
		params.put("return_url", returnUrl);
		params.put("notify_url", notifyUrl);
		params.put("_input_charset", inputCharset);
		params.put("default_bank", defaultBank);
		String sign = Md5Encrypt.md5(getContent(params, key));
		return sign;
	}
	
	private static String getContent(Map params, String privateKey){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value != null && value.trim().length() != 0) {
				if (first) {
					prestr = prestr + key + "=" + value;
					first = false;
				} else {
					prestr = prestr + "&" + key + "=" + value;
				}
			}
		}
		return prestr + privateKey;
	}

	public String getDefaultBank() {
		return defaultBank;
	}

	public void setDefaultBank(String defaultBank) {
		this.defaultBank = defaultBank;
	}
	
	
}
