package com.winxuan.ec.exception;

import java.io.Serializable;
/**
 * 中国银联退款异常
 * @author youwen
 *
 */
public class ChinaPayRefunderException extends BusinessException{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021196280492765985L;

	public ChinaPayRefunderException(Serializable businessObject, String message) {
		super(businessObject, message);
	}

}
