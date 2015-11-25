/*
 * @(#)RefundServiceImpl.java
 *
 */

package com.winxuan.ec.service.refund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.OrderDao;
import com.winxuan.ec.dao.RefundCredentialDao;
import com.winxuan.ec.exception.RefundExpiredException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.exception.UnsupportBankException;
import com.winxuan.ec.model.bank.Bank;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderPayment;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author huangyixiang
 * @version 2013-5-15
 */
@Service("refundService")
@Transactional(rollbackFor = Exception.class)
public class RefundServiceImpl implements RefundService {

	 private static final Log LOG = LogFactory.getLog(RefundServiceImpl.class);
	/**
	 * 订单状态判断
	 */
	private static final List<Long> ORDER_PROCESS = Arrays.asList(
			Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL,
			Code.ORDER_PROCESS_STATUS_ERP_CANCEL);
	/**
	 * 无手续费比例
	 */
	private static final BigDecimal REFUNDFREE_ALL = new BigDecimal(1);

	/**
	 * 无外卡手续费金额
	 */
	private static final BigDecimal REFUNDFREE_FREE = new BigDecimal(0);
	
	@Autowired
	private EmployeeService employeeService;

	@InjectDao
	private RefundCredentialDao refundCredentialDao;

	@Autowired
	private CodeService codeService;

	@Autowired
	private PaymentService paymentService;

	@InjectDao
	private OrderDao orderDao;
	

	@Override
	public List<RefundCredential> findByOrder(String orderId) {
		return refundCredentialDao.findByOrder(orderDao.get(orderId));
	}

	@Override
	@Transactional(noRollbackFor = { UnsupportBankException.class,
			RefundExpiredException.class })
	public void refund2Card(Order order, BigDecimal refundMoney)
			throws ReturnOrderException, UnsupportBankException,
			RefundExpiredException {
		refund2Card(order, refundMoney, null, true, null);
	}

	@Override
	@Transactional(noRollbackFor = { UnsupportBankException.class,
			RefundExpiredException.class })
	public void refund2Card(Order order, BigDecimal refundMoney, Bank bank,
			boolean checkOrder, String outerId) throws ReturnOrderException,
			UnsupportBankException, RefundExpiredException {
		List<OrderPayment> refundOrderPayments = order.getCanRefundOnlinePayPayment();
		OrderPayment refundOrderPayment = null;
		if(refundOrderPayments.size()>1){
			throw new ReturnOrderException(order, "正在处理退款,暂时无法取消");
		}else if(refundOrderPayments!=null){
			refundOrderPayment  = refundOrderPayments.get(0);
		}
		
		if (bank == null) {
			bank = BankConfig.getBank(refundOrderPayment.getPayment().getId());
		}
		if (bank == null || !bank.canRefund()) {
			throw new UnsupportBankException(order, "不支持此银行退款,bank:"
					+ refundOrderPayment.getPayment().getId()
					+ " orderPaymentId:" + refundOrderPayment.getId());
		}

		if (checkOrder) {
			List<RefundCredential> existRefund = refundCredentialDao
					.findByOrder(order,order.getProcessStatus());
			if (existRefund != null && !existRefund.isEmpty()) {
				throw new ReturnOrderException(order, "该订单已经申请过一次退款");
			}
			if (refundOrderPayment == null) {
				throw new ReturnOrderException(order, "订单没有线上支付，不能退回原卡");
			}
			if (!refundOrderPayment.isPay()) {
				throw new ReturnOrderException(order, "订单未网上支付，不能退款");
			}
			if (refundMoney.compareTo(BigDecimal.ZERO) <= 0
					|| refundMoney.compareTo(refundOrderPayment
							.getCanRefundMoney()) > 0) {
				throw new ReturnOrderException(order, "退款金额小于等于0或者可退款金额不足");
			}
			Payment payment = paymentService.get(bank.getId());
			Integer refundTerm = payment.getRefundTerm();
			Date payTime = refundOrderPayment.getCredential().getPayTime();
			if (new Date().after(DateUtils.addDays(payTime, refundTerm))) {
				throw new RefundExpiredException(order, "超期，不能申请原卡原退,bank:"
						+ refundOrderPayment.getPayment().getId()
						+ " orderPaymentId:" + refundOrderPayment.getId());
			}
		} else {
			checkRefundLegal(order);
		}
		if(refundOrderPayment!=null&&StringUtils.isBlank(outerId)){
			outerId = refundOrderPayment.getCredential().getOuterId();
		}
		LOG.info("refund outerId："+outerId);
		saveRefundCredential(order, refundMoney, bank, outerId);
	}

	/**
	 * checkOrder为false时2种情况下可以退款： 1.已取消订单，网银支付后，原卡原退
	 * 2.未取消订单，网银支付后，比较批次，如果相同，不处理；若不同，核对订单状态，已经支付的订单，原卡原退
	 * 
	 * @param order
	 * @throws ReturnOrderException
	 */
	private void checkRefundLegal(Order order) throws ReturnOrderException {
		OrderPayment refundOrderPayment = order.getOnlinePayPayment();
		Code actualStatus = order.getProcessStatus();
		if ((actualStatus.equals(codeService
				.get(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL)) || actualStatus
				.equals(codeService
						.get(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL)))
				&& refundOrderPayment != null) {
			return;
		}
		if (refundOrderPayment != null
				&& order.getRequireOnlinePayMoney().equals(BigDecimal.ZERO)
				&& !order.getOnlinePayMoney().equals(BigDecimal.ZERO)) {
			return;
		}
		throw new ReturnOrderException(order, "非法的退款");
	}

	public List<RefundCredential> findWaitRefundCredential(Integer count) {
		return refundCredentialDao.findByStatus(
				codeService.get(RefundCredential.STATUS_SYS_WAIT), count);
	}

	public void updateRefundCredential(RefundCredential refundCredential) {
		refundCredentialDao.update(refundCredential);
	}

	public RefundCredential get(String id) {
		return refundCredentialDao.get(id);
	}

	private void saveRefundCredential(Order order, BigDecimal refundMoney,
			Bank bank, String outerId) {
		RefundCredential refundCredential = new RefundCredential();
		refundCredential.setCreateTime(new Date());
		refundCredential.setCustomer(order.getCustomer());
		refundCredential.setOperator(employeeService.get(Employee.SYSTEM));
		refundCredential.setOrder(order);
		refundCredential.setOuterId(StringUtils.isBlank(outerId) ? order
				.getOnlinePayPayment().getCredential().getOuterId() : outerId);
		refundCredential.setPayment(bank == null ? order.getOnlinePayPayment()
				.getPayment() : paymentService.get(bank.getId()));
		refundCredential.setStatus(codeService
				.get(RefundCredential.STATUS_SYS_WAIT));
		Payment payment = order.getOnlinePayPayment().getPayment();
		refundFreeMoney(refundCredential,order,refundMoney,payment);
		
		refundCredential.setProcessStatus(order.getProcessStatus());
		refundCredentialDao.save(refundCredential);
	}
	
	
	/**
	 * 客户或中启取消订单扣取手续费,系统取消无需手续费
	 */
	private void refundFreeMoney(RefundCredential refundCredential,Order order,BigDecimal refundMoney,Payment payment){
		BigDecimal refundfee = order.getOnlinePayPayment().getPayment()
				.getRefundFee();
		if (ORDER_PROCESS.contains(order.getProcessStatus().getId())) {   //客户取消，中启取消扣取手续费
			refundCredential.setMoney(refundMoney.multiply(REFUNDFREE_ALL.subtract(refundfee)));
			refundCredential.setRefundfreemoney(refundMoney.multiply(refundfee));
		} else {   //系统取消无手续费
			refundCredential.setMoney(refundMoney);
			refundCredential.setRefundfreemoney(REFUNDFREE_FREE);
		}
		LOG.info(String.format("<%s支付金额%s元>------<实际退款金额%s元>------<手续费收取%s元>",
				order.getOnlinePayPayment().getPayment().getName(),
				refundfee,refundCredential.getMoney(),refundCredential.getRefundfreemoney()));
	}

	@Override
	public RefundCredential getRefundCredentialId(Map<String, Object> parameters) {
		List<RefundCredential> reuslt = refundCredentialDao.find(parameters,new Pagination(),(short)0);
		if (CollectionUtils.isNotEmpty(reuslt)) {
			return reuslt.get(0);
		}
		return null;
	}

	@Override
	public List<Payment> getPaymentList() {
		List<Payment> paymentList = new ArrayList<Payment>();

		List<Object> paymentIdList = refundCredentialDao.getPaymentId();
		if (CollectionUtils.isEmpty(paymentIdList)) {
			return paymentList;
		}

		for (Object obj : paymentIdList) {
			Long id = Long.parseLong(obj.toString().substring(9,
					obj.toString().length() - 1));
			Payment payment = paymentService.get(id);
			if (payment != null) {
				paymentList.add(payment);
			}
		}
		return paymentList;
	}

	@Override
	public List<RefundCredential> find(Map<String, Object> params,
			Pagination pagination, short sort) {
		return refundCredentialDao.find(params, pagination, sort);
	}

	@Override
	public List<RefundMessage> find(Long id,boolean available) {
		return refundCredentialDao.find(id,available);
	}

	@Override
	public void saveorupdate(RefundMessage refundMessage) {
		refundCredentialDao.saveorupdate(refundMessage);
	}

	@Override
	public List<RefundMessage> findRefundMessage(Map<String, Object> params,
			Pagination pagination, short sort) {
		return refundCredentialDao.findRefundMessage(params, pagination, sort);
	}

	@Override
	public RefundMessage get(Long id) {
		return refundCredentialDao.get(id);
	}

	@Override
	public void saveOrUpdateRefundCredential(RefundCredential refundCredential) {
		refundCredentialDao.saveOrUpdate(refundCredential);
	}


}
