package com.winxuan.ec.task.job.feed;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.feed.service.FeedService;
import com.winxuan.ec.feed.service.etao.EtaoService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * feed全量索引
 * @author ztx
 */
@Component("feedFullIndex")
public class CreateFullIndex implements TaskAware,Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EtaoService etaoService;
	
	@Autowired
	private FeedService feedService;
	
	@Override
	public String getName() {
		return "feedFullIndex";
	}

	@Override
	public String getDescription() {
		return "feed全量索引(一淘,豆瓣,微购)";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		etaoService.createSellerCats();
		feedService.createFullIndex("full.group1");
	}
}
