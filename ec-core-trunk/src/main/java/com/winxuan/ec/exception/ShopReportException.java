package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * 
 * @author HideHai
 * @version 0.1 ,2012-5-30
 **/
public class ShopReportException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208267285150080042L;

	public ShopReportException(Serializable businessObject, String message) {
		super(businessObject, message);
	}

}

