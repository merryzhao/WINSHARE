package com.winxuan.ec.task.service.task;

import java.util.List;

import org.quartz.Trigger;

import com.winxuan.ec.task.exception.quartz.QuartzException;
import com.winxuan.ec.task.model.quartz.TaskTrigger;
import com.winxuan.ec.task.support.quartz.TaskTemplate;
import com.winxuan.framework.pagination.Pagination;

public interface TriggerService {
	
	/**
	 * 获取所有任务列表
	 * @param pagination
	 * @return
	 */
	public List<TaskTrigger> viewList(Pagination pagination);
	
	/**
	 * 获取正在运行的任务
	 * @return
	 */
	public List<TaskTrigger> viewExecuting();
	
	/**
	 * 测试方法
	 */
	public void add();
	
	/**
	 * 添加任务的触发器
	 * @param jobKey
	 * @return
	 */
	public void addJobTrigger(TaskTemplate template) throws QuartzException;
	/**
	 * 暂停任务的触发器
	 * @param jobKey
	 * @return
	 */
	public void pauseTrigger(TaskTemplate template) throws QuartzException;
	/**
	 * 恢复任务的触发器
	 * @param jobKey
	 * @return
	 */
	public void resumeTrigger(TaskTemplate template) throws QuartzException;
	
	/**
	 * 清除任务的触发器
	 * @param jobKey
	 * @return
	 */
	public void clearTrigger(TaskTemplate template) throws QuartzException;
	
	/**
	 * 通过名称查找触发器
	 * @param jobKey
	 * @return
	 */
	public TaskTrigger findTrigger(String jobKey);
	
	public Trigger findQuartzTrigger(String jobKey)  throws QuartzException;

}
