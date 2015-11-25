/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

package com.winxuan.ec.admin.controller.comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerCommentReply;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.web.FormToMapUtils;

/**
 * 
 * 
 * @author chenlong
 * @version 1.0,2011-10-24
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerCommentService customerCommentService;

	@Autowired
	ChannelService channelService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * 跳转到评论管理页面
	 */
	@RequestMapping(value = "/goList", method = RequestMethod.GET)
	public ModelAndView goList() {
		ModelAndView mav = new ModelAndView("/comment/list");
		return mav;
	}

	/**
	 * 搜索评论
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(SearchForm form, @MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/comment/list");
		Map<String, Object> parameters = FormToMapUtils.getMap(form);
		parameters.put("channel", channelService.get(form.getChannel()));
		List<CustomerComment> list = customerService.findComments(parameters, pagination, (short) 0);
		mav.addObject("list", list);
		mav.addObject("pagination", pagination);
		mav.addObject("form", form);
		return mav;
	}

	/**
	 * 跳转到评论回复页面
	 */
	@RequestMapping(value = "/goReply", method = RequestMethod.GET)
	public ModelAndView goReply(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/comment/reply");
		CustomerComment comment = customerCommentService.get(id);
		mav.addObject("comment", comment);
		return mav;
	}

	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public ModelAndView reply(@RequestParam("commentId") Long commentId,
			@RequestParam("content") String content, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/comment/reply");
		CustomerCommentReply reply = new CustomerCommentReply();
		CustomerComment comment = customerCommentService.get(commentId);
		reply.setComment(comment);
		reply.setContent(content);
		reply.setReplier(employee);
		Date date = new Date();
		reply.setReplyTime(date);
		customerCommentService.saveReply(reply);
		mav.addObject("comment", reply.getComment());
		return mav;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView del(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/comment/result");
		CustomerComment comment = customerCommentService.get(id);
		customerCommentService.delete(comment);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/batchdel", method = RequestMethod.POST)
	public ModelAndView batchDel(@RequestParam("commentid") List<Long> ids) {
		ModelAndView mav = new ModelAndView("redirect:/comment/goList");
		for (Long id : ids) {
			CustomerComment comment = customerCommentService.get(id);
			customerCommentService.delete(comment);
		}
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/updatereply", method = RequestMethod.POST)
	public ModelAndView updateReply(CustomerCommentReply reply,
			@MyInject Employee employee) {
		ModelAndView view = new ModelAndView("/comment/reply");
		CustomerCommentReply re = customerCommentService.findReply(reply
				.getId());
		re.setContent(reply.getContent());
		customerCommentService.updateReply(re);
		view.addObject("comment",
				customerCommentService.get(reply.getComment().getId()));
		return view;
	}
}
