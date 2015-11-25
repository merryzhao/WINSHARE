package com.winxuan.ec.task.service.sap.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.task.dao.ec.EcDao;
import com.winxuan.ec.task.dao.sap.SapDao;
import com.winxuan.ec.task.exception.sap.SapOrderTransferException;
import com.winxuan.ec.task.model.sap.SapOrder;
import com.winxuan.ec.task.service.sap.SapOrderService;

/**
 * 
 * @author yangxinyi
 *
 */
@Service("sapOrderService")
@Transactional(rollbackFor = Exception.class)
public class SapOrderServiceImpl implements SapOrderService, Serializable {

	private static final long serialVersionUID = -4796977379786908942L;
	private static final Log LOG = LogFactory.getLog(SapOrderService.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private SapDao sapDao;
	@Autowired
	private EcDao ecDao;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DeliveryService deliveryService;
	
	@Override
	public void transferOrder(Order order) throws Exception {
		order = orderService.get(order.getId());
		checkOrder(order);
		sendOrder(order);
//		sendOrderItem(order);
		orderService.transportSapOrder(order);
		LOG.info(order.getId() + ": transfers");
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	private void checkOrder(Order order) throws SapOrderTransferException{
		if(!order.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING)

				&& !order.getTransferResult().getId().equals(Code.EC2SAP_STATUS_NONE)){
			throw new SapOrderTransferException(order.getId()+": order status error!");
		}
		if(order.getConsignee() == null){
			throw new SapOrderTransferException(order.getId()+": order consignee null!");
		}
		for(OrderItem orderItem : order.getItemList()){
			ProductSale sale = orderItem.getProductSale();
			Product product = sale.getProduct();
			if(product.getMerchId() == 0) {
				throw new SapOrderTransferException(order.getId()+": "+product.getId()+" merchantId is not exist!");
			}
			if(sale.getOuterId() == null) {
				throw new SapOrderTransferException(order.getId()+": "+sale.getId()+" owncode is not exist!");
			}
		}
	}
	
	private void sendOrder(Order order) {
		order = orderService.get(order.getId());
		String deliveryOption = null;
		if(Code.OUT_OF_STOCK_OPTION_CANCEL.equals(order.getConsignee().getOutOfStockOption().getId())) {
			deliveryOption = "X";
		}
		Object[] params  = null;
		for(OrderItem orderItem : order.getItemList()){
			ProductSale productSale = orderItem.getProductSale();
			params  = new Object[] {
					order.getId(),
					order.getOuterId(),
					orderItem.getId(), 
					order.getDistributionCenter().getDcDest().getName(),
					order.getDistributionCenter().getRemark(),
					productSale.getOuterId(),
					orderItem.getPurchaseQuantity(),
					orderItem.getBalancePrice(),
					orderItem.getListPrice(), 
					order.getCustomer().getId(),
					order.getChannel().getId(),
					deliveryOption
			};
			sapDao.sendOrderItems(params);
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> listSapOrderDelivery() {
		return sapDao.listSapOrderDelivery();
	}
	
	@Override
	public void processFetchOrder(String orderId) throws Exception {
		Order ecOrder = orderService.get(orderId); 
		List<SapOrder> sapOrders = sapDao.listSapOrderItemDelivery(ecOrder.getId());
		if(sapOrders == null || sapOrders.isEmpty()) {
			throw new SapOrderTransferException(ecOrder.getId() + " : sap order has no order item!");
		}
		Set<OrderItem> items = ecOrder.getItemList();
		if(sapOrders.size() != items.size()){
			throw new SapOrderTransferException(ecOrder.getId() + " : sap item size not equals ec size!");
		}
		if(sapDao.sapDoCancel(orderId)) {
			orderService.cancel(ecOrder,new Code(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL),employeeService.get(Employee.SYSTEM));
			sendChannelOrde(ecOrder);
			sapDao.updateSapOrderIflag(orderId);
		} else {
			if(ecOrder.getProcessStatus().getId().equals(Code.ORDER_PROCESS_STATUS_PICKING)) {
				processOrderDeliveryInfo(ecOrder, sapOrders);
				DeliveryCompany deliveryCompany = deliveryService.getDeliveryCompany(DeliveryCompany.CHENGDU_CENTER);
				orderService.delivery(ecOrder, deliveryCompany, ecOrder.getDeliveryCode(), employeeService.get(Employee.SYSTEM));
				sendChannelOrde(ecOrder);
				sapDao.updateSapOrderIflag(orderId);
			}
		}
	}
	
	private void processOrderDeliveryInfo(Order ecOrder, List<SapOrder> sapOrders) {
		Date deliveryTime = null;
		String deliveryCode = null;
		//count用来记录订单中发货不为0的订单项,如果存在发货不为0的订单项，则以该订单项的发货时间为准
		for(SapOrder sapOrder : sapOrders) {
			OrderItem ecOrderItem = orderService.getOrderItem(Long.valueOf(sapOrder.getOrderItemId()));
			ecOrderItem.setDeliveryQuantity(sapOrder.getDeliveryQuantity());
			// D803订单回传到ec时，对回传数据进行判断，如果发货数量为0，则不记录该订单的发货时间
			if(sapOrder.getDeliveryQuantity() > 0){
				deliveryTime = sapOrder.getDeliveryTime();
			}
			deliveryCode = sapOrder.getDeliveryCode();
//			sapDao.updateOrderItem(orderItem.getId(), sapOrder.getDeliveryQuantity());
		}
		
		ecOrder.setDeliveryTime(deliveryTime);
		ecOrder.setDeliveryCode(deliveryCode);
	}
	
	/**
	 * 回传渠道订单
	 * 
	 * @param ecOrder
	 */
	private void sendChannelOrde(Order ecOrder) {
		if (ecOrder.getChannel() != null && ecOrder.getChannel().isUsingApi()
				&& ecOrder.getOuterId() != null) {
			ecDao.sendChannelOrder(ecOrder);
		}
	}
	
}