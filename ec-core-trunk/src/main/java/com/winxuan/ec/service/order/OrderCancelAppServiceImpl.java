package com.winxuan.ec.service.order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderCancelAppDao;
import com.winxuan.ec.model.order.OrderCancelApp;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author yuhu
 * @version 1.0,2011-10-17下午04:34:39
 */
@Service("orderCancelAppService")
@Transactional(rollbackFor = Exception.class)
public class OrderCancelAppServiceImpl implements OrderCancelAppService,Serializable {
	
	private static final long serialVersionUID = -5767021308547776803L;
	
	@InjectDao
	private OrderCancelAppDao orderCancelAppDao;
	
	

	
	public void save(OrderCancelApp cancelApp) {
			orderCancelAppDao.save(cancelApp);
	}

	public  List<OrderCancelApp> find(Map<String, Object> params, Pagination pagination) {
		return orderCancelAppDao.find(params, pagination);
	}

	@Override
	public void audit(OrderCancelApp cancelApp, boolean isPass) {
		// TODO Auto-generated method stub

	}

	public boolean existByOrderId(String orderId) {
		return orderCancelAppDao.existByOrderId(orderId);
	}

	@Override
	public void update(OrderCancelApp cancelApp) {
		orderCancelAppDao.update(cancelApp);
		
	}


}
