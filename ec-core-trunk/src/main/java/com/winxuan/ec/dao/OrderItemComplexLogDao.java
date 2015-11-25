package com.winxuan.ec.dao;

import com.winxuan.ec.model.order.OrderItemComplexLog;
import com.winxuan.framework.dynamicdao.annotation.Save;


/**
 * 
 * @author: yangxinyi
 * @date: 2014-3-4
 */
public interface OrderItemComplexLogDao {
	
	@Save
	void save(OrderItemComplexLog orderItemComplexLog);
	
}

