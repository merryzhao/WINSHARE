/*
 * @(#)StockDocumentsDao.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.documents.StockDocuments;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 *  
 * @author YangJun
 * @version 1.0, 2015年1月5日
 */
public interface StockDocumentsDao {
	@Save
	void save(StockDocuments stockDocuments);
}
