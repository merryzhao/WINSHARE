package com.winxuan.ec.support.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.support.interceptor.ExceptionLog;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-22
 */
//@Component
public class ExceptionLogImpl implements ExceptionLog {
	private static final Logger LOG = Logger.getLogger(ExceptionLogImpl.class);
	private static final int MAGIC_ZERO = 0;
	private static final int MAGIC_500 = 500;
	
	@Autowired
	private ExceptionConsumer exceptionConsumer;

	@Override
	public void log(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		if(ex != null && !(ex instanceof SQLException)){
			com.winxuan.ec.model.exception.ExceptionLog exceptionLog = com.winxuan.ec.support.exception.ExceptionUtil.newExceptionLog(request);
			if(handler != null){
				exceptionLog.setSource(handler.toString());
			}
			if(ex instanceof BusinessException){
				BusinessException be = (BusinessException)ex;
				String str = be.getMessage();
				if(str != null){
					exceptionLog.setMessage(str.length() > MAGIC_500 ? str.substring(MAGIC_ZERO, MAGIC_500) : str);
				}
				try {
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        ObjectOutputStream oos = new ObjectOutputStream(baos);
			        oos.writeObject(be.getBusinessObject());
					exceptionLog.setObject(baos.toByteArray());
					baos.close();
					oos.close();
				} catch (IOException e) {
					LOG.info("Log write database error: " + e.getMessage());
				}
			}else{
				exceptionLog.setMessage(ex.getMessage());
			}

			exceptionLog.setTime(new Date());
			exceptionConsumer.save(exceptionLog);
		}else{
			LOG.info("Log write database error: " + ex.getMessage());
		}
	}
}
