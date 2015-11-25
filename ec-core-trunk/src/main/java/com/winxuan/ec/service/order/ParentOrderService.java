/**
 * 
 */
package com.winxuan.ec.service.order;

import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-10-16
 */
public interface ParentOrderService {

	ParentOrder get(String id);
	
	void save(ParentOrder parentOrder);
	
	void update(ParentOrder parentOrder);
	
	/**
	 * 通过父订单取消所有子订单
	 * @param parentOrder 父订单
	 * @param proessStatus 处理状态
	 * @param operator 处理人
	 */
	void cancel(ParentOrder parentOrder,Code proessStatus,User operator) throws OrderStatusException,
	CustomerAccountException, PresentCardException, PresentException, ReturnOrderException;
	
	/**
	 * 通过父订单作废所有子订单
	 * @param parentOrder 父订单
	 */
	void archive(ParentOrder parentOrder,Employee operator) throws OrderStatusException;
	
	/**
	 * 返回父订单状态
	 * @param parentOrder
	 * @return
	 */
	int getCurrentStatus(ParentOrder parentOrder);
}
