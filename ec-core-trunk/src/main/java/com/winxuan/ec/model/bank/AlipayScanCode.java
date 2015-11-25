package com.winxuan.ec.model.bank;

import java.math.BigDecimal;

import com.winxuan.ec.util.AlipayPaymentUtil;

/**
 * 支付宝扫码支付
 * 
 * @author bianlin
 * @version 1.0,2013-12-9
 */
public class AlipayScanCode extends Alipay{
	protected String qrPayMode;

	public String getQrPayMode() {
		return qrPayMode;
	}

	public void setQrPayMode(String qrPayMode) {
		this.qrPayMode = qrPayMode;
	}
	
	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		setOutTradeNo(tradeId);
		setBody(tradeId);
		setSubject(tradeId);
		setTotalFee(amount.toString());
		sign = AlipayPaymentUtil.createUrl(payGateway, SERVICE,
				SIGN_TYPE, getOutTradeNo(), INPUT_CHARSET, getPartner(), getKey(),
				getShowUrl(), getBody(), getTotalFee(), PAYMENT_TYPE, getSellerEmail(), getSubject(),
				getNotifyUrl(), getReturnUrl(), null, null, qrPayMode);

	}
}
