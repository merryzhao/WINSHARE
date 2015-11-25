/*
 * @(#)DeliveryTypeDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-7-15
 */
public interface DeliveryTypeDao {

	@Get
	DeliveryType get(Long id);

	@Query("from DeliveryType d")
	@Conditions({		
		@Condition("d.available=:available")
	})	
	List<DeliveryType> find(@Parameter("available") Boolean available);

	@Update
	void update(DeliveryType deliveryType);

	@Query(sqlQuery=true,value="SELECT deliverytype FROM delivery_type_macth"
	        + " WHERE flag = 1 AND (FIND_IN_SET(?,channel) OR channel = '1000') AND FIND_IN_SET(?,area) " 
	        + " AND starttime < NOW() AND (endtime IS NULL OR endtime > NOW()) ORDER BY priority DESC")
	Long findDeliveryType(Long channelId, Long areaId);

}
