package com.winxuan.ec.service.order;

import java.util.List;

import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.exception.OrderDcMatchException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderItemStock;
import com.winxuan.ec.model.user.User;

/**
 * 
 * @author: HideHai
 * @date: 2013-7-25
 */
public interface OrderDcService {
	
	OrderDistributionCenter createOrderDcNew(Order order) throws OrderDcMatchException;
	
	/**
	 * 修改订单实际发货的dc信息,并且记录日志
	 * 实际发货由业务人员根据,dc实际情况进行手动分配.在未分配的情况下.实际发货dc和应发货dc一致.
	 * @param order
	 * @param target
	 * @return 修改是否成功
	 * @throws BusinessException
	 */
	@Deprecated
	Boolean updateOrderDcOriginal(Order order,Code dc,User user) throws BusinessException;
	
	/**
	 * 保存订单项的库存信息
	 * @param orderItem
	 * @throws BusinessException
	 */
	void saveOrderItemStock(Order order) throws OrderStockException;
	
	/**
	 * 获取订单项的库存信息
	 * @param orderItem
	 * @return
	 */
	OrderItemStock getOrderItemStock(OrderItem orderItem);

	@Deprecated
	void updateOrderItemStock(Order order) throws OrderStockException;
	
	void getDcSatisfactionRate(Order o,List<DcPriority> dps,List<Long> exculdeDcs);
	
	void satisfactionRateSort(List<DcPriority> dps);
		
}
