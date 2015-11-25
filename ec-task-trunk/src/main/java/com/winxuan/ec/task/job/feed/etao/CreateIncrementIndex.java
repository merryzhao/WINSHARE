package com.winxuan.ec.task.job.feed.etao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.feed.service.FeedIncrementService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 增量索引
 * @author cast911
 */
@Component("feedIncrementIndexPrepare")
public class CreateIncrementIndex implements TaskAware,Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FeedIncrementService feedIncrementService;
	
	@Override
	public String getName() {
		return "feedIncrementIndexPrepare";
	}

	@Override
	public String getDescription() {
		return "feed增量索引预留接口";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		feedIncrementService.createIncrementIndex("increment.group2");
	}

}
