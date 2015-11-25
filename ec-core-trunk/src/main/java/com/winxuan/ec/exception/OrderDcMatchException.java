package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 *******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-29 下午2:47:06 --!
 * @description:订单dc信息异常
 ******************************** 
 */
public class OrderDcMatchException extends OrderInitException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4372569860495394972L;

	public OrderDcMatchException(Order order, String message) {
		super(order, message);
	}

}
