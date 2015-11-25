package com.winxuan.ec.support.exception;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.winxuan.ec.model.exception.ExceptionLog;
import com.winxuan.ec.service.exception.ExceptionService;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
@Component
public class ExceptionConsumerImpl implements ExceptionConsumer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6479305149060483271L;
	
	private ExceptionService exceptionService;

	public void setExceptionService(ExceptionService exceptionService) {
		this.exceptionService = exceptionService;
	}

	@Override
	public void save(ExceptionLog exceptionLog) {
		exceptionService.save(exceptionLog);
	}

}
