package com.winxuan.ec.task.job.robot;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.robot.RobotCategory;
import com.winxuan.ec.task.service.ec.EcCategoryService;
import com.winxuan.ec.task.service.robot.RobotCategoryService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 分类商品同步JOB
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Component("categorySyncJob")
public class CategorySyncJob implements TaskAware, Serializable{

	private static final long serialVersionUID = 9214384487994434587L;
	private static final Logger LOG = Logger.getLogger(CategorySyncJob.class);
	@Autowired
	private EcCategoryService ecCategoryService;
	@Autowired
	private RobotCategoryService robotCategoryService;
	@Override
	public String getName() {
		return "categorySyncJob";
		
	}

	@Override
	public String getDescription() {
		return "卓越图书分类-同步";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		LOG.info("[START] sync category To Ec-Core");
		long time = System.currentTimeMillis();
		try {
			List<RobotCategory> list = robotCategoryService.getNewCategories();
			if(!list.isEmpty()){
				ecCategoryService.syncCategory(list);
				LOG.info(String.format("[END] sync category To Ec-Core , usetime %s", (System.currentTimeMillis() - time)));
			} else {
				LOG.info("[END] current not have category sync");
			}
			
		} catch (Exception e) {
			LOG.error(e);
		}
	}

}
