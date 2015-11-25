/*
 * @(#)OrderServiceImplTests1.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.product.ProductService;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-11-16
 */
public class OrderServiceImplTest extends BaseTest {

	private static final Long TEST_TOWN_ID = 34160L;
	private static final Long TEST_DISTINCT_ID = 1510L;
	private static final Long TEST_CUSTOMER_ID = 117L;
	private static final Long TEST_PRODUCT_SALE_1_ID = 10005563L;
	private static final Long TEST_PRODUCT_SALE_2_ID = 	10005981L;
	private static final int TEST_ORDER_ITEM_1_QUANTITY = 1;
	private static final int TEST_ORDER_ITEM_2_QUANTITY = 1;
	private static final Long TEST_INVOICE_MODE_ID = 15001L;
	private static final Long TEST_INVOICE_TITLE_TYPE_ID = 3460L;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CodeService codeService;

	/**
	 * 测试收货人town为空，发票是否的town是否为空：发票的town为选中区域的默认town值
	 */
	@Test
	public void testCreateCase0(){
		Customer customer = customerService.get(TEST_CUSTOMER_ID);
		DeliveryType deliveryType = deliveryService
				.getDeliveryType(DeliveryType.EXPRESS);
		Channel channel = channelService.get(Channel.CHANNEL_EC);
		Area district = areaService.get(TEST_DISTINCT_ID);
		Order order = new Order();
		order.setCustomer(customer);
		order.setCreator(customer);
		order.setDeliveryType(deliveryType);
		order.setChannel(channel);
		OrderConsignee consignee = new OrderConsignee();
		consignee.setConsignee("xxx");
		consignee.setPhone("83157557");
		consignee.setEmail("liuan@qq.com");
		consignee.setDistrict(district);
		consignee.setCity(district.getParent());
		consignee.setProvince(district.getParent().getParent());
		consignee.setCountry(district.getParent().getParent().getParent()
				.getParent());
		consignee.setAddress("文轩在线");
		consignee.setZipCode("610018");
		consignee.setOrder(order);
		order.setConsignee(consignee);

		ProductSale productSale1 = productService
				.getProductSale(TEST_PRODUCT_SALE_1_ID);
		ProductSale productSale2 = productService
				.getProductSale(TEST_PRODUCT_SALE_2_ID);

		BigDecimal orderListPrice = BigDecimal.ZERO;
		BigDecimal orderSalePrice = BigDecimal.ZERO;

		OrderItem item1 = new OrderItem();
		int item1Qty = TEST_ORDER_ITEM_1_QUANTITY;
		item1.setProductSale(productSale1);
		item1.setPurchaseQuantity(item1Qty);
		item1.setListPrice(productSale1.getListPrice());
		item1.setSalePrice(customer.getSalePrice(productSale1));
		item1.setShop(productSale1.getShop());

		OrderItem item2 = new OrderItem();
		int item2Qty = TEST_ORDER_ITEM_2_QUANTITY;
		item2.setProductSale(productSale2);
		item2.setPurchaseQuantity(item2Qty);
		item2.setListPrice(productSale2.getListPrice());
		item2.setSalePrice(customer.getSalePrice(productSale2));
		item2.setShop(productSale2.getShop());

		order.addItem(item1);
		order.addItem(item2);

		for (OrderItem item : order.getItemList()) {
			orderListPrice = orderListPrice.add(item.getTotalListPrice());
			orderSalePrice = orderSalePrice.add(item.getTotalSalePrice());
		}

		Area defaultTown = areaService.getDefTownByDistrict(district);
		
		BigDecimal deliveryFee = deliveryService.getDeliveryFee(deliveryType, defaultTown, orderListPrice);
		
		BigDecimal payMoney = orderSalePrice.add(deliveryFee);

		OrderPayment orderPayment = new OrderPayment();
		Payment payment = paymentService.get(Payment.ACCOUNT);
		orderPayment.setPayment(payment);
		orderPayment.setPayMoney(payMoney);
		orderPayment.setCreateTime(new Date());
		order.addPayment(orderPayment);

		OrderInvoice invoice = order.getInvoice("图书", new Code(
				TEST_INVOICE_MODE_ID), orderSalePrice);
		invoice.setOperator(employeeService.get(Employee.SYSTEM));
		invoice.setTitle("xxx");
		invoice.setTitleType(new Code(TEST_INVOICE_TITLE_TYPE_ID));
		order.addInvoice(invoice);

		try {
			orderService.create(order);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		order = orderService.get(order.getId());
		Assert.assertEquals(order.getInvoice("图书", new Code(TEST_INVOICE_MODE_ID), payMoney).getTown(), defaultTown);
	}
	
	/**
	 * 测试只用暂存款支付：订单状态变为等待配货，用户暂存款余额减少，商品增加库存占用
	 * @throws CustomerAccountException 
	 * @throws OrderStatusException 
	 */
	@Test
	public void testCreateCase1() throws OrderStatusException, CustomerAccountException {
		Customer customer = customerService.get(TEST_CUSTOMER_ID);
		BigDecimal balance = customer.getAccount().getBalance();
		DeliveryType deliveryType = deliveryService
				.getDeliveryType(DeliveryType.EXPRESS);
		Channel channel = channelService.get(Channel.CHANNEL_EC);
		Area town = areaService.get(TEST_TOWN_ID);
		Order order = new Order();
		order.setCustomer(customer);
		order.setCreator(customer);
		order.setDeliveryType(deliveryType);
		order.setChannel(channel);
		OrderConsignee consignee = new OrderConsignee();
		consignee.setConsignee("xxx");
		consignee.setPhone("83157557");
		consignee.setEmail("liuan@qq.com");
		consignee.setTown(town);
		consignee.setDistrict(town.getParent());
		consignee.setCity(town.getParent().getParent());
		consignee.setProvince(town.getParent().getParent().getParent());
		consignee.setCountry(town.getParent().getParent().getParent()
				.getParent().getParent());
		consignee.setAddress("文轩在线");
		consignee.setZipCode("610018");
		consignee.setOrder(order);
		order.setConsignee(consignee);

		ProductSale productSale1 = productService
				.getProductSale(TEST_PRODUCT_SALE_1_ID);
		ProductSale productSale2 = productService
				.getProductSale(TEST_PRODUCT_SALE_2_ID);

		BigDecimal orderListPrice = BigDecimal.ZERO;
		BigDecimal orderSalePrice = BigDecimal.ZERO;

		OrderItem item1 = new OrderItem();
		int item1Qty = TEST_ORDER_ITEM_1_QUANTITY;
		item1.setProductSale(productSale1);
		item1.setPurchaseQuantity(item1Qty);
		item1.setListPrice(productSale1.getListPrice());
		item1.setSalePrice(customer.getSalePrice(productSale1));
		item1.setShop(productSale1.getShop());

		OrderItem item2 = new OrderItem();
		int item2Qty = TEST_ORDER_ITEM_2_QUANTITY;
		item2.setProductSale(productSale2);
		item2.setPurchaseQuantity(item2Qty);
		item2.setListPrice(productSale2.getListPrice());
		item2.setSalePrice(customer.getSalePrice(productSale2));
		item2.setShop(productSale2.getShop());

		order.addItem(item1);
		order.addItem(item2);

		for (OrderItem item : order.getItemList()) {
			orderListPrice = orderListPrice.add(item.getTotalListPrice());
			orderSalePrice = orderSalePrice.add(item.getTotalSalePrice());
		}

		
		BigDecimal deliveryFee = deliveryService.getDeliveryFee(deliveryType,
				town, orderListPrice);
		
		BigDecimal payMoney = orderSalePrice.add(deliveryFee);

		OrderPayment orderPayment = new OrderPayment();
		Payment payment = paymentService.get(Payment.ACCOUNT);
		orderPayment.setPayment(payment);
		orderPayment.setPayMoney(payMoney);
		orderPayment.setCreateTime(new Date());
		order.addPayment(orderPayment);

		OrderInvoice invoice = order.getInvoice("图书", new Code(
				TEST_INVOICE_MODE_ID), orderSalePrice);
		invoice.setOperator(employeeService.get(Employee.SYSTEM));
		invoice.setTitle("xxx");
		invoice.setTitleType(new Code(TEST_INVOICE_TITLE_TYPE_ID));
		order.addInvoice(invoice);

		try {
			orderService.create(order);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		order = orderService.get(order.getId());
		List<Order> list = new ArrayList<Order>();
		list.add(order);
		orderService.payByAccount(list, customer, services.employeeService.get(Employee.SYSTEM));
		Assert.assertEquals(2, order.getPurchaseKind());
		Assert.assertEquals(TEST_ORDER_ITEM_1_QUANTITY
				+ TEST_ORDER_ITEM_2_QUANTITY, order.getPurchaseQuantity());
		Assert.assertEquals(payMoney, order.getAdvanceMoney());
		Assert.assertEquals(Code.ORDER_PAY_TYPE_FIRST_PAY, order.getPayType()
				.getId());
		Assert.assertEquals(2, order.getItemList().size());
		Assert.assertEquals(Code.ORDER_PROCESS_STATUS_WAITING_PICKING, order
				.getProcessStatus().getId());
		Assert.assertEquals(balance.subtract(payMoney), order.getCustomer()
				.getAccount().getBalance());
	}

	/**
	 * 测试客户取消订单：订单被取消成功
	 */
	@Test
	public void testCancelCase1() {
		String orderId = "20111118013876";
		Order order = orderService.get(orderId);
		Code cancelStatus = codeService
				.get(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
		try {
			orderService.cancel(order, cancelStatus, order.getCustomer());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		order = orderService.get(orderId);
		Assert.assertEquals(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL, order
				.getProcessStatus().getId());
	}

	/**
	 * 测试一个订单使用暂存款支付：订单状态变为等待配货，暂存款余额减少，商品增加库存占用
	 */
	@Test
	public void testPayCase1() {
		String orderId = "20111121013896";
		Order order = orderService.get(orderId);
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		CustomerAccount account = order.getCustomer().getAccount();
		BigDecimal balance = account.getBalance();
		BigDecimal payMoney = order.getTotalPayMoney();
		try {
			orderService.pay(orderList, account, payMoney, order.getCustomer());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	 	order = orderService.get(orderId);
		Assert.assertEquals(order.getProcessStatus().getId(),
				Code.ORDER_PROCESS_STATUS_WAITING_PICKING);
		Assert.assertEquals(balance.subtract(payMoney), order.getCustomer()
				.getAccount().getBalance());
	}

	/**
	 * 测试订单由暂存款和货到付款支付，订单部分发货，暂存款金额能完全支付部分发货金额：
	 * 订单状态改为部分发货，减少商品库存和占用，多余的钱退暂存款，
	 * 订单应支付金额为0
	 */
	@Test
	public void testDeliveryCase1() {
		String orderId = "20111121013897";
		Order order = orderService.get(orderId);
		BigDecimal balance = order.getCustomer().getAccount().getBalance();
		Employee operator = employeeService.get(Employee.SYSTEM);
		final Long deliveryCompanyId = 1310L;
		DeliveryCompany deliveryCompany = deliveryService
				.getDeliveryCompany(deliveryCompanyId);
		OrderItem item = order.getItemList().iterator().next();
		ProductSale productSale = item.getProductSale();
		int stockQuantity = productSale.getStockQuantity();
		int saleQuantity = productSale.getSaleQuantity();
		int purchaseQuantity = item.getPurchaseQuantity();
		final int deliveryQuantity = 12;
		item.setDeliveryQuantity(deliveryQuantity);
		BigDecimal advanceMoney = order.getAdvanceMoney();
		BigDecimal refundMoney = advanceMoney.subtract(
				item.getBalancePrice().multiply(
						BigDecimal.valueOf(deliveryQuantity))).subtract(
				order.getDeliveryFee());
		if (refundMoney.compareTo(BigDecimal.ZERO) <= 0) {
			refundMoney = BigDecimal.ZERO;
		}
		try {
			orderService.delivery(order, deliveryCompany, "123456", operator);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		order = orderService.get(orderId);
		Assert.assertEquals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG, order
				.getProcessStatus().getId());
		Assert.assertEquals(balance.add(refundMoney), order.getCustomer()
				.getAccount().getBalance());
		Assert.assertEquals(refundMoney, order.getPaymentList().iterator()
				.next().getReturnMoney());
		Assert.assertEquals(BigDecimal.ZERO, order.getRequidPayMoney());
		productSale = order.getItemList().iterator().next().getProductSale();
		Assert.assertEquals(stockQuantity - deliveryQuantity,
				productSale.getStockQuantity());
		Assert.assertEquals(saleQuantity - purchaseQuantity,
				productSale.getSaleQuantity());
	}

	/**
	 * 测试订单由暂存款和货到付款支付，订单部分发货，暂存款金额不能完全支付部分发货金额：
	 * 订单状态改为部分发货，减少商品库存和占用，订单应支付金额为发货金额加运费减已支付金额
	 */
	@Test
	public void testDeliveryCase2() {
		String orderId = "20111121013897";
		Order order = orderService.get(orderId);
		BigDecimal balance = order.getCustomer().getAccount().getBalance();
		Employee operator = employeeService.get(Employee.SYSTEM);
		final Long deliveryCompanyId = 1310L;
		DeliveryCompany deliveryCompany = deliveryService
				.getDeliveryCompany(deliveryCompanyId);
		OrderItem item = order.getItemList().iterator().next();
		ProductSale productSale = item.getProductSale();
		int stockQuantity = productSale.getStockQuantity();
		int saleQuantity = productSale.getSaleQuantity();
		int purchaseQuantity = item.getPurchaseQuantity();
		final int deliveryQuantity = 25;
		item.setDeliveryQuantity(deliveryQuantity);
		BigDecimal advanceMoney = order.getAdvanceMoney();
		BigDecimal balanceMoney = item.getBalancePrice();
		try {
			orderService.delivery(order, deliveryCompany, "123456", operator);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		order = orderService.get(orderId);
		Assert.assertEquals(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG, order
				.getProcessStatus().getId());
		Assert.assertEquals(balance, order.getCustomer().getAccount()
				.getBalance());
		Assert.assertTrue(order.getPaymentList().iterator().next()
				.getReturnMoney().compareTo(BigDecimal.ZERO) == 0);
		Assert.assertEquals(
				balanceMoney.multiply(BigDecimal.valueOf(deliveryQuantity))
						.add(order.getDeliveryFee()).subtract(advanceMoney),
				order.getRequidPayMoney());
		productSale = order.getItemList().iterator().next().getProductSale();
		Assert.assertEquals(stockQuantity - deliveryQuantity,
				productSale.getStockQuantity());
		Assert.assertEquals(saleQuantity - purchaseQuantity,
				productSale.getSaleQuantity());
	}
	
	/**
	 * 检查直销库存，3个仓都不满足情况不创建订单
	 */
	@Test
	public void testCheckOrderStock() throws OrderStockException{
		Order order = orderService.get("20100101376446");
		order.getDeliveryQuantity();
		order.setDistributionCenter(null);
		List<OrderItem> list = orderService.findOutOfStockItemList(order);
		Assert.fail(list.size()+"");
		if (!list.isEmpty()) {			
			throw new OrderStockException(list.get(0));
		}
	}
}
