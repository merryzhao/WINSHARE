package com.winxuan.ec.service.order;

import com.winxuan.ec.model.order.OrderCollection;

/**
 * 订单集货
 * @author heshuai
 *
 */
public interface OrderCollentionService {
	
	/**
	 * 根据订单编码查询该订单是否集货。
	 * @param orderId
	 * @return
	 */
	OrderCollection getOrderCollention(String orderId);
	
	void saveOrUpdate(OrderCollection orderCollection);

}
