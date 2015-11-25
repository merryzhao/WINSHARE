package com.winxuan.ec.task.job.ec.product;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 商品销售统计 job
 * @author heyadong
 *
 */
@Component("productSalesStatisticsJob")
public class ProductSalesStatisticsJob implements TaskAware, Serializable{
	private static Log log = LogFactory.getLog(ProductSalesStatisticsJob.class);

	private static final long serialVersionUID = 8795851070217165099L;

	@Autowired
	EcProductService ecProductService;
	
	@Override
	public String getName() {
		return "productSalesStatisticsJob";
	}

	@Override
	public String getDescription() {
		
		return "每日商品销售发货统计";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		Long sum = 0L;
		int limit = 2000;
		while(true){
			int n = ecProductService.sumSales(limit);
			sum += n;
			log.info("当前【销售】订单统计个数:" + n);
			if (n < limit){
				log.info("本次【销售】订单统计总个数---:" + sum);
				break;
			}
		}
		sum = 0L;
		while(true){
			int n = ecProductService.sumReject(limit);
			sum += n;
			log.info("当前【拒收】订单统计个数:" + n);
			if (n < limit){
				log.info("本次【拒收】订单统计总个数---:" + sum);
				break;
			}
		}
		
		
		sum = 0L;
		while(true){
			int n = ecProductService.sumRefund(limit);
			sum += n;
			log.info("当前【退货】订单统计个数:" + n);
			if (n < limit){
				log.info("本次【退货】订单统计总个数---:" + sum);
				break;
			}
		}
		
		
		//合并苏宁销售
		ecProductService.mergeSuning();
	}

}
