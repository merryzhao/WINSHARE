package com.winxuan.ec.service.finance;

import java.util.Date;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDeliveryFinance;
import com.winxuan.ec.model.order.OrderReturnFinance;
import com.winxuan.ec.model.returnorder.ReturnOrder;

/**
 * 财务相关业务接口
 * @author HideHai
 */
public interface FinanceService {
	
	/**
	 * 通过订单编号获取订单的发货数据
	 * @param orderId
	 * @return
	 */
	OrderDeliveryFinance get(String orderId);
	
	/**
	 * 通过订单号获取业务编号获取退货数据
	 * @param orderId
	 * @param business
	 * @return
	 */
	OrderReturnFinance get(String orderId,String business);
	
	/**
	 * 记录订单发货的财务数据
	 * 只处理EC和中启交互的订单.
	 * @param order	处理的订单
	 */
	boolean processDelvieryFinance(Order order,Date returnTime);

	
	/**
	 *  对退货取消、中启取消的订单记录退货财务数据
	 * @param order
	 * @throws OrderStatusException	
	 */
	boolean processReuturnFinance(Order order,Date returnTime);
	
	/**
	 * 对退货单记录退货财务数据
	 * 退货单状态为已退款或者已换货
	 * @param returnOrder
	 */
	boolean processReturnFinance(ReturnOrder returnOrder,Date returnTime);
	
	/**
	 * 判断是否存在发货数据
	 * @param order
	 * @return
	 */
	boolean existDeliveryFinance(Order order);
	
	/**
	 * 判断是否存在退货数据
	 * @param returnOrder
	 * @return
	 */
	boolean existReturnFinance(Order order);
	
	/**
	 * 判断是否存在退货数据
	 * @param returnOrder
	 * @return
	 */
	boolean existReturnFinance(ReturnOrder returnOrder);
	
	/**
	 * 更新退货数据状态为已上架[Code.RETURN_ONSHELF_YES]
	 * 更新退货上架时间
	 * @param order	已下传中启的订单
	 * @param returnTime ERP退货时间
	 */
	void updateReturnFinance(Order order,Date returnTime);
	
	/**
	 * 更新退货数据状态为已上架[Code.RETURN_ONSHELF_YES]
	 * 更新退货上架时间
	 * @param returnOrder	EC已下传中启的退货单
	 * @param returnTime ERP退货时间
	 */
	void updateReturnFinance(ReturnOrder returnOrder,Date returnTime);
}
