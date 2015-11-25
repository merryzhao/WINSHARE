package com.winxuan.ec.task.job.ec.product;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 
 * @author Heyadong
 * @version 1.0, 2012-3-28
 */
@Component("productRelationToAmazonJob")
public class ProductRelationToAmazonJob implements TaskAware, Serializable{

	private static final long serialVersionUID = -7893397117422537783L;
	private static final Logger LOG = Logger.getLogger(ProductRelationToAmazonJob.class);
	@Autowired
	private EcProductService ecProductService;
	
	@Override
	public String getName() {
		return "productRelationToAmazonJob";
	}

	@Override
	public String getDescription() {
		
		return "将EC中,期限内  MC关系商品 转成 卓越关系";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
	    
	    
	    
		Date date = new Date();
		int start = 0;
		int limit = 500;
		
		//同步之前,将新抓到商品的新分类,批量更新状态为。MC关联.
	    //while (ecProductService.syncProductNewCategory(limit) >= limit);
	    while(true){
	    	if(ecProductService.syncProductNewCategory(limit) < limit){
	    		break;
	    	}
	    }

		
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LOG.info("[SYNC] START product MC to Amazon ");
		int total = 0;
		while (true)  
		{	
			List<EcProductCategoryStatus> list = ecProductService.getMcProducts(date, start, limit);
			if (list == null || list.isEmpty()){
				break;
			}
			int syncNumber = ecProductService.syncProductRelation(list);
			total += syncNumber;
			start += limit;
			start -= syncNumber;
			LOG.info(String.format("[SYNC] product MC to Amazon [date:%s,  total:%s,  sync:%s]", format.format(date), list.size(), syncNumber));
		}
		LOG.info(String.format("[SYNC] END product MC to Amazon , total:%s ", total));
	}

}
