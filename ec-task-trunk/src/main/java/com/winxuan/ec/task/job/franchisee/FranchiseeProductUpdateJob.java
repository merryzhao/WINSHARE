/**
 * 
 */
package com.winxuan.ec.task.job.franchisee;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.franchisee.product.FranchiseeProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-6-5
 */
@Component("franchiseeProductUpdateJob")
public class FranchiseeProductUpdateJob implements TaskAware, Serializable{

	private static final Log LOG = LogFactory.getLog(FranchiseeProductUpdateJob.class);
	
	private static final long serialVersionUID = 2804944721747562818L;

	@Autowired
	private FranchiseeProductService franchiseeProductService;

	@Override
	public String getName() {
		return "franchiseeProductUpdateJob";
	}

	
	@Override
	public String getDescription() {
		return "定时更新生效和过期的促销活动商品";
	}

	
	@Override
	public String getGroup() {
		return "DEFAULT";
	}

	
	@Override
	public void start() {
		LOG.info("更新促销商品开始");
		franchiseeProductService.putProductIntoTopic();
		LOG.info("更新促销商品结束");
	}

}
