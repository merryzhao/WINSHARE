package com.winxuan.ec.admin.controller.refund;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.refund.RefundCredential;
import com.winxuan.ec.model.refund.RefundLog;
import com.winxuan.ec.model.refund.RefundMessage;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.exception.ExceptionLogService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.service.refund.RefundService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author  周斯礼
 * @version 2013-7-1
 */
@Controller
@RequestMapping(value="/refund")
public class RefundController {
	
	@Autowired
	private RefundService refundService;
	@Autowired
	private ExceptionLogService exceptionLogService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private PaymentService paymentService;
	private Logger logger = Logger.getLogger(RefundController.class);
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("/refund/list");
		List<Payment> paymentList = refundService.getPaymentList();
		List<Code> statusList = codeService.findByParent(RefundCredential.STATUS);
		RefundForm refundForm = new RefundForm();
		mav.addObject("statusList",statusList);
		mav.addObject("refundForm",refundForm);
		mav.addObject("paymentList", paymentList);
		return mav;
	}
	
	
	@RequestMapping(value="/getRefundCredential", method=RequestMethod.POST)
	public ModelAndView getRefundCredential(@MyInject Pagination pagination,
			RefundForm refundForm){
		ModelAndView mav = new ModelAndView("/refund/list");
		List<Payment> paymentList = refundService.getPaymentList();
		List<Code> statusList = codeService.findByParent(RefundCredential.STATUS);
		List<RefundCredential> credentials = null;
		try {
			credentials = refundService.find(refundForm.getRefundParams(),
					pagination, refundForm.getSort());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		mav.addObject("statusList",statusList);
		mav.addObject("refundForm",refundForm);
		mav.addObject("credentials",credentials);
		mav.addObject("pagination",pagination);
		mav.addObject("paymentList", paymentList);
		return mav;
	}
	
	@RequestMapping(value="/getRefundLog/{refundId}", method=RequestMethod.GET)
	public ModelAndView getRefundLog(@PathVariable String refundId){
		ModelAndView mav = new ModelAndView("/refund/log");
		RefundCredential refundCredential =  refundService.get(refundId);
		List<RefundLog> refundLogs =exceptionLogService.get(refundId);
		mav.addObject("refundLogs", refundLogs);
		mav.addObject("refundCredential",refundCredential);
		return mav;
	}
	@RequestMapping(value="/indexMessage", method=RequestMethod.GET)
	public ModelAndView indexMessage(){
		ModelAndView mav = new ModelAndView("/refund/message");
		List<Payment> paymentList = refundService.getPaymentList();
		mav.addObject("paymentList", paymentList);
		return mav;
	}
	
	@RequestMapping(value="/newMessage/{messageId}", method=RequestMethod.GET)
	public ModelAndView newMessage(@PathVariable Long messageId){
		ModelAndView mav = new ModelAndView("/refund/new");
		if(messageId != -1){
			RefundMessage refundMessage = refundService.get(messageId);
			mav.addObject("refundMessage", refundMessage);
		}
		List<Payment> paymentList = refundService.getPaymentList();
		mav.addObject("paymentList", paymentList);
		return mav;
	}
	
	@RequestMapping(value="/delMessage/{messageId}", method=RequestMethod.POST)
	public ModelAndView delMessage(@PathVariable Long messageId){
		ModelAndView mav = new ModelAndView("/refund/result");
		RefundMessage refundMessage = refundService.get(messageId);
		refundMessage.setAvailable(!refundMessage.isAvailable());
		refundService.saveorupdate(refundMessage);
		mav.addObject("message", refundMessage.isAvailable());
		return mav;
	}
	
	@RequestMapping(value="/findRefundMessage", method=RequestMethod.POST)
	public ModelAndView findRefundMessage(@MyInject Pagination pagination,
			@RequestParam(value="paymentId",required=false)Long paymentId,
			@RequestParam(value="available",required=false)String available){
		ModelAndView mav = new ModelAndView("/refund/message");
		Map<String, Object> params = new HashMap<String, Object>();
		if(paymentId.longValue() != 0){
			params.put("paymentId", paymentId);
		}
		if(!"-1".equals(available)){
			params.put("available", "1".equals(available)?true:false);
		}
		List<RefundMessage> refundMessages =  refundService.findRefundMessage(params, pagination, (short)0);
		List<Payment> paymentList = refundService.getPaymentList();
		mav.addObject("paymentId", paymentId);
		mav.addObject("available", available);
		mav.addObject("paymentList", paymentList);
		mav.addObject("refundMessages",refundMessages);
		mav.addObject("pagination",pagination);
		return mav;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("refundMessage") RefundMessage refundMessage,			
			@MyInject Employee employee){
		ModelAndView mav = new ModelAndView("refund/new");
		refundMessage.setCreateTime(new Date());
		refundMessage.setEmployee(employee);
		refundMessage.setPayment(paymentService.get(refundMessage.getPayment().getId()));
		refundService.saveorupdate(refundMessage);
		List<Payment> paymentList = refundService.getPaymentList();
		mav.addObject("refundMessage",refundMessage);
		mav.addObject("paymentList",paymentList);
		return mav;
	}
	
	
	@RequestMapping(value="/detail")
	public ModelAndView detail(@RequestParam(value="id")String id){
		ModelAndView mav = new ModelAndView("/refund/detail");
		RefundCredential credential = refundService.get(id);
		mav.addObject("credential", credential);
		return mav;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public ModelAndView edit(@RequestParam("id")String id,
			@RequestParam("status")Long status,
			@RequestParam("message")String message,
			@MyInject Employee employee){
		ModelAndView mav = new ModelAndView("refund/result");
		if(employee == null){
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "请先登陆");
			return mav;
		}
		RefundCredential credential = refundService.get(id);
		if(credential == null){
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "系统内部错误");
			return mav;
		}
		credential.setStatus(new Code(status));
		credential.setMessage(message);
		credential.setRefundTime(new Date());
		refundService.updateRefundCredential(credential);
		mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		return mav;
	}
}


