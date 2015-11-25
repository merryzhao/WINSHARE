package com.winxuan.ec.exception;

import java.io.Serializable;

/**import com.winxuan.ec.exception.EmployeeBussinessException;
 * 
 * @author bianlin
 *
 */
public class EmployeeBussinessException extends BusinessException  {
	private static final long serialVersionUID = 6483023937354713247L;
	
	public EmployeeBussinessException(Serializable businessObject,
			String message) {
		super(businessObject, message);
		// TODO Auto-generated constructor stub
	}
}
