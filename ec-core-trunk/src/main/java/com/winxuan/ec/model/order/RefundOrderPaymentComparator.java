/*
 * @(#)RefundOrderPaymentComparator.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.order;

import java.util.Comparator;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.payment.Payment;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-30
 */
public class RefundOrderPaymentComparator implements Comparator<OrderPayment> {

	@Override
	public int compare(OrderPayment o1, OrderPayment o2) {
		int result = 0;
		Payment payment1 = o1.getPayment();
		Long paymentId1 = payment1.getId();
		Long paymentTypeId1 = payment1.getType().getId();
		Payment payment2 = o2.getPayment();
		Long paymentId2 = payment2.getId();
		Long paymentTypeId2 = payment2.getType().getId();
		if(paymentTypeId1.equals(Code.PAYMENT_TYPE_ONLINE)){
			result = -1;
		} else if(paymentTypeId2.equals(Code.PAYMENT_TYPE_ONLINE)){
			result = 1;
		} else if (containsPriority(paymentTypeId1)) {
			result = -1;
		} else if (containsPriority(paymentTypeId2)) {
			result = 1;
		} else if (paymentId1.equals(Payment.GIFT_CARD)
				&& paymentId2.equals(Payment.COUPON)) {
			result = -1;
		} else if (paymentId2.equals(Payment.GIFT_CARD)
				&& paymentId1.equals(Payment.COUPON)) {
			result = 1;
		}
		return result;
	}

	private boolean containsPriority(Long payTypeId) {
		return payTypeId.equals(Code.PAYMENT_TYPE_ACCOUNT)
				|| payTypeId.equals(Code.PAYMENT_TYPE_OFFLINE)
				|| payTypeId.equals(Code.PAYMENT_TYPE_ONLINE)
				|| payTypeId.equals(Code.PAYMENT_TYPE_COD);
	}

}
