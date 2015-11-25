package com.winxuan.ec.exception;

import java.io.Serializable;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-9-3 下午2:56:42  --!
 * @description:dc异常
 ********************************
 */
public class DcRuleException extends BusinessException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6483023937354713247L;

	public DcRuleException(Serializable businessObject, String message) {
		super(businessObject, message);
		// TODO Auto-generated constructor stub
	}

}
