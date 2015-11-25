package com.winxuan.ec.service.erp;

import com.winxuan.ec.model.erp.EcErpOrder;

/**
 * 
 * @author yj
 * 
 */
public interface EcErpOrderService {

	void save(EcErpOrder erpOrder);

	EcErpOrder get(String order,String state);
	
	void update(EcErpOrder erpOrder);
}
