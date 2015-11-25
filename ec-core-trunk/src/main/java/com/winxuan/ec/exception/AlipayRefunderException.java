package com.winxuan.ec.exception;

import java.io.Serializable;
/**
 * 支付宝退款异常
 * @author youwen
 *
 */
public class AlipayRefunderException extends BusinessException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408619846062941394L;
	

	public AlipayRefunderException(Serializable businessObject, String message) {
		super(businessObject, message);
	}
	
}
