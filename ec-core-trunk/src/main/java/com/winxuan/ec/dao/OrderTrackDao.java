/*
 * @(#)OrderTrackDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderTrack;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.dynamicdao.annotation.query.Order;

/**
 * 
 * description
 * @author liyang
 * @version 1.0,2011-7-21
 */
public interface OrderTrackDao {
	@Save
	void save(OrderTrack orderTrack);
	
	@Query("from OrderTrack ot")
	@Conditions({
	@Condition("ot.type.id in :type"),
	@Condition("ot.createTime >=:startTrackDate"),
	@Condition("ot.createTime<= :endTrackDate"),
	@Condition("ot.order.createTime>=:startOrderDate"),
	@Condition("ot.order.createTime<=:endOrderDate"),
	@Condition("ot.order.deliveryTime>=:startDeliveryDate2"),
	@Condition("ot.order.deliveryTime<=:endDeliveryDate"),
	@Condition("ot.order.id=:orderId"),
	@Condition("ot.employee.realName=:employee"),
	@Condition("ot.order.customer.name=:name")
	})
	@OrderBys({
	@OrderBy("ot.createTime desc"), 
	@OrderBy("ot.createTime asc"),
	@OrderBy("o.createTime desc"),
	@OrderBy("o.createTime asc"),
	@OrderBy("o.deliveryTime desc"),
	@OrderBy("o.deliveryTime asc")
	})
	List<OrderTrack> findOrderTrack(@ParameterMap Map<String,Object> parameters,@Order Short orderIndex,@Page Pagination pagination);
}
