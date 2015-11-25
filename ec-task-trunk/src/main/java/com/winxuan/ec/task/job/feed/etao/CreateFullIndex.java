package com.winxuan.ec.task.job.feed.etao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.feed.service.FeedService;
import com.winxuan.ec.feed.service.etao.EtaoService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 全量索引
 * @author cast911
 */
@Component("feedFullIndexPrepare")
public class CreateFullIndex implements TaskAware,Serializable {

	private static final long serialVersionUID = 5862210672106532776L;

	@Autowired
	private EtaoService etaoService;
	
	@Autowired
	private FeedService feedService;
	
	@Override
	public String getName() {
		return "feedFullIndexPrepare";
	}

	@Override
	public String getDescription() {
		return "feed全量索引预留接口";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		etaoService.createSellerCats();
		feedService.createFullIndex("full.group2");
	}
}
