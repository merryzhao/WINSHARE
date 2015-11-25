package com.winxuan.ec.front.controller.customer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderInvoiceException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderInvoice;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.order.OrderReceive;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.order.OrderInvoiceService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 订单
 * @author  zhoujun
 * @version 1.0,2011-8-6
 */

@Controller
@RequestMapping(value="/customer/order")
public class OrderController {

	private static final Log LOG = LogFactory.getLog(OrderController.class);
	
	private static final String ORDER_INVOIEC_FORMNAME = "orderInvoiceCreateForm";
	private static final String TITLE_TYPE = "titleTypes";
	private static final String ORDERIDNAME = "orderId";
	private static final String BOOK="图书";
	private static final String MERCHANDISE="百货";
	private static final String PROCESSSTATUS ="processStatus";
	private static final String LAST_MONTH = "lastMonth";
 	private static final Integer SEX_MONTH = -6;
	
	
	//礼券
	private static final Long PAYMENT_TYPE_CERTIFICATE = 20L;
	//礼品卡
	private static final Long PAYMENT_TYPE_GIFT = 21L;
	//暂存款
	private static final Long PAYMENT_TYPE_ACCOUNT = 15L;
	//支付金额
	private static final String RETRUN_PAY = "pay";
	//暂存款金额
	private static final String RETRUN_ACCOUNT = "account";
	//礼品卡金额
	private static final String RETRUN_GIFT = "gift";
	//礼券金额
	private static final String RETRUN_CERTIFICATE = "certificate";
	//运费
	private static final String RETRUN_DELIVERYFREE = "deliveryfree";
	//总金额
	private static final String RETRUN_ALL = "all";
	//总金额
	private static final BigDecimal INITIAL_AMOUNT = new BigDecimal(0.00);
	
	private static final List<Long> ORDER_PROCESSSTATUS = Arrays.asList(
			Code.ORDER_PROCESS_STATUS_DELIVERIED,Code.ORDER_PROCESS_STATUS_COMPLETED
			,Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG);
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OrderInvoiceService orderInvoiceService;
	
	@Autowired
	private BatchPayService batchPayService;
	
	@Autowired
	private PaymentService paymentService;

	/**
	 * 查询订单列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(OrderForm orderForm,@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView  = new ModelAndView("/customer/order/list");
		Map<String, Object> parameters = orderForm.generateQueryMap();		
		Map<String,Object> selectedParameters = new HashMap<String,Object>();
		selectedParameters.putAll(parameters);
		//处理查询条件, 对一些值具有多个意思的进行转换
		if(parameters.containsKey(PROCESSSTATUS)){
			List<Long > statusList = new ArrayList<Long>();
			if(Code.ORDER_PROCESS_STATUS_DELIVERIED.equals(parameters.get(PROCESSSTATUS))){
			    statusList.add(Code.ORDER_PROCESS_STATUS_DELIVERIED);
				statusList.add(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG);
			}
			if(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL.equals(parameters.get(PROCESSSTATUS))){
				statusList.add(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
				statusList.add(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL);
				statusList.add(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL);
				statusList.add(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
				statusList.add(Code.ORDER_PROCESS_STATUS_ERP_CANCEL);
			}
			if(statusList.size() > 0){
				parameters.put(PROCESSSTATUS, statusList);
			}
		}
		if( parameters == null || parameters.size() < 1 ){
			List<Long> processList = new ArrayList<Long>();
				processList.add(Code.ORDER_PROCESS_STATUS_NEW);
				processList.add(Code.ORDER_PROCESS_STATUS_WAITING_PICKING);
				processList.add(Code.ORDER_PROCESS_STATUS_PICKING);
				processList.add(Code.ORDER_PROCESS_STATUS_DELIVERIED);	
				processList.add(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG);	
				processList.add(Code.ORDER_PROCESS_STATUS_COMPLETED);					
				
				//已取消
				processList.add(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
				processList.add(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL);
				processList.add(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL);
				processList.add(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
				processList.add(Code.ORDER_PROCESS_STATUS_ERP_CANCEL);
			parameters.put(PROCESSSTATUS, processList);
		}	
		if(!parameters.containsKey("startCreateTime")){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, SEX_MONTH);
			parameters.put("startCreateTime", cal.getTime());
		}
		if(parameters.containsKey("payment") && new Long(0).equals(parameters.get("payment"))){
			List<Payment> payments = paymentService.find(Code.PAYMENT_TYPE_ONLINE);
			List<Long> pt = new ArrayList<Long>();
			for(Payment pa : payments){
				if(pa.isAvailable()){
					pt.add(pa.getId());
				}
			}
			parameters.put("payment", pt.toArray(new Long[0]));
		}
		if(parameters.containsKey("invoiceType")){
			if(new Long(0).equals(parameters.get("invoiceType"))){
				parameters.put("isnull", 0);
			}else{
				parameters.put("isnotnull", 0);
			}
		}	
		parameters.put("customerId", customer.getId());	
		List<Order> orderList = orderService.find(parameters, Short.valueOf("4"), pagination);
		modelAndView.addObject("selectedParameters",selectedParameters);
		List<Code> processStatusList= codeService.get(Code.ORDER_PROCESS_STATUS).getAvailableChildren();
		List<Code> usefulStatusList =new ArrayList<Code>();
		for(Code code:processStatusList){
			if(code.getId().compareTo(Code.ORDER_PROCESS_STATUS_COMPLETED)<=0){
				usefulStatusList.add(code);
			}
		}
		Code cancel = new Code(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
		cancel.setName("已取消");
		usefulStatusList.add(cancel);
		
		modelAndView.addObject("processStatusList",usefulStatusList);
		modelAndView.addObject("paymentTypeList",codeService.get(Code.PAYMENT_TYPE).getAvailableChildren());
		modelAndView.addObject("orderList", orderList);
		modelAndView.addObject("pagination", pagination);
		//补开发票的服务器时间对比
		Calendar caltime=Calendar.getInstance();
		caltime.add(Calendar.MONTH, -1);
		modelAndView.addObject(LAST_MONTH, caltime.getTime());
		return  modelAndView;
	}  
	@RequestMapping(value ="/cancelList",method = RequestMethod.GET)
	public ModelAndView cancelList(OrderForm orderForm,@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView  = new ModelAndView("/customer/order/cancelList");
		Map<String, Object> parameters = orderForm.generateQueryMap();
		Map<String,Object> selectedParameters = new HashMap<String,Object>();
		selectedParameters.putAll(parameters);
		if( parameters == null || parameters.size() < 1 ){	
			List<Long> processList = new ArrayList<Long>();
			processList.add(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
			processList.add(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL);
			processList.add(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL);
			processList.add(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL);
			processList.add(Code.ORDER_PROCESS_STATUS_ERP_CANCEL);
			parameters.put("processStatus", processList);
		}
		parameters.put("customerId", customer.getId());	
		List<Order> orderList = orderService.find(parameters, Short.valueOf("4"), pagination);
		List<Code> usefulStatusList =new ArrayList<Code>();
		List<Code> processStatusList= codeService.get(Code.ORDER_PROCESS_STATUS).getAvailableChildren();
		for(Code code:processStatusList){
			if(code.getId().compareTo(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)>=0 && code.getId().compareTo(Code.ORDER_PROCESS_STATUS_ERP_CANCEL)<=0){
				usefulStatusList.add(code);
			}
		}
		modelAndView.addObject("selectedParameters",selectedParameters);
		modelAndView.addObject("paymentTypeList",codeService.get(Code.PAYMENT_TYPE).getAvailableChildren());
		modelAndView.addObject("orderList", orderList);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ModelAndView get(@PathVariable("id") String id,@MyInject Customer customer){
		ModelAndView modelAndView  = new ModelAndView("/customer/order/order_detail");
		Order order  = orderService.get(id);
		if(order != null && order.getCustomer().equals(customer)){			
			modelAndView.addObject("order",order);
		}else{
			modelAndView.setViewName("/customer/order/error");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "你不存在该订单");
		}
		return modelAndView;
	}
	

	/**
	 * 订单确认收货
	 * @param id
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/{id}/receive",method=RequestMethod.POST)
	public ModelAndView receive(@PathVariable("id") String id,@MyInject Customer customer, OrderReceive receive){
		ModelAndView modelAndView  = new ModelAndView("/customer/order/receive");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		Order order  = orderService.get(id);
		receive.getExpressManner();
		if(order != null && order.getCustomer().equals(customer)){
			receive.setOrder(order);
			receive.setCreateTime(new Date());
			receive.setAssess(codeService.get(Code.ORDER_RECEIVE_VERY_GOOD));
			receive.setSource(codeService.get(Code.ORDER_CONFIRM_TYPE_PERSON));
			try {
				orderService.receive(receive);
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"操作成功!");
			} catch (Exception e) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"确认失败!");
			}
		}else{
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"订单用户错误!");
		}
		return modelAndView;

	}
	
	
	@RequestMapping(value="/{id}/cancel",method=RequestMethod.GET)
	public ModelAndView cancel(@PathVariable("id") String id,@MyInject Customer customer) throws PresentCardException, PresentException, ReturnOrderException{
		ModelAndView modelAndView  = new ModelAndView("/customer/order/cancel");
		Order order  = orderService.get(id);
		//先款后货并且未支付无法取消退款
		if(order.getPaymentStatus().equals(Code.ORDER_PAY_STATUS_NONE)
				&& order.getPayType().equals(Code.ORDER_PAY_TYPE_FIRST_PAY)){
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"正在付款中，无法取消");
			return modelAndView;
		}
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		if(order != null && order.getCustomer().equals(customer)){
			Code cancelStatus = codeService.get(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL);
			try {
				orderService.cancel(order, cancelStatus, customer);
			} catch (OrderStatusException e) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
				LOG.error(e.getMessage(), e);
			} catch (CustomerAccountException e) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
				LOG.error(e.getMessage(), e);
			}
		}else{
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/{id}/pay", method = RequestMethod.GET)
	public ModelAndView paySingle(@PathVariable("id") String id,HttpServletRequest request) {
		return pay(new String[] { id }, request);
	}

	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public ModelAndView payMulti(@RequestParam("id")String[] idArray,HttpServletRequest request){
		return pay(idArray, request);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	private ModelAndView pay(String[] idArray, HttpServletRequest request){
		if(StringUtils.isBlank(request.getParameter("bank"))){
			return renderPayError("支付方式不存在");
		}
		Bank bank = BankConfig.getBank(Long.valueOf(request.getParameter("bank")));
		if(bank == null){
			return renderPayError("未找到对应的支付方式");
		}
		try{
			BatchPay batchPay = batchPayService.pay(idArray,bank);
			bank.init(batchPay.getId(), batchPay.getTotalMoney(), request.getParameter("code"));
			ModelAndView modelAndView  = new ModelAndView(bank.getViewName());
			modelAndView.addObject("bank", bank);
			return modelAndView;
		}catch (Exception e) {
			LOG.info(e.getMessage(),e);
			return renderPayError(e.getMessage());
		}
	}
	
	
	private ModelAndView renderPayError(String msg) {
		ModelAndView modelAndView = new ModelAndView("/order/bank/error");
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, msg);
		return modelAndView;
	}
	
	@RequestMapping(value="/logisticsinfo/{id}", method=RequestMethod.GET)
	public ModelAndView fetchLogisticsInfo(@PathVariable("id")String id,@MyInject Customer customer){
		ModelAndView view = new ModelAndView("/customer/order/order_logistics");
		Order order = orderService.get(id);
		if(!order.getCustomer().getId().equals(customer.getId())){
			view.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			return view;
		}
		//物流信息, 会导致连接超时
		orderService.fetchLogisticsInfo(order);
		view.addObject("order", order);
		view.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return view; 
	}
	
	/**
	 * 跳转到补开发票页面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "invoice/{id}", method = RequestMethod.GET)
	public ModelAndView newOrderInvoice(@PathVariable("id") String orderId) {
		final Long codeId = 3459L;
		Order order = orderService.get(orderId);
		ModelAndView mav = new ModelAndView("/customer/order/invoice");
		mav.addObject(ORDERIDNAME, orderId);
		OrderInvoiceCreateForm orderInvoiceCreateForm = new OrderInvoiceCreateForm();
		//发票金额为0不能开发票
		if(order.getInvoiceMoney().compareTo(INITIAL_AMOUNT) != 1){
			mav.setViewName("/customer/order/error");
			mav.addObject(ControllerConstant.MESSAGE_KEY, "订单无法开取发票");
			return mav;
		}
		//计算订单各种类型的支付情况
		Map<String,BigDecimal> orderPay = new HashMap<String, BigDecimal>();
		orderPay.put(RETRUN_ACCOUNT,INITIAL_AMOUNT);
		orderPay.put(RETRUN_CERTIFICATE,INITIAL_AMOUNT);
		orderPay.put(RETRUN_GIFT,INITIAL_AMOUNT);
		BigDecimal all  = INITIAL_AMOUNT;
		for(OrderPayment payment : order.getPaymentList()){
			all = all.add(payment.getPayMoney());
			if(payment.getPayment().getId()==PAYMENT_TYPE_ACCOUNT){
				orderPay.put(RETRUN_ACCOUNT,orderPay.get(RETRUN_ACCOUNT).add(payment.getPayMoney()));
			}else if(payment.getPayment().getId()==PAYMENT_TYPE_CERTIFICATE){
				orderPay.put(RETRUN_CERTIFICATE,orderPay.get(RETRUN_CERTIFICATE).add(payment.getPayMoney()));
			}else if(payment.getPayment().getId()==PAYMENT_TYPE_GIFT){
				orderPay.put(RETRUN_GIFT, orderPay.get(RETRUN_GIFT).add(payment.getPayMoney()));
			}
		}
		orderPay.put(RETRUN_PAY,order.getInvoiceMoney());
		orderPay.put(RETRUN_DELIVERYFREE,order.getDeliveryFee());
		orderPay.put(RETRUN_ALL,all);
		orderInvoiceCreateForm.setPayMentMoney(orderPay);
		orderInvoiceCreateForm.setContent(order.isExistMerchandiseItem()?MERCHANDISE:BOOK);
		orderInvoiceCreateForm.setCountry(order.getConsignee().getCountry()
				.getId());
		orderInvoiceCreateForm.setProvince(order.getConsignee().getProvince()
				.getId());
		orderInvoiceCreateForm.setCity(order.getConsignee().getCity().getId());
		orderInvoiceCreateForm.setDistrict(order.getConsignee().getDistrict()
				.getId());
		orderInvoiceCreateForm
		.setConsignee(order.getConsignee().getConsignee());
		orderInvoiceCreateForm.setTown(order.getConsignee().getTown().getId());
		orderInvoiceCreateForm.setOrderId(orderId);
		Set<Code> titleTypes = codeService.get(codeId).getChildren();
		mav.addObject(TITLE_TYPE, titleTypes);
		// 默认是order.deliveryQuantity，如果未发货，取order.purchaseQuantity
		if (!order.isDeliveried()) {
			orderInvoiceCreateForm.setQuantity(order.getPurchaseQuantity());
		} else {
			orderInvoiceCreateForm.setQuantity(order.getDeliveryQuantity());
		}
		// 如果手机号码为空 则设置成办公电话
		if (order.getConsignee().getMobile() != null) {
			orderInvoiceCreateForm.setMobile(order.getConsignee().getMobile());
		} else {
			orderInvoiceCreateForm.setMobile(order.getConsignee().getPhone());
		}
		orderInvoiceCreateForm.setEmail(order.getConsignee().getEmail());
		orderInvoiceCreateForm.setZipCode(order.getConsignee().getZipCode());
		orderInvoiceCreateForm.setAddress(order.getConsignee().getAddress());
		orderInvoiceCreateForm.setMoney(order.getInvoiceMoney());
		mav.addObject(ORDER_INVOIEC_FORMNAME, orderInvoiceCreateForm);
		return mav;
	}
	
	
	/**
	 * 补开发票
	 * @param orderInvoiceCreateForm
	 * @return
	 * @throws ParseException
	 * @throws OrderStatusException
	 * @throws CustomerAccountException
	 * @throws OrderInvoiceException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView createInvoice(@Valid OrderInvoiceCreateForm orderInvoiceCreateForm,
			@MyInject Customer customer) throws ParseException,OrderStatusException,
			CustomerAccountException,OrderInvoiceException{
		final Long codeId = 15002L;
		ModelAndView mav = new ModelAndView("/order/orderinvoice");
		Order order = orderService.get(orderInvoiceCreateForm.getOrderId());
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
			//订单是否具备补开发票条件
			if(order==null||order.getCustomer().getId()!=customer.getId()||
					order.getConsignee().isNeedInvoice()||!ORDER_PROCESSSTATUS.contains(order.getProcessStatus().getId())
					||order.getInvoiceList().size()>0||order.getLastProcessTime().before(cal.getTime())){
				mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_WARNING);
				return mav;
			}
		OrderInvoice orderInvoice = new OrderInvoice();
		orderInvoice.setDc(order.getDistributionCenter().getDcDest());//设置实际出货仓库
		orderInvoice.setContent(order.isExistMerchandiseItem()?MERCHANDISE:BOOK);// 设置发票内容
		orderInvoice.setAddress(orderInvoiceCreateForm.getAddress());// 设置详细地址
		orderInvoice.setCity(new Area(orderInvoiceCreateForm.getCity()));// 设置城市
		orderInvoice.setCountry(new Area(orderInvoiceCreateForm.getCountry()));// 设置国家
		orderInvoice.setProvince(new Area(orderInvoiceCreateForm.getProvince()));// 设置省
		orderInvoice.setDistrict(new Area(orderInvoiceCreateForm.getDistrict()));// 设置地区
		orderInvoice.setTown(new Area(orderInvoiceCreateForm.getTown()));//设置乡镇
		orderInvoice.setCreateTime(new Date());// 设置开票时间
		orderInvoice.setEmail(customer.getEmail());
		orderInvoice.setPhone(customer.getPhone());
		orderInvoice.setMobile(orderInvoiceCreateForm.getMobile());
		orderInvoice.setMoney(orderInvoiceService.calcInvoiceMoney(order, order.getInvoiceMoney()));
		// 默认是order.deliveryQuantity，如果未发货，取order.purchaseQuantity
		if (!order.isDeliveried()) {
			orderInvoice.setQuantity(order.getPurchaseQuantity());
		} else {
			orderInvoice.setQuantity(order.getDeliveryQuantity());
		}
		orderInvoice.setConsignee(orderInvoiceCreateForm.getConsignee());
		//收货人名称 
		orderInvoice.setTitle(StringUtils.isBlank(orderInvoiceCreateForm.getTitle())?order.getConsignee().getConsignee():orderInvoiceCreateForm.getTitle());
		orderInvoice.setZipCode(orderInvoiceCreateForm.getZipCode());
		orderInvoice.setTitleType(new Code(orderInvoiceCreateForm.getTitleType()));// 发票类型
		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setMode(new Code(codeId));// 设置开票方式
		orderInvoice.setDeliveryTime(order.getDeliveryTime());// 发货时间
		orderInvoice.setDeliveryOption(order.getConsignee().getDeliveryOption());
		orderInvoice.setOperator(customer);
		if(order.getConsignee().getInvoiceType()!=null){
			orderInvoice.setType(order.getConsignee().getInvoiceType());
		}
		orderInvoice.setOrder(order);
		order.addInvoice(orderInvoice);
		try {
			orderInvoiceService.createOrderInvoice(orderInvoice);
		} catch (Exception e) {
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			return mav;
		}
		mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	

//--------下方功能已移至前端实现---------------------------
//--------edit by Min-Huang-----------------------------
//--------2011-12-05 14:10------------------------------
//	/**
//	 * 将订单的所有商品添加到购物车
//	 * @param orderId
//	 * @return
//	 */
//	@RequestMapping(value="/addAllFromCart/{id}", method=RequestMethod.GET)
//	public ModelAndView addAllFromCart(@PathVariable("id")String orderId){
//		ModelAndView modelAndView = new ModelAndView("redirect:/shoppingcart/modify");
//		modelAndView.addObject("p", fetchProductSels(orderId));
//		return modelAndView;
//	}
//	
//	/**
//	 * 将订单中的所有商品添加到收藏
//	 * @param orderId
//	 * @return
//	 */
//	@RequestMapping(value="/addAllFromFavorite/{id}", method=RequestMethod.GET)
//	public ModelAndView addAllFromFavorite(@PathVariable("id")String orderId){
//		ModelAndView view = new ModelAndView("redirect:/customer/favorite/add");
//		view.addObject("p", fetchProductSels(orderId));
//		return view;
//	}
//	
//	private Long[] fetchProductSels(String orderId){
//		Order order = orderService.get(orderId);
//		List<Long> pss = new ArrayList<Long>();
//		for(ProductSale ps : order.getProductSales()){
//			pss.add(ps.getId());
//		}
//		return pss.toArray(new Long[0]);
//	}
//	
}
