/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.question;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerQuestionService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.shop.ShopService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.web.FormToMapUtils;

/**
 * 
 * 
 * @author chenlong
 * @version 1.0,2011-10-26
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerCommentService customerCommentService;
	@Autowired
	ShopService shopService;

	@Autowired
	CustomerQuestionService customerQuestionService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 跳转到咨询管理页面
	 */
	@RequestMapping(value = "/goList", method = RequestMethod.GET)
	public ModelAndView goList() {
		ModelAndView mav = new ModelAndView("/question/list");
		mav.addObject("shops", shopService.findAll());
		return mav;
	}

	/**
	 * 搜索评论
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(SearchForm form, @MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/question/list");
		Map<String, Object> parameters = FormToMapUtils.getMap(form);
		List<CustomerQuestion> list = new ArrayList<CustomerQuestion>();
		if (parameters.get("replierName") != null) {
			List<CustomerQuestionReply> replyList = customerQuestionService
					.findReplay(parameters, pagination, (short) 0);
			if (replyList != null && !replyList.isEmpty()) {
				for (CustomerQuestionReply reply : replyList) {
					list.add(reply.getQuestion());
				}
			}
		} else {
			list = customerQuestionService.find(parameters, pagination,
					(short) 0);
		}
		mav.addObject("list", list);
		mav.addObject("form", form);
		mav.addObject("shops", shopService.findAll());
		mav.addObject("pagination", pagination);
		return mav;
	}

	/**
	 * 跳转到咨询回复页面
	 */
	@RequestMapping(value = "/goReply", method = RequestMethod.GET)
	public ModelAndView goReply(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/question/reply");
		CustomerQuestion question = customerQuestionService.get(id);
		mav.addObject("question", question);
		return mav;
	}

	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public ModelAndView reply(@RequestParam("questionId") Long questionId,
			@RequestParam("content") String content, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/question/reply");
		CustomerQuestionReply reply = new CustomerQuestionReply();
		CustomerQuestion question = customerQuestionService.get(questionId);
		reply.setQuestion(question);
		reply.setContent(content);
		reply.setReplier(employee);
		Date date = new Date();
		reply.setReplyTime(date);
		customerQuestionService.saveServiceReply(reply);
		mav.addObject("question", reply.getQuestion());
		return mav;
	}

	@RequestMapping(value = "/updatereply", method = RequestMethod.POST)
	public ModelAndView updateReply(CustomerQuestionReply reply,
			@MyInject Employee employee) {
		ModelAndView view = new ModelAndView("/question/reply");
		CustomerQuestionReply rep = customerQuestionService.findReply(reply
				.getId());
		rep.setContent(reply.getContent());
		customerQuestionService.updateReply(rep);
		view.addObject("question",
				customerQuestionService.get(reply.getQuestion().getId()));
		return view;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView reply(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/comment/result");
		CustomerQuestion question = customerQuestionService.get(id);
		customerQuestionService.delete(question);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

}
