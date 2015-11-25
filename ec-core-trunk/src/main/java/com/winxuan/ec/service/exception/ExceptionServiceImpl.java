package com.winxuan.ec.service.exception;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ExceptionDao;
import com.winxuan.ec.model.exception.ExceptionLog;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
@Service("exceptionService")
@Transactional(rollbackFor = Exception.class)
public class ExceptionServiceImpl implements ExceptionService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4484949035326985444L;
	
	@InjectDao
	private ExceptionDao exceptionDao;

	@Override
	public void save(ExceptionLog exceptionLog) {
		exceptionDao.save(exceptionLog);
	}
}
