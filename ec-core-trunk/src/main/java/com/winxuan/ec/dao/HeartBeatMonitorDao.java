/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.heartbeat.Beat;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * @author monica
 *
 */
public interface HeartBeatMonitorDao {
	
	/**
	 * 返回满足发送短消息条件的心跳记录
	 * @return
	 */
	@Query(" from Beat b where (current_timestamp() + interval b.timeout minute) > b.updatetime ")
	List<Beat> findDangerHeartBeat();
	
	/**
	 * 获取heartbeat表中的所有心跳记录
	 */
	@Query(" from Beat b")
	@OrderBys({
		@OrderBy("b.appkey asc")
	})
	List<Beat> getAllBeat(@Order Short sort);
	
	/**
	 * 根据appkey和hostname获取对应的beat
	 */
	@Query(" from Beat b where b.appkey = ? and b.hostname = ?")
	Beat getBeat(String appkey, String hostname);
	
	/**
	 * 更新应用的配置信息
	 */
	@Update
	void updateBeatInfo(Beat beat);
	
}
