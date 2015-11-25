package com.winxuan.ec.service.settle;

/**
 * 每日销售
 * @author wenchx
 * @version 1.0, 2014-8-1 下午2:49:21
 */
public interface SalesSettleService {
	 
	/**
	 * 任务启动入口
	 * @param pageSize   
	 * @return void   
	 * @throws
	 */
	int start(int limit);
	/**
	 * 任务启动入口
	 * @param pageSize   
	 * @return void   
	 * @throws
	 */
	int startReturnSales(int limit);
	/**
	 * 任务启动入口
	 * @param pageSize   
	 * @return void   
	 * @throws
	 */
	int startRejectSales(int limit);
	 
	/**
	 * 发货数据下传sap处理
	 * @param limit   
	 * @return void   
	 * @throws
	 */
	int deliverySalesExecutor(int limit);
	/**
	 * 退货数据下传sap处理
	 * @param limit   
	 * @return void   
	 * @throws
	 */
	int returnSalesExecutor(int limit);
	/**
	 * 拒收数据下传sap处理
	 * @param limit   
	 * @return void   
	 * @throws
	 */
	int rejectSalesExecutor(int limit);
}
