package com.winxuan.ec.task.job.erp;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.dao.erp.ErpDao;
import com.winxuan.ec.task.model.erp.ErpProductStock;
import com.winxuan.ec.task.service.erp.ErpProductStockService;
import com.winxuan.ec.task.service.erp.impl.ErpOrderServiceImpl;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-7 下午12:40:32 --!
 * @description:商品库存同步
 ******************************** 
 */
@Component("erpProductStockSynchro")
public class ErpProductStockSynchro implements TaskAware, Serializable,
		TaskConfigure {

	private static final Log LOG = LogFactory.getLog(ErpOrderServiceImpl.class);

	private static final long serialVersionUID = -2602258672402825016L;
	
	private static final int DEFAULT_PAGESIZE = 1000;

	private static final int DEFAULT_PAGINATION = 1;
	

	@Autowired
	ErpProductStockService erpProductStockService;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private ErpDao erpDao;


	@Override
	public String getName() {
		return "erpProductStockSynchro";
	}

	@Override
	public String getDescription() {
		return "\u4e3b\u6570\u636e\u5546\u54c1\u5e93\u5b58\u540c\u6b65";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		try {
			int count = erpDao.countErpProductStock();
			if (count > MagicNumber.ZERO) {
				int pagination = DEFAULT_PAGINATION;
				List<ErpProductStock> result = null;
				boolean flag = false;
				do {
					result = erpDao.needUpdateProductStock(DEFAULT_PAGESIZE,pagination);
					flag = CollectionUtils.isEmpty(result);
					if (!flag) {
						this.updateLogic(result);
						pagination++;
					}
				} while (!flag);
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}
	
	private void updateLogic(List<ErpProductStock> result) throws Exception{
		for (ErpProductStock erpProductStock : result) {
			erpProductStockService.incrementalUpdateProduct(erpProductStock);
		}
	}

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, msg[0]);
	}
}
