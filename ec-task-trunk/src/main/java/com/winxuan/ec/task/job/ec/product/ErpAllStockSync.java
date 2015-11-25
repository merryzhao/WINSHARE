package com.winxuan.ec.task.job.ec.product;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.ec.StockSyncInfo;
import com.winxuan.ec.task.service.ec.StockSyncService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * Erp库存全量同步
 * 
 * @author: HideHai
 * @date: 2013-10-16
 */
@Component("erpAllStockSync")
public class ErpAllStockSync implements TaskAware {

	private static final Log LOG = LogFactory.getLog(ErpAllStockSync.class);

	@Autowired
	private StockSyncService stockSyncService;

	public String getName() {
		return "erpAllStockSync";
	}

	public String getDescription() {
		return "中启ERP库存全量更新";
	}

	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	public void start() {
		if (stockSyncService.getCount() > 0) {
			// 将库存不一样且状态为库存没有变化的变为库存有变化
			stockSyncService.updateDifStockIsChanege();
		}
		List<StockSyncInfo> needProcessList = stockSyncService
				.findNeedProcessInfo();
		int count = 0;
		long s = System.currentTimeMillis();
		while (true) {
			if (CollectionUtils.isEmpty(needProcessList)) {
				break;
			}
			for (StockSyncInfo syncInfo : needProcessList) {
				try {
					LOG.info(String.format("process sapcode: %s : %s : %s",
							syncInfo.getOuterId(), syncInfo.getLocation(),
							syncInfo.getStock()));
					stockSyncService.processStockSyncInfo(syncInfo);
				} catch (Exception e) {
					stockSyncService.markInfoIgnore(syncInfo.getId());
					LOG.error(e.getMessage(), e);
				}
			}
			count = count + needProcessList.size();
			needProcessList = stockSyncService.findNeedProcessInfo();
		}
		LOG.info(String.format("all process count: %s ", count));
		LOG.info(String.format("all process time: %s",
				(System.currentTimeMillis() - s)));
	}

}
