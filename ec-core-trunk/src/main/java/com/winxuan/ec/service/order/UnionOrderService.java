package com.winxuan.ec.service.order;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.framework.pagination.Pagination;
/** 
 * @author zhoujun
 * @version 1.0,2011-9-7
 */
public interface UnionOrderService {
   
    void save(UnionOrder unionOrder);
    
    UnionOrder getByOrderId(String orderId);
   
    List<UnionOrder> find(Map<String,Object> parameters,Short sort,Pagination pagination);
}
