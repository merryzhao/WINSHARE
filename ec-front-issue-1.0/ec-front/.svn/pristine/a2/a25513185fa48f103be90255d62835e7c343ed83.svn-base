/*
 * @(#)TenpayCallBackConrtoller.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.front.controller.order.callback;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tenpay.RequestHandler;
import com.tenpay.ResponseHandler;
import com.tenpay.client.ClientResponseHandler;
import com.tenpay.client.TenpayHttpClient;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.Tenpay;
import com.winxuan.ec.model.order.CallBackForm;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-11
 */
@Controller
@RequestMapping(value="/order/onlinepay/tenpay")
public class TenpayCallBackConrtoller extends OnlinePayCallBackController{

	private static final Log LOG = LogFactory.getLog(AlipayCallBackController.class);
	
	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request) {
		return payByReturn(request);
	}

	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request, HttpServletResponse response) {
		if(!payByNotify(request)){
			return "fail";
		}
		Tenpay tenpay = BankConfig.getSimpleBank(Tenpay.class);
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(tenpay.getKey());
		// 通知id
		String notifyId = resHandler.getParameter("notify_id");
		// 创建请求对象
		RequestHandler queryReq = new RequestHandler(null, null);
		// 通信对象
		TenpayHttpClient httpClient = new TenpayHttpClient();
		// 应答对象
		ClientResponseHandler queryRes = new ClientResponseHandler();
		// 通过通知ID查询，确保通知来至财付通
		queryReq.init();
		queryReq.setKey(tenpay.getKey());
		queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
		queryReq.setParameter("partner", tenpay.getPartner());
		queryReq.setParameter("notify_id", notifyId);
		final int timeOut = 4000;
		try{
		// 通信对象
		httpClient.setTimeOut(timeOut);
		// 设置请求内容
		httpClient.setReqContent(queryReq.getRequestURL());
		// 后台调用
		if (httpClient.call()) {
			// 设置结果参数
			queryRes.setContent(httpClient.getResContent());
			queryRes.setKey(tenpay.getKey());
			// 获取返回参数
			String retcode = queryRes.getParameter("retcode");// 0表示成功非0表示失败
			String tradeState = resHandler.getParameter("trade_state");// 支付结果：0—成功1—失败
			String tradeMode = resHandler.getParameter("trade_mode");// 1-即时到账
			// 其他保留
			// 判断签名及结果
			if (queryRes.isTenpaySign() && "0".equals(retcode)
					&& "0".equals(tradeState) && "1".equals(tradeMode)) {
				resHandler.sendToCFT("Success");
				return "Success";
			} else {
				// 错误时，返回结果未签名，记录retcode、retmsg看失败详情。
				LOG.info("验证状态返回失败" + "retcode:"
						+ queryRes.getParameter("retcode") + " retmsg:"
						+ queryRes.getParameter("retmsg"));
			}
		} else {
			// 有可能因为网络原因，请求已经处理，但未收到应答。
			LOG.info("验证状态请求失败" + httpClient.getResponseCode()
					+ httpClient.getErrInfo());
		}
		}catch (Exception e) {
			LOG.info(e.getMessage(),e);
		}
		return "fail";
	}

	@Override
	protected boolean verify(HttpServletRequest request) throws Exception {
		Tenpay tenpay = BankConfig.getSimpleBank(Tenpay.class);
		boolean flag = false;
		//创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, null);
		resHandler.setKey(tenpay.getKey());
		//判断签名
		if(resHandler.isTenpaySign()) {
			//支付结果
			String tradeState = resHandler.getParameter("trade_state");
			String tradeMode = resHandler.getParameter("trade_mode");
			if( "0".equals(tradeState) && "1".equals(tradeMode) ) 
			{
				flag = true;
			}
		}
		return flag;
	}

	@Override
	protected CallBackForm getCallBackForm(HttpServletRequest request) {
		CallBackForm callBackForm = new CallBackForm();
		Tenpay tenpay = BankConfig.getSimpleBank(Tenpay.class);
		callBackForm.setMoney(new BigDecimal(request.getParameter("total_fee")).movePointLeft(2));
		callBackForm.setBatchId(request.getParameter("out_trade_no"));
		callBackForm.setOuterTradeNo(request.getParameter("transaction_id"));
		callBackForm.setPaymentId(tenpay.getId());
		return callBackForm;
	}


}
