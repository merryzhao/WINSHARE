/*
 * @(#)CategoryController.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.customer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccountDetail;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.ec.model.payment.Payment;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.UserStatusLog;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerLockService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.service.payment.PaymentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;
/**
 * 用户后台控制
 * 
 * @author wumaojie
 * @version 1.0,2011-8-2
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static final String DEFUALT="-1";
	private static final String STATUSID="statusId";
	private static final String JSONRESULT="/customer/success_result";
	private static final String PAGINATION="pagination";
	private static final String ID="id";
	private static final String CUSTOMER="customer";
	private static final String STATUS="status";
	private static final String DATAFORMAT="yyyy-MM-dd";
	private static final String ERRORID="id为";
	private static final String ERROR="error";
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private DateFormat dateFormat;

	@Autowired
	private AttachService attachService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CustomerLockService customerLockService;

	/**
	 * 根据参数查询暂存款
	 * 
	 * @param orderId
	 * @param customerName
	 * @param startDate
	 * @param endDate
	 * @param pagination
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/advanceaccountList", method = RequestMethod.POST)
	public ModelAndView listAdvanceaccount(@Valid AdvanceAccountFrom advanceAccountFrom,
			BindingResult result,
			@MyInject Pagination pagination) throws ParseException {
		// 构建map
		Map<String, Object> parameters = new HashMap<String, Object>();
		String customerName = advanceAccountFrom.getCustomerName();
		if(!StringUtils.isBlank(customerName)){
			Customer c = customerService.getByNameOrEmail(customerName);
			if(c != null){
				parameters.put("account", c.getAccount());
			}
		}
		parameters.put("orderId", advanceAccountFrom.getOrderId());
		parameters.put("startDate", advanceAccountFrom.getStartDate());
		parameters.put("endDate", advanceAccountFrom.getEndDate());
		// 查询暂存款详细数据
		List<CustomerAccountDetail> customerAccountDetails = customerService
		.findAccountDetail(parameters, pagination);	
		ModelAndView mav = new ModelAndView("/customer/advanceaccountList");
		mav.addObject("customerAccountDetails", customerAccountDetails);
		mav.addObject(PAGINATION, pagination);
		mav.addObject("advanceAccountFrom", advanceAccountFrom);
		return mav;
	}
	/**
	 * 跳转到暂存款，返回所有数据
	 * 
	 * @param pagination
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/advanceaccountQuery", method = RequestMethod.GET)
	public ModelAndView viewAdvanceaccount(){
		ModelAndView mav = new ModelAndView("/customer/advanceaccountList");
		return mav;
	}

	/**
	 * 跳转到会员管理
	 * 
	 */
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(){
		ModelAndView mav = new ModelAndView("/customer/customer_manage");
		mav.addObject("sources", codeService.get(Code.USER_SOURCE));
		return mav;
	}

	/**
	 * 初始化日期
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat(DATAFORMAT);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value="/gotoAddBalance",method = RequestMethod.GET)
	public ModelAndView payByPaymentCredential(){
		ModelAndView mav = new ModelAndView();
		List<Payment> listPayment = paymentService.find(true, false);
		List<Payment> newListPayment = new ArrayList<Payment>();
		Code type = null;
		for (Payment payment : listPayment) {
			type = payment.getType();
			if (type.getId().equals(Code.PAYMENT_TYPE_CHANNEL)
					|| type.getId().equals(Code.PAYMENT_TYPE_OFFLINE)
					|| type.getId().equals(Code.PAYMENT_TYPE_ONLINE)) {
				newListPayment.add(payment);
			}
		}
		mav.addObject("listPayment",newListPayment);
		BalanceAddForm balanceAddForm = new BalanceAddForm();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		mav.addObject("today", sdf.format(new Date()));
		balanceAddForm.setPayTime(sdf.format(new Date()));
		mav.addObject("balanceAddForm",balanceAddForm);
		mav.setViewName("/customer/balance");
		return mav;
	}

	@RequestMapping(value="/attach",method = RequestMethod.GET)
	public ModelAndView getattach(@MyInject Employee operator,@MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/customer/attachList");
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put(ID,operator.getId() );
		List<EmployeeAttach> list = attachService.find(parameters, (short)0, pagination);
		mav.addObject("list",list);
		mav.addObject(PAGINATION,pagination);
		return mav;
	}

	@RequestMapping(value = "/addBalance", method = RequestMethod.POST)
	public ModelAndView createBalance(@Valid BalanceAddForm balanceAddForm,BindingResult result,@MyInject Employee employee )
	throws ParseException, OrderStatusException, CustomerAccountException{
		Customer customer = customerService.getByName(balanceAddForm.getCustomer());
		SimpleDateFormat format = new SimpleDateFormat(DATAFORMAT);
		PaymentCredential paymentCredential = new PaymentCredential();
		paymentCredential.setPayment(paymentService.get(balanceAddForm.getPayment()));
		paymentCredential.setPayTime(format.parse(balanceAddForm.getPayTime()));
		paymentCredential.setCustomer(customerService.getByName(balanceAddForm.getCustomer()));
		paymentCredential.setMoney(NumberUtils.createBigDecimal(balanceAddForm.getMoney()));
		paymentCredential.setOuterId(balanceAddForm.getOuterId());
		paymentCredential.setPayer(balanceAddForm.getPayer());
		paymentCredential.setRemark(balanceAddForm.getRemark());
		paymentCredential.setOperator(employee);
		customerService.chargeAccount(customer, NumberUtils.createBigDecimal(balanceAddForm.getMoney()), paymentCredential);
		ModelAndView mav = new ModelAndView("/customer/result");
		mav.addObject(CUSTOMER,balanceAddForm.getCustomer());
		mav.addObject("money",balanceAddForm.getMoney());
		return mav;
	}

	@RequestMapping(value = "/getBalanceByName", method = RequestMethod.POST)
	public ModelAndView listStorageType(@Parameter("name") String name) {
		ModelAndView mav = new ModelAndView("/order/result");
		Customer customer = customerService.getByNameOrEmail(name);
		ModelMap map = new ModelMap();
		if(customer!=null){
			map.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
			map.put(ControllerConstant.MESSAGE_KEY, customer.getAccount().getBalance());
		}else{
			map.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_INTERNAL_ERROR);
		}
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/checkCustomer", method = RequestMethod.GET)
	public ModelAndView checkCustomer(@RequestParam("name") String name) {
		ModelAndView mav = new ModelAndView("/order/result");
		Customer customer = customerService.getByName(name);
		ModelMap map = new ModelMap();
		if(customer!=null){
			for(CustomerAddress ca: customer.getAddressList()){
				if(ca.isUsual()){
					map.put("address", ca);
					break;
				}
			}
			map.put(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);

		}
		mav.addAllObjects(map);
		return mav;
	}

	@RequestMapping(value = "/confirmBalance", method = RequestMethod.POST)
	public ModelAndView payByPaymentCredential(@Valid BalanceAddForm balanceAddForm,BindingResult result,@MyInject Employee employee,@RequestParam("balance") String balance )
	throws ParseException, OrderStatusException, CustomerAccountException{
		ModelAndView mav = new ModelAndView();
		if(!result.hasErrors()){
			Payment payment = paymentService.get(balanceAddForm.getPayment());
			mav.setViewName("/customer/balanceConfirm");
			mav.addObject("balanceAddForm",balanceAddForm);
			mav.addObject("balance", balance);
			mav.addObject("payment", payment.getName());
		}else{
			List<Payment> listPayment = paymentService.find(Code.PAYMENT_TYPE_ONLINE);
			List<Payment> newListPayment = new ArrayList<Payment>();
			for(Payment payment:listPayment){
				if(payment.isAvailable()){
					newListPayment.add(payment);
				}
			}
			mav.addObject("listPayment",newListPayment);
			mav.addObject("balanceAddForm",balanceAddForm);
			mav.setViewName("/customer/balance");
		}
		return mav;
	}
	/**
	 * 查询会员信息跳转会员信息详细画面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/_info", method = RequestMethod.GET)
	public ModelAndView customerInfo(@PathVariable(ID) Long id) {
		ModelAndView mav = new ModelAndView("/customer/customer_info");
		Customer customer = customerService.get(id);
		mav.addObject(CUSTOMER,customer);
		return mav;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(SearchForm searchForm,@MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/customer/customer_manage");
		Map<String,Object> parameters = searchForm.getParameters();
		List<Long> longs = new ArrayList<Long>();
		if(searchForm.getChannel()!=null){
			Channel channel = channelService.get(searchForm.getChannel());
			if(channel!=null){
				searchForm.getChildChannels(channel, longs);
				parameters.put("channelIds",longs );
			}
		}
		List<Customer> list = customerService.find(parameters, pagination);
		mav.addObject("customerList", list);
		mav.addObject(PAGINATION, pagination);
		return mav;
	}

	@RequestMapping(value="/changeState/{id}", method=RequestMethod.GET)
	public ModelAndView changeChannelState(@PathVariable(ID) Long id,@MyInject Employee employee){
		ModelAndView mav=new ModelAndView("/channel/success_result");
		ModelMap modelMap = new ModelMap();
		Customer c = customerService.get(id);
		if(c.isAvailable()){
			//c.setAvailable(false);
			customerLockService.lockUser(c, employee);
			modelMap.put(ControllerConstant.MESSAGE_KEY, "禁用");
		}else{
			//c.setAvailable(true);
			customerLockService.accountUnLock(c, employee);
			modelMap.put(ControllerConstant.MESSAGE_KEY, "启用");
		}
		modelMap.put(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
		//customerService.update(c);
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value="/balancemanage", method=RequestMethod.GET)
	public ModelAndView balanceReturnManage(){
		ModelAndView mav=new ModelAndView("/customer/balance_return_query");
		mav.addObject("types",codeService.get(Code.CUSTOMER_CASH_TYPE).getAvailableChildren());
		mav.addObject("statuses", codeService.get(Code.CUSTOMER_CASH_STATUS).getAvailableChildren());
		return mav;
	}

	/**
	 * 查询退款信息
	 * @param request
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/findinfo", method = RequestMethod.POST)
	public ModelAndView findInfo(HttpServletRequest request,@MyInject Pagination pagination,@MyInject Employee employee){
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String customerName=request.getParameter(CUSTOMER);
		String status=request.getParameter(STATUS);
		String type=request.getParameter("type");
		String code=request.getParameter("code");
		ModelAndView mav = new ModelAndView("/customer/balance_return_query");
		Map<String,Object> parameters = new HashMap<String, Object>();
		if (!StringUtils.isBlank(customerName)) {
			Customer c = customerService.getByNameOrEmail(customerName);
			parameters.put("customer", c);
		}
		if(!StringUtils.isBlank(code)){
			parameters.put(ID, Long.valueOf(code));
		}
		if(!DEFUALT.equals(status)){
			parameters.put(STATUS, Long.valueOf(status));
		}
		if(!DEFUALT.equals(type)){
			parameters.put("type", Long.valueOf(type));
		}
		Date startTime=getStartDateTime(startDate);
		Date endTime=getEndDateTime(endDate);
		if(startTime!=null){
			parameters.put("startCreateTime", startTime);
		}
		if(endTime!=null){
			parameters.put("endCreateTime", endTime);
		}
		//保存查询条件
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.addObject("CUSTOMER", customerName);
		mav.addObject(STATUS, status);
		mav.addObject("type", type);
		mav.addObject("code", code);
		List<CustomerCashApply> customerCashApplies=customerService.findCustomerCashApply(parameters, pagination);
		mav.addObject("customerCashApplys", customerCashApplies);
		mav.addObject("types",codeService.get(Code.CUSTOMER_CASH_TYPE).getAvailableChildren());
		mav.addObject("statuses", codeService.get(Code.CUSTOMER_CASH_STATUS).getAvailableChildren());
		mav.addObject(PAGINATION, pagination);
		return mav;
	}

	/**
	 * 返回开始时间
	 * @return
	 */
	private Date getStartDateTime(String startDate) {
		SimpleDateFormat dateformat = new SimpleDateFormat(DATAFORMAT);
		if(StringUtils.isNotBlank(startDate)){
			try {
				Date date = dateformat.parse(startDate);
				return date;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	/**
	 * 返回截止时间
	 * @return
	 */
	private Date getEndDateTime(String endDate) {
		SimpleDateFormat dateformat = new SimpleDateFormat(DATAFORMAT);
		if(StringUtils.isNotBlank(endDate)){
			try {
				Date date = dateformat.parse(endDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1);
				return calendar.getTime();
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 批量处理
	 * @param request
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/processall", method = RequestMethod.POST)
	public ModelAndView processAll(HttpServletRequest request,@MyInject Employee employee){
		String cids=request.getParameter("cids");
		ModelAndView mav = new ModelAndView(JSONRESULT);
		String error="";
		List<String> errorIds=new ArrayList<String>();
		String[] ids=cids.split(",");
		for(int i=0;i<ids.length;i++){
			if(!StringUtils.isBlank(ids[i])){
				CustomerCashApply customerCashApply=customerService.getCashApply(Long.valueOf(ids[i]));
				if(customerCashApply!=null)
				{
					try {
						customerService.updateProcessingCashApply(customerCashApply, employee);
						errorIds.add(ids[i]);
					} catch (Exception e) {
						error=error+ERRORID+ids[i]+"的记录处理失败: "+e.getMessage()+"\n";

					}
				}
			}
		}
		mav.addObject("errorIds",errorIds);
		mav.addObject(ERROR,error);
		mav.addObject(STATUSID, Code.CUSTOMER_CASH_STATUS_PROCESSING);
		mav.addObject(STATUS,codeService.get(Code.CUSTOMER_CASH_STATUS_PROCESSING).getName());
		return mav;
	}

	/**
	 * 处理
	 * @param request
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public ModelAndView process(HttpServletRequest request,@MyInject Employee employee){
		String id=request.getParameter(ID);
		ModelAndView mav = new ModelAndView(JSONRESULT);
		String error="";
		if(!StringUtils.isBlank(id)){
			CustomerCashApply customerCashApply=customerService.getCashApply(Long.valueOf(id));
			if(customerCashApply!=null)
			{
				try {
					customerService.updateProcessingCashApply(customerCashApply, employee);
				}
				catch (Exception e) {
					error=error+ERRORID+id+"的记录处理失败: "+e.getMessage();
				}
			}
		}
		mav.addObject(ERROR,error);
		mav.addObject(STATUSID, Code.CUSTOMER_CASH_STATUS_PROCESSING);
		mav.addObject(STATUS,codeService.get(Code.CUSTOMER_CASH_STATUS_PROCESSING).getName());
		return mav;
	}


	/**
	 * 批量撤回
	 * @param request
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/cancelall", method = RequestMethod.POST)
	public ModelAndView cancelAll(HttpServletRequest request,@MyInject Employee employee){
		String cids=request.getParameter("cids");
		ModelAndView mav = new ModelAndView(JSONRESULT);
		String[] ids=cids.split(",");
		String error="";
		List<String> errorIds=new ArrayList<String>();
		for(int i=0;i<ids.length;i++){
			if(!StringUtils.isBlank(ids[i])){
				CustomerCashApply customerCashApply=customerService.getCashApply(Long.valueOf(ids[i]));
				if(customerCashApply!=null)
				{
					try {
						customerService.cancelCashApply(customerCashApply, employee);
						errorIds.add(ids[i]);
					} catch (Exception e) {

						error=error+ERRORID+ids[i]+"的记录撤回失败:"+e.getMessage()+"\n";
					}
				}
			}
		}
		mav.addObject("errorIds",errorIds);
		mav.addObject(ERROR,error);
		mav.addObject(STATUSID, Code.CUSTOMER_CASH_STATUS_CANCEL);
		mav.addObject(STATUS,codeService.get(Code.CUSTOMER_CASH_STATUS_CANCEL).getName());
		return mav;
	}



	/**
	 * 撤回
	 * @param request
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ModelAndView cancel(HttpServletRequest request,@MyInject Employee employee){
		String id=request.getParameter(ID);
		ModelAndView mav = new ModelAndView(JSONRESULT);
		String error="";
		if(!StringUtils.isBlank(id)){
			CustomerCashApply customerCashApply=customerService.getCashApply(Long.valueOf(id));
			if(customerCashApply!=null)
			{
				try {
					customerService.cancelCashApply(customerCashApply, employee);
				} catch (Exception e) {

					error=error+ERRORID+id+"的记录撤回失败:"+e.getMessage();
				}
			}
		}
		mav.addObject(ERROR,error);
		mav.addObject(STATUS,codeService.get(Code.CUSTOMER_CASH_STATUS_CANCEL).getName());
		return mav;
	}

	/**
	 * 批量回填
	 * 
	 * @param file
	 * @param pagination
	 * @return
	 * @throws CustomerAccountException 
	 */
	@RequestMapping(value = "/successcashall", method = RequestMethod.POST)
	public ModelAndView updateupdateSuccessCashApplyApplyAll(
			@RequestParam("fileName") MultipartFile file,@MyInject Employee employee){
		// 文件获取流，流获取workbook,
		Workbook excel = null;
		String error="";
		try {
			excel = Workbook.getWorkbook(file.getInputStream());
		} catch (Exception e1) {
			throw new RuntimeException("excel无法读取");
		}
		// 获取第一页
		Sheet sheet = excel.getSheet(ZERO);
		for (int i = ONE; i < sheet.getRows(); i++) {
			String cid = sheet.getCell(ZERO, i).getContents().trim();
			String tradeNo = sheet.getCell(ONE, i).getContents().trim();
			String mark = sheet.getCell(TWO, i).getContents().trim();
			if (!StringUtils.isBlank(cid)
					|| !StringUtils.isBlank(tradeNo)
					|| !StringUtils.isBlank(mark)) {
				if(!StringUtils.isBlank(cid)){
					CustomerCashApply customerCashApply=customerService.getCashApply(Long.valueOf(cid));
					if(customerCashApply!=null){
						try {
							customerService.updateSuccessCashApply(customerCashApply, employee, tradeNo, mark);
						} catch (Exception e) {
							error=error+ERRORID+cid+"的记录回填失败："+e.getMessage()+"\n";
						}
					}
				}else{
					continue;
				}

			} else {
				break;
			}
		}
		ModelAndView mav = new ModelAndView("/customer/cresult");
		mav.addObject(ERROR, error);
		return mav;
	}

	/** 
	 * 回填
	 * @param id
	 * @param mark
	 * @param tradeNo
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/successcash", method = RequestMethod.POST)
	public ModelAndView updateSuccessCashApply(
			@RequestParam(ID) String id,
			@RequestParam("mark") String mark,
			@RequestParam("tradeNo") String tradeNo,
			@MyInject Employee employee){
		String error="";
		CustomerCashApply customerCashApply=null;
		if(!StringUtils.isBlank(id)){
			customerCashApply=customerService.getCashApply(Long.valueOf(id));
			if(customerCashApply!=null){
				try {
					customerService.updateSuccessCashApply(customerCashApply, employee, tradeNo, mark);
				} catch (Exception e) {
					error=error+ERRORID+id+"的记录回填失败："+e.getMessage();
				}
			}
		} 
		ModelAndView mav = new ModelAndView(JSONRESULT);
		mav.addObject(STATUS, codeService.get(Code.CUSTOMER_CASH_STATUS_SUCCESS).getName());
		mav.addObject(STATUSID, Code.CUSTOMER_CASH_STATUS_SUCCESS);
		mav.addObject(ERROR, error);
		return mav;
	}

	/**
	 * 跳转详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/returninfo/{id}", method=RequestMethod.GET)
	public ModelAndView balanceReturnInfo(@PathVariable(ID) Long id){
		ModelAndView mav=new ModelAndView("/customer/balance_return_info");
		CustomerCashApply customerCashApply=customerService.getCashApply(id);
		mav.addObject("customerCashApply", customerCashApply);
		return mav;
	}

	/**
	 * 用户状态更改日志
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/statuslog", method=RequestMethod.GET)
	public ModelAndView statusLog(@PathVariable(ID) Long id){
		ModelAndView mav=new ModelAndView("/customer/statuslog_Failure");
		mav.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_PARAMETER_ERROR);
		Customer customer = customerService.get(id);
		if(customer!=null){
			List<UserStatusLog> logs =  customerService.findStatusLog(customer,MagicNumber.TEN);
			if(logs != null && !logs.isEmpty()){
				mav=new ModelAndView("/customer/statuslog");
				mav.addObject(ControllerConstant.RESULT_KEY,ControllerConstant.RESULT_SUCCESS);
				mav.addObject("logs", logs);
			}else{
				mav.addObject(ControllerConstant.MESSAGE_KEY, "没有日志记录!");
			}
		}else{
			mav.addObject(ControllerConstant.MESSAGE_KEY, "没有找到对应的用户!");
		}
		return mav;
	}
	
	/**
	 * 验证用户是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/customerIsExistence", method = RequestMethod.POST)
	public ModelAndView customerIsExistence(@RequestParam("name") String name ,Long channel) {
		ModelAndView mav = new ModelAndView("/customer/result");
		Customer customer = null;
		//判断是否加盟店渠道
		if(Channel.FRANCHISEE.equals(channel)){
			customer = customerService.getByName(new Code(Code.USER_SOURCE_TAOBAO),name);
		}else{
			 customer = customerService.getByName(name);
		}
		mav.addObject("customer", customer);
		return mav;
	}

}
