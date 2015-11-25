/**
 * 
 */
package com.winxuan.ec.service.message;

import com.winxuan.ec.dao.SmsMessageLogDao;
import com.winxuan.ec.model.message.SmsMessageLog;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * @author monica
 * 短信日志记录
 */
public class SmsMessageLogServiceImpl implements SmsMessageLogService {

	@InjectDao
	SmsMessageLogDao smsMessageLogDao;
	
	@Override
	public SmsMessageLog getSmsMessageLog(Long id) {
		// TODO Auto-generated method stub
		return smsMessageLogDao.get(id);
	}

	@Override
	public void save(SmsMessageLog smsMessageLog) {
		// TODO Auto-generated method stub
		smsMessageLogDao.save(smsMessageLog);
	}

	@Override
	public void update(SmsMessageLog smsMessageLog) {
		// TODO Auto-generated method stub
		smsMessageLogDao.update(smsMessageLog);
	}

}
