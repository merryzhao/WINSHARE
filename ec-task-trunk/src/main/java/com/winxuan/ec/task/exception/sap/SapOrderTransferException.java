package com.winxuan.ec.task.exception.sap;

/**
 * 
 * @author yangxinyi
 *
 */
public class SapOrderTransferException extends Exception {

	private static final long serialVersionUID = -8999799844208886089L;
	
	public SapOrderTransferException() {
		super();
	}
	
	public SapOrderTransferException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SapOrderTransferException(String message) {
		super(message);
	}
	
	public SapOrderTransferException(Throwable cause) {
		super(cause);
	}
	
}