package com.winxuan.ec.service.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.PaymentCredentialDao;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.framework.dynamicdao.InjectDao;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-6-5 下午5:05:55  --!
 * 
 ********************************
 */
@Service("paymentCredentialService")
@Transactional(rollbackFor = Exception.class)
public class PaymentCredentialServiceImpl implements PaymentCredentialService{

	@InjectDao
	private PaymentCredentialDao paymentCredentialDao;
	
	@Override
	public void save(PaymentCredential paymentCredential) {
		paymentCredentialDao.save(paymentCredential);
		
	}

	@Override
	public boolean isExisted(Payment payment, String outerId) {
		return paymentCredentialDao.isExisted(payment, outerId);
	}

	@Override
	public PaymentCredential getByOuterIdAndPayment(Payment payment,
			String outerId) {
		return paymentCredentialDao.getByOuterIdAndPayment(payment, outerId);
	}

	@Override
	public PaymentCredential get(Long id) {
		return paymentCredentialDao.get(id);
	}

}
