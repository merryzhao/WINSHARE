package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderReceive;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * @author HideHai
 * @version 1.0,2012-3-29下午06:18:53
 */
@Component("completeOrder")
public class CompleteOrder implements TaskAware, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8552275322023955781L;

	private static final Log LOG = LogFactory.getLog(CompleteOrder.class);

	private static final Integer PAGE_SIZE = Integer.valueOf(100);

	@Autowired
	OrderService orderService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Override
	public String getName() {
		return "completeOrder";
	}

	@Override
	public String getDescription() {
		return "订单确认完成";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		LOG.debug("task CompleteOrder started!!!!");
		hibernateLazyResolver.openSession();
		int currentPage = MagicNumber.ONE;
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(currentPage);
		pagination.setPageSize(PAGE_SIZE);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, MagicNumber.TEN * MagicNumber.TWO
				* MagicNumber.NEGATIVE_ONE);
		Calendar startDeliveryCalendar = calendar;
		Calendar endDeliveryCalendar = calendar;
		startDeliveryCalendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ZERO);
		startDeliveryCalendar.set(Calendar.MINUTE, MagicNumber.ZERO);
		startDeliveryCalendar.set(Calendar.SECOND, MagicNumber.ZERO);
		Date startDeliveryTime = startDeliveryCalendar.getTime();

		endDeliveryCalendar
				.set(Calendar.HOUR_OF_DAY, MagicNumber.DATE_MAX_HOUR);
		endDeliveryCalendar.set(Calendar.MINUTE, MagicNumber.DATE_MAX_MINUTE);
		endDeliveryCalendar.set(Calendar.SECOND, MagicNumber.DATE_MAX_SECOND);
		Date endDeliveryTime = endDeliveryCalendar.getTime();

		List<Order> list = null;

		// 取20天以前已经发货的但是未确认收货的订单
		while ((list = orderService.findNeedConfirmReceiveOrder(
				startDeliveryTime, endDeliveryTime, pagination,true)) != null
				&& !list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {
				Order order = list.get(i);
				OrderReceive receive = new OrderReceive();
				receive.setOrder(order);
				receive.setReceiveTime(new Date());
				receive.setCreateTime(new Date());
				receive.setSource(new Code(Code.ORDER_CONFIRM_TYPE_SYSTEM_AUTO));
				receive.setAssess(new Code(Code.ORDER_RECEIVE_GENERAL));
				receive.setExpressSpeed(new Code(Code.ORDER_RECEIVE_GENERAL));
				receive.setExpressManner(new Code(Code.ORDER_RECEIVE_GENERAL));
				try {
					// 更新订单状态为已完成
					orderService.receive(receive);
				} catch (OrderStatusException e) {
					LOG.error(e.getMessage(), e);
					// 出现错误，继续执行
				}
			}
			pagination.setCurrentPage(++currentPage);
		}
		hibernateLazyResolver.releaseSession();
		LOG.debug("task CompleteOrder ended!!!!");
	}

}
