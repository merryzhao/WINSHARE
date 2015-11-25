/*
 * @(#)AlipayCallBackController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.order.callback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.util.SignatureHelper;
import com.winxuan.ec.model.bank.Alipay;
import com.winxuan.ec.model.bank.AlipayNetBank;
import com.winxuan.ec.model.bank.AlipayWildCard;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.framework.util.io.HttpResource;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-11
 */
@Controller
@RequestMapping(value="/order/onlinepay/alipay")
public class AlipayCallBackController extends OnlinePayCallBackController{

	private static final String SPACE = "  ";
	private static final Log LOG = LogFactory.getLog(AlipayCallBackController.class);
	
	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request){
		return payByReturn(request);
	}
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request) {
		if (payByNotify(request)) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	protected boolean verify(HttpServletRequest request) throws Exception{
		Alipay bank = getBank(request);
		boolean flag = false;
		boolean urlCheck = urlCheck(bank, request);
		
		if (!urlCheck) {
			LOG.info("验证支付状态失败：urlCheck = " + urlCheck);
			return false;
		}
		boolean signCheck = false;
		String mysign1 = SignatureHelper.sign(getReturnParams(request,false), bank.getKey());
		if (!mysign1.equalsIgnoreCase(request.getParameter("sign"))) {
			LOG.info("getReturnParams = " + getReturnParams(request,false) + SPACE + "bank = " + bank.getKey());
			String mysign2 = SignatureHelper.sign(getReturnParams(request,true), bank.getKey());
			if(!mysign2.equalsIgnoreCase(request.getParameter("sign"))){
				LOG.info("getReturnParams = " + getReturnParams(request,true) + SPACE + "bank = " + bank.getKey());
				LOG.info("验证支付状态失败");
				return false;
			}
		}
		signCheck = true;
		String tradeStatus = request.getParameter("trade_status");
		if(("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus) 
				|| "TRADE_FINISHED".equalsIgnoreCase(tradeStatus))
				&& urlCheck && signCheck){
			flag = true;
		}
		else {
			LOG.info("trade_status = " + request.getParameter("trade_status"));
		}
		return flag;
	}
	
	private boolean urlCheck(Alipay bank, HttpServletRequest request) {
		if(bank instanceof AlipayWildCard){
			return alipayWildCardUrlCheck(bank, request);
		}
		else{
			return alipayUrlCheck(bank, request);
		}
	}
	
	private boolean alipayWildCardUrlCheck(Alipay bank, HttpServletRequest request){
		String verifyUrl = "https://mapi.alipay.com/gateway.do"
			+ "?service=notify_verify"
			+ "&partner=" + bank.getPartner()
			+ "&notify_id=" + request.getParameter("notify_id");
		String alipayText = null;
		try {
			alipayText = new String(
					new HttpResource(verifyUrl).getContent());
		} catch (IOException e) {
			LOG.info("verifyUrl = " + verifyUrl + SPACE + "alipayText = " + alipayText + SPACE + e.getMessage(), e);
		}
		boolean urlCheck = "true".equalsIgnoreCase(alipayText);
		if(!urlCheck){
			LOG.info("verifyUrl = " + verifyUrl + SPACE + "alipayText = " + alipayText + SPACE);
		}
		return urlCheck;
	}
	
	private boolean alipayUrlCheck(Alipay bank, HttpServletRequest request){
		String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "partner="
				+ bank.getPartner()
				+ "&notify_id="
				+ request.getParameter("notify_id");
		// 获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String alipayText = "";
		try {
			alipayText = new String(
					new HttpResource(alipayNotifyURL).getContent());
		} catch (IOException e) {
			LOG.info(e.getMessage(), e);
		}
		boolean urlCheck = "true".equalsIgnoreCase(alipayText);
		return urlCheck;
	}

	@Override
	protected CallBackForm getCallBackForm(HttpServletRequest request) {
		CallBackForm callBackForm = new CallBackForm();
		Alipay bank = getBank(request);
		callBackForm.setMoney(new BigDecimal(request.getParameter("total_fee")));
		callBackForm.setBatchId(request.getParameter("out_trade_no"));
		callBackForm.setOuterTradeNo(request.getParameter("trade_no"));
		callBackForm.setPaymentId(bank.getId());
		return callBackForm;
	}
	
	private Alipay getBank(HttpServletRequest request){
		Alipay alipay = BankConfig.getSimpleBank(Alipay.class);
		AlipayNetBank alipayNetBank = BankConfig.getSimpleBank(AlipayNetBank.class);
		AlipayWildCard alipayWildCard = BankConfig.getSimpleBank(AlipayWildCard.class);
		String partner = request.getParameter("seller_email");
		if(partner != null){
			if(partner.equals(alipay.getSellerEmail())){
				return alipay;
			}else if(partner.equals(alipayNetBank.getSellerEmail())){
				return alipayNetBank;
			}else if(partner.equals(alipayWildCard.getSellerEmail())){
				return alipayWildCard;
			}
		}
		return null;
	}
	
	private Map<String, String> getReturnParams(HttpServletRequest request,
			boolean encode) throws UnsupportedEncodingException {
		Map<String, String> params = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		for (Object param : requestParams.keySet()) {
			String name = (String) param;
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			if (encode) {
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化（现在已经使用）
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			}
			params.put(name, valueStr);
		}
		return params;
	}


}
