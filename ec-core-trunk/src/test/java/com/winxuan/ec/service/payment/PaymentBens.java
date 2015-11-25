package com.winxuan.ec.service.payment;

import com.winxuan.ec.model.payment.PaymentCredential;

/**
 * payment工具
 * @author Heyadong
 *
 */
public class PaymentBens {
	public static PaymentCredential createPaymentCredential(){
		PaymentCredential paymentCredential = new PaymentCredential();
		return paymentCredential;
	}
}
