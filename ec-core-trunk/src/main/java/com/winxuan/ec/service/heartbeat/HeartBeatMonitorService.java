/**
 * 
 */
package com.winxuan.ec.service.heartbeat;

import java.util.List;

import com.winxuan.ec.model.heartbeat.Beat;

/**
 * @author monica
 *
 */
public interface HeartBeatMonitorService {

	/**
	 * 从数据库的heartbeat表中取出数据
	 * @return
	 */
	List<Beat> findDangerHeatBeat();
	
	//按照appkey的首字母升序排列
	List<Beat> getAll(Short sort);
	
	Beat get(String appkey, String hostname);
	
	void updateBeatInfo(Beat beat);
	
	void audit(Beat beat);
	
	int getMessageSend(Beat beat);
}
