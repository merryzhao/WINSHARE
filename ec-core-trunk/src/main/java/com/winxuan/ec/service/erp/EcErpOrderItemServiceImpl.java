package com.winxuan.ec.service.erp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ErpOrderItemDao;
import com.winxuan.ec.model.erp.EcErpOrderItem;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author yj
 * 
 */
@Service("ecErpOrderItemService")
@Transactional(rollbackFor = Exception.class)
public class EcErpOrderItemServiceImpl implements EcErpOrderItemService {

	@InjectDao
	private ErpOrderItemDao erpOrderItemDao;

	@Override
	public void save(EcErpOrderItem erpOrderItem) {
		erpOrderItemDao.save(erpOrderItem);
	}

	@Override
	public List<EcErpOrderItem> findByOrder(String order) {
		return erpOrderItemDao.findByOrderid(order);
	}

}
