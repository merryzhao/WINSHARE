/*
 * @(#)DeliveryInfo.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-7-15
 */
public interface DeliveryInfoDao {
	@Get
	DeliveryInfo get(Long id);

	@Update
	void update(DeliveryInfo deliveryInfo);

	@Delete
	void delete(DeliveryInfo deliveryInfo);

	@Query("from DeliveryInfo d ")
	@Conditions({ @Condition("d.area=:area"),
		@Condition("d.available=true"),
			@Condition("d.deliveryType=:deliveryType"),
			@Condition("d.dc.id=:dc")})
	@OrderBys({ 
		@OrderBy("d.deliveryType.sort asc")})
	List<DeliveryInfo> find(@Parameter("area") Area area,
			@Parameter("deliveryType") DeliveryType deliveryType, @Parameter("dc")Long dc, @Order short orderBy);
	
	@Query("from DeliveryInfo d where d.dc.id = 110003 and d.available=true and d.deliveryType = ? and d.area = ?")
	DeliveryInfo getByDeliveryTypeAndArea(DeliveryType deliveryType, Area area);

}
