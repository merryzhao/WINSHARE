package com.winxuan.ec.dao;

import java.util.List;

import com.winxuan.ec.model.erp.EcErpOrderDelivery;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 
 * @author yj
 * 
 */
public interface ErpOrderDeliveryDao {

	@Save
	void save(EcErpOrderDelivery erpOrderDelivery);

	@Query("from EcErpOrderDelivery ed where ed.order=? order by ed.deliveryTime desc")
	List<EcErpOrderDelivery> findByOrderid(String order);
}
