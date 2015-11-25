package com.winxuan.ec.task.dao.robot;

import java.io.Serializable;

/**
 * RobotTask Dao
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
public interface RobotTaskDao extends Serializable {
	
	/**
	 * 批量添加robot任务
	 * @param priority 优先级
	 * @param targetType 抓取类型
	 * @param params 参数
	 */
	public void addTasks(int priority, int targetType, String ... params);
}
