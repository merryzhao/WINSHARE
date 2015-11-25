/*
 * @(#)BatchPayService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.util.Map;

import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.ec.model.order.OrderBatchPay;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-11
 */
public interface BatchPayService {

	BatchPay get(String id);
	
	/**
	 * 根据摘要获取批量支付信息
	 * @param id
	 * @return
	 */
	BatchPay getByDigest(String digest);
	
	void update(BatchPay batchPay);

	void save(BatchPay batchPay);
	
	/**
	 * 根据订单数组获取批量支付对象
	 * @return
	 */
	BatchPay pay(String[] idArray) throws OrderException;
	
	/**
	 * 根据订单数组和支付平台获取批量支付对象
	 * @return
	 */
	BatchPay pay(String[] idArray, Bank bank) throws OrderException;
	
	/**
	 * 网银支付回调处理
	 * @param callBackForm
	 * @param requestParams
	 * @return
	 * @throws OrderStatusException
	 *             如果订单不处于已提交状态或者处于已提交状态但不是先款后货类型，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 * @throws OrderStockException
	 *             如果订单商品可用量不足，抛出此异常
	 * @throws PaymentCredentialException 
	 * 			      如果支付凭证已经使用,抛出此异常     
	 * @throws ReturnOrderException 
	 */
	BatchPay onlinePayCallback(CallBackForm callBackForm, Map<String, String[]> requestParams) throws OrderStatusException,
	OrderStockException, CustomerAccountException, PaymentCredentialException, ReturnOrderException;
	
	String getParames(@SuppressWarnings("rawtypes") Map requestParams);
	
	OrderBatchPay getByOrderId(String orderId);

}
