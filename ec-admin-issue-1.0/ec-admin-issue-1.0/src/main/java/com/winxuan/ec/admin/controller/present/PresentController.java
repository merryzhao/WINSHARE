/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.present;

import java.util.ArrayList;
import java.util.Arrays;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 礼券
 * 
 * @author Chenlong
 * @version 1.0,2011-8-31
 */
@Controller
@RequestMapping("/present")
public class PresentController {
	private static final String NEEDVERIFYCOUNT = "needVerifyCount";
	private static final String MESSAGE = "message";
	private static final int ZERO = 0;
	@Autowired
	PresentService presentService;

	@Autowired
	CodeService codeService;

	@Autowired
	CustomerService customerService;

	/**
	 * 默认,跳转到礼券查询页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView present() {
		// 礼券状态
		Code status = codeService.get(Code.PRESENT_STATUS);
		ModelAndView mav = new ModelAndView("present/query_giftcertificate");
		mav.addObject("status", status);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	@RequestMapping(value = "/dispensePage", method = RequestMethod.GET)
	public ModelAndView goDispense(@RequestParam("id") Long id) {
		PresentBatch presentBatch = presentService.getBatch(id);
		ModelAndView mav = new ModelAndView("/present/dispense");
		mav.addObject("presentBatch", presentBatch);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	@RequestMapping(value = "/dispense", method = RequestMethod.POST)
	public ModelAndView dispense(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "dispenseType", required = true) String type,
			@RequestParam(value = "dispenseObj", required = true) String dispenseObj) {
		List<String> list = new ArrayList<String>(Arrays.asList(dispenseObj.split("\r\n")));
		PresentBatch presentBatch = presentService.getBatch(id);
		Integer remainCount = presentBatch.getNum()- presentBatch.getCreatedNum();
		ModelAndView mav = new ModelAndView("/present/result");
		mav.addObject(MESSAGE, "分发礼券成功");
		StringBuffer listErrorNames = new StringBuffer();
		try {
			if ("email".equals(type)) {
				if (list.size() <= (remainCount)) {
					presentService.sendPresent4Email(presentBatch, list,null);
				} else {
					mav.addObject(MESSAGE, "邮箱数量不能大于可分发礼券数量");
				}
			} else {
				List<Customer> listCustomers = new ArrayList<Customer>();
				for (String s : list) {
					Customer customer = customerService.getByName(s);
					if (customer != null) {
						listCustomers.add(customer);
					} else {
						listCustomers.clear();
						listErrorNames.append(s+" ");
						break;
					}
				}
				if(listCustomers.isEmpty()){
					mav.addObject(MESSAGE, listErrorNames.toString()+"用户不存在，分发失败");
				}else if(remainCount - listCustomers.size() < 0&&presentBatch.getNum()!=ZERO){
					mav.addObject(MESSAGE, "账户数量不能大于可分发礼券数量");
				} else{
					presentService.sendPresent4Customer(presentBatch,listCustomers,null);
				}
			}
		} catch (PresentException e) {
			mav.addObject(MESSAGE, e.getMessage());
		}
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 查询礼券
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(@Valid PresentFindForm presentFindForm,
			@MyInject Pagination pagination) throws ParseException {
		// 构建参数MAP
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(presentFindForm.getCodeName(),
				presentFindForm.getCodingLong());
		parameters.put("state", presentFindForm.getStatusList());
		parameters.put("startDate", presentFindForm.getStartDate());
		parameters.put("endDate", presentFindForm.getEndDate());
 		// 查询礼券列表
		List<Present> presents = presentService.find(parameters, pagination);
		// 礼券状态
		Code status = codeService.get(Code.PRESENT_STATUS);
		// 返回
		ModelAndView mav = new ModelAndView("/present/query_giftcertificate");
		//礼券批次状态
		Code batchStatus = codeService.get(Code.PRESENT_BATCH_STATUS);
		mav.addObject("batchStatus", batchStatus);
		mav.addObject("presents", presents);
		mav.addObject("status", status);
		mav.addObject("presentFindForm", presentFindForm);
		mav.addObject("pagination", pagination);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 礼券详细
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}/particular", method = RequestMethod.GET)
	public ModelAndView particular(@PathVariable("id") Long id) {
		// 获取当前礼券
		Present present = presentService.get(id);
		// 返回
		ModelAndView mav = new ModelAndView(
				"/present/giftcertificate_particular");
		mav.addObject("present", present);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 注销礼券
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(@RequestParam("logout") Long[] id,
			@MyInject Employee operator) {
		int sucess = 0;
		// 循环注销礼券
		for (Long lon : id) {
			try {
				// 注销，如果注销失败则忽略跳过
				Present present = presentService.get(lon);
				presentService.cancelPresent(present, operator);
				sucess++;
			} catch (PresentException e) {
				continue;
			}
		}
		ModelAndView mav = new ModelAndView("/present/logout");
		mav.addObject("all", id.length);
		mav.addObject("count", sucess);
		mav.addObject("result", sucess != 0);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}
}
