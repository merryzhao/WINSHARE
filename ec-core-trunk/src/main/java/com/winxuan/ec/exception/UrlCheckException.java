/*
 * @(#)UrlCheckException.java
 *
 */

package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * description
 * @author  huangyixiang
 * @version 2013-5-17
 */
public class UrlCheckException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209795426279289681L;

	public UrlCheckException(Serializable businessObject, String message) {
		super(businessObject, message);
	}

}
