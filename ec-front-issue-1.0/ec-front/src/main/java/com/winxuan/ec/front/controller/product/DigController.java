/*
 * @(#)DigController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.product;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.web.CookieUtils;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-9-29
 */
@Controller
@RequestMapping(value = "/product")
public class DigController {
	private static final Log LOG =LogFactory.getLog(DigController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/{p}/digging", method = RequestMethod.GET)
	public ModelAndView dig(@MyInject Customer customer,
			@PathVariable("p") Long p,
			@CookieValue(value = Constant.COOKIE_CLIENT_NAME, required = false) String client,
			@CookieValue(value = Constant.COOKIE_SESSION_NAME, required = false) String session) {
		ModelAndView modelAndView =new ModelAndView("/product/digging");
		ProductSale productSale = productService.getProductSale(p);
		if (productSale == null) {
			//TODO
			LOG.info("不存在productSale为"+p);
		}
		if (StringUtils.isBlank(client)) {
			client = writeCookie(Constant.COOKIE_CLIENT_NAME,
					Constant.COOKIE_CLIENT_AGE);
		}
		if (StringUtils.isBlank(session)) {
			session = writeCookie(Constant.COOKIE_SESSION_NAME, -1);
		}
		modelAndView.addObject("status", customerService.dig(customer,client,session, productSale));
		modelAndView.addObject("count", productService.getTotalLikeByProductSaleId(productSale));
		return modelAndView;
	}
	
	private String writeCookie(String name, int maxAge) {
		String value = RandomCodeUtils.create(Constant.COOKIE_RANDOM_CODE_MODE,
				Constant.COOKIE_RANDOM_CODE_LENGTH);
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(Constant.COOKIE_DOMAIN);
		cookie.setMaxAge(maxAge);
		cookie.setPath(Constant.COOKIE_PATH);
		CookieUtils.writeCookie(cookie);
		return value;
	}
}
