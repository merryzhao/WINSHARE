package com.winxuan.ec.service.order;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.OrderCancelApp;
import com.winxuan.framework.pagination.Pagination;

/**
 * 订单取消申请接口
 * @author yuhu
 * @version 1.0,2011-10-17下午04:29:19
 */
public interface OrderCancelAppService {

	/**
	 * 创建订单取消申请
	 * @param cancelApp
	 */
	void save (OrderCancelApp cancelApp);
	
	/**
	 * 更新订单取消申请
	 * 当申请状态为审核不通过时，修改状态为未审核状态
	 * @param cancelApp
	 */
	void update(OrderCancelApp cancelApp);
	
	/**
	 * 查询订单取消申请
	 * @param params
	 * @param pagination
	 */
	 List<OrderCancelApp> find(Map<String,Object> params,Pagination pagination);
	
	/**
	 * 审核订单取消申请
	 * 审核之前要判断申请状态，只有申请状态为待审核状态时才能审核
	 * @param cancelApp 需要审核的取消申请
	 * @param isPass  是否审核通过，审核通过后状态改为审核通过，审核不通过时状态改为审核不通过
	 */
	void audit(OrderCancelApp cancelApp , boolean isPass);
	
	/**
	 * 判断改订单是否已经申请
	 * @param orderId
	 */
	boolean existByOrderId(String orderId);
	

}
