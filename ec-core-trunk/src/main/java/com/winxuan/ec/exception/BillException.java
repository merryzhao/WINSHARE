package com.winxuan.ec.exception;

/**
 * 帐单业务
 * @author heyadong
 *
 */
public class BillException extends Exception{
	
	private static final long serialVersionUID = -8200580847848553520L;

	public BillException(String msg){
		super(msg);
	}
}
