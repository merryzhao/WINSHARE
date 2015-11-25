/*
 * @(#)SolrRequestException
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.exception.search;

/**
 * description
 * @author  huangyixiang
 * @version 1.0,2011-11-3
 */
public class SolrRequestException extends Exception{
	private static final long serialVersionUID = -1259830666401974000L;

	public SolrRequestException() {
		super();
	}

	public SolrRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public SolrRequestException(String message) {
		super(message);
	}

	public SolrRequestException(Throwable cause) {
		super(cause);
	}

}

