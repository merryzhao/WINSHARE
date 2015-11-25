package com.winxuan.ec.exception;

import java.io.Serializable;
/**
 * 财付通退款异常
 * @author youwen
 *
 */
public class TenPayRefunderException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7114374589028707579L;

	public TenPayRefunderException(Serializable businessObject, String message) {
		super(businessObject, message);
	}

}
