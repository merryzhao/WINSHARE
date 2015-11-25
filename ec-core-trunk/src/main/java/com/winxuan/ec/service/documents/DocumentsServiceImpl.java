/*
 * @(#)StockDocumentsServiceImpl.java
 *
 * Copyright 2015 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.documents;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.StockDocumentsDao;
import com.winxuan.ec.exception.StockDocumentsException;
import com.winxuan.ec.model.documents.StockDocuments;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.services.pss.model.vo.StockDocumentsVo;
import com.winxuan.services.pss.service.StockDocumentsService;
import com.winxuan.services.support.convert.ConvertUtils;
import com.winxuan.services.support.exception.RemoteBusinessException;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0, 2015年1月5日
 */
@Service("documentsService")
@Transactional(rollbackFor = Exception.class)
public class DocumentsServiceImpl implements DocumentsService {
	@InjectDao
	private StockDocumentsDao stockDocumentsDao;

	@Autowired
	private StockDocumentsService remoteStockDocumentsService;

	@Override
	public void save(Set<StockDocuments> stockDocumentss) {
		for (StockDocuments stockDocuments : stockDocumentss) {
			stockDocumentsDao.save(stockDocuments);
		}
	}

	@Override
	public void documentProcessing(StockDocuments stockDocuments) throws StockDocumentsException {
		stockDocumentsDao.save(stockDocuments);

		StockDocumentsVo stockDocumentsVo = new StockDocumentsVo();
		ConvertUtils.convert(stockDocuments, stockDocumentsVo);

		try {
			remoteStockDocumentsService.documentsProcessing(stockDocumentsVo);
		} catch (RemoteBusinessException e) {
			throw new StockDocumentsException(stockDocuments, e.getMessage());
		}
	}

	@Override
	public void documentProcessing(Set<StockDocuments> stockDocumentss) throws StockDocumentsException {
		save(stockDocumentss);

		Set<StockDocumentsVo> stockDocumentsVos = new HashSet<StockDocumentsVo>();
		for (StockDocuments stockDocuments : stockDocumentss) {
			StockDocumentsVo stockDocumentsVo = new StockDocumentsVo();
			ConvertUtils.convert(stockDocuments, stockDocumentsVo);
			stockDocumentsVos.add(stockDocumentsVo);
		}

		try {
			remoteStockDocumentsService.documentsProcesses(stockDocumentsVos);
		} catch (RemoteBusinessException e) {
			StockDocumentsVo stockDocumentsVo = (StockDocumentsVo)e.getRemoteBusinessException();
			StockDocuments stockDocuments = new StockDocuments();
			ConvertUtils.convert(stockDocumentsVo, stockDocuments);
			throw new StockDocumentsException(stockDocuments, e.getMessage());
		}
		
	}
}
