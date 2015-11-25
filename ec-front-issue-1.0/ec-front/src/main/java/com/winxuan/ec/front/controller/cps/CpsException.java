package com.winxuan.ec.front.controller.cps;
/**
 * descriptionXXX
 * @author  zhoujun
 * @version 1.0,2011-11-7
 */
public class CpsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4170644067757009078L;
	
	public CpsException(){
		super();
	}
	
	public CpsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CpsException(String message) {
		super(message);
	}

	public CpsException(Throwable cause) {
		super(cause);
	}
}
