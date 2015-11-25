package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;
/**
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public interface UnionOrderDao {
	
	@Query("from UnionOrder u where u.order.id =?")
	UnionOrder getByOrderId(String orderId);
	
	@Save
	void save(UnionOrder unionOrder);
	
	@Query("from UnionOrder u")
	@Conditions({
		@Condition("u.id = :id"), 
		@Condition("u.createDate >= :startCreateDate"),
		@Condition("u.createDate <= :endCreateDate"),
		@Condition("u.cookieInfo = :cookieInfo"),
		@Condition("u.union.id = :unionId"),
		@Condition("u.union.available = :available"),
		@Condition("u.order.id = :orderId"),
		@Condition("u.order.processStatus.id in :processStatus"),
		@Condition("u.union.name like :name"),
		@Condition("u.order.deliveryTime>=:startDeliveryTime"),
		@Condition("u.order.deliveryTime<=:endDeliveryTime"),
		@Condition("u.order.lastProcessTime>=:startLastProcessTime"),
		@Condition("u.order.lastProcessTime<=:endLastProcessTime")
	})
	@OrderBys({
		@OrderBy("u.order.id desc")
	})
	List<UnionOrder> find(@ParameterMap Map<String, Object> parameters,
			@com.winxuan.framework.dynamicdao.annotation.query.Order Short sort,@Page Pagination pagination);
	
}
