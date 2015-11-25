/*
 * @(#) AnonymousController.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.front.controller.customer;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.verifier.AnonymousVerifier;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.framework.validator.IdentityValidator;

/**
 * @author Min-Huang
 * @version 1.0,Mar 5, 2012
 */

@Controller
public class AnonymousController {

	private static final String RESULT_KEY = "result";

	private static final String STATUS_NORMAL = "1";
	private static final String STATUS_EXIST = "2";
	private static final String STATUS_ERROR = "0";

	private static final Logger LOG = LoggerFactory
			.getLogger(AnonymousController.class);

	@Autowired
	private CustomerService customerService;

	private IdentityValidator identityValidator;

	/**
	 * @param identityValidator
	 *            the identityValidator to set
	 */
	public void setIdentityValidator(IdentityValidator identityValidator) {
		this.identityValidator = identityValidator;
	}

	/**
	 * @param customerService
	 *            the customerService to set
	 */
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * 
	 */

	@RequestMapping(value = "/anonym/validate")
	public ModelAndView validate(@RequestParam("email") String email) {
		ModelAndView mav = new ModelAndView("/anonym/validate");
		mav.addObject(RESULT_KEY, getStatus(email));
		return mav;
	}

	private String getStatus(String email) {
		if (StringUtils.isBlank(email)) {
			return STATUS_ERROR;
		} else {
			boolean isEmail = verify(email);
			if (isEmail && isExist(email)) {
				return STATUS_EXIST;
			}
			return isEmail ? STATUS_NORMAL : STATUS_ERROR;
		}
	}

	/**
	 * @param email
	 * @return
	 */
	private boolean isExist(String email) {
		boolean result = false;
		Customer customer = customerService.getByEmail(email);
		if (customer != null && customer.getId() != null) {
			result = true;
		}
		customer = customerService.getByEmail(new Code(
				Code.USER_SOURCE_ANONYMITY), email);
		if (customer != null && customer.getId() != null) {
			result = true;
		}
		return result;
	}

	@RequestMapping(value = "/anonym/active")
	public ModelAndView active(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("name") String name,@RequestParam("mobile") String mobile) {
		ModelAndView mav = new ModelAndView("/anonym/active");
		String status = getStatus(email);
		if (status.equals(STATUS_NORMAL)) {
			AnonymousVerifier verifier = new AnonymousVerifier();
			verifier.setMail(email);
			verifier.setPassword(password);
			verifier.setName(name);
			if(StringUtils.isNotBlank(mobile)){
				verifier.setMobile(mobile);
			}
			try {
				if (identityValidator.login(verifier)) {
					mav.addObject(RESULT_KEY, status);
				} else {
					mav.addObject(RESULT_KEY, STATUS_ERROR);
				}
			} catch (Exception e) {
				mav.addObject(RESULT_KEY, STATUS_ERROR);
				LOG.warn("User email active failed: " + e.getMessage());
			}
		} else {
			mav.addObject(RESULT_KEY, status);
		}
		return mav;
	}

	private boolean verify(String email) {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			return false;
		}
		return true;
	}
}
