package com.winxuan.ec.support.p6spy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.p6spy.engine.logging.appender.P6Logger;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-21 下午3:50:43  --!
 * @description:
 ********************************
 */
public class WinXuanLog implements P6Logger {
    
	private static final Log LOG = LogFactory.getLog(WinXuanLog.class);

	@Override
	public void logSQL(int connectionId, String now, long elapsed,
			String category, String prepared, String sql) {
		this.logText(sql);
	}

	@Override
	public void logException(Exception e) {
		LOG.error(e);
		
	}

	@Override
	public void logText(String text) {
		LOG.info(text);
		
	}

	@Override
	public String getLastEntry() {
		
		return null;
	}

	
}
