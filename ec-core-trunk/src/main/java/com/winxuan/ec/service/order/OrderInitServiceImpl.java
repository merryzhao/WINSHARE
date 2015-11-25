/**
 * 
 */
package com.winxuan.ec.service.order;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.OrderInitDao;
import com.winxuan.ec.model.order.OrderInit;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-7
 */
@Service
public class OrderInitServiceImpl implements OrderInitService {

   
	@InjectDao
	private OrderInitDao orderInitDao;
	
	@Override
	public void update(OrderInit orderInit) {
		orderInitDao.update(orderInit);
	}

	
	@Override
	public void save(OrderInit orderInit) {
		orderInitDao.save(orderInit);
	}

}
