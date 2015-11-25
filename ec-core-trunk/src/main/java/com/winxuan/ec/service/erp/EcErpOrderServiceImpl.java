package com.winxuan.ec.service.erp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ErpOrderDao;
import com.winxuan.ec.model.erp.EcErpOrder;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author yj
 * 
 */
@Service("ecErpOrderService")
@Transactional(rollbackFor = Exception.class)
public class EcErpOrderServiceImpl implements EcErpOrderService {

	@InjectDao
	private ErpOrderDao erpOrderDao;

	@Override
	public void save(EcErpOrder erpOrder) {
		erpOrderDao.save(erpOrder);
	}

	@Override
	public EcErpOrder get(String order, String state) {
		return erpOrderDao.get(order, state);
	}

	@Override
	public void update(EcErpOrder erpOrder) {
		erpOrderDao.update(erpOrder);
	}

}
