/*
 * @(#)CancelUnprocessOrders.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.job.ec.order;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.task.service.erp.ErpOrderService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 对订单进行系统流程外处理
 * @author  HideHai
 * @version 1.0,2011-12-22
 */
@Component("processFreeOrder")
public class ProcessFreeOrder implements Serializable{

	private static final long serialVersionUID = 6074948621397755214L;
	private static final Log LOG = LogFactory.getLog(AuditingOrder.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private ErpOrderService erpOrderService;
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	/**
	 * 缺货取消订单
	 * @param orders
	 */
	public void cancelOrder(List<String> orders){
		hibernateLazyResolver.openSession();
		if(orders != null && !orders.isEmpty()){
			for(String id : orders){
				Order order = orderService.get(id);
				try {
					if(!order.isCanceled()){
						orderService.cancel(order, 
								new Code(Code.ORDER_PROCESS_STATUS_ERP_CANCEL), 
								new Employee(6L));
						LOG.info(String.format("%s cancel!", order.getId()));
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 创建退货单支付凭证
	 * @throws ParseException
	 */
	public void createChannelCredential() throws ParseException{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("supplyType",new Long[]{Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL});
		parameters.put("storageType", new Long[]{Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM,Code.STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM});
		parameters.put("transferResult", Code.EC2ERP_STATUS_NEW);
		parameters.put("processStatus",new Long[] { Code.ORDER_PROCESS_STATUS_COMPLETED,Code.ORDER_PROCESS_STATUS_DELIVERIED});
		parameters.put("virtual",false);
		parameters.put("channel", new Long[]{110L,111L});
		parameters.put("startCreateTime",SimpleDateFormat.getDateInstance().parse("2011-12-12"));
		parameters.put("endCreateTime", SimpleDateFormat.getDateInstance().parse("2011-12-15"));

		int i = 1 ;

		Pagination pagination = new Pagination();
		pagination.setPageSize(40);
		pagination.setCurrentPage(i);
		List<Order> processOrder = null;
		hibernateLazyResolver.openSession();
		while((processOrder = orderService.find(parameters, (short) 0, pagination)) != null 
				&& !processOrder.isEmpty()){
			LOG.info("page : "+i);
			for(Order order : processOrder){
				try{
					Set<OrderPayment> payments = order.getPaymentList();
					if(payments != null && !payments.isEmpty()){
						boolean flag = false;
						for(OrderPayment payment : payments){
							if(payment.isPay() && payment.getCredential() == null 
									&& payment.getPayment().getId().equals(Payment.CHANNEL)){
								PaymentCredential credential = new PaymentCredential();
								credential.setCustomer(order.getCustomer());
								credential.setMoney(payment.getPayMoney());
								credential.setOperator(new Employee(6L));
								credential.setOuterId(order.getId());
								credential.setPayTime(new Date());
								credential.setOrderPaymentList(payments);
								credential.setPayment(new Payment(Payment.CHANNEL));
								credential.setPayer("HideHai");
								credential.setRemark("fouder order process!");
								payment.setCredential(credential); 
								LOG.info(String.format("%s create.", order.getId()));
								flag = true;
							}
						}
						if(flag){
							orderService.update(order, new Employee(6L));
						}
					}
				}catch (Exception e) {
					LOG.info(String.format("%s failure.", order.getId()));
				}
			}
			pagination.setCurrentPage(++i);
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 方正EC的退货单增加对应的支付凭据
	 * @param returnOrders
	 */
	public void createChannelCredential(List<String> returnOrders){
		hibernateLazyResolver.openSession();
		if(returnOrders != null && !returnOrders.isEmpty()){
			for(String id : returnOrders){
				try{
					ReturnOrder returnOrder = returnOrderService.get(Long.valueOf(id));
					if(returnOrder != null){
						Order order = returnOrder.getOriginalOrder();
						Set<OrderPayment> payments = returnOrder.getOriginalOrder().getPaymentList();
						if(payments != null && !payments.isEmpty()){
							for(OrderPayment payment : payments){
								if(payment.isPay() && payment.getCredential() == null){
									PaymentCredential credential = new PaymentCredential();
									credential.setCustomer(order.getCustomer());
									credential.setMoney(payment.getPayMoney());
									credential.setOperator(new Employee(6L));
									credential.setOuterId(order.getId());
									credential.setPayTime(new Date());
									credential.setOrderPaymentList(payments);
									credential.setPayment(new Payment(Payment.CHANNEL));
									credential.setPayer("HideHai");
									credential.setRemark("fouder order process!");
									payment.setCredential(credential); 
								}
							}
						}
						orderService.update(order, new Employee(6L));
					}
					LOG.info(String.format("%s process!", id));
				}catch(Exception e){
					LOG.error(e.getMessage());
				}
			}
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 订单发货
	 * @param orders
	 */
	public void orderDelivery(List<String> orders){
		hibernateLazyResolver.openSession();
		if(orders != null && !orders.isEmpty()){
			for(String id : orders){
				Order order = orderService.get(id);
				try {
					if(!order.isDeliveried()){
						Set<OrderItem> items = order.getItemList();
						for(OrderItem item : items){
							item.setDeliveryQuantity(item.getPurchaseQuantity());
						}
						DeliveryCompany company = deliveryService.getDeliveryCompany(1330L);
						orderService.delivery(order, company, "99887766", new Employee(6L));
					}
					LOG.info(String.format("%s process!", id));
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 *  订单退款
	 * @param orders
	 */
	public void orderRefund(List<String> orders){
		hibernateLazyResolver.openSession();
		for(String id : orders){
			Order order = orderService.get(id);
			try {
				orderService.cancel(order, new Code(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL), new Employee(Employee.SYSTEM));
				LOG.info(String.format("%s process!", id));
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 更改退货单的状态为不处理/中启处理异常
	 * @param returnOrders
	 */
	public void updateReturnOrderException(List<String> returnOrders){
		for(String s : returnOrders){
			hibernateLazyResolver.openSession();
			ReturnOrder returnOrder = returnOrderService.get(Long.parseLong(s));
			if(returnOrder.getStatus().getId().equals(Code.RETURN_ORDER_STATUS_RETURNING)){
				erpOrderService.updateReturnOrderCancel(returnOrder);
			}
			hibernateLazyResolver.releaseSession();
		}
	}
}

