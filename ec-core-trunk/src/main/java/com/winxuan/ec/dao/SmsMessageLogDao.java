/**
 * 
 */
package com.winxuan.ec.dao;

import com.winxuan.ec.model.message.SmsMessageLog;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;

/**
 * @author monica
 * 短信日志记录
 */
public interface SmsMessageLogDao {

	@Get
	SmsMessageLog get(Long id);
	
	@Save
	void save(SmsMessageLog smsMessageLog);
	
	@Update
	void update(SmsMessageLog smsMessageLog);

}
