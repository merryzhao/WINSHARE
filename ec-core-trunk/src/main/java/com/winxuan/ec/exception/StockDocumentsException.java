/*
 * @(#)StockDocumentsExce.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.exception;

import java.io.Serializable;

import com.winxuan.ec.model.documents.StockDocuments;


/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2015-1-9
 */
public class StockDocumentsException extends BusinessException {

	private static final long serialVersionUID = 4524417921941115643L;
	
	/**
	 * 
	 * @param stockDocuments
	 * @param message
	 */
	public StockDocumentsException(StockDocuments stockDocuments, String message) {
		super(stockDocuments, message);
	}

	/**
	 * @param businessObject
	 * @param message
	 */
	public StockDocumentsException(Serializable businessObject, String message) {
		super(businessObject, message);
	}
	
}
