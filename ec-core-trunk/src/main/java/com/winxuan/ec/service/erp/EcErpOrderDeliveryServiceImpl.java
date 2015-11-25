package com.winxuan.ec.service.erp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ErpOrderDeliveryDao;
import com.winxuan.ec.model.erp.EcErpOrderDelivery;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author yj
 * 
 */
@Service("ecErpOrderDeliveryService")
@Transactional(rollbackFor = Exception.class)
public class EcErpOrderDeliveryServiceImpl implements EcErpOrderDeliveryService {

	@InjectDao
	private ErpOrderDeliveryDao erpOrderDeliveryDao;

	@Override
	public void save(EcErpOrderDelivery erpOrderDelivery) {
		erpOrderDeliveryDao.save(erpOrderDelivery);
	}

	@Override
	public List<EcErpOrderDelivery> findByOrder(String order) {
		return erpOrderDeliveryDao.findByOrderid(order);
	}

}
