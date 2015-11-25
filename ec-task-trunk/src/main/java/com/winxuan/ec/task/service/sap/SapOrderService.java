package com.winxuan.ec.task.service.sap;

import java.util.List;

import com.winxuan.ec.model.order.Order;

/**
 * 
 * @author yangxinyi
 *
 */
public interface SapOrderService {
	
	void transferOrder(Order order) throws Exception;

	List<String> listSapOrderDelivery();

	void processFetchOrder(String orderId) throws Exception;
	
}