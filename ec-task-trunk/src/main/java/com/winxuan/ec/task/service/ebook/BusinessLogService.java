package com.winxuan.ec.task.service.ebook;

import java.util.List;

import com.winxuan.ec.task.model.ebook.BusinessLog;

/**
 * 电子书入库日志记录
 * @author luosh
 *
 */
public interface BusinessLogService {

	void logInfo(BusinessLog businessLog, String description, String errorCode,
			int stepCode, int resultStatus,int statusCode) ;

	List<BusinessLog> find(String name, int resultStatus);

	void save(BusinessLog businessLog);

	List<BusinessLog> find(String keyWord, int i, Long vendorId);
}
