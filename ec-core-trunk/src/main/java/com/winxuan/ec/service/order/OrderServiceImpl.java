/*
 * @(#)OrderServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.dao.OrderDeliverySplitDao;
import com.winxuan.ec.dao.OrderItemComplexLogDao;
import com.winxuan.ec.dao.OrderReceiveDao;
import com.winxuan.ec.dao.OrderTrackDao;
import com.winxuan.ec.dao.OrderUpdateLogDao;
import com.winxuan.ec.exception.BatchDeliveryException;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderCancelException;
import com.winxuan.ec.exception.OrderCloneException;
import com.winxuan.ec.exception.OrderDcMatchException;
import com.winxuan.ec.exception.OrderDeliveryException;
import com.winxuan.ec.exception.OrderException;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderPresellException;
import com.winxuan.ec.exception.OrderShipperInfoException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.exception.RefundExpiredException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.exception.UnsupportBankException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderCloneLog;
import com.winxuan.ec.model.order.OrderCollection;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderDistributionCenter;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderInit;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderItemComplexLog;
import com.winxuan.ec.model.order.OrderPackage;
import com.winxuan.ec.model.order.OrderPackges;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderPromotion;
import com.winxuan.ec.model.order.OrderReceive;
import com.winxuan.ec.model.order.OrderTrack;
import com.winxuan.ec.model.order.OrderUpdateLog;
import com.winxuan.ec.model.order.ParentOrder;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.product.DeliveryProductSale;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.customer.points.CustomerPointsRule;
import com.winxuan.ec.service.customer.points.CustomerPointsRuleFactory;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.finance.FinanceService;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.erp.adapter.ErpService;
import com.winxuan.erp.adapter.WmsService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;
import com.winxuan.services.pss.service.StockService;
import com.winxuan.services.support.exception.RemoteBusinessException;

/**
 * description
 * 
 * @author chenlong
 * @version 1.0,2011-7-19
 */
@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService, {
	private static final long serialVersionUID = -2736803455285593559L;
	/**
	 * 无手续费比例
	 */
	private static final BigDecimal REFUNDFREE_ALL = new BigDecimal(1);
	Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Value("${order.isUpdateActualQuantity}")
	boolean isUpdateActualQuantity = false;

	/**
	 * 是否拆单
	 */
	boolean isSplit = false;

	/**
	 * 是否需要集货
	 */
	@Value("${order.isCollection}")
	boolean isCollection = false;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PromotionService promotionService;

	private ErpService erpService;

	private WmsService wmsService;

	@InjectDao
	private OrderDao orderDao;

	@InjectDao
	private OrderDeliverySplitDao orderDeliverySplitDao;

	@InjectDao
	private OrderReceiveDao orderReceiveDao;

	@InjectDao
	private OrderTrackDao orderTrackDao;

	@InjectDao
	private OrderUpdateLogDao orderUpdateLogDao;

	@InjectDao
	private OrderItemComplexLogDao orderItemComplexLogDao;

	@Autowired
	private OrderLogisticsInfoFetcher orderLogisticsInfoFetcher;

	@Autowired
	private PresentService presentService;

	@Autowired
	private PresentCardService presentCardService;

	@Autowired
	private ParentOrderService parentOrderService;

	@Autowired
	private MailService mailService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CustomerPointsRuleFactory customerPointsRuleFactory;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private OrderMessageService orderMessageService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private FinanceService financeService;

	private OrderStatusService orderStatusService;

	@Autowired
	private RefundService refundService;

	@Autowired
	private OrderDcService orderDcService;

	@Autowired
	private ProductSaleStockService productSaleStockService;

	@Autowired
	private OrderInitService orderInitService;

	@Autowired
	private DcService dcService;

	@Autowired
	private CollectionItemService collectionItemService;

	@Autowired
	private ReturnOrderService returnOrderService;

	@Autowired
	private OrderShipperInfoService orderShipperInfoService;

	@Autowired
	private StockService remoteStockService;

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public void setErpService(ErpService erpService) {
		this.erpService = erpService;
	}

	public void setWmsService(WmsService wmsService) {
		this.wmsService = wmsService;
	}

	/**
	 * 下单
	 * 
	 * @param order
	 *            订单
	 */
	@Override
	public void create(Order order) throws OrderInitException, OrderStockException, CustomerAccountException,
			PresentException, PresentCardException {
		try {
			initProductSaleStockVo(order);
		} catch (RemoteBusinessException e) {
			throw new RuntimeException("远程调用初始化库存失败.", e);
		}
		updateLockStockSales(order);
		setOrderCollection(order);
		// 记录订单中存在的套装书
		List<OrderItem> complexOrderItems = findComplexProductsByOrder(order);
		initDefaultTown(order);
		initOrder(order);
		initDc(order);
		if (!order.isChannelOrder()) {
			promotionEffect(order);
			checkOrderStock(order);
		}
		setupBalancePrice(order);
		setupOrderPayment(order, order.getCreator());
		if (!order.isChannelOrder()) {
			CustomerPointsRule customerPointsRule = customerPointsRuleFactory.createOrderCustomerPointsRule(
					order.getCustomer(), order);
			customerPointsRule.generatePoints(order);
			resetOrderMaxPayDate(order);
			checkOrderStatus(order, order.getCreator());
		}
		if (!order.isSplit()) {
			ParentOrder parentOrder = new ParentOrder();
			Set<Order> orderList = new HashSet<Order>();
			orderList.add(order);
			BeanUtils.copyProperties(order, parentOrder);
			OrderInit orderInit = new OrderInit(order);
			order.setOrderInit(orderInit);
			OrderCollection oc = new OrderCollection(order, false);
			oc.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_CONFIGURE)); // 系统配置
			order.setOrderCollection(oc);
			parentOrder.setOrderList(orderList);
			parentOrder.setSplit(order.isSplit());
			parentOrderService.save(parentOrder);
			order.setParentOrder(parentOrder);
			saveOrderComplexProductsLog(order, complexOrderItems);// 保存订单拆分前套装书信息
			if (!order.isChannelOrder()) {
				if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
					promotionEffect(order);
				}
				customerService.addToBoughtList(order); // 记录用户已购商品
				sendMail(order);
			}
			this.broadcastOrderStatus(order);
			// orderStockService.broadcastInitOrderStock(order.getId());
			// //MQ丢消息进行订单初始化
			initOrderStock(order);
			log.info("订单号：" + order.getId() + "不拆单");
		} else {
			ParentOrder parentOrder = splitOrder(order);
			parentOrderService.save(parentOrder);
			for (Order subOrder : parentOrder.getOrderList()) {
				saveOrderComplexProductsLog(subOrder, complexOrderItems);
				this.broadcastOrderStatus(subOrder);
				initOrderStock(subOrder);
			}
			order.setParentOrder(parentOrder);
			log.info("父订单号：" + parentOrder.getId() + "拆单");
			// 渠道订单进入EC后系统判断会被拆单发货的时候，就生成触发短信信息（仅针对天猫渠道）
			orderMessageService.splitOrderMessage(parentOrder);
		}
	}

	/**
	 * 下单增加锁定商品销量
	 * 
	 * @param order
	 */
	private void updateLockStockSales(Order order) {
		Set<OrderItem> items = order.getItemList();
		for (OrderItem item : items) {
			if (Channel.FRANCHISEE.compareTo(order.getChannel().getId()) == 0) {
				productSaleStockService.updateLockSales(order.getChannel(), order.getCustomer().getId(),
						item.getProductSale(), item.getPurchaseQuantity());
			} else {
				productSaleStockService.updateLockSales(order.getChannel(), item.getProductSale(),
						item.getPurchaseQuantity());
			}
		}
	}

	/**
	 * 拆分订单
	 * 
	 * @param order
	 * @return
	 * @throws OrderStockException
	 * @throws OrderDcMatchException
	 */
	private ParentOrder splitOrder(Order order) throws OrderDcMatchException, OrderStockException {
		ParentOrder parentOrder = new ParentOrder();
		BeanUtils.copyProperties(order, parentOrder);
		parentOrder.setSplit(order.isSplit());
		Set<Order> orderList = generateSubOrder(order, parentOrder);
		if (!CollectionUtils.isEmpty(orderList) && orderList.size() > 1) {
			parentOrder.setSplit(true);
		} else {
			parentOrder.setSplit(false);
		}
		parentOrder.setOrderList(orderList);
		return parentOrder;
	}

	/**
	 * 根据订单生成子订单
	 * 
	 * @param order
	 * @param parentOrder
	 * @return
	 * @throws OrderStockException
	 * @throws OrderDcMatchException
	 */
	private Set<Order> generateSubOrder(Order order, ParentOrder parentOrder) throws OrderDcMatchException,
			OrderStockException {
		Map<ProductSale, Integer> map = order.getProductsaleNumMap();
		Set<Order> orderList = new TreeSet<Order>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				BigDecimal salePrice1 = o1.getSalePrice();
				BigDecimal salePrice2 = o2.getSalePrice();
				if (salePrice1.compareTo(salePrice2) >= 0) {
					return 1;
				}
				return -1;
			}
		});
		List<DcPriority> dps = getDps(order);
		int size = dps.size();
		int count = 0;
		for (DcPriority dp : dps) {
			count++;
			boolean needColl = true;
			Order subOrder = new Order();
			BigDecimal listPrice = BigDecimal.ZERO;
			BigDecimal salePrice = BigDecimal.ZERO;
			int purchaseQuantity = 0;
			for (OrderItem item : order.getItemList()) {
				ProductSale productSale = item.getProductSale();
				// int subStock =
				// productSaleStockService.getAvalibleQuantity(productSale, new
				// Code(dp.getLocation()));
				int subStock = item.getProductSaleStockVoAvailableQuantity(dp.getLocation());
				int needStock = map.get(productSale) != null ? map.get(productSale) : MagicNumber.ZERO;
				if (needStock > MagicNumber.ZERO) {
					int canCollStock = needStock - subStock >= MagicNumber.ZERO ? subStock : needStock;
					if (count == size) {
						canCollStock = map.get(productSale) != null ? map.get(productSale) : MagicNumber.ZERO;
						if (canCollStock == MagicNumber.ZERO) {
							continue;
						}
					}
					if (canCollStock != 0) {
						needStock = needStock - canCollStock;
						map.put(productSale, needStock);
						OrderItem subOrderItem = new OrderItem();
						subOrderItem.setShop(item.getShop());
						subOrderItem.setBalancePrice(item.getBalancePrice());
						subOrderItem.setListPrice(item.getListPrice());
						subOrderItem.setSalePrice(item.getSalePrice());
						subOrderItem.setPurchaseQuantity(canCollStock);
						purchaseQuantity += canCollStock;
						subOrderItem.setProductSale(productSale);
						subOrderItem.setOrder(subOrder);
						listPrice = listPrice.add(item.getListPrice().multiply(new BigDecimal(canCollStock)));
						salePrice = salePrice.add(item.getSalePrice().multiply(new BigDecimal(canCollStock)));
						subOrder.addItem(subOrderItem);
					}
					if (needStock > 0) {
						needColl = false;
					}
				}
			}
			copyOrder(order, subOrder);
			subOrder.setPurchaseQuantity(purchaseQuantity);
			subOrder.setPurchaseKind(subOrder.getItemList().size());
			subOrder.setListPrice(listPrice);
			subOrder.setSalePrice(salePrice);
			subOrder.setParentOrder(parentOrder);
			OrderDistributionCenter dc = subOrder.getDistributionCenter();
			dc.setDcDest(new Code(dp.getLocation()));
			orderList.add(subOrder);
			if (needColl) {
				break;
			}
		}
		setOrderbalance(order, orderList);
		setOrderPayment(order, orderList);
		return orderList;
	}

	/**
	 * 分摊优惠金额和运费
	 * 
	 * @param order
	 * @param orderList
	 */
	private void setOrderbalance(Order order, Set<Order> orderList) {
		BigDecimal orderTotalSalePrice = order.getSalePrice();
		int size = orderList.size();
		int count = 0;
		BigDecimal actualSaveMoney = BigDecimal.ZERO;
		BigDecimal actualDeliveryFee = BigDecimal.ZERO;
		BigDecimal actualInvoice = BigDecimal.ZERO;
		BigDecimal totalInvoiceMoney = BigDecimal.ZERO;
		OrderInvoice tempOrderInvoice = new OrderInvoice();
		if (order.getConsignee().isNeedInvoice()) {
			for (OrderInvoice orderInvoice : order.getInvoiceList()) {
				totalInvoiceMoney = totalInvoiceMoney.add(orderInvoice.getMoney());
				tempOrderInvoice = orderInvoice;
				break;
			}
		}
		for (Order subOrder : orderList) {
			BigDecimal balanceSaveMoney = BigDecimal.ZERO;
			BigDecimal balanceDeliveryFee = BigDecimal.ZERO;
			BigDecimal balanceInvoice = BigDecimal.ZERO;
			OrderInvoice orderInvoice = new OrderInvoice();
			count++;
			BigDecimal rate = subOrder.getSalePrice().divide(orderTotalSalePrice, 4, BigDecimal.ROUND_HALF_UP);
			if (count == size) {
				balanceSaveMoney = order.getSaveMoney().subtract(actualSaveMoney);
				balanceDeliveryFee = order.getDeliveryFee().subtract(actualDeliveryFee);
				balanceInvoice = totalInvoiceMoney.subtract(actualInvoice);
			} else {
				if (order.getSaveMoney().compareTo(BigDecimal.ZERO) > 0) {
					balanceSaveMoney = order.getSaveMoney().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
					actualSaveMoney = actualSaveMoney.add(balanceSaveMoney);
				}
				if (order.getDeliveryFee().compareTo(BigDecimal.ZERO) > 0) {
					balanceDeliveryFee = order.getDeliveryFee().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
					actualDeliveryFee = actualDeliveryFee.add(balanceDeliveryFee);
				}
				if (order.getConsignee().isNeedInvoice() && totalInvoiceMoney.compareTo(BigDecimal.ZERO) > 0) {
					balanceInvoice = totalInvoiceMoney.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
					actualInvoice = actualInvoice.add(balanceInvoice);
				}
			}
			subOrder.setSaveMoney(balanceSaveMoney);
			subOrder.setDeliveryFee(balanceDeliveryFee);
			if (order.getConsignee().isNeedInvoice() && totalInvoiceMoney.compareTo(BigDecimal.ZERO) > 0) {
				BeanUtils.copyProperties(tempOrderInvoice, orderInvoice);
				orderInvoice.setMoney(balanceInvoice);
				orderInvoice.setOrder(subOrder);
				subOrder.addInvoice(orderInvoice);
			}
		}
	}

	/**
	 * 初始化子订单的支付
	 * 
	 * @param order
	 * @param orderList
	 */
	private void setOrderPayment(Order order, Set<Order> orderList) {
		for (Order subOrder : orderList) {
			BigDecimal advanceMoney = BigDecimal.ZERO;
			OrderPayment orderPayment = new OrderPayment();
			Set<OrderPayment> paymentList = new HashSet<OrderPayment>();
			for (OrderPayment tempOrderPayment : order.getPaymentList()) {
				BeanUtils.copyProperties(tempOrderPayment, orderPayment);
				orderPayment.setPayMoney(subOrder.getSalePrice().add(subOrder.getDeliveryFee())
						.subtract(subOrder.getSaveMoney()));
				orderPayment.setOrder(subOrder);
				paymentList.add(orderPayment);
				boolean isPay = orderPayment.isPay();
				if (isPay) {
					advanceMoney = advanceMoney.add(orderPayment.getPayMoney());
				}
			}
			subOrder.setPaymentList(paymentList);
			subOrder.setAdvanceMoney(advanceMoney);
		}
	}

	/**
	 * 拆单dc队列列表
	 * 
	 * @param order
	 * @return
	 */
	private List<DcPriority> getDps(Order order) {
		List<DcPriority> tempdps = new ArrayList<DcPriority>();
		List<DcPriority> dps = dcService.findDcPriorityByArea(order.getConsignee().getProvince());
		List<Long> excludeDps = new ArrayList<Long>();
		int dpsCount = dps.size();
		int count = 0;
		for (int i = 0; i < dpsCount; i++) {
			orderDcService.getDcSatisfactionRate(order, dps, excludeDps);
			orderDcService.satisfactionRateSort(dps);
			DcPriority firstDp = dps.get(0);
			if (i == count) {
				if (firstDp.getNumber() == order.getPurchaseQuantity() || firstDp.getNumber() == 0) {
					tempdps.add(firstDp);
					return tempdps;
				}
				tempdps.add(firstDp);
			} else {
				int number = firstDp.getNumber();
				if (number <= 0) {
					return tempdps;
				}
				tempdps.add(firstDp);
				if (getCompleteFillDps(order, tempdps) != null) {
					return tempdps;
				}
				if (getCompleteFillDps(order, dps) != null) {
					orderDcService.satisfactionRateSort(dps);
					return dps;
				}
			}
			excludeDps.add(firstDp.getLocation());
			dps.remove(firstDp);
		}
		return tempdps;
	}

	/**
	 * 计算dps列表能满足完全订单
	 * 
	 * @param order
	 * @param dps
	 * @return
	 */
	private List<DcPriority> getCompleteFillDps(Order order, List<DcPriority> dps) {
		if (CollectionUtils.isEmpty(dps)) {
			return null;
		}
		for (DcPriority dp : dps) {
			dp.setNumber(0);
		}
		int total = 0;
		for (OrderItem item : order.getItemList()) {
			int itemtotal = 0;
			for (DcPriority dp : dps) {
				int num = 0;
				if (dp.isAvailable()) {
					num = item.getProductSaleStockVoAvailableQuantity(dp.getLocation());
					num = item.getPurchaseQuantity() >= num ? num : item.getPurchaseQuantity();
					dp.setNumber(dp.getNumber() + num);
				}
				itemtotal = itemtotal + num > item.getPurchaseQuantity() ? item.getPurchaseQuantity() : itemtotal + num;
			}
			total = total + itemtotal;
		}
		if (total < order.getPurchaseQuantity()) {
			return null;
		}
		return dps;
	}

	private void copyOrder(Order resourceOrder, Order targetOrder) {
		OrderInit orderInit = new OrderInit(targetOrder);
		initDc(targetOrder);
		targetOrder.setOrderInit(orderInit);
		OrderCollection oc = new OrderCollection(targetOrder, false);
		oc.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_CONFIGURE)); // 系统配置
		targetOrder.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_NEW), resourceOrder.getCreator());
		targetOrder.setOrderCollection(oc);
		targetOrder.setChannel(resourceOrder.getChannel());
		OrderConsignee consignee = new OrderConsignee();
		BeanUtils.copyProperties(resourceOrder.getConsignee(), consignee);
		consignee.setOrder(targetOrder);
		targetOrder.setConsignee(consignee);
		targetOrder.setCreator(resourceOrder.getCreator());
		targetOrder.setCustomer(resourceOrder.getCustomer());
		targetOrder.setDeliveryType(resourceOrder.getDeliveryType());
		targetOrder.setPayType(resourceOrder.getPayType());
		targetOrder.setSupplyType(resourceOrder.getSupplyType());
		targetOrder.setStorageType(resourceOrder.getStorageType());
		targetOrder.setCustomer(resourceOrder.getCustomer());
		targetOrder.setShop(resourceOrder.getShop());
		targetOrder.setOuterId(resourceOrder.getOuterId());
		targetOrder.setProcessStatus(resourceOrder.getProcessStatus());
		targetOrder.setErpStatus(resourceOrder.getErpStatus());
		targetOrder.setPaymentStatus(resourceOrder.getPaymentStatus());
		if (targetOrder.getLogByStatus(Code.ORDER_PROCESS_STATUS_WAITING_PICKING) == null
				&& Code.ORDER_PAY_STATUS_COMPLETED.equals(targetOrder.getPaymentStatus().getId())) {
			targetOrder.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING), targetOrder.getCreator());
		}
		targetOrder.setCreateTime(resourceOrder.getCreateTime());
		targetOrder.setLastProcessTime(resourceOrder.getLastProcessTime());
		targetOrder.setMaxPayTime(resourceOrder.getMaxPayTime());
		targetOrder.setVirtual(resourceOrder.isVirtual());
		targetOrder.setNote(resourceOrder.getNote());
		targetOrder.setTransferResult(resourceOrder.getTransferResult());
	}

	private List<OrderItem> findComplexProductsByOrder(Order order) {
		List<OrderItem> complexItems = new ArrayList<OrderItem>();
		for (OrderItem orderItem : order.getItemList()) {
			if (orderItem.getProductSale().isComplex()) {
				OrderItem tempComplexItem = new OrderItem();
				tempComplexItem.setProductSale(orderItem.getProductSale());
				tempComplexItem.setListPrice(orderItem.getListPrice());
				tempComplexItem.setSalePrice(orderItem.getSalePrice());
				tempComplexItem.setPurchaseQuantity(orderItem.getPurchaseQuantity());
				complexItems.add(tempComplexItem);
			}
		}
		return complexItems;
	}

	private void saveOrderComplexProductsLog(Order order, List<OrderItem> tempComplexOrderItems) {
		for (OrderItem tempOrderItem : tempComplexOrderItems) {
			OrderItemComplexLog complexLog = new OrderItemComplexLog();
			complexLog.setOrder(order);
			complexLog.setProductSale(tempOrderItem.getProductSale());
			complexLog.setListPrice(tempOrderItem.getListPrice());
			complexLog.setSalePrice(tempOrderItem.getSalePrice());
			complexLog.setPurchaseQuantity(tempOrderItem.getPurchaseQuantity());
			complexLog.setCreateTime(new Date());
			orderItemComplexLogDao.save(complexLog);
		}
	}

	/**
	 * 给订单设置默认乡镇,分仓项目的时候添加此逻辑
	 * 
	 * @param order
	 */
	private void initDefaultTown(Order order) {
		OrderConsignee consignee = order.getConsignee();
		if (consignee.getTown() == null) {
			Area town = areaService.getDefTownByDistrict(consignee.getDistrict());
			consignee.setTown(town);
		}
		Set<OrderInvoice> invoiceList = order.getInvoiceList();
		if (invoiceList != null && 1 == invoiceList.size()) {
			OrderInvoice invoice = invoiceList.iterator().next();
			invoice.setTown(consignee.getTown());
		}
	}

	private void sendMail(Order order) {
		if (order.getOuterId() == null) {
			order.getConsignee().getConsignee();
			for (OrderPayment orderPayment : order.getPaymentList()) {
				orderPayment.getPayment().getName();
				orderPayment.getPayment().getId();
			}
			mailService.sendMailByOrderStatus(order); // 发送邮件
		}
	}

	/**
	 * 初始化订单
	 * 
	 * @param order
	 * @throws OrderInitException
	 */
	private void initOrder(Order order) throws OrderInitException {
		// 查询固定区域匹配表.如果条件在表内,修改为指定配货方式
		if (order.getDeliveryType() == null) {
			DeliveryType defDeliveryType = deliveryService.findDefaultDeliveryType(order.getChannel(), order
					.getConsignee().getDistrict());
			order.setDeliveryType(defDeliveryType);
		}
		order.setCreateTime(new Date());
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_NEW), order.getCreator());
		// 判断是否已经设置过下传中启的状态
		if (order.getTransferResult() == null) {
			order.setTransferResult(codeService.get(Code.EC2ERP_STATUS_NONE));
		}
		BigDecimal listPrice = BigDecimal.ZERO;
		BigDecimal salePrice = BigDecimal.ZERO;
		int purchaseQuantity = 0;
		Code storageType = null;
		boolean booking = false;
		boolean virtual = false;
		Shop shop = null;
		Set<OrderItem> orderItems = order.getItemList();
		if (orderItems == null || orderItems.size() == 0) {
			throw new OrderInitException(order, " 订单项为空");
		}
		for (OrderItem orderItem : orderItems) {
			ProductSale productSale = orderItem.getProductSale();
			int itemPurchaseQuantity = orderItem.getPurchaseQuantity();
			if (itemPurchaseQuantity <= 0) {
				throw new OrderInitException(order, productSale + " 订购数量小于等于0");
			}
			purchaseQuantity += itemPurchaseQuantity;
			listPrice = listPrice.add(orderItem.getTotalListPrice());
			salePrice = salePrice.add(orderItem.getTotalSalePrice());
			if (storageType == null) {
				storageType = productSale.getStorageType();
				virtual = productSale.getProduct().isVirtual();
				shop = productSale.getShop();
			} else {
				if (!storageType.equals(productSale.getStorageType())) {
					throw new OrderInitException(order, productSale + " 储配方式不一致");
				}
				if (virtual != productSale.getProduct().isVirtual()) {
					throw new OrderInitException(order, productSale + " 虚拟类型不一致");
				}
				if (!shop.equals(productSale.getShop())) {
					throw new OrderInitException(order, productSale + " 所属卖家不一致");
				}
			}
			if (productSale.getSupplyType().getId().equals(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING)) {
				booking = true;
			}
		}
		order.setPurchaseQuantity(purchaseQuantity);
		order.setPurchaseKind(orderItems.size());
		order.setListPrice(listPrice);
		order.setSalePrice(salePrice);
		order.setStorageType(storageType);
		if (Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER.equals(storageType.getId())) {
			order.setTransferResult(new Code(Code.EC2ERP_STATUS_CANCEL));
		}
		Long supplyTypeId = booking ? Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING : Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL;
		if (null == order.getSupplyType()) {
			order.setSupplyType(new Code(supplyTypeId));
		}
		order.setVirtual(virtual);
		order.setShop(shop);
		OrderComplexUtils.splitProductComplex(order);
		BigDecimal deliveryFee = order.getDeliveryFee();
		if (deliveryFee == null) {
			if (shop.getId().equals(Shop.WINXUAN_SHOP)) {
				deliveryFee = deliveryService.getDeliveryFee(order.getDeliveryType(), order.getConsignee().getTown(),
						listPrice);
			} else {
				deliveryFee = shop.getDeliveryFee();
			}
			order.setDeliveryFee(deliveryFee);
		}
		boolean oosCancel = order.getChannel().isOutOfStockCancel();
		if (oosCancel) {
			OrderConsignee consignee = order.getConsignee();
			consignee.setOutOfStockOption(codeService.get(Code.OUT_OF_STOCK_OPTION_CANCEL));
		}
		orderFormat(order);
	}

	private void initDc(Order order) {
		OrderDistributionCenter orderDistributionCenter = order.getDistributionCenter();
		if (orderDistributionCenter == null) {
			orderDistributionCenter = new OrderDistributionCenter();
			orderDistributionCenter.setOrder(order);
			order.setDistributionCenter(orderDistributionCenter);
		}
	}

	/**
	 * 对订单某些信息做处理 现只对consignee信息处理
	 * 
	 * @param order
	 */
	private void orderFormat(Order order) {
		OrderConsignee consignee = order.getConsignee();
		if (consignee != null) {
			consignee.setConsignee(consignee.getConsignee().trim());
			if (!StringUtils.isBlank(consignee.getPhone())) {
				consignee.setPhone(consignee.getPhone().trim());
			}
			if (!StringUtils.isBlank(consignee.getMobile())) {
				consignee.setMobile(consignee.getMobile().trim());
			}
		}
	}

	/**
	 * 订单送礼券类促销活动生效
	 * 
	 * @param order
	 */
	private void promotionEffect(Order order) {
		try {
			promotionService.orderPromotionEffect(order);
		} catch (PromotionException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 检查非文轩卖家的自储自发的订单 下单、订单支付时如果订单状态为“等待配货”，则直接修改状态为“正在配货”
	 * 
	 * @param order
	 */
	private void checkOrderStatus(Order order, User operator) {
		if (!order.getShop().getId().equals(Shop.WINXUAN_SHOP)
				&& order.getStorageType().getId().equals(Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER)
				&& order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
			order.setProcessStatus(new Code(Code.ORDER_PROCESS_STATUS_PICKING));
			order.addStatusLog(order.getProcessStatus(), operator);
		}
	}

	/**
	 * 检查订单库存
	 * 
	 * @param order
	 * @throws OrderStockException
	 */
	private void checkOrderStock(Order order) throws OrderStockException {
		if (order.isCheckStock() && !order.getTransferResult().getId().equals(Code.EC2SAP_STATUS_NONE)) {
			List<OrderItem> list = findOutOfStockItemList(order);
			if (!list.isEmpty()) {
				throw new OrderStockException(list.get(0));
			}
		}
	}

	@Override
	public List<OrderItem> findOutOfStockItemList(Order order) {
		List<OrderItem> list = new ArrayList<OrderItem>();
		OrderDistributionCenter dc = order.getDistributionCenter();
		for (OrderItem orderItem : order.getItemList()) {
			ProductSale productSale = orderItem.getProductSale();
			if (productSale.getShop().getId().equals(Shop.WINXUAN_SHOP)) {
				int availableQuantity = 0;
				// 已指定了仓库就按照原有方式计算
				if (null != dc && dc.getDcDest() != null) {
					ProductSaleStock productSaleStock = productSale.getStockByDc(dc.getDcDest());
					boolean isRapid = Code.DC_8A19.equals(dc.getDcDest().getId());
					boolean isBooking = Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(productSale.getSupplyType()
							.getId());
					if (isRapid || isBooking) {
						availableQuantity = productSaleStock.getVirtualAvalibleQuantity();
					} else {
						availableQuantity = productSale.getAvalibleQuantity();
					}
				} else {
					availableQuantity = productSale.getAvalibleQuantity();
				}
				if (orderItem.getPurchaseQuantity() > availableQuantity) {
					list.add(orderItem);
				}
			}
		}
		return list;
	}

	/**
	 * 计算订单商品项balancePrice
	 * 
	 * @param order
	 */
	private void setupBalancePrice(Order order) {
		BigDecimal saveMoney = order.getSaveMoney();
		Set<OrderItem> orderItems = order.getItemList();
		if (saveMoney.compareTo(BigDecimal.ZERO) <= 0) { // 订单没有优惠不进行分摊计算,balanceprice与saleprice相同
			for (OrderItem item : orderItems) {
				item.setBalancePrice(item.getSalePrice());
			}
		}
		// 订单有优惠，则按照订单中每种商品的实洋占比计算balanceprice
		else {
			BigDecimal orderTotalSalePrice = order.getSalePrice();
			BigDecimal orderTotalBalancePrice = orderTotalSalePrice.subtract(saveMoney);
			BigDecimal actualTotalBalancePrice = BigDecimal.ZERO;
			ProductSale maxPriceProduct = null;
			BigDecimal maxPrice = BigDecimal.ZERO;
			for (OrderItem item : orderItems) {
				int quantity = item.getPurchaseQuantity();
				BigDecimal balancePrice = item.getSalePrice();
				BigDecimal rate = item.getTotalSalePrice().divide(orderTotalSalePrice, MagicNumber.TEN,
						BigDecimal.ROUND_HALF_UP);
				balancePrice = orderTotalBalancePrice.multiply(rate).divide(BigDecimal.valueOf(quantity), 1,
						BigDecimal.ROUND_UP);
				actualTotalBalancePrice = actualTotalBalancePrice.add(balancePrice.multiply(BigDecimal
						.valueOf(quantity)));
				if (balancePrice.compareTo(maxPrice) > 0) {
					maxPrice = balancePrice;
					maxPriceProduct = item.getProductSale();
				}
				item.setBalancePrice(balancePrice);
			}
			if (actualTotalBalancePrice.compareTo(orderTotalBalancePrice) != 0) {
				BigDecimal diff = orderTotalBalancePrice.subtract(actualTotalBalancePrice);
				for (OrderItem item : orderItems) {
					ProductSale itemP = item.getProductSale();
					if (itemP.equals(maxPriceProduct)) {
						BigDecimal balance = item.getBalancePrice();
						BigDecimal diffRate = diff.divide(BigDecimal.valueOf(item.getPurchaseQuantity()),
								MagicNumber.TEN, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_UP);
						balance = balance.add(diffRate).setScale(2, BigDecimal.ROUND_UP); // 保留两位小数，进位处理
						item.setBalancePrice(balance.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : balance);
					}
				}
			}
		}
	}

	/**
	 * 设置订单支付数据
	 * 
	 * @param order
	 * @throws CustomerAccountException
	 * @throws PresentException
	 * @throws PresentCardException
	 * @throws OrderInitException
	 */
	private void setupOrderPayment(Order order, User operator) throws CustomerAccountException, PresentException,
			PresentCardException, OrderInitException {
		Set<OrderPayment> paymentList = order.getPaymentList();
		if (paymentList == null || paymentList.size() == 0) {
			throw new OrderInitException(order, "支付数据为空");
		}
		BigDecimal advanceMoney = BigDecimal.ZERO;
		BigDecimal totalPayMoney = BigDecimal.ZERO;
		boolean usingCod = false;
		for (OrderPayment orderPayment : order.getPaymentList()) {
			orderPayment.setCreateTime(new Date());
			orderPayment.setOrder(order);
			PaymentCredential credential = orderPayment.getCredential();
			Payment payment = paymentService.get(orderPayment.getPayment().getId());
			BigDecimal payMoney = orderPayment.getPayMoney();
			if (payMoney.compareTo(BigDecimal.ZERO) <= 0) {
				throw new OrderInitException(order, payment + "支付金额 小于等于0");
			}
			Long paymentId = payment.getId();
			Long paymentType = payment.getType().getId();
			boolean isPay = true;
			Date now = new Date();
			if (Payment.ACCOUNT.equals(paymentId)) {
				isPay = false;
			} else if (paymentType.equals(Code.PAYMENT_TYPE_COD)) {
				usingCod = true;
				isPay = false;
			} else if (paymentId.equals(Payment.COUPON)) {
				advanceMoney = advanceMoney.add(payMoney);
				Present present = presentService.get(Long.parseLong(credential.getOuterId()));
				presentService.usePresent(present, order, payMoney);
			} else if (paymentId.equals(Payment.GIFT_CARD)) {
				advanceMoney = advanceMoney.add(payMoney);
				PresentCard presentCard = presentCardService.get(credential.getOuterId());
				presentCardService.use(presentCard, order, payMoney.negate());
			} else if (paymentId.equals(Payment.CHANNEL)) {
				isPay = orderPayment.isPay();
				if (isPay) {
					advanceMoney = advanceMoney.add(payMoney);
				} else {
					isPay = false;
				}
			} else {
				isPay = false;
			}
			if (isPay) {
				orderPayment.setPay(true);
				if (orderPayment.getPayTime() == null) {
					orderPayment.setPayTime(now);
				}
			}
			orderPayment.setOrder(order);
			order.updateMaxPayTime(payment.getMaxPayDate());
			totalPayMoney = totalPayMoney.add(payMoney);
		}
		if (totalPayMoney.compareTo(order.getTotalPayMoney()) != 0) {
			throw new OrderInitException(order, "OrderPayment金额合计" + totalPayMoney + "与一共需要支付的金额"
					+ order.getTotalPayMoney() + "不相等");
		}
		if (advanceMoney.compareTo(order.getTotalPayMoney()) > 0) {
			throw new OrderInitException(order, "预付款金额" + advanceMoney + "大于一共需要支付的金额" + order.getTotalPayMoney());
		}
		order.setAdvanceMoney(advanceMoney);
		setupPayStatus(order, operator);
		Long payType = order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_NEW) && usingCod ? Code.ORDER_PAY_TYPE_FIRST_DELIVERY
				: Code.ORDER_PAY_TYPE_FIRST_PAY;
		if (order.isCODOrder()) {
			order.setPayType(new Code(Code.ORDER_PAY_TYPE_FIRST_DELIVERY));
		} else {
			order.setPayType(new Code(payType));
		}
	}

	/**
	 * 订单设置支付和处理状态
	 * 
	 * @param order
	 */
	private void setupPayStatus(Order order, User operator) {
		BigDecimal advanceMoney = order.getAdvanceMoney();
		int payStatusComparetor = advanceMoney.compareTo(order.getTotalPayMoney());
		if (payStatusComparetor == 0) {
			order.setPaymentStatus(new Code(Code.ORDER_PAY_STATUS_COMPLETED));
			if (order.getLogByStatus(Code.ORDER_PROCESS_STATUS_WAITING_PICKING) == null
			// 360buy已经支付完的订单也有COD订单，京东COD订单不分包，总实洋大于500的COD订单需要人工审核
					&& !order.isCODOrder()) {
				order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING), operator);
			}
		} else if (payStatusComparetor < 0) {
			Long payStatus = advanceMoney.compareTo(BigDecimal.ZERO) > 0 ? Code.ORDER_PAY_STATUS_PART
					: Code.ORDER_PAY_STATUS_NONE;
			order.setPaymentStatus(new Code(payStatus));
		}
	}

	/**
	 * 使用的新的支付凭证对订单进行登记到款 前置条件： 1.订单没有此支付方式，并且支付类型一致 2.后台界面产生的登记到款
	 * 
	 * 目前进行登记到款的订单未支付的记录只有一条，且不能部分登记到款
	 * 
	 * @param order
	 * @param paymentCredential
	 */
	private void swapOrderPayment(Order order, PaymentCredential paymentCredential, User operator, BigDecimal payMoney) {
		if (payMoney.compareTo(BigDecimal.ZERO) <= 0 || !(operator instanceof Employee)
				|| operator.getId().equals(Employee.SYSTEM)) {
			return;
		}
		Set<OrderPayment> orderPaymentSet = order.getPaymentList();
		Payment chooisePayment = paymentCredential.getPayment();
		OrderPayment oldPayment = null;
		for (OrderPayment orderPayment : orderPaymentSet) {
			Payment payment = orderPayment.getPayment();
			if (!orderPayment.isPay() && !payment.getId().equals(chooisePayment.getId())) {
				boolean isTypeMatch = orderPayment.getPayment().getType().getId()
						.equals(chooisePayment.getType().getId());
				if (isTypeMatch) {
					oldPayment = orderPayment;
					break;
				}
			}
		}
		if (oldPayment != null) {
			orderPaymentSet.remove(oldPayment);
			OrderPayment newOrderPayment = new OrderPayment();
			newOrderPayment.setCreateTime(new Date());
			newOrderPayment.setOrder(order);
			newOrderPayment.setPay(false);
			newOrderPayment.setPayment(chooisePayment);
			newOrderPayment.setPayMoney(oldPayment.getPayMoney());
			order.addPayment(newOrderPayment);
			this.update(order);
			orderPaymentSet.add(newOrderPayment);
		}
	}

	@Override
	public void pay(List<Order> orderList, PaymentCredential paymentCredential, User operator)
			throws OrderStatusException, CustomerAccountException, OrderStockException, PaymentCredentialException,
			ReturnOrderException {
		Customer customer = orderList.get(0).getCustomer();
		customer.getAccount().getBalance();

		if (!paymentService.checkAndSave(paymentCredential)) {
			log.info("支付方式：" + paymentCredential.getPayment().getName() + "，凭据号：" + paymentCredential.getOuterId()
					+ "已支付过。");
			return;
		}

		BigDecimal paymentCredentialMoney = paymentCredential.getMoney();
		for (Order order : orderList) {
			Code actualStatus = order.getProcessStatus();

			/* 已取消订单，网银支付后，退款至暂存款 */
			if ((actualStatus.equals(codeService.get(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)) || actualStatus
					.equals(codeService.get(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)))
					&& paymentCredential.getPayment().getType().getId().equals(Code.PAYMENT_TYPE_ONLINE)) {
				BigDecimal refundMoney = order.getOnlinePayMoney();
				if (paymentCredentialMoney.compareTo(refundMoney) >= 0) {
					Bank bank = BankConfig.getBank(paymentCredential.getPayment().getId());
					try {
						refundService.refund2Card(order, refundMoney, bank, false, paymentCredential.getOuterId());
					} catch (UnsupportBankException e) {
						log.info(e.getMessage(), e);
						customerService.useAccount(customer, refundMoney, order, operator, new Code(
								Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
					} catch (RefundExpiredException e) {
						log.info(e.getMessage(), e);
						customerService.useAccount(customer, refundMoney, order, operator, new Code(
								Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
					}
					paymentCredentialMoney = paymentCredentialMoney.subtract(refundMoney);
				}
				continue;
			}

			/* 未取消订单，网银支付后，比较批次，如果相同，不处理；若不同，核对订单状态，已经支付的订单，退款至暂存款 */
			if (paymentCredential.getPayment().getType().getId().equals(Code.PAYMENT_TYPE_ONLINE)
					&& order.getRequireOnlinePayMoney().equals(BigDecimal.ZERO)
					&& !order.getOnlinePayMoney().equals(BigDecimal.ZERO)) {
				OrderPayment orderPayment = order.getPayment(paymentCredential.getPayment().getId());

				if (orderPayment != null && orderPayment.getCredential() != null
						&& orderPayment.getCredential().getOuterId().equals(paymentCredential.getOuterId())) {
					paymentCredentialMoney = paymentCredentialMoney.subtract(orderPayment.getPayMoney());
					continue;
				}
				BigDecimal refundMoney = order.getOnlinePayMoney();
				if (paymentCredentialMoney.compareTo(refundMoney) >= 0) {
					Bank bank = BankConfig.getBank(paymentCredential.getPayment().getId());
					try {
						// 退款金额需要银行退款比例来扣取手续费
						BigDecimal refundfee = paymentCredential.getPayment().getRefundFee();
						refundMoney = refundMoney.multiply(REFUNDFREE_ALL.subtract(refundfee));
						refundService.refund2Card(order, refundMoney, bank, false, paymentCredential.getOuterId());
						// 多次支付凭证记录
						Set<OrderPayment> orderPaymentSet = order.getPaymentList();
						OrderPayment refundOrderPayment = new OrderPayment();
						refundOrderPayment.setCreateTime(new Date());
						refundOrderPayment.setCredential(paymentCredential);
						refundOrderPayment.setOrder(order);
						refundOrderPayment.setPay(true);
						refundOrderPayment.setPayment(paymentCredential.getPayment());
						refundOrderPayment.setPayMoney(paymentCredential.getMoney());
						refundOrderPayment.setPayTime(paymentCredential.getPayTime());
						refundOrderPayment.setReturnMoney(refundMoney);
						orderPaymentSet.add(refundOrderPayment);
						order.setPaymentList(orderPaymentSet);
						this.update(order);
					} catch (UnsupportBankException e) {
						log.info(e.getMessage(), e);
						customerService.useAccount(customer, refundMoney, order, operator, new Code(
								Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
					} catch (RefundExpiredException e) {
						log.info(e.getMessage(), e);
						customerService.useAccount(customer, refundMoney, order, operator, new Code(
								Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
					}
					paymentCredentialMoney = paymentCredentialMoney.subtract(refundMoney);
				}
				continue;
			}

			/* 处理已提交订单 */
			Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_NEW);
			if (!order.getDeliveryType().getId().equals(DeliveryType.DIY)
					&& (!actualStatus.equals(expectedStatus) || actualStatus.equals(expectedStatus)
							&& !order.getPayType().getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY))) {
				throw new OrderStatusException(order, new Code[] { expectedStatus });
			}
			Set<OrderPayment> orderPaymentSet = order.getPaymentList();
			Payment chooisePayment = paymentCredential.getPayment();
			swapOrderPayment(order, paymentCredential, operator, paymentCredentialMoney);
			for (OrderPayment orderPayment : orderPaymentSet) {
				if (!orderPayment.isPay()) {
					Long orderPaymentId = orderPayment.getPayment().getId();
					Long credentialPaymentId = paymentCredential.getPayment().getId();
					Long orderPaymentType = orderPayment.getPayment().getType().getId();
					Long credentialPaymentType = chooisePayment.getType().getId();
					boolean isIdOrTypeMatch = (orderPaymentId.equals(credentialPaymentId) || (orderPaymentType
							.equals(Code.PAYMENT_TYPE_ONLINE) && credentialPaymentType.equals(Code.PAYMENT_TYPE_ONLINE)));
					if (isIdOrTypeMatch) {
						BigDecimal payMoney = orderPayment.getPayMoney();
						if (paymentCredentialMoney.compareTo(payMoney) >= 0) {
							paymentCredentialMoney = paymentCredentialMoney.subtract(payMoney);
							orderPayment.setPay(true);
							orderPayment.setPayTime(new Date());
							orderPayment.setCredential(paymentCredential);
							orderPayment.setPayment(paymentCredential.getPayment());
							order.setAdvanceMoney(order.getAdvanceMoney().add(payMoney));
						}
					}
				}
			}
			setupPayStatus(order, operator);
			if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
				updateStock(order);
				checkOrderStatus(order, operator);
				sendMail(order);
			}
			this.update(order);
			promotionEffect(order);
		}
		if (paymentCredentialMoney.compareTo(BigDecimal.ZERO) == 1) {
			customerService.useAccount(customer, paymentCredentialMoney, null, operator, new Code(
					Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
		}
	}

	@Override
	public void pay(List<Order> orderList, CustomerAccount customerAccount, BigDecimal accountMoney, User operator)
			throws OrderStatusException, CustomerAccountException, OrderStockException {
		if (accountMoney.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("支付金额必须大于0");
		}
		for (Order order : orderList) {
			Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_NEW);
			Code actualStatus = order.getProcessStatus();
			if (!actualStatus.equals(expectedStatus) || actualStatus.equals(expectedStatus)
					&& !order.getPayType().getId().equals(Code.ORDER_PAY_TYPE_FIRST_PAY)) {
				throw new OrderStatusException(order, new Code[] { expectedStatus });
			}
			if (accountMoney.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal requidPayMoney = order.getRequidPayMoney();
				if (accountMoney.compareTo(requidPayMoney) >= 0) {
					accountMoney = accountMoney.subtract(requidPayMoney);
					customerService.useAccount(customerAccount.getCustomer(), requidPayMoney.negate(), order, operator,
							new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_PAY));
					order.setAdvanceMoney(order.getAdvanceMoney().add(requidPayMoney));
					OrderPayment orderPayment = new OrderPayment();
					orderPayment.setOrder(order);
					orderPayment.setPayment(paymentService.get(Payment.ACCOUNT));
					orderPayment.setPayMoney(requidPayMoney);
					orderPayment.setPay(true);
					Date now = new Date();
					orderPayment.setCreateTime(now);
					orderPayment.setPayTime(now);
					order.addPayment(orderPayment);
					order.setPaymentStatus(new Code(Code.ORDER_PAY_STATUS_COMPLETED));
					order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING), operator);
					updateStock(order);
					checkOrderStatus(order, operator);
					this.update(order);
					promotionEffect(order);
					sendMail(order);
				}
			}
		}
	}

	@Override
	public void payByAccount(List<Order> orderList, Customer customer, User operator) throws OrderStatusException,
			CustomerAccountException {

		for (Order order : orderList) {
			Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_NEW);
			Code actualStatus = order.getProcessStatus();
			if (!actualStatus.equals(expectedStatus)) {
				throw new OrderStatusException(order, new Code[] { expectedStatus });
			}
			BigDecimal requidPayMoney = order.getAccountUnpaidMoney();
			customerService.useAccount(customer, requidPayMoney.negate(), order, operator, new Code(
					Code.CUSTOMER_ACCOUNT_USE_TYPE_PAY));

			OrderPayment orderPayment = order.getPayment(Payment.ACCOUNT);
			orderPayment.setPay(true);
			orderPayment.setPayTime(new Date());
			orderPayment.setOrder(order);
			order.setAdvanceMoney(order.getAdvanceMoney().add(requidPayMoney));
			setupPayStatus(order, operator);
			this.update(order);
			if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)) {
				updateStock(order);
				checkOrderStatus(order, operator);
				sendMail(order);
			}
			promotionEffect(order);
		}
	}

	@Override
	public void audit(Order order, Employee operator) throws OrderStatusException, OrderStockException {
		Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_NEW);
		Code actualStatus = order.getProcessStatus();
		if (!actualStatus.equals(expectedStatus) || actualStatus.equals(expectedStatus)
				&& !order.getPayType().getId().equals(Code.ORDER_PAY_TYPE_FIRST_DELIVERY)) {
			throw new OrderStatusException(order, new Code[] { expectedStatus });
		}
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING), operator);
		updateStock(order);
		this.update(order);
		promotionEffect(order);
		sendMail(order);
	}

	@Override
	public void nullify(Order order, Employee operator) throws OrderStatusException {
		if (!order.getCanBeArchived()) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_PICKING),
					new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING) });
		}
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_ARCHIVE), operator);
		updateStock(order);
		this.update(order);
	}

	@Override
	public void picking(String orderid, Employee operator) {
		if (StringUtils.isBlank(orderid)) {
			throw new NullPointerException();
		}
		Order order = orderDao.get(orderid);
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_PICKING), operator);
		updateStock(order);
		this.update(order);
	}

	@Override
	public void erpIntercept(Order order, Employee operator) throws OrderStatusException {
		if (order == null) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT) });
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT)) {
			return;
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)
				&& order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)) {
			order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT), operator);
			this.update(order);
		}
	}

	@Override
	public void cancel(Order order, Code cancelStatus, User operator) throws OrderStatusException,
			CustomerAccountException, PresentCardException, PresentException, ReturnOrderException {
		Long cancelCodeId = cancelStatus.getId();
		boolean canbeCancel;
		if (Code.EC2ERP_STATUS_CANCEL.equals(order.getTransferResult().getId())) {
			canbeCancel = order.canbeDonotUploadErpCancel();
		} else if (Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL.equals(cancelCodeId)) {
			canbeCancel = order.canbeCustomerCancel(operator);
		} else if (Code.ORDER_PROCESS_STATUS_ERP_CANCEL.equals(cancelCodeId)) {
			canbeCancel = order.canbeErpCancel();
		} else if (Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL.equals(cancelCodeId)) {
			canbeCancel = order.canbeOutOfStockCancel();
		} else if (Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL.equals(cancelCodeId)) {
			canbeCancel = order.canbeRejectionCancel();
		} else if (Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL.equals(cancelCodeId)) {
			canbeCancel = order.canbeSystemCancel();
		} else {
			canbeCancel = false;
		}
		if (!canbeCancel) {
			throw new OrderStatusException(order, new Code[] { cancelStatus });
		}
		order.addStatusLog(cancelStatus, operator);
		BigDecimal advanceMoney = order.getAdvanceMoney();
		if (advanceMoney.compareTo(BigDecimal.ZERO) > 0) {
			refundOrder(order, advanceMoney, operator);
		}
		/**
		 * infor上线后，青白江仓(8A17)的订单由infor处理。调用DMS服务回传订单取消信息
		 * 因为没办法估计其他地方处理取消这个方法的异常方式，这个地方暂时抛出运行时异常
		 */
		try {
			backfillOrderShipperInfo(order);
		} catch (OrderShipperInfoException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		updateStock(order);
		if (Code.ORDER_PROCESS_STATUS_ERP_CANCEL.equals(cancelCodeId)
				|| Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL.equals(cancelCodeId)) {
			financeService.processReuturnFinance(order, new Date());
		}
		this.update(order);
		sendMail(order);
		orderMessageService.cancel(order);
		this.broadcastOrderStatus(order);
	}

	@Override
	public void interceptCancel(Order order, User operator) throws OrderException {
		if (!order.canbeDelivery()) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_PICKING),
					new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT) });
		}
		Set<OrderItem> orderItems = order.getItemList();
		int totalDeliveryQuantity = 0;
		int totalDeliveryKind = 0;
		BigDecimal totalDeliveryListPrice = BigDecimal.ZERO;
		BigDecimal totalDeliverySalePrice = BigDecimal.ZERO;
		for (OrderItem orderItem : orderItems) {
			int purchaseQuantity = orderItem.getPurchaseQuantity();
			int deliveryQuantity = orderItem.getDeliveryQuantity();
			BigDecimal listPrice = orderItem.getListPrice();
			BigDecimal salePrice = orderItem.getSalePrice();
			String msg = orderItem.getProductSale() + "发货数量" + deliveryQuantity;
			if (deliveryQuantity < 0) {
				throw new OrderDeliveryException(order, msg + ",小于0");
			} else if (deliveryQuantity > purchaseQuantity) {
				throw new OrderDeliveryException(order, msg + ",大于订购数量" + purchaseQuantity);
			}
			totalDeliveryQuantity += deliveryQuantity;
			totalDeliveryListPrice = totalDeliveryListPrice.add(listPrice.multiply(new BigDecimal(deliveryQuantity)));
			totalDeliverySalePrice = totalDeliverySalePrice.add(salePrice.multiply(new BigDecimal(deliveryQuantity)));
			if (deliveryQuantity > 0) {
				totalDeliveryKind++;
			}
		}
		if (totalDeliveryQuantity == 0) {
			throw new OrderDeliveryException(order, "发货数量全部为0");
		}
		order.setDeliveryListPrice(totalDeliveryListPrice);
		order.setDeliverySalePrice(totalDeliverySalePrice);
		order.setDeliveryQuantity(totalDeliveryQuantity);
		order.setDeliveryKind(totalDeliveryKind);
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_ERP_CANCEL), operator);
		setupDeliveryMoney(order);

		updateStock(order);
		this.update(order);
		try {
			returnOrderService.create(order.getId(), operator);
		} catch (ReturnOrderException e) {
			throw new OrderCancelException(order, e.getMessage());
		}
	}

	@Override
	public void transportNewOrder(Order order) throws OrderStatusException {
		Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_WAITING_PICKING);
		Code actualStatus = order.getProcessStatus();
		if (!expectedStatus.equals(actualStatus)) {
			throw new OrderStatusException(order, new Code[] { expectedStatus });
		}
		Employee operator = employeeService.get(Employee.SYSTEM);
		order.setTransferResult(new Code(Code.EC2ERP_STATUS_NEW));
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_PICKING), operator);
		sendMail(order);
		this.update(order);
	}

	@Override
	public void transportSapOrder(Order order) throws OrderStatusException {
		Code expectedStatus = codeService.get(Code.ORDER_PROCESS_STATUS_WAITING_PICKING);
		Code actualStatus = order.getProcessStatus();
		if (!expectedStatus.equals(actualStatus)) {
			throw new OrderStatusException(order, new Code[] { expectedStatus });
		}
		Employee operator = employeeService.get(Employee.SYSTEM);
		order.setTransferResult(new Code(Code.EC2SAP_STATUS_NEW));
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_PICKING), operator);
		this.update(order);
	}

	@Override
	public void updateErpStatus(Order order, Code erpStatus) {
		order.setErpStatus(erpStatus);
		this.update(order);
	}

	@Override
	public void updateDeliveryCode(Order order, DeliveryCompany company, String deliveryCode)
			throws OrderStatusException {
		if (order.isDeliveried()) {
			if (!order.getDeliveryCompany().equals(company) || !order.getDeliveryCode().equals(deliveryCode)) {
				DeliveryCompany old = order.getDeliveryCompany();
				order.setDeliveryCompany(company);
				order.setDeliveryCode(deliveryCode);
				this.update(order);
				if (!old.equals(company)) {
					orderMessageService.reassignmentDeliveryCompany(order);
				}
			}
		} else {
			throw new OrderStatusException(order, null);
		}
	}

	/**
	 * 计算订单退款（这个方法很强大，不会多退用户钱 by刘安）
	 * 
	 * @param order
	 * @param operator
	 * @throws CustomerAccountException
	 * @throws PresentCardException
	 * @throws PresentException
	 * @throws ReturnOrderException
	 */
	private void refundOrder(Order order, BigDecimal refund, User operator) throws CustomerAccountException,
			PresentCardException, PresentException, ReturnOrderException {
		List<OrderPayment> paymentList = order.getRefundSortedPaymentList();
		for (OrderPayment orderPayment : paymentList) {
			Long paymentTypeId = orderPayment.getPayment().getType().getId();
			Long paymentId = orderPayment.getPayment().getId();
			BigDecimal canRefundMoney = orderPayment.getCanRefundMoney();
			if (canRefundMoney.compareTo(BigDecimal.ZERO) > 0 && refund.compareTo(BigDecimal.ZERO) > 0) {
				BigDecimal realRefundMoney = refund.compareTo(canRefundMoney) > 0 ? canRefundMoney : refund;
				refund = refund.subtract(realRefundMoney);
				/* 原卡原退 */
				if (paymentTypeId.equals(Code.PAYMENT_TYPE_ONLINE) && orderPayment.isPay()
						&& realRefundMoney.compareTo(BigDecimal.ZERO) > 0) {
					if (!Payment.CHANNEL.equals(paymentId) && !Payment.OFF_PERIOD.equals(paymentId)) {
						if (!Payment.CHINA_PAY_MOBILE.equals(paymentId)) {
							try {
								refundService.refund2Card(order, realRefundMoney);
							} catch (UnsupportBankException e) {
								log.info(e.getMessage(), e);
								customerService.useAccount(order.getCustomer(), realRefundMoney, order, operator,
										new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
							} catch (RefundExpiredException e) {
								log.info(e.getMessage(), e);
								customerService.useAccount(order.getCustomer(), realRefundMoney, order, operator,
										new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
							}
						} else {
							customerService.useAccount(order.getCustomer(), realRefundMoney, order, operator, new Code(
									Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
						}
					}
				} else if (paymentTypeId.equals(Code.PAYMENT_TYPE_ACCOUNT)
						|| paymentTypeId.equals(Code.PAYMENT_TYPE_OFFLINE)) {
					customerService.useAccount(order.getCustomer(), realRefundMoney, order, operator, new Code(
							Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
				} else if (paymentId.equals(Payment.GIFT_CARD)) {
					PresentCard presentCard = presentCardService.get(orderPayment.getCredential().getOuterId());
					presentCardService.use(presentCard, order, realRefundMoney);
				} else if (paymentId.equals(Payment.COUPON)) {
					Present present = presentService.get(Long.valueOf(orderPayment.getCredential().getOuterId()));
					presentService.reissuePresent(present, order);
				}
				orderPayment.setReturnMoney(orderPayment.getReturnMoney().add(realRefundMoney));
			}
		}
	}

	@Override
	public void delivery(Order order, DeliveryCompany deliveryCompany, String deliveryCode, User operator)
			throws OrderStatusException, OrderDeliveryException, CustomerAccountException, PresentCardException,
			PresentException, ReturnOrderException {
		if (!order.canbeDelivery()) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_PICKING),
					new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT) });
		}
		Set<OrderItem> orderItems = order.getItemList();
		int totalDeliveryQuantity = 0;
		int totalDeliveryKind = 0;
		BigDecimal totalDeliveryListPrice = BigDecimal.ZERO;
		BigDecimal totalDeliverySalePrice = BigDecimal.ZERO;
		for (OrderItem orderItem : orderItems) {
			int purchaseQuantity = orderItem.getPurchaseQuantity();
			int deliveryQuantity = orderItem.getDeliveryQuantity();
			BigDecimal listPrice = orderItem.getListPrice();
			BigDecimal salePrice = orderItem.getSalePrice();
			String msg = orderItem.getProductSale() + "发货数量" + deliveryQuantity;
			if (deliveryQuantity < 0) {
				throw new OrderDeliveryException(order, msg + ",小于0");
			} else if (deliveryQuantity > purchaseQuantity) {
				throw new OrderDeliveryException(order, msg + ",大于订购数量" + purchaseQuantity);
			}
			totalDeliveryQuantity += deliveryQuantity;
			totalDeliveryListPrice = totalDeliveryListPrice.add(listPrice.multiply(new BigDecimal(deliveryQuantity)));
			totalDeliverySalePrice = totalDeliverySalePrice.add(salePrice.multiply(new BigDecimal(deliveryQuantity)));
			if (deliveryQuantity > 0) {
				totalDeliveryKind++;
			}
		}
		if (totalDeliveryQuantity == 0) {
			throw new OrderDeliveryException(order, "发货数量全部为0");
		}
		if(!Code.DC_8A17.equals(order.getDistributionCenter().getDcOriginal().getId())){
			order.setDeliveryCompany(deliveryCompany);
			order.setDeliveryCode(deliveryCode);
		}
		order.setDeliveryListPrice(totalDeliveryListPrice);
		order.setDeliverySalePrice(totalDeliverySalePrice);
		order.setDeliveryQuantity(totalDeliveryQuantity);
		order.setDeliveryKind(totalDeliveryKind);
		Long status = totalDeliveryQuantity == order.getPurchaseQuantity() ? Code.ORDER_PROCESS_STATUS_DELIVERIED
				: Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG;
		order.addStatusLog(new Code(status), operator);
		/**
		 * 计算订单退款（这个逻辑很强大，应收金额的负数就是退款金额 by刘安）
		 */
		if (status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)) {
			BigDecimal requidPayMoney = order
					.getDeliveryBalancePrice()
					.add(order.getDeliveryFee())
					.subtract(order.getAdvanceMoney())
					.subtract(
							order.getTotalBalancePrice().subtract(order.getSalePrice().subtract(order.getSaveMoney())));
			if (requidPayMoney.compareTo(BigDecimal.ZERO) < 0) {
				refundOrder(order, requidPayMoney.negate(), operator);
			}
		}
		setupDeliveryMoney(order);

		/**
		 * 设置发货时间
		 */
		if (order.getDeliveryTime() == null) {
			order.setDeliveryTime(new Date());
		}
		/**
		 * infor上线后，青白江仓(8A17)的订单由infor处理。调用DMS服务回传订单发货信息
		 */
		try {
			backfillOrderShipperInfo(order);
		} catch (OrderException e) {
			throw new OrderDeliveryException(order, e.getMessage());
		}
		/**
		 * 设置出货DC
		 */
		OrderDistributionCenter center = order.getDistributionCenter();
		if (!center.getDcOriginal().getId().equals(center.getDcDest().getId())) {
			center.setDcDest(center.getDcOriginal());
		}
		updateStock(order);
		this.update(order);
		promotionEffect(order);
		sendMail(order);
		financeService.processDelvieryFinance(order, order.getDeliveryTime());
		// 发货发送短信
		orderMessageService.delivery(order);
		this.broadcastOrderStatus(order);
	}

	/**
	 * infor上线后，青白江仓(8A17)的订单由infor处理。调用DMS服务回传订单发货或者订单取消信息
	 * 
	 * @param order
	 * @throws OrderException
	 */
	private void backfillOrderShipperInfo(Order order) throws OrderShipperInfoException {
		OrderDistributionCenter center = order.getDistributionCenter();
		Preconditions.checkNotNull(center, "订单的OrderDistributionCenter is null!");
		Preconditions.checkNotNull(center.getDcOriginal(), "订单的出货dc is null!");
		Code dcOriginal = center.getDcOriginal();
		if (Code.DC_8A17.equals(dcOriginal.getId())) {
			this.orderShipperInfoService.backfillOrderShipperInfo(order);
		}
	}

	/**
	 * 计算orderPayment.deliveryMoney
	 * 
	 * @param order
	 */
	private void setupDeliveryMoney(Order order) {
		for (OrderPayment orderPayment : order.getPaymentList()) {
			BigDecimal deliveryMoney = BigDecimal.ZERO;
			BigDecimal payMoney = orderPayment.getPayMoney();
			BigDecimal returnMoney = orderPayment.getReturnMoney();
			if (orderPayment.isPay()) {
				deliveryMoney = payMoney.subtract(returnMoney);
			} else {
				Long paymentType = orderPayment.getPayment().getType().getId();
				if (paymentType.equals(Code.PAYMENT_TYPE_COD)) {
					deliveryMoney = order.getRequidPayMoney();
					orderPayment.setReturnMoney(payMoney.subtract(deliveryMoney));
				}
			}
			orderPayment.setDeliveryMoney(deliveryMoney);
		}
	}

	@Override
	public void receive(OrderReceive receive) throws OrderStatusException {
		Order order = receive.getOrder();
		Code[] expectedStatus = codeService.find(new Long[] { Code.ORDER_PROCESS_STATUS_DELIVERIED,
				Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG });
		Code actualStatus = order.getProcessStatus();
		if (!ArrayUtils.contains(expectedStatus, actualStatus)) {
			throw new OrderStatusException(order, expectedStatus);
		}
		Customer customer = order.getCustomer();
		customer.setLastTradeTime(new Date());
		// 更改CreateTime
		receive.setCreateTime(new Date());
		orderReceiveDao.save(receive);
		// 更改order状态为交易成功状态
		order.addStatusLog(new Code(Code.ORDER_PROCESS_STATUS_COMPLETED), customer);
		this.update(order);
		promotionEffect(order);
		customerService.update(customer);
		// 赠送积分
		Long source = receive.getSource().getId();
		CustomerPointsRule customerPointsRule = null;
		if (Code.ORDER_CONFIRM_TYPE_PERSON.compareTo(source) == 0) {// 手工确认
			customerPointsRule = customerPointsRuleFactory.createOrderConfirmCustomerPointsRule(customer, order);
			customerPointsRule.addPoints();
		} else if (Code.ORDER_CONFIRM_TYPE_SYSTEM_AUTO.compareTo(source) != 0) {// 系统自动确认
			throw new UnsupportedOperationException();
		}
		customerPointsRule = customerPointsRuleFactory.createOrderCustomerPointsRule(customer, order);
		customerPointsRule.addPoints();
	}

	/**
	 * 确认收货： 1.自提 && 未完全支付 的订单：确认收货只改变订单的交易状态为未完成 2.其他订单，按照之前的逻辑进行
	 * 
	 * @param newOrder
	 * @param operator
	 */
	@Override
	public void updateForConfirmGotGoods(Order newOrder, User operator) {
		if (newOrder.getDeliveryType().getId().equals(DeliveryType.DIY)
				&& !newOrder.getPaymentStatus().getId().equals(Code.ORDER_PAY_STATUS_COMPLETED)) {
			String oldStatus = codeService.get(newOrder.getProcessStatus().getId()).getName();
			String newStatus = codeService.get(Code.ORDER_PROCESS_STATUS_COMPLETED).getName();
			newOrder.setProcessStatus(codeService.get(Code.ORDER_PROCESS_STATUS_COMPLETED));
			orderDao.update(newOrder);
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.ORDER_STATE, oldStatus,
					newStatus);
			orderUpdateLogDao.save(log);
		} else {
			this.update(newOrder, operator);
		}
	}

	@Override
	public void update(Order newOrder, User operator) {
		OrderConsignee newOrderConsignee = newOrder.getConsignee();
		Set<OrderPayment> newset = newOrder.getPaymentList();
		Iterator<OrderPayment> newIterator = newset.iterator();
		Hibernate.initialize(newOrderConsignee);
		// orderDao.evict(newOrder);
		Order oldOrder = get(newOrder.getId());
		OrderConsignee oldOrderConsignee = oldOrder.getConsignee();
		String oldConsignee = oldOrderConsignee.getConsignee();
		String newConsignee = newOrderConsignee.getConsignee();
		if (oldConsignee != null && !oldConsignee.equals(newConsignee) || newConsignee != null
				&& !newConsignee.equals(oldConsignee)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.CONSIGNEE, oldConsignee,
					newConsignee);
			orderUpdateLogDao.save(log);
		}
		String oldPhone = oldOrderConsignee.getPhone();
		String newPhone = newOrderConsignee.getPhone();
		if (oldPhone != null && !oldPhone.equals(newPhone) || newPhone != null && !newPhone.equals(oldPhone)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.PHONE, oldPhone, newPhone);
			orderUpdateLogDao.save(log);
		}
		String oldMobile = oldOrderConsignee.getMobile();
		String newMobile = newOrderConsignee.getMobile();
		if (oldMobile != null && !oldMobile.equals(newMobile) || newMobile != null && !newMobile.equals(oldMobile)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.MOBILE, oldMobile, newMobile);
			orderUpdateLogDao.save(log);
		}
		String oldEmail = oldOrderConsignee.getEmail();
		String newEmail = newOrderConsignee.getEmail();
		if (oldEmail != null && !oldEmail.equals(newEmail) || newEmail != null && !newEmail.equals(oldEmail)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.EMAIL, oldEmail, newEmail);
			orderUpdateLogDao.save(log);
		}
		String oldZipCode = oldOrderConsignee.getZipCode();
		String newZipCode = newOrderConsignee.getZipCode();
		if (oldZipCode != null && !oldZipCode.equals(newZipCode) || newZipCode != null
				&& !newZipCode.equals(oldZipCode)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.ZIPCODE, oldZipCode, newZipCode);
			orderUpdateLogDao.save(log);
		}
		Area oldProvince = oldOrderConsignee.getProvince();
		Area newProvince = newOrderConsignee.getProvince();
		if (!oldProvince.equals(newProvince)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.PROVINCE, oldProvince.getName(),
					newProvince.getName());
			orderUpdateLogDao.save(log);
		}
		Area oldCity = oldOrderConsignee.getCity();
		Area newCity = newOrderConsignee.getCity();
		if (!oldCity.equals(newCity)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.CITY, oldCity.getName(),
					newCity.getName());
			orderUpdateLogDao.save(log);
		}
		Area oldTown = oldOrderConsignee.getTown();
		Area newTown = newOrderConsignee.getTown();
		if (!oldTown.equals(newTown)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.TOWN, oldTown.getName(),
					newTown.getName());
			orderUpdateLogDao.save(log);
		}
		Area oldDistrict = oldOrderConsignee.getDistrict();
		Area newDistrict = newOrderConsignee.getDistrict();
		if (oldDistrict != null && !oldDistrict.equals(newDistrict) || newDistrict != null
				&& !newDistrict.equals(oldDistrict)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.DISTRICT,
					oldDistrict == null ? null : oldDistrict.getName(), newDistrict == null ? null
							: newDistrict.getName());
			orderUpdateLogDao.save(log);
		}
		String oldAddress = oldOrderConsignee.getAddress();
		String newAddress = newOrderConsignee.getAddress();
		if (oldAddress != null && !oldAddress.equals(newAddress) || newAddress != null
				&& !newAddress.equals(oldAddress)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.ADDRESS, oldAddress, newAddress);
			orderUpdateLogDao.save(log);
		}
		DeliveryType oldDeliveryType = oldOrder.getDeliveryType();
		DeliveryType newDeliveryType = newOrder.getDeliveryType();
		if (oldDeliveryType != null && !oldDeliveryType.equals(newDeliveryType) || newDeliveryType != null
				&& !newDeliveryType.equals(oldDeliveryType)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.DELIVERY_TYPE,
					oldDeliveryType == null ? null : oldDeliveryType.getName(), newDeliveryType == null ? null
							: newDeliveryType.getName());
			orderUpdateLogDao.save(log);
		}
		String oldRemark = oldOrderConsignee.getRemark();
		String newRemark = newOrderConsignee.getRemark();
		if (oldRemark != null && !oldRemark.equals(newRemark) || newRemark != null && !newRemark.equals(oldRemark)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.REMARK, oldRemark, newRemark);
			orderUpdateLogDao.save(log);
		}
		boolean oldNeedInvoice = oldOrderConsignee.isNeedInvoice();
		boolean newNeedInvioce = newOrderConsignee.isNeedInvoice();
		if (oldNeedInvoice != newNeedInvioce) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.NEED_INVOICE,
					oldNeedInvoice ? OrderUpdateLog.NEED_INVOICE_TRUE : OrderUpdateLog.NEED_INVOICE_FALSE,
					newNeedInvioce ? OrderUpdateLog.NEED_INVOICE_TRUE : OrderUpdateLog.NEED_INVOICE_FALSE);
			orderUpdateLogDao.save(log);
		}
		Code oldInvoiceType = oldOrderConsignee.getInvoiceType();
		Code newInvoiceType = newOrderConsignee.getInvoiceType();
		if (oldInvoiceType != null && !oldInvoiceType.equals(newInvoiceType) || newInvoiceType != null
				&& !newInvoiceType.equals(oldInvoiceType)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.INVOICE_TYPE,
					oldInvoiceType == null ? null : oldInvoiceType.getName(), newInvoiceType == null ? null
							: newInvoiceType.getName());
			orderUpdateLogDao.save(log);
		}
		String oldInvoiceTitle = oldOrderConsignee.getInvoiceTitle();
		String newInvoiceTitle = newOrderConsignee.getInvoiceTitle();
		if (oldInvoiceTitle != null && !oldInvoiceTitle.equals(newInvoiceTitle) || newInvoiceTitle != null
				&& !newInvoiceTitle.equals(oldInvoiceTitle)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.INVOICE_TITLE, oldInvoiceTitle,
					newInvoiceTitle);
			orderUpdateLogDao.save(log);
		}
		Code oldDeliveryOption = oldOrderConsignee.getDeliveryOption();
		Code newDeliveryOption = newOrderConsignee.getDeliveryOption();
		if (!oldDeliveryOption.equals(newDeliveryOption)) {
			OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.DELIVERY_OPTION,
					oldDeliveryOption.getName(), newDeliveryOption.getName());
			orderUpdateLogDao.save(log);
		}
		// 修改支付方式日志
		Set<OrderPayment> oldset = oldOrder.getPaymentList();
		Iterator<OrderPayment> oldIterator = oldset.iterator();
		while (oldIterator.hasNext()) {
			boolean isNeedLog = false;
			OrderPayment oldOrderPayment = oldIterator.next();
			while (newIterator.hasNext()) {
				OrderPayment newOrderPayment = newIterator.next();
				if (oldOrderPayment.getId().equals(newOrderPayment.getId())) {
					if (oldOrderPayment.getPayment().getId() != newOrderPayment.getPayment().getId()) {
						OrderUpdateLog log = new OrderUpdateLog(operator, newOrder, OrderUpdateLog.PAYMENT,
								oldOrderPayment.getPayment().getName(), newOrderPayment.getPayment().getName());
						orderUpdateLogDao.save(log);
						isNeedLog = true;
						break;
					}
				}
			}
			if (isNeedLog) {
				break;
			}
		}
		orderDao.merge(newOrder);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Order get(String id) {
		return orderDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> find(Map<String, Object> parameters, short sort, Pagination pagination) {
		return orderDao.find(parameters, sort, pagination);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> findNeedConfirmReceiveOrder(Customer customer, Date beginDate) {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("processStatus", new Long[] { Code.ORDER_PROCESS_STATUS_DELIVERIED,
				Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG });
		parameters.put("customer", customer);
		parameters.put("createTime", beginDate);
		return orderDao.find(parameters, null, null);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> findNeedConfirmReceiveOrder(Date startDeliveryTime, Date endDeliveryTime, Pagination pagination,
			boolean needOuterId) {
		// 暂时不考虑多任务处理的情况
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("processStatus", new Long[] { Code.ORDER_PROCESS_STATUS_DELIVERIED,
				Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG });
		parameters.put("startDeliveryTime", startDeliveryTime);
		parameters.put("endDeliveryTime", endDeliveryTime);
		if (needOuterId) {
			return orderDao.find(parameters, null, pagination);
		} else {
			return orderDao.findWithoutOuterId(parameters, pagination);
		}
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> findNeedInviteCommentOrder(Date startCreateTime, Date endCreateTime, Pagination pagination) {
		// 暂时不考虑多任务处理的情况
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("processStatus", Code.ORDER_PROCESS_STATUS_COMPLETED);
		parameters.put("startCreateTime", startCreateTime);
		parameters.put("endCreateTime", endCreateTime);
		return orderDao.findWithoutOuterId(parameters, pagination);
	}

	@Override
	public void addTrack(OrderTrack orderTrack) {
		orderTrackDao.save(orderTrack);
	}

	@Override
	public void fetchLogisticsInfo(Order order) {
		if (order.isAllowedQueryLogistics()) {
			order.setLogisticsList(orderLogisticsInfoFetcher.fetch(order.getDeliveryCompany().getCode(),
					order.getDeliveryCode()));
		}
	}

	@Override
	public void updateStock(Order order) {
		Long status = order.getProcessStatus().getId();

		if (Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM.equals(order.getStorageType().getId())) {
			// 不下传中启
			if (Code.EC2ERP_STATUS_CANCEL.equals(order.getTransferResult().getId())) {
				return;
			}
			if (order.isVirtual()) {
				if (Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(status)) {
					for (OrderItem item : order.getItemList()) {
						productSaleStockService.subStock(item.getProductSale(), item.getPurchaseQuantity());
					}
				}
			} else {
				if (Code.ORDER_PROCESS_STATUS_NEW.equals(status)
						|| Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(status)) {
					for (OrderItem item : order.getItemList()) {
						productSaleStockService.addStockSales(item);
					}
				} else if (status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED)
						|| status.equals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG)
						|| (order.isCanceled() && !status.equals(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL))) {
					for (OrderItem item : order.getItemList()) {
						if (order.isCanceled()) {
							if (Channel.FRANCHISEE.equals(order.getChannel().getId())) {
								productSaleStockService
										.updateLockSales(order.getChannel(), order.getCustomer().getId(),
												item.getProductSale(), -item.getPurchaseQuantity());
							} else {
								productSaleStockService.updateLockSales(order.getChannel(), item.getProductSale(),
										-item.getPurchaseQuantity());
							}
						}
						productSaleStockService.subStockSales(item);
					}
				}
			}
			// 电子书不减库存
		} else if (Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(order.getStorageType().getId())) {
			return;
			// 百货支付减库存
		} else {
			if (Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(status)
					|| Code.ORDER_PROCESS_STATUS_PICKING.equals(status)) {
				for (OrderItem item : order.getItemList()) {
					productSaleStockService.subStock(item.getProductSale(), item.getPurchaseQuantity());
				}
			}
		}
	}

	@Override
	public void increaseStockLockQuantity(OrderItem item) {
		ProductSale productSale = item.getProductSale();
		List<Long> dcs = dcService.findAvailableDc();
		int stock = 0;
		int sale = 0;
		for (Long dc : dcs) {
			ProductSaleStock productSaleStock = productSale.getStockByDc(new Code(dc));
			if (productSaleStock != null) {
				stock += productSale.getSupplyType().getId().equals(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING) ? productSaleStock
						.getVirtual() : productSaleStock.getStock();
				sale += productSaleStock.getSales();
			}
		}
		item.setStockQuantity(stock);
		item.setSaleQuantity(sale);
	}

	/**
	 * 根据复本量计算新订单的最大支付时间 只处理EC官网线上支付的已提交状态订单
	 * 
	 * @param order
	 */
	private void resetOrderMaxPayDate(Order order) {
		if (!order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_NEW)) {
			return;
		}
		Long channelId = order.getChannel().getId();
		Channel rebate = channelService.get(Channel.CHANNEL_REBATE);
		boolean needReset = channelId.equals(Channel.CHANNEL_EC) || rebate.isGrandParent(order.getChannel());
		if (!needReset) {
			return;
		}
		if (!order.isRequidOnlinePay()) {
			return;
		}
		boolean highLock = false;
		Set<OrderItem> itemList = order.getItemList();
		for (OrderItem item : itemList) {
			if (item.getPurchaseQuantity() > MagicNumber.TWENTY) {
				highLock = true;
				break;
			}
		}
		Date now = new Date();
		Date maxPayDate = highLock ? DateUtils.addHours(now, MagicNumber.SIX) : DateUtils.addDays(now, MagicNumber.ONE);
		order.updateMaxPayTime(maxPayDate);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<OrderTrack> findTrack(Map<String, Object> parameters, short sort, Pagination pagination) {
		return orderTrackDao.findOrderTrack(parameters, sort, pagination);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long getCountOfUnaudited() {
		return orderDao.getCountOfUnaudited();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isExistByCustomerAndOrderId(Customer customer, String orderId) {
		return orderDao.isExistByCustomerAndOrderId(customer, orderId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public OrderItem getOrderItem(long id) {
		return orderDao.get(id);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> findByCustomer(Customer customer, int size) {
		return orderDao.findByCustomer(customer, size);
	}

	@Override
	public List<OrderDeliverySplit> findDeliverySplit(Order order) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", order.getId());
		return orderDeliverySplitDao.find(parameters, (short) 1, null);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Order> findByChannelIdAndOuterId(long channelId, String outerId) {
		return orderDao.findByChannelIdAndOuterId(channelId, outerId);
	}

	@Override
	public boolean isExistOuterId(String outerId) {
		if (StringUtils.isBlank(outerId)) {
			return false;
		} else {
			return orderDao.isExistOuterId(outerId);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> findOrderCollectByShop(Shop shop, Map<String, Object> param) {
		List<Map<String, Object>> list = orderDao.findOrderCollectByShop(param);
		List<Object[]> result = new ArrayList<Object[]>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				result.add(map.values().toArray());
			}
		}
		return result;
	}

	@Override
	public List<Order> findForDeliveryCompany(Map<String, Object> parameters, Short sort, Pagination pagination) {
		return this.orderDao.findForDeliveryCompany(parameters, sort, pagination);
	}

	/**
	 * 复制订单
	 */
	@Override
	public Order copyOrder(User user, String orderid) throws OrderCloneException {
		Order oldOrder = get(orderid);
		if (oldOrder == null) {
			throw new OrderCloneException("没有找到相关的订单!");
		}
		if (!oldOrder.isCanBeCopy()) {
			throw new OrderCloneException("该订单不满足复制条件!");
		}
		try {
			Order neworder = cloneNewOrder(oldOrder);
			updateCloneState(user, oldOrder, neworder);
			return neworder;
		} catch (OrderStockException e) {
			Order o = e.getOrder();
			OrderItem orderItem = e.getOrderItem();
			ProductSale productSale = orderItem.getProductSale();
			String msg = "订单(" + o.getId() + ")中商品(" + productSale.getId() + ")需要可用量:"
					+ orderItem.getPurchaseQuantity() + ",实际可用量:" + productSale.getAvalibleQuantity();
			throw new OrderCloneException(msg);
		} catch (CustomerAccountException e) {
			throw new OrderCloneException("使用金额大于暂存款余额");
		} catch (Exception e) {
			throw new OrderCloneException(e.getMessage());
		}
	}

	/**
	 * 更改克隆订单的状态
	 * 
	 * @param user
	 * @param oldorder
	 * @param neworder
	 */
	private void updateCloneState(User user, Order oldorder, Order neworder) {
		// 复制订单日志
		OrderCloneLog clonelog = new OrderCloneLog(user, oldorder, neworder);
		orderDao.saveOrderCloneLog(clonelog);
	}

	private Order cloneNewOrder(Order orign) throws OrderInitException, OrderStockException, CustomerAccountException,
			PresentException, PresentCardException {
		Order newOrder = new Order();
		newOrder.setAdvanceMoney(orign.getAdvanceMoney());
		newOrder.setChannel(orign.getChannel());
		addConsignee(orign, newOrder);
		newOrder.setCreator(orign.getCreator());
		newOrder.setCustomer(orign.getCustomer());
		newOrder.setDeliveryFee(orign.getDeliveryFee());
		newOrder.setDeliveryType(orign.getDeliveryType());
		newOrder.setSaveMoney(orign.getSaveMoney());
		addItemList(orign, newOrder);
		addPayment(orign, newOrder);
		addPromotion(orign, newOrder);
		newOrder.setPayType(orign.getPayType());
		create(newOrder);
		return newOrder;
	}

	private void addPromotion(Order originalOrder, Order newOrder) {
		Set<OrderPromotion> promotions = originalOrder.getPromotionList();
		if (!CollectionUtils.isEmpty(promotions)) {
			for (OrderPromotion oldPromotion : promotions) {
				OrderPromotion newPromotion = new OrderPromotion(oldPromotion.getPromotion());
				newPromotion.setOrder(newOrder);
				newPromotion.setPresentBatch(oldPromotion.getPresentBatch());
				newPromotion.setPresentNum(oldPromotion.getPresentNum());
				newPromotion.setSaveMoney(oldPromotion.getSaveMoney());
				newOrder.addPromotion(newPromotion);
			}
		}
	}

	private void addPayment(Order originalOrder, Order newOrder) {
		Set<OrderPayment> orderPayments = originalOrder.getPaymentList();
		for (OrderPayment orderPayment : orderPayments) {
			OrderPayment payment = new OrderPayment();
			payment.setCreateTime(new Date());
			payment.setOrder(newOrder);
			if (orderPayment.isPrePay()) {
				payment.setPay(true);
				payment.setPayTime(orderPayment.getPayTime());
				payment.setCredential(orderPayment.getCredential());
			} else {
				payment.setPay(false);
			}
			payment.setPayMoney(orderPayment.getPayMoney());
			payment.setPayment(orderPayment.getPayment());
			newOrder.addPayment(payment);
		}
	}

	private void addConsignee(Order originalOrder, Order newOrder) {
		OrderConsignee originalConsignee = originalOrder.getConsignee();
		OrderConsignee newc = new OrderConsignee();
		newc.setAddress(originalConsignee.getAddress());
		newc.setCity(originalConsignee.getCity());
		newc.setConsignee(originalConsignee.getConsignee());
		newc.setCountry(originalConsignee.getCountry());
		newc.setDeliveryOption(originalConsignee.getDeliveryOption());
		newc.setDistrict(originalConsignee.getDistrict());
		newc.setTown(originalConsignee.getTown());
		newc.setEmail(originalConsignee.getEmail());
		newc.setMobile(originalConsignee.getMobile());
		newc.setOrder(newOrder);
		newc.setOutOfStockOption(originalConsignee.getOutOfStockOption());
		newc.setPhone(originalConsignee.getPhone());
		newc.setProvince(originalConsignee.getProvince());
		newc.setZipCode(originalConsignee.getZipCode());
		newOrder.setConsignee(newc);
	}

	private void addItemList(Order originalOrder, Order newOrder) {
		BigDecimal originalItemPrice = originalOrder.getSalePriceByItems();
		BigDecimal ignorePrice = originalItemPrice.subtract(originalOrder.getSalePrice());
		ignorePrice = ignorePrice.compareTo(BigDecimal.ZERO) > 0 ? ignorePrice : BigDecimal.ZERO;
		for (OrderItem orderItem : originalOrder.getItemList()) {
			OrderItem item = new OrderItem();
			if (ignorePrice.compareTo(BigDecimal.ZERO) > 0 && ignorePrice.compareTo(orderItem.getSalePrice()) < 0) {
				item.setSalePrice(orderItem.getSalePrice().subtract(ignorePrice));
				ignorePrice = BigDecimal.ZERO;
			} else {
				item.setSalePrice(orderItem.getSalePrice());
			}
			item.setListPrice(orderItem.getListPrice());
			item.setProductSale(orderItem.getProductSale());
			item.setPurchaseQuantity(orderItem.getPurchaseQuantity());
			item.setShop(orderItem.getProductSale().getShop());
			newOrder.addItem(item);
		}
	}

	@Override
	public void saveOrderExtend(OrderExtend orderExtend) {
		orderDao.saveOrderExtend(orderExtend);
	}

	@Override
	public List<Order> findOrderWithMeta(Map<String, Object> parameters, Short sort, Pagination pagination) {
		List<Order> orders = new ArrayList<Order>();
		if (parameters.get("meta") == null && parameters.get("nmeta") == null) {
			orders = orderDao.findOrderWithMeta(parameters, sort, pagination);
		} else {
			orders = orderDao.findOrderWithMeta1(parameters, sort, pagination);
		}
		return orders;
	}

	@Override
	public int getPurchaseQuantityToday(Customer customer, ProductSale ps) {
		return ((Long) orderDao.getPurchaseQuantityToday(customer.getId(), ps.getId())).intValue();
	}

	@Override
	public int getPurchaseQuantityToday(ProductSale ps) {
		return ((Long) orderDao.getPurchaseQuantityToday(null, ps.getId())).intValue();

	}

	@Override
	public void batchDelivery(List<OrderPackage> packages, Employee operator) throws BatchDeliveryException {
		// 业务验证
		for (OrderPackage op : packages) {
			Order o = op.getOrder();
			if (op.getOrder() == null) {
				throw new BatchDeliveryException(String.format("订单不存在.请确认发货参数"));
			} else if (!Code.EC2ERP_STATUS_CANCEL.equals(o.getTransferResult().getId())) {
				throw new BatchDeliveryException(String.format("订单:%s 是下传到中启的订单!!.", o.getId()));
			} else {

				for (DeliveryProductSale dp : op.getProductSaleSet()) {

					OrderItem oi = o.getItemBySAP(dp.getSapCode());
					if (oi == null) {
						throw new BatchDeliveryException(String.format("订单:%s SAP[%s]编码没有找到.", o.getId(),
								dp.getSapCode()));
					} else if (dp.getDeliveryQuantity() > oi.getPurchaseQuantity()) {
						throw new BatchDeliveryException(String.format(
								"订单号:%s,  商品SAP编码:%s ,发货数大于购买数量,  购买数量%s,  发货数量%s", o.getId(), dp.getSapCode(),
								oi.getPurchaseQuantity(), dp.getDeliveryQuantity()));
					} else {
						oi.setDeliveryQuantity(dp.getDeliveryQuantity());
					}
				}
			}
		}
		// 批量发货
		for (OrderPackage op : packages) {
			try {
				this.delivery(op.getOrder(), op.getDeliveryCompany(), op.getDeliveryCode(), operator);
			} catch (Exception e) {
				log.error("批量发货[ERROR]", e);
				throw new BatchDeliveryException(e.getMessage());
			}
		}
	}

	@Override
	public void cancelPresell(Order order, Employee employee) throws OrderPresellException {
		if (!Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(order.getSupplyType().getId())) {
			throw new OrderPresellException(order, "该订单不是预售订单!");
		}
		Code newSupplyType = codeService.get(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL);
		OrderUpdateLog log = new OrderUpdateLog(employee, order, "供应类型", order.getSupplyType().getName(),
				newSupplyType.getName());
		order.setSupplyType(codeService.get(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
		order.addUpdateLog(log);
		orderDao.save(order);
	}

	/**
	 * 广播订单状态
	 * 
	 * @param order
	 */
	private void broadcastOrderStatus(Order order) {
		// 兼容其他系统没有注入该Service
		if (orderStatusService == null) {
			log.warn("订单广播失败!orderStatusService 没有注入");
			return;
		}

		if (!CollectionUtils.isEmpty(order.getBroadCastList())) {
			orderStatusService.broadcastOrderStatus(order.getId(), order.getBroadCastList());
			order.getBroadCastList().clear();
		}
	}

	/**
	 * 订单有任何更新.广播出去
	 * 
	 * @param order
	 *            by.cat911
	 */
	private void update(Order order) {
		this.orderDao.update(order);
		this.broadcastOrderStatus(order);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findBookingOrderId(int start, int size) throws OrderPresellException {
		return this.orderDao.findBookingOrderId(start, size);
	}

	@Override
	public void saveOrderPackage(OrderPackges orderPackges) {
		this.orderDao.save(orderPackges);
	}

	@Override
	public OrderPackges getOrderPackges(String orderid) {
		return orderDao.getOrderPackges(orderid);
	}

	public String getStatusByErp(String orderId) {
		return erpService.getStatusByOrder(orderId);
	}

	public String erpInsertIntercept(String orderId) {
		return wmsService.insertIntercept(orderId);
	}

	@Override
	public void initOrderStock(Order order) throws OrderStockException, OrderDcMatchException {
		if (order.getOrderInit() != null && Code.ORDER_INIT_WAITING.equals(order.getOrderInit().getStatus().getId())) {
			orderDcService.saveOrderItemStock(order);// 记录下单订单快照
			OrderDistributionCenter odc = orderDcService.createOrderDcNew(order);
			collectionItemService.createCollectionItem(order, odc);
			updateStock(order);// 更新库存
			completeOrderInit(order);
		} else {
			log.info(order.getId() + " 已经进行过库存初始化");
		}

	}

	private void completeOrderInit(Order order) {
		OrderInit orderInit = order.getOrderInit();
		orderInit.setStatus(new Code(Code.ORDER_INIT_COMPLETE));
		orderInitService.update(orderInit);
		orderDao.update(order);
	}

	@Override
	public List<String> getNeedInitOrders(Pagination pagination) {
		return orderDao.getInitOrders(pagination, Code.ORDER_INIT_WAITING);
	}

	private void setOrderCollection(Order order) {
		OrderCollection oc = order.getOrderCollection();
		if (oc != null) {
			if (!isCollection) {
				oc.setCollect(isCollection);
				oc.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_CONFIGURE));// 系统配置
			} else {
				oc.setStrategy(new Code(Code.ORDER_STRATEGY_OUTER_APPOINT));// 外部指定
			}
		} else {
			oc = new OrderCollection(order, isCollection);
			oc.setStrategy(new Code(Code.ORDER_STRATEGY_SYSTEM_CONFIGURE)); // 系统配置
			order.setOrderCollection(oc);
		}

	}

	@Override
	public void initProductSaleStockVo(Order order) throws RemoteBusinessException {
		Set<OrderItem> items = order.getItemList();
		if (org.apache.commons.collections.CollectionUtils.isEmpty(items)) {
			return;
		}

		Set<Long> productSaleIds = new HashSet<Long>();
		for (OrderItem item : items) {
			productSaleIds.add(item.getProductSale().getId());
		}

		Map<Long, Set<ProductSaleStockVo>> pssvMap = remoteStockService.searchProductSaleStocks(productSaleIds);

		for (OrderItem item : items) {
			item.setProductSaleStockVos(pssvMap.get(item.getProductSale().getId()));
		}
	}

	@Override
	public void extendOrder(Order order, Employee employee) throws OrderStatusException {
		if (order == null) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT) });
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT)) {
			return;
		}
		if (order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)
				&& order.getTransferResult().getId().equals(Code.EC2ERP_STATUS_NEW)) {
			if (!employee.getId().equals(Employee.SYSTEM)) {
				OrderExtend extend = new OrderExtend();
				extend.setOrder(order);
				extend.setMeta(OrderExtend.CHANNEL_FLAG);
				extend.setValue("1");
				saveOrderExtend(extend);
			}
			this.update(order);
		}
	}

	@Override
	public void auditShipperInfo(Order order, Employee operator) throws OrderStatusException {
		Code processStatus = order.getProcessStatus();
		if (!Code.ORDER_PROCESS_STATUS_NEW.equals(processStatus.getId())
				&& !Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(processStatus.getId())) {
			throw new OrderStatusException(order, new Code[] { new Code(Code.ORDER_PROCESS_STATUS_NEW),
					new Code(Code.ORDER_PROCESS_STATUS_WAITING_PICKING) });
		}
		this.update(order);
	}

}
