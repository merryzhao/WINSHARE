/*
 * @(#)CheckoutController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.CustomerExtendIsNullException;
import com.winxuan.ec.exception.OrderException;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.front.controller.cps.Cps;
import com.winxuan.ec.front.controller.cps.CpsFactory;
import com.winxuan.ec.front.controller.cps.UnionOrderValidate;
import com.winxuan.ec.front.controller.cps.extractor.QQExtractor;
import com.winxuan.ec.front.controller.customer.CheckoutForm.CheckoutInfo;
import com.winxuan.ec.front.controller.customer.CheckoutForm.CheckoutSubmit;
import com.winxuan.ec.front.service.cache.CacheService;
import com.winxuan.ec.front.service.cache.CacheServiceImpl;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.delivery.DeliveryInfo;
import com.winxuan.ec.model.delivery.DeliveryType;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderConsignee;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderNote;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.UnionOrder;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.shoppingcart.ShoppingcartProm;
import com.winxuan.ec.model.shoppingcart.ShoppingcartSeparator;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerPayPasswordService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.service.promotion.PromotionService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.service.shoppingcart.ShoppingcartService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.promotion.PromotionPrice;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.support.validator.utils.BeanValidator;
import com.winxuan.framework.util.BigDecimalUtils;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * 订单提交
 * 
 * @author HideHai
 * @version 1.0,2011-8-9
 */
@Controller
@RequestMapping(value = "/customer/checkout")
public class CheckoutController {


	private static final Log LOG = LogFactory.getLog(CheckoutController.class);
	//基本的订单免邮费促销活动--满38减运费
	private static final Long BASE_ORDER_SAVE_DELIVERYFEE_PROMOTION_ID = 624L;
	private static final String HASPAYPASSWORD = "hasPayPassword";

	@Autowired
	private AreaService areaService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PresentService presentService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private PresentCardService presentCardService;
	@Autowired
	private ShoppingcartService shoppingcartService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	@Qualifier("extractor")
	private QQExtractor extractor;

	@Autowired
	@Qualifier("cpsFactory")
	private CpsFactory cpsFactory;

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private CustomerPayPasswordService customerPayPasswordService;
	
	@RequestMapping(value = "/orders/payByAccount", method = RequestMethod.POST)
	public ModelAndView payByAccountList(@MyInject Customer customer,
			@RequestParam("payPassword") String payPassword,
			@RequestParam("orderId") String[] orderIds) {
		ModelAndView modelAndView = new ModelAndView("/customer/checkout/account_pay_list");
		
		if(orderIds == null || orderIds.length == 0){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "订单号不能为空");
			return modelAndView;
		}
		
		boolean success = true;
		boolean customerExtendException = false;
		List<Order> orderList = new ArrayList<Order>();
		List<Order> orderListFailure = new ArrayList<Order>();
		BigDecimal requiredPayMoney = BigDecimalUtils.ZERO;
		
		
		Order firstOrder = orderService.get(orderIds[0]);
		if(firstOrder == null){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "订单号不正确:" + orderIds[0]);
			success = false;
		}
		
		boolean isAccountFrozen = false;
		if(cacheService.payPasswordErrLimit(firstOrder.getCustomer().getId().toString())){
			isAccountFrozen = true;
			success = false;
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, 
					"您已经连续输入错误密码"+ CacheServiceImpl.PAY_PASSWORD_ERROR_TIMES_LIMIT +"次，系统将暂时冻结您的暂存款");
		}

		boolean isPasswordRight = false;
		for(String orderId : orderIds){
			Order order = orderService.get(orderId);
			if(order == null){
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "订单号不正确:" + orderId);
				success = false;
			}
			
			if(null != order 
					&& order.getRequidPayMoney().compareTo(BigDecimalUtils.ZERO)>0
					&& (order.isRequidOnlinePay() 
							|| (order.useAccountAndUnpaid() && order.getAccountUnpaidMoney().equals(order.getRequidPayMoney())))){
				orderList.add(order);
				requiredPayMoney = requiredPayMoney.add(order.getRequidPayMoney());
			}else{
				orderListFailure.add(order);
				success = false;
			}
			
			if(!success){
				continue;
			}
			
			if(!order.useAccountAndUnpaid()){
				continue;
			}
			
			try {
				if(isPasswordRight = customerService.validatePayPassword(customer, payPassword)){
					try {
						if(!isAccountFrozen){
							if(order.isAccountCanPay()){
								List<Order> needPayOrderList = new ArrayList<Order>();
								needPayOrderList.add(order);
								orderService.payByAccount(needPayOrderList, customer, customer);
								requiredPayMoney = requiredPayMoney.subtract(order.getAccountPaidMoney());
								LOG.info("批量支付-暂存款支付成功 orderId : " + order.getId());
							}
							else{
								success = false;
								modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "暂存款余额不足,请重新下单");
								
								continue;
							}
						}

					} catch (Exception e) {
						success = false;
						LOG.error(e.getMessage(),e);
						modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
						continue;
					} 
				}
				else{
					success = false;
					modelAndView.addObject("order", order);
					modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "支付密码错误");
					continue;
				}
			} catch (CustomerExtendIsNullException e) {
				customerExtendException = true;
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "您未设置支付密码，请先设置");
				continue;
			}
		}
		if(!isAccountFrozen && !customerExtendException){
			cacheService.updateCache(firstOrder.getCustomer().getId().toString(), isPasswordRight);
		}
		if(success){
			modelAndView.setViewName("/customer/checkout/view_list");
		}
	
		modelAndView.addObject(HASPAYPASSWORD, customerPayPasswordService.hasPayPassword(customer));
		modelAndView.addObject("orderList", orderList);
		modelAndView.addObject("orderListFailure", orderListFailure);
		modelAndView.addObject("requiredPayMoney", requiredPayMoney);
		return modelAndView;
	}
	
	@RequestMapping(value = "/order/payByAccount", method = RequestMethod.POST)
	public ModelAndView payByAccount(@MyInject Customer customer, 
			@RequestParam("payPassword") String payPassword,
			@RequestParam("orderId") String orderId) {
		ModelAndView modelAndView = new ModelAndView("/customer/checkout/account_pay");
		if(StringUtils.isNullOrEmpty(orderId)){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "订单号不能为空");
			return modelAndView;
		}
		Order order = orderService.get(orderId);
		if(order == null){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "订单号不正确:" + orderId);
			return modelAndView;
		}
		if(!cacheService.payPasswordErrLimit(customer.getId().toString())){
			try {
				boolean isPasswordRight;
				if(isPasswordRight = customerService.validatePayPassword(customer, payPassword)){
					try {
						if(order.isAccountCanPay()){
							List<Order> needPayOrderList = new ArrayList<Order>();
							needPayOrderList.add(order);
							orderService.payByAccount(needPayOrderList, customer, customer);
							LOG.info("暂存款支付成功 orderId : " + order.getId());
							modelAndView.setViewName("redirect:/customer/checkout/" + orderId);
						}
						else{
							modelAndView.addObject(ControllerConstant.MESSAGE_KEY , "暂存款余额不足,请重新下单");
						}
					} catch (CustomerAccountException e) {
						LOG.error(e.getMessage(),e);
						modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
					} catch (OrderStatusException e) {
						LOG.error(e.getMessage(),e);
						modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
					}
				}
				else{
					modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "支付密码错误");
				}
				cacheService.updateCache(customer.getId().toString(), isPasswordRight);
			} catch (CustomerExtendIsNullException e) {
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "您未设置支付密码，请先设置");
			}
		}
		else{
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, 
					"您已经连续输入错误密码"+ CacheServiceImpl.PAY_PASSWORD_ERROR_TIMES_LIMIT +"次，系统将暂时冻结您的暂存款");
		}
		modelAndView.addObject(HASPAYPASSWORD, customerPayPasswordService.hasPayPassword(customer));
		modelAndView.addObject("order", order);
		return modelAndView;
	}

	/**
	 * 支付单张订单
	 * @param orderId
	 * @param shoppingcart
	 * @return
	 */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ModelAndView view(@MyInject Customer customer,
			@PathVariable("orderId") String orderId,
			@MyInject Shoppingcart shoppingcart) {
		ModelAndView modelAndView = new ModelAndView("/customer/checkout/view");
		Order order = orderService.get(orderId);
		modelAndView.addObject("order", order);
		modelAndView.addObject("shoppingcart", shoppingcart);
		if(order.useAccountAndUnpaid()){
			modelAndView.setViewName("/customer/checkout/account_pay");
		}
		modelAndView.addObject(HASPAYPASSWORD, customerPayPasswordService.hasPayPassword(customer));
		return modelAndView;
	}

	/**
	 *  支付多张订单
	 * @param orders
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/orders",method=RequestMethod.POST)
	public ModelAndView view(@MyInject Customer customer,
			@RequestParam("payOrder")String payOrder){
		ModelAndView modelAndView = new ModelAndView("/customer/checkout/view_list");
		Order order;
		
		List<Order> orderList = new ArrayList<Order>();
		List<Order> orderListFailure = new ArrayList<Order>();
		BigDecimal requiredPayMoney = BigDecimalUtils.ZERO;
		for(String s : payOrder.split(",")){
			order = orderService.get(s);
			if(null != order 
					&& order.getRequidPayMoney().compareTo(BigDecimalUtils.ZERO)>0
					&& (order.isRequidOnlinePay() 
							|| (order.useAccountAndUnpaid() && order.getAccountUnpaidMoney().equals(order.getRequidPayMoney())))){
				orderList.add(order);
				requiredPayMoney = requiredPayMoney.add(order.getRequidPayMoney());
			}else{
				orderListFailure.add(order);
			}
			if(order.useAccountAndUnpaid()){
				modelAndView.setViewName("/customer/checkout/account_pay_list");
			}
		}
		modelAndView.addObject(HASPAYPASSWORD, customerPayPasswordService.hasPayPassword(customer));
		modelAndView.addObject("orderList", orderList);
		modelAndView.addObject("orderListFailure", orderListFailure);
		modelAndView.addObject("requiredPayMoney", requiredPayMoney);
		return modelAndView;
	}

	/**
	 *  跳转订单页面
	 *  如果没有卖家编号传入，则从购物车获取卖家传递到页面
	 * @param customer
	 * @param shoppingcart
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView get(@MyInject Customer customer,
			@MyInject Shoppingcart shoppingcart,
			HttpServletRequest request,
			Long shopId,Long supplyTypeId){
		ModelAndView modelAndView = new ModelAndView("redirect:/shoppingcart");
		ShoppingcartSeparator shoppingcartSeparator = null;
		List<CustomerAddress> addresses=null;
		CheckoutForm submitOrderForm = null;
		List<ShoppingcartItem> shoppingcartItems=null;
		if(shoppingcart == null){
			LOG.info("shoppingcart is null!");
			return modelAndView;
		}
		promotionService.setupShoppingcartPromotion(shoppingcart);
		Map<ShoppingcartSeparator, List<ShoppingcartItem>> map = shoppingcart.getGroupItems();
		Map<ShoppingcartSeparator, BigDecimal> mapPrice = shoppingcart.getGroupListPrice();
		if(map == null || map.isEmpty() || mapPrice == null  || mapPrice.isEmpty() ){
			LOG.info("shoppingcart map is null!");
			return modelAndView;
		}
		if(shopId != null && supplyTypeId != null){
			LOG.info(shopId  + " " + supplyTypeId);
			shoppingcartSeparator = new ShoppingcartSeparator(shopService.get(shopId),codeService.get(supplyTypeId));
		}else{
			shoppingcartSeparator = map.keySet().iterator().next();
		}
		shoppingcartItems = map.get(shoppingcartSeparator);
		BigDecimal listPrice =  mapPrice.get(shoppingcartSeparator);
		if(shoppingcartItems == null || shoppingcartItems.isEmpty()){
			LOG.info("shoppingcartItems map is null!");
			return modelAndView;
		}
		submitOrderForm = new CheckoutForm();
		if(customer==null){
			addresses=null;
			modelAndView.setViewName("/customer/checkout/anonym");
		}else{
			addresses = customer.getAddressList();
			String name="";
			if(customer.getSource().getId().equals(Code.USER_SOURCE_ALIPAY)){
				name=customer.getNickName();
			}else if(org.apache.commons.lang.StringUtils.isNotBlank(customer.getRealName())){
				name=customer.getRealName();
			}
			modelAndView.addObject("consigneeName",name);
			modelAndView.setViewName("/customer/checkout/new");	
		}
		if(null != addresses && addresses.size()>0){
			modelAndView.setViewName("/customer/checkout/post");	
			CustomerAddress address = customer.getUsualAddress();
			modelAndView.addObject("address", address);
			modelAndView.addObject("relationship", vliadCustomerAddressRelationship(address,request.getRemoteAddr()));
		}
		submitOrderForm.setInvoiceValue(listPrice);
		modelAndView.addObject("shop", shoppingcartSeparator.getShop());
		modelAndView.addObject("supplyType", shoppingcartSeparator.getSupplyType());
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("submitOrderForm", submitOrderForm);
		modelAndView.addObject("shoppingcartlist", shoppingcartItems);
		CustomerAddress customerAddress = customer.getUsualAddress();
		for (ShoppingcartItem shoppingcartItem : shoppingcartItems) {
			if(shoppingcartItem.getQuantity()>=10&&customerAddress.getPayment()!=null&&
					Payment.BANK.equals(customerAddress.getPayment().getId())){
				modelAndView.addObject("netpay",paymentService.get(Payment.NET_PAY));
			}
		}
		return modelAndView;
	}

	/**
	 * 提交订单页面结算促销信息
	 * @param customer
	 * @param shoppingcart
	 * @param checkoutForm
	 * @return
	 */
	@RequestMapping(value="/info",method=RequestMethod.POST)
	public ModelAndView info(@MyInject Customer customer,
			@MyInject Shoppingcart shoppingcart,
			HttpServletRequest request,
			CheckoutForm checkoutForm,BindingResult result){
		ModelAndView modelAndView = new ModelAndView("/customer/checkout/info");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		if(!BeanValidator.isValid(result, checkoutForm, CheckoutInfo.class)){
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,validError(result));
			return modelAndView;
		}
		Area area;
		Payment payment;
		CustomerAccount account;
		Code deliveryOptionCode;
		DeliveryType deliveryType;
		DeliveryInfo deliveryInfo;
		CustomerAddress customerAddress;
		List<DeliveryInfo>  deliveryInfos;
		BigDecimal deliveryFee = BigDecimal.ZERO;
		BigDecimal needPay = BigDecimal.ZERO;
		BigDecimal salePrice = BigDecimal.ZERO;
		BigDecimal listPrice = BigDecimal.ZERO;

		Shop shop = shopService.get(checkoutForm.getShopId());
		Code supplyType = codeService.get(checkoutForm.getSupplyTypeId());
		promotionService.setupShoppingcartPromotion(shoppingcart);
		ShoppingcartSeparator shoppingcartSeparator = new ShoppingcartSeparator(shop,supplyType);
		Map<ShoppingcartSeparator, BigDecimal> listPriceMap = shoppingcart.getGroupListPrice();
		Map<ShoppingcartSeparator, BigDecimal> salePricemap = shoppingcart.getGroupSalePrice();

		account = customer.getAccount();
		area = areaService.get(checkoutForm.getTownId());
		payment = paymentService.get(checkoutForm.getPaymentId());
		deliveryOptionCode = codeService.get(checkoutForm.getDeliveryOption());
		deliveryType = deliveryService.getDeliveryType(checkoutForm.getDeliveryTypeId());
		customerAddress = customerService.getCustomerAddress(checkoutForm.getAddressId());
		deliveryInfos = deliveryService.findDeliveryInfo(area,deliveryType, customerAddress.getAddress());
		if(listPriceMap == null || salePricemap == null 
				|| payment == null || customerAddress == null 
				|| deliveryOptionCode == null || account ==null
				|| org.apache.commons.collections.CollectionUtils.isEmpty(deliveryInfos)){
			LOG.info("null");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"object fetch null!");
			return modelAndView;
		}

		listPrice = listPriceMap.get(shoppingcartSeparator);
		salePrice = salePricemap.get(shoppingcartSeparator);

		customerAddress.setPayment(payment);				
		customerAddress.setDeliveryType(deliveryType);
		customerAddress.setDeliveryOption(deliveryOptionCode);
		customerService.updateAddress(customerAddress);

		deliveryInfo = deliveryInfos.get(0);
		if(!shop.getId().equals(Shop.WINXUAN_SHOP)){
			deliveryFee = shop.getDeliveryFee();
		}else{
			deliveryFee = deliveryInfo.getDeliveryFee(listPrice);
		}
		checkoutForm.setDeliveryFee(deliveryFee);
		needPay = salePrice.add(deliveryFee);
		checkoutForm.setSalePrice(salePrice);
		if(checkoutForm.getUsePresent() && needPay.compareTo(BigDecimalUtils.ZERO) >0){
			Present present = presentService.get(checkoutForm.getPresentId());
			BigDecimal presentMoney = present.getValue();
			BigDecimal presentPay = presentMoney.compareTo(needPay) > 0 ? needPay : presentMoney;
			checkoutForm.setPresentMoney(presentPay);
			needPay = needPay.subtract(presentPay);
		}
		checkoutForm.setNeedPay(needPay);
		setupCheckoutFormPromotion(checkoutForm,shop,shoppingcart.getGroupItems().get(shoppingcartSeparator),customer);
		needPay = checkoutForm.getNeedPay();
		if(checkoutForm.getUsePresentCard()  && needPay.compareTo(BigDecimalUtils.ZERO) >0){
			PresentCard[] cards = parsePresentCard(checkoutForm.getPresentCardId());
			BigDecimal presentCardPay = presentCardPay(cards,needPay);
			checkoutForm.setPresentCardMoney(presentCardPay);
			needPay = needPay.subtract(presentCardPay);
		}
		if(checkoutForm.getUseAccount()  && needPay.compareTo(BigDecimalUtils.ZERO) >0){
			BigDecimal balance = account.getBalance();
			BigDecimal pay = balance.compareTo(needPay) > 0 ? needPay : balance;
			checkoutForm.setAccountMoney(pay);
			needPay = needPay.subtract(pay);
		}
		checkoutForm.setNeedPay(needPay);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		modelAndView.addObject("checkoutInfo", checkoutForm);
		return modelAndView;
	}

	/**
	 * 为checkoutForm设置促销信息
	 * @param checkoutForm
	 */
	private void setupCheckoutFormPromotion(CheckoutForm checkoutForm,Shop shop,List<ShoppingcartItem> items,Customer customer){
		if(checkoutForm== null || shop == null){
			return;
		} 
		List<Promotion> promotions = null;
		BigDecimal actualMoney = checkoutForm.getActualMoney();
		BigDecimal presentMoney = null;
		
		boolean isPloy = true;
		if (customer!= null&&customer.getChannel()!=null&&Channel.CHANNEL_AGENT.equals(customer.getChannel().getId())) {
			isPloy=false;
		}
		//是联盟用户，优惠和代理一样
		boolean isUnion=false;
		UnionOrderValidate unionOrderValidate = new UnionOrderValidate();
		if(customer!= null)
		{
			isUnion  = unionOrderValidate.isUnionOrder(customer);
		}
		if(isUnion)
		{
			isPloy=false;
		}
		if(checkoutForm.getPresentId() != null && checkoutForm.getUsePresent()){
			Present present = presentService.get(checkoutForm.getPresentId());
			presentMoney = checkoutForm.getPresentMoney();
			if(present != null && !present.getBatch().isPloy()){
				isPloy = false;
			}
		}
		List<ProductSale> psList = new ArrayList<ProductSale>();
		for(ShoppingcartItem item : items){
			psList.add(item.getProductSale());
		}
		try {
			promotions = promotionService.findEffectivePromotion(psList);
		} catch (PromotionException e) {
			LOG.error(e.getMessage(), e);
		}
		if(promotions != null){
			for(Promotion promotion : promotions){
				if(Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(promotion.getType().getId()) && isPloy){
				
					PromotionPrice pp = promotion.getPriceForOrderSaveAmountByProductPromotion(items,presentMoney);
					if (pp != null) {
						ShoppingcartProm prom = new ShoppingcartProm(
								shop.getId(), checkoutForm.getSupplyTypeId(),
								pp);
						checkoutForm.add(prom);
						BigDecimal saveMoney = prom.getPromotionPrice().getSaveMoney();
						if (saveMoney.compareTo(BigDecimal.ZERO) > 0) {
							checkoutForm.addSaveMoney(saveMoney);
							BigDecimal needPay = checkoutForm.getNeedPay().subtract(saveMoney);
							checkoutForm.setNeedPay(needPay.compareTo(BigDecimal.ZERO) > 0 
									? needPay : BigDecimal.ZERO);
							actualMoney = checkoutForm.getActualMoney();
						}
					}
				}
				break;
			}
			for(Promotion promotion : promotions){
				if(!promotion.checkShopAvailable(shop)){
					continue;
				}
				if (Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT.equals(promotion.getType().getId()) && isPloy) { // 满省活动
					ShoppingcartProm prom = new ShoppingcartProm(
							shop.getId(),
							checkoutForm.getSupplyTypeId(),
							promotion.getPriceForOrderSaveAmountPromotion(actualMoney));
					checkoutForm.add(prom);
					BigDecimal saveMoney = prom.getPromotionPrice().getSaveMoney();
					if(saveMoney.compareTo(BigDecimal.ZERO)>0){
						checkoutForm.addSaveMoney(saveMoney);
						BigDecimal needPay = checkoutForm.getNeedPay().subtract(saveMoney);
						checkoutForm.setNeedPay(needPay.compareTo(BigDecimal.ZERO)>0?needPay:BigDecimal.ZERO);
						actualMoney = checkoutForm.getActualMoney();
					}
				}else if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(promotion.getType().getId()) && isPloy) {  //满送券活动
					ShoppingcartProm prom = new ShoppingcartProm(
							shop.getId(),
							checkoutForm.getSupplyTypeId(),
							promotion.getPriceForSendPresentPromotion(actualMoney));
					checkoutForm.add(prom);
				}else if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(promotion.getType().getId())) {
					if((!isPloy&&BASE_ORDER_SAVE_DELIVERYFEE_PROMOTION_ID.equals(promotion.getId()))
							||isPloy)
						{
							if(checkoutForm.getTownId() != null){
								Area area = areaService.get(checkoutForm.getTownId());
								PromotionPrice pp = promotion.getPriceForDeliveryFeePromotion(area,actualMoney);
								if(pp == null){
									continue;
								}
								ShoppingcartProm prom = new ShoppingcartProm(
										shop.getId(),
										checkoutForm.getSupplyTypeId(),
										pp);
								checkoutForm.add(prom);
								BigDecimal saveMoney = prom.getPromotionPrice().getSaveMoney();
								BigDecimal deliveryFee = checkoutForm.getDeliveryFee();
								if(saveMoney.compareTo(BigDecimal.ZERO)<0){
									checkoutForm.setDeliveryFee(BigDecimal.ZERO);
								}else if(saveMoney.compareTo(BigDecimal.ZERO)>0){
									BigDecimal fee = checkoutForm.getDeliveryFee().subtract(saveMoney);
									checkoutForm.setDeliveryFee(fee.compareTo(BigDecimal.ZERO)>0?fee:BigDecimal.ZERO);
								}
								BigDecimal savedFee = deliveryFee.subtract(checkoutForm.getDeliveryFee());
								BigDecimal needPay = checkoutForm.getNeedPay().subtract(savedFee);
								checkoutForm.setNeedPay(needPay.compareTo(BigDecimal.ZERO)>0?needPay:BigDecimal.ZERO);
							}
						}
				} else if (Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE.equals(promotion.getType().getId()) && isPloy) {    // 代理不享受
					if (checkoutForm.getTownId() != null
							&& checkoutForm.getDeliveryFee().compareTo(BigDecimal.ZERO) > 0
							&& !CollectionUtils.isEmpty(items)) {
						Area area = areaService.get(checkoutForm.getTownId());
						if(promotion.canSaveDeliveryFee(area,psList)){
							BigDecimal deliveryFee = checkoutForm.getDeliveryFee();
							checkoutForm.setDeliveryFee(BigDecimal.ZERO);
							BigDecimal needPay = checkoutForm.getNeedPay().subtract(deliveryFee);
							checkoutForm.setNeedPay(needPay.compareTo(BigDecimal.ZERO)>0?needPay:BigDecimal.ZERO);
						}
					}
				}
			}
		}
	}

	/**
	 * 	提交订单
	 * @param customer	当前用户
	 * @param shoppingcart	购物车
	 * @param checkoutForm	提交表单
	 * @param result
	 * @return
	 * @throws PresentCardException 
	 * @throws PresentException 
	 * @throws CustomerExtendIsNullException 
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView orderSubmit(@MyInject Customer customer,
			@MyInject Shoppingcart shoppingcart,
			@CookieValue(value="c",required=false) String cookie,
			HttpServletRequest request,
			CheckoutForm checkoutForm,BindingResult result) throws PresentException, PresentCardException, CustomerExtendIsNullException{
		long s = System.currentTimeMillis();
		ModelAndView modelAndView = new ModelAndView(new RedirectView("/shoppingcart"));
		Order order = new Order();
		List<ShoppingcartItem> shoppingcartItems = null;
		
		if(BeanValidator.isValid(result, checkoutForm, CheckoutSubmit.class)){
			try {
				
				if(checkoutForm.getUseAccount()
						&& (!customerPayPasswordService.hasPayPassword(customer)
								|| !customerService.validatePayPassword(customer, checkoutForm.getPayPassword()))){
					LOG.warn("customerExtend is null or payPassword error");
					return modelAndView;
				}
				
				CustomerAddress address = customerService.getCustomerAddress(checkoutForm.getAddressId());
				Map<ShoppingcartSeparator, List<ShoppingcartItem>> map = shoppingcart.getGroupItems();

				if(map==null || address == null ||  !address.getCustomer().equals(customer)){
					LOG.warn(String.format("map or address is null, Customer Name: %s", customer.getName()));
					return modelAndView;
				}
				ShoppingcartSeparator shoppingcartSeparator = new ShoppingcartSeparator(shopService.get(checkoutForm.getShopId()), 
						new Code(checkoutForm.getSupplyTypeId()));
				order.setCreator(customer);
				order.setCustomer(customer);
				order.setShop(shoppingcartSeparator.getShop());
				shoppingcartItems = map.get(shoppingcartSeparator);
				consigneeCovert(address,checkoutForm,order);
				porpertyConvert(address,shoppingcartItems, checkoutForm,address.getAddress(),order,customer);
				payInfoConvert(address,shoppingcartItems,checkoutForm, order);
				channelConvert(order, request);
				orderAnnexation(order,request);
				
				orderService.create(order);
				
				if(checkoutForm.getUseAccount()){
					List<Order> orderList = new ArrayList<Order>();
					orderList.add(order);
					//attention：not one transaction with create order
					orderService.payByAccount(orderList, customer, order.getCreator());
				}
				
				orderCompete(order, request);   
				flushShoppingcart(shoppingcart,shoppingcartItems);
				LOG.info(String.format("order: %s execute time:%s ",order.getId(),(System.currentTimeMillis() - s)));
				modelAndView.setView(new RedirectView("/customer/checkout/"+order.getId()));				
				return modelAndView;
			} catch (OrderInitException e) {
				LOG.error(e.getMessage(),e);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			} catch (CustomerAccountException e) {
				LOG.error(e.getMessage(),e);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			} catch (OrderException e) {
				LOG.error(e.getMessage(),e);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
			}
		}else{
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,validError(result));
		}
		return modelAndView;
	}

	/**
	 * 订单渠道信息设置
	 * @param order
	 * @param request
	 */
	private void channelConvert(Order order,HttpServletRequest request){
		boolean isUnion=false;
		Customer customer = order.getCustomer();
		UnionOrderValidate unionOrderValidate = new UnionOrderValidate();
		if(customer.getChannel().getId().equals(Channel.CHANNEL_AGENT)){
			order.setChannel(customer.getChannel());
		}else{
			order.setChannel(customer.getChannel());
			isUnion  = unionOrderValidate.isUnionOrder(order.getCustomer());
		}
		if (isUnion){
			Long unionId = unionOrderValidate.getUnionId();
			if(unionId != null){
				if(unionId.equals(UnionOrder.UNION_AD)){
					order.setChannel(channelService.get(Channel.CHANNEL_UNION_AD));
				}else{
					order.setChannel(channelService.get(Channel.CHANNEL_UNION_DOT));
				}
			}else{
				LOG.error(String.format("union order has't unoin id : ",unionId));
				order.setChannel(channelService.get(Channel.CHANNEL_UNION));
			}
		}

	}

	/**
	 * 下单完成后的反馈
	 * 联盟订单返单
	 * @param order
	 * @param request
	 */
	private void orderCompete(Order order,HttpServletRequest request){
		UnionOrderValidate unionOrderValidate = new UnionOrderValidate();
		boolean isUnion = unionOrderValidate.isUnionOrder(order.getCustomer());
		if(isUnion){
			Long unionId = unionOrderValidate.getUnionId();
			Cps cps = cpsFactory.get(unionId);
			if(cps !=null){
				try {						
					cps.saveAndSend(request,order);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}else{
				LOG.error(String.format("union cps null - union id: %s  - order id : ",unionId,order.getId()));
			}
		}
		if (Code.USER_SOURCE_QQ.equals(order.getCustomer().getSource().getId())
				&& Customer.UNION_LOGIN_TYPE == order.getCustomer().getLastLoginType()) {
			order.setUnite(true);
			extractor.sendToQQCps(order);
		}
	}
	
	/**
	 * 订单附加信息
	 * @param order
	 */
	private void orderAnnexation(Order order,HttpServletRequest request){
		String requestIP = request.getRemoteAddr();
		OrderNote orderNote = new OrderNote();
		order.setNote(orderNote);
		orderNote.setAddress(requestIP);
		orderNote.setUserAgent(request.getHeader("user-agent"));
		orderNote.setCookie(CookieUtils.getCookieValue(request, "c"));
		orderNote.setOrder(order);
	}

	/**
	 *  转换订单信息
	 * @return
	 * @throws OrderInitException 
	 */
	private void porpertyConvert(CustomerAddress address ,List<ShoppingcartItem> shoppingcartItems,
			CheckoutForm checkOutOrderForm,String customerAddress,
			Order order,Customer customer) throws OrderInitException{
		Shop shop = order.getShop();
		OrderItem orderItem = null;
		DeliveryInfo deliveryInfo;
		int orderTotalQuantity = 0;
		BigDecimal salePrice = BigDecimalUtils.ZERO;
		BigDecimal listPrice = BigDecimalUtils.ZERO;

		for(ShoppingcartItem item : shoppingcartItems){
			orderItem = new OrderItem();
			orderItem.setShop(item.getShop());
			ProductSale productSale = item.getProductSale();
			if(customer!= null){
				productSale.setPurchasedQuantity(orderService.getPurchaseQuantityToday(
						customer, productSale));
			}
			productSale.setPurchasedQuantityAll(orderService.getPurchaseQuantityToday(productSale));
			productSale.getProduct().getCategory().getCode();
			orderItem.setProductSale(productSale);
			orderItem.setSalePrice(item.getSalePrice());
			orderItem.setListPrice(item.getListPrice());
			orderItem.setReturnQuantity(MagicNumber.ZERO);
			orderItem.setDeliveryQuantity(MagicNumber.ZERO);
			orderItem.setPurchaseQuantity(item.getQuantity());
			order.addItem(orderItem);
			orderTotalQuantity=+item.getQuantity();
			salePrice = salePrice.add(item.getTotalSalePrice());
			listPrice = listPrice.add(item.getTotalListPrice());
		}
		order.setSalePrice(salePrice);
		order.setListPrice(listPrice);
		order.setPurchaseQuantity(orderTotalQuantity);
		order.setChannel(channelService.get(Channel.CHANNEL_EC));

		List<DeliveryInfo> deliveryInfos = deliveryService.findDeliveryInfo(address.getTown(),
				address.getDeliveryType(), customerAddress);

		if(deliveryInfos!= null && !deliveryInfos.isEmpty()){
			deliveryInfo = deliveryInfos.get(0);
			if(!shop.getId().equals(Shop.WINXUAN_SHOP)){
				order.setDeliveryFee(shop.getDeliveryFee().setScale(2));		
			}else{
				order.setDeliveryFee(deliveryInfo.getDeliveryFee(order.getListPrice()).setScale(2));										//运费
			}
			order.setDeliveryType(deliveryInfo.getDeliveryType());
		}else{
			throw new OrderInitException(order, "deliveryfee error!");
		}
	}


	/**
	 * 支付信息转换
	 * 如有优惠 先处理优惠信息
	 * @throws OrderInitException 
	 */
	private void payInfoConvert(CustomerAddress address,List<ShoppingcartItem> shoppingcartItems,CheckoutForm checkOutOrderForm,Order order) throws OrderInitException{
		BigDecimal pay = null;
		Payment payment = null;
		Customer customer = order.getCustomer();
		Set<OrderPayment> orderPayments = new HashSet<OrderPayment>();
		order.setSaveMoney(BigDecimalUtils.ZERO);
		BigDecimal needPay = order.getTotalPayMoney();					//订单需要支付的金额 实洋+运费-优惠金额
		if(checkOutOrderForm.getUsePresent() && needPay.compareTo(BigDecimalUtils.ZERO)>0){		//添加礼券的支付信息
			List<Present> presents = presentService.findEffectivePresentByCustomerAndShoppingCart(customer, shoppingcartItems);
			Present present = presentService.get(checkOutOrderForm.getPresentId());
			if(present == null || !presents.contains(present)){
				throw new OrderInitException(order, "没有对应的礼券或者订单不符合礼券规则!");
			}
			pay = needPay.compareTo(present.getValue()) > 0 ? present.getValue() : needPay;
			payment = paymentService.get(Payment.COUPON);
			orderPayments.add(parseOrderPayment(order, payment, pay,present.getId().toString()));
			needPay =needPay.subtract(pay);
		}
		order.setPaymentList(orderPayments);
		promotionService.setupOrderPromotion(order);
		needPay = needPay.subtract(order.getPromotionSaveMoney());
		if(checkOutOrderForm.getUsePresentCard() && needPay.compareTo(BigDecimalUtils.ZERO)>0){		//添加礼品卡的支付信息
			PresentCard[] cards =  validPresentCard(checkOutOrderForm.getPresentCardId(),checkOutOrderForm.getPresentCardPass(),customer);
			payment = paymentService.get(Payment.GIFT_CARD);
			for(PresentCard card : cards){
				pay = needPay.compareTo(card.getBalance())>0 ? card.getBalance() : needPay;
				orderPayments.add(parseOrderPayment(order, payment, pay,card.getId()));
				needPay = needPay.subtract(pay);
				if(needPay.compareTo(BigDecimalUtils.ZERO)==0){
					break;
				}
			}
		}
		if(checkOutOrderForm.getUseAccount() && needPay.compareTo(BigDecimalUtils.ZERO)>0){			//添加暂存款的支付信息
			CustomerAccount account = customer.getAccount();
			if(account == null){
				throw new OrderInitException(order, "account==null");
			}
			pay =needPay.compareTo(account.getBalance())>0 ? account.getBalance():needPay;
			payment = paymentService.get(Payment.ACCOUNT);
			orderPayments.add(parseOrderPayment(order, payment, pay,null));
			needPay =needPay.subtract(pay);
		}
		if(needPay.compareTo(BigDecimalUtils.ZERO)>0){
			orderPayments.add(parseOrderPayment(order, address.getPayment(), needPay,null));
		}
		order.setPaymentList(orderPayments);

		if(checkOutOrderForm.getNeedInvoice()){
			OrderInvoice orderInvoice = order.getInvoice(order.isExistMerchandiseItem()?"百货":"图书",
					new Code(Code.ORDER_INVOICE_MODE_NORMAL),
					orderInvoiceService.calcInvoiceMoney(order, checkOutOrderForm.getInvoiceValue()));
			orderInvoice.setOperator(employeeService.get(Employee.SYSTEM)); 
			orderInvoice.setTransferred(true);
			order.addInvoice(orderInvoice);
		}
	}


	/**
	 *  转换订单收货信息
	 * @param address
	 * @param checkOutOrderForm
	 * @return
	 */
	private void consigneeCovert(CustomerAddress address,CheckoutForm checkOutOrderForm,Order order){
		OrderConsignee consignee = new OrderConsignee();
		order.setConsignee(consignee);
		consignee.setOrder(order);
		consignee.setConsignee(address.getConsignee());
		consignee.setEmail(address.getEmail());
		consignee.setCountry(address.getCountry());
		consignee.setProvince(address.getProvince());
		consignee.setCity(address.getCity());
		consignee.setDistrict(address.getDistrict());
		consignee.setTown(address.getTown());
		consignee.setAddress(address.getAddress());
		consignee.setPhone(address.getPhone());
		consignee.setMobile(address.getMobile());
		consignee.setDeliveryOption(address.getDeliveryOption());			
		consignee.setZipCode(address.getZipCode());
		consignee.setNeedInvoice(checkOutOrderForm.getNeedInvoice());
		consignee.setInvoiceTitle(StringUtils.isNullOrEmpty(checkOutOrderForm.getInvoiceTitle()) ? 
				address.getConsignee() : checkOutOrderForm.getInvoiceTitle());
		consignee.setInvoiceType(new Code(Code.INVOICE_TYPE_GENERAL));	
		Long tittleType = checkOutOrderForm.getInvoiceType() == null ? Code.INVOICE_TITLE_TYPE_PERSONAL :checkOutOrderForm.getInvoiceType();
		consignee.setInvoiceTitleType(new Code(tittleType));
	}

	/**
	 * 从购物车里清除订单项
	 * @param order
	 */
	private void flushShoppingcart(Shoppingcart shoppingcart,
			List<ShoppingcartItem> shoppingcartItems) {
		for (ShoppingcartItem item : shoppingcartItems) {
			ProductSale productSale = item.getProductSale();
			shoppingcartService.add(shoppingcart, productSale, 0);
		}
		Cookie cookie = new Cookie(Constant.COOKIE_SHOPPINGCART_COUNT_NAME,
				String.valueOf(shoppingcart.getCount()));
		cookie.setMaxAge(Constant.COOKIE_CLIENT_AGE);
		cookie.setPath(Constant.COOKIE_PATH);
		cookie.setDomain(Constant.COOKIE_DOMAIN);
		CookieUtils.writeCookie(cookie);
	}

	/**
	 * 验证地址内部信息是否一致
	 * 作为下单页面是否触发用户选择的标识
	 * @param customerAddress
	 * @param requestIP
	 * @return 
	 */
	private int vliadCustomerAddressRelationship(CustomerAddress customerAddress,String requestIP){
		Long now = null;
		int result = MagicNumber.NEGATIVE_ONE;
		Area area = customerAddress.getTown();
		Payment payment =  customerAddress.getPayment();
		DeliveryType deliveryType = customerAddress.getDeliveryType();
		if(payment == null || deliveryType == null){
			return result;
		}
		Long payTypeCodeId = payment.getType().getId();
		Long old = customerAddress.getDeliveryType().getId();
		List<DeliveryInfo> deliveryInfos =deliveryService
		.findDeliveryInfo(area,deliveryType, requestIP);
		for(DeliveryInfo info : deliveryInfos){
			now= info.getDeliveryType().getId();
			if(old.equals(now)){
				result = MagicNumber.ZERO;	//如果配送方式匹配，返回为0
			}
		}
		List<Payment> payments= paymentService.find(area, deliveryType); // 除线上支付外的支付方式
		if(!payments.contains(payment) && !payTypeCodeId.equals(Code.PAYMENT_TYPE_ONLINE)){
			result += MagicNumber.NEGATIVE_TWO; 		//除开线上支付，如不匹配，返回值减去2
		}
		return result;
	}


	private String validError(BindingResult result){
		List<FieldError> errors =  result.getFieldErrors();
		StringBuffer sb = new StringBuffer();
		for(FieldError error : errors){
			LOG.info(error.getField()+" "+error.getDefaultMessage());
			sb.append(error.getDefaultMessage());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 *  验证礼品卡
	 * @param cardsId
	 * @param password
	 */
	private PresentCard[] validPresentCard(String[] cardsIds,String[] passwords,Customer customer) throws OrderInitException{
		if(cardsIds == null || passwords == null || cardsIds.length != passwords.length){
			throw new RuntimeException("礼品卡、密码数量不符!");
		}
		PresentCard[] presentCards = new PresentCard[cardsIds.length];
		for(int i=0 ; i< cardsIds.length ; i++){
			try {
				String pass = "-1".equals(passwords[i]) ? null : passwords[i];
				PresentCard card = presentCardService.get(cardsIds[i],pass, customer.getId());
				presentCards[i] = card;
			} catch (PresentCardException e) {
				LOG.error(e.getMessage(), e);
				throw new OrderInitException(null,e.getMessage());
			}
		}
		return presentCards;
	}

	private PresentCard[] parsePresentCard(String[] presentCardsId){
		if(presentCardsId != null){
			PresentCard[] presentCards = new PresentCard[presentCardsId.length];
			int i = 0;
			for(String id : presentCardsId){
				PresentCard presentCard = presentCardService.get(id); 
				if(presentCard != null){
					presentCards[i] = presentCard; 	
					i++;
				}
			}
			return presentCards;
		}
		return null;
	}

	/**
	 * 得到礼品卡支付的金额
	 * @param cards
	 * @param needPay
	 * @return
	 */
	private BigDecimal presentCardPay(PresentCard[] cards ,BigDecimal needPay){
		BigDecimal needPayCurrent = needPay;
		BigDecimal result = BigDecimalUtils.ZERO;
		for(PresentCard card : cards){
			result = result.add(card.getBalance());
		}
		result = result.compareTo(needPayCurrent) > 0 ? needPayCurrent : result;
		return result;
	}

	/**
	 *  转换订单支付信息
	 * @param order
	 * @param payment
	 * @param realPay
	 * @return
	 * @throws OrderInitException 
	 */
	private OrderPayment parseOrderPayment(Order order,Payment payment, BigDecimal realPay,String outId) throws OrderInitException{

		Long paymentId = payment.getId();
		OrderPayment orderPayment = new OrderPayment();
		orderPayment.setOrder(order);
		orderPayment.setPayMoney(realPay);
		orderPayment.setPay(false);
		orderPayment.setCreateTime(new Date());
		orderPayment.setPayment(payment);
		orderPayment.setDeliveryMoney(BigDecimalUtils.ZERO);
		orderPayment.setReturnMoney(BigDecimalUtils.ZERO);

		if(paymentId.equals(Payment.COUPON)
				|| paymentId.equals(Payment.GIFT_CARD)){
			if(realPay.compareTo(BigDecimal.ZERO)<1){
				throw new OrderInitException(order,payment.getName()+"支付方式金额不能为0");
			}
			PaymentCredential credential = new PaymentCredential();
			credential.setCustomer(order.getCustomer());
			credential.setMoney(realPay);
			credential.setOperator(order.getCustomer());
			credential.setPayer(order.getCustomer().getName());
			credential.setPayment(payment);
			credential.setPayTime(new Date());
			credential.setOuterId(outId);
			paymentService.save(credential);
			orderPayment.setCredential(credential);
		}
		return orderPayment;
	}


}
