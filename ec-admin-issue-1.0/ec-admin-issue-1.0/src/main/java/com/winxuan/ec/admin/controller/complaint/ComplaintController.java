package com.winxuan.ec.admin.controller.complaint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComplain;
import com.winxuan.ec.model.customer.CustomerComplainReply;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.customer.CustomerComplainReplyService;
import com.winxuan.ec.service.customer.CustomerComplainService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author fred
 * 
 */
@Controller
@RequestMapping(value = "/complaint")
public class ComplaintController {

	@Autowired
	private CustomerComplainService complainInfoService;
	@Autowired
	private CustomerComplainReplyService customerComplainReplyService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/goList", method = RequestMethod.GET)
	public ModelAndView index(ComplainForm complainForm,
			@MyInject Pagination pagination)
			throws ParseException {
		ModelAndView mode = new ModelAndView("/complaint/list");
		mode.addAllObjects(this.assemblyDataForBase(pagination, null));
		mode.getModelMap().remove("pagination");
		return mode;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView list(@Valid ComplainForm complainForm,
			@MyInject Pagination pagination)
			throws ParseException {
		ModelAndView mode = new ModelAndView("/complaint/list");
		Map<String, Object> parameters = complainForm.generateQueryMap();
		mode.addAllObjects(assemblyDataForBase(pagination, parameters));
		mode.addObject("state", complainForm.getState());
		return mode;
	}

	/**
	 *
	 * @return
	 */
	private Map<String, ?> assemblyDataForBase(Pagination pagination,
			Map<String, Object> parameters) {
		Map<String, Object> map = new HashMap<String, Object>();
		pagination.setPageSize(MagicNumber.TWENTY);
		List<CustomerComplain> list = complainInfoService.find(parameters,
				pagination, (short) 0);
		map.put("pagination", pagination);
		map.put("list", list);
		return map;
	}

	/**
	 * 跳转到投诉回复页面
	 */
	@RequestMapping(value = "/goReply", method = RequestMethod.GET)
	public ModelAndView goReply(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/complaint/reply");
		CustomerComplain customerComplain = complainInfoService.get(id);
		mav.addObject("customerComplain", customerComplain);
		return mav;
	}

	/**
	 * 回复
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public ModelAndView reply(ComplainReplyForm complainReplyForm,
			@MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("redirect:/complaint/goReply?id="
				+ complainReplyForm.getId());
		CustomerComplain complain = complainInfoService.get(complainReplyForm
				.getId());
		if (complain != null) {
			CustomerComplainReply reply = this.packagReply(complain, employee,
					complainReplyForm);
			customerComplainReplyService.save(reply);
			mav.addObject("customerComplain", reply.getQuestion());
		}
		return mav;
	}

	private CustomerComplainReply packagReply(CustomerComplain complain,
			Employee employee, ComplainReplyForm complainReplyForm) {
		CustomerComplainReply reply = new CustomerComplainReply();
		reply.setQuestion(complain);
		reply.setContent(complainReplyForm.getContent());
		reply.setReplier(employee);
		reply.setReplyTime(new Date());
		complain.setUpdateTime(new Date());
		complain.setUser(employee);
		complain.setState(new Code(Code.COMPLAIN_INFO_STATUS_FINISH));
		return reply;
	}

	/**
	 * 修改回复
	 */
	@RequestMapping(value = "/updatereply", method = RequestMethod.POST)
	public ModelAndView updateReply(ComplainReplyForm complainReplyForm,
			@MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/complaint/result");

		CustomerComplainReply cr = customerComplainReplyService
				.get(complainReplyForm.getId());
		if (cr != null) {
			cr.setContent(complainReplyForm.getContent());
			cr.setReplier(employee);
			cr.setReplyTime(new Date());
			customerComplainReplyService.update(cr);
			mav.addObject(ControllerConstant.MESSAGE_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} else {
			mav.addObject(ControllerConstant.MESSAGE_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
		}

		return mav;

	}

	/**
	 * 删除投诉记录
	 */
	@RequestMapping(value = "/deleteComplaint", method = RequestMethod.GET)
	public ModelAndView deleteComplaint(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("/complaint/result");
		CustomerComplain customerComplain = complainInfoService.get(id);
		complainInfoService.delete(customerComplain);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	/**
	 * 删除投诉回复记录
	 */
	@RequestMapping(value = "/deleteReply", method = RequestMethod.GET)
	public ModelAndView deleteReply(@RequestParam("id") Long id,
			@RequestParam("ccid") Long ccid) {
		ModelAndView mav = new ModelAndView("/complaint/result");

		ComplainReplyForm complainReplyForm = new ComplainReplyForm();
		complainReplyForm.setId(id);
		mav.addObject("complainReplyForm", complainReplyForm);

		customerComplainReplyService.delete(customerComplainReplyService
				.get(id));
		CustomerComplain complain = complainInfoService.get(ccid);

		if (complain.getReplyList().size() == MagicNumber.ZERO) {
			complain.setState(new Code(Code.COMPLAIN_INFO_STATUS_DISPOSE));
			complainInfoService.update(complain);
		}
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

}
