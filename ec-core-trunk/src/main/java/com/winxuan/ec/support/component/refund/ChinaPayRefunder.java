package com.winxuan.ec.support.component.refund;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.winxuan.ec.exception.ChinaPayRefunderException;
import com.winxuan.ec.model.bank.Chinapay;
import com.winxuan.ec.model.bank.constant.ChinaPayRefundConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.BatchPay;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.order.BatchPayService;
import com.winxuan.ec.service.payment.PaymentCredentialService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.util.ApplicationContextUtils;

/**
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-5-20 下午4:13:35 --!
 * 
 ******************************** 
 */
public class ChinaPayRefunder extends Refunder<Chinapay> {

	/**
	 * 中国银联
	 */
	private static final Long CHINA_PAY = 82L;
	
	private static final Long STATUS_SUCESS = 3L;

	private static final Long STATUS_SUBMIT_SUCESS = 1L;

	private static final Log LOG = LogFactory.getLog(ChinaPayRefunder.class);
	/**
	 * 截取交易时间
	 */
	private static final Integer SUBDATE_START = 0;
	private static final Integer SUBDATE_END = 8;
	
	private ExceptionLogService exceptionLogService = ApplicationContextUtils.getBean(ExceptionLogService.class);
	
	private PaymentCredentialService paymentCredentialService = ApplicationContextUtils.getBean(PaymentCredentialService.class);
	private PaymentService paymentService = ApplicationContextUtils.getBean(PaymentService.class);
	private BatchPayService batchPayService = ApplicationContextUtils.getBean(BatchPayService.class);

	@Override
	public String refund(RefundCredential refundCredential) throws  ChinaPayRefunderException{
		String result = StringUtils.EMPTY;
		Chinapay bank = this.getBank();
		OrderPayment orderPayment = refundCredential.getOrder()
				.getOnlinePayPayment();
		if (orderPayment == null) {
			return result;
		}
		LOG.info(orderPayment.getPayment());
		PaymentCredential paymentCredential = orderPayment.getCredential();
		/**
		 * 检查支付凭证是否属于银联
		 */
		if (paymentCredential == null || !paymentCredential.getPayment().getId().equals(CHINA_PAY)) {
			LOG.info("无效检查凭证处理："+paymentCredential == null?"凭证为空":paymentCredential.getPayment().getName());
			paymentCredential = paymentCredentialService
					.getByOuterIdAndPayment(paymentService.get(CHINA_PAY),
							refundCredential.getOuterId());
		}
		String outerId = paymentCredential.getOuterId();
		bank.setRefunderDate(getTransDate(refundCredential,outerId));
		bank.init(outerId, refundCredential.getMoney(), "");
		bank.setPriv1(String.format("%s:%s",refundCredential.getOrder().getId(),System.currentTimeMillis()));
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(bank.getRefundAction());
		post.addParameter(ChinaPayRefundConstant.REFUND_RTRANS_TYPE,
				bank.getTransType());
		post.addParameter(ChinaPayRefundConstant.REFUND_MER_ID,
				bank.getPartner());
		post.addParameter(ChinaPayRefundConstant.REFUND_ORDER_ID,
				bank.getOrdId());
		post.addParameter(ChinaPayRefundConstant.REFUND_PRIV1, bank.getPriv1());
		post.addParameter(ChinaPayRefundConstant.REFUND_REFUND_AMOUNT,
				bank.getTransAmt());
		post.addParameter(ChinaPayRefundConstant.REFUND_RETURN_URL,
				bank.getRefundNotifyUrl());
		post.addParameter(ChinaPayRefundConstant.REFUND_TRANS_DATE,
				bank.getRefunderDate());
		post.addParameter(ChinaPayRefundConstant.REFUND_VERSION,
				bank.getVersion());
		post.addParameter(ChinaPayRefundConstant.REFUND_CHKVALUE,
				bank.getRefundChkValue());
		try {
			exceptionLogService.info(refundCredential.getId(),String.format("请求报文:%s,\n请求地址:%s",
					this.showPostParameters(post), post.getURI()), orderPayment.getPayment().getName());
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
		} catch (HttpException e) {
			exceptionLogService.error(refundCredential.getId(),String.format("请求异常:%s",
					e.getMessage()), orderPayment.getPayment().getName());
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			exceptionLogService.error(refundCredential.getId(),String.format("请求异常:%s",
					e.getMessage()), orderPayment.getPayment().getName());
			LOG.error(e.getMessage(), e);
		}
		
		return result.trim();
	}
	

	/**
	 * 展示post请求参数
	 * 
	 * @param post
	 */
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
		String search = "&Status=";
		int start = res.indexOf(search) + search.length();
		Long resultStatus = null;
		try {
			resultStatus = Long.valueOf(res.substring(start, start + 1));
		} catch (NumberFormatException e) {
			LOG.error(res);
			LOG.error(String.format("应答报文解析出错：%s \n 错误信息：%s", res,e.getMessage()));
			return RefundCredential.STATUS_FAILED;
		}
		if (resultStatus.equals(STATUS_SUBMIT_SUCESS)) {
			return RefundCredential.STATUS_OTHER_WAIT;
		}
		return RefundCredential.STATUS_FAILED;
	}

	@Override
	public boolean checkCallBackUrl(HttpServletRequest request) {
		return true;
	}

	private boolean checkRequest(HttpServletRequest request) {
		if (!"0".equals(request.getParameter("ResponseCode"))) {
			LOG.error("check failure" + Refunder.getReturnParamsString(request));
			return false;
		}

		Chinapay chinapay = this.getBank();
		PrivateKey privateKey = new PrivateKey();
		boolean flag = privateKey.buildKey(chinapay.getPgpNo(), 0,
				chinapay.getPgpubkPath());
		if (flag) {
			try {
				SecureLink secureLink = new SecureLink(privateKey);
				StringBuilder plainData = new StringBuilder();
				plainData.append(request.getParameter("MerID"));
				plainData.append(request.getParameter("ProcessDate"));
				plainData.append(request.getParameter("TransType"));
				plainData.append(request.getParameter("OrderId"));
				plainData.append(request.getParameter("RefundAmout"));
				plainData.append(request.getParameter("Status"));
				plainData.append(request.getParameter("Priv1"));
				boolean verf = secureLink.verifyAuthToken(plainData.toString(),
						request.getParameter("CheckValue"));
				return verf;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				return false;
			}

		} else {
			LOG.error("build key fail. pgpNo is: " + chinapay.getPgpNo()
					+ ",pgpubk path is: " + chinapay.getPgpubkPath(),
					new RuntimeException("build key fail. pgpNo is: "
							+ chinapay.getPgpNo() + ",pgpubk path is: "
							+ chinapay.getPgpubkPath()));
			return false;
		}
	}

	@Override
	public RefundCallBackForm getCallBackForm(HttpServletRequest request) {
		RefundCallBackForm callBackForm = null;
		if (this.checkRequest(request)) {
			callBackForm = new RefundCallBackForm();
			callBackForm.setOuterId(request.getParameter("OrderId"));
			callBackForm.setRefundCredentialId(request
					.getAttribute(ChinaPayRefundConstant.REFUND_CREDENTIAL_ID)
					+ StringUtils.EMPTY);
			// String str = request.getParameter("ProcessDate");
			callBackForm.setRefundtime(new Date());
			Long status = Long.valueOf(request.getParameter("Status"));
			if (STATUS_SUCESS.equals(status)) {
				callBackForm
						.setStatus(new Code(RefundCredential.STATUS_SUCCESS));
			} else {
				callBackForm
						.setStatus(new Code(RefundCredential.STATUS_FAILED));
			}
		} else {
			LOG.error("check failure" + Refunder.getReturnParamsString(request));
		}
		return callBackForm;

	}

	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential) {
			return null;
	}
	
	public String getTransDate(RefundCredential refundCredential,String outerId){
		String transDate = outerId.substring(SUBDATE_START, SUBDATE_END);
		BatchPay batchPay = batchPayService.get(refundCredential.getOuterId());
		String search = "&transdate=";
		String params = batchPay.getParams();
		if(StringUtils.isNotBlank(params)){
			int start = params.indexOf(search) + search.length();
			transDate = params.substring(start, start + 8);
		}
		return transDate;
	}

}
