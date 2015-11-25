/*
 * @(#)IpsCallBackController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.order.callback;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.Ips;
import com.winxuan.ec.model.order.CallBackForm;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-11
 */
@Controller
@RequestMapping(value="/order/onlinepay/ips")
public class IpsCallBackController extends OnlinePayCallBackController{

	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request) {
		return payByReturn(request);
	}

	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request) {
		if (payByNotify(request)) {
			return "Success";
		}
		return "";
	}

	@Override
	protected boolean verify(HttpServletRequest request) throws Exception {
		boolean flag = false;
		String billno = request.getParameter("billno");
		String amount = request.getParameter("amount");
		String mydate = request.getParameter("date");
		String succ = request.getParameter("succ");
		String ipsbillno = request.getParameter("ipsbillno");
		String signature = request.getParameter("signature");
		Ips ips = BankConfig.getSimpleBank(Ips.class);
		// IPS 3.0平台增加 (币种)
		String currencyType = request.getParameter("Currency_type");
		// 交易成功
		if ("Y".equalsIgnoreCase(succ)) {
			// MD5withRSA 验证结果
			cryptix.jce.provider.MD5WithRSA a = new cryptix.jce.provider.MD5WithRSA();
			// 明文：订单编号+订单金额+订单日期+成功标志+IPS订单编号+币种（IPS3.0平台添加币种）
			String content = billno + amount + mydate + succ + ipsbillno + currencyType;
			// verify the message
			// 参数说明(明文,密文,公钥路径)
			a.verifysignature(content, signature,ips.getPubKeyPath());
			// 验证签名成功
			if (a.getresult() == 0) {
				flag = true;
			} 
		} 
		return flag;
	}

	@Override
	protected CallBackForm getCallBackForm(HttpServletRequest request) {
		CallBackForm callBackForm = new CallBackForm();
		Ips ips = BankConfig.getSimpleBank(Ips.class);
		callBackForm.setMoney(new BigDecimal(request.getParameter("amount")));
		callBackForm.setBatchId(request.getParameter("billno"));
		callBackForm.setOuterTradeNo(request.getParameter("ipsbillno"));
		callBackForm.setPaymentId(ips.getId());
		return callBackForm;
	}


}
