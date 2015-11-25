package com.winxuan.ec.service.order;

import java.util.List;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderLogistics;
import com.winxuan.ec.service.BaseService;

/*******************************************
* @ClassName: OrderLogisticsService 
* @Description: TODO
* @author:cast911
* @date:2014年9月13日 下午1:01:45 
*********************************************/
public interface OrderLogisticsService extends BaseService<OrderLogistics> {

	List<OrderLogistics> findOrderLogistics(Order order);

	Order  getOrder(Long deliveryCompanyId,String deliveryCode);
	
}
