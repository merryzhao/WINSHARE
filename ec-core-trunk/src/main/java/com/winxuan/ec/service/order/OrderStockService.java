package com.winxuan.ec.service.order;

/**
 * 
 * @author: zhoujun
 * @date: 2014-08-07
 */
public interface OrderStockService {
	
	/**
	 * 订单创建完成后，将订单需要初始化库存信息广播出去
	 * 记录库存快照
	 * 如果没有指定DC，则初始化DC
	 * 如果订单需要集货，则创建集货指令
	 * 更新库存的占用
	 * @param order
	 */
	void broadcastInitOrderStock(String orderId);
	
}
