package com.winxuan.ec.task.dao.ebook;

import java.util.List;

import com.winxuan.ec.task.model.ebook.BusinessLog;
/**
 * 日志记录
 * @author luosh
 *
 */
public interface BusinessLogDao {
	
	void save(BusinessLog businessLog);
	
	List<BusinessLog> find(String name, int resultStatus, Long vendorID);
	
	List<BusinessLog> find(String name, int resultStatus);
}
