/*
 * @(#)ErpOrderServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.erp.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.DeliveryCompanyDao;
import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.dao.OrderDeliverySplitDao;
import com.winxuan.ec.dao.OrderDistributionCenterDao;
import com.winxuan.ec.exception.OrderDcMatchException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.erp.EcErpOrder;
import com.winxuan.ec.model.erp.EcErpOrderDelivery;
import com.winxuan.ec.model.erp.EcErpOrderItem;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderCourierReceipt;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderStatusLog;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderDc;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.erp.EcErpOrderDeliveryService;
import com.winxuan.ec.service.erp.EcErpOrderItemService;
import com.winxuan.ec.service.erp.EcErpOrderService;
import com.winxuan.ec.service.finance.FinanceService;
import com.winxuan.ec.service.order.OrderInterfaceService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.util.DateUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.task.component.ec.InvoiceItemIdGenerator;
import com.winxuan.ec.task.dao.ec.EcDao;
import com.winxuan.ec.task.dao.erp.ErpDao;
import com.winxuan.ec.task.exception.erp.ErpOrderTransferException;
import com.winxuan.ec.task.model.erp.ErpArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryCompany;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.ec.task.model.erp.ErpOrderInvoice;
import com.winxuan.ec.task.model.erp.ErpOrderItem;
import com.winxuan.ec.task.model.erp.ErpReturnOrder;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.util.BigDecimalUtils;
import com.winxuan.framework.util.StringUtils;

/**
 * ERP
 * 
 * @author HideHai
 * @version 1.0,2011-8-30
 */
@Service("erpOrderService")
@Transactional(rollbackFor = Exception.class)
public class ErpOrderServiceImpl implements ErpOrderService, Serializable {

	private static final Log LOG = LogFactory.getLog(ErpOrderServiceImpl.class);

	private static final long serialVersionUID = -625479541775333602L;

	private static final String ERP_SYSTEM_OPERATOR = "Admin";
	@Autowired
	JdbcTemplate jdbcTemplateErp;
	@InjectDao
	private OrderDao orderDao;
	@InjectDao
	private OrderDeliverySplitDao orderDeliverySplitDao;
	@InjectDao
	private DeliveryCompanyDao deliveryCompanyDao;
	@InjectDao
	private OrderDistributionCenterDao orderDistributionCenterDao;
	@Autowired
	private ErpDao erpDao;
	@Autowired
	private EcDao ecDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderInterfaceService orderInterfaceService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private FinanceService financeService;
	@Autowired
	private InvoiceItemIdGenerator invoiceItemIdGenerator;
	@Autowired
	private DcService dcService;
	@Autowired
	private EcErpOrderService ecErpOrderService;
	@Autowired
	private EcErpOrderItemService ecErpOrderItemService;
	@Autowired
	private EcErpOrderDeliveryService ecErpOrderDeliveryService;

	@Override
	public void transferOrder(Order order) throws Exception {
		order = orderService.get(order.getId());
		OrderDistributionCenter dc = order.getDistributionCenter();
		if (dc == null) {
			LOG.info("dc is null" + order);
			return;
		}
//		if (order.getDistributionCenter().getDcDest().getId().equals(Code.DC_8A17)) {
//			return;
//		}
		checkOrder(order);
		sendOrder(order);
		sendOrderItem(order);
		if (order.getConsignee().isNeedInvoice()) {
			transferUserInvoiceByOrder(order);
		}
		orderService.transportNewOrder(order);
		LOG.info(order.getId() + ": transfers");
	}

	@Override
	public void transferOrderInvoice(OrderInvoice orderInvoice) throws Exception {
		Order order = orderInvoice.getOrder();
		if (order.getDistributionCenter().getDcDest().getId().equals(Code.DC_8A17)) {
			return;
		}
		OrderConsignee consignee = order.getConsignee();
		Object[] invoice = new Object[] { orderInvoice.getId(), orderInvoice.getId(), 0, orderInvoice.getTitle(), orderInvoice.getContent(), orderInvoice.getMoney(),
				orderInvoice.getQuantity(), BigDecimalUtils.capitalize(orderInvoice.getMoney()), orderInvoice.getMoney(), 2 };
		erpDao.sendUserInovice(invoice);

		Object[] invoiceOrder = new Object[] { orderInvoice.getId(), orderInvoice.getId(), ErpParseUtils.parseDeliveryType(order), order.getCustomer().getId().toString(),
				orderInvoice.getConsignee(), orderInvoice.getDistrict().getId(), orderInvoice.getZipCode(), getInvoiceAddress(orderInvoice), order.getPurchaseQuantity(),
				order.getListPrice(), order.getSalePrice(), order.getPurchaseKind(), ErpParseUtils.parseDeliveryTimeDesc(consignee), "true", orderInvoice.getTitle(),
				orderInvoice.getContent(), order.getInvoiceMoney(), order.getRequidPayMoney(), orderInvoice.getMobile(), "N", ErpParseUtils.parsePaymentStatus(order),
				ErpParseUtils.parseChannelType(order), order.getChannel().getId(), orderInvoice.getDistrict().getId(), ErpParseUtils.parsePayDeliveryFee(order),
				order.getSaveMoney(), order.getDeliveryFee(), order.getAdvanceMoney(), order.getSalePrice(), orderInvoice.getRemark(), "7",
				ErpParseUtils.parseOutOfStockOption(consignee), null, null, order.getDistributionCenter().getDcOriginal().getName() };
		erpDao.sendInoviceOrder(invoiceOrder);

		Object[] invoiceOrderItem = null;
		for (OrderItem orderItem : order.getItemList()) {
			ProductSale productSale = orderItem.getProductSale();
			Product product = productSale.getProduct();
			invoiceOrderItem = new Object[] { invoiceItemIdGenerator.generator(), orderInvoice.getId(), orderInvoice.getId(), ErpParseUtils.parseDeliveryType(order),
					product.getMerchId(), ErpParseUtils.parseProductSort(product), orderItem.getListPrice(), orderItem.getBalancePrice(), getOrderItemDiscount(orderItem),
					ErpParseUtils.parseArea(consignee), consignee.getZipCode(), getInvoiceAddress(orderInvoice), orderItem.getPurchaseQuantity(), orderItem.getTotalListPrice(),
					orderItem.getTotalBalancePrice(), ErpParseUtils.parsePaymentStatus(order), "false", ErpParseUtils.parseErpShopId(orderItem.getShop()), "7" };
			erpDao.sendInoviceOrderItem(invoiceOrderItem);
		}
		orderInvoiceService.completeTranfer(orderInvoice);
		LOG.info("transfer order invoice : " + orderInvoice.getId());
	}

	@Override
	public void transferReturnOrder(ReturnOrder returnOrder) throws Exception {
		returnOrder = returnOrderService.get(returnOrder.getId());
		if (returnOrder.getOriginalOrder().getDistributionCenter().getDcDest().getId().equals(Code.DC_8A17)) {
			return;
		}
		checkReturnOrder(returnOrder);
		sendReturnOrder(returnOrder);
		sendChargeOffOrder(returnOrder);
		sendReturnOrderItem(returnOrder);
		Employee operator = employeeService.get(Employee.SYSTEM);
		returnOrderService.startReturn(returnOrder, operator);
		LOG.info("transfer returnOrder : " + returnOrder.getId());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ErpOrder> fetchOrderState() {
		return erpDao.fetchOrderState();
	}

	@Override
	public List<ErpOrder> fetchOrderState(String orderId) {
		return erpDao.fetchOrderState(orderId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> fetchNeedProcessOrder() {
		return ecDao.fetchNeedProcessBlockOrder();
	}

	@Override
	public List<ErpOrder> fetchErpRejectOrder() {
		return erpDao.fetchOrderReject();
	}

	@Override
	public void processUnsuccessBlockOrder(ErpOrder erpOrder) throws Exception {
		Order ecOrder = isNeedUpdateOrderStatus(erpOrder);
		if (!(ecOrder.canbeDelivery() || ecOrder.isDeliveried())) {
			throw new ErpOrderTransferException(ecOrder.getId() + ": order status error for delivery!");
		}
		if (erpOrder.getState().equals(ErpOrder.STATUS_CANCELLED_FOR_REJECT)) {
			// 如果中启拦截人是Admin，并且是COD订单，中启拦截了默认就是缺货，就回传渠道缺货取消
			if (ERP_SYSTEM_OPERATOR.equals(erpOrder.getCuser()) && ecOrder.isCODOrder()) {
				processErpOutOfStockCancel(erpOrder, ecOrder);
			} else {
				processErpCancel(erpOrder, ecOrder);
			}
			ecDao.updateBlockOrder(erpOrder, EcDao.BLOCK_SUCCESS);
		} else if (processErpDelivery(ecOrder)) {
			ecDao.updateBlockOrder(erpOrder, EcDao.BLOCK_DELIVERY);
		}
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
	}

	private void orderCustomerCancel(ErpOrder erpOrder, Order ecOrder) throws Exception {
		if (ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)) {
			erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
			return;
		}
		orderService.cancel(ecOrder, new Code(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL), employeeService.get(Employee.SYSTEM));
		updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_CANCEL));
		sendChannelOrde(ecOrder);
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
		LOG.info(erpOrder.getOrderId() + " erp customer cancel!");
	}

	public void processFetchOrder(ErpOrder erpOrder) throws Exception {
		Order ecOrder = isNeedUpdateOrderStatus(erpOrder);
		String erpOrderState = erpOrder.getState();

		if (erpOrderState.equals(ErpOrder.STATUS_DELIVERY)) { // 发货
			if (ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT)) {
				if (ecErpOrderService.get(erpOrder.getOrderId(), ErpOrder.STATUS_DELIVERY) == null) {
					// 记录到中间表
					saveOrderStatus(erpOrder);
					erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrderState);
				}
			}
			if (ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)) {
				processErpDelivery(erpOrder, ecOrder);
				this.updateDistributionCenter(ecOrder, erpOrder);
			} else if (ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)) {
				LOG.info(String.format("delivery order flag process,id:%s,status:%s", ecOrder.getId(), ecOrder.getProcessStatus().getId()));
				erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
			} else if (ecOrder.isDeliveried()) {
				updateEcOrderDeliveryInfoByErpOrder(erpOrder, ecOrder);
				this.updateDistributionCenter(ecOrder, erpOrder);
			} else {
				LOG.info(String.format("delivery order ingore process,id:%s,status:%s", ecOrder.getId(), ecOrder.getProcessStatus().getId()));
			}

		} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_OUT_OF_STOCK)) { // 缺货取消
			if (ecOrder.canbeOutOfStockCancel()) {
				processErpOutOfStockCancel(erpOrder, ecOrder);
			} else {
				erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
			}
		} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_REJECT)) {
			if (ecOrder.canbeErpCancel()) {
				processErpCancel(erpOrder, ecOrder); // 中启取消
			} else if (ecOrder.isDeliveried()) {
				processsErpReject(erpOrder, ecOrder); // 拒收取消
			} else {
				LOG.info(String.format("statue 09 ingore process,id:%s", ecOrder.getId()));
			}
			if (ecDao.existErpBlock(ecOrder.getId())) {
				ecDao.updateBlockOrder(erpOrder, EcDao.BLOCK_SUCCESS);
			}
		} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_BY_CUSTOMER)) { // 客户取消
			orderCustomerCancel(erpOrder, ecOrder);
		} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_BY_UNRECIVE)) { // 未送达
			processsErpReject(erpOrder, ecOrder);
		} else if (erpOrderState.equals(ErpOrder.STATUS_CONFIRM)) { // 妥投
			updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_CONFIRM));
		} else if (erpOrderState.equals(ErpOrder.STATUS_RETURN_ONSHELF)) { // 退货数据记录
			financeService.updateReturnFinance(ecOrder, erpOrder.getFhrq());
			erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
			LOG.info(String.format("Sale Order finance,id: %s", ecOrder.getId()));
		}
	}

	/**
	 * 保存等待拦截的订单信息
	 * 
	 * @param erpOrder
	 */
	private void saveOrderStatus(ErpOrder erpOrder) {
		// 保存订单信息
		saveEcErpOrder(erpOrder);
		// 保存订单明细
		saveEcErpOrderItem(erpOrder);
		// 保存运单信息
		saveEcOrderDelivery(erpOrder);
	}

	private void saveEcErpOrderItem(ErpOrder erpOrder) {
		List<ErpOrderItem> erpOrderItems = erpDao.fetchOrderStateItem(erpOrder.getOrderId());
		for (ErpOrderItem ei : erpOrderItems) {
			EcErpOrderItem ecErpOrderItem = new EcErpOrderItem();
			ecErpOrderItem.setOrder(erpOrder.getOrderId());
			ecErpOrderItem.setMerchid(ei.getCommodity().intValue());
			ecErpOrderItem.setDeliveryQuantity(ei.getQuantity());
			ecErpOrderItem.setOutOfStockQuantity(ei.getOutOfStockQuantity());
			ecErpOrderItemService.save(ecErpOrderItem);
		}
	}

	private void saveEcOrderDelivery(ErpOrder erpOrder) {
		List<ErpOrder> orderDeliveryInfo = erpDao.fetchOrderDelivery(erpOrder.getOrderId());
		for (ErpOrder e : orderDeliveryInfo) {
			EcErpOrderDelivery ecErpOrderDelivery = new EcErpOrderDelivery();
			ecErpOrderDelivery.setOrder(e.getOrderId());
			ecErpOrderDelivery.setState(e.getState());
			ecErpOrderDelivery.setOrderDelivery(e.getCode());
			ecErpOrderDelivery.setDeliveryCompany(Integer.valueOf(e.getDeliveryCompany()));
			ecErpOrderDelivery.setDdlxid(e.getDdlxid());
			ecErpOrderDelivery.setDeliveryTime(e.getFhrq());
			ecErpOrderDelivery.setDc(e.getDc());
			ecErpOrderDeliveryService.save(ecErpOrderDelivery);
		}
	}

	private void saveEcErpOrder(ErpOrder erpOrder) {
		EcErpOrder ecErpOrder = new EcErpOrder();
		ecErpOrder.setOrder(erpOrder.getOrderId());
		ecErpOrder.setState(erpOrder.getState());
		ecErpOrder.setOrderDelivery(erpOrder.getCode());
		ecErpOrder.setDeliveryCompany(Integer.valueOf(erpOrder.getDeliveryCompany()));
		ecErpOrder.setDdlxid(erpOrder.getDdlxid());
		ecErpOrder.setDeliveryTime(erpOrder.getFhrq());
		ecErpOrder.setGkthfsid(erpOrder.getReturnTypeId());
		ecErpOrder.setDc(erpOrder.getDc());
		ecErpOrder.setStatus(EcErpOrder.NOT_DEAL);
		ecErpOrderService.save(ecErpOrder);
	}

	/**
	 * 发货的时候更新dc信息,<br>
	 * dc信息应该在订单创建的时候就初始化,鉴于之前的老数据没有dc信息.实体做的是主键管理.在持久层会抛数据异常.<br>
	 * 这里处理一下.
	 * 
	 * @param order
	 * @param erpOrder
	 * @throws OrderDcMatchException
	 */
	private void updateDistributionCenter(Order order, ErpOrder erpOrder) throws OrderDcMatchException {
		OrderDistributionCenter odc = orderDistributionCenterDao.get(order.getId());
		DcRule dcRule = dcService.findByAvailableDc(erpOrder.getDc());
		if (dcRule == null) {
			throw new OrderDcMatchException(order, String.format("dc:(%s) is not found!", erpOrder.getDc()));
		}
		if (odc == null) {
			odc = new OrderDistributionCenter();
			odc.setDcOriginal(dcRule.getLocation());
		}
		odc.setDcDest(dcRule.getLocation());
		odc.setRemark(dcRule.getLocation().getName());
		odc.setOrder(order);
		order.setDistributionCenter(odc);
	}

	@Override
	@Deprecated
	public void fetchOrder(ErpOrder erpOrder) throws Exception {
		Order ecOrder = isNeedUpdateOrderStatus(erpOrder);
		Long ecStatusId = ecOrder.getProcessStatus().getId();
		LOG.info(String.format("%s status %s process begin! ", ecOrder.getId(), ecStatusId));
		String erpOrderState = erpOrder.getState();
		boolean updateFlag = true;
		if (ecOrder.canbeDelivery()) {
			if (erpOrderState.equals(ErpOrder.STATUS_DELIVERY)) { // 已发出
				if (erpDao.erpDoCancel(erpOrder)) {
					processErpBlockOrder(erpOrder);
					updateFlag = false;
				} else {
					if (!processErpDelivery(erpOrder, ecOrder)) {
						updateFlag = false;
					}
				}
			} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_OUT_OF_STOCK)) { // 缺货取消
				processErpOutOfStockCancel(erpOrder, ecOrder);
			} else if (erpOrderState.equals(ErpOrder.STATUS_PICKING)) { // 集货
				updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_WAIT));
			} else if (erpOrderState.equals(ErpOrder.STATUS_TESTING)) { // 质检包装
				updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_PACKING));
			} else if (erpOrderState.equals(ErpOrder.STATUS_WAITING_DELIVERY)) { // 等待发运
				updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_WAIT_DELIVERY));
			} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_REJECT)) { // 拒收取消
				processErpCancel(erpOrder, ecOrder);
			} else if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_BY_CUSTOMER)) { // 审核未通过-客户取消
				orderService.cancel(ecOrder, new Code(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL), employeeService.get(Employee.SYSTEM));
				updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_CANCEL));
				sendChannelOrde(ecOrder);
				LOG.info(erpOrder.getOrderId() + " erp customer cancel!");
			} else if (erpOrderState.equals(ErpOrder.STATUS_RETURN_ONSHELF)) {
				updateFlag = false;
			}

		} else if (ecOrder.isDeliveried()) {
			if (erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_REJECT) // 拒收
					|| erpOrderState.equals(ErpOrder.STATUS_CANCELLED_BY_UNRECIVE)) { // 未送达
				processsErpReject(erpOrder, ecOrder);
			} else if (erpOrderState.equals(ErpOrder.STATUS_DELIVERY)) {
				updateEcOrderDeliveryInfoByErpOrder(erpOrder, ecOrder);
			} else if (erpOrderState.equals(ErpOrder.STATUS_CONFIRM)) { // 妥投
				updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_CONFIRM));
			}
		} else if (ecStatusId.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL) || ecStatusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)) {
			if (erpOrderState.equals(ErpOrder.STATUS_RETURN_ONSHELF)) {
				LOG.info(String.format("Sale Order finance,id: %s", ecOrder.getId()));
				financeService.processReuturnFinance(ecOrder, erpOrder.getFhrq());
			}
		}
		if (updateFlag) {
			erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
		}
		LOG.info(String.format("%s status %s process end!", ecOrder.getId(), ecOrder.getProcessStatus().getId()));
	}

	/**
	 * 
	 * @param erpOrder
	 * @param ecOrder
	 * @throws Exception
	 */
	public void processErpBlockOrder(ErpOrder erpOrder) throws Exception {
		try {
			Order ecOrder = orderService.get(erpOrder.getOrderId());
			Long processStatusId = ecOrder.getProcessStatus().getId();
			String erpStatus = erpOrder.getState();
			if (processStatusId.equals(Code.ORDER_PROCESS_STATUS_PICKING)) {
				if (erpStatus.equals(ErpOrder.STATUS_DELIVERY)) {
					if (!ecDao.existErpBlock(erpOrder.getOrderId())) {
						ecDao.saveBlockOrder(erpOrder, EcDao.BLOCK_INIT);
					}
					processOrderDeliveryInfo(erpOrder, ecOrder);
					orderService.erpIntercept(ecOrder, employeeService.get(Employee.SYSTEM));
					LOG.info(erpOrder.getOrderId() + " erp do cancel!");
				} else if (erpStatus.equals(ErpOrder.STATUS_CANCELLED_FOR_OUT_OF_STOCK)) {
					processErpOutOfStockCancel(erpOrder, ecOrder);
				}
			} else if (processStatusId.equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT)) {
				if (erpStatus.equals(ErpOrder.STATUS_DELIVERY)) {
					if (!ecDao.existErpBlock(erpOrder.getOrderId())) {
						ecDao.saveBlockOrder(erpOrder, EcDao.BLOCK_INIT);
					}
					OrderStatusLog log = ecOrder.getLogByStatus(processStatusId);
					if (!log.getOperator().getId().equals(Employee.SYSTEM)) {
						processOrderDeliveryInfo(erpOrder, ecOrder);
						orderDao.update(ecOrder);
						LOG.info(erpOrder.getOrderId() + " delivery info log!");
					}
				} else if (erpStatus.equals(ErpOrder.STATUS_CANCELLED_FOR_OUT_OF_STOCK)) {
					processErpOutOfStockCancel(erpOrder, ecOrder);
				}
			}
		} catch (OrderStatusException e) {
			throw new ErpOrderTransferException(e.getMessage());
		}
	}

	/**
	 * 中启取消
	 * 
	 * @throws ErpOrderTransferException
	 */
	private void processErpCancel(ErpOrder erpOrder, Order ecOrder) throws ErpOrderTransferException {
		try {
			orderService.cancel(ecOrder, new Code(Code.ORDER_PROCESS_STATUS_ERP_CANCEL), employeeService.get(Employee.SYSTEM));
			updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_CATCH));
			financeService.processDelvieryFinance(ecOrder, erpOrder.getFhrq());
			sendChannelOrde(ecOrder);
			erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
			LOG.info(erpOrder.getOrderId() + " erp stop cancel!");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new ErpOrderTransferException(e.getMessage());
		}
	}

	/**
	 * ERP订单缺货取消
	 * 
	 * @param erpOrder
	 * @param ecOrder
	 * @throws Exception
	 */
	private void processErpOutOfStockCancel(ErpOrder erpOrder, Order ecOrder) throws Exception {
		Code cancelCode = null;
		String ecOrderId = erpOrder.getOrderId();
		cancelCode = codeService.get(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL);
		List<ErpOrderItem> erpOrderItems = erpDao.fetchOrderStateItem(ecOrderId);
		if (erpOrderItems != null && !erpOrderItems.isEmpty()) {
			Set<OrderItem> items = ecOrder.getItemList();
			for (OrderItem ecOrderItem : items) {
				Long merchantId = ecOrderItem.getProductSale().getProduct().getMerchId();
				for (ErpOrderItem erpOrderItem : erpOrderItems) {
					if (merchantId.equals(erpOrderItem.getCommodity())) {
						LOG.info(String.format("%s stock: %s", ecOrderItem.getId(), erpOrderItem.getOutOfStockQuantity()));
						ecOrderItem.setOutOfStockQuantity(erpOrderItem.getOutOfStockQuantity());
						break;
					}
				}
			}
		}
		orderService.cancel(ecOrder, cancelCode, employeeService.get(Employee.SYSTEM));
		updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_OUT_OF_STOCK));
		sendChannelOrde(ecOrder);
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
		LOG.info(ecOrder.getId() + " out of stock cancel!");
	}

	/**
	 * 记录订单发货信息
	 * 
	 * @param erpOrder
	 * @param ecOrder
	 * @throws ErpOrderTransferException
	 */
	private void processOrderDeliveryInfo(ErpOrder erpOrder, Order ecOrder) throws ErpOrderTransferException {
		String ecOrderId = erpOrder.getOrderId();
		if (StringUtils.isNullOrEmpty(erpOrder.getCode()) || StringUtils.isNullOrEmpty(erpOrder.getDeliveryCompany())) {
			throw new ErpOrderTransferException("erp order delivery is null!");
		}
		List<ErpOrderItem> erpOrderItems = erpDao.fetchOrderStateItem(ecOrderId);
		if (erpOrderItems == null || erpOrderItems.isEmpty()) {
			throw new ErpOrderTransferException(ecOrderId + " : erp order has no order item!");
		}
		Set<OrderItem> items = ecOrder.getItemList();
		if (erpOrderItems.size() != items.size()) {
			throw new ErpOrderTransferException(ecOrderId + " : erp item size not equals ec size!");
		}
		int totalDeliveryQuantity = 0;
		int totalDeliveryKind = 0;
		BigDecimal totalDeliveryListPrice = BigDecimal.ZERO;
		BigDecimal totalDeliverySalePrice = BigDecimal.ZERO;

		for (OrderItem ecOrderItem : items) {
			Long merchantId = ecOrderItem.getProductSale().getProduct().getMerchId();
			for (ErpOrderItem erpOrderItem : erpOrderItems) {
				if (merchantId.equals(erpOrderItem.getCommodity())) {
					int deliveryQuantity = erpOrderItem.getQuantity();
					BigDecimal listPrice = ecOrderItem.getListPrice();
					BigDecimal salePrice = ecOrderItem.getSalePrice();
					ecOrderItem.setDeliveryQuantity(deliveryQuantity);
					totalDeliveryListPrice = totalDeliveryListPrice.add(listPrice.multiply(new BigDecimal(deliveryQuantity)));
					totalDeliverySalePrice = totalDeliverySalePrice.add(salePrice.multiply(new BigDecimal(deliveryQuantity)));
					totalDeliveryQuantity += deliveryQuantity;
					totalDeliveryKind++;
					break;
				}
			}
		}
		ecOrder.setDeliveryQuantity(totalDeliveryQuantity);
		ecOrder.setDeliveryKind(totalDeliveryKind);
		ecOrder.setDeliveryTime(erpOrder.getFhrq());
		ecOrder.setDeliveryListPrice(totalDeliveryListPrice);
		ecOrder.setDeliverySalePrice(totalDeliverySalePrice);
	}

	/**
	 * 24小时拦截未处理的订单发货ERP订单发货
	 * 
	 * @return
	 */
	private boolean processErpDelivery(Order ecOrder) throws Exception {
		if (ecOrder.getProcessStatus().equals(Code.ORDER_PROCESS_STATUS_DELIVERIED) || ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)) {
			LOG.info("订单" + ecOrder.getId() + "已发货");
			return true;
		}
		String ecOrderId = ecOrder.getId();
		ErpOrder erpOrder = getErpOrder(ecOrderId);
		List<ErpOrder> orderDeliveryInfo = findErpOrder(ecOrderId);
		if (CollectionUtils.isEmpty(orderDeliveryInfo)) {
			LOG.info(ecOrderId + " : waiting for deliveryinfo!");
			return false;
		}
		// 记录订单发货信息
		orderDeliveryInfo(ecOrder, erpOrder);
		DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(Long.valueOf(erpOrder.getDeliveryCompany()));
		saveOrdereDeliveryInfos(orderDeliveryInfo);
		LOG.info("进入发送短消息功能");
		orderService.delivery(ecOrder, deliveryCompany, erpOrder.getCode(), employeeService.get(Employee.SYSTEM));
		LOG.info("成功执行发送短消息功能");
		sendChannelOrde(ecOrder);
		return true;
	}

	private void orderDeliveryInfo(Order ecOrder, ErpOrder erpOrder) throws ErpOrderTransferException {
		String ecOrderId = erpOrder.getOrderId();
		if (StringUtils.isNullOrEmpty(erpOrder.getCode()) || StringUtils.isNullOrEmpty(erpOrder.getDeliveryCompany())) {
			throw new ErpOrderTransferException("erp order delivery is null!");
		}
		List<ErpOrderItem> erpOrderItems = getEcOrderItem(ecOrderId);
		if (erpOrderItems == null || erpOrderItems.isEmpty()) {
			throw new ErpOrderTransferException(ecOrderId + " : erp order has no order item!");
		}
		Set<OrderItem> items = ecOrder.getItemList();
		if (erpOrderItems.size() != items.size()) {
			throw new ErpOrderTransferException(ecOrderId + " : erp item size not equals ec size!");
		}
		int totalDeliveryQuantity = 0;
		int totalDeliveryKind = 0;
		BigDecimal totalDeliveryListPrice = BigDecimal.ZERO;
		BigDecimal totalDeliverySalePrice = BigDecimal.ZERO;

		for (OrderItem ecOrderItem : items) {
			Long merchantId = ecOrderItem.getProductSale().getProduct().getMerchId();
			for (ErpOrderItem erpOrderItem : erpOrderItems) {
				if (merchantId.equals(erpOrderItem.getCommodity())) {
					int deliveryQuantity = erpOrderItem.getQuantity();
					BigDecimal listPrice = ecOrderItem.getListPrice();
					BigDecimal salePrice = ecOrderItem.getSalePrice();
					ecOrderItem.setDeliveryQuantity(deliveryQuantity);
					totalDeliveryListPrice = totalDeliveryListPrice.add(listPrice.multiply(new BigDecimal(deliveryQuantity)));
					totalDeliverySalePrice = totalDeliverySalePrice.add(salePrice.multiply(new BigDecimal(deliveryQuantity)));
					totalDeliveryQuantity += deliveryQuantity;
					totalDeliveryKind++;
					break;
				}
			}
		}
		ecOrder.setDeliveryQuantity(totalDeliveryQuantity);
		ecOrder.setDeliveryKind(totalDeliveryKind);
		ecOrder.setDeliveryTime(erpOrder.getFhrq());
		ecOrder.setDeliveryListPrice(totalDeliveryListPrice);
		ecOrder.setDeliverySalePrice(totalDeliverySalePrice);
	}

	private List<ErpOrderItem> getEcOrderItem(String ecOrderId) {
		List<EcErpOrderItem> ecErpOrderItems = ecErpOrderItemService.findByOrder(ecOrderId);
		List<ErpOrderItem> erpOrderItems = new ArrayList<ErpOrderItem>();
		for (EcErpOrderItem ei : ecErpOrderItems) {
			ErpOrderItem erpOrderItem = new ErpOrderItem();
			erpOrderItem.setOrderId(Long.valueOf(ei.getOrder()));
			erpOrderItem.setCommodity(Long.valueOf(ei.getMerchid()));
			erpOrderItem.setQuantity(ei.getDeliveryQuantity());
			erpOrderItem.setOutOfStockQuantity(ei.getOutOfStockQuantity());
			erpOrderItems.add(erpOrderItem);
		}
		return erpOrderItems;
	}

	private ErpOrder getErpOrder(String id) throws ErpOrderTransferException {
		ErpOrder erpOrder = new ErpOrder();
		EcErpOrder ecErpOrder = ecErpOrderService.get(id, ErpOrder.STATUS_DELIVERY);
		if (ecErpOrder == null) {
			throw new ErpOrderTransferException(id + ": ecerporder is null!");
		}
		if (ecErpOrder.isDeal()) {
			throw new ErpOrderTransferException(id + ": ecerporder status error for delivery!");
		}
		erpOrder.setOrderId(ecErpOrder.getOrder());
		erpOrder.setCode(ecErpOrder.getOrderDelivery());
		erpOrder.setDc(ecErpOrder.getDc());
		erpOrder.setDdlxid(ecErpOrder.getDdlxid());
		erpOrder.setDeliveryCompany(ecErpOrder.getDeliveryCompany().toString());
		erpOrder.setFhrq(ecErpOrder.getDeliveryTime());
		erpOrder.setReturnTypeId(ecErpOrder.getGkthfsid());
		erpOrder.setState(ecErpOrder.getState());
		ecErpOrder.setStatus(EcErpOrder.AL_DEAL);
		ecErpOrderService.update(ecErpOrder);
		return erpOrder;
	}

	private List<ErpOrder> findErpOrder(String id) {
		List<EcErpOrderDelivery> deliveries = ecErpOrderDeliveryService.findByOrder(id);
		List<ErpOrder> erpOrders = new ArrayList<ErpOrder>();
		for (EcErpOrderDelivery ed : deliveries) {
			ErpOrder erpOrder = new ErpOrder();
			erpOrder.setOrderId(ed.getOrder());
			erpOrder.setState(ed.getState());
			erpOrder.setCode(ed.getOrderDelivery());
			erpOrder.setDeliveryCompany(ed.getDeliveryCompany().toString());
			erpOrder.setDdlxid(ed.getDdlxid());
			erpOrder.setFhrq(ed.getDeliveryTime());
			erpOrder.setDc(ed.getDc());
			erpOrders.add(erpOrder);
		}
		return erpOrders;
	}

	/**
	 * ERP订单发货
	 * 
	 * @param erpOrder
	 * @param ecOrder
	 * @return 返回true，表示发货完成，标记接口数据为S 返回false,表示发货条件不足，但无异常
	 * @throws Exception
	 */
	private boolean processErpDelivery(ErpOrder erpOrder, Order ecOrder) throws Exception {
		String ecOrderId = erpOrder.getOrderId();
		List<ErpOrder> orderDeliveryInfo = erpDao.fetchOrderDelivery(erpOrder.getOrderId());
		if (orderDeliveryInfo == null || orderDeliveryInfo.isEmpty()) {
			LOG.info(ecOrderId + " : waiting for deliveryinfo!");
			return false;
		}
		processOrderDeliveryInfo(erpOrder, ecOrder);
		DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(Long.valueOf(erpOrder.getDeliveryCompany()));
		saveOrdereDeliveryInfos(orderDeliveryInfo);
		LOG.info("进入发送短消息功能");
		orderService.delivery(ecOrder, deliveryCompany, erpOrder.getCode(), employeeService.get(Employee.SYSTEM));
		LOG.info("成功执行发送短消息功能");
		sendChannelOrde(ecOrder);
		updateErpStatus4Ecorder(ecOrder, new Code(Code.ERP_PROCESS_STATUS_DELIVERY_ALL));
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
		return true;
	}

	private void saveOrdereDeliveryInfos(List<ErpOrder> orderDeliveryInfo) {
		if (orderDeliveryInfo.size() > MagicNumber.ONE) {
			for (ErpOrder erpOrder : orderDeliveryInfo) {
				LOG.info(String.format("%s deliveryinfo save , code : %s ", erpOrder.getOrderId(), erpOrder.getCode()));
				OrderDeliverySplit orderDeliverySplit = new OrderDeliverySplit();
				orderDeliverySplit.setOrder(orderDao.get(erpOrder.getOrderId()));
				orderDeliverySplit.setCode(erpOrder.getCode());
				orderDeliverySplit.setCompany(deliveryCompanyDao.get(Long.parseLong(erpOrder.getDeliveryCompany())));
				orderDeliverySplit.setDeliveryTime(erpOrder.getFhrq());
				orderDeliverySplitDao.save(orderDeliverySplit);
			}
		}
	}

	/**
	 * 回传渠道订单
	 * 
	 * @param ecOrder
	 */
	private void sendChannelOrde(Order ecOrder) {
		if (ecOrder.getChannel() != null && ecOrder.getChannel().isUsingApi() && ecOrder.getOuterId() != null) {
			ecDao.sendChannelOrder(ecOrder);
		}
	}

	/**
	 * 回传退货单
	 * 
	 * @param returnOrder
	 */
	private void sendChannelReturnOrder(ReturnOrder returnOrder) {
		Order ecOrder = returnOrder.getOriginalOrder();
		if (ecOrder.getChannel() != null && ecOrder.getChannel().isUsingApi() && !StringUtils.isNullOrEmpty(ecOrder.getOuterId())) {
			ecDao.sendChannelReturnOrder(returnOrder);
		}
	}

	/**
	 * 处理ERP发起的退货信息
	 * 
	 * @param erpOrder
	 * @param ecOrder
	 * @throws Exception
	 */
	private void processsErpReject(ErpOrder erpOrder, Order ecOrder) throws Exception {
		String erpOrderState = erpOrder.getState();
		Code cancelCode = codeService.get(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
		orderService.cancel(ecOrder, cancelCode, employeeService.get(Employee.SYSTEM));
		updateErpStatus4Ecorder(ecOrder, new Code(erpOrderState.equals(ErpOrder.STATUS_CANCELLED_FOR_REJECT) ? Code.ERP_PROCESS_STATUS_REJECT : Code.ERP_PROCESS_STATUS_UNRECIVE));
		sendChannelOrde(ecOrder);
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
		LOG.info(String.format("%s rejection cancel!", ecOrder.getId()));
	}

	private void updateErpStatus4Ecorder(Order ecOrder, Code status) {
		orderService.updateErpStatus(ecOrder, status);
	}

	public List<OrderPayment> fetchReturnOrder(ErpOrder erpOrder) throws Exception {
		ErpReturnOrder erpReturnOrder = erpDao.getByErpReturnOrderId(erpOrder);
		if (erpReturnOrder == null) {
			throw new ErpOrderTransferException(String.format("%s erpReturnOrder not found!", erpOrder.getOrderId()));
		}
		Employee employee = employeeService.get(Employee.SYSTEM);
		ReturnOrder returnOrder = returnOrderService.get(Long.valueOf(erpReturnOrder.getReturnOrderId()));
		if (returnOrder == null) {
			throw new ErpOrderTransferException(String.format("%s returnOrder not found!", erpReturnOrder.getReturnOrderId()));
		}
		List<OrderPayment> payments = null;
		Long returnOrderSatusId = returnOrder.getStatus().getId();
		LOG.info(String.format(" %s : returnOrder status %s!", returnOrder.getId(), erpOrder.getState()));
		if (returnOrderSatusId.equals(Code.RETURN_ORDER_STATUS_RETURNING)) {
			if (erpOrder.getState().equals(ErpOrder.REJECT_GOODS_CONFIRM)) {
				List<ErpOrderItem> erpReturnOrderItem = erpDao.fetchOrderStateItem(erpReturnOrder.getErpReturnOrderId());
				if (erpReturnOrderItem == null || erpReturnOrderItem.isEmpty()) {
					throw new ErpOrderTransferException(erpReturnOrder.getErpReturnOrderId() + " : erpReturnOrderItem not found!");
				}
				Set<ReturnOrderItem> returnOrderItems = returnOrder.getItemList();
				if (returnOrderItems.size() < erpReturnOrderItem.size()) {
					throw new ErpOrderTransferException(String.format("%s : erpReturnOrderItem size > ec size!", returnOrder.getId()));
				}
				for (ReturnOrderItem returnOrderItem : returnOrderItems) {
					Long merchantId = returnOrderItem.getOrderItem().getProductSale().getProduct().getMerchId();
					for (ErpOrderItem erpOrderItem : erpReturnOrderItem) {
						if (merchantId.equals(erpOrderItem.getCommodity())) {
							returnOrderItem.setRealQuantity(erpOrderItem.getQuantity());
							break;
						}
					}
				}

				payments = returnOrderService.completeReturn(returnOrder, employee);
				this.updateReturnOrderDc(returnOrder, erpOrder);
				sendChannelReturnOrder(returnOrder);
				erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
				LOG.info(returnOrder.getId() + " : returnOrder processe success!");
			}
		} else if (returnOrderSatusId.equals(Code.RETURN_ORDER_STATUS_REFUNDED) || returnOrderSatusId.equals(Code.RETURN_ORDER_STATUS_CHANGED)) {
			if (erpOrder.getState().equals(ErpOrder.STATUS_RETURN_ONSHELF)) {
				financeService.updateReturnFinance(returnOrder, erpOrder.getFhrq());
				erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
				return null;
			}
		}
		return payments;
	}

	/**
	 * 设置目标的dc信息
	 * 
	 * @param returnOrder
	 * @param erpOrder
	 * @throws ErpOrderTransferException
	 */
	private void updateReturnOrderDc(ReturnOrder returnOrder, ErpOrder erpOrder) throws ErpOrderTransferException {
		ReturnOrderDc returnOrderDc = returnOrder.getReturnOrderDc();
		if (returnOrderDc == null) {
			String msg = "(%s) not found dc info,plase init dc into";
			throw new ErpOrderTransferException(String.format(msg, returnOrder.getId()));
		}
		DcRule rule = dcService.findByAvailableDc(erpOrder.getDc());
		returnOrderDc.setTargetRealDc(rule.getLocation());
	}

	@Override
	public void processReturnOrderReplace(List<OrderPayment> newPayments, ErpOrder erpOrder) throws Exception {
		ReturnOrder returnOrder = returnOrderService.get(Long.valueOf(erpOrder.getOrderId()));
		if (!returnOrder.getType().getId().equals(Code.RETURN_ORDER_TYPE_REPLACE)) {
			return;
		}
		if (newPayments == null || newPayments.isEmpty()) {
			LOG.info(returnOrder.getId() + " : replace returnOrder, but no payments!");
			return;
		}
		Employee operator = employeeService.get(Employee.SYSTEM);
		returnOrderService.createOrder(returnOrder, newPayments, operator);
	}

	@Override
	public void updateReturnOrderCancel(ReturnOrder returnOrder) {
		// TODO 更改退货单状态
	}

	@Override
	public List<String> fetchDistinctOrderDelivery() {
		return erpDao.fetchDistinctOrderDelivery();
	}

	@Override
	public void updateDeliveryInfoByOrderId(String orderId) throws Exception {
		Order order = orderService.get(orderId);
		if (order == null) {
			throw new ErpOrderTransferException(String.format("%s not found!", orderId));
		}
		Long statusId = order.getProcessStatus().getId();
		if (statusId.equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL) || // 中启取消和EC归档直接标记接口数据已被处理，EC不做更新
				statusId.equals(Code.ORDER_PROCESS_STATUS_ARCHIVE)) {
			erpDao.updateErpDeliverytatus(orderId, null);
			return;
		}
		List<ErpOrder> orderDeliveryInfo = erpDao.fetchOrderDelivery(orderId); // 获取ERP的运单信息
		Set<OrderDeliverySplit> deliverySplits = order.getDeliverySplits();
		LOG.info(String.format("%s deliverySplits is null ? %s ", orderId, deliverySplits.size()));
		if (CollectionUtils.isEmpty(deliverySplits)) {
			if (orderDeliveryInfo.size() <= MagicNumber.ONE) {
				for (ErpOrder erpOrder : orderDeliveryInfo) {
					validateErpOrderDelivery(erpOrder);
					updateEcOrderDeliveryInfoByErpOrder(erpOrder, order);
				}
			} else {
				saveOrdereDeliveryInfos(orderDeliveryInfo);
				updateEcOrderDeliveryInfoByErpOrder(orderDeliveryInfo.get(0), order);
			}
		} else {
			boolean has = false;
			HashSet<OrderDeliverySplit> splits = new HashSet<OrderDeliverySplit>();
			for (OrderDeliverySplit deliverySplit : deliverySplits) { // 移除不存在的运单号
				has = this.equalsErpDelivery(deliverySplit, orderDeliveryInfo);
				if (!has) {
					splits.add(deliverySplit);
				}
			}
			deliverySplits.removeAll(splits);

			for (ErpOrder erpOrder : orderDeliveryInfo) { // 添加新回传的运单号
				has = this.equalsEcDelivery(erpOrder, deliverySplits);
				if (!has) {
					OrderDeliverySplit split = this.createDeliverySplit(erpOrder, order);
					deliverySplits.add(split);
				}
			}

			if (deliverySplits.size() > MagicNumber.ZERO) {
				updateEcOrderDeliveryInfoByErpOrder(orderDeliveryInfo.get(0), order);
			}
			orderService.update(order, employeeService.get(Employee.SYSTEM));
		}
		erpDao.updateErpDeliverytatus(orderId, null);
	}

	/**
	 * 创建分包信息
	 * 
	 * @param erpOrder
	 * @param order
	 * @return
	 */
	private OrderDeliverySplit createDeliverySplit(ErpOrder erpOrder, Order order) {
		OrderDeliverySplit split = new OrderDeliverySplit();
		split.setCode(erpOrder.getCode());
		split.setCompany(deliveryService.getDeliveryCompany(Long.valueOf(erpOrder.getDeliveryCompany())));
		split.setDeliveryTime(erpOrder.getFhrq());
		split.setOrder(order);
		return split;
	};

	/**
	 * updateDeliveryInfoByOrderId(String orderId),私有辅助方法,</br>
	 * 
	 * @param erpOrder
	 * @param deliverySplits
	 * @return boolean
	 */
	private boolean equalsEcDelivery(ErpOrder erpOrder, Set<OrderDeliverySplit> deliverySplits) {
		String erpCompany = erpOrder.getDeliveryCompany();
		String erpCode = erpOrder.getCode();
		for (OrderDeliverySplit deliverySplit : deliverySplits) {
			String company = deliverySplit.getCompany().getId().toString();
			String code = deliverySplit.getCode();
			if (company.equals(erpCompany) && code.equals(erpCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * updateDeliveryInfoByOrderId(String orderId),私有辅助方法,</br>
	 * 在ec的分包信息中有erp不存在的运单信息.
	 * 
	 * @param deliverySplit
	 *            ec分包信息
	 * @param orderDeliveryInfo
	 *            erp运单信息
	 * @return boolean
	 * @throws ErpOrderTransferException
	 */
	private boolean equalsErpDelivery(OrderDeliverySplit deliverySplit, List<ErpOrder> orderDeliveryInfo) throws ErpOrderTransferException {
		String company = deliverySplit.getCompany().getId().toString();
		String code = deliverySplit.getCode();
		for (ErpOrder erpOrder : orderDeliveryInfo) {
			validateErpOrderDelivery(erpOrder);
			String erpCompany = erpOrder.getDeliveryCompany();
			String erpCode = erpOrder.getCode();
			if (erpCompany.equals(company) && erpCode.equals(code)) {
				return true;
			}
		}
		return false;
	}

	private void validateErpOrderDelivery(ErpOrder erpOrder) throws ErpOrderTransferException {
		if (StringUtils.isNullOrEmpty(erpOrder.getCode())) {
			throw new ErpOrderTransferException(String.format("%s code is null!", erpOrder.getOrderId()));
		} else if (StringUtils.isNullOrEmpty(erpOrder.getDeliveryCompany())) {
			throw new ErpOrderTransferException(String.format("%s delivery company is null!", erpOrder.getOrderId()));
		} else if (erpOrder.getFhrq() == null) {
			throw new ErpOrderTransferException(String.format("%s delivery time is null!", erpOrder.getOrderId()));
		}
	}

	/**
	 * 更新原运单运单号
	 * 
	 * @param erpOrder
	 * @param order
	 * @throws OrderStatusException
	 */
	private void updateEcOrderDeliveryInfoByErpOrder(ErpOrder erpOrder, Order order) throws OrderStatusException {
		LOG.info(String.format("%s update order delivery,code : %s", order.getId(), erpOrder.getCode()));
		DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(Long.valueOf(erpOrder.getDeliveryCompany()));
		orderService.updateDeliveryCode(order, deliveryCompany, erpOrder.getCode());
		erpDao.updateErpProcessStatus(erpOrder.getOrderId(), erpOrder.getState());
	}

	public Order isNeedUpdateOrderStatus(ErpOrder erpOrder) throws ErpOrderTransferException {
		String ecOrderId = erpOrder.getOrderId();
		Order ecOrder = orderService.get(erpOrder.getOrderId());
		if (ecOrder == null) {
			throw new ErpOrderTransferException(ecOrderId + ": not found!");
		}
		if (!ecOrder.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)) {
			throw new ErpOrderTransferException(ecOrder.getId() + "order transfer status error!");
		}
		return ecOrder;
	}

	/**
	 * 退货单
	 * 
	 * @param returnOrder
	 */
	private void sendReturnOrder(ReturnOrder returnOrder) {
		Order order = returnOrder.getOriginalOrder();
		Object[] params = new Object[] { // SMTHHDDID, KHDDID, KHDDH, KHHHDDID,
				// KHHHDDH
				returnOrder.getId(), order.getId(), order.getId(), returnOrder.getId(), returnOrder.getId(), ErpParseUtils.parseReturnGoodsMeans(returnOrder),
				returnOrder.getDesireTotalQuantity(), returnOrder.getDesireTotalListPrice(), returnOrder.getDesireTotalSalePrice(),
				"1".equals(ErpParseUtils.parsePaymentStatus(order)) };
		erpDao.sendReturnOrder(params);
	}

	/**
	 * 生成退换货订单
	 * 
	 * @param returnOrder
	 */
	private void sendChargeOffOrder(ReturnOrder returnOrder) {
		Order order = returnOrder.getOriginalOrder();
		OrderConsignee orderConsignee = order.getConsignee();
		BigDecimal desireTotalSalePrice = returnOrder.getDesireTotalSalePrice();
		Object[] params = new Object[] { returnOrder.getId(), returnOrder.getId(), ErpParseUtils.parseDeliveryType(order), order.getCustomer().getId().toString(),
				orderConsignee.getConsignee(), ErpParseUtils.parseArea(orderConsignee), orderConsignee.getZipCode(), getInvoiceAddress(orderConsignee),
				returnOrder.getDesireTotalQuantity(), returnOrder.getDesireTotalListPrice(), desireTotalSalePrice, returnOrder.getDesireKindQuantity(), "0",
				orderConsignee.getEffectWay(), "N", ErpParseUtils.parseChannelType(order), order.getChannel().getId(), ErpParseUtils.parseArea(orderConsignee),
				desireTotalSalePrice, orderConsignee.getInvoiceTitle(), ErpParseUtils.parseReturnGoodsMeans(returnOrder), ErpParseUtils.parseDeliveryTimeDesc(orderConsignee),
				order.getDeliveryFee(), getTotalSaveMoney(order), ErpParseUtils.parsePaymentStatus(order), ErpParseUtils.parsePayDeliveryFee(order),
				order.getDistributionCenter().getDcOriginal().getName() };
		erpDao.sendReturnOrder4Main(params);
	}

	/**
	 * 退货单项
	 * 
	 * @param returnOrder
	 */
	private void sendReturnOrderItem(ReturnOrder returnOrder) {
		Order order = returnOrder.getOriginalOrder();
		OrderConsignee orderConsignee = order.getConsignee();
		Set<ReturnOrderItem> returnOrderItems = returnOrder.getItemList();
		Object[] params1 = null;
		Object[] params2 = null;
		for (ReturnOrderItem returnItem : returnOrderItems) {
			ProductSale productSale = returnItem.getOrderItem().getProductSale();
			Product product = productSale.getProduct();
			params1 = new Object[] { returnItem.getId(), returnItem.getOrderItem().getId(), order.getId(), order.getId(), returnOrder.getId(), returnOrder.getId(),
					returnItem.getId(), ErpParseUtils.parseReturnGoodsMeans(returnOrder), "13", returnItem.getAppQuantity(), returnItem.getDesireTotalListPrice(),
					returnItem.getDesireTotalSalePrice(), product.getMerchId() };
			erpDao.sendReturnOrderItem(params1);

			params2 = new Object[] { returnItem.getId(), returnOrder.getId(), returnOrder.getId(), ErpParseUtils.parseDeliveryType(order), product.getMerchId(),
					ErpParseUtils.parseProductSort(product), returnItem.getOrderItem().getListPrice(), returnItem.getOrderItem().getSalePrice(),
					getOrderItemDiscount(returnItem.getOrderItem()), ErpParseUtils.parseArea(orderConsignee), orderConsignee.getZipCode(), getInvoiceAddress(orderConsignee),
					returnItem.getAppQuantity(), returnItem.getDesireTotalListPrice(), returnItem.getDesireTotalSalePrice(), ErpParseUtils.parsePaymentStatus(order), "false", // EC系统无赠品
					ErpParseUtils.parseErpShopId(order.getShop()), ErpParseUtils.parseReturnGoodsMeans(returnOrder) };
			erpDao.sendReturnOrderItem4Main(params2);

		}
	}

	private void checkReturnOrder(ReturnOrder returnOrder) throws ErpOrderTransferException {
		if (!returnOrder.isNeedtransfers() || returnOrder.isTransferred()) {
			throw new ErpOrderTransferException("returnOrder status error!");
		}
	}

	/**
	 * 传输主订单
	 * 
	 * @param order
	 */
	private void sendOrder(Order order) {
		OrderConsignee consignee = order.getConsignee();

		BigDecimal orderSalePrice = BigDecimal.ZERO;
		BigDecimal orderSaveMoney = BigDecimal.ZERO;
		if (order.getSaveMoney().compareTo(BigDecimal.ZERO) > 0) {
			orderSalePrice = order.getTotalBalancePrice();
			BigDecimal minuend = order.getSalePrice().subtract(order.getSaveMoney());
			if (orderSalePrice.compareTo(minuend) > 0) {
				orderSaveMoney = orderSalePrice.subtract(minuend);
			}
		} else {
			orderSalePrice = order.getSalePrice();
			orderSaveMoney = order.getSaveMoney();
		}
		BigDecimal realTotalSalePrice = BigDecimal.ZERO;
		for (OrderItem orderItem : order.getItemList()) {
			realTotalSalePrice = realTotalSalePrice.add(orderItem.getTotalSalePrice());
		}
		if (realTotalSalePrice.compareTo(order.getSalePrice()) > 0) {
			orderSaveMoney = orderSaveMoney.add(realTotalSalePrice.subtract(order.getSalePrice()));
		}

		/**
		 * 设置插入到表jkxx_ddgl_khdd的记录的字段值 对于渠道为苏宁以及易迅的订单进行特殊处理
		 */

		// String orderPassage;
		// if (Channel.YIXUN_WINSHARE.equals(order.getChannel().getId())){
		// orderPassage = order.getChannelPackageNum();
		// }
		// else{
		// orderPassage = order.getConsignee().getId();
		// }

		Object[] params = new Object[] { order.getId(), order.getId(),
				ErpParseUtils.parseDeliveryType(order),
				order.getCustomer().getId().toString(),
				consignee.getConsignee(),
				ErpParseUtils.parseArea(consignee),
				consignee.getZipCode(),
				getInvoiceAddress(consignee),
				order.getPurchaseQuantity(),
				order.getListPrice(),
				orderSalePrice,
				order.getPurchaseKind(),
				ErpParseUtils.parseDeliveryTimeDesc(consignee),
				consignee.isNeedInvoice() ? "true" : "false",
				consignee.isNeedInvoice() ? consignee.getInvoiceTitle() : "",
				consignee.isNeedInvoice() ? consignee.getInvoiceTitle() : "",
				order.getInvoiceMoney(),
				order.getRequidPayMoney(),
				consignee.getEffectWay(),
				"N",
				ErpParseUtils.parsePaymentStatus(order),
				ErpParseUtils.parseChannelType(order),
				order.getChannel().getId(),
				ErpParseUtils.parseArea(consignee),
				ErpParseUtils.parsePayDeliveryFee(order),
				orderSaveMoney,
				order.getDeliveryFee(),
				order.getAdvanceMoney(),
				orderSalePrice,
				consignee.getRemark(),
				"1",
				ErpParseUtils.parseOutOfStockOption(consignee),
				// Channel.SUNING_EDI.equals(order.getChannel().getId()) ? order
				// .getConsignee().getRemark() : null,
				// Channel.SUNING_EDI.equals(order.getChannel().getId()) ? "SN"
				// : null,

				/**
				 * 如果为苏宁渠道，则调用getConsignee()方法
				 * 如果为易迅，则调用getChannelPackageNum()方法取订单号
				 */
				Channel.SUNING_EDI.equals(order.getChannel().getId()) ? order.getConsignee().getRemark() : Channel.YIXUN_WINSHARE.equals(order.getChannel().getId()) ? order
						.getChannelPackageNum() : Channel.CHANNEL_ID_DANGDANG_JIT.equals(order.getChannel().getId()) ? order.getOuterId() : null,
				//
				order.isCODOrder() ? "COD" : Channel.SUNING_EDI.equals(order.getChannel().getId()) ? "SN" : null,

				// orderPassage;
				order.getDistributionCenter().getDcOriginal().getName() };
		erpDao.sendOrder(params);
		if (order.isCODOrder()) {
			sendDangDangCod(order);
		}
	}

	/**
	 * 渠道COD电子面单
	 */
	private void sendDangDangCod(Order order) {
		OrderCourierReceipt orderCourierReceipt = orderInterfaceService.parseOrderCodJson(order);
		Object[] params = new Object[] {
				order.getId(),
				orderCourierReceipt.getOuterOrderId(),
				orderCourierReceipt.getSurface(order.getChannel().getId()),// 面单类型
				orderCourierReceipt.getTargetWarehouse(), orderCourierReceipt.getSendGoodsWarehouse(), orderCourierReceipt.getShopWarehouse(),
				orderCourierReceipt.getExpressCompany(), orderCourierReceipt.getSendGoodsTime(),
				Code.DC_8A17.equals(order.getDistributionCenter().getDcOriginal().getId()) ? "四川省-成都市" : "北京市", orderCourierReceipt.getTotalBarginPrice(),
				orderCourierReceipt.getShopId(), DateUtils.LONG_DATE_FORMAT.format(order.getCreateTime()), orderCourierReceipt.getRejectWarehouse(),
				orderCourierReceipt.getConsignerAddr(), orderCourierReceipt.getWaybillNumber(), orderCourierReceipt.getRemark() };
		erpDao.sendDangDangCod(params);
	}

	/**
	 * 传输订单项
	 * 
	 * @param order
	 */
	private void sendOrderItem(Order order) {
		order = orderService.get(order.getId());
		OrderConsignee consignee = order.getConsignee();
		Object[] params = null;
		for (OrderItem orderItem : order.getItemList()) {
			ProductSale productSale = orderItem.getProductSale();
			Product product = productSale.getProduct();
			params = new Object[] { orderItem.getId(), order.getId(), order.getId(), ErpParseUtils.parseDeliveryType(order), product.getMerchId(),
					ErpParseUtils.parseProductSort(product), orderItem.getListPrice(), orderItem.getBalancePrice(), getOrderItemDiscount(orderItem),
					ErpParseUtils.parseArea(consignee), consignee.getZipCode(), getInvoiceAddress(consignee), orderItem.getPurchaseQuantity(), orderItem.getTotalListPrice(),
					orderItem.getTotalBalancePrice(), ErpParseUtils.parsePaymentStatus(order), "false", ErpParseUtils.parseErpShopId(orderItem.getShop()), "1" };
			erpDao.sendOrderItems(params);
		}
	}

	/**
	 * 传输订单发票
	 * 
	 * @param order
	 * @throws ErpOrderTransferException
	 */
	private void transferUserInvoiceByOrder(Order order) throws ErpOrderTransferException {
		Set<OrderInvoice> invoices = order.getInvoiceList();
		if (invoices == null || invoices.isEmpty()) {
			LOG.warn(order.getId() + " 需要传输发票但无发票信息!");
			return;
		}
		OrderInvoice invoice = invoices.iterator().next();
		Object[] params = new Object[] { invoice.getId(), order.getId(), 0, invoice.getTitle(), invoice.getContent(), invoice.getMoney(), order.getPurchaseQuantity(),
				BigDecimalUtils.capitalize(invoice.getMoney()), invoice.getMoney(), 0 };
		erpDao.sendUserInovice(params);
	}

	/**
	 * 检查订单是否符合传输的条件
	 * 
	 * @param order
	 * @throws ErpOrderTransferException
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	private void checkOrder(Order order) throws ErpOrderTransferException {
		if (!order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)

		&& !order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NONE)) {
			throw new ErpOrderTransferException(order.getId() + ": order status error!");
		}
		if (order.getConsignee() == null) {
			throw new ErpOrderTransferException(order.getId() + ": order consignee null!");
		}
		if (order.getChannel().getId().equals(Channel.YIXUN_WINSHARE) && StringUtils.isNullOrEmpty(order.getChannelPackageNum())) {
			throw new ErpOrderTransferException(order.getId() + ": yixun order has no channelPackageNum!");
		}
		/*
		 * if(order.isDangDangCOD() &&
		 * StringUtils.isNullOrEmpty(OrderExtend.getExtendValueByKey(order,
		 * OrderExtend.DANGDANG_COD_ELEC_BILL))) { throw new
		 * ErpOrderTransferException(order.getId() +
		 * ": dangdang COD order has no dianzimiandan!"); }
		 */
		/** 判断是否COD订单并且面单信息不为空 */
		if (order.isCODOrder() && StringUtils.isNullOrEmpty(OrderExtend.getExtendValueByKey(order))) {
			throw new ErpOrderTransferException(order.getId() + ":COD order has no dianzimiandan!");
		}

		for (OrderItem orderItem : order.getItemList()) {
			ProductSale sale = orderItem.getProductSale();
			Product product = sale.getProduct();
			if (product.getMerchId() == 0) {
				throw new ErpOrderTransferException(order.getId() + ": " + product.getId() + " merchantId is not exist!");
			}
			if (sale.getOuterId() == null) {
				throw new ErpOrderTransferException(order.getId() + ": " + sale.getId() + " owncode is not exist!");
			}
		}
	}

	private String getInvoiceAddress(OrderInvoice orderInvoice) {
		return ErpParseUtils.parseAddress(orderInvoice.getCountry(), orderInvoice.getProvince(), orderInvoice.getCity(), orderInvoice.getDistrict(), orderInvoice.getTown(),
				orderInvoice.getAddress());
	}

	/**
	 * @param consignee
	 * @return
	 */
	private String getInvoiceAddress(OrderConsignee consignee) {
		return ErpParseUtils.parseAddress(consignee.getCountry(), consignee.getProvince(), consignee.getCity(), consignee.getDistrict(), consignee.getTown(),
				consignee.getAddress());
	}

	/**
	 * 得到优惠金额+礼券+礼品卡的金额
	 * 
	 * @return
	 */
	private BigDecimal getTotalSaveMoney(Order order) {
		BigDecimal totalSave = BigDecimal.ZERO;
		if (order.getSaveMoney() != null) {
			totalSave = totalSave.add(order.getSaveMoney());
		}
		if (order.getPaymentList() != null && !order.getPaymentList().isEmpty()) {
			Set<OrderPayment> orderPayments = order.getPaymentList();
			for (OrderPayment orderPayment : orderPayments) {
				if (orderPayment.isPay() && (orderPayment.getPayment().getId().equals(Payment.COUPON) || orderPayment.getPayment().getId().equals(Payment.GIFT_CARD))) {
					totalSave.add(orderPayment.getPayMoney());
				}
			}
		}
		return totalSave;
	}

	/**
	 * 计算订单项的折扣
	 * 
	 * @param orderItem
	 * @return
	 */
	private BigDecimal getOrderItemDiscount(OrderItem orderItem) {
		BigDecimal salePrice = orderItem.getBalancePrice();
		BigDecimal listPrice = orderItem.getListPrice();
		BigDecimal discount = salePrice.divide(listPrice, 4, BigDecimal.ROUND_HALF_UP);
		discount = discount.multiply(new BigDecimal(100)).setScale(2);
		return discount;
	}

	@Override
	public void checkOrderReturnBack() throws Exception {
		List<String> list = ecDao.findReturnDelay();
		if (list != null && !list.isEmpty()) {
			int num = 0;
			for (String oid : list) {
				if (erpDao.checkDelivery(oid)) {
					num++;
					LOG.info(oid);
				}
			}
			ecDao.saveOrderReturnMonitorResult(num);
		}
	}

	@Override
	public List<ErpOrderInvoice> fetchOrderInvoice() {
		return this.erpDao.fetchOrderInvoice();
	}

	@Override
	public void updateOrderInvoiceStatus(String id) {
		this.erpDao.updateOrderInvoiceStatus(id);
	}

	@Override
	public void updateOrderInvoiceDelivery(String deliveryCode, Integer deliveryCompany, Date deliveryTime, String id) {
		this.ecDao.updateOrderInvoiceDelivery(deliveryCode, deliveryCompany, deliveryTime, id);
	}

	@Override
	public void processErpArea() throws Exception {
		LOG.info("区域同步开始");
		int pageNum = 1;
		List<ErpArea> areas = this.erpDao.fetchArea(pageNum++);
		while (null != areas && !areas.isEmpty()) {
			for (ErpArea ea : areas) {
				if (StringUtils.isNullOrEmpty(ea.getId()) || StringUtils.isNullOrEmpty(ea.getType()) || StringUtils.isNullOrEmpty(ea.getZt())) {
					LOG.warn("数据非法：" + ea.getLogMsg() + " 不能为空");
				} else {
					this.ecDao.saveArea(ea);
					this.erpDao.updateAreaStatus(ea.getId(), ea.getType(), ea.getZt());
				}
			}
			areas = this.erpDao.fetchArea(pageNum++);
		}
		this.erpDao.deleteArea();
		this.ecDao.executeProcedure("up_area");
		LOG.info("区域同步结束");
	}

	@Override
	public void processErpDeliveryArea() throws Exception {
		LOG.info("区域配送公司关系同步开始");
		int pageNum = 1;
		List<ErpDeliveryArea> deliveryAreas = this.erpDao.fetchDeliveryArea(pageNum++);
		while (null != deliveryAreas && !deliveryAreas.isEmpty()) {
			for (ErpDeliveryArea eda : deliveryAreas) {
				if (StringUtils.isNullOrEmpty(eda.getPqid()) || StringUtils.isNullOrEmpty(eda.getCysid()) || StringUtils.isNullOrEmpty(eda.getType())
						|| StringUtils.isNullOrEmpty(eda.getMark())) {
					LOG.warn("数据非法：" + eda.getLogMsg() + " 不能为空");
				} else {
					this.ecDao.saveDeliveryArea(eda);
					this.erpDao.updateDeliveryAreaStatus(eda.getPqid(), eda.getCysid(), eda.getType(), eda.getMark());
				}
			}
			deliveryAreas = this.erpDao.fetchDeliveryArea(pageNum++);
		}
		this.erpDao.deleteDeliveryArea();
		this.ecDao.executeProcedure("up_delivery_area");
		LOG.info("区域配送公司关系同步结束");
	}

	@Override
	public void processErpDeliveryCompany() throws Exception {
		LOG.info("配送公司同步开始");
		int pageNum = 1;
		List<ErpDeliveryCompany> deliveryCompanys = this.erpDao.fetchDeliveryCompany(pageNum++);
		while (null != deliveryCompanys && !deliveryCompanys.isEmpty()) {
			for (ErpDeliveryCompany edc : deliveryCompanys) {
				if (StringUtils.isNullOrEmpty(edc.getId()) || StringUtils.isNullOrEmpty(edc.getZt()) || StringUtils.isNullOrEmpty(edc.getType())) {
					LOG.warn("数据非法：" + edc.getLogMsg() + " 不能为空");
				} else {
					this.ecDao.saveDeliveryCompany(edc);
					this.erpDao.updateDeliveryCompanyStatus(edc.getId(), edc.getType(), edc.getZt());
				}
			}
			deliveryCompanys = this.erpDao.fetchDeliveryCompany(pageNum++);
		}
		this.erpDao.deleteDeliveryCompany();
		this.ecDao.executeProcedure("up_deliverycompany");
		LOG.info("配送公司同步结束");
	}

	@Override
	public void blockFiledprocessErpDelivery(String orderId) throws Exception {
		Order ecOrder = orderService.get(orderId);
		if (processErpDelivery(ecOrder)) {
			ecDao.updateBlockOrder(ecOrder.getId(), EcDao.BLOCK_DELIVERY);
		}
	}

}
