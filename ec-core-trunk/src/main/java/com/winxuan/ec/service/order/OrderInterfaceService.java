package com.winxuan.ec.service.order;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderCourierReceipt;

/**
 * 
 * @author yangxinyi
 * 
 */
public interface OrderInterfaceService {

	String getStatusByErp(String orderId);
    String getStatusByWms(String orderId);
    String getStatusByWmsBj(String orderId);
    String getStatusByInterface(Order order);
    
    OrderCourierReceipt parseOrderCodJson(Order order);
    
    String erpInsertIntercept(String orderId); 
    
    /**
     * 保存COD仓库信息
     * @param order
     */
    void saveCodWarehouseExtend(Order order);
	
}
