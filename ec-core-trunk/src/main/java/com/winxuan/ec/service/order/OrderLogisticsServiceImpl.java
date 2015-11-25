package com.winxuan.ec.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.BaseDao;
import com.winxuan.ec.dao.OrderLogisticsDao;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderLogistics;
import com.winxuan.ec.service.AbstractBaseService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/*******************************************
* @ClassName: OrderLogisticsServiceImpl 
* @Description: TODO
* @author:cast911
* @date:2014年9月13日 下午1:02:27 
*********************************************/
@Service("orderLogisticsService")
@Transactional(rollbackFor = Exception.class)
public class OrderLogisticsServiceImpl extends AbstractBaseService<OrderLogistics> implements OrderLogisticsService {

	private static final Log LOG = LogFactory.getLog(OrderLogisticsService.class);

	@InjectDao
	protected OrderLogisticsDao orderLogisticsDao;

	@Autowired
	protected OrderService orderService;

	@Override
	public BaseDao<OrderLogistics> getDao() {
		return orderLogisticsDao;
	}

	@Override
	public List<OrderLogistics> findOrderLogistics(Order order) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("order", order);
		return orderLogisticsDao.find(parameters);
	}

	@Override
	public Order getOrder(Long deliveryCompanyId, String deliveryCode) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("deliveryCompany", deliveryCompanyId);
		parameters.put("deliveryCode", deliveryCode);
		List<Order> orderList = orderService.findForDeliveryCompany(parameters, (short) 1, new Pagination(20));
		if (CollectionUtils.isEmpty(orderList)) {
			LOG.warn("没有订单");
			return null;
		}
		if (orderList.size() > 1) {
			LOG.warn("一单多号");
		}

		return orderList.get(0);
	}

}
