package com.winxuan.ec.admin.controller.agent;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
 

/**
 * @author df.rsy
 * @version 1.0,2011-11-26
 */
@Controller
@RequestMapping("/agent")
public class AgentController {
    
	@Autowired
	CustomerService customerService;
	@Autowired
	ChannelService channelService;
	@Autowired
	OrderService orderService;
    /**
     * 跳转代理商信息管理画面
     * @return
     */
	@RequestMapping(value = "/agentmanage", method = RequestMethod.GET)
	public ModelAndView agentManage() {
		ModelAndView mav = new ModelAndView("/agent/agentmanage");
		return mav;
	}
	
    /**
     * 跳转代理商信息管理画面
     * @return
     */
	@RequestMapping(value = "/agentinfo", method = RequestMethod.GET)
	public ModelAndView agentInfoManage() {
		ModelAndView mav = new ModelAndView("/agent/agentinfo");
		return mav;
	}
	
	/**
	 * 查询代理商
	 * @param name
	 * @param isAgent
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/findagent", method = RequestMethod.POST)
	public ModelAndView findAgent(@RequestParam(required=false,value="userName") String name,
			@RequestParam(required=false,value="isAgent") String isAgent,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/agent/agentmanage");
		Map<String,Object> params=new HashMap<String, Object>();
		if(!StringUtils.isBlank(name)){
			params.put("name", name);
		}
		if("agent".equals(isAgent)){
 			params.put("channel",channelService.get(Channel.CHANNEL_AGENT));
		}
		List<Customer> customers = customerService.find(params, pagination);
		mav.addObject("customers", customers);
		mav.addObject("pagination", pagination);
		return mav;
	}
	
    /**
     * 代理商交易 查询
     * @param userName
     * @param startDate
     * @param endDate
     * @param pagination
     * @return
     */
	@RequestMapping(value = "/agentorder", method = RequestMethod.POST)
	public ModelAndView findAgentOrder(@RequestParam(required=false,value="userName") String userName,
			@RequestParam(required=false,value="startDate") String startDate,
			@RequestParam(required=false,value="endDate") String endDate,
			@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/agent/agentinfo");
		Map<String,Object> params=new HashMap<String, Object>();
		if(!StringUtils.isBlank(userName)){
			String[] names=userName.split("\r\n");
			List<String> nameList=new ArrayList<String>();
			for(String name:names){
				if(!StringUtils.isBlank(name)){
					nameList.add(name);
				}
			}
			params.put("name",nameList.isEmpty()?null:nameList);
		}
  		params.put("startCreateTime", StringUtils.isBlank(startDate)?null:getDateByString(startDate+" 00:00:00"));
 		params.put("endCreateTime", StringUtils.isBlank(endDate)?null:getDateByString(endDate+" 23:59:59"));
   		params.put("channel",Channel.CHANNEL_AGENT);
  		List<Order> orders=orderService.find(params, (short)0, pagination);
  		mav.addObject("pagination", pagination);
  		mav.addObject("orders",orders);
  		mav.addObject("userName", userName);
  		mav.addObject("startDate", startDate);
  		mav.addObject("endDate", endDate);
		return mav;
	}
	
	/**
	 * 设置为代理商
	 * @param id
	 * @param discount
	 * @return
	 */
	@RequestMapping(value = "/setagent", method = RequestMethod.POST)
	public ModelAndView setAgent(
			@RequestParam("id") Long id,
			@RequestParam("discount") BigDecimal discount,
			@MyInject Employee employee) {
  		ModelAndView mav = new ModelAndView("/agent/agent");
        Customer customer = customerService.get(id);
        customerService.setupCustomerAgent(customer, discount, true,employee);
        mav.addObject("discount",customer.getDiscount());
		return mav;
	}
	
	/**
	 * 取消代理商资格
	 * @param id
 	 * @return
	 */
	@RequestMapping(value = "/cancelagent", method = RequestMethod.POST)
	public ModelAndView cancelAgent(
			@RequestParam("id") Long id,
			@MyInject Employee employee) {
 		ModelAndView mav = new ModelAndView("/agent/agent");
        Customer customer = customerService.get(id);
        customerService.setupCustomerAgent(customer,null, false,employee);
 		return mav;
	}
	
	public Date getDateByString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date timeDate = new Date();
		try {
			if (StringUtils.isBlank(time)) {
				timeDate = null;
			} else {
				timeDate = sdf.parse(time);
			}
		} catch (ParseException e) {
			time = null;
		}
		return timeDate;
	}
	
}
