package com.winxuan.ec.exception;

import java.io.Serializable;
/**
 * 退款异常
 * @author youwen
 *
 */
public class ChinaPayMobileRefundException  extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3726186645532453706L;

	public ChinaPayMobileRefundException(Serializable businessObject,
			String message) {
		super(businessObject, message);
	}

}
