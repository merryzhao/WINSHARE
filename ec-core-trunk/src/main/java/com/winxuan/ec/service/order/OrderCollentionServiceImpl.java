package com.winxuan.ec.service.order;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.OrderCollectionDao;
import com.winxuan.ec.model.order.OrderCollection;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 
 * @author heshuai
 *
 */
@Service("orderCollentionService")
public class OrderCollentionServiceImpl implements OrderCollentionService {

	@InjectDao
	private OrderCollectionDao orderCollectionDao;
	
	@Override
	public OrderCollection getOrderCollention(String orderId) {
		OrderCollection orderCollection = orderCollectionDao.getCollentionByOrderId(orderId);		
		return orderCollection;
	}

	@Override
	public void saveOrUpdate(OrderCollection orderCollection) {
		orderCollectionDao.saveOrUpdate(orderCollection);
	}

}
