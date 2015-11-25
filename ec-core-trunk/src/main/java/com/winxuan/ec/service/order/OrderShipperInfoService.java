/*
 * @(#)OrderShipperService.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.order;




import java.util.List;

import com.winxuan.ec.exception.OrderShipperInfoException;
import com.winxuan.ec.exception.OrderShipperInfoMatchException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderShipperInfo;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 *  
 * @author YangJun
 * @version 1.0, 2015年1月14日
 */
public interface OrderShipperInfoService {
	
	/**
	 * 匹配订单运单号、承运商等信息，保存承运商
	 * @param order
	 * @throws IllegalStateException  if order status is not new status or waiting picking status
	 */
	void matchingOrderShipperInfo(Order order) throws OrderShipperInfoMatchException;
	
	/**
	 * 订单发货、取消状态回填给DMS
	 * @param order
	 * @throws IllegalArgumentException if order is null
	 * @throws IllegalStateException  if order status is not delivery or cancel status
	 */
	void backfillOrderShipperInfo(Order order) throws OrderShipperInfoException;
	
	/**
	 * 根据EC订单号查询承运商信息
	 * @param orderId
	 * @return
	 */
	OrderShipperInfo getOrderShipperInfo(String orderId);
	
	/**
	 * 保存货运公司信息
	 * @param orderShipperInfo
	 */
	void save(OrderShipperInfo orderShipperInfo);
	
	/**
	 * 查询需要审核的订单承运商信息
	 * @param parameters
	 * @param sort
	 * @param pagination
	 * @return
	 */
	List<OrderShipperInfo> find(Pagination pagination);
	
	/**
	 * 获得需要审核承运商信息的总数
	 * @return
	 */
	long countNeedAuditShipperInfo();
	
	
	void update(OrderShipperInfo orderShipperInfo);
	
}
