/*
 * @(#)CancelOverduePayOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 取消逾期未支付的订单
 * 
 * @author HideHai
 * @version 1.0,2011-12-5
 */
@Component("cancelOverduePayOrder")
public class CancelOverduePayOrder implements TaskAware, Serializable {

	private static final long serialVersionUID = 4546559910787921343L;
	private static final Log LOG = LogFactory.getLog(CancelOverduePayOrder.class);
	private static final String SELECT_SQL = "SELECT id FROM _order o WHERE o.maxpaytime is not null AND o.maxpaytime <NOW()"
			+ " AND o.processstatus = 8001 AND o.paymentstatus in (4002,4003)";

	@Autowired
	private OrderService orderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	@Override
	public void start() {
		List<String> orders = jdbcTemplateEcCore.queryForList(SELECT_SQL, String.class);
		if (orders != null && !orders.isEmpty()) {
			Order order = null;
			Employee operator = employeeService.get(Employee.SYSTEM);
			Code cancelCode = new Code(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL);
			for (String id : orders) {
				hibernateLazyResolver.openSession();
				try {
					order = orderService.get(id);
					if (order != null) {
						orderService.cancel(order, cancelCode, operator);
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				} finally {
					hibernateLazyResolver.releaseSession(); 
				}
			}
		}
	}

	@Override
	public String getName() {
		return "cancelOverduePayOrder";
	}

	@Override
	public String getDescription() {
		return "取消逾期未支付的订单";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

}
