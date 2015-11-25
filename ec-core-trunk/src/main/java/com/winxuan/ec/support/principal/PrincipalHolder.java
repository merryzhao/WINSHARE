/*
 * @(#)PrincipalHolder.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.principal;

import com.winxuan.framework.validator.Principal;

/**
 * description
 * @author  yangxinyi
 * @version 1.0,2013-9-4
 */
public class PrincipalHolder {

	private static final ThreadLocal<Principal> HOLDER = new ThreadLocal<Principal>();

	public static void reset() {
		HOLDER.remove();
	}

	public static Principal get() {
		Principal principal = HOLDER.get();
		return principal;
	}

	public static void set(Principal principal) {
		if (principal == null) {
			reset();
		} else {
			HOLDER.set(principal);
		}
	}
	
}
