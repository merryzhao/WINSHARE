package com.winxuan.ec.task.job.franchisee;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.winxuan.ec.task.service.franchisee.product.FranchiseeProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 每天定时统计各个加盟商商品数量
 * 
 * @author guanxingjiang
 * @version 1.0,2013-12-31
 */
@Component("franchiseeProductQuantityJob")
public class FranchiseeProductQuantityJob implements TaskAware, Serializable {

	private static final Log LOG = LogFactory.getLog(FranchiseeProductQuantityJob.class);
	
	private static final long serialVersionUID = -3258424661263700031L;

	@Autowired
	private FranchiseeProductService franchiseeProductService;

	@Override
	public String getName() {
		return "franchiseeProductQuantityJob";
	}

	@Override
	public String getDescription() {
		return "统计加盟商上架商品数量";
	}

	@Override
	public String getGroup() {
		return "DEFAULT";
	}

	@Override
	public void start() {
		LOG.info("开始统计加盟商上架商品数量");
		franchiseeProductService.updateFranchiseeProductQuantity();
		LOG.info("统计加盟商上架商品数量结束");
	}

}
