package com.winxuan.ec.service.exception;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.exception.ExceptionLogDao;
import com.winxuan.ec.model.refund.RefundLog;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-23
 */
@Service("exceptionLogService")
@Transactional(rollbackFor = Exception.class)
public class ExceptionLogServiceImpl implements ExceptionLogService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4484949035326985444L;
	
	@Autowired
	private ExceptionLogDao exceptionLogDao;

	@Override
	public void info(String id,String info,String type){
		RefundLog refundFailed = new RefundLog();
		refundFailed.info(id, info,type);
		exceptionLogDao.save(refundFailed);
	}

	@Override
	public void error(String id, String info, String type) {
		RefundLog refundFailed = new RefundLog();
		refundFailed.error(id, info,type);
		exceptionLogDao.save(refundFailed);
	}

	@Override
	public void warn(String id, String info, String type) {
		RefundLog refundFailed = new RefundLog();
		refundFailed.warn(id, info,type);
		exceptionLogDao.save(refundFailed);
	}

	@Override
	public List<RefundLog> get(String id) {
		return exceptionLogDao.get(id);
	}
}
