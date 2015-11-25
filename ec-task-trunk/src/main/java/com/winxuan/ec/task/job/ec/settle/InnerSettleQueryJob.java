package com.winxuan.ec.task.job.ec.settle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.service.settle.InnerSettleService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 * @description: TODO
 *
 * @createtime: 2014-2-18 下午4:34:51
 *
 * @author zenghua
 *
 * @version 1.0
 */
@Component("innerSettleQueryJob")
public class InnerSettleQueryJob implements TaskAware, TaskConfigure {
	@Autowired
	private InnerSettleService innerSettleService;

	@Override
	public int getNotifyLevel() {
		return 0;
	}

	@Override
	public void doNotify(String... msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "innerSettleQueryJob";
	}

	@Override
	public String getDescription() {
		return "内部结算2.0查询任务";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		innerSettleService.getAllOrderItem();
	}

}
