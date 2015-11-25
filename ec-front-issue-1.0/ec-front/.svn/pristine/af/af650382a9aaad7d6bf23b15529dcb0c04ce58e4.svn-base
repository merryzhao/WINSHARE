/*
 * @(#)ChinapayCallBackController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.order.callback;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.Chinapay;
import com.winxuan.ec.model.order.CallBackForm;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-9
 */
@Controller
@RequestMapping(value="/order/onlinepay/chinapay")
public class ChinapayCallBackController extends OnlinePayCallBackController{

	private static final Log LOG = LogFactory.getLog(OnlinePayCallBackController.class);
	
	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request){
		return payByReturn(request);
	}
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request) {
		payByNotify(request);
		return "";
	}
	
	@Override
	protected boolean verify(HttpServletRequest request) {
		Chinapay chinapay = (Chinapay)BankConfig.getSimpleBank(Chinapay.class);
		PrivateKey privateKey = new PrivateKey(); 
		boolean flag = privateKey.buildKey(chinapay.getPgpNo(), 0, chinapay.getPgpubkPath());
		if(flag){
			SecureLink secureLink =  new SecureLink(privateKey);
			boolean verf =  secureLink.verifyTransResponse(request.getParameter("merid"), 
					request.getParameter("orderno"), request.getParameter("amount"),
					request.getParameter("currencycode"), request.getParameter("transdate"),
					request.getParameter("transtype"),request.getParameter("status") ,
					request.getParameter("checkvalue"));
			return verf && "1001".equals(request.getParameter("status"));
		}
		else{
			LOG.error("build key fail. pgpNo is: " + chinapay.getPgpNo()
					+ ",pgpubk path is: " + chinapay.getPgpubkPath(), new RuntimeException("build key fail. pgpNo is: " + chinapay.getPgpNo()
		                    + ",pgpubk path is: " + chinapay.getPgpubkPath()));
			return false;
		}
	}

	@Override
	protected CallBackForm getCallBackForm(HttpServletRequest request) {
		CallBackForm callBackForm = new CallBackForm();
		Chinapay chinapay = BankConfig.getSimpleBank(Chinapay.class);
		callBackForm.setMoney(new BigDecimal(request.getParameter("amount")).movePointLeft(2));
		callBackForm.setBatchId(request.getParameter("orderno").substring(0,chinapay.getTradeIdLength()));
		callBackForm.setOuterTradeNo(request.getParameter("orderno"));
		callBackForm.setPaymentId(chinapay.getId());
		return callBackForm;
	}




}
