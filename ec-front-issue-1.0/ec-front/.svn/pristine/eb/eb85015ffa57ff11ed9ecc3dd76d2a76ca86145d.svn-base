/*
 * @(#)PresentCardOrderController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.service.presentcard.PresentCardOrderService;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-12-16
 */
@Controller
@RequestMapping("/presentcardorder")
public class PresentCardOrderController {
	
	@Autowired
	private PresentCardOrderService presentCardOrderService;
	
	@ResponseBody
	@RequestMapping(value="/activate",method=RequestMethod.GET)
	public String activate(@RequestParam("orderId") String orderId, @RequestParam("sign") String sign){
		try {
			presentCardOrderService.activateByMember(orderId, sign);
		} catch (PresentCardException e) {
			return e.getMessage();
		}
		return "active success";
	}
}
