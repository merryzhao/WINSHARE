package com.winxuan.ec.task.job.feed;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.feed.service.FeedIncrementService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * feed增量索引
 * @author ztx
 */
@Component("feedIncrementIndex")
public class CreateIncrementIndex implements TaskAware,Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FeedIncrementService feedIncrementService;
	
	@Override
	public String getName() {
		return "feedIncrementIndex";
	}

	@Override
	public String getDescription() {
		return "feed增量索引(一淘,微购)";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		feedIncrementService.createIncrementIndex("increment.group1");
	}

}
