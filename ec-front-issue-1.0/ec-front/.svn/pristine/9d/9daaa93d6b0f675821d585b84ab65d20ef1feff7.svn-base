package com.winxuan.ec.front.controller.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComplain;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerComplainService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.verifycode.VerifyCodeService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author cast911 玄玖东
 * 
 */
@Controller
@RequestMapping(value = "/customer")
public class ComplaintController {

	@Autowired
	private CustomerComplainService customerComplainService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private VerifyCodeService verifyCodeService;

	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	public ModelAndView complaint(@MyInject Customer customer,
			@MyInject Pagination pagination) {
		ModelAndView mode = new ModelAndView("/customer/complaint/simple");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customer", customer.getId());
		pagination.setPageSize(MagicNumber.THREE);
		List<CustomerComplain> customerComplain = customerComplainService.find(
				parameters, pagination,(short) 0);
		mode.addObject("customer", customer);
		mode.addObject("pagination", pagination);
		mode.addObject("customerComplains", customerComplain);
		return mode;
	}

	/**
	 * 用户投诉
	 * 
	 * @param complainForm
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/complaint/save", method = RequestMethod.POST)
	public ModelAndView saveComplaint(
			ComplainForm complainForm,
			@MyInject Customer customer,
			@CookieValue(value = "verify_number", required = true) String verifyNumber) {
		ModelAndView modelAndView = new ModelAndView(
				"/customer/complaint/simple");
		try {
			if (verifyCodeService.verify(complainForm.getVerifyCode(),
					verifyNumber)) {
				CustomerComplain complainInfo = new CustomerComplain();
				Order o = null; 
				if (!"".equals(complainForm.getOrderId())) {
					o = orderService.get(complainForm.getOrderId());
					if (o == null||o.getCustomer() != customer) {
						modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
								"该订单号不存在!");
						return modelAndView;
					}
				}
				complainInfo.setOrder(o);
				if (!"".equals(complainForm.getEmail())) {
					complainInfo.setEmail(complainForm.getEmail());
				} else {
					complainInfo.setEmail(customer.getEmail());
				}

				if (!"".equals(complainForm.getPhone())) {
					complainInfo.setPhone(complainForm.getPhone());
				} else {
					complainInfo.setPhone(customer.getMobile());
				}

				complainInfo.setContent(complainForm.getContent());
				complainInfo.setCreateTime(new Date());

				complainInfo.setTitle(complainForm.getTitle());
				complainInfo.setState(new Code(
						Code.COMPLAIN_INFO_STATUS_DISPOSE));
				complainInfo.setCustomer(customer);
				customerComplainService.save(complainInfo);
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_SUCCESS);
				modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
						"我们已经接受到您的投诉, 将在第一时间联系您.");
			} else {
				modelAndView.addObject(ControllerConstant.RESULT_KEY,
						ControllerConstant.RESULT_PARAMETER_ERROR);
				modelAndView
						.addObject(ControllerConstant.MESSAGE_KEY, "验证码错误!");
			}
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_INTERNAL_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY, "服务器内部错误!");
		}

		return modelAndView;
	}

}
