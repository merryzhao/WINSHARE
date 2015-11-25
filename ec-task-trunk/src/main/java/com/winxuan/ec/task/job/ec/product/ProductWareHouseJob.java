package com.winxuan.ec.task.job.ec.product;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.ec.EcProductWareHouse;
import com.winxuan.ec.task.service.ec.EcProductWareHouseService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;
/**
 * description
 * @author  lean bian
 * @version 1.0,2013-09-26
 */
@Component("ProductWareHouseJob")
public class ProductWareHouseJob implements TaskAware, Serializable{
	private static Log log = LogFactory.getLog(ProductWareHouseJob.class);
	
	@Autowired
	private EcProductWareHouseService ecProductWareHouseService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ProductWareHouseJob";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "中启回传库存更新";
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		Long sum = 0L;
		int limit = 2000;
		hibernateLazyResolver.openSession();
		
		while(true){
			List<EcProductWareHouse> changedEcProductWareHouseList= ecProductWareHouseService.getChangedWarehouseList(limit);
			
			for(EcProductWareHouse changedEcProductWareHouseItem : changedEcProductWareHouseList){
				ecProductWareHouseService.changeStockAndStateOneByOne(changedEcProductWareHouseItem);
			}
			int n = changedEcProductWareHouseList.size();			
			sum += n;
			log.info("当前同步的【中启回传库存】的商品总量为:" + n);
			if (n < limit){
				log.info("本次同步的【中启回传库存】的商品总量为---:" + sum);
				break;
			}
		}
		hibernateLazyResolver.releaseSession();
	}

}
