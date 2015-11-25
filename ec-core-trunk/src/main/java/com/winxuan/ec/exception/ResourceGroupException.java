package com.winxuan.ec.exception;

import com.winxuan.ec.model.authority.ResourceGroup;

/**
 * exception
 * @author sunflower
 *
 */
public class ResourceGroupException extends BusinessException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5813646476100031228L;

	
	public ResourceGroupException(ResourceGroup resourceGroup, String message) {
		super(resourceGroup,message);
	}


}
