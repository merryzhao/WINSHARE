/*
 * @(#)ErpOrderService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.erp;

import java.util.Date;
import java.util.List;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.ec.task.model.erp.ErpOrderInvoice;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-8-30
 */
public interface ErpOrderService {

	/**
	 * 下传订单到ERP
	 * @param order
	 * @throws Exception
	 */
	void transferOrder(Order order) throws Exception;

	/**
	 * 下传退货单到ERP
	 * @param returnOrder
	 * @throws Exception
	 */
	void transferReturnOrder(ReturnOrder returnOrder) throws Exception;

	/**
	 * 下传EC发票到中启
	 * @throws Exception
	 */
	void transferOrderInvoice(OrderInvoice orderInvoice) throws Exception;

	/**
	 *  获取需要回传EC的ERP订单信息
	 * @throws Exception
	 */
	List<ErpOrder> fetchOrderState();

	List<ErpOrder> fetchOrderState(String orderId);

	/**
	 * 查询需要更新运单的订单号
	 * @return
	 */
	List<String> fetchDistinctOrderDelivery();

	/**
	 * 得到超过24小时未处理的拦截订单
	 * @return
	 */
	List<String> fetchNeedProcessOrder();

	/**
	 * 得到中启12小时内的拦截订单
	 * @return
	 */
	List<ErpOrder> fetchErpRejectOrder();


	/**
	 *  对拦截失败的订单进行发货
	 * @param erpOrder
	 * @throws Exception
	 */
	void processUnsuccessBlockOrder(ErpOrder erpOrder) throws Exception;

	/**
	 *  回传ERP销售订单信息
	 *  全部已发/部分发货/缺货取消/ERP取消
	 * @param order
	 * @throws Exception
	 */
	@Deprecated
	void fetchOrder(ErpOrder erpOrder) throws Exception;

	/**
	 *  回传ERP退换货订单信息
	 * @param returnOrder
	 * @throws Exception
	 */
	List<OrderPayment> fetchReturnOrder(ErpOrder erpOrder) throws Exception;

	/**
	 * EC换货单的新订单生成.
	 */
	void processReturnOrderReplace(List<OrderPayment> newPayments,ErpOrder erpOrder) throws Exception; 

	/**
	 * 处理EC退货单状态为中启异常
	 */
	void updateReturnOrderCancel(ReturnOrder returnOrder);

	/**
	 * 从中启更新指定订单号的运单信息
	 * 1.检查订单号是否是EC订单号
	 * 2.如果EC没有多条运单记录，ERP只有一条记录，则直接更新主运单号
	 *   如果ERP有多条运单记录，则记录多条记录，并更新主运单号
	 * 3.如果EC有多条运单记录，则对多条记录进行更新，并更新主运单号
	 * @param orderId
	 */
	void updateDeliveryInfoByOrderId(String orderId) throws Exception;

	/**
	 * 检查ec回传超时订单在中启中是否发货
	 * @throws Exception
	 */
	void checkOrderReturnBack() throws Exception;

	/**
	 * 拦截记录处理
	 * 如果是06则记录相关的中启发货信息
	 * 如果是08则更改为客户取消.（EQ汇单取消）
	 * EC等待拦截也需要记录一次发货信息
	 * @param erpOrder
	 * @param ecOrder
	 * @throws ErpOrderTransferException
	 */
	void processErpBlockOrder(ErpOrder erpOrder) throws Exception;
	
	void processFetchOrder(ErpOrder erpOrder) throws Exception;
	
	/**
	 * 查询需要回传的发票信息
	 */
	List<ErpOrderInvoice> fetchOrderInvoice();
	
	/**
	 * 更新已经回传的状态为“S”
	 */
	void updateOrderInvoiceStatus(String id);
	
	/**
	 * 修改EC发票的发货信息
	 */
	void updateOrderInvoiceDelivery(String deliveryCode, Integer deliveryCompany, Date deliveryTime, String id);
	
	/**
	 * 同步区域到EC，并清理ERP已经同步的数据
	 */
	void processErpArea() throws Exception;
	
	/**
	 * 同步配送公司和区域关系到EC，并清理ERP已经同步的数据
	 */
	void processErpDeliveryArea() throws Exception;
	
	/**
	 * 同步配送公司到EC，并清理ERP已经同步的数据
	 */
	void processErpDeliveryCompany() throws Exception;
	
	/**
	 * 24小时拦截未处理的订单发货ERP订单发货
	 * @param ecOrder
	 */
	void blockFiledprocessErpDelivery (String orderId)  throws Exception;
}

