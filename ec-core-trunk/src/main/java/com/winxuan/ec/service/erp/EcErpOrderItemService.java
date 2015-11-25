package com.winxuan.ec.service.erp;

import java.util.List;

import com.winxuan.ec.model.erp.EcErpOrderItem;

/**
 * 
 * @author yj
 *
 */
public interface EcErpOrderItemService {
	
	void save(EcErpOrderItem erpOrderItem);
	
	List<EcErpOrderItem> findByOrder(String order);
}
