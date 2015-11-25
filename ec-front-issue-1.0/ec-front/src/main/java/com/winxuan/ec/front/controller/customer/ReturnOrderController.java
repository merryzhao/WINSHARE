package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.returnorder.ReturnOrder;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.returnorder.ReturnOrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-28
 */
@Controller
@RequestMapping(value="/customer/returnorder")
public class ReturnOrderController {
	
	private static final Log LOG = LogFactory.getLog(ReturnOrderController.class);	
	
	//退货订单、是否应该退款、待业务确定
    private static final Long IS_SHOULD_REFUND_580002 = 580002L;
    //退货订单、是否应该退款、应该退款
    private static final Long IS_SHOULD_REFUND_580003 = 580003L;
    //联盟
  	private static final Long CODE_PARENT_LIANMENG = 700L;
  	//文轩、代理
  	private static final Long CODE_PARENT_DAILI = 1001L;
	
	private static final long THREE_MONTH = 7776000000l;
	@Autowired
	private ReturnOrderService  returnOrderService;
	
	@Autowired
	private OrderService orderService ;
	
	@Autowired
	private CodeService codeService ;
	
	@Autowired
	private DcService dcService;
	
	private MultipartFileUtil fileUtil;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer,@MyInject Pagination pagination){ 
		pagination.setPageSize(Integer.parseInt("5"));
		Map<String,Object> parameters = new HashMap<String, Object>();
		List<Long> types = new ArrayList<Long>();
		types.add(Code.RETURN_ORDER_TYPE_RETURN);
		types.add(Code.RETURN_ORDER_TYPE_REPLACE);
		parameters.put("customer", customer);
		parameters.put("type",types);
		List<ReturnOrder> returnOrders = returnOrderService.find(parameters, pagination);
		ModelAndView modelAndView  = new ModelAndView("/customer/returnorder/list");
		modelAndView.addObject("returnOrders", returnOrders);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ModelAndView  get(@PathVariable("id") Long id,@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/detail"); 
		ReturnOrder returnOrder = returnOrderService.get(id);
		if(returnOrder == null || !returnOrder.getCustomer().getName().equals(customer.getName())){
			modelAndView = new ModelAndView("redirect:/customer/returnorder");
			return modelAndView;
		}
		DcRule addressDcRule = dcService.findByAvailableDc(returnOrder.getOriginalOrder().getDistributionCenter().getDcDest().getName());
		modelAndView.addObject("returnOrder", returnOrder);
		if(addressDcRule != null){
			modelAndView.addObject("address", addressDcRule.getAddress());
		}
		return modelAndView;
	}
	@RequestMapping(value ="/{id}/cancel",method = RequestMethod.GET)
	public ModelAndView  cancel(@PathVariable("id") Long id,@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/cancel"); 
		ReturnOrder returnOrder = returnOrderService.get(id);
		if(returnOrder == null || !returnOrder.getCustomer().getName().equals(customer.getName())){
			modelAndView = new ModelAndView("redirect:/customer/returnorder");
			return modelAndView;
		}
		returnOrderService.cancel(returnOrder, customer);
		Code status = returnOrder.getStatus();
		modelAndView.addObject("status", status.getId());
		return modelAndView;
	}
	
	@RequestMapping(value="/return",method=RequestMethod.GET)
	public ModelAndView returnOrder(@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/return_step1");	
		return modelAndView;
	}
	@RequestMapping(value="/change",method=RequestMethod.GET)
	public ModelAndView change(@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/change_step1");	
		return modelAndView;
	}

	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	public ModelAndView step2(@MyInject Customer customer,
			@RequestParam("orderId") String orderId,
			@RequestParam(required = false, value = "isReturn") Boolean isReturn)
			throws ReturnOrderException {
		orderId = orderId.trim();
		validateOrder(orderService, customer, orderId);
		ModelAndView modelAndView = new ModelAndView(
				"/customer/returnorder/return_step2");
		isReturn = isReturn == null ? true : false;
		if (!isReturn) {
			modelAndView.setViewName("/customer/returnorder/change_step2");
		}
		Order order = orderService.get(orderId);
		modelAndView.addObject("order", order);
		return modelAndView;
	}
	
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	public ModelAndView validate(@MyInject Customer customer,@RequestParam("orderId")String orderId){
		
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/validate");
		try {
			orderId = orderId.trim();
			validateOrder(orderService,customer,orderId);
		} catch (ReturnOrderException e) {
			LOG.info(e.getMessage());
			modelAndView.addObject("status", false);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage().substring(4));//删掉用户不应看到的从validateOrder传入的null
			return modelAndView;
		}
		modelAndView.addObject("status", true);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/step3",method=RequestMethod.POST)
	public ModelAndView step3(@MyInject Customer customer,
			@RequestParam("orderId")String orderId,
			ReturnOrderForms returnOrderForms,
			@RequestParam(required=false,value="isReturn")Boolean isReturn) throws ReturnOrderException  {
		validateOrder(orderService, customer, orderId);
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/return_step3");
	    isReturn = isReturn== null ?true:false;
	    if(!isReturn){
			modelAndView.setViewName("/customer/returnorder/change_step3");
		}
		List<Code> codes = codeService.get(Code.RETURN_ORDER_REASON).getAvailableChildren();
		List<ReturnOrderItem> returnOrderItems = returnOrderForms.getReturnOrderItems(orderService, codeService);
		modelAndView.addObject("returnOrderItems",returnOrderItems);
		modelAndView.addObject("orderId",orderId);
		modelAndView.addObject("codes",codes);
		return modelAndView;
	}
	@RequestMapping(value="/step4",method=RequestMethod.POST)
	public ModelAndView step4(@MyInject Customer customer,
			@RequestParam("orderId")String orderId,
			ReturnOrderForms returnOrderForms,
			@RequestParam(required=false,value="isReturn")Boolean isReturn){	
		List<ReturnOrderItem> returnOrderItems = returnOrderForms.getReturnOrderItems(orderService, codeService);
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/return_step4");
		 isReturn = isReturn== null ?true:false;
		    if(!isReturn){
				modelAndView.setViewName("/customer/returnorder/change_step4");
			}
		modelAndView.addObject("orderId",orderId);
		modelAndView.addObject("returnOrderItems", returnOrderItems);
		return modelAndView;
	}
	@RequestMapping(value="/step5",method=RequestMethod.POST)
	public ModelAndView step5(@MyInject Customer customer,
			@RequestParam("orderId")String orderId,
			ReturnOrderForms returnOrderForms,
			@RequestParam(required=false,value="isReturn")Boolean isReturn) throws ReturnOrderException{
		validateOrder(orderService,customer,orderId);
		ModelAndView modelAndView = new ModelAndView("/customer/returnorder/return_step5");
		ReturnOrder returnOrder =new ReturnOrder();
		returnOrder.setStatus(new Code(Code.RETURN_ORDER_STATUS_NEW));
		Order order = orderService.get(orderId);
		returnOrder.setCustomer(customer);
		returnOrder.setCreateTime(new Date());
		isReturn = isReturn== null ?true:false;
	    if(!isReturn){
			modelAndView.setViewName("/customer/returnorder/change_step5");
			returnOrder.setType(new Code(Code.RETURN_ORDER_TYPE_REPLACE));
		}else{
			returnOrder.setType(new Code(Code.RETURN_ORDER_TYPE_RETURN));
		}	
		returnOrder.setHolder(new Code(Code.RETURN_ORDER_HOLDER_CUSTOMER));
		returnOrder.setResponsible(new Code(Code.RETURN_ORDER_HOLDER_CUSTOMER));	
		returnOrder.setPickup(new Code(Code.RETURN_ORDER_STYLE_PERSON));	
		List<ReturnOrderItem> returnOrderItems = returnOrderForms.getReturnOrderItems(orderService, codeService);	
		for(ReturnOrderItem returnOrderItem: returnOrderItems){
			returnOrderItem.setReturnOrder(returnOrder);
		}
		Set<ReturnOrderItem> itemList = new HashSet<ReturnOrderItem>();
		itemList.addAll(returnOrderItems);
		returnOrder.setItemList(itemList);
		//货到付款(直销，代理，联盟)订单，是否应该退款设为待定
		if(order.getPayType().getId().equals(Code.ORDER_PAY_TYPE_FIRST_DELIVERY)
				&& (order.getChannel().getParent().getId().equals(CODE_PARENT_LIANMENG) 
						|| order.getChannel().getParent().getId().equals(CODE_PARENT_DAILI))){			
			returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580002));
		}else{
			returnOrder.setShouldrefund(new Code(IS_SHOULD_REFUND_580003));
		}
		returnOrderService.create(returnOrder, order, customer);
		return modelAndView;
	}
	
	/**
	 * 验证订单
	 * @param orderService
	 * @param customer
	 * @param orderId
	 * @throws ReturnOrderException
	 */
	public void validateOrder(OrderService orderService,Customer customer,String orderId) throws ReturnOrderException {	
		boolean blag = orderService.isExistByCustomerAndOrderId(customer,orderId);
		if(!blag){
			throw new ReturnOrderException(null,"当前用户不存在该订单号");
		}
		Order order = orderService.get(orderId);	
		if(!Code.ORDER_PROCESS_STATUS_COMPLETED.equals(order.getProcessStatus().getId())){
			throw new ReturnOrderException(null,"该订单号没有交易完成");
		}else{
			Date deliveryDate = order.getDeliveryTime();
			if(deliveryDate == null){
				throw new ReturnOrderException(null,"该订单超过了退货时间");
			}
			long deliveryDatemi = deliveryDate.getTime();
			Date now = new Date();
			long mi = now.getTime();
			if (mi - deliveryDatemi > THREE_MONTH) {	
				throw new ReturnOrderException(null,"该订单超过了退货时间");
			}
			Set<OrderItem> orderItems = order.getItemList();
			int count = 0;
			for(OrderItem orderItem : orderItems){
				count = count + orderItem.getCanReturnQuantity();
			}
			if(count <= 0){
				throw new ReturnOrderException(null,"该订单号已没有商品可退");
			}
		}
		
	}
	 	 @PostConstruct  
	     public void init() {   
	 		fileUtil = new MultipartFileUtil();   
	 		fileUtil.setAllowedContentType("image/jpeg");   
	   }   


}
