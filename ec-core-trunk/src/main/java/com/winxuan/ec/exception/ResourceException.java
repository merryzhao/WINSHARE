package com.winxuan.ec.exception;

import com.winxuan.ec.model.authority.Resource;

/**
 * resource exception
 * @author sunflower
 *
 */
public class ResourceException extends BusinessException {



	/**
	 * 
	 */
	private static final long serialVersionUID = 5651233245208882458L;
	
	
	public ResourceException(Resource resource, String message) {
		super(resource,message);
	}



}
