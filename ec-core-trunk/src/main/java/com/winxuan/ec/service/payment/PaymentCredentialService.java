package com.winxuan.ec.service.payment;

import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-6-5 下午5:05:42  --!
 * 
 ********************************
 */
public interface PaymentCredentialService {

	

	void save(PaymentCredential paymentCredential);

	boolean isExisted(Payment payment, String outerId);
	
	PaymentCredential getByOuterIdAndPayment(Payment payment, String outerId);
	
	PaymentCredential get(Long id);
	
}
