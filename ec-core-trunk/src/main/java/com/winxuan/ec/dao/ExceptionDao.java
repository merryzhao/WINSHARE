package com.winxuan.ec.dao;

import com.winxuan.ec.model.exception.ExceptionLog;
import com.winxuan.framework.dynamicdao.annotation.Save;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
public interface ExceptionDao {
	@Save
	void save(ExceptionLog exceptionLog);
}
