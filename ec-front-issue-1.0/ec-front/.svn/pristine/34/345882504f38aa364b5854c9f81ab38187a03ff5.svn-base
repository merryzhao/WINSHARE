/*
 * @(#)QuestionController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户提问
 * @author  HideHai
 * @version 1.0,2011-10-13
 */
@Controller
@RequestMapping(value = "/customer/question")
public class QuestionController {

	@Autowired
	private CustomerService customerService;


	@RequestMapping(method=RequestMethod.GET)
	ModelAndView view(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/question/view");
		long questionCount = customerService.getQuestionCount(customer);
		long replyCount = customerService.getQuestionReplyCount(customer);
		List<CustomerQuestion> questions = 
			customerService.findQuestions(customer, pagination, Short.valueOf("0"));
		modelAndView.addObject("questionCount", questionCount);
		modelAndView.addObject("replyCount", replyCount);
		modelAndView.addObject("questions", questions);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

	@RequestMapping(value="/reply",method=RequestMethod.GET)
	ModelAndView reply(@MyInject Customer customer,@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/question/replies");
		long questionCount = customerService.getQuestionCount(customer);
		long replyCount = customerService.getQuestionReplyCount(customer);
		List<CustomerQuestionReply> questionReplies = 
			customerService.findQuestionReplies(customer, pagination, Short.valueOf("0"));
		modelAndView.addObject("questionCount", questionCount);
		modelAndView.addObject("replyCount", replyCount);
		modelAndView.addObject("questionReplies", questionReplies);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}

}

