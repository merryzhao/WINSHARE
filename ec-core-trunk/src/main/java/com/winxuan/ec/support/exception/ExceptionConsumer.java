package com.winxuan.ec.support.exception;

import com.winxuan.ec.model.exception.ExceptionLog;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
public interface ExceptionConsumer {
	void save(ExceptionLog exceptionLog);
}
