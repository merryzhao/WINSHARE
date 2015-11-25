/*
 * @(#)RefundService.java
 *
 */

package com.winxuan.ec.service.refund;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.RefundExpiredException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.exception.UnsupportBankException;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  huangyixiang
 * @version 2013-5-15
 */
public interface RefundService {
	
	RefundCredential get(String id);
	
	List<RefundCredential> findWaitRefundCredential(Integer count);

	void refund2Card(Order order, BigDecimal refundMoney) throws ReturnOrderException, UnsupportBankException, RefundExpiredException;
	
	void updateRefundCredential(RefundCredential refundCredential);
	
	void saveOrUpdateRefundCredential(RefundCredential refundCredential);
	
	/**
	 * 通过outerId,PeymentId,获取退款id
	 * @param outerId
	 * @param paymentId
	 * @return 退款id
	 */
	RefundCredential getRefundCredentialId(Map<String, Object> parameters);
	
	/**
	 * 检查银行是否可退款
	 * 客户，中启取消订单手续费扣除,系统取消无需手续费
	 * @param order
	 * @param refundMoney
	 * @param bank
	 * @param checkOrder 检查订单是否可退款
	 * @param outerId
	 * @throws ReturnOrderException
	 * @throws UnsupportBankException
	 * @throws RefundExpiredException
	 */
	void refund2Card(Order order, BigDecimal refundMoney, Bank bank,
			boolean checkOrder, String outerId) throws ReturnOrderException,
			UnsupportBankException, RefundExpiredException;

	List<RefundCredential> findByOrder(String orderId);
	
	List<RefundCredential> find(Map<String, Object> params, Pagination pagination, short sort);
	
	List<Payment> getPaymentList();
	
	RefundMessage get(Long id);
	List<RefundMessage> find(Long id,boolean available);
	List<RefundMessage> findRefundMessage(Map<String, Object> params, Pagination pagination, short sort);
	void saveorupdate(RefundMessage refundMessage);
	
}
