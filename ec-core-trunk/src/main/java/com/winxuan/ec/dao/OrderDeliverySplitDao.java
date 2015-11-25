/*
 * @(#)OrderTrackDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;


/**
 * @author HideHai
 */
public interface OrderDeliverySplitDao {

	@Save
	void save(OrderDeliverySplit orderDeliverySplit);

	@Query("from OrderDeliverySplit os ")
	@Conditions({
		@Condition("os.order.id = :orderId"),
		@Condition("os.deliveryTime >=:deliveryTimeBegin"),
		@Condition("os.deliveryTime <=:deliveryTimeEnd")
	})
	@OrderBys({
		@OrderBy("os.deliveryTime desc"), 
		@OrderBy("os.deliveryTime asc")
	})
	List<OrderDeliverySplit> find(@ParameterMap Map<String,Object> parameters,@Order Short orderBy,@Page Pagination pagination);
}
