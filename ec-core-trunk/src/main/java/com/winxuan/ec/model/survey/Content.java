/*
 * @(#)Content.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.survey;

import java.io.Serializable;

/**
 * 
 * @author sunflower
 *
 */
public interface Content extends Serializable {

	Serializable getId();
	
	String getTitle();
	
}
