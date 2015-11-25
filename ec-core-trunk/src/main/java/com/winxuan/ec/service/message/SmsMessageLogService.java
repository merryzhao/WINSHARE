/**
 * 
 */
package com.winxuan.ec.service.message;

import com.winxuan.ec.model.message.SmsMessageLog;

/**
 * @author monica
 * 短信日志记录
 */
public interface SmsMessageLogService {

	SmsMessageLog getSmsMessageLog(Long id);
	
	void save(SmsMessageLog smsMessageLog);
	
	void update(SmsMessageLog smsMessageLog);
}
