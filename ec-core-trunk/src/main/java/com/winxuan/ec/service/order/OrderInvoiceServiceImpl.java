/*
 * @(#)OrderInvoiceServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.dao.OrderInvoiceDao;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-9-5
 */
@Service("orderInvoiceService")
@Transactional(rollbackFor = Exception.class)
public class OrderInvoiceServiceImpl implements OrderInvoiceService,Serializable {

	private static final long serialVersionUID = 4428614189050396780L;

	@InjectDao
	private OrderInvoiceDao orderInvoiceDao;
	
	@InjectDao
	private OrderDao orderDao;

	@Override
	public void save(OrderInvoice orderInvoice) {
		orderInvoice.setStatus(new Code(Code.INVOICE_STATUS_NORMAL));
		orderInvoiceDao.save(orderInvoice);
	}

	@Override
	public void remove(OrderInvoice orderInvoice) {
		orderInvoiceDao.remove(orderInvoice);
	}

	@Override
	public void completeTranfer(OrderInvoice orderInvoice){
		orderInvoice.setTransferred(true);
		orderInvoiceDao.update(orderInvoice);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<OrderInvoice> find(Map<String, Object> parameters,
			Pagination pagination) {
		return orderInvoiceDao.find(parameters, pagination);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> findOrderInvoice(Map<String, Object> parameters,
			Pagination pagination) {
		return orderDao.findOrderInvoice(parameters, pagination);
	}



	@Override
	@Deprecated
	public void cancel(Order order) {
		//目前未做发票总金额限制，取消订单不对发票进行处理
	}

	@Override
	public void createOrderInvoice(OrderInvoice orderInvoice) throws OrderStatusException {
		orderInvoice.setTransferred(false);
		orderInvoice.setStatus(new Code(Code.INVOICE_STATUS_NORMAL));
		orderInvoiceDao.save(orderInvoice);
	}

	@Override
	public BigDecimal calcInvoiceMoney(Order order, BigDecimal desireValue) {
		BigDecimal actualMoney = order.getInvoiceMoney();
		if(desireValue == null){
			desireValue = actualMoney;
		}
		boolean isChannel  = order.getCustomer().getChannel().getId().equals(Channel.CHANNEL_AGENT);
		BigDecimal listPrice = order.getListPrice();
		BigDecimal invoiceMoney = isChannel ? (desireValue.compareTo(listPrice) > 0 ? listPrice : desireValue) : 
			actualMoney;
		return invoiceMoney;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public OrderInvoice get(String id) {
		return this.orderInvoiceDao.get(id);
	}

	@Override
	public void update(OrderInvoice orderInvoice) {
		this.orderInvoiceDao.update(orderInvoice);
	}
	
	

}

