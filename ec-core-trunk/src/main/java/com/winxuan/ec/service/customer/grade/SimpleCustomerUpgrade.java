package com.winxuan.ec.service.customer.grade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.winxuan.ec.dao.CustomerDao;
import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 用户晋级具体实现类
 * @author sunflower
 *
 */
@Service("customerUpgrade")
public class SimpleCustomerUpgrade extends CustomerUpgrade implements Serializable {

	private static final long serialVersionUID = -4360067526709957728L;
	private static final int CAN_UPGRADE_TO_GOLD = 15000;
	private static final int CAN_UPGRADE_TO_SILVER = 7000;
	private static final int CAN_UPGRADE_TO_SILVER_ORDER = 2800;
	private static final int CAN_UPGRADE_TO_GOLD_ORDER = 5800;
	
	@InjectDao
	protected CustomerDao customerDao;
	
	@InjectDao
	protected OrderDao orderDao;
	
	@Override
	protected boolean canUpgradeToGoldByOrderPoints(Customer customer) {
		

		return canUpgrade(customer,CAN_UPGRADE_TO_GOLD_ORDER);
	}

	@Override
	protected boolean canUpgradeToSilverByOrderPoints(Customer customer) {

		return canUpgrade(customer,CAN_UPGRADE_TO_SILVER_ORDER);
	}
	
	private boolean canUpgrade(Customer customer, int needPoints) {
		
		List<Order> orders = findOrdersOfLastDay(customer);
		if(orders == null || orders.size() == 0){
			return false;
		}
		for(Order order : orders){
			
			if(order.getPurchaseTotalPoints() >= needPoints){
				return true;
			}
		}
		return false;
	}

	
	private List<Order> findOrdersOfLastDay(Customer customer) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, MagicNumber.NEGATIVE_ONE);
		Calendar startCreateCalendar = calendar;
		Calendar endCreateCalendar = calendar;
		startCreateCalendar.set(Calendar.HOUR_OF_DAY, MagicNumber.ZERO);
		startCreateCalendar.set(Calendar.MINUTE, MagicNumber.ZERO);
		startCreateCalendar.set(Calendar.SECOND, MagicNumber.ZERO);
		Date startCreateTime = startCreateCalendar.getTime();

		endCreateCalendar.set(Calendar.HOUR_OF_DAY, MagicNumber.DATE_MAX_HOUR);
		endCreateCalendar.set(Calendar.MINUTE, MagicNumber.DATE_MAX_MINUTE);
		endCreateCalendar.set(Calendar.SECOND, MagicNumber.DATE_MAX_SECOND);
		Date endCreateTime = endCreateCalendar.getTime();
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
/*		parameters.put("processStatus", new Long[] {
				Code.ORDER_PROCESS_STATUS_COMPLETED,
				Code.ORDER_PROCESS_STATUS_DELIVERIED,
				Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG,
				Code.ORDER_PROCESS_STATUS_NEW,
				Code.ORDER_PROCESS_STATUS_PICKING,
				Code.ORDER_PROCESS_STATUS_WAITING_PICKING,
				});*/
		parameters.put("startCreateTime", startCreateTime);
		parameters.put("endCreateTime", endCreateTime);
		parameters.put("customerId", customer.getId());
		return orderDao.find(parameters, null, null);
	}

	@Override
	protected boolean canUpgradeToGoldByAccount(Customer customer) {
		int points = customer.getAccount().getPoints();
		if(points >= CAN_UPGRADE_TO_GOLD){
			return true;
		}
		return false;
	}

	@Override
	protected boolean canUpgradeToSilverByAccount(Customer customer) {
		int points = customer.getAccount().getPoints();
		if(points >= CAN_UPGRADE_TO_SILVER){
			return true;
		}
		return false;
	}

	@Override
	protected void upgrade(short grade,Customer customer){
		customer.setGrade(grade);
		 customerDao.update(customer);
	}
	
}
