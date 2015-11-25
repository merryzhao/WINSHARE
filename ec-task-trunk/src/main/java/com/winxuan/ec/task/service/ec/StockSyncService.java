package com.winxuan.ec.task.service.ec;

import java.util.List;

import com.winxuan.ec.task.model.ec.StockSyncInfo;


/**
 * ERP全量库存同步
 * @author: HideHai 
 * @date: 2013-10-16
 */
public interface StockSyncService {
	
	
	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	 StockSyncInfo getNeedProcessInfo(long id);
	
	/**
	 * 查询需要处理的列表
	 * @return
	 */
	 List<StockSyncInfo> findNeedProcessInfo();
	
	/**
	 * 处理库存信息
	 * @param stockSyncInfo
	 * @throws Exception
	 */
	 void processStockSyncInfo(StockSyncInfo stockSyncInfo) throws Exception;
	
	/**
	 * 标记数据忽略
	 * @param id
	 */
	 void markInfoIgnore(long id);
	 
	 /**
	  * 获取库存不一样且没有同步的数量
	  * @return
	  */
	 int getCount();
	 
	 /**
	  * 将库存不一样且ischange为0的更新为1
	  */
	 void updateDifStockIsChanege();

}

