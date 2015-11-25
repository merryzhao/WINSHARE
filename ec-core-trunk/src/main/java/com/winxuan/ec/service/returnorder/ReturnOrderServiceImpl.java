/*
 * @(#)ReturnOrderServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.returnorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ReturnOrderDao;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderDc;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackage;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageItem;
import com.winxuan.ec.model.returnorder.ReturnOrderPackageRelate;
import com.winxuan.ec.model.returnorder.ReturnOrderTag;
import com.winxuan.ec.model.returnorder.ReturnOrderTrack;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.customer.points.CustomerPointsRule;
import com.winxuan.ec.service.customer.points.CustomerPointsRuleFactory;
import com.winxuan.ec.service.finance.FinanceService;
import com.winxuan.ec.service.order.OrderMessageService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.BigDecimalUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-14
 */
@Service("returnOrderService")
@Transactional(rollbackFor = Exception.class)
public class ReturnOrderServiceImpl implements ReturnOrderService,Serializable {

	private static final long serialVersionUID = -3338109443658760439L;

	private static final Log LOG = LogFactory.getLog(ReturnOrderServiceImpl.class);
	//联盟
	private static final Long CODE_PARENT_LIANMENG = 700L;
	//文轩、代理
	private static final Long CODE_PARENT_DAILI = 1001L;
	//退货订单、标签、原包非整退
    private static final Long RETURN_ORDER_TAG_570002 = 570002L;
    //退货订单、标签、货到付款退货
    private static final Long RETURN_ORDER_TAG_570003 = 570003L;
	//退货订单、是否应该退款、不应该退款
    private static final Long IS_SHOULD_REFUND_580001 = 580001L;
    //退货订单、是否应该退款、待业务确定
    private static final Long IS_SHOULD_REFUND_580002 = 580002L;
    //退货订单、是否应该退款、应该退款
    private static final Long IS_SHOULD_REFUND_580003 = 580003L;
    //退货订单、是否应该退款、待定已退
    private static final Long IS_SHOULD_REFUND_580004 = 580004L;
    //退货订单、是否应该退款、待定不退
    private static final Long IS_SHOULD_REFUND_580005 = 580005L;

	@InjectDao
	private ReturnOrderDao returnOrderDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PresentCardService presentCardService;

	@Autowired
	private PresentService presentService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerPointsRuleFactory customerPointsRuleFactory;
	
	@Autowired
	private OrderMessageService orderMessageService;
	
	@Autowired
	private FinanceService financeService;
	
	@Autowired
	private CodeService codeService;

	@Override
	public ReturnOrder create(String originalOrderId, User creator)
			throws ReturnOrderException {
		Order order = orderService.get(originalOrderId);
		if(order != null){
			if(0 != Code.ORDER_PROCESS_STATUS_ERP_CANCEL.compareTo(order.getProcessStatus().getId())){
				throw new ReturnOrderException(order, "订单状态不是‘拦截取消’，不能创建销退单！");
			}
			ReturnOrder returnOrder = new ReturnOrder();
			
			returnOrder.setType(codeService.get(24001l));//类型:退货
			returnOrder.setResponsible(codeService.get(27001l));//默认责任方为客户
			returnOrder.setHolder(codeService.get(27001l));//默认承担方为客户
			returnOrder.setPickup(codeService.get(28002l));//退换货方式：上门取件
			returnOrder.setReason(codeService.get(30001l));//原因：个人喜欢
			returnOrder.setRefundDeliveryFee(order.getDeliveryFee());//付退运费
			returnOrder.setRefundCompensating(new BigDecimal(0.00));//付退金额
			returnOrder.setRefundCouponValue(new BigDecimal(0.00));//付退礼券金额
			returnOrder.setRemark("拦截失败，未发运");
			
			BigDecimal  returnAmount = new BigDecimal(0);
			for (OrderItem item : order.getItemList()) {
				//退货数量，默认整单退
				Integer quantity  = item.getCanReturnQuantity();
				returnOrder.addItem(item,quantity);
				//商品实洋乘以退货数量
				BigDecimal amount = item.getBalancePrice().multiply(new BigDecimal(quantity));
				//退货金额相加
				returnAmount = returnAmount.add(amount);
			}
    		//付退运费
    		BigDecimal deliveryfee = order.getDeliveryFee();
    		//加上付退运费
    		returnAmount = returnAmount.add(deliveryfee);
    		//加入退货金额
    		returnOrder.setRefundGoodsValue(returnAmount);
    		//拦截失败，未发运 ，原则上都是应该退款
    		returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580003));
			
			returnOrder.setCustomer(order.getCustomer());
			returnOrder.setOriginalOrder(order);
			returnOrder.setCreator(creator);
			returnOrder.setCreateTime(new Date());
			returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_NEW), creator);
			ReturnOrderDc returnOrderDc = new ReturnOrderDc();
			returnOrderDc.setRetrunOrder(returnOrder);
			returnOrderDc.setTargetDc(order.getDistributionCenter().getDcDest());
			returnOrder.setReturnOrderDc(returnOrderDc);
			returnOrderDao.save(returnOrder);
			return returnOrder;
		}
		return null;
	}

	@Override
	public void create(ReturnOrder returnOrder, Order originalOrder,
			User creator) throws ReturnOrderException{
		checkNewReturnOrderStatus(returnOrder,originalOrder);
		returnOrder.setCustomer(originalOrder.getCustomer());
		Set<ReturnOrderItem> itemList = returnOrder.getItemList();
		Long typeId = returnOrder.getType().getId();
		if (typeId.equals(Code.RETURN_ORDER_TYPE_RETURN)
				|| typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE)) {
			if (itemList == null || itemList.isEmpty()) {
				throw new ReturnOrderException(returnOrder,"itemList must be not empty");
			}
			for(ReturnOrderItem returnOrderItem : itemList){
				OrderItem orderItem = returnOrderItem.getOrderItem();
				int canReturnQuantity = orderItem.getDeliveryQuantity() - orderItem.getReturnQuantity();
				if(returnOrderItem.getAppQuantity() > canReturnQuantity){
					throw new ReturnOrderException(returnOrder,"itemList quantity too large");
				}else{
					orderItem.setReturnQuantity(orderItem.getReturnQuantity() + returnOrderItem.getAppQuantity());
				}
			}
			returnOrder.setRefundCompensating(BigDecimal.ZERO);
			returnOrder.setRefundCouponValue(BigDecimal.ZERO);
		} else {
			if (itemList != null && !itemList.isEmpty()) {
				throw new ReturnOrderException(returnOrder,"itemList must be empty");
			}
		}
		returnOrder.setOriginalOrder(originalOrder);
		returnOrder.setCreator(creator);
		returnOrder.setCreateTime(new Date());
		returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_NEW), creator);
		ReturnOrderDc returnOrderDc = new ReturnOrderDc();
		returnOrderDc.setRetrunOrder(returnOrder);
		returnOrderDc.setTargetDc(originalOrder.getDistributionCenter().getDcDest());
		returnOrder.setReturnOrderDc(returnOrderDc);
		returnOrderDao.save(returnOrder);
		//根据是否应该退款，写入相应的returnOrderTag
		if(returnOrder.getShouldrefund().getId().equals(IS_SHOULD_REFUND_580001)){
			saveReturnOrderTag(returnOrder, RETURN_ORDER_TAG_570002);
		}else if(returnOrder.getShouldrefund().getId().equals(IS_SHOULD_REFUND_580002)){
			saveReturnOrderTag(returnOrder, RETURN_ORDER_TAG_570003);
		}
	}


	private void checkNewReturnOrderStatus(ReturnOrder returnOrder,Order origOrder) throws ReturnOrderException{
		Long typeId = returnOrder.getType().getId();
		if(typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE) || 
				typeId.equals(Code.RETURN_ORDER_TYPE_RETURN)){
			if (!origOrder.isDeliveried()) {
				throw new ReturnOrderException(returnOrder,"销售订单未发货,不能创建退货/换货!");
			}
			if(returnOrder.getRefundDeliveryFee() == null){
				returnOrder.setRefundDeliveryFee(BigDecimal.ZERO);
			}
		}
	}

	@Override
	public void audit(ReturnOrder returnOrder, User auditor) throws ReturnOrderException {
		if (returnOrder.getOriginalOrder() == null || !returnOrder.getStatus().getId().equals(Code.RETURN_ORDER_STATUS_NEW)) {
			throwStatusErrorException();
		}
		Long typeId = returnOrder.getType().getId();
		if(typeId.equals(Code.RETURN_ORDER_TYPE_RETURN) || typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE)){
			if(returnOrder.getOriginalOrder().getDistributionCenter().getDcDest().getId().equals(Code.DC_8A17)){				
				if(!(returnOrder.getOriginalOrder().isDeliveried() 
						|| returnOrder.getOriginalOrder().getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_ERP_CANCEL))){
					throwStatusErrorException();
				}
			}else{
				if(!(returnOrder.getOriginalOrder().isDeliveried())){
					throwStatusErrorException();
				}
			}
		}
		returnOrder.setAuditor(auditor);
		returnOrder.setAuditTime(new Date());
		returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_AUDITED), auditor);
		if(typeId.equals(Code.RETURN_ORDER_TYPE_RETURN) 
				|| typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE)){
			if(returnOrder.getOriginalOrder().getStorageType().getId()
					.equals(Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER)
					//根据原始订单判断是否下传中启
					|| returnOrder.getOriginalOrder().getTransferResult().getId().equals(Code.EC2ERP_STATUS_CANCEL)
					|| returnOrder.getOriginalOrder().getTransferResult().getId().equals(Code.EC2SAP_STATUS_NEW)
			){
				returnOrder.setNeedtransfers(false);
				returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_RETURNING), auditor);
			}else{
				returnOrder.setNeedtransfers(true);
			}
		}else if(typeId.equals(Code.RETURN_ORDER_TYPE_COMPENSATE) || typeId.equals(Code.RETURN_ORDER_TYPE_COMPENSATE_BOOK)){
			refund(returnOrder, auditor);
		}
		returnOrderDao.update(returnOrder);
	}

	@Override
	public void cancel(ReturnOrder returnOrder, User auditor) {
		if (!returnOrder.getStatus().getId()
				.equals(Code.RETURN_ORDER_STATUS_NEW)) {
			throwStatusErrorException();
		}
		for(ReturnOrderItem returnOrderItem : returnOrder.getItemList()){
			OrderItem orderItem = returnOrderItem.getOrderItem();
			orderItem.setReturnQuantity(orderItem.getReturnQuantity() - returnOrderItem.getAppQuantity());
		}
		returnOrder.setAuditor(auditor);
		returnOrder.setAuditTime(new Date());
		returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_CANCEL), auditor);
		returnOrderDao.update(returnOrder);
	}

	@Override
	public void startReturn(ReturnOrder returnOrder, User operator) {
		Long typeId = returnOrder.getType().getId();
		if (!typeId.equals(Code.RETURN_ORDER_TYPE_RETURN)
				&& !typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE)) {
			throw new RuntimeException("returnOrder type error");
		}
		if (!returnOrder.getStatus().getId() 
				.equals(Code.RETURN_ORDER_STATUS_AUDITED)) {
			throwStatusErrorException();
		}
		returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_RETURNING),operator);
	    returnOrder.setTransferred(true);
		returnOrderDao.update(returnOrder);
	}

	@Override
	public List<OrderPayment> completeReturn(ReturnOrder returnOrder, boolean refund,
			User operator) throws ReturnOrderException {
		List<OrderPayment> orderPaymentList = null;
		if (!returnOrder.getStatus().getId()
				.equals(Code.RETURN_ORDER_STATUS_RETURNING)) {
			throwStatusErrorException();
		}
		returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_RECEIVED),
				operator);
		returnOrderDao.update(returnOrder);
		if(refund){
			orderPaymentList = refund(returnOrder, operator);
		}
		//退货成功后扣减积分
		CustomerPointsRule customerPointsRule = customerPointsRuleFactory
		.createReturnOrderCustomerPointsRule(returnOrder.getOriginalOrder());
		customerPointsRule.addPoints();
		return orderPaymentList;
	}

	@Override
	public List<OrderPayment> completeReturn(ReturnOrder returnOrder, User operator) throws ReturnOrderException {
		if(returnOrder.getShouldrefund().getId().equals(IS_SHOULD_REFUND_580003)){
			return completeReturn(returnOrder,true,operator);
		}else{			
			return completeReturn(returnOrder,false,operator);
		}
	}

	@Override
	public List<OrderPayment> refund(ReturnOrder returnOrder, User operator) throws ReturnOrderException {
		List<OrderPayment> orderPaymentList = null;
		Long typeId = returnOrder.getType().getId();
		Long statusId = returnOrder.getStatus().getId();
		if (typeId.equals(Code.RETURN_ORDER_TYPE_RETURN)
				|| typeId.equals(Code.RETURN_ORDER_TYPE_REPLACE)) {
			if (!statusId.equals(Code.RETURN_ORDER_STATUS_RECEIVED)) {
				throwStatusErrorException();
			}
		} else {
			if (!statusId.equals(Code.RETURN_ORDER_STATUS_AUDITED)) {
				throwStatusErrorException();
			}
		}
		try{
			if(Code.RETURN_ORDER_TYPE_COMPENSATE.equals(typeId) || Code.RETURN_ORDER_TYPE_COMPENSATE_BOOK.equals(typeId)){
				if(returnOrder.getOriginalOrder().isOffPeriod()){
					throw new ReturnOrderException(returnOrder,"账期支付订单不能开补偿和书款补偿!!");
				}
				doCompensateRefund(returnOrder, operator);
				returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_REFUNDED),operator);
			}
			else if(Code.RETURN_ORDER_TYPE_RETURN.equals(typeId) || Code.RETURN_ORDER_TYPE_REPLACE.equals(typeId)){
				//只有是否应该退款状态为“应该”或者“待定”的可以执行退款操作
				Long isShouldRefund = returnOrder.getShouldrefund().getId();
				if(isShouldRefund.equals(IS_SHOULD_REFUND_580002)||isShouldRefund.equals(IS_SHOULD_REFUND_580003)){
					orderPaymentList = doReturnRefund(returnOrder, operator);
					returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_REFUNDED),operator);
					financeService.processReturnFinance(returnOrder, new Date());
				}
				if(isShouldRefund.equals(IS_SHOULD_REFUND_580002)){
					returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580004));
				}
			}
			returnOrder.setRefundTime(new Date());
			returnOrder.setRefunder(operator);
			returnOrder.setRefunded(true);
			returnOrderDao.update(returnOrder);
			
			orderMessageService.returnedPurchase(returnOrder);
			
		}catch (PresentCardException e) {
			throw new ReturnOrderException(returnOrder,e.getMessage());
		} catch (CustomerAccountException e) {
			throw new ReturnOrderException(returnOrder,e.getMessage());
		}
		return orderPaymentList;
	}


	/**
	 * 订单退换货类型 - 补偿 换货
	 * @throws ReturnOrderException 
	 * @throws  Exception 
	 */
	public void createOrder(ReturnOrder returnOrder, List<OrderPayment> orderPaymentList, User operator) throws ReturnOrderException{
		if(!Code.RETURN_ORDER_TYPE_REPLACE.equals(returnOrder.getType().getId())){
			LOG.info(returnOrder.getId() + " : paymentList is null or empty, correct is 24002L , now  type id :" + returnOrder.getType().getId() );
			return;
		}
		if(orderPaymentList == null || orderPaymentList.isEmpty()){
			LOG.info(returnOrder.getId() + " : paymentList is null or empty" );
			return;
		}
		Order originalOrder = returnOrder.getOriginalOrder();

		Order order = new Order();
		BigDecimal advanceMoney = BigDecimal.ZERO;
		for(OrderPayment op : orderPaymentList){
			order.addPayment(op);
			advanceMoney= advanceMoney.add(op.getPayMoney());
		}
		order.setAdvanceMoney(advanceMoney);

		order.setChannel(originalOrder.getChannel());
		addConsignee(originalOrder,order);
		order.setCreator(originalOrder.getCreator());
		order.setCustomer(originalOrder.getCustomer());
		order.setDeliveryFee(BigDecimal.ZERO);
		order.setDeliveryType(originalOrder.getDeliveryType());
		addItemList(returnOrder, order);
		order.setPayType(originalOrder.getPayType());
		if(new Code(Channel.FRANCHISEE).equals(originalOrder.getChannel())){
			order.setOuterId(originalOrder.getOuterId());   //为换货订单设置原始订单的外部订单号
		}
		order.setSaveMoney(calcRefundTotalValue(returnOrder).subtract(advanceMoney));
		try{
			orderService.create(order);
			if(order.useAccountAndUnpaid()){
    			List<Order> listOrders = new ArrayList<Order>();
                listOrders.add(order);
                orderService.payByAccount(listOrders, order.getCustomer(), operator);
			}
			returnOrder.setNewOrder(order);
			returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_CHANGED),operator);
			returnOrderDao.update(returnOrder);
			orderMessageService.replace(returnOrder);
			
		}catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}

	private void addConsignee(Order originalOrder,Order newOrder){
		OrderConsignee originalConsignee = originalOrder.getConsignee();
		OrderConsignee newc = new OrderConsignee();
		newc.setAddress(originalConsignee.getAddress());
		newc.setCity(originalConsignee.getCity());
		newc.setConsignee(originalConsignee.getConsignee());
		newc.setCountry(originalConsignee.getCountry());
		newc.setDeliveryOption(originalConsignee.getDeliveryOption());
		newc.setDistrict(originalConsignee.getDistrict());
		newc.setEmail(originalConsignee.getEmail());
		newc.setMobile(originalConsignee.getMobile());
		newc.setOrder(newOrder);
		newc.setOutOfStockOption(originalConsignee.getOutOfStockOption());
		newc.setPhone(originalConsignee.getPhone());
		newc.setProvince(originalConsignee.getProvince());
		newc.setRemark(originalOrder.getId() +"换货");
		newc.setZipCode(originalConsignee.getZipCode());
		newOrder.setConsignee(newc);
	}

	private void addItemList(ReturnOrder returnOrder, Order newOrder) {
		for (ReturnOrderItem returnOrderItem : returnOrder.getItemList()) {
			OrderItem item = new OrderItem();
			item.setPurchaseQuantity(returnOrderItem.getRealQuantity());
			OrderItem originalItem = returnOrderItem.getOrderItem();
			item.setSalePrice(originalItem.getBalancePrice());
			item.setListPrice(originalItem.getListPrice());
			item.setProductSale(originalItem.getProductSale());
			item.setShop(originalItem.getProductSale().getShop());
			newOrder.addItem(item);
		}
	}

	private OrderPayment getOrderPayment(ReturnOrder returnOrder,BigDecimal money,OrderPayment originalOrderPayment){
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setPayMoney(money);
		orderPayment.setPay(true);
		orderPayment.setCreateTime(new Date());
		orderPayment.setPayment(originalOrderPayment.getPayment());
		orderPayment.setDeliveryMoney(BigDecimalUtils.ZERO);
		orderPayment.setPayTime(new Date());

		if (originalOrderPayment.getCredential() != null) {
			PaymentCredential credential = new PaymentCredential();
			credential
			.setCustomer(returnOrder.getOriginalOrder().getCustomer());
			credential.setMoney(money);
			credential.setOperator(new Employee(Employee.SYSTEM));
			credential.setPayment(originalOrderPayment.getPayment());
			credential.setPayTime(new Date());
			credential.setOuterId(originalOrderPayment.getCredential()
					.getOuterId());
			orderPayment.setCredential(credential);
		}
		return orderPayment;
	}



	/**
	 * 订单退换货类型 - 补偿 退款、书款补偿退款
	 */
	private void doCompensateRefund(ReturnOrder returnOrder, User operator) throws ReturnOrderException{
		if(!Code.RETURN_ORDER_TYPE_COMPENSATE.equals(returnOrder.getType().getId())
				&& !Code.RETURN_ORDER_TYPE_COMPENSATE_BOOK.equals(returnOrder.getType().getId())){
			throw new ReturnOrderException(returnOrder,"该退货单不是补偿退货！");
		}
		Order order = returnOrder.getOriginalOrder();
		if(order.getPayment(Payment.CHANNEL) != null || order.getPayment(Payment.OFF_PERIOD)!= null){
			LOG.info(returnOrder.getId()+":"+order.getId()+"补偿不退入暂存款");
			return;
		}
		try {
			if(returnOrder.getRefundCompensating().compareTo(BigDecimal.ZERO) > 0){
				customerService.useAccount(order.getCustomer(),returnOrder.getRefundCompensating(),order,
						operator,new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
			}
			BigDecimal couponValue = returnOrder.getRefundCouponValue();
			if(couponValue != null && couponValue.compareTo(BigDecimal.ZERO) > 0){
				presentService.reissuePresent(returnOrder.getCustomer(), couponValue);
			}
		} catch (CustomerAccountException e) {
			throw new ReturnOrderException(returnOrder, e.getMessage());
		} catch (PresentException e) {
			throw new ReturnOrderException(returnOrder, e.getMessage());
		}
	}


	private List<OrderPayment> doReturnRefund(ReturnOrder returnOrder, User operator) throws ReturnOrderException, CustomerAccountException, PresentCardException{
		List<OrderPayment> list = new ArrayList<OrderPayment>();
		Order order = returnOrder.getOriginalOrder();

		returnOrder.setRefundGoodsValue(calcRefundGoodsValue(returnOrder));
		BigDecimal refund = calcRefundTotalValue(returnOrder);
		if (refund.compareTo(BigDecimal.ZERO) >= 0) {
			List<OrderPayment> paymentList = order.getRefundSortedPaymentList();
			if (paymentList != null && !paymentList.isEmpty()) {
				BigDecimal accountReturnMoney = BigDecimal.ZERO;

				OrderPayment onlinePayOrderPayment = order.getOnlinePayPayment();
				if(onlinePayOrderPayment != null){
					Payment onlinePayPayment = onlinePayOrderPayment.getPayment();
					Long onlinePayPaymentId = onlinePayOrderPayment.getId();
					BigDecimal onlinePayReturnedMoney = onlinePayOrderPayment.getReturnMoney() == null ? BigDecimal.ZERO : onlinePayOrderPayment.getReturnMoney();
					BigDecimal onlinePayMoney = onlinePayOrderPayment.getPayMoney().subtract(onlinePayReturnedMoney);
					BigDecimal onlineSubRefund = refund.compareTo(onlinePayMoney) > 0 ? onlinePayMoney : refund;
					if(onlinePayMoney.compareTo(BigDecimal.ZERO) > 0 && refund.compareTo(BigDecimal.ZERO) > 0){
						if(!Payment.CHANNEL.equals(onlinePayPaymentId) && !Payment.OFF_PERIOD.equals(onlinePayPaymentId)){
							/*try {
								refundService.refund2Card(order, onlineSubRefund);
							} catch (UnsupportBankException e) {
								LOG.info(e.getMessage(),e);
								accountReturnMoney = accountReturnMoney.add(onlineSubRefund);
							} catch (RefundExpiredException e) {
								LOG.info(e.getMessage(),e);
								accountReturnMoney = accountReturnMoney.add(onlineSubRefund);
							}*/
							accountReturnMoney = accountReturnMoney.add(onlineSubRefund);
						}
						refund = refund.subtract(onlineSubRefund);
						onlinePayOrderPayment.setReturnMoney(onlinePayOrderPayment.getReturnMoney().add(onlineSubRefund));
						returnOrder.addRefund(onlinePayPayment, onlineSubRefund, null);
					}
				}
				
				
				
				for (OrderPayment orderPayment : paymentList) {
					Payment payment = orderPayment.getPayment();
					Long paymentId = payment.getId();
					BigDecimal returnedMoney = orderPayment.getReturnMoney() == null ? BigDecimal.ZERO : orderPayment.getReturnMoney();
					BigDecimal payMoney = orderPayment.getPayMoney().subtract(returnedMoney);
					if(payMoney.compareTo(BigDecimal.ZERO) > 0 && refund.compareTo(BigDecimal.ZERO) > 0){
						if(Payment.COUPON.equals(paymentId) 
								|| Payment.GIFT_CARD.equals(paymentId)
								|| Code.PAYMENT_TYPE_ONLINE.equals(payment.getType().getId())){
							continue;
						}else{
							BigDecimal subRefund = refund.compareTo(payMoney) > 0 ? payMoney : refund;
							if(!Payment.CHANNEL.equals(paymentId) && !Payment.OFF_PERIOD.equals(paymentId)){
								accountReturnMoney = accountReturnMoney.add(subRefund);
							}else{
								OrderPayment op = getOrderPayment(returnOrder, subRefund, orderPayment);
								list.add(op);
							}
							refund = refund.subtract(subRefund);
							orderPayment.setReturnMoney(orderPayment.getReturnMoney().add(subRefund));
							returnOrder.addRefund(payment, subRefund, null);
						}
					}
				}
				if(accountReturnMoney.compareTo(BigDecimal.ZERO) > 0){
					customerService.useAccount(order.getCustomer(),accountReturnMoney,order,
							operator,new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_REFUND));
					Payment payment = new Payment();
					payment.setId(Payment.ACCOUNT);
					payment.setType(new Code(Code.PAYMENT_TYPE_ACCOUNT));
					PaymentCredential pc = new PaymentCredential();
					OrderPayment op = new OrderPayment();
					op.setPayment(payment);
					op.setCredential(pc);
					OrderPayment orderPayment = getOrderPayment(returnOrder, accountReturnMoney, op);
					//getOrderPayment为通用方法:里面设置成了true.暂存款的支付逻辑单独提出来了.所以orderPayment设置为false
					orderPayment.setPay(false);
					list.add(orderPayment);
				}

				for (OrderPayment orderPayment : paymentList) {
					Payment payment = orderPayment.getPayment();
					Long paymentId = payment.getId();
					BigDecimal returnedMoney = orderPayment.getReturnMoney() == null ? BigDecimal.ZERO : orderPayment.getReturnMoney();
					BigDecimal payMoney = orderPayment.getDeliveryMoney().subtract(returnedMoney);
					if(payMoney.compareTo(BigDecimal.ZERO) > 0 && refund.compareTo(BigDecimal.ZERO) > 0){
						BigDecimal subRefund = refund.compareTo(payMoney) > 0 ? payMoney : refund;
						String outerId = null;
						if(Payment.GIFT_CARD.equals(paymentId)){
							outerId = orderPayment.getCredential().getOuterId();
							PresentCard presentCard = presentCardService.get(outerId);
							presentCardService.use(presentCard, order,subRefund);
							refund = refund.subtract(subRefund);
							orderPayment.setReturnMoney(orderPayment.getReturnMoney().add(subRefund));
							returnOrder.addRefund(payment, subRefund, outerId);
							OrderPayment opt = getOrderPayment(returnOrder, subRefund, orderPayment);
							list.add(opt);
						}
					}
				}
				calcReturnQuantity(returnOrder);
			}
		}
		return list ;
	}

	/**
	 *  退货单如果实物和申请数量不一致，更新订单项的退货数量
	 * @param returnOrder
	 */
	private void calcReturnQuantity(ReturnOrder returnOrder){
		Set<ReturnOrderItem> returnOrderItems = returnOrder.getItemList();
		for(ReturnOrderItem returnOrderItem : returnOrderItems){
			int realQuantity = returnOrderItem.getRealQuantity();
			int appQuantity = returnOrderItem.getAppQuantity();
			if(realQuantity < appQuantity){
				OrderItem orderItem = returnOrderItem.getOrderItem();
				orderItem.setReturnQuantity(orderItem.getReturnQuantity() - (appQuantity - realQuantity));
			}
		}
	}

	private BigDecimal calcRefundTotalValue(ReturnOrder returnOrder) throws ReturnOrderException{
		BigDecimal deliveryFee = returnOrder.getRefundDeliveryFee() == null ? BigDecimal.ZERO : returnOrder.getRefundDeliveryFee();
		BigDecimal refund = calcRefundGoodsValue(returnOrder).add(deliveryFee);
		return refund;
	}

	private BigDecimal calcRefundGoodsValue(ReturnOrder returnOrder) throws ReturnOrderException{
		BigDecimal refundGoodsValue = BigDecimal.ZERO;
		for(ReturnOrderItem returnOrderItem : returnOrder.getItemList()){
			if(returnOrderItem.getRealQuantity() > returnOrderItem.getAppQuantity()){
				throw new ReturnOrderException(returnOrder,"实际退还数量不能大于申请数量");
			}
			BigDecimal itemPrice = returnOrderItem.getOrderItem().getBalancePrice().multiply(new BigDecimal(returnOrderItem.getRealQuantity()));
			refundGoodsValue = refundGoodsValue.add(itemPrice);
		}
		return BigDecimalUtils.format(refundGoodsValue);
	}

	private void throwStatusErrorException() {
		throw new RuntimeException("returnOrder status error");
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ReturnOrder get(Long id) {
		return returnOrderDao.get(id);
	}

	@Override
	public void update(ReturnOrder returnOrder) {
		if (!returnOrder.getStatus().getId().equals(Code.RETURN_ORDER_STATUS_NEW)) {
			throwStatusErrorException();
		}
		Order originalOrder = returnOrder.getOriginalOrder();
		BigDecimal deliveryFee = originalOrder.getDeliveryFee() == null ? BigDecimal.ZERO : originalOrder.getDeliveryFee();
		BigDecimal refundDeliveryFee = returnOrder.getRefundDeliveryFee() == null ? BigDecimal.ZERO : returnOrder.getRefundDeliveryFee();
		if(!Code.RETURN_ORDER_TYPE_RETURN.equals(returnOrder.getType().getId())
				&& refundDeliveryFee.compareTo(BigDecimal.ZERO) > 0){
			throw new RuntimeException("非退货单不能退还退款运费");
		}

		if(refundDeliveryFee.compareTo(deliveryFee)>0){
			throw new RuntimeException("退款运费不能大于原运费");
		}

		if(refundDeliveryFee.compareTo(BigDecimal.ZERO) > 0){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", returnOrder.getOriginalOrder().getId());
			parameters.put("type", Code.RETURN_ORDER_TYPE_RETURN);
			List<ReturnOrder> oldList = returnOrderDao.find(parameters, new Pagination(1)); 
			if( oldList != null && oldList.size() == 1 ){
				throw new RuntimeException("非首次退货不能退还退款运费");
			}
		}


		returnOrderDao.update(returnOrder);
	}

	@Override
	public void addTrack(ReturnOrder returnOrder, ReturnOrderTrack track) {
		returnOrder.addTrack(track);
		returnOrderDao.save(track);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ReturnOrder> find(Map<String, Object> parameters,
			Pagination pagination) {
		return returnOrderDao.find(parameters, pagination);
	}


	@Override
	public List<Object[]> findReturnOrderCollectByShop(Shop shop,
			Map<String, Object> param) {
		List<Map<String, Object>> list =returnOrderDao.findReturnOrderCollectByShop(param);
		List<Object[]> result = new ArrayList<Object[]>();
		if(list != null){
			for(Map<String, Object> map : list){
				result.add(map.values().toArray());
			}
		}
		return result;
	}


	@Override
	public List<ReturnOrderTag> findReturnOrderTagByReturnOrderId(Long id) {
		return returnOrderDao.findReturnOrderTagByReturnOrderId(id);
	}


	@Override
	public void saveReturnOrderTag(ReturnOrder returnOrder, Long tagId) throws ReturnOrderException {
		ReturnOrderTag returnOrderTag = new ReturnOrderTag();
		returnOrderTag.setReturnOrder(returnOrder);
		returnOrderTag.setTag(new Code(tagId));
		returnOrderDao.save(returnOrderTag);
	}


	@Override
	public void notRefund(ReturnOrder returnOrder, User operator) {
		//做待定-不退款操作时，退货单状态到‘实物入库’即可，和‘原包非整退’最后状态一致；
		/*returnOrder.addLog(new Code(Code.RETURN_ORDER_STATUS_REFUNDED),operator);*/
		if(returnOrder.getShouldrefund().getId().equals(IS_SHOULD_REFUND_580002)){			
			returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580005));
		}
		returnOrderDao.update(returnOrder);
	}


	@Override
	public void saveReturnOrderPackage(ReturnOrderPackage returnPackage) throws ReturnOrderException {
		returnOrderDao.save(returnPackage);
	}


	@Override
	public void saveReturnOrderPackageItem(ReturnOrderPackageItem packageItem) throws ReturnOrderException {
		returnOrderDao.save(packageItem);
	}


	@Override
	public void saveReturnOrderPackageRelate(
			ReturnOrderPackageRelate packageRelate)  throws ReturnOrderException {
		returnOrderDao.save(packageRelate);
	}


	@Override
	public List<ReturnOrderPackage> findReturnOrderPackage(
			Map<String, Object> parameters, Pagination pagination) {
		return returnOrderDao.findReturnOrderPackage(parameters,pagination);
	}


	@Override
	public List<ReturnOrderPackageItem> getPackageItem(Long id) {
		return returnOrderDao.getPackageItem(id);
	}

	@Override
	public String getReturnExpressId(String orderid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnOrderPackage getPackage(Long packageid) {
		return returnOrderDao.getPackage(packageid);
	}

	@Override
	public void update(ReturnOrderPackage returnOrderPackage) {
		returnOrderDao.update(returnOrderPackage);
	}

	@Override
	public void update(ReturnOrderPackageRelate ropr) {
		returnOrderDao.update(ropr);
	}
	
}

