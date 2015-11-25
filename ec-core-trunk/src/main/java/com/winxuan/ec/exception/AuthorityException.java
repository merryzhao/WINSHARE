package com.winxuan.ec.exception;

import com.winxuan.ec.model.user.Employee;

/**
 * 权限exception
 * @author sunflower
 *
 */
public class AuthorityException extends BusinessException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6251830142347873092L;
	
	public AuthorityException(Employee employee, String message) {
		super(employee, message);
	}


}
