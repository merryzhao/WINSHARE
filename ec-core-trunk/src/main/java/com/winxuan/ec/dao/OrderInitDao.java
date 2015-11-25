package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderInit;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;

/**
 * 订单初始化DAO
 * @author heshuai
 *
 */
public interface OrderInitDao {
   
	@Save
	void save(OrderInit orderInit);
	
	@Update
	void update(OrderInit orderInit);
		
}
