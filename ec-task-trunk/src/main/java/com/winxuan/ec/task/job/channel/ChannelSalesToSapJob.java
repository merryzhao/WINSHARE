package com.winxuan.ec.task.job.channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.job.ec.product.ProductSalesStatisticsJob;
import com.winxuan.ec.task.model.ec.convert.ChannelSalesInfo;
import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * @description: TODO
 *
 * @createtime: 2013-12-9 下午3:49:46
 *
 * @author zenghua
 *
 * @version 1.0
 */
@Component("channelSalesToSapJob")
public class ChannelSalesToSapJob implements TaskAware, Serializable {
	private static Log log = LogFactory.getLog(ProductSalesStatisticsJob.class);

	private static final long serialVersionUID = 8795851070217165099L;

	@Autowired
	EcProductService ecProductService;
	@Override
	public String getName() {
		return "ChannelSalesToSapJob";
	}

	@Override
	public String getDescription() {
		
		return "渠道商品下传SAP数据统计";
	}

	@Override
	public String getGroup() {
		return null;
	}

	@Override
	public void start() {
		Long sum = 0L;
		int limit = 2000;
		List<ChannelSalesInfo> list = new ArrayList<ChannelSalesInfo>();
		
		while (true) {
			list = ecProductService.sumChannelSales(list, limit);
			int n = list.size();
			sum += n;
			
			log.info("当前【下传sap】发货商品个数：" + n);
			if (n < limit) {
				log.info("本次【下传sap】发货商品总个数：" + sum);
				break;
			}
		}
		
		sum = 0L;
		list.clear();//清空上次的数据
		while (true) {
			list  = ecProductService.sumChannelReturun(list, limit);
			int n = list.size();
			sum += n;
			log.info("当前【下传sap】退货商品个数：" + n);
			if (n < limit) {
				log.info("本次【下传sap】退货商品总个数：" + sum);
				break;
			}
		}
		
		sum = 0L;
		while (true) {
			int n = ecProductService.updateSalesRecord(limit);
			sum += n;
			log.info("当前【更新下传SAP商品记录状态】个数：" + n);
			if (n < limit) {
				log.info("本次【更新下传SAP商品记录状态】总个数：" + sum);
				break;
			}
		}
		
		//====================冲销====================
		sum = 0L;
		list.clear();//清空上次的数据
		while (true) {
			list = ecProductService.sumRollbackSales(list, limit);
			int n = list.size();
			sum += n;
			log.info("当前【冲销发货】个数：" + n);
			if (n < limit) {
				log.info("本次【冲销发货】总个数：" + sum);
				break;
			}
		}
		
		sum = 0L;
		list.clear();//清空上次的数据
		while (true) {
			list = ecProductService.sumRollbackReturn(list, limit);
			int n = list.size();
			sum += n;
			log.info("当前【冲销退货】个数：" + n);
			if (n < limit) {
				log.info("本次【冲销退货】总个数：" + sum);
				break;
			}
		}
		
		sum = 0L;
		while (true) {
			int n = ecProductService.updateRollbackRecord(limit);
			sum += n;
			log.info("当前【更新冲销商品状态】个数：" + n);
			if (n < limit) {
				log.info("本次【更新冲销商品状态】总个数：" + sum);
				break;
			}
		}
	}

}
