package com.winxuan.ec.exception;

import java.io.Serializable;

/**
 * 凭证异常
 * @author Heyadong
 * @version 1.0 , 2012-2-8
 */
public class PaymentCredentialException extends BusinessException {
	
	private static final long serialVersionUID = -7555532455059573727L;
	
	public PaymentCredentialException(Serializable businessObject, String message) {
		super(businessObject, message);
	}
	
	
	

}
