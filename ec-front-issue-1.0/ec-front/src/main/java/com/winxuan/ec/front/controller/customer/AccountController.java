package com.winxuan.ec.front.controller.customer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.impl.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.CustomerCashApplyException;
import com.winxuan.ec.exception.CustomerExtendIsNullException;
import com.winxuan.ec.front.model.ip.json.JsonStatus;
import com.winxuan.ec.front.service.cache.CacheService;
import com.winxuan.ec.front.service.cache.CacheServiceImpl;
import com.winxuan.ec.front.service.ip.IpService;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccountDetail;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.customer.CustomerPayee;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerExtendService;
import com.winxuan.ec.service.customer.CustomerPayPasswordService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.Constant;
import com.winxuan.framework.cache.CacheManager;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.sms.SMSMessage;
import com.winxuan.framework.sms.SMSService;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.security.MD5Custom;
import com.winxuan.framework.util.security.MD5Utils;
import com.winxuan.framework.util.web.CookieUtils;

/*
 * @(#)CustomerController.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */

/**
 * 用户
 * @author  HideHai
 * @version 1.0,2011-7-15
 */
@Controller
@RequestMapping(value="/customer/advanceaccount")
public class AccountController {
	
	private static final Log LOG = LogFactory.getLog(AccountController.class);
	
	private static final String STATUS ="status";
	
	private static final String ACCOUNT="account";
	
	private static final String RESULT_ERROR = "/customer/advanceaccount/error";

	private static final String REFUND_ERROR = "/customer/advanceaccount/error";
	
	private static final String REFUND_SUCCESS = "/customer/advanceaccount/refund_success";
	
	private static final String CASHAPPLYFORM_KEY = "cashapplyform_key";
	
	private static final String CUSTOMER_EXTEND = "customerExtend";
	
	private static final Integer CACHE_WEEK = 86400 * 7;
	
	private static final String SEND_LIMIT_KEY = "front_send_limit_key";
	
	private static final String SEND_PAY_LIMIT_KEY = "front_send_pay_limit_key";
	
	private static final Integer CACHE_SEC = 60;
	
	private static final String SMS_TEXT = "本次验证码:";
	
	private static final String SMS_SIGNATURE = ". 【文轩网】";
	
	private static final String VERIFY_CODE_COOKIE_NAME = "checkCode";
	
	private static final String VERIFY_CODE_COOKIE_SPLIT = "__";
	
	private static final long VERIFY_CODE_COOKIE_EXPIRE_TIME = 10 * 60 * 1000;
	
	
	@Autowired
	private CustomerService  customerService;
	
	@Autowired
	private CustomerExtendService customerExtendService;
	
	@Autowired
	private CodeService  codeService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private CustomerPayPasswordService customerPayPasswordService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private IpService ipService;
	
	

	
	@RequestMapping(value="/validatePayPassword", method=RequestMethod.POST)
	public ModelAndView validatePayPassword(@MyInject Customer customer , String payPassword){
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/validatePaypassword");
		int ret = MagicNumber.ZERO; 
		String msg = null;
		
		if(cacheService.payPasswordErrLimit(customer.getId().toString())){
			ret = MagicNumber.TWO;//账号冻结
			msg = CacheServiceImpl.ACCOUNT_FROZEN_MESSAGE;
		}
		else{
			try {
				if(customerService.validatePayPassword(customer, payPassword)){
					ret = MagicNumber.ONE;
					msg = "验证成功";
					cacheService.updateCache(customer.getId().toString(), true);
				}
				else{
					ret = MagicNumber.ZERO;//验证密码失败
					cacheService.updateCache(customer.getId().toString(), false);
					int restTimes = cacheService.getRestPaypasswordErrTimes(customer.getId().toString());
					if(restTimes == MagicNumber.ZERO){
						msg = CacheServiceImpl.ACCOUNT_FROZEN_MESSAGE;
					}
					else{
						msg = "支付密码错误,再连续输入错误"+ restTimes +"次将暂时冻结您的暂存款";
					}
				}
			} catch (CustomerExtendIsNullException e) {
				ret = -1; //未设置支付密码
				msg = "您还未设置支付密码，请先设置";
			}
		}
		
		modelAndView.addObject(STATUS, ret);
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, msg);
		return modelAndView;
	}
	
	/**
	 * 获取暂存款
	 * @param customer
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer,@MyInject Pagination pagination){
		pagination.setPageSize(Integer.parseInt("5"));
		Map<String ,Object> detailMap = new HashMap<String ,Object>();
		detailMap.put(ACCOUNT, customer.getAccount());
		List<CustomerAccountDetail> details = customerService.findAccountDetail(detailMap, pagination);	
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/list");
		modelAndView.addObject(ACCOUNT, customer.getAccount());
		modelAndView.addObject("details", details);
		modelAndView.addObject("pagination", pagination);
		boolean hasPayPassword = customerPayPasswordService.hasPayPassword(customer);
		modelAndView.addObject("hasPayPassword", hasPayPassword);
		return modelAndView;
	}
	@RequestMapping(value="/refundlist",method=RequestMethod.GET)
	public ModelAndView refundList(@MyInject Customer customer,@MyInject Pagination pagination){
		pagination.setPageSize(Integer.parseInt("5"));
		Map<String ,Object> cashApplyMap = new HashMap<String ,Object>();
		cashApplyMap.put("customer", customer);
		List<CustomerCashApply> cashApplys = customerService.findCustomerCashApply(cashApplyMap, pagination);
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/refundlist");
		modelAndView.addObject(ACCOUNT, customer.getAccount());
		modelAndView.addObject("bankType", codeService.get(Code.BANK).getAvailableChildren());
		modelAndView.addObject("cashApplys", cashApplys);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	@RequestMapping(value="/refund",method=RequestMethod.GET)
	public ModelAndView refund(@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/refund");
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("bankType", codeService.get(Code.BANK).getAvailableChildren());
		return modelAndView;
	}

	
	@RequestMapping(value="/refund/{id}/cancel",method=RequestMethod.GET)
	public ModelAndView cancel(@MyInject Customer customer,@PathVariable("id") Long id){
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/cancel");
		CustomerCashApply cashApply =customerService.getCashApplyByCustomerAndId(customer, id);
		if(cashApply == null){
			modelAndView = new ModelAndView(RESULT_ERROR);
			modelAndView.addObject(STATUS, false);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"不存在该申请退款");
			return modelAndView;
		}
		try {
			customerService.cancelCashApplyByCustomer(cashApply,customer);
		} catch (Exception e) {
			 LOG.info(e.getMessage());
			 modelAndView = new ModelAndView(REFUND_ERROR);
			 modelAndView.addObject(STATUS, false);
			 modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			 return modelAndView;
		}
		Code code = codeService.get(cashApply.getStatus().getId());
		modelAndView.addObject(STATUS,true);
		modelAndView.addObject("cashApplyStatus",code);
		modelAndView.addObject(ACCOUNT,customer.getAccount());
		return modelAndView;
	}
	
	@RequestMapping(value="/refund/{id}/modify",method=RequestMethod.POST)
	public ModelAndView modifyRefund(@MyInject Customer customer,
			CashApplyForm cashApplyForm,@PathVariable("id") Long id)throws CustomerCashApplyException{
		ModelAndView modelAndView = new ModelAndView("redirect:/customer/advanceaccount/refundlist");
		CustomerPayee customerPayee = customer.getPayee()== null?new CustomerPayee():customer.getPayee();	
		CustomerCashApply customerCashApply = customerService.getCashApplyByCustomerAndId(customer, id);
		if(customerCashApply == null){
			throw new CustomerCashApplyException(customerCashApply, "不存在用户id为"
					+ customer.getId() + "申请id" + id);
		}	
		cashApplyForm.generateCashApply(customerCashApply,customerPayee,areaService);
		customerService.modifyCashApply(customerCashApply, customer);
		customerService.saveOrUpdateCustomerPayee(customerPayee);	
		return modelAndView;
	}
	
	@RequestMapping(value="/applyRefund",method=RequestMethod.POST)
	public ModelAndView applyRefund(@MyInject Customer customer,
			CashApplyForm cashApplyForm,
			HttpServletRequest httpServletRequest) throws CustomerAccountException,CustomerCashApplyException{
		
		ModelAndView modelAndView = new ModelAndView("/customer/advanceaccount/preconfirm");
		/*
		 * 当前登陆用户是否设置过支付密码
		 */
		CustomerExtend customerExtend = customer.getCustomerExtend();
		if(customerExtend != null && StringUtils.isNotBlank(customerExtend.getPayPassword())){
			modelAndView.addObject(CUSTOMER_EXTEND, customer.getCustomerExtend());
		}
		
		int payEmailStatus = 0;
		if(!ipService.isChinaIp(httpServletRequest.getRemoteAddr())){
			
			EmailValidator validator = new EmailValidator();
			String email = customer.getPayEmail();
			if(StringUtils.isBlank(email)
					|| !validator.isValid(email, null)){
				payEmailStatus = MagicNumber.NEGATIVE_ONE;
//				emailMsg = "您还未设置邮箱，请先设置邮箱";
			}
			else{
				payEmailStatus = MagicNumber.ONE;
//				emailMsg = "您已经设置过支付邮箱";
			}
		}
		int payMobileStatus = 0; //未设置手机
		if(customerExtend != null 
				&& !StringUtils.isBlank(customerExtend.getPayMobile())){
			payMobileStatus = MagicNumber.ONE;
		}
		
		modelAndView.addObject("payMobileStatus", payMobileStatus);
		modelAndView.addObject("payEmailStatus", payEmailStatus);
		
		/*
		 * cacheKey生成策略:
		 */
		cacheManager.put(CASHAPPLYFORM_KEY + customer.getId(), cashApplyForm, CACHE_WEEK);//缓存一个星期
		return modelAndView;
	}
	
	/*
	 * 获取提现的校验码
	 * 限制30s发一次
	 */
	@RequestMapping(value="/sendPayCheckCode", method=RequestMethod.GET)
	public ModelAndView sendPayCheckCode(@MyInject Customer customer){
		ModelAndView mav = new ModelAndView(REFUND_ERROR);
		/*
  		 * 限制30s发一次
  		 */
  		Integer cValue = (Integer)cacheManager.get(SEND_PAY_LIMIT_KEY + customer.getId());
  		if(cValue != null){
  			mav.setViewName(REFUND_ERROR);
  			mav.addObject(STATUS, false);
  			mav.addObject(ControllerConstant.MESSAGE_KEY, "60秒后才能重新获取!");
  			return mav;
  		}else{
  			cacheManager.put(SEND_PAY_LIMIT_KEY + customer.getId(), new Integer(MagicNumber.NINE), CACHE_SEC);
  		}
		
		CustomerExtend customerExtend = customer.getCustomerExtend();
		if(customerExtend != null){
			String payMobile = customerExtend.getPayMobile();
			if(StringUtils.isNotBlank(payMobile)){
				String payCheckCode = getRandomCode();
				SMSMessage smsMessage = new SMSMessage();
				smsMessage.setPhones(payMobile);
				smsMessage.setText(SMS_TEXT + payCheckCode + SMS_SIGNATURE);
				smsMessage.setOperator(com.winxuan.framework.sms.Constant.OPERATORS_WODONG);
				smsService.sendMessage(smsMessage);
				LOG.info("发送短信:" + SMS_TEXT + payCheckCode + SMS_SIGNATURE + " TO " + payMobile);
				Cookie cookie = CookieUtils.getCookie("payCheckCode");
				if(cookie != null){
					cookie.setValue(MD5Custom.encrypt(payCheckCode));
				}else{
					cookie = new Cookie("payCheckCode", MD5Custom.encrypt(payCheckCode));
				}
				/*
				 * 注意path 在写之前设置为根,默认是当前路径
				 */
				cookie.setPath("/");
				CookieUtils.writeCookie(cookie);
				
				mav.addObject(STATUS, true);
				mav.setViewName(REFUND_SUCCESS);
			}else{
				//no payMobile
				mav.addObject(ControllerConstant.MESSAGE_KEY, "您尚未设置支付手机");
				mav.addObject(STATUS, false);
			}
		}else{
			//no customerExtend
			mav.addObject(ControllerConstant.MESSAGE_KEY, "您尚未设置支付密码,请先设置支付密码");
			mav.addObject(STATUS, false);
		}
		return mav;
	}
	
	/*
	 * 验证提现校验码和支付密码，正确则提现申请成功
	 */
	@RequestMapping(value="/applyRefundConfirm", method=RequestMethod.POST)
	public ModelAndView applyRefundConfirm(@MyInject Customer customer,
			@RequestParam(value="payCheckCode", required=false)String payCheckCode,
			@CookieValue(value = VERIFY_CODE_COOKIE_NAME , required =false) Cookie verifyCodeCookie,
			@RequestParam(value="payPassword")String payPassword) throws CustomerCashApplyException, CustomerAccountException{
		ModelAndView mav = new ModelAndView(REFUND_ERROR);
		
		/*
		 * 用户是否已被冻结
		 */
		if(cacheService.payPasswordErrLimit(customer.getId().toString())){
			mav.addObject(ControllerConstant.MESSAGE_KEY, 
					CacheServiceImpl.ACCOUNT_FROZEN_MESSAGE);
			return mav;
		}
		
		CashApplyForm cashApplyForm = (CashApplyForm)cacheManager.get(CASHAPPLYFORM_KEY + customer.getId());
		
		Cookie cookie = CookieUtils.getCookie("payCheckCode");
		if(cookie == null && verifyCodeCookie == null){
			mav.addObject(ControllerConstant.MESSAGE_KEY, "请先获取随机码或当前随机码已过期，请重新获取");
			mav.addObject(STATUS, MagicNumber.TWO);
		}else{
			String cookieRandomCode = (cookie == null ? null : cookie.getValue());
			if((!StringUtils.isBlank(cookieRandomCode) && MD5Custom.encrypt(payCheckCode).equals(cookieRandomCode))
					|| verifyCode(customer, verifyCodeCookie, payCheckCode).getStatus() == MagicNumber.ONE){
					CustomerExtend customerExtend = customer.getCustomerExtend();
					if(customerExtend != null){
						if(customerExtend.getPayPassword().equals(MD5Custom.encrypt(payPassword))){
							cacheService.updateCache(customer.getId().toString(), true);
							CustomerPayee customerPayee = customer.getPayee() == null?new CustomerPayee():customer.getPayee();	
							CustomerCashApply customerCashApply = null ;
//							CashApplyForm cashApplyForm = (CashApplyForm)cacheManager.get(CASHAPPLYFORM_KEY + customer.getId());
							customerCashApply = cashApplyForm.getCustomerCashApply(customer,areaService,customerPayee);
							customerService.save(customerCashApply,customer);
							customerService.saveOrUpdateCustomerPayee(customerPayee);
							mav.addObject(STATUS, MagicNumber.ZERO);
							mav.setViewName(REFUND_SUCCESS);
						}else{
							//error:支付密码不正确
							mav.addObject(ControllerConstant.MESSAGE_KEY, "支付密码不正确");
							if(cacheService.payPasswordErrLimit(customer.getId().toString())){
								mav.addObject(ControllerConstant.MESSAGE_KEY, 
										CacheServiceImpl.ACCOUNT_FROZEN_MESSAGE);
							}
							else{
								cacheService.updateCache(customer.getId().toString(), false);
							}
							
							mav.addObject(STATUS, MagicNumber.ONE);
						}
					}else{
						//error:用户的支付记录不存在
						mav.addObject(ControllerConstant.MESSAGE_KEY, "您尚未设置支付密码,请先设置支付密码");
						mav.addObject(STATUS, MagicNumber.ONE);
					}
			}else{
				mav.addObject(ControllerConstant.MESSAGE_KEY, "验证码输入错误，请重新获取");
				mav.addObject(STATUS, MagicNumber.TWO);
			}
		}
		return mav;
	}
	
	
	@RequestMapping(value="/getCustomerMobile", method=RequestMethod.GET)
	public ModelAndView getCustomerMobile(@MyInject Customer customer){
		ModelAndView mav = new ModelAndView(REFUND_SUCCESS);
		String payMobile = customerPayPasswordService.getCustomerPayMobile(customer);
		mav.addObject("payMobile", payMobile);
		mav.addObject(STATUS, true);
		return mav;
	}
	
	
	/*
	 * 设置支付密码时发送校验码
	 * 设置30s发一次
	 */
	@RequestMapping(value="/sendCheckCode", method=RequestMethod.GET)
	public ModelAndView sendCheckCode(@MyInject Customer customer,
			@RequestParam(value="payMobile", required=false)String payMobile){
  		ModelAndView mav = new ModelAndView(REFUND_SUCCESS);
  		
  		if(StringUtils.isNotBlank(payMobile)){
  			if(!verifyMobileRegex(payMobile)){
  				mav.setViewName(REFUND_ERROR);
  				mav.addObject(STATUS, false);
  				mav.addObject(ControllerConstant.MESSAGE_KEY, "请输入正确格式的电话号码!");
  				return mav;
  			}
  			if(!verifyMobileUsed(payMobile)){
  				mav.setViewName(REFUND_ERROR);
  				mav.addObject(STATUS, false);
  				mav.addObject(ControllerConstant.MESSAGE_KEY, "该号码已被其他用户绑定!");
  				return mav;
  			}
  		}
  		/*
  		 * 限制30s发一次
  		 */
  		Integer cValue = (Integer)cacheManager.get(SEND_LIMIT_KEY + customer.getId());
  		if(cValue != null){
  			mav.setViewName(REFUND_ERROR);
  			mav.addObject(STATUS, false);
  			mav.addObject(ControllerConstant.MESSAGE_KEY, "60秒后才能重新获取!");
  			return mav;
  		}else{
  			cacheManager.put(SEND_LIMIT_KEY + customer.getId(), new Integer(MagicNumber.EIGHT), CACHE_SEC);
  		}
		CustomerExtend customerExtend = customer.getCustomerExtend();
		String persistentPayMobile = "";
		if(customerExtend != null){
			persistentPayMobile = customerExtend.getPayMobile();
			if(!StringUtils.isBlank(persistentPayMobile)){
				payMobile = persistentPayMobile;
			}
		}
		String checkCode = getRandomCode();
		SMSMessage msg = new SMSMessage();
		msg.setPhones(payMobile);
		msg.setText(SMS_TEXT + checkCode + SMS_SIGNATURE);
		msg.setOperator(com.winxuan.framework.sms.Constant.OPERATORS_WODONG);
		smsService.sendMessage(msg);
		LOG.info("发送短信:" + checkCode + SMS_SIGNATURE + " TO " + payMobile);
		
		//判断是否为重复发送，如果是，则重写cookie
		Cookie cookie = CookieUtils.getCookie(VERIFY_CODE_COOKIE_NAME);
		if(cookie != null){
			cookie.setValue(MD5Custom.encrypt(checkCode));
		}else{
			cookie = new Cookie(VERIFY_CODE_COOKIE_NAME, MD5Custom.encrypt(checkCode));
		}
		cookie.setPath("/");
		CookieUtils.writeCookie(cookie);
		
		mav.addObject(STATUS, true);
		return mav;
	}
	
	@RequestMapping(value="/verifyCheckCode", method=RequestMethod.GET)
	public ModelAndView verifyCheckCode(@MyInject Customer customer,
			@RequestParam(value=VERIFY_CODE_COOKIE_NAME, required=true)String checkCode,
			@RequestParam(value="payMobile", required=false)String payMobile){
		ModelAndView mav = new ModelAndView(REFUND_ERROR);
		if(verifyMobileRegex(payMobile)){
			mav.addObject(STATUS, false);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "请输入正确格式的电话号码!");
		}
		
		if(verifyMobileUsed(payMobile)){
			mav.addObject(STATUS, false);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "该号码已被其他用户绑定!");
		}
		
		Cookie cookie = CookieUtils.getCookie(VERIFY_CODE_COOKIE_NAME);
		if(cookie == null){
			mav.addObject(STATUS, false);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "校验码已过期,请重新获取!");
		}else{
			String cookieCheckCode = cookie.getValue();
			String md5CheckCode = MD5Custom.encrypt(checkCode); 
			if(md5CheckCode.equals(cookieCheckCode)){
				CustomerExtend customerExtend = customer.getCustomerExtend();
				if(customerExtend == null){
					customerExtend = new CustomerExtend();
					customerExtend.setCustomer(customer);
					customerExtend.setPayMobile(payMobile);
					customer.setCustomerExtend(customerExtend);
					customerService.saveOrUpdateCustomerExtend(customerExtend);
				}
				else if(StringUtils.isBlank(customerExtend.getPayMobile())){
					customerExtend.setPayMobile(payMobile);
					customerService.saveOrUpdateCustomerExtend(customerExtend);
				}
				cookie.setMaxAge(0);
				CookieUtils.writeCookie(cookie);
				mav.setViewName(REFUND_SUCCESS);
				mav.addObject(STATUS, true);
			}else{
				mav.addObject(STATUS, false);
				mav.addObject(ControllerConstant.MESSAGE_KEY, "校验码不正确,请重新获取!");
			}
		}
		return mav;
	}
	
	/*
	 * 修改支付密码
	 */
	@RequestMapping(value="/updatePayPassword", method=RequestMethod.POST)
	public ModelAndView updatePayPassword(@MyInject Customer customer,
			@RequestParam(value="payPassword",required=true)String payPassword,
			@RequestParam(value="rePayPassword",required=true)String rePayPassword) throws Exception{
		ModelAndView mav = new ModelAndView(REFUND_ERROR);
		
		if(payPassword.equals(rePayPassword)){
			if(!verifyPayPasswordRegex(payPassword)){
				mav.addObject(ControllerConstant.MESSAGE_KEY, "密码长度应为6到16位,且应只包含字母、数字及下划线,请重新输入!");
				mav.addObject(STATUS, false);
				return mav;
			}
			CustomerExtend customerExtend = customer.getCustomerExtend();
			if(customerExtend == null){
				customerExtend = new CustomerExtend();
			}
			customerExtend.setCustomer(customer);
			customerExtend.setPayPassword(MD5Custom.encrypt(payPassword));
			customer.setCustomerExtend(customerExtend);
			customerService.saveOrUpdateCustomerExtend(customerExtend);
			/*
			 * 用户重新设置支付密码后解冻
			 */
			cacheService.updateCache(customer.getId().toString(), true);
			mav.addObject(STATUS, true);
			mav.setViewName(REFUND_SUCCESS);
		}else{
			mav.addObject(ControllerConstant.MESSAGE_KEY, "两次输入的密码不一致,请重新输入!");
			mav.addObject(STATUS, false);
		}
		return mav;
	}
	
	@RequestMapping(value="/refund_ok", method=RequestMethod.GET)
	public ModelAndView refundOk(){
		ModelAndView mav = new ModelAndView("/customer/advanceaccount/refund_ok");
		return mav;
	}
	

	
	/*
	 * 生成六位随机码
	 */
	private String getRandomCode(){
		return RandomCodeUtils.create(RandomCodeUtils.MODE_NUMBER, MagicNumber.SIX);
	}
	
	private boolean verifyMobileRegex(String mobile){
		if(mobile != null && !"".equals(mobile)){
			Pattern p = Pattern.compile("^1\\d{10}$");     
			Matcher m = p.matcher(mobile);     
			return m.matches(); 
		}
		return false;
	}
	
	private boolean verifyMobileUsed(String mobile){
		if(mobile != null && !"".equals(mobile)){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("payMobile", mobile);
			List<CustomerExtend> customerExtentSet = customerExtendService.find(parameters);
			if(CollectionUtils.isNotEmpty(customerExtentSet) && customerExtentSet.size() == MagicNumber.ONE){
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean verifyPayPasswordRegex(String payPassword){
		if(payPassword != null && !"".equals(payPassword)){
			Pattern p = Pattern.compile("^\\w{4,12}$");
			Matcher m = p.matcher(payPassword);
			return m.matches();
		}
		return false;
	}
	
	@RequestMapping(value="/sendVerifyCodeToEmail", method=RequestMethod.GET)
	public ModelAndView sendVerifyCodeToEmail(@MyInject Customer customer,
			HttpServletRequest request,
			@RequestParam(value = "payEmail", required=false) String payEmail){
		ModelAndView mav = new ModelAndView("/customer/advanceaccount/sendVerifyCodeToEmail");
		Map<String, Object> mailMode = new HashMap<String, Object>();
		int status = MagicNumber.ONE;
		String message = "发送验证码成功";
		
		String exsitPayEmail = customer.getPayEmail();
		if(!StringUtils.isBlank(exsitPayEmail)){
			payEmail = exsitPayEmail.trim();
		}
		
		EmailValidator emailValidator = new EmailValidator();
		if(StringUtils.isBlank(payEmail) 
				|| !emailValidator.isValid(payEmail, null)){
			status = MagicNumber.NEGATIVE_ONE;
			message = "邮箱格式不正确 ";
		}
		else if(ipService.isChinaIp(request.getRemoteAddr())){
			status = MagicNumber.NEGATIVE_TWO;
			message = "国内用户请使用手机获取验证码";
		}
		else if(StringUtils.isBlank(exsitPayEmail)){
			if(customerExtendService.payEmailExist(payEmail)){
				status = MagicNumber.NEGATIVE_THREE;
				message = "该邮箱已被使用，请更换其他邮箱 ";
			}
		}
		if(!StringUtils.isBlank(payEmail) && status == MagicNumber.ONE){
			String verifyCode = getRandomCode();
			generateVerifyCodeCookie(customer, verifyCode);
			
			String subject = "文轩网:验证码";
			mailMode.put("customer", customer);
			mailMode.put("verifyCode", verifyCode);
			mailService.sendMail(payEmail, subject, Constant.MAIL_VERIFY_CODE, mailMode);
		}
		
		mav.addObject(STATUS, status);
		mav.addObject(ControllerConstant.MESSAGE_KEY, message);
		return mav;
	}
	
	private void generateVerifyCodeCookie(Customer customer, String verifyCode){
		String key = customer.getId() + VERIFY_CODE_COOKIE_SPLIT
				+ System.currentTimeMillis() + VERIFY_CODE_COOKIE_SPLIT
				+ verifyCode;
		
		String md5Key = MD5Utils.encryptWithKey(key);
		Cookie cookie = new Cookie(VERIFY_CODE_COOKIE_NAME, customer.getId() + VERIFY_CODE_COOKIE_SPLIT
				+ System.currentTimeMillis() + VERIFY_CODE_COOKIE_SPLIT + md5Key);
		cookie.setPath("/");
		CookieUtils.writeCookie(cookie);
	}
	/**
	 * 验证邮箱验证码
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/verifyEmailCode", method=RequestMethod.GET)
	public ModelAndView verifyEmailCode(@MyInject Customer customer,
			@CookieValue(value = VERIFY_CODE_COOKIE_NAME) Cookie verifyCodeCookie,
			@RequestParam(value = VERIFY_CODE_COOKIE_NAME) String verifyCode,
			@RequestParam(value = "payEmail", required = false) String payEmail){
		ModelAndView mav = new ModelAndView("/customer/advanceaccount/verifyEmailCode");
		JsonStatus jsonStatus = new JsonStatus();

		String exsitPayEmail = customer.getPayEmail();
		if(!StringUtils.isBlank(exsitPayEmail)){
			payEmail = exsitPayEmail.trim();
		}
		
		EmailValidator emailValidator = new EmailValidator();
		if(StringUtils.isBlank(payEmail)
				|| !emailValidator.isValid(payEmail, null)){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_SIX);
			jsonStatus.setMessage("邮箱格式不正确 ");
		}
		else{
			jsonStatus = verifyCode(customer, verifyCodeCookie, verifyCode);
		}
		
		if(StringUtils.isBlank(exsitPayEmail) && jsonStatus.getStatus() == MagicNumber.ONE){
			if(customerExtendService.payEmailExist(payEmail)){
				jsonStatus.setStatus(MagicNumber.NEGATIVE_SEVEN);
				jsonStatus.setMessage("该邮箱已被使用，请更换其他邮箱 ");
			}
			else{
				CustomerExtend customerExtend = customer.getCustomerExtend();
				if(customerExtend == null){
					customerExtend = new CustomerExtend();
					customerExtend.setCustomer(customer);
					customer.setCustomerExtend(customerExtend);
				}
				customerExtend.setPayEmail(payEmail.trim());
				customerService.saveOrUpdateCustomerExtend(customerExtend);
			}
		}
		
		mav.addObject(STATUS, jsonStatus.getStatus());
		mav.addObject(ControllerConstant.MESSAGE_KEY, jsonStatus.getMessage());
		return mav;
	}
	
	private JsonStatus verifyCode(Customer customer, Cookie verifyCodeCookie, String verifyCode){
		JsonStatus jsonStatus = new JsonStatus();
		if(verifyCodeCookie == null 
				|| StringUtils.isBlank(verifyCodeCookie.getValue())){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_ONE);
			jsonStatus.setMessage("验证码错误：cookie为空");
			return jsonStatus;
		}
		
		String cookieStr = verifyCodeCookie.getValue();
		String[] values = cookieStr.split(VERIFY_CODE_COOKIE_SPLIT);
		if(values.length != MagicNumber.THREE){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_TWO);
			jsonStatus.setMessage("验证码错误：" + cookieStr);
			return jsonStatus;
		}
		
		long customerId = Long.parseLong(values[0]);
		long time = Long.parseLong(values[1]);
		if(!customer.getId().equals(customerId)){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_THREE);
			jsonStatus.setMessage("验证码错误：用户ID不符");
			return jsonStatus;
		}
		
		if(System.currentTimeMillis() - time > VERIFY_CODE_COOKIE_EXPIRE_TIME){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_FOUR);
			jsonStatus.setMessage("验证码已过期,请重新获取");
			return jsonStatus;
		}
		
		String key = customer.getId() + VERIFY_CODE_COOKIE_SPLIT
				+ time + VERIFY_CODE_COOKIE_SPLIT
				+ verifyCode;
		String md5Key = MD5Utils.encryptWithKey(key);
		if(!md5Key.equals(values[2])){
			jsonStatus.setStatus(MagicNumber.NEGATIVE_FIVE);
			jsonStatus.setMessage("验证码错误");
			return jsonStatus;
		}
		
		jsonStatus.setStatus(1);
		jsonStatus.setMessage("验证成功");
		return jsonStatus;
	}
}

