package com.winxuan.ec.task.job.ec.order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * @author HideHai
 * @version 1.0,2012-3-29下午06:18:53
 */
@Component("cloneOrder")
public class CloneOrder {

	private static final Log LOG = LogFactory.getLog(CloneOrder.class);
	private static String reason = " 快递掉件补单";
	private static final String LOG_FILE = "F:/proExtends/clonOrder.txt";
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	private List<String> dataInput(){
		List<String> list = new ArrayList<String>();
		list.add("20120323425232");
		return list;
	}

	private Order cloneNewOrder(Order orign) throws Exception{
		Order newOrder = new Order();
		BigDecimal advanceMoney = orign.getAdvanceMoney();
		newOrder.setAdvanceMoney(advanceMoney);
		newOrder.setChannel(orign.getChannel());
		addConsignee(orign,newOrder);
		newOrder.setCreator(orign.getCreator());
		newOrder.setCustomer(orign.getCustomer());
		newOrder.setDeliveryFee(BigDecimal.ZERO);
		newOrder.setDeliveryType(orign.getDeliveryType());
		addItemList(orign, newOrder);
		addPayment(orign, newOrder);
		newOrder.setPayType(orign.getPayType());
		orderService.create(newOrder);
		return newOrder;

	}

	private void addPayment(Order originalOrder,Order newOrder){
		Set<OrderPayment> orderPayments = originalOrder.getPaymentList();
		for(OrderPayment orderPayment : orderPayments){
			OrderPayment payment = new OrderPayment();
			payment.setCreateTime(new Date());
			payment.setOrder(newOrder);
			payment.setPay(orderPayment.isPay());
			payment.setPayMoney(orderPayment.getPayMoney());
			payment.setPayTime(orderPayment.getPayTime());
			payment.setCredential(orderPayment.getCredential());
			payment.setPayment(orderPayment.getPayment());
			newOrder.addPayment(payment);
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
		newc.setRemark(String.format("%s - %s", originalOrder.getId(),reason));
		newc.setZipCode(originalConsignee.getZipCode());
		newOrder.setConsignee(newc);
	}

	private void addItemList(Order originalOrder, Order newOrder) {
		for (OrderItem orderItem : originalOrder.getItemList()) {
			OrderItem item = new OrderItem();
			item.setSalePrice(orderItem.getBalancePrice());
			item.setListPrice(orderItem.getListPrice());
			item.setProductSale(orderItem.getProductSale());
			item.setPurchaseQuantity(orderItem.getPurchaseQuantity());
			item.setShop(orderItem.getProductSale().getShop());
			newOrder.addItem(item);
		}
	}

	public void start(){
		List<String> orders = dataInput();
		for(String s : orders){
			hibernateLazyResolver.openSession();
			try {
				Order origOrder = orderService.get(s);
				if(origOrder != null){
					Order order= cloneNewOrder(origOrder);
					if(order != null){
						cloneLog(String.format("orignOrder: %s create newOrder success! id: %s", origOrder.getId(),order.getId()));
					}else{
						cloneLog(String.format("orignOrder: %s create newOrder failure!", origOrder.getId()));
					}
				}
			} catch (Exception e) {
				cloneLog(e.getMessage());
				LOG.error(e.getMessage());
			}
			hibernateLazyResolver.releaseSession();
		}
		cloneLog("over");
	}

	private void cloneLog(String s){
		LOG.info(s);
		if(!LOG_FILE.isEmpty() && s != null){
			File logFile = new File(LOG_FILE);
			try {
				if(!logFile.exists()){
					logFile.createNewFile();
				}
				FileWriter writer;
				BufferedWriter bufferedWriter;
				writer = new FileWriter(logFile,true);
				bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(s);
				bufferedWriter.newLine();
				writer.flush();
				bufferedWriter.flush();
			} catch (IOException e) {
				LOG.error(s,e);
			}
		}
	}
}
