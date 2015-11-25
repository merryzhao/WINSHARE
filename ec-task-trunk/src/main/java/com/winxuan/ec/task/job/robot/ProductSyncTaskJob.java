package com.winxuan.ec.task.job.robot;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 为新商品添加抓取任务JOB
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Component("productSyncTaskJob")
public class ProductSyncTaskJob implements TaskAware, Serializable{

	private static final long serialVersionUID = 4891846672066104835L;
	@Autowired
	private EcProductService ecProductService;
    private static final Logger LOG = Logger.getLogger(ProductSyncTaskJob.class);
	
	@Override
	public String getName() {
		return "productSyncTaskJob";
	}

	@Override
	public String getDescription() {
		return "将新加入的商品添加到robot task。";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		int max = 500;
		List<EcProductCategoryStatus> products = ecProductService.getNewProducts(max);
		LOG.info(String.format("sync product(new) to task total:%s ", products.size()));
		while (!products.isEmpty()) {
			ecProductService.syncProductToTask(products);
			
			products = ecProductService.getNewProducts(max);
		}
	}

}
