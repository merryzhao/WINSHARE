/*
 * @(#)Recommend
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.recommend;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.ec.task.service.recommend.RecommendService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.utils.ThreadLocalMode;

/**
 * description
 * @author  huangyixiang
 * @version 1.0,2011-11-9
 */
@Component("recommend")
public class Recommend implements Serializable,TaskAware{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5739796822303571973L;
	private static final Log LOG = LogFactory.getLog(Recommend.class);
	
	@Autowired
	RecommendService recommendService;
	
	public void start() {
		LOG.info("recommend start");
		try {
			
			ThreadLocalMode.set(RecommendMode.BUY);
			LOG.info("start 购买记录，商品推荐商品(mode=" + ThreadLocalMode.get() + ")");
			LOG.info("start initBaseData()............");
			recommendService.initBaseData();
			LOG.info("start saveBaseDataToFile()............");
			recommendService.saveBaseDataToFile();
			LOG.info("start doCalculate()............");
			recommendService.doCalculate();
			LOG.info("start saveResult()............");
			recommendService.saveResult();
			LOG.info("end............");
			
			
			ThreadLocalMode.set(RecommendMode.VISIT);
			LOG.info("start 浏览历史，商品推荐商品(mode=" + ThreadLocalMode.get() + ")");
			LOG.info("start initBaseData()............");
			recommendService.initBaseData();
			LOG.info("start saveBaseDataToFile()............");
			recommendService.saveBaseDataToFile();
			LOG.info("start doCalculate()............");
			recommendService.doCalculate();
			LOG.info("start saveResult()............");
			recommendService.saveResult();
			LOG.info("end............");
			
			
			ThreadLocalMode.set(RecommendMode.SEARCH);
			LOG.info("start 相关搜索(mode=" + ThreadLocalMode.get() + ")");
			LOG.info("start initBaseData()............");
			recommendService.initBaseData();
			LOG.info("start saveBaseDataToFile()............");
			recommendService.saveBaseDataToFile();
			LOG.info("start doCalculate()............");
			recommendService.doCalculate();
			LOG.info("start saveResult()............");
			recommendService.saveResult();
			LOG.info("end............");
			
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return "recommend";
	}

	@Override
	public String getDescription() {
		return "商品,搜索推荐";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

}

