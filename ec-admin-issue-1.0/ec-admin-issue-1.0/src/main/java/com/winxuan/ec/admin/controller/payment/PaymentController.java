/*
 * @(#)PaymentControl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.payment;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.payment.PaymentService;

/**
 * 处理支付方式跳转
 * 
 * @author zhongsen
 * @version 1.0,2011-8-2
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

	private static final String PAYMENT_EDIT_STR = "paymentEditForm";
	private static final String MESSAGE_NOT_FOUND_PAYMENT="没有找到对应的支付方式";
	private static final String MESSAGE_SAVE_SUCCESS="";
	
	@Autowired
	PaymentService paymentService;

	@Autowired
	CodeService codeService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		List<Payment> payments = paymentService.find(false, false);
		ModelAndView mav = new ModelAndView("/payment/list");
		mav.addObject("payments", payments);
		return mav;
	}

	/**
	 * 添加支付方式
	 * 
	 * @param paymentEditForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@Valid PaymentEditForm paymentEditForm,
			BindingResult result) {
		if (!result.hasErrors()) {
			BigDecimal payFee = paymentEditForm.getPayFee();
			BigDecimal payFeeMin = paymentEditForm.getPayFeeMin();
			BigDecimal refundFee = paymentEditForm.getRefundFee();
			BigDecimal refundFeeMin = paymentEditForm.getRefundFeeMin();
			Payment payment = new Payment();
			payment.setAllowRefund(paymentEditForm.isAllowRefund());
			payment.setAvailable(paymentEditForm.isAvailable());
			payment.setDescription(paymentEditForm.getDescription());
			payment.setName(paymentEditForm.getName());
			if (null != payFee) {
				payment.setPayFee(payFee);
			}
			if (null != payFeeMin) {
				payment.setPayFeeMin(payFeeMin);
			}
			Long payFeeType = paymentEditForm.getPayFeeType();
			if (null != payFeeType) {
				payment.setPayFeeType(codeService.get(payFeeType));
			}
			if (null != refundFee) {
				payment.setRefundFee(refundFee);
			}
			if (null != refundFeeMin) {
				payment.setRefundFeeMin(refundFeeMin);
			}
			Long refundFeeType = paymentEditForm.getPayFeeType();
			if (null != payFeeType) {
				payment.setPayFeeType(codeService.get(refundFeeType));
			}
			payment.setRefundTerm(paymentEditForm.getRefundTerm());
			payment.setShow(paymentEditForm.isShow());
			payment.setType(codeService.get(paymentEditForm.getType()));
			paymentService.save(payment);
			ModelAndView mav = new ModelAndView("/payment/success");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/payment/_new");
			mav.addObject(PAYMENT_EDIT_STR, paymentEditForm);
			return mav;
		}

	}

	/**
	 * 跳转到添加支付方式页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newPayment() {
		PaymentEditForm paymentEditForm = new PaymentEditForm();
		ModelAndView mav = new ModelAndView("/payment/_new");
		Code payment = codeService.get(Code.PAYMENT_TYPE);
		mav.addObject(PAYMENT_EDIT_STR, paymentEditForm);
		mav.addObject("paymentType", payment);
		return mav;
	}

	/**
	 * 跳转到修改支付方式页面
	 * 
	 * @param paymentId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("paymentId") String paymentId) {
		Payment payment = paymentService.get(Long.valueOf(paymentId));
		PaymentEditForm paymentEditForm = new PaymentEditForm();
		paymentEditForm.setName(payment.getName());
		paymentEditForm.setAllowRefund(payment.isAllowRefund());
		paymentEditForm.setAvailable(payment.isAvailable());
		paymentEditForm.setDescription(payment.getDescription());
		paymentEditForm.setPayFee(payment.getPayFee());
		paymentEditForm.setPayFeeMin(payment.getPayFeeMin());
		Code payFeeType = payment.getPayFeeType();
		if (null == payFeeType) {
			paymentEditForm.setPayFeeType(null);
		} else {
			paymentEditForm.setPayFeeType(payFeeType.getId());
		}
		paymentEditForm.setRefundFee(payment.getRefundFee());
		paymentEditForm.setRefundFeeMin(payment.getRefundFeeMin());
		paymentEditForm.setRefundTerm(payment.getRefundTerm());
		Code refundFeeType = payment.getRefundFeeType();
		if (null == refundFeeType) {
			paymentEditForm.setRefundFeeType(null);
		} else {
			paymentEditForm.setRefundFeeType(refundFeeType.getId());
		}
		paymentEditForm.setShow(payment.isShow());
		paymentEditForm.setType(payment.getType().getId());
		ModelAndView mav = new ModelAndView("/payment/edit");
		mav.addObject("paymentId", paymentId);
		mav.addObject(PAYMENT_EDIT_STR, paymentEditForm);
		return mav;
	}

	/**
	 * 修改支付方式
	 * 
	 * @param paymentEditForm
	 * @param paymentId
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)
	public ModelAndView update(@PathVariable("paymentId") String paymentId,
			@Valid PaymentEditForm paymentEditForm, BindingResult result) {
		if (!result.hasErrors()) {
			Payment payment = new Payment();
			payment.setId(Long.valueOf(paymentId));
			payment.setAllowRefund(paymentEditForm.isAllowRefund());
			payment.setAvailable(paymentEditForm.isAvailable());
			payment.setDescription(paymentEditForm.getDescription());
			payment.setName(paymentEditForm.getName());
			payment.setPayFee(paymentEditForm.getPayFee());
			payment.setPayFeeMin(paymentEditForm.getPayFeeMin());
			Long payFeeType = paymentEditForm.getPayFeeType();
			if (null != payFeeType) {
				payment.setPayFeeType(codeService.get(payFeeType));
			}
			payment.setRefundFee(paymentEditForm.getRefundFee());
			payment.setRefundFeeMin(paymentEditForm.getRefundFeeMin());
			Long refundFeeType = paymentEditForm.getPayFeeType();
			if (null != payFeeType) {
				payment.setPayFeeType(codeService.get(refundFeeType));
			}
			payment.setRefundTerm(paymentEditForm.getRefundTerm());
			payment.setShow(paymentEditForm.isShow());
			payment.setType(codeService.get(paymentEditForm.getType()));
			paymentService.update(payment);
			ModelAndView mav = new ModelAndView("/payment/success");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/payment/edit");
			mav.addObject("paymentId", paymentId);
			mav.addObject(PAYMENT_EDIT_STR, paymentEditForm);
			return mav;
		}
	}
	
	@RequestMapping(value = "/{paymentId}/changeAvailable", method = RequestMethod.POST)
	public ModelAndView updateAvailable(@PathVariable("paymentId")Long paymentId){
		Payment payment = paymentService.get(paymentId);
		paymentService.update(payment);
		ModelAndView mav=new ModelAndView("/payment/result");
		ModelMap modelMap = new ModelMap();
		if(null==payment){
			modelMap.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelMap.put(ControllerConstant.MESSAGE_KEY, MESSAGE_NOT_FOUND_PAYMENT);
		}else{
			payment.setAvailable(!payment.isAvailable());
			paymentService.update(payment);
			modelMap.put(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
			modelMap.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		}
		mav.addAllObjects(modelMap);
		return mav;
	}
}
