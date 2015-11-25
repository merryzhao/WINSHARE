package com.winxuan.ec.front.controller.customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.service.customer.CustomerService;

/**
 * 
 * 
 * @author YangJun
 * @version 1.0,2011-10-18
 *
 */
@Controller
@RequestMapping(value = "/emailActive")
public class EmailActiveController {
	@Autowired
	private CustomerService customerService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ModelAndView email(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "date", required = false) Date date,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value="version", required=false) short version) {
		ModelAndView modelAndView = null;
		short validate = customerService.activeEmail(email, date, key, version);
		switch(validate){
		case 0:
			modelAndView = new ModelAndView("/customer/email/failed");
			break;
		case 1:
			modelAndView = new ModelAndView("/customer/email/success");
			break;
		case 2:
			modelAndView = new ModelAndView("/customer/email/invalidation");
			break;
		default:
			modelAndView = new ModelAndView("/customer/email/invalidation");
			break;
		}
		return modelAndView;
	}
}
