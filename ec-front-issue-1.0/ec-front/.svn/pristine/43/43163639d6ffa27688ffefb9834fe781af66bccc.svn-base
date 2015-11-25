/*
 * @(#)PresentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.presentcard.PresentCardDealLog;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户礼品卡
 * @author  HideHai
 * @version 1.0,2011-9-8
 */
@Controller
@RequestMapping(value="/customer/presentcard")
public class PresentCardController {

	private static final Log LOG = LogFactory.getLog(PresentCardController.class);

	private static final int MAX_PAGE_SIZE = 5;
	
	@Autowired
	private PresentCardService presentCardService;
	//@Autowired
	//private VerifyCodeService verifyCodeService;
	
	

	/**
	 * 获取用户下单的礼品卡
	 * @return
	 */
	@RequestMapping(value="/checkout",method=RequestMethod.GET)
	public ModelAndView checkout(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/presentcard/checkout");
		List<PresentCard> cards = 
			presentCardService.findEffectivePresentCardByCustomer(customer.getId(),pagination);
		modelAndView.addObject("presentCards", cards);
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	/**
	 *  用户使用礼品卡
	 * @param customer
	 * @param card
	 * @param password
	 * @param code
	 * @param verifyNumber
	 * @return
	 */
	@RequestMapping(value="/use",method=RequestMethod.POST)
	public ModelAndView useCard(@MyInject Customer customer,
			@RequestParam(value="card",required=true) String card,
			@RequestParam(value="password",required=true) String password
			){ 
		ModelAndView modelAndView = new ModelAndView("/customer/presentcard/use");
		try {
				PresentCard presentCard =  presentCardService.get(card, password, customer.getId());
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject("presentCard", presentCard);
		} catch (PresentCardException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
		}
		return modelAndView;
	}
	
	/**
	 * 查询用户拥有的礼品卡
	 * @param customer
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/presentcard/list");
		//每页最多显示5条记录
		pagination.setPageSize(MAX_PAGE_SIZE);
		Map map = new HashMap();
		map.put("customerId", customer.getId());
		List<PresentCard> presentCards = new ArrayList<PresentCard>();
		presentCards = presentCardService.find(map, pagination);
		modelAndView.addObject("presentCards", presentCards);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	/**
	 * 查询礼品卡的使用明细
	 * @param customer
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/usedList",method=RequestMethod.GET)
	public ModelAndView usedList(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/presentcard/usedList");
		//每页最多显示5条记录
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<PresentCardDealLog> presentCardDealLogs = new ArrayList<PresentCardDealLog>();
		Map map = new HashMap();
		map.put("customerId", customer.getId());
		presentCardDealLogs = presentCardService.findDealLogList(map, pagination);
		modelAndView.addObject("presentCardDealLogs", presentCardDealLogs);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	@RequestMapping(value="/bind",method=RequestMethod.GET)
	public ModelAndView bind(@MyInject Customer customer,
			@RequestParam(value="code",required=true) String code,
			@RequestParam(value="password",required=true) String password){
		ModelAndView modelAndView = new ModelAndView("/customer/presentcard/bind");
		try {
			presentCardService.bind(code, password, customer.getId());
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		} catch (PresentCardException e) {
			LOG.error(e.getMessage(),e);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}

}

