package com.winxuan.ec.task.model.robot;

/**
 * robot任务
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
public class RobotTask {
	/**
	 * 抓取卓越图书
	 */
	public static final int TARGET_TYPE_AMAZON_BOOK = 4;
	
	/**
	 * 新任务
	 */
	public static final int TASK_STATUS_NEW = 70001;
	/**
	 * 抓取完成
	 */
	public static final int TASK_STATUS_FINISH = 70003;
	/**
	 * 未找到
	 */
	public static final int TASK_STATUS_NOTFOUND= 70006;
}
