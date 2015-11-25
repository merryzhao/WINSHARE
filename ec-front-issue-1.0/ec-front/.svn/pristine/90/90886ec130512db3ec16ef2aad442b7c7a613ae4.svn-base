/*
 * @(#)OnlinePayRefundCallBackController.java
 *
 */

package com.winxuan.ec.front.controller.order.refund;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winxuan.ec.exception.UrlCheckException;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.Chinapay;
import com.winxuan.ec.model.bank.constant.ChinaPayRefundConstant;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.component.refund.Refunder;

/**
 * description
 * @author  huangyixiang
 * @version 2013-5-17
 */
@Controller
@RequestMapping(value="/callback/refund/{bankId}")
public class OnlinePayRefundCallBackController {
	

	private static final Log LOG = LogFactory.getLog(OnlinePayRefundCallBackController.class);
	
	@Autowired
	private RefundService refundService;
	
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request
			,@PathVariable Long bankId) throws UrlCheckException, UnsupportedEncodingException {
		this.showRequest(request, bankId);
		Bank bank = BankConfig.getBank(bankId);
		Refunder refunder = bank.getRefunder();
		if(!refunder.checkCallBackUrl(request)){
			UrlCheckException e = new UrlCheckException(request.getRequestURI() + "?" + request.getQueryString(), "url验证失败");
			LOG.error("ReturnParamsString : " + Refunder.getReturnParamsString(request));
			exceptionLogService.error("", String.format("退款异常:<%s>,退款通知：<%s>", 
					e.getMessage(),Refunder.getReturnParamsString(request)),
					paymentService.get(bankId).getName());
			throw e;
		}
		if(bank instanceof Chinapay){
			this.setRefundCredentialId(request);
		}
		
		RefundCallBackForm refundCallBackForm = refunder.getCallBackForm(request);
		if(refundCallBackForm!=null){
			RefundCredential refundCredential = refundService.get(refundCallBackForm.getRefundCredentialId());
			refunder.refundCallBackSetCredential(request, refundCredential);
			refundService.updateRefundCredential(refundCredential);
			exceptionLogService.info(refundCredential.getId(), "退款通知:"+refundCredential.getMessage(),
					refundCredential.getPayment().getName());
			return refunder.getSuccessResponse();
		}
		return refunder.getFailResponse();
	}
	
	/**
	 * set一个RefundCredentialId
	 * @param request
	 */
	private void setRefundCredentialId(HttpServletRequest request){
		String outerId = request.getParameter(ChinaPayRefundConstant.REFUND_ORDER_ID);
		String priv1 = request.getParameter(ChinaPayRefundConstant.REFUND_PRIV1);
		String[] privs = priv1.split(":");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("outerId", outerId);
		parameters.put("paymentId", Payment.CHINA_PAY);
		parameters.put("orderIds", privs[0]);
		RefundCredential refundCredential = refundService.getRefundCredentialId(parameters);
		request.setAttribute(ChinaPayRefundConstant.REFUND_CREDENTIAL_ID,refundCredential.getId());
	}
	
	private void showRequest(HttpServletRequest request,Long bankId){
		LOG.info(bankId+":"+Refunder.getReturnParamsString(request));
	}
}
