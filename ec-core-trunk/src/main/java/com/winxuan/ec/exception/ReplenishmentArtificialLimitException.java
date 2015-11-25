package com.winxuan.ec.exception;

/**
 * 
 * @author john
 *
 */
public class ReplenishmentArtificialLimitException extends Exception{
	
	private static final long serialVersionUID = -8200580847848553520L;

	public ReplenishmentArtificialLimitException(String msg){
		super(msg);
	}
}
