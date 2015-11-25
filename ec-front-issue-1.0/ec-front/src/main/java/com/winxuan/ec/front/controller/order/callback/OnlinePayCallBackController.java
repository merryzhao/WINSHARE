/*
 * @(#)OnlinePayCallBackController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.order.callback;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.util.IpUtils;

/**
 * description
 * 
 * @author qiaoyao
 * @version 1.0,2011-8-9
 */

public abstract class OnlinePayCallBackController {

	private static final Log LOG = LogFactory.getLog(OnlinePayCallBackController.class);

	private static final short TYPE_RETURN = 1;

	private static final short TYPE_NOTIFY = 2;

	private static final Map<Short, String> TYPE_MAP = new HashMap<Short, String>();

	static {
		TYPE_MAP.put(TYPE_RETURN, "return");
		TYPE_MAP.put(TYPE_NOTIFY, "notify");
	}

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private BatchPayService batchPayService;

	protected ModelAndView payByReturn(HttpServletRequest request) {
		try {
			BatchPay batchPay = payCallBack(request, TYPE_RETURN);
			ModelAndView modelAndView = new ModelAndView("/order/bank/success");
			modelAndView.addObject("orderList", batchPay.getOrderList());
			modelAndView.addObject("callBackForm", getCallBackForm(request));
			return modelAndView;
		} catch (Exception e) {
			LOG.warn(batchPayService.getParames(request.getParameterMap()));
			LOG.error(e.getMessage(), e);
			ModelAndView modelAndView = new ModelAndView("/order/bank/error");
			modelAndView.addObject("msg", e.getMessage());
			return modelAndView;
		}

	}

	protected boolean payByNotify(HttpServletRequest request) {
		try {
			payCallBack(request, TYPE_NOTIFY);
			return true;
		} catch (Exception e) {
			LOG.warn(batchPayService.getParames(request.getParameterMap()));
			LOG.error(e.getMessage(), e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private BatchPay payCallBack(HttpServletRequest request, short type) throws Exception {
		CallBackForm callBackForm = getCallBackForm(request);
		LOG.info("ip: " + IpUtils.getClientIP(request) + " " + callBackForm + " start by " + TYPE_MAP.get(type));
		if (!verify(request)) {
			LOG.debug(callBackForm.getBatchId() + " verify fail");
			throw new RuntimeException("参数验证失败");
		}

		return batchPayService.onlinePayCallback(callBackForm, request.getParameterMap());
	}

	protected abstract boolean verify(HttpServletRequest request) throws Exception;

	protected abstract CallBackForm getCallBackForm(HttpServletRequest request);

}
