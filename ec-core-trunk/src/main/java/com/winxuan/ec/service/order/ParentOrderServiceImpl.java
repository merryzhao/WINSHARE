/**
 * 
 */
package com.winxuan.ec.service.order;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ParentOrderDao;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * description
 * 
 * @author guanxingjiang
 * 
 * @version 1.0 2014-10-16
 */
@Service("parentOrderService")
@Transactional(rollbackFor = Exception.class)
public class ParentOrderServiceImpl implements ParentOrderService {

	@InjectDao
	private ParentOrderDao parentOrderDao;

	@Autowired
	private OrderService orderService;

	@Override
	public ParentOrder get(String id) {
		return parentOrderDao.get(id);
	}

	@Override
	public void save(ParentOrder parentOrder) {
		parentOrderDao.save(parentOrder);
	}

	@Override
	public void update(ParentOrder parentOrder) {
		parentOrderDao.update(parentOrder);
	}

	@Override
	public void cancel(ParentOrder parentOrder, Code proessStatus, User operator) throws OrderStatusException,
			CustomerAccountException, PresentCardException, PresentException, ReturnOrderException {
		for (Order order : parentOrder.getOrderList()) {
			orderService.cancel(order, proessStatus, operator);
		}
	}

	@Override
	public void archive(ParentOrder parentOrder, Employee operator) throws OrderStatusException {
		for (Order order : parentOrder.getOrderList()) {
			orderService.nullify(order, operator);
		}
	}

	@Override
	public int getCurrentStatus(ParentOrder parentOrder) {
		int flag = 0;
		Set<Order> subOrders = new HashSet<Order>();
		if (parentOrder.getOrderList() != null) {
			subOrders = parentOrder.getOrderList();
		}
		Map<Order, Long> deliveryFlags = new HashMap<Order, Long>();
		for (Order subOrder : subOrders) {
			deliveryFlags.put(subOrder, subOrder.getProcessStatus().getId());
		}
		// 只要存在一个处于已提交、等待配货和正在配货的子订单，就不会发送短信
		if (deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_NEW)
				|| deliveryFlags.containsKey(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)
				|| deliveryFlags.containsKey(Code.ORDER_PROCESS_STATUS_PICKING)) {
			flag = 1;
		}
		// 当存在一个子订单为已发货、部分发货或者交易完成状态，
		// 并且不存在子订单处于已提交、等待配货和正在配货的，就会发送拆单、分包短信
		if ((deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_DELIVERIED)
				|| (deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)) || (deliveryFlags
					.containsValue(Code.ORDER_PROCESS_STATUS_COMPLETED)))
				&& !deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_NEW)
				&& !deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)
				&& !deliveryFlags.containsValue(Code.ORDER_PROCESS_STATUS_PICKING)) {
			flag = 2;
		}
		return flag;
	}
}
