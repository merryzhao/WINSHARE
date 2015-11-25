package com.winxuan.ec.task.service.union;



/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-12-29
 */
public interface AdUnionOrderService {
	/**
	 * 得到发货订单
	 * @return
	 */
	public void updateDeliveryOrderListbyDate();
	/**
	 * 得到退货订单
	 * @return
	 */
	public void addRefundOrderListbyDate();
	/**
	 * 得到取消订单
	 * @return
	 */
	public void updateCancelOrderListbyDate();
	
	/**
	 * 批量更新订单
	 * @throws Exception
	 */
	public void  updateOrder();
	
}
