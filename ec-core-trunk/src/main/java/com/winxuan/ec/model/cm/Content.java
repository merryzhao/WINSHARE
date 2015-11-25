/*
 * @(#)Content.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.cm;

import java.io.Serializable;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-10-26
 */
public interface Content extends Serializable {

	Serializable getId();
	
	String getName();
	
	String getUrl();
}
