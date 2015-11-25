/*
 * @(#)PresentController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.front.controller.customer.CheckoutForm.CheckoutShoppingcart;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.shoppingcart.ShoppingcartSeparator;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.validator.utils.BeanValidator;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户礼券
 * @author  HideHai
 * @version 1.0,2011-9-8
 */
@Controller
@RequestMapping(value="/customer/present")
public class PresentController {

	private static final String ALL_PRESENT = "all";
	private static final String VALID_PRESENT = "valid";
	private static final String USED_PRESENT = "used";
	private static final String PAST_PRESENT = "past";
	private static final int MAX_PAGE_SIZE = 5;

	private static final Log LOG = LogFactory.getLog(PresentController.class);

	@Autowired
	private PresentService presentService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private CodeService codeService;

	/**
	 * 获取用户下单可以使用的礼券列表
	 * @return
	 */
	@RequestMapping(value="/checkout",method=RequestMethod.GET)
	public ModelAndView checkout(@MyInject Customer customer,@MyInject Shoppingcart shoppingcart,
			CheckoutForm checkoutForm,BindingResult result){
		ModelAndView modelAndView = new ModelAndView("/customer/present/checkout");
		modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		List<Present> presents = null;
		if(BeanValidator.isValid(result, checkoutForm, CheckoutShoppingcart.class)){
			Shop shop = shopService.get(checkoutForm.getShopId());
			Code supplyType = codeService.get(checkoutForm.getSupplyTypeId());
			Map<ShoppingcartSeparator, List<ShoppingcartItem>> map = shoppingcart.getGroupItems();
			if(shop != null && supplyType != null && map != null){
				List<ShoppingcartItem> shoppingcartItems = null;
				ShoppingcartSeparator shoppingcartSeparator = new ShoppingcartSeparator(shop,supplyType);
				shoppingcartItems = map.get(shoppingcartSeparator);
				presents = presentService.findEffectivePresentByCustomerAndShoppingCart(customer, shoppingcartItems); 
				modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			}
		}
		modelAndView.addObject("presents", presents);
		modelAndView.addObject("channel", customer.getChannel().getId());
		return modelAndView;
	}
	/**
	 * 用户激活礼券
	 * @param customer
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/active",method=RequestMethod.GET)
	public ModelAndView active(@MyInject Customer customer ,@RequestParam("code") String code){
		ModelAndView modelAndView = new ModelAndView("/customer/present/active");
		try {
			Present present  = presentService.activePresent(code, customer);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject("present",present);
		} catch (PresentException e) {
			LOG.debug(e.getMessage());
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}

	/**
	 * 用户查询拥有的礼券
	 * @param customer
	 * @param presentType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer ,
			@RequestParam(value ="presentType",defaultValue = "all") String presentType,
			@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/present/list");

		List<Present> presents = new ArrayList<Present>();
		Map map = new HashMap();
		map.put("customer", customer.getId());
		map.put("state", stateMapping(presentType));

		if(PAST_PRESENT.equals(presentType)){
			map.put("currentDate", new Date());
		}
		if(VALID_PRESENT.equals(presentType)){
			map.put("startDate", new Date());
		}
		//每页最多显示5条记录
		pagination.setPageSize(MAX_PAGE_SIZE);
		presents = presentService.find(map, pagination);
		modelAndView.addObject("presents", presents);
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	/**
	 * 有效：礼券处于激活状态，并且没有过期
	 * 已使用：对应于code中的已使用和已支付
	 * 过期：处于激活状态，但是已经过期
	 * @param presentType
	 * @return
	 */
	private List<Long> stateMapping(String presentType){

		List<Long> list = new ArrayList<Long>();
		if(ALL_PRESENT.equals(presentType)){
			list.add(Code.PRESENT_STATUS_ACTIVE);
			list.add(Code.PRESENT_STATUS_PAY);
			list.add(Code.PRESENT_STATUS_USED);
		}
		else if(VALID_PRESENT.equals(presentType)){//有效
			list.add(Code.PRESENT_STATUS_ACTIVE);
		}
		else if(USED_PRESENT.equals(presentType)){//已使用
			list.add(Code.PRESENT_STATUS_USED);
			list.add(Code.PRESENT_STATUS_PAY);
		}
		else if(PAST_PRESENT.equals(presentType)){//过期
			list.add(Code.PRESENT_STATUS_ACTIVE);
		}
		return list;
	}
	
}

