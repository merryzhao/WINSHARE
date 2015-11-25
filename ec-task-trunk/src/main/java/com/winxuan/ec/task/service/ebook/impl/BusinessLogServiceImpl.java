package com.winxuan.ec.task.service.ebook.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.ebook.BusinessLogDao;
import com.winxuan.ec.task.model.ebook.BusinessLog;
import com.winxuan.ec.task.service.ebook.BusinessLogService;

/**
 * 电子书入库日志记录
 * @author luosh
 *
 */
@Service("businessLogService")
@Transactional(rollbackFor=Exception.class)
public class BusinessLogServiceImpl implements BusinessLogService {
	
	@Autowired
	BusinessLogDao businessLogDao;

	@Override
	public void logInfo(BusinessLog businessLog, String description,
			String errorCode, int stepCode, int resultStatus, int statusCode) {
		businessLog.setDiscription(description);
		businessLog.setErrorCode(errorCode);
		businessLog.setStepCode(stepCode);
		businessLog.setResultStatus(resultStatus);
		businessLog.setStatusCode(statusCode);
		businessLog.setCreateDate(new Date(System.currentTimeMillis()));
		businessLogDao.save(businessLog);
	}

	@Override
	public List<BusinessLog> find(String name, int resultStatus){
		return businessLogDao.find(name, resultStatus);
	}

	@Override
	public void save(BusinessLog businessLog) {
		businessLogDao.save(businessLog);
		
	}

	@Override
	public List<BusinessLog> find(String keyWord, int resultStatus, Long vendorId) {
		return businessLogDao.find(keyWord, resultStatus, vendorId);
	}
}
