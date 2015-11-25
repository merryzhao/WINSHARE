/*
 * @(#)BookContoller.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.groupbuy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.groupinfo.GroupShoppingInfo;
import com.winxuan.ec.service.groupinfo.GroupInfoService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;

/**
 * 团购首页
 * 
 * @author cast 911
 * @version 1.0,2011-11-10
 */
@Controller
public class GroupPurchaseController {

	@Autowired
	GroupInfoService groupInfoService;
	@Autowired
	private VerifyCodeService verifyCodeService;

	public VerifyCodeService getVerifyCodeService() {
		return verifyCodeService;
	}

	public void setVerifyCodeService(VerifyCodeService verifyCodeService) {
		this.verifyCodeService = verifyCodeService;
	}

	public GroupInfoService getGroupInfoService() {
		return groupInfoService;
	}

	public void setGroupInfoService(GroupInfoService groupInfoService) {
		this.groupInfoService = groupInfoService;
	}
	
	/**
	 *  屏蔽团购入口
	@RequestMapping(value = "/tuan", method = RequestMethod.GET)
	ModelAndView view() {
     	ModelAndView modelAndView = new ModelAndView("/grouppurchase/index");
		return modelAndView;
	}
	 */

	@RequestMapping(value = "/giftcard", method = RequestMethod.GET)
	public ModelAndView signinGet() {
		return new ModelAndView("presentcard/giftcard_index");
	}
   
	/**
	 * 是否是json格式
	 * @param format
	 * @return
	 */
	private boolean isJson(String format){
		if ("json".equals(format) || "jsonp".equals(format)){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/groupinfo/save",method=RequestMethod.POST)
	public ModelAndView save(GroupInfoForm groupInfoForm,
			@CookieValue(value = "verify_number") String verifyNumber,
			@RequestParam(required = false) String format) {
		ModelAndView modelAndView  = null;
		if(isJson(format)){
			modelAndView = new ModelAndView("/groupshopping/info");
		}else{
			modelAndView = new ModelAndView("/grouppurchase/index");
		}
		
	
		boolean flag = verifyCodeService.verify(groupInfoForm.getsRand(),
				verifyNumber);
		if (flag) {
			try {
				GroupShoppingInfo groupShoppingInfo = new GroupShoppingInfo();
				groupShoppingInfo
						.setCompanyName(groupInfoForm.getCompanyName());
				groupShoppingInfo.setPhone(groupInfoForm.getPhone());
				groupShoppingInfo.setContent(groupInfoForm.getContent());
				groupShoppingInfo.setName(groupInfoForm.getName());
				groupShoppingInfo.setCreateTime(new Date());
				groupShoppingInfo.setState(new Code(
						Code.GROUP_SHOPPING_STATUS_EXAMINE));
				this.groupInfoService.save(groupShoppingInfo);
				modelAndView.addObject("groupShoppingInfo",groupShoppingInfo);
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "申请成功!我们将在第一时间联系您.");
			} catch (Exception e) {
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_INTERNAL_ERROR);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
						"服务器内部错误!");
			}

		} else {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "验证码错误!");
		}
		return modelAndView;
	}

}
