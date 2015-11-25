package com.winxuan.ec.admin.controller.order;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.order.OrderReportService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 订单报表.
 * @author heyadong
 * @version 1.0, 2012-8-9 下午02:38:24
 */
@Controller
@RequestMapping("/order/report")
public class OrderReportController {
	private static final Log LOG = LogFactory.getLog(OrderReportController.class);
	

	//订单ID排序
	private static final short ORDER_ID = 5;
	@Autowired
	private ChannelService channelService;
	
	
	@Autowired 
	private OrderService orderService;
	
	@Autowired
	private OrderReportService orderReportService;
	
	@Value("${path.console}")
	private String path;
	
	@RequestMapping("/delivery")
	public ModelAndView delivery(){
		
		ModelAndView mav = new ModelAndView("/order/report/delivery");
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("available", true);
		mav.addObject("channels", channelService.find(parameter));
		
		return mav;
	}
	
	@RequestMapping("/delivery/download")
	public synchronized ModelAndView downloadDelivery(OrderDeliveryReportForm form, @MyInject Employee employee) throws Exception {
		//  /order/orderDelivery
		ModelAndView mav = new ModelAndView("redirect:/files/order/delivery/" + employee.getId() + ".xls");
		Map<String, Object> orderParameter = parseOrderDeliveryReportForm(form);
//		orderParameter.put("meta", OrderExtend.ORDER_PACKAGES);
		String dirpath = path + "/order/delivery";
		File dir = new File(dirpath);
		dir.mkdirs(); 
		orderReportService.generateOrderDeliveryReport(dirpath + "/" + employee.getId() + ".xls", orderParameter, ORDER_ID);
		
		return mav;
	}
	
	private Map<String,Object> parseOrderDeliveryReportForm(OrderDeliveryReportForm form) {
		
		Map<String,Object> orderParameter = new HashMap<String,Object>();
		orderParameter.put("startDeliveryTime", form.getStartDeliveryTime());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(form.getEndDeliveryTime());
		calendar.add(Calendar.DATE, 1);
		orderParameter.put("endDeliveryTime", calendar.getTime());
		
		orderParameter.put("status",Order.DELIVERY_PROCESSSTATUS);
		
		Integer state = form.getState();
		if(OrderExtend.ORDER_PACKAGES.equals(state)) {
			orderParameter.put("meta", state);
		} else if (state != null && state == -1) {
			orderParameter.put("nmeta", state);
		}
		
		if( form.getChannels() != null) {
			orderParameter.put("channels", form.getChannels());
		}
		if (form.getOrders() != null && !form.getOrders().isEmpty()){
		    String[] orders = form.getOrders().split(IOUtils.LINE_SEPARATOR);
		    for (int i = 0 ; i < orders.length; i++){
		        orders[i] = orders[i].trim();
		    }
		    orderParameter.put("orders", orders);
		}
		orderParameter.put("city", form.getCity() == null ? -1L : form.getCity());
		orderParameter.put("district", form.getDistrict() == null ? -1L : form.getDistrict());
		orderParameter.put("province", form.getProvince()  == null ? -1L : form.getProvince());
		orderParameter.put("country", form.getCountry()  == null ? -1L : form.getCountry());
		orderParameter.put("ignore", form.ignore());
		return orderParameter;
	}
	
	
	
	@RequestMapping(value="/delivery/list", method=RequestMethod.POST)
	public ModelAndView deliveryList(OrderDeliveryReportForm form,@MyInject Pagination pagination){
		
		ModelAndView mav = new ModelAndView("/order/report/list");
		Map<String, Object> orderParameter = parseOrderDeliveryReportForm(form);
		List<Order> orders = orderService.findOrderWithMeta(orderParameter, ORDER_ID, pagination);
		 
		mav.addObject("pagination",pagination);
		mav.addObject("orders", orders);
		return mav;
	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
