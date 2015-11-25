/*
 * @(#)RefundRequest.java
 *
 */

package com.winxuan.ec.task.job.refund;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.component.refund.Refunder;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2013-5-16
 */
@Component("refundRequest")
// @Transactional(rollbackFor = Exception.class)
public class RefundRequest implements Serializable, TaskAware {

	private static final Log LOG = LogFactory.getLog(RefundRequest.class);

	/**
	 * 手动退款的支付类型
	 */
	private static final List<Long> NOT_REFUND = Arrays.asList(127L);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244174902992811511L;
	/**
	 * 日志长度
	 */
	private static final Integer SIZE = 65500;

	@Autowired
	RefundService refundService;

	@Autowired
	CodeService codeService;

	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;
	
	@Autowired
	private ExceptionLogService exceptionService; 

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		List<RefundCredential> refundCredentialList = null;
		//处理退款失败
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int size = 0;
		while ((refundCredentialList = refundService
				.findWaitRefundCredential(20)) != null
				&& !refundCredentialList.isEmpty()) {
			for (RefundCredential refundCredential : refundCredentialList) {
				Long paymentId = refundCredential.getPayment().getId();
				/**
				 * 手动退款不进行后续处理
				 */
				if (this.manualProcessing(refundCredential)) {
					continue;
				}
				Bank bank = BankConfig.getBank(paymentId);
				Refunder refunder = bank.getRefunder();
				String response;
				String paymentName = refundCredential.getPayment().getName();
				
				try {
					exceptionService.info(refundCredential.getId(),String.format("共(%s)退款：%s", 
							refundCredentialList.size(),size++),paymentName);
					response = refunder.refund(refundCredential);
					refundCredential.setStatus(codeService.get(refunder
							.getRefundProcessStatus(response)));
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					exceptionService.error(refundCredential.getId(), "退款异常 \n"+exceptionMessage(e), 
							refundCredential.getPayment().getName());
					response = e.getMessage();
					refundCredential.setStatus(codeService
							.get(RefundCredential.STATUS_FAILED));
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				response = simpleDateFormat.format(new Date()) + " : "
						+ response;
				exceptionService.info(refundCredential.getId(), "应答报文\n"+response,refundCredential.getPayment().getName());
				refundCredential.setMessage(response);
				
				if (RefundCredential.STATUS_SUCCESS.equals(refundCredential
						.getStatus().getId())) {
					refundCredential.setRefundTime(new Date());
				}
				refundService.updateRefundCredential(refundCredential);
			}
		}
		hibernateLazyResolver.releaseSession();
	}

	/**
	 * 该逻辑为手动退款,后续修正为自动退款 by cast911
	 * @param refundCredential
	 * @return
	 */
	private boolean manualProcessing(RefundCredential refundCredential) {
		Long paymentId = refundCredential.getPayment().getId();
		boolean result = NOT_REFUND.contains(paymentId);
		if (result) {
			refundCredential.setMessage("Waiting for the business people");
			refundCredential.setStatus(codeService
					.get(RefundCredential.STATUS_OTHER_WAIT));
			refundCredential.setRefundTime(new Date());
			refundService.updateRefundCredential(refundCredential);
		}
		return result;

	}
	
	/**
	 * 解决退款失败  按退款失败原因分别处理退款，再次退款时间需在本年
	 * @throws IOException 
	 * @throws Exception
	 */
	private int refundFailed() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", RefundCredential.STATUS_FAILED);
		List<RefundCredential> refundCredentials = refundService.find(params, null, (short)0);
		for (RefundCredential refundCredential : refundCredentials) {
			Long paymentId = refundCredential.getPayment().getId();
			Bank bank = BankConfig.getBank(paymentId);
			Refunder refunder = bank.getRefunder();
			Date refundtime = refundCredential.getCreateTime();
			String refundId = refundCredential.getId();
			if(new DateTime(refundtime).getYear()==new DateTime(new Date()).getYear()){
				RefundCredential refund =  refunder.refundFailed(refundCredential);
				if(refund!=null){
					refundService.saveOrUpdateRefundCredential(refund);
					if(CollectionUtils.isNotEmpty(refund.getChildren())){
						exceptionService.info(refundId,String.format("原退款<%s:%s>已更换批次号",
								refundId,refund.getStatus().getName()),
								refundCredential.getPayment().getName());
					}
				}
			}
		}
		return refundCredentials.size();
	}
	
	
	/**
	 * 错误信息打印
	 * @param e
	 * @return
	 */
	public StringBuffer exceptionMessage(Exception e){
		StringBuffer message = new StringBuffer();
		StackTraceElement [] messages=e.getStackTrace();
		for (StackTraceElement stackTraceElement : messages) {
			message.append(stackTraceElement.getClassName());
			message.append(String.format("((%s)%s:%s)\n",stackTraceElement.getFileName(),stackTraceElement.getMethodName(),stackTraceElement.getLineNumber()));
		}
		if(message.length() > SIZE){
			return new StringBuffer(message.substring(0, SIZE));
		}
		return message;
	}

	@Override
	public String getName() {
		return "refundRequest";
	}

	@Override
	public String getDescription() {
		return "原卡原退";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}
}
