/*
 * @(#)OrderShipperInfoDao.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderShipperInfo;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0, 2015年1月15日
 */
public interface OrderShipperInfoDao {
	@Save
	void save(OrderShipperInfo orderShipperInfo);

	@Get
	OrderShipperInfo get(String orderId);

	@Query(sqlQuery = true, value = "SELECT osi._order,osi.errormessage  FROM order_shipper_info osi LEFT JOIN _order o ON osi._order=o.id"
			+ " WHERE o.processstatus IN (8001,8002) AND osi.status IN (0,2) ORDER BY osi._order DESC")
	List<Map<String, String>> find(@Page Pagination pagination);

	@Query(value = "SELECT COUNT(osi._order) FROM order_shipper_info osi LEFT JOIN _order o ON o.id=osi._order"
			+ " WHERE o.processstatus IN (8001,8002) AND osi.status IN (0,2)", sqlQuery = true)
	long countNeedAuditShipperInfo();

	@Update
	void update(OrderShipperInfo orderShipperInfo);
}
