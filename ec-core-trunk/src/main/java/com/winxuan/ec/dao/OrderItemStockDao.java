package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderItemStock;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;


/**
 * 
 * @author: HideHai 
 * @date: 2013-10-28
 */
public interface OrderItemStockDao {
	
	
	@Save
	void save(OrderItemStock orderItemStock);
	
	@Update
	void update(OrderItemStock orderItemStock);
	
	@SaveOrUpdate
	void saveOrUpdate(OrderItemStock orderItemStock);

	@Query("FROM OrderItemStock oik WHERE oik.orderItem.id = ?")
	OrderItemStock getByOrderItem(OrderItem orderItem);
}

