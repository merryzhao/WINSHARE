package com.winxuan.ec.service.erp;

import java.util.List;

import com.winxuan.ec.model.erp.EcErpOrderDelivery;

/**
 * 
 * @author yj
 * 
 */
public interface EcErpOrderDeliveryService {

	void save(EcErpOrderDelivery erpOrderDelivery);

	List<EcErpOrderDelivery> findByOrder(String order);
}
