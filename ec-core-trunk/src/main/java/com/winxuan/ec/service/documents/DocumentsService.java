/*
 * @(#)StockDocumentsService.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.documents;

import java.util.Set;

import com.winxuan.ec.exception.StockDocumentsException;
import com.winxuan.ec.model.documents.StockDocuments;

/**
 * description
 *  
 * @author YangJun
 * @version 1.0, 2015年1月5日
 */
public interface DocumentsService {
	/**
	 * 
	 * @param stockDocumentss
	 */
	void save(Set<StockDocuments> stockDocumentss);
	
	/**
	 * 
	 * @param stockDocuments
	 */
	void documentProcessing(StockDocuments stockDocuments) throws StockDocumentsException;
	
	/**
	 * 
	 * @param stockDocumentss
	 */
	void documentProcessing(Set<StockDocuments> stockDocumentss) throws StockDocumentsException;
}
