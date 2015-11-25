/*
 * @(#)OrderInvoiceDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-8-1
 */
public interface OrderInvoiceDao {
	@Get
	OrderInvoice get(String id);
	@Save
	void save(OrderInvoice orderInvoice);
	
	@Delete
	void remove(OrderInvoice orderInvoice);
	
	@Update
	void update(OrderInvoice orderInvoice);
	
	@Query("from OrderInvoice o ")
	@Conditions({@Condition("o.transferred = :transferred"),
		    @Condition("o.needtransfers = :needtransfers"),
		    @Condition("o.order.id in :orderId"),
		    @Condition("o.id = :invoiceId"),
		    @Condition("o.order.outerId in :outerId"),
			@Condition("o.order.deliveryTime >= :startDevlieryTime"),
			@Condition("o.order.deliveryTime <= :endDeliveryTime"),
			@Condition("o.order.customer.id = :customerId")
			 })
	List<OrderInvoice> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
}
