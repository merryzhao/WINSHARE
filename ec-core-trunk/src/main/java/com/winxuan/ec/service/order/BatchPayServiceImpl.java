/*
 * @(#)BatchPayServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BatchPayDao;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderBatchPay;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-11
 */
@Service("batchPayService")
@Transactional(rollbackFor = Exception.class)
public class BatchPayServiceImpl implements BatchPayService {
	
	private static final Log LOG = LogFactory.getLog(BatchPayServiceImpl.class);

	@InjectDao
	private BatchPayDao batchPayDao;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BatchPay get(String id) {
		return batchPayDao.get(id);
	}

	public void save(BatchPay batchPay) {
		batchPayDao.save(batchPay);
	}

	public void update(BatchPay batchPay) {
		batchPayDao.update(batchPay);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public BatchPay getByDigest(String digest) {
		return batchPayDao.getByDigest(digest);
	}
	
	
	@Override
	public BatchPay pay(String[] idArray,Bank bank) throws OrderException {
		return findDigestBatchy(idArray,bank);
	}
	

	@Override
	public BatchPay pay(String[] idArray) throws OrderException {
		return findDigestBatchy(idArray,null);
	}
	
	private BatchPay findDigestBatchy(String[] idArray,Bank bank) throws OrderException{
		List<Order> orderList = getOrderList(idArray);
		StringBuffer sb = new StringBuffer();
		for(Order order : orderList){
			sb.append(order.getId());
		}
		String bankId = "";
		if(bank!=null){
			bankId = bank.getId().toString();
		}
		String digest = DigestUtils.md5Hex(sb.toString()+bankId);
		LOG.info(String.format("<bank:%s>,<digest:%s>", bankId,digest));
		BatchPay batchPay = getByDigest(digest);
		if(batchPay != null){
			return batchPay;
		}
		batchPay = new BatchPay();
		BigDecimal totalMoney = BigDecimal.ZERO;
		for(Order order : orderList){
			totalMoney = totalMoney.add(order.getRequidPayMoney());
			batchPay.addOrder(order);
		}
		batchPay.setCreateTime(new Date());
		batchPay.setSuccess(false);
		batchPay.setTotalMoney(totalMoney);
		batchPay.setDigest(digest);
		save(batchPay);
		return batchPay;
	}
	
	private List<Order> getOrderList(String[] idArray)  {
		if(idArray == null || idArray.length == 0 ){
			throw new IllegalArgumentException("订单号错误");
		}
		idArray =  new  HashSet<String>(Arrays.asList(idArray)).toArray(new String[0]);
		
		List<Order> orderList = new ArrayList<Order>();
		Arrays.sort(idArray);
		for(String id : idArray){
			Order order = orderService.get(id);
			orderList.add(order);
		}
		return orderList;
	}
	
	
	@Override
	public BatchPay onlinePayCallback(CallBackForm callBackForm, Map<String, String[]> requestParams) throws OrderStatusException,
						OrderStockException, CustomerAccountException, PaymentCredentialException, ReturnOrderException{
		String batchPayNo = callBackForm.getBatchId();
		if (StringUtils.isBlank(batchPayNo)) {
			throw new RuntimeException("tradeId is blank");
		}
		
		BatchPay batchPay = get(batchPayNo);
		if (batchPay == null) {
			throw new RuntimeException(batchPayNo + " is not exist");
		}
		
		String params = getParames(requestParams);
		if(!params.equals(batchPay.getParams())){
			batchPay.setParams(params);
		}
		batchPay.setSuccess(true);
		update(batchPay);
		
		doPay(batchPay.getOrderList(), callBackForm);
		return batchPay;
	}
	
	private void doPay(Set<Order> orders, CallBackForm callBackForm) throws OrderStatusException, 
					OrderStockException, CustomerAccountException, PaymentCredentialException, ReturnOrderException{
		Customer payCustomer = new Customer();
		List<Order> orderList = new ArrayList<Order>(orders);
		if(orderList != null && orderList.size() > 0){
			payCustomer =  orderList.get(0).getCustomer();
			PaymentCredential paymentCredential = new PaymentCredential();
			paymentCredential.setCustomer(payCustomer);
			paymentCredential.setMoney(callBackForm.getMoney());
			paymentCredential.setOperator(new Employee(Employee.SYSTEM));
			paymentCredential.setOuterId(callBackForm.getOuterTradeNo());
			paymentCredential.setPayer(payCustomer.getName());
			paymentCredential.setPayment(paymentService.get(callBackForm.getPaymentId()));
			paymentCredential.setPayTime(new Date());
			orderService.pay(orderList, paymentCredential, new Employee(Employee.SYSTEM));
			
			LOG.info(callBackForm.getBatchId() +" success");
		}else{
			LOG.info(callBackForm.getBatchId() +" orderList is empty");
		}
	}
	
	public String getParames(@SuppressWarnings("rawtypes") Map requestParams){
		StringBuffer sb = new StringBuffer();
		for (Object param : requestParams.keySet()) {
			String name = (String) param;
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			sb.append(name).append("=").append(valueStr).append("&");
		}
		return sb.toString();
	}

	@Override
	public OrderBatchPay getByOrderId(String orderId) {
		return batchPayDao.getByOrderId(orderId);
	}
	
}
