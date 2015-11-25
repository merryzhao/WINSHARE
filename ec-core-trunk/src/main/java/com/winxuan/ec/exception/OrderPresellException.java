package com.winxuan.ec.exception;

import com.winxuan.ec.model.order.Order;

/**
 * 订单预售异常
 * @author heyadong
 * @since 2012-12-21 下午2:33:36
 */
public class OrderPresellException extends OrderException{
	private static final long serialVersionUID = 5916309326736724818L;
	public OrderPresellException(Order order, String message) {
		super(order, message);
	}

	
	

}
