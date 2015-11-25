/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.presentcard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.admin.controller.order.OrderController;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.presentcard.PresentCardOrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;

/**
 * 处理礼品卡订单相关页面跳转
 * 
 * @author zhongsen
 * @version 1.0,2011-9-9
 */
@Controller
@RequestMapping("/presentcardorder")
public class PresentCardOrderController {

	private static final String ORDERIDSTR = "orderId";
	private static final String ACTIVE="/presentcardorder/presentcardorder_activate";
	private static final String GRANT="/presentcardorder/presentcardorder_grant";
	private static final String REISSUE="/presentcardorder/presentcardorder_reissue";
	private static final String RESULT="/presentcardorder/result";
	private static final Log LOG = LogFactory.getLog(OrderController.class);
	
	@Autowired
	PresentCardOrderService presentCardOrderService;

	@Autowired
	OrderService orderService;

	/**
	 * 跳转到发卡页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tosend", method = RequestMethod.GET)
	public ModelAndView toSend() {
		ModelAndView mav = new ModelAndView(GRANT);
		return mav;
	}

	/**
	 * 根据orderId查找Order并跳转到发卡页面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/tosendofdata", method = RequestMethod.POST)
	public ModelAndView toSendOfData(@Parameter("orderId") String orderId) {
		ModelAndView mav = new ModelAndView(GRANT);
		Order order = orderService.get(orderId.trim());
		mav.addObject(ORDERIDSTR,orderId);
		mav.addObject("order", order);
		return mav;
	}

	/**
	 * 发卡
	 * 
	 * @param presentCardId
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView send(@Parameter(ORDERIDSTR) String orderId,
			@MyInject Employee operator) {
		ModelAndView mav = new ModelAndView(RESULT);
		try {
			presentCardOrderService.sendPresentCard(orderId, operator);
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (PresentCardException e) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
		}
		return mav;
	}

	/**
	 * 根据orderId查找Order和礼品卡列表并跳转到补发页面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/toresendofdata", method = RequestMethod.POST)
	public ModelAndView toResendOfData(@Parameter("orderId") String orderId) {
		ModelAndView mav = new ModelAndView(REISSUE);
		Order order = orderService.get(orderId.trim());
		mav.addObject(ORDERIDSTR,orderId);
		mav.addObject("presentCards",
				presentCardOrderService.findByOrderId(orderId.trim()));
		mav.addObject("order", order);
		return mav;
	}

	/**
	 * 跳转到补发页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toresend", method = RequestMethod.GET)
	public ModelAndView toResend() {
		ModelAndView mav = new ModelAndView(REISSUE);
		return mav;
	}

	/**
	 * 补发
	 * 
	 * @param orderId
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/resend", method = RequestMethod.POST)
	public ModelAndView resend(@Parameter(ORDERIDSTR) String orderId,
			@MyInject Employee operator) {
		ModelAndView mav = new ModelAndView(RESULT);
		try {
			presentCardOrderService.logoutReissue(orderId, operator);
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (PresentCardException e) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
		}
		return mav;
	}

	/**
	 * 跳转到激活页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toactive", method = RequestMethod.GET)
	public ModelAndView toActive() {
		ModelAndView mav = new ModelAndView(ACTIVE);
		return mav;
	}

	/**
	 * 根据orderId查找Order并跳转到激活页面
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/toactiveofdata", method = RequestMethod.POST)
	public ModelAndView toActiveOfData(@Parameter("orderId") String orderId) {
		ModelAndView mav = new ModelAndView(ACTIVE);
		Order order = orderService.get(orderId.trim());
		mav.addObject("order", order);
		mav.addObject(ORDERIDSTR,orderId);
		mav.addObject("presentCards",
				presentCardOrderService.findByOrderId(orderId.trim()));
		return mav;
	}

	/**
	 * 激活
	 * 
	 * @param orderId
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	public ModelAndView active(@Parameter(ORDERIDSTR) String orderId,
			@Parameter("presentCardIds") String presentCardIds,
			@MyInject Employee operator) {
		ModelAndView mav = new ModelAndView(ACTIVE);
		try {
			List<String> presentCards = new ArrayList<String>();
			Order order = orderService.get(orderId.trim());
			String[] idStrings = StringUtils.split(presentCardIds, "\\|");
			for (String string : idStrings){
				presentCards.add(string);
			}
			presentCardOrderService.activateByEmployeeNew(orderId, presentCards, operator);
			mav.addObject("order", order);
			mav.addObject(ORDERIDSTR,orderId);
			mav.addObject("presentCards",
					presentCardOrderService.findByOrderId(orderId.trim()));
		} catch (PresentCardException e) {
			LOG.error(e.getMessage());
		}
		return mav;
	}

}
