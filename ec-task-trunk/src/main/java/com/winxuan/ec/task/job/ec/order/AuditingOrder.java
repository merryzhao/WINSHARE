/*
 * @(#)AuditingOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.winxuan.ec.exception.OrderShipperInfoMatchException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderShipperInfo;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.order.OrderShipperInfoService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.ec.task.util.ShipperKeywordUtils;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 自动审核EC订单 规则： <br />
 * 1.1码洋小于1000且订单无备注； <br />
 * 1.2下单用户必须满足：历史订单中至少有1次货到付款成功交易(即订单发货且无退货情况)且在最近2个月内无退货记录； <br />
 * 1.3订单中收货人姓名和地址信息都齐全且姓名和地址中都不含 !~@$%^&* 特殊字符； <br />
 * 1.4收货人电话中：座机号码不含区号时位数为7位或8位的、手机号码必须为11位；<br />
 * 1.5 手机和座机至少填写了一项。<br />
 * 
 * 货到付款订单审核和EC订单承运商匹配
 * 
 * @author HideHai
 * @version 1.0,2011-11-16
 */
@Component("auditingOrder")
public class AuditingOrder implements TaskAware, Serializable, TaskConfigure {

	private static final long serialVersionUID = -6435863052036882389L;

	private static final Log LOG = LogFactory.getLog(AuditingOrder.class);
	private static final char[] DIERY_KEY = "!~@$%^&*".toCharArray();
	private static final String REMARK_INVOICE = "发票";
	private static final String CHANNEL_DANGDANG = "8096,8097";

	/**
	 * 最近2个月没有退货码洋小于1000需要审核的订单
	 */
	private static final String NEED_AUDITING_ORDER_SQL = "SELECT DISTINCT o.id _order "
			+ "FROM _order o "
			+ "LEFT JOIN returnorder  b ON o.id=b.originalorder AND b.createtime>=DATE_ADD(now(),INTERVAL -60 day) AND b.createtime<=now() "
			+ "WHERE b.originalorder is null AND o.processstatus = 8001 "
			+ "AND o.paytype = 5002 AND o.paymentstatus in (4002,4003) "
			+ "AND o.listprice < 1000 AND o.channel not in (?)";

	private static final String NEED_AUDITING_ORDER_DANGDANG_COD = "SELECT DISTINCT o.id _order FROM _order o WHERE o.processstatus = 8001 "
			+ "AND o.paytype = 5002 AND o.paymentstatus in (4002,4003) " + "AND o.listprice < 500 and o.channel in (?)";

	private static final String CHECK_HISTORY_DONE_ORDER = "select count(*) from _order o where o.paytype = 5002 "
			+ "AND o.processstatus in (8004,8005,8011) and o.customer = ?";

	private static final String CHECK_NORMAL_ORDER_SQL = "select count(*) from _order o "
			+ "left join  returnorder r on o.id = r.originalorder  " + "where r.id is NOT null AND o.paytype = 5002 "
			+ "AND o.processstatus in (8004,8005,8011) and o.customer = ? "
			+ "AND r.refundtime>=DATE_ADD(NOW(),INTERVAL -60 DAY) " + "AND r.refundtime<=NOW() ";
	/**
	 * 最近2个月需要匹配承运商的出货dc为成都青白江仓的订单(8A17)
	 */
	private static final String NEED_MATCH_SHIPPER_ORDER = "select distinct o.id   from _order o left join order_shipper_info osi on osi._order=o.id"
			+ " left join order_distribution_center odc ON odc._order=o.id "
			+ " where o.processstatus in(8001,8002) AND osi._order is null AND odc.dcoriginal=110003 and o.createtime>=DATE_ADD(now(),INTERVAL -60 day) AND o.createtime<=now()";

	@Autowired
	private OrderService orderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	@Autowired
	private OrderShipperInfoService orderShipperInfoService;

	@Override
	public String getName() {
		return "auditingOrder";
	}

	@Override
	public String getDescription() {
		return "EC订单自动审核和EC订单承运商匹配";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		/**
		 * COD订单审核
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				auditCodOrder();
			}
		}).start();
		/**
		 * 自动匹配承运商信息任务
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				matchOrderShipperInfo();
			}
		}).start();
				
	}

	private void auditCodOrder() {
		List<String> needAuditingOrder = null;
		/**
		 * 自动审核EC订单
		 */
		if ((needAuditingOrder = findNeedAuditingOrder()) != null && !needAuditingOrder.isEmpty()) {
			for (String id : needAuditingOrder) {
				hibernateLazyResolver.openSession();
				try {
					Order order = orderService.get(id);
					if (order.isCODOrder()) {
						autoCheckingDangDang(order);
						continue;
					}
					autoChecking(order);
				} catch (Exception e) {
					// 运行时异常继续处理其他订单
					continue;
				} finally {
					hibernateLazyResolver.releaseSession();
				}
			}

		}
	}

	private void matchOrderShipperInfo() {
		List<String> orders = this.queryNeedMatchShipperOrderList();
		if (CollectionUtils.isNotEmpty(orders)) {
			for (String orderId : orders) {
				hibernateLazyResolver.openSession();
				try {
					Order order = orderService.get(orderId);
					autoMatchOrderShipperInfo(order);
				} catch (Exception e) {
					// 运行时异常继续处理其他订单
					continue;
				} finally {
					hibernateLazyResolver.releaseSession();
				}
			}
		}
	}

	/**
	 * 自动匹配订单承运商。 <br />
	 * 1.如果满足自动匹配条件，自动匹配承运商；如果自动匹配抛错，记录默认匹配异常信息。<br />
	 * 2.如果不满足自动匹配条件，记录默认的人工审核信息<br />
	 * 
	 * @param order
	 */
	private void autoMatchOrderShipperInfo(Order order) {
		Preconditions.checkArgument(null != order, "Order is null!");
		OrderConsignee consignee = order.getConsignee();
		Preconditions.checkNotNull(consignee, "Order consignee is null!");
		// 详细地址
		String address = consignee.getAddress();
		List<String> addressKeyword = ShipperKeywordUtils.getAddressKeyword();
		boolean addressFlag = isContainsKeyword(address, addressKeyword);
		// 备注信息
		String remark = consignee.getRemark();
		boolean remarkFlag = Boolean.FALSE;
		if (org.apache.commons.lang.StringUtils.isNotBlank(remark)) {
			List<String> remarkKeyword = ShipperKeywordUtils.getRemarkKeyword();
			remarkFlag = isContainsKeyword(remark, remarkKeyword);
		}
		// 详细地址或者备注信息不满足条件，需要人工审核，新增一条判断是否需要人工审核承运商的货运信息
		if (addressFlag || remarkFlag) {
			LOG.info(order.getId() + " 属性不满足自动审核承运商的条件，进入人工审核！");
			this.saveOrderShipperInfo(order, OrderShipperInfo.DEFAULT_STATUS, null);
		} else {
			// 满足条件的订单直接调用DMS匹配承运商信息
			try {
				LOG.info(order.getId() + " 满足的条件自动审核，自动匹配承运商！");
				this.orderShipperInfoService.matchingOrderShipperInfo(order);
			} catch (OrderShipperInfoMatchException e) {
				// 如果调用DMS有异常也算做需要人工审核的订单，状态为2
				LOG.info(order.getId() + " 调用DMS抛出异常，进入人工审核处理。异常信息：" + e.getMessage());
				this.saveOrderShipperInfo(order, OrderShipperInfo.ERROR_STATUS, e.getMessage());
			}
		}

	}

	/**
	 * 是否包含对应的关键字
	 * 
	 * @param source
	 * @param keywords
	 * @return
	 */
	private boolean isContainsKeyword(String source, List<String> keywords) {
		boolean flag = Boolean.FALSE;
		Preconditions.checkArgument(null != source, "source is null!");
		Preconditions.checkArgument(null != keywords, "keywords is null!");
		// 统计转成小写，以方便匹配
		source = source.toLowerCase();
		for (String keyword : keywords) {
			flag = source.contains(keyword);
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 新增货运公司信息,处理异常信息
	 * 
	 * @param order
	 * @param status
	 */
	private void saveOrderShipperInfo(Order order, short status, String errorMessage) {
		OrderShipperInfo orderShipperInfo = buildDefaultOrderShipperInfo(order);
		orderShipperInfo.setStatus(status);
		if (OrderShipperInfo.ERROR_STATUS.equals(status)) {
			orderShipperInfo.setErrorMessage(errorMessage);
		}
		this.orderShipperInfoService.save(orderShipperInfo);
	}

	/**
	 * 人工审核时创建默认的货运信息，适用于状态0或者状态2
	 * 
	 * @param order
	 * @return
	 */
	private OrderShipperInfo buildDefaultOrderShipperInfo(Order order) {
		OrderShipperInfo orderShipperInfo = new OrderShipperInfo();
		orderShipperInfo.setOrderId(order.getId());
		orderShipperInfo.setCarriageType(OrderShipperInfo.DEFAULT_CARRIAGE_TYPE);
		orderShipperInfo.setJobType(OrderShipperInfo.DEFAULT_JOB_TYPE);
		orderShipperInfo.setCreateTime(new Date());
		return orderShipperInfo;
	}

	public List<String> findNeedAuditingOrder() {
		List<String> orderIdList = jdbcTemplateEcCore.query(NEED_AUDITING_ORDER_SQL, new Object[] { CHANNEL_DANGDANG },
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("_order");
					}
				});
		orderIdList.addAll(jdbcTemplateEcCore.query(NEED_AUDITING_ORDER_DANGDANG_COD,
				new Object[] { CHANNEL_DANGDANG }, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("_order");
					}
				}));
		return orderIdList;
	}

	/**
	 * 检查传入的用户 正常发货，没有退货记录的货到付款订单数
	 * 
	 * @param customer
	 * @return
	 */
	public boolean hasNormalOrder(Customer customer) {
		return jdbcTemplateEcCore.queryForInt(CHECK_HISTORY_DONE_ORDER, new Object[] { customer.getId() }) > 0
				&& jdbcTemplateEcCore.queryForInt(CHECK_NORMAL_ORDER_SQL, new Object[] { customer.getId() }) == 0;
	}

	public void autoCheckingDangDang(Order order) {
		try {
			String phone = order.getConsignee().getPhone();
			String mobile = order.getConsignee().getMobile();
			String remark = order.getConsignee().getRemark();
			if ((StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(mobile))
					|| StringUtils.isNullOrEmpty(order.getConsignee().getConsignee())
					|| StringUtils.isNullOrEmpty(order.getConsignee().getAddress())) {
				LOG.info(order.getId() + " 属性不满足自动审核条件!");
				return;
			}
			for (char c : DIERY_KEY) {
				if (order.getConsignee().getConsignee().contains(c + "")
						|| order.getConsignee().getAddress().contains(c + "")) {
					LOG.info(order.getId() + " 包含审核非法字符:" + c);
					return;
				}
			}
			boolean validateWay = false;
			if (!StringUtils.isNullOrEmpty(phone)) {
				int index = phone.indexOf('-');
				if (index != -1) {
					String phoneNumber = phone.substring(index + MagicNumber.ONE, phone.length());
					String phoneArea = phone.substring(0, index);
					if (NumberUtils.isNumber(phoneArea)
							&& (phoneArea.length() < MagicNumber.FOUR)
							&& (NumberUtils.isNumber(phoneNumber) && (phoneNumber.length() == MagicNumber.SEVEN || phoneNumber
									.length() == MagicNumber.EIGHT))) {
						validateWay = true;
					}
				} else {
					// 无区域号
					if (NumberUtils.isNumber(phone)
							&& (phone.length() == MagicNumber.SEVEN || phone.length() == MagicNumber.EIGHT)) {
						validateWay = true;
					}
				}
			}
			if (!StringUtils.isNullOrEmpty(mobile)) {
				if (NumberUtils.isNumber(mobile) && mobile.length() == MagicNumber.ELEVEN) {
					validateWay = true;
				}
			}
			if (!validateWay) {
				LOG.info(order.getId() + " 联系方式之一不满足规则!");
				return;
			}
			if (!StringUtils.isNullOrEmpty(remark) && remark.contains(REMARK_INVOICE)) {
				LOG.info("order id" + order.getId() + ";" + order.getChannel().getName() + " COD订单备注信息含 发票 2字不满足规则”");
				return;
			}

			orderService.audit(order, employeeService.get(Employee.SYSTEM));
			LOG.info(order.getId() + " auditing.");
		} catch (Exception e) {
			doNotify(order.getId());
			LOG.error(order.getId() + ": transfers exception " + e.getMessage(), e);
		}
	}

	public void autoChecking(Order order) {
		try {
			boolean doCheck = hasNormalOrder(order.getCustomer());
			if (!doCheck) {
				LOG.info(order.getId() + " 没有历史货到付款未退款订单记录!");
				return;
			}
			String phone = order.getConsignee().getPhone();
			String mobile = order.getConsignee().getMobile();
			if ((StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(mobile))
					|| StringUtils.isNullOrEmpty(order.getConsignee().getConsignee())
					|| StringUtils.isNullOrEmpty(order.getConsignee().getAddress())
					|| !StringUtils.isNullOrEmpty(order.getConsignee().getRemark())) {
				LOG.info(order.getId() + " 属性不满足自动审核条件!");
				return;
			}
			for (char c : DIERY_KEY) {
				if (order.getConsignee().getConsignee().contains(c + "")
						|| order.getConsignee().getAddress().contains(c + "")) {
					LOG.info(order.getId() + " 包含审核非法字符:" + c);
					return;
				}
			}
			boolean validateWay = false;
			if (!StringUtils.isNullOrEmpty(phone)) {
				int index = phone.indexOf('-');
				// 有区域号
				if (index != -1) {
					String phoneNoArea = phone.substring(index + MagicNumber.ONE, phone.length());
					if (NumberUtils.isNumber(phoneNoArea)
							&& (phoneNoArea.length() == MagicNumber.SEVEN || phoneNoArea.length() == MagicNumber.EIGHT)) {
						validateWay = true;
					}
				}
			}
			if (!StringUtils.isNullOrEmpty(mobile)) {
				if (NumberUtils.isNumber(mobile) && mobile.length() == MagicNumber.ELEVEN) {
					validateWay = true;
				}
			}
			if (!validateWay) {
				LOG.info(order.getId() + " 联系方式之一不满足规则!");
				return;
			}
			orderService.audit(order, employeeService.get(Employee.SYSTEM));
			LOG.info(order.getId() + " auditing.");
		} catch (Exception e) {
			doNotify(order.getId());
			LOG.error(order.getId() + ": transfers exception " + e.getMessage(), e);
		}
	}

	@Override
	public int getNotifyLevel() {
		return TaskConfigure.LEVEL_ALL;
	}

	@Override
	public void doNotify(String... msg) {
		notifyService.notify(this, String.format("%s 自动审核过程中发生未知异常!", msg[0]));
	}

	/**
	 * 查询需要匹配承运商的订单
	 * 
	 * @return
	 */
	private List<String> queryNeedMatchShipperOrderList() {
		return this.jdbcTemplateEcCore.queryForList(NEED_MATCH_SHIPPER_ORDER, String.class);
	}

}
