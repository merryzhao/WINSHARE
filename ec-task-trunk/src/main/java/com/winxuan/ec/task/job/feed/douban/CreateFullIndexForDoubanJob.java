package com.winxuan.ec.task.job.feed.douban;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.winxuan.ec.feed.service.douban.DoubanService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * author lin bian
 */
@Component("createFullIndexForDoubanJob")
public class CreateFullIndexForDoubanJob implements TaskAware,Serializable{
	private static final long serialVersionUID = 5862210672106532776L;

	@Autowired
	private DoubanService doubanService;
	
	@Override
	public String getName() {
		return "createFullIndexForDoubanJob";
	}

	@Override
	public String getDescription() {
		return "douban feed全量索引(已废弃)";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		doubanService.createFullIndex();
	}
}
