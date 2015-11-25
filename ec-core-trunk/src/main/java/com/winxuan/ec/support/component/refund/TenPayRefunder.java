package com.winxuan.ec.support.component.refund;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tenpay.RequestHandler;
import com.tenpay.client.ClientResponseHandler;
import com.tenpay.client.TenpayHttpClient;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.bank.Tenpay;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.payment.PaymentCredentialService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.util.ApplicationContextUtils;

/**
 * ******************************
 * 
 * @author:cast911
 * @lastupdateTime:2013-6-9 下午2:08:44 --!
 * 
 ******************************** 
 */
public class TenPayRefunder extends Refunder<Tenpay> {

	private static final Log LOG = LogFactory.getLog(TenPayRefunder.class);

	private static final List<Long> SUCCES_STATUS = Arrays.asList(4L, 10L);

	private static final List<Long> WAIT_STATUS = Arrays.asList(8L, 9L, 11L);

	private static final List<Long> FAIL_STATUS = Arrays.asList(3L, 5L, 6L, 1L,
			2L);

	private PaymentCredentialService paymentCredentialService = ApplicationContextUtils
			.getBean(PaymentCredentialService.class);

	private PaymentService paymentService = ApplicationContextUtils
			.getBean(PaymentService.class);
	
	private ExceptionLogService exceptionLogService = ApplicationContextUtils.getBean(ExceptionLogService.class);

	@Override
	public String refund(RefundCredential refundCredential) throws Exception {
		Tenpay tenpay = this.getBank();
		RequestHandler reqHandler = this.initRequest(refundCredential);
		// 通信对象
		TenpayHttpClient httpClient = this.initHttpClient(reqHandler,
				refundCredential);
		exceptionLogService.info(refundCredential.getId(),String.format("请求报文:%s,\n请求地址:%s",
				this.showPostParameters(reqHandler), reqHandler.getRequestURL()),
				refundCredential.getPayment().getName());
		// 应答对象
		ClientResponseHandler resHandler = new ClientResponseHandler();
		String rescontent = "null";
		if (httpClient.call()) {
			rescontent = httpClient.getResContent();
			resHandler.setContent(rescontent);
			resHandler.setKey(tenpay.getKey());
			String retcode = resHandler.getParameter("retcode");
			if (resHandler.isTenpaySign() && "0".equals(retcode)) {
				/*
				 * 退款状态 refund_status 4，10：退款成功。 3，5，6：退款失败。 8，9，11:退款处理中。 1，2:
				 * 未确定，需要商户原退款单号重新发起。
				 * 7：转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号
				 * ，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
				 */
				String refundStatus = resHandler.getParameter("refund_status");
				String outRefundNo = resHandler.getParameter("out_refund_no");
				LOG.info("商户退款单号" + outRefundNo + "的退款状态是：" + refundStatus);
			} else {
				// 错误时，返回结果未签名，记录retcode、retmsg看失败详情。
				LOG.error("retcode:" + resHandler.getParameter("retcode")
						+ " retmsg:" + resHandler.getParameter("retmsg"));
			}
		} else {
			exceptionLogService.error(refundCredential.getId(), String.format("后台调用通信失败:<%s>,<%s>",
					httpClient.getResponseCode(),httpClient.getErrInfo()),
					refundCredential.getPayment().getName());
			LOG.error(httpClient.getResponseCode());
			LOG.error(httpClient.getErrInfo());
			throw new ReturnOrderException(refundCredential.getOrder(),
					"后台调用通信失败");
		}
		return rescontent;
	}

	@Override
	public Long getRefundProcessStatus(String res) {
		ClientResponseHandler resHandler = new ClientResponseHandler();
		try {
			resHandler.setContent(res);
			Long refundStatus = Long.valueOf(resHandler
					.getParameter("refund_status"));
			if (SUCCES_STATUS.contains(refundStatus)) {
				// this.saveOuterId(resHandler);
				return RefundCredential.STATUS_SUCCESS;
			} else if (WAIT_STATUS.contains(refundStatus)) {
				return RefundCredential.STATUS_OTHER_WAIT;
			} else if (FAIL_STATUS.contains(refundStatus)) {
				return RefundCredential.STATUS_FAILED;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return RefundCredential.STATUS_FAILED;
		}
		return RefundCredential.STATUS_FAILED;
	}

	@Override
	public boolean checkCallBackUrl(HttpServletRequest httpServletRequest) {
		return true;
	}

	@Override
	public RefundCallBackForm getCallBackForm(
			HttpServletRequest httpServletRequest) {
		return null;
	}

	/**
	 * 设置接口参数
	 * 
	 * @param refundCredential
	 * @return
	 * @throws ReturnOrderException
	 */
	private RequestHandler initRequest(RefundCredential refundCredential)
			throws ReturnOrderException {
		Tenpay tenpay = this.getBank();
		// 创建查询请求对象
		OrderPayment orderPayment = refundCredential.getOrder()
				.getOnlinePayPayment();
		if (orderPayment == null) {
			throw new ReturnOrderException(refundCredential.getOrder(),
					"该订单没有支付信息.检查该订单的支付状况");
		}
		PaymentCredential paymentCredential = orderPayment.getCredential();
		Payment payment = paymentService.get(Payment.TEN_PAY);
		if (paymentCredential == null) {
			paymentCredential = paymentCredentialService
					.getByOuterIdAndPayment(payment,
							refundCredential.getOuterId());
		}
	

		String transactionId = refundCredential.getOuterId();
		BigDecimal totalFee = paymentCredential.getMoney(); 
		BigDecimal refundFee = refundCredential.getMoney();
		String outRefundNo = System.currentTimeMillis() + "";
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init();
		reqHandler.setKey(tenpay.getKey());
		reqHandler.setGateUrl(tenpay.getRefundAction());
		reqHandler.setParameter("service_version", "1.1");
		reqHandler.setParameter("partner", tenpay.getPartner());
		reqHandler.setParameter("out_trade_no", "");
		reqHandler.setParameter("transaction_id", transactionId);
		reqHandler.setParameter("out_refund_no", outRefundNo);
		reqHandler.setParameter("total_fee", totalFee.movePointRight(2) + "");
		reqHandler.setParameter("refund_fee", refundFee.movePointRight(2) + "");
		reqHandler.setParameter("op_user_id", tenpay.getRefundOpUserId());
		reqHandler.setParameter("op_user_passwd",
				tenpay.getRefundUserPassWord());
		reqHandler.setParameter("recv_user_id", "");
		reqHandler.setParameter("reccv_user_name", "");
		return reqHandler;
	}
	
	private StringBuffer showPostParameters(RequestHandler reqHandler) {
		SortedMap<String, Object> map = reqHandler.getAllParameters();
		StringBuffer buildr = new StringBuffer();
		for (SortedMap.Entry<String, Object> parameter : map.entrySet()) {
			buildr.append(parameter.getKey() + "="
					+ parameter.getValue() + ",");
		}
		LOG.info(buildr.toString());
		return buildr;
	}

	/**
	 * 初始化通信对象
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private TenpayHttpClient initHttpClient(RequestHandler reqHandler,
			RefundCredential refundCredential)
			throws UnsupportedEncodingException {
		Tenpay tenpay = this.getBank();
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setTimeOut(20);
		httpClient.setCaInfo(new File(tenpay.getRefundCaInfo()));
		httpClient.setCertInfo(new File(tenpay.getRefundCertInfo()),
				tenpay.getPartner());
		httpClient.setMethod("POST");
		String requestUrl = reqHandler.getRequestURL();
		httpClient.setReqContent(requestUrl);
		return httpClient;

	}

	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential) {
		return null;
	}



}
