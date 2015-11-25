package com.winxuan.ec.exception;

import java.io.Serializable;
/**
 * 手机10086支付退款异常
 * @author youwen
 *
 */
public class ShouJiZhiFuRefunderException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6819646657084843984L;

	public ShouJiZhiFuRefunderException(Serializable businessObject,
			String message) {
		super(businessObject, message);
	}

}
