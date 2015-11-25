package com.winxuan.ec.support.component.refund;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hisun.iposm.HiiposmUtil;
import com.winxuan.ec.exception.ShouJiZhiFuRefunderException;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.ShouJiZhiFu;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderBatchPay;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.payment.PaymentCredentialService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.util.ApplicationContextUtils;



/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-6-27 上午11:09:59  --!
 * @description:取这个名字名字只是为了和ShouJiZhiFu.java 保持对应.没有实现
 ********************************
 */
public class ShouJiZhiFuRefunder extends Refunder<ShouJiZhiFu> {

	private static final Log LOG = LogFactory.getLog(ShouJiZhiFuRefunder.class);
	
	private static final Long SHOU_JI_ZHI_FU = 126L;
	
	private static final String SUCCESS = "SUCCESS";

	private ExceptionLogService exceptionLogService = ApplicationContextUtils.getBean(ExceptionLogService.class);
	
	private PaymentCredentialService paymentCredentialService = ApplicationContextUtils
			.getBean(PaymentCredentialService.class);
	
	private PaymentService paymentService = ApplicationContextUtils.getBean(PaymentService.class);
	
	private RefundService refundService = ApplicationContextUtils.getBean(RefundService.class);
	
	private BatchPayService batchPayService = ApplicationContextUtils.getBean(BatchPayService.class);
	
	@Override
	public String refund(RefundCredential refundCredential) throws ShouJiZhiFuRefunderException {
		String result = "";
		ShouJiZhiFu bank = (ShouJiZhiFu)BankConfig.getBank(126L);
		OrderPayment orderPayment = refundCredential.getOrder().getOnlinePayPayment();
		if (orderPayment == null) {
			return result;
		}
		LOG.info(orderPayment.getPayment());
		PaymentCredential paymentCredential = orderPayment.getCredential();
		if(paymentCredential == null){
			paymentCredential = paymentCredentialService.getByOuterIdAndPayment(paymentService
					.get(SHOU_JI_ZHI_FU), refundCredential.getOuterId());
		}
		String outerId = paymentCredential.getOuterId();
		bank.init(outerId, refundCredential.getMoney(), "");
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(bank.getRefundAction());
		buildParams(bank, post, refundCredential);
		try {
			exceptionLogService.info(refundCredential.getId(),
					String.format("请求报文:%s,\n请求地址:%s",this.showPostParameters(post), post.getURI())
					,orderPayment.getPayment().getName());
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
			LOG.info(result);
		} catch (HttpException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return result.trim();
	}

	private void buildParams(ShouJiZhiFu bank, PostMethod post, RefundCredential refundCredential) {
		String type = "OrderRefund";
		OrderBatchPay batchPay = batchPayService.getByOrderId(refundCredential.getOrder().getId());
		Date date = new Date();
		String amount = refundCredential.getMoney().multiply(new BigDecimal(100)).intValueExact()+"";
		post.addParameter("merchantId", bank.getPartner());
		post.addParameter("requestId", date.getTime()+"");
		post.addParameter("signType", bank.getSignType());
		post.addParameter("type", type);
		post.addParameter("version", bank.getVersion());
		post.addParameter("orderId", batchPay.getBatchPayId());
		post.addParameter("amount", amount);
		StringBuffer signData = new StringBuffer();
		signData
			.append(bank.getPartner())
			.append(date.getTime()+"")
			.append(bank.getSignType())
			.append(type)
			.append(bank.getVersion())
			.append(batchPay.getBatchPayId())
			.append(amount);
		HiiposmUtil hiiposmUtil = new HiiposmUtil();
		String hmac = hiiposmUtil.MD5Sign(signData.toString(), bank.getSignKey());
		post.addParameter("hmac", hmac);
	}
	

	private StringBuffer showPostParameters(PostMethod post) {
		NameValuePair[] parametrs = post.getParameters();
		StringBuffer buildr = new StringBuffer();
		for (NameValuePair nameValuePair : parametrs) {
			buildr.append(nameValuePair.getName() + "="
					+ nameValuePair.getValue() + ",");
		}
		LOG.info(buildr.toString());
		return buildr;
	}
	
	@Override
	public Long getRefundProcessStatus(String res) {
		String[] results = res.split("&");
		if(results.length != 0){
			for(String result : results){
				if(!result.startsWith("status")){
					continue;
				}else{
					if("SUCCESS".equals(result.split("=")[1])){
						return RefundCredential.STATUS_SUCCESS;
					}else{
						return RefundCredential.STATUS_FAILED;
					}
				}
			}
		}
		return RefundCredential.STATUS_FAILED;
	}

	@Override
	public boolean checkCallBackUrl(HttpServletRequest httpServletRequest) {
		return true;
	}
	
	@Override
	public RefundCallBackForm getCallBackForm(
			HttpServletRequest request) {
		RefundCallBackForm callBackForm = new RefundCallBackForm();
		String outerId = request.getParameter("orderId");
		callBackForm.setOuterId(outerId);
		//这个参数没用到
		callBackForm.setRefundCredentialId(outerId);
		callBackForm.setRefundtime(new Date());
		String status = request.getParameter("status");
		if(SUCCESS.equals(status)){
			callBackForm.setStatus(new Code(RefundCredential.STATUS_SUCCESS));
		}else{
			callBackForm.setStatus(new Code(RefundCredential.STATUS_FAILED));
		}
		return callBackForm;
	}
	
	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
