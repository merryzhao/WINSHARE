/**
 * 
 */
package com.winxuan.ec.exception;

/**
 * @author john
 *
 */
public class ReplenishmentRestrictLimitException extends Exception{

	private static final long serialVersionUID = -7721524018462835836L;
	
	public ReplenishmentRestrictLimitException(String msg){
		super(msg);
	}
}
