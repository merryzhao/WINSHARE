/*
 * @(#)CustomerServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.customer;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.CodeDao;
import com.winxuan.ec.dao.CustomerAccountDao;
import com.winxuan.ec.dao.CustomerAddressDao;
import com.winxuan.ec.dao.CustomerBoughtDao;
import com.winxuan.ec.dao.CustomerCashApplyDao;
import com.winxuan.ec.dao.CustomerCommentDao;
import com.winxuan.ec.dao.CustomerDao;
import com.winxuan.ec.dao.CustomerDiggingDao;
import com.winxuan.ec.dao.CustomerFavoriteDao;
import com.winxuan.ec.dao.CustomerFavoriteTagDao;
import com.winxuan.ec.dao.CustomerNotifyDao;
import com.winxuan.ec.dao.CustomerPayeeDao;
import com.winxuan.ec.dao.CustomerPointsDao;
import com.winxuan.ec.dao.CustomerQuestionDao;
import com.winxuan.ec.dao.CustomerVisitedDao;
import com.winxuan.ec.dao.PaymentCredentialDao;
import com.winxuan.ec.dao.ProductSalePerformanceDao;
import com.winxuan.ec.dao.UserLoginLogDao;
import com.winxuan.ec.dao.UserStatusLogDao;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.CustomerCashApplyException;
import com.winxuan.ec.exception.CustomerExtendIsNullException;
import com.winxuan.ec.exception.CustomerPointsException;
import com.winxuan.ec.exception.RegisterEmailDuplicateException;
import com.winxuan.ec.exception.RegisterNameDuplicateException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CashApplyUpdateLog;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAccountDetail;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.customer.CustomerBought;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerDigging;
import com.winxuan.ec.model.customer.CustomerExtend;
import com.winxuan.ec.model.customer.CustomerExtension;
import com.winxuan.ec.model.customer.CustomerFavorite;
import com.winxuan.ec.model.customer.CustomerFavoriteTag;
import com.winxuan.ec.model.customer.CustomerIP;
import com.winxuan.ec.model.customer.CustomerNotify;
import com.winxuan.ec.model.customer.CustomerPayee;
import com.winxuan.ec.model.customer.CustomerPoints;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.customer.CustomerQuestionReply;
import com.winxuan.ec.model.customer.CustomerThirdParty;
import com.winxuan.ec.model.customer.CustomerVisited;
import com.winxuan.ec.model.customer.verifier.AnonymousVerifier;
import com.winxuan.ec.model.customer.verifier.OpenVerifier;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.UserLockState;
import com.winxuan.ec.model.user.UserLoginLog;
import com.winxuan.ec.model.user.UserStatusLog;
import com.winxuan.ec.service.customer.points.CustomerPointsRule;
import com.winxuan.ec.service.customer.points.CustomerPointsRuleFactory;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.support.util.StringTool;
import com.winxuan.ec.util.Constant;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.StringUtils;
import com.winxuan.framework.util.ipseeker.IPSeeker;
import com.winxuan.framework.util.security.MD5Custom;
import com.winxuan.framework.util.security.MD5Utils;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;
import com.winxuan.framework.validator.AuthenticationException;
import com.winxuan.framework.validator.Principal;
import com.winxuan.framework.validator.Verifier;
import com.winxuan.framework.validator.impl.PasswordVerifier;

/**
 * 顾客服务接口实现类
 * 
 * @author liuan wumaojie
 * @version 1.0,2011-7-13
 */

@Service("customerService")
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService, Serializable {

	/**
	 * 
	 */
	public static final Integer ALL = 0;
	public static final Integer ONE_MONTH = 1;
	public static final Integer THREE_MONTH = 2;
	public static final Integer SIX_MONTH = 3;
	public static final Integer ONE_YEAR = 4;
	public static final Integer ONE_YEAR_AGO = 5;

	private static final String DEFAULT_DOMAIN = ".winxuan.com";
	private static final String DEFAULT_PATH = "/";
	private static final String HEAD_SHOW = "qqHeadShow";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7651146433616557521L;
	private static final String CUSTOMER = "customer";
	private static final short EMAIL_DURATION = 1;// 邮箱验证码激活期限

	private static final Code DEFAULT_USER_SOURCE = new Code(
			Code.USER_SOURCE_EC_FRONT);
	
	private static final Code USER_SOURCE_ANONYMITY = new Code(
			Code.USER_SOURCE_ANONYMITY);
	
	private static final Code USER_SOURCE_9YUE_FRONT = new Code(
			Code.USER_SOURCE_9YUE_FRONT);
	/**
	 * 增加QQ联合登陆用户
	 */
	private static final Code USER_SOURCE_QQ = new Code(
			Code.USER_SOURCE_QQ);

	Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@InjectDao
	private CustomerNotifyDao customerNotifyDao;

	@InjectDao
	private CodeDao codeDao;

	@InjectDao
	private CustomerDao customerDao;

	@InjectDao
	private CustomerCommentDao customerCommentDao;

	@InjectDao
	private CustomerQuestionDao customerQuestionDao;

	@InjectDao
	private CustomerAddressDao customerAddressDao;

	@InjectDao
	private CustomerAccountDao customerAccountDao;

	@InjectDao
	private PaymentCredentialDao paymentCredentialDao;

	@InjectDao
	private CustomerFavoriteDao customerFavoriteDao;

	@InjectDao
	private CustomerFavoriteTagDao customerFavoriteTagDao;

	@InjectDao
	private CustomerBoughtDao customerBoughtDao;

	@InjectDao
	private CustomerDiggingDao customerDiggingDao;

	@InjectDao
	private CustomerVisitedDao customerVisitedDao;

	@InjectDao
	private CustomerPointsDao customerPointsDao;

	@InjectDao
	private ProductSalePerformanceDao productSalePerformanceDao;

	@InjectDao
	private CustomerCashApplyDao customerCashApplyDao;

	@InjectDao
	private CustomerPayeeDao customerPayeeDao;

	@InjectDao
	private UserStatusLogDao userStatusLogDao;
	
	@InjectDao
	private UserLoginLogDao userLoginLogDao;

	@Autowired
	private MailService mailService;

	@Autowired
	private CustomerPointsRuleFactory customerPointsRuleFactory;

	@Autowired
	private CustomerLockService customerLockService;
	
	
	@Value("${subject}")
	private String subject;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer get(Long id) {
		return customerDao.get(id);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer getByName(String name) {
		Customer customer = getByName(DEFAULT_USER_SOURCE, name);
		if (customer != null){
			return customer;
		}
		else{
			return getByName(USER_SOURCE_QQ, name);
		}
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer getByName(Code source, String name) {
		return customerDao.getByName(source, name);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer getByEmail(String email) {
		return getByEmail(DEFAULT_USER_SOURCE, email);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer getByEmail(Code source, String email) {
		return customerDao.getByEmail(source, email);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Customer getByNameOrEmail(String key) {
		Customer c = getByName(key);
		if (c == null) {
			c = getByEmail(key);
		}
		return c;
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean nameIsExisted(String name) {
		boolean result = nameIsExisted(USER_SOURCE_ANONYMITY, name);
		if(!result){
			result =  nameIsExisted(DEFAULT_USER_SOURCE, name);
		}
		if(!result){
			result =  nameIsExisted(USER_SOURCE_9YUE_FRONT, name);
		}
		return result;
	}

	@Override
	public boolean nameIsExisted(Code source, String name) {
		return customerDao.nameIsExisted(source, name);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean emailIsExisted(String email) {
		boolean result = emailIsExisted(USER_SOURCE_ANONYMITY, email);
		if(!result){
			result =  emailIsExisted(DEFAULT_USER_SOURCE, email);
		}	
		if(!result){
			result =  emailIsExisted(USER_SOURCE_9YUE_FRONT, email);
		}
		return result;
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean emailIsExisted(Code source, String email) {
		return customerDao.emailIsExisted(source, email);
	}

	public void register(Customer customer)
			throws RegisterNameDuplicateException,
			RegisterEmailDuplicateException {
		if (nameIsExisted(customer.getSource(), customer.getName())) {
			throw new RegisterNameDuplicateException();
		}
		
		if (nameIsExisted(new Code(Code.USER_SOURCE_ANONYMITY), customer.getName())) {
			throw new RegisterNameDuplicateException("匿名账号已存在.");
		}
		
		if (emailIsExisted(customer.getSource(), customer.getEmail())) {
			throw new RegisterEmailDuplicateException();
		}
		
		if (emailIsExisted(new Code(Code.USER_SOURCE_ANONYMITY), customer.getEmail())) {
			throw new RegisterEmailDuplicateException();
		}
		
		Date now = new Date();
		customer.setRegisterTime(now);
		customer.setLastLoginTime(now);
		customer.setFirstOrder(true);
		CustomerAccount account = new CustomerAccount(customer);
		customer.setAccount(account);
		if (StringUtils.isNullOrEmpty(customer.getNickName())) {
			customer.setNickName(customer.getName());
			
		}
		customerDao.save(customer);
		customerAccountDao.save(account);
		UserLockState userLockState  = this.customerLockService.getUserLockState(customer);
		customer.setUserLockState(userLockState);
	}

	public Customer login(String name, String password) {
		Customer customerFromWinxuan = doLoginWinxuan(name, password);
		if(customerFromWinxuan == null){
			return doLogin9yue(name, password);
		}
		return customerFromWinxuan;
	}

	/**
	 * 第三方账号绑定后自动切换登陆（获取数据库已加密密码登陆）
	 * @param name
	 * @param password
	 * @return Customer
	 */
	private Customer doLoginWinxuan(String name, String password) {
		Customer customer = customerDao.getByNameAndPassword(new Code(
				Code.USER_SOURCE_EC_FRONT), name, password);
		//防止自动登陆密码再次加密
		password = MD5Custom.encrypt(password);
				        
		if(customer == null){
			customer = customerDao.getByNameAndPassword(new Code(
					Code.USER_SOURCE_EC_FRONT), name, password);
		}
		
		if(customer==null){
			//判断是文轩网用户
			customer = customerDao.getByEmailAndPassword(new Code(Code.USER_SOURCE_EC_FRONT), name, password);
		}		
		if (customer == null) {
			//判断是匿名用户
			customer = customerDao.getByEmailAndPassword(new Code(Code.USER_SOURCE_ANONYMITY), name, password);
		}
		if (customer != null) {
			UserLockState userLockState  = this.customerLockService.getUserLockState(customer);
			userLockState.setErrorTimes(0);
			this.customerLockService.update(userLockState);
			customer.setLastLoginTime(new Date());
			customerDao.update(customer);
		}

		return customer;
	}
	
	
	public Customer login9yue(String name, String password) {
		Customer customerFrom9yue = doLogin9yue(name, password);
		if(customerFrom9yue == null){
			return doLoginWinxuan(name, password);
		}
		return customerFrom9yue;
	}

	private Customer doLogin9yue(String name, String password) {
		password = StringTool.md5(password).toLowerCase();
		//处理9月网验证--通过name，password，用户名登录
		Customer customer = customerDao.getByNameAndPassword(new Code(
				Code.USER_SOURCE_9YUE_FRONT), name, password);
		//处理9月网验证--通过email，password。邮箱登录
		if(customer==null){
			customer = customerDao.getByEmailAndPassword(new Code(Code.USER_SOURCE_9YUE_FRONT), name, password);
		}		
		if (customer != null) {
			customer.setLastLoginTime(new Date());
			customerDao.update(customer);
		}

		return customer;
	}
	
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean validatePayPassword(Customer customer, String payPassword)
		throws CustomerExtendIsNullException{
		
		if(customer.getCustomerExtend() == null){
			throw new CustomerExtendIsNullException();
		}
		
		if(StringUtils.isNullOrEmpty(payPassword)){
			return false;
		}
		
		boolean ret = false;
		payPassword = MD5Custom.encrypt(payPassword);
		if(payPassword.equals(customer.getCustomerExtend().getPayPassword())){
			ret = true;
		}
		return ret;
	}

	public void update(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public void sendResetPasswordMail(Customer customer) {
		String subject = "文轩网：密码找回";
		String templateName = Constant.MAIL_FIND_PASSWORD;

		String email = customer.getEmail();
		long time = System.currentTimeMillis();
		String token = MD5Utils.encryptWithKey(time + email);
		String url = "https://passport.winxuan.com/resetPassword?";
		String encode = "UTF-8";
		try {
			url += "time=" + time + "&email="
					+ URLEncoder.encode(email, encode) + "&token="
					+ URLEncoder.encode(token, encode);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持UTF-8编码", e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("link", url);
		map.put(CUSTOMER, customer);

		mailService.sendMail(email, subject, templateName, map);
	}

	@Override
	public boolean resetPassword(String email, String newPassword) {
		//用户重置密码是如果通过40001和40021都能查询到数据，则重置两个账户密码
		boolean resetWinPwd = this.resetPassword(email, newPassword, DEFAULT_USER_SOURCE);
		boolean reset9yuePwd = this.resetPassword(email, newPassword, USER_SOURCE_9YUE_FRONT);
		return resetWinPwd||reset9yuePwd;
	}
	
	@Override
	public boolean resetPassword(String email, String newPassword,Code userSource) {

		Customer customer = customerDao.getByEmail(userSource, email);
		if (customer == null) {
			customer = customerDao.getByEmail(new Code(
					Code.USER_SOURCE_ANONYMITY), email);
			if (customer == null) {
				    return false;
			} 
		}
		if(Code.USER_SOURCE_9YUE_FRONT.equals(customer.getSource().getId())){
			customer.setPassword(StringTool.md5(newPassword));
		} else {
			customer.setPassword(MD5Custom.encrypt(newPassword));
		}
		customerDao.update(customer);
		this.accountSelfUnLock(customer);
		return true;
	}
	
	/**
	 * 解锁客户自己锁定的账户
	 * @param customer
	 */
	private void accountSelfUnLock(Customer customer){
		UserLockState userLockState = customerLockService.getUserLockState(customer);
		if(customer.equals(userLockState.getOperator())){
			customerLockService.accountUnLock(customer, customer);
		}
		
	}

	public void useAccount(Customer customer, BigDecimal money, Order order,
			User user, Code type) throws CustomerAccountException {
		CustomerAccount account = customer.getAccount();
		BigDecimal balance = account.getBalance();
		// 如果使用金额大于暂存款余额
		if (money.negate().compareTo(balance) == 1) {
			throw new CustomerAccountException(account, money.negate()
					+ "使用金额大于暂存款余额");
		}
		// 暂存款余额 加上 使用金额。。money充值时为正使用时为负
		balance = balance.add(money);
		// 设置暂存款余额
		account.setBalance(balance);
		account.setLastUseTime(new Date());
		CustomerAccountDetail customerAccountDetail = account.getAccountDetail(
				money, balance, type, order, user);
		account.getDetailList().add(customerAccountDetail);
		customerDao.update(customer);

	}

	public void saveAddress(CustomerAddress address) {
		customerAddressDao.save(address);
	}

	public void updateAddress(CustomerAddress address) {
		customerAddressDao.update(address);
	}

	public void updateAddressUsual(CustomerAddress address, boolean isUsual) {
		List<CustomerAddress> list = address.getCustomer().getAddressList();
		for (CustomerAddress ca : list) {
			if (ca.isUsual() && !ca.getId().equals(address.getId())) {
				ca.setUsual(false);
				customerAddressDao.update(ca);
				break;
			}
		}

		address.setUsual(isUsual);
		customerAddressDao.update(address);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Principal get(Serializable id) {
		return get(new Long(id.toString()));
	}

	private Customer createByOpenVerifier(OpenVerifier verifier)
			throws AuthenticationException {
		Customer customer = getByName(verifier.getSource(),
				verifier.getOuterId());
		if (customer == null) {
			customer = new Customer();
			customer.setSource(verifier.getSource());
			customer.setName(verifier.getOuterId());
			customer.setNickName(verifier.getNickName());
			customer.setChannel(new Channel(Channel.CHANNEL_EC));
			customer.setAvatar(verifier.getFigureURL());
			customer.setAvailable(Customer.ENABLE);
			customer.setLastLoginType(verifier.getType());
			String mail = verifier.getMail();
			customer.setEmail(StringUtils.isNullOrEmpty(mail) ? verifier
					.getOuterId() : mail);
			try {
				register(customer);
			} catch (Exception e) {
				throw new AuthenticationException(e);
			}
		} else {
			customer.setNickName(verifier.getNickName());
			customer.setLastLoginTime(new Date());
			customerDao.update(customer);
		}
		if (verifier.getType() == OpenVerifier.FANLI_LOGIN) {
			if (!StringUtils.isNullOrEmpty(verifier.getHeadShow())) {
				Cookie cookie = new Cookie(HEAD_SHOW, verifier.getHeadShow());
				cookie.setDomain(DEFAULT_DOMAIN);
				cookie.setPath(DEFAULT_PATH);
				CookieUtils.writeCookie(cookie);
			}
		}
		return customer;
	}

	/**
	 * @param anonymousVerifier
	 * @throws AuthenticationException
	 */
	private Customer createByAnonymousVerifier(
			AnonymousVerifier anonymousVerifier) throws AuthenticationException {
		String mail = anonymousVerifier.getMail();
		Code anonymCode = new Code(Code.USER_SOURCE_ANONYMITY);
		Customer customer = getByEmail(anonymCode, mail);
		if (customer == null) {
			customer = new Customer();
			customer.setSource(anonymCode);
			customer.setChannel(new Channel(Channel.CHANNEL_EC));
			customer.setName(anonymousVerifier.getMail());
			customer.setPassword(MD5Custom.encrypt(anonymousVerifier
					.getPassword()));
			customer.setEmail(anonymousVerifier.getMail());
			customer.setNickName(anonymousVerifier.getName());
			customer.setRealName(anonymousVerifier.getName());
			customer.setLastLoginType(Customer.NORMAL_LOGIN_TYPE);
			customer.setAvailable(Customer.ENABLE);
			if (!StringUtils.isNullOrEmpty(anonymousVerifier.getMobile())) {
				customer.setMobile(anonymousVerifier.getMobile());
			}
			try {
				register(customer);
				// sendAnonymousRegisterEmail(customer);
			} catch (Exception e) {
				throw new AuthenticationException(e);
			}
		} else {
			throw new AuthenticationException("邮箱" + mail + "已存在，请使用密码登录");
		}
		return customer;
	}

	public Principal authenticate(Verifier verifier)
			throws AuthenticationException {
		removeCookie();
		Principal principal = null;
		/*记录用户的登陆日志,无论登陆成功还是失败都需要记录*/
		UserLoginLog userLoginLog = new UserLoginLog();
		HttpServletRequest request = WebContext.currentRequest();
		userLoginLog.setIpAddress(request.getRemoteAddr());
		userLoginLog.setUserAgent(request.getHeader("User-Agent"));
		IPSeeker iPSeeker = IPSeeker.getInstance();
		String address = iPSeeker.getAddress(userLoginLog.getIpAddress());
		userLoginLog.setAddress(address);
		userLoginLog.setLoginTime(new Date());
		userLoginLog.setLogin(true);
		userLoginLog.setLoginType(new Code(Code.USER_LOGIN_WEBSITE));
		if (verifier instanceof PasswordVerifier) {
			PasswordVerifier verifierPass = (PasswordVerifier) verifier;
			if(Long.valueOf(1).equals(verifierPass.getType()))
			{
			principal = doLoginWinxuan(verifierPass.getUserName(),
			verifierPass.getPassword());
			}
			if(Long.valueOf(2).equals(verifierPass.getType()))
			{
			principal = doLogin9yue(verifierPass.getUserName(),
			verifierPass.getPassword());
			}
			else
			{
			principal = login(verifierPass.getUserName(),
						verifierPass.getPassword());
			}
			
			userLoginLog.setName(verifierPass.getUserName());
			
		} else if (verifier instanceof OpenVerifier) {
			OpenVerifier openVerifier = (OpenVerifier) verifier;
			principal = createByOpenVerifier(openVerifier);
			
			userLoginLog.setName(openVerifier.getOuterId());
			
		} else if (verifier instanceof AnonymousVerifier) {
			AnonymousVerifier anonymousVerifier = (AnonymousVerifier) verifier;
			principal = createByAnonymousVerifier(anonymousVerifier);
			userLoginLog.setName(principal.getName());
			
		}
		if (principal != null) {
			if (!(principal instanceof Customer)) {
				throw new AuthenticationException("登录方式不正确");
			} else {
				Customer customer = (Customer) principal;
				if (verifier instanceof OpenVerifier) {
					customer.setLastLoginType(((OpenVerifier) verifier)
							.getType());
				}
				userLoginLog.setName(customer.getEmail());
				Date now = new Date(System.currentTimeMillis());
				customer.setLastLoginTime(now);
				update(customer);
				userLoginLog.setUser(customer);
			}
		}else{
			userLoginLog.setLogin(false);
		}
		userLoginLogDao.save(userLoginLog);
		return principal;
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerAccountDetail> findAccountDetail(
			Map<String, Object> parameters, Pagination pagination) {
		return customerAccountDao.findAccountDetail(parameters, pagination,
				(short) 0);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerAccountDetail> findAccountDetail(
			CustomerAccount account, Order order) {
		return customerAccountDao.findAccountDetail(account, order);
	}

	public void chargeAccount(Customer customer, BigDecimal money,
			PaymentCredential credential) {
		try {
			useAccount(customer, money, null, credential.getOperator(),
					new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_CHARGE));
		} catch (CustomerAccountException e) {
			throw new RuntimeException(e);
		}
		paymentCredentialDao.save(credential);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerAddress getCustomerAddress(Long id) {
		return customerAddressDao.get(id);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerAddress getCustomerAddress(Long id, Customer customer) {
		return customerAddressDao.getByCustomerAndId(id, customer);
	}

	public void deleteAddress(CustomerAddress customerAddress) {
		customerAddressDao.delete(customerAddress);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long countOfNewFavorite(Customer customer) {
		return customerFavoriteDao.countOfNew(customer);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long countOfReductionFavorate(Customer customer) {
		return customerFavoriteDao.countOfReduction(customer);
	}

	@Override
	public boolean addToFavorite(Customer customer, ProductSale productSale) {
		CustomerFavorite favorite = customerFavoriteDao
				.getByCustomerAndProductSale(customer, productSale);
		if (favorite != null) {
			favorite.setAddTime(new Date());
			customerFavoriteDao.update(favorite);
			return false;
		}
		customerFavoriteDao.save(new CustomerFavorite(customer, productSale));
/*		ProductSalePerformance productSalePerformance = productSale
				.getPerformance();
		if(productSalePerformance != null){
			productSalePerformance.setTotalFavorite(productSalePerformance
					.getTotalFavorite() + 1);
			productSalePerformanceDao.saveOrupdate(productSalePerformance);	
		}*/
		CustomerPointsRule customerPointsRule = customerPointsRuleFactory
				.createCollectCustomerPointsRule(customer);
		customerPointsRule.addPoints();
		return true;
	}

	@Override
	public boolean removeFromFavorite(Customer customer, ProductSale productSale) {
		CustomerFavorite favorite = customerFavoriteDao
				.getByCustomerAndProductSale(customer, productSale);
		if (favorite == null) {
			return false;
		}
		Set<CustomerFavoriteTag> tagList = favorite.getTagList();
		if (tagList != null && !tagList.isEmpty()) {
			Set<CustomerFavoriteTag> removeTags = new HashSet<CustomerFavoriteTag>();
			for (CustomerFavoriteTag tag : tagList) {
				removeTags.add(tag);
			}
			for (CustomerFavoriteTag removeTag : removeTags) {
				favorite.removeTag(removeTag);
				customerFavoriteTagDao.update(removeTag);
			}
		}
		customerFavoriteDao.delete(favorite);
		return true;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerFavorite getFavorite(Long favoriteId) {
		return customerFavoriteDao.get(favoriteId);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerFavorite> findFavorite(Customer customer, Code sort,
			CustomerFavoriteTag tag, Boolean flag, short index,
			Pagination pagination) {
		return customerFavoriteDao.find(customer, sort, tag, flag, pagination,
				index);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerFavorite> findFavorite(Customer customer, int size) {
		return customerFavoriteDao.find(customer, size);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerFavoriteTag getFavoriteTag(Long tagId) {
		return customerFavoriteTagDao.get(tagId);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerFavoriteTag getFavoriteTagByTagName(Customer customer,
			String tagName) {
		return customerFavoriteTagDao.getByCustomerAndName(customer, tagName);
	}

	@Override
	public boolean updateFavoriteTag(Customer customer, Long tagId,
			String tagName) {
		CustomerFavoriteTag tag = customerFavoriteTagDao.getByCustomerAndId(
				customer, tagId);
		if (tag == null) {
			return false;
		}
		if (customerFavoriteTagDao.getByCustomerAndName(customer, tagName) != null) {
			return false;
		}
		tag.setTagName(tagName);
		customerFavoriteTagDao.update(tag);
		return true;
	}

	@Override
	public boolean deleteFavoriteTag(Customer customer, Long tagId) {
		CustomerFavoriteTag tag = customerFavoriteTagDao.getByCustomerAndId(
				customer, tagId);
		if (tag == null) {
			return false;
		}
		customerFavoriteTagDao.delete(tag);
		return true;
	}

	@Override
	public void addTagToFavorite(CustomerFavorite favorite, String tagString) {
		Customer customer = favorite.getCustomer();
		String[] tagNameArray = tagString.split("\\s+");
		for (String tagName : tagNameArray) {
			Set<CustomerFavoriteTag> existedTags = favorite.getTagList();
			boolean existed = false;
			if (existedTags != null) {
				for (CustomerFavoriteTag existedTag : existedTags) {
					if (existedTag.getTagName().equals(tagName)) {
						existed = true;
						break;
					}
				}
			}
			if (!existed) {
				CustomerFavoriteTag tag = customerFavoriteTagDao
						.getByCustomerAndName(customer, tagName);
				if (tag == null) {
					tag = new CustomerFavoriteTag(customer, tagName);
					customerFavoriteTagDao.save(tag);
				}
				favorite.addTag(tag);
			}
		}
		if (favorite.getTagList() != null) {
			Set<CustomerFavoriteTag> removeTags = new HashSet<CustomerFavoriteTag>();
			for (CustomerFavoriteTag tag : favorite.getTagList()) {
				boolean existed = false;
				for (String tagName : tagNameArray) {
					if (tag.getTagName().equals(tagName)) {
						existed = true;
						break;
					}
				}
				if (!existed) {
					removeTags.add(tag);
				}
			}
			if (!removeTags.isEmpty()) {
				for (CustomerFavoriteTag removeTag : removeTags) {
					favorite.removeTag(removeTag);
					customerFavoriteTagDao.update(removeTag);
				}
			}
		}
		customerFavoriteDao.update(favorite);
	}

	@Override
	public boolean createFavoriteTag(Customer customer, String tagName) {
		CustomerFavoriteTag tag = customerFavoriteTagDao.getByCustomerAndName(
				customer, tagName);
		if (tag == null) {
			tag = new CustomerFavoriteTag(customer, tagName);
			customerFavoriteTagDao.save(tag);
			return true;
		}
		return false;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Set<String> findRecommendFavoriteTagNames(Customer customer,
			ProductSale productSale, int size) {
		Set<String> recommendTags = new LinkedHashSet<String>();
		List<CustomerFavoriteTag> tagList = customerFavoriteTagDao
				.findByCustomer(customer, size);
		if (tagList != null) {
			for (CustomerFavoriteTag tag : tagList) {
				recommendTags.add(tag.getTagName());
			}
		}
		Product product = productSale.getProduct();
		addRecommendTags(recommendTags, product.getCategory(), size);
		if(recommendTags.size() < size){
			recommendTags.add(CustomerFavoriteTag.DEFAULT_TAG_NAME);
		}
		return recommendTags;
	}
	
	private void addRecommendTags(Set<String> recommendTags, Category category, int size){
		if(recommendTags.size() >= size || category == null){
			return;
		}
		recommendTags.add(category.getName());
		addRecommendTags(recommendTags, category.getParent(), size);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<CustomerFavoriteTag, Integer> findFavoriteTag(Customer customer) {
		List<Object[]> queryList = customerFavoriteTagDao.group(customer);
		int noTagCount = ((Long) customerFavoriteTagDao.countByNoTag(customer))
				.intValue();
		int allTagCount = ((Long) customerFavoriteTagDao
				.countByAllTag(customer)).intValue();
		Map<CustomerFavoriteTag, Integer> result = new LinkedHashMap<CustomerFavoriteTag, Integer>();

		CustomerFavoriteTag allTag = new CustomerFavoriteTag(customer, "全部");
		allTag.setId(new Long(-1));
		result.put(allTag, allTagCount);

		if (noTagCount > 0) {
			CustomerFavoriteTag noTag = new CustomerFavoriteTag(customer,
					"未设置标签");
			final long id = -2;
			noTag.setId(id);
			result.put(noTag, noTagCount);
		}
		if (queryList != null) {
			for (Object[] objs : queryList) {
				result.put((CustomerFavoriteTag) objs[0], (Integer) objs[1]);
			}
		}
		return result;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerFavorite getFavorite(Customer customer,
			ProductSale productSale) {
		return customerFavoriteDao.getByCustomerAndProductSale(customer,
				productSale);
	}

	@Override
	public void addToBoughtList(Order order) {
		Set<OrderItem> itemList = order.getItemList();
		for (OrderItem item : itemList) {
			CustomerBought bought = new CustomerBought(item);
			customerBoughtDao.save(bought);
		}
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerBought> findBought(Customer customer, Code productSort,
			Map<String, Object> parameters, short orderBy, Pagination pagination) {
		return customerBoughtDao.findBought(customer, productSort, parameters,
				orderBy, pagination);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long findBoughtCount(Customer customer, Code productSort,
			Map<String, Object> parameters) {
		return customerBoughtDao.findBoughtCount(customer, productSort,
				parameters);
	}

	@Override
	public boolean dig(Customer customer, String client, String session,
			ProductSale productSale) {
		boolean add = false;
		CustomerDigging digging = null;
		if (customer != null) {
			digging = customerDiggingDao.get(customer, productSale);
		}
		if (digging == null) {
			digging = customerDiggingDao.get(client, productSale);
		}
		if (digging == null) {
			digging = new CustomerDigging(customer, client, session,
					productSale);
			customerDiggingDao.save(digging);
			ProductSalePerformance productSalePerformance = productSale
					.getPerformance();
			productSalePerformance.setTotalDigging(productSalePerformance
					.getTotalDigging() + 1);
			productSalePerformanceDao.saveOrupdate(productSalePerformance);
			add = true;
			CustomerPointsRule customerPointsRule = customerPointsRuleFactory
					.createFavoriteCustomerPointsRule(customer);
			customerPointsRule.addPoints();
		}
		return add;
	}

	public List<Customer> find(Map<String, Object> parameters,
			Pagination pagination) {
		return customerDao.find(parameters, pagination);
	}

	@Override
	public void visit(CustomerVisited visited) {
		CustomerVisited existedVisited = customerVisitedDao.get(
				visited.getClient(), visited.getProductSale());
		if (existedVisited == null) {
			customerVisitedDao.save(visited);
		} else {
			existedVisited.setDeleted(false);
			existedVisited.setVisitTime(new Date());
			customerVisitedDao.update(existedVisited);
		}
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ProductSale> findVisitedList(String client, int maxResults) {
	    List<ProductSale> ps = customerVisitedDao.findAvailableByClient(client, maxResults);
	    for(ProductSale p : ps){
	        p.setImageUrl(p.getProduct().getImageUrlFor55px());
	    }
	    return ps;
	}

	@Override
	public void cleanVisitedList(String client) {
		customerVisitedDao.deleteByClient(client);
	}

	@Override
	public long getCommentCount(Customer customer) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		return customerCommentDao.findCount(parameters);
	}

	@Override
	public List<CustomerComment> findComments(Customer customer,
			Pagination pagination, Short sort) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		return customerCommentDao.find(parameters, pagination,
				Short.valueOf("0"));
	}

	@Override
	public List<CustomerComment> findUsefulComments(Customer customer,
			Pagination pagination, Short sort) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		parameters.put("usefulCount", 1);
		return customerCommentDao.find(parameters, pagination,
				Short.valueOf("0"));
	}

	@Override
	public List<CustomerQuestion> findQuestions(Customer customer,
			Pagination pagination, Short sort) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		return customerQuestionDao.find(parameters, pagination, sort);
	}

	@Override
	public List<CustomerQuestionReply> findQuestionReplies(User replier,
			Pagination pagination, Short sort) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("replier", replier);
		return customerQuestionDao.findReply(parameters, pagination, sort);
	}

	@Override
	public long getQuestionCount(Customer customer) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		return customerQuestionDao.find(parameters);
	}

	@Override
	public long getQuestionReplyCount(Customer customer) {
		return customerQuestionDao.findReply(customer);
	}

	@Override
	public long[] getCommentTypeCount(Customer customer) {
		long[] countArray = new long[2];
		countArray[0] = customerCommentDao.findUsefulCount(customer);
		countArray[1] = customerCommentDao.finduselessCount(customer);
		return countArray;
	}

	@Override
	public void saveOrUpdateCustomerExtension(
			CustomerExtension customerExtension) {
		customerDao.saveOrUpdateCustomerExtension(customerExtension);
	}
	
	@Override
	public void saveOrUpdateCustomerExtend(
			CustomerExtend customerExtend) {
		customerDao.saveOrUpdateCustomerExtend(customerExtend);
	}

	@Override
	public boolean sendEmailValidate(Customer customer) {
		if (customer == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = sdf.format(new Date());
		short version = customer.getEmailActive() == 0 ? (short) 2
				: (short) (customer.getEmailActive() + 1);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(CUSTOMER, customer);
		StringBuffer urlBuffer = new StringBuffer();
		try {
			urlBuffer.append("/emailActive/email?email=").append(
					URLEncoder.encode(customer.getEmail(), "utf-8"));
			urlBuffer.append("&date=")
					.append(URLEncoder.encode(currDate, "utf-8"))
					.append("&key=");
			urlBuffer
					.append(URLEncoder.encode(
							MD5Custom.encrypt(customer.getEmail() + currDate),
							"utf-8"));
			urlBuffer.append("&version=").append(version);
			model.put("url", urlBuffer.toString());
			mailService.sendMail(customer.getEmail(), this.subject,
					Constant.MAIL_EMAIL_VALIDATE, model);
			customer.setEmailActive(version);
			customerDao.update(customer);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public short activeEmail(String email, Date date, String key, short version) {
		if (StringUtils.isNullOrEmpty(email) || date == null
				|| StringUtils.isNullOrEmpty(key)) {
			return 0;
		}
		if (version == 0 || version == 1) {
			return 0;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (!MD5Custom.encrypt(email + sdf.format(date)).equalsIgnoreCase(key)) {
			return 0;
		}
		Calendar currDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(date);
		endDate.add(Calendar.MONTH, EMAIL_DURATION);
		if (currDate.after(endDate)) {
			return 2;
		}

		Customer customer = this.getByEmail(email);
		if (customer == null) {
			customer = this.getByEmail(new Code(Code.USER_SOURCE_ANONYMITY),
					email);
			if (customer == null) {
				return 0;
			}
		}
		if (customer.getEmailActive() == version) {
			customer.setEmailActive((short) 1);
			customerDao.update(customer);
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public void subPointsByExchangePresent(Customer customer, int points)
			throws CustomerPointsException {
		CustomerAccount account = customer.getAccount();
		int accountPoints = account.getPoints();
		if (accountPoints < points) {
			throw new CustomerPointsException();
		}
		account.setPoints(accountPoints - points);
		customerAccountDao.update(account);
		CustomerPoints customerPoints = new CustomerPoints(customer, -points,
				new Code(Code.CUSTOMER_POINTS_TYPE_PRESENT_EXCHANGE), null,
				"积分兑换礼券");
		customerPointsDao.save(customerPoints);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerPoints> findPoints(Customer customer, Date startDate,
			Date endDate, Pagination pagination) {
		return customerPointsDao.find(customer, startDate, endDate, pagination);
	}

	@Override
	public List<CustomerComment> findComments(Map<String, Object> parameters,
			Pagination pagination, Short sort) {
		return customerCommentDao.find(parameters, pagination, sort);
	}

	@Override
	public void addNotify(CustomerNotify customerNotify) {
		customerNotifyDao.save(customerNotify);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerNotify> findArrival(Map<String, Object> parameters,
			short order, Pagination pagination) {
		Code type = codeDao.get(Code.ARRIVAL);
		parameters.put("type", type);
		parameters.put("saleStatus",
				codeDao.get(Code.PRODUCT_SALE_STATUS_ONSHELF));
		return customerNotifyDao.find(parameters, pagination, order);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerNotify> findPriceReduce(Map<String, Object> parameters,
			short order, Pagination pagination) {
		Code type = codeDao.get(Code.PRICE_REDUCE);
		Object reduced = parameters.get("flagPriceReduce");
		if (reduced != null && reduced.equals(Boolean.TRUE)) {
			parameters.put("flagArrival", Boolean.TRUE);
			parameters.put("saleStatus",
					codeDao.get(Code.PRODUCT_SALE_STATUS_ONSHELF));
		}
		parameters.put("type", type);
		return customerNotifyDao.find(parameters, pagination, order);
	}

	@Override
	public boolean removeNotify(Customer customer, ProductSale productSale,
			Code type) {
		CustomerNotify customerNotify = customerNotifyDao
				.getByCustomerAndProductSale(customer, productSale, type);
		if (customerNotify == null) {
			return false;
		}
		customerNotifyDao.delete(customerNotify);
		return true;
	}

	@Override
	public void updateNotify(CustomerNotify customerNotify, boolean isNotified) {
		customerNotify.setNotified(isNotified);
		customerNotifyDao.update(customerNotify);
	}

	@Override
	public void updateNotifies(List<CustomerNotify> customerNotifies,
			boolean isNotified) {
		for (CustomerNotify customerNotify : customerNotifies) {
			updateNotify(customerNotify, isNotified);
		}
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerNotify getNotify(Customer customer, ProductSale productSale,
			Code type) {
		return customerNotifyDao.getByCustomerAndProductSale(customer,
				productSale, type);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<Integer, Integer> countNotifyByDate(Customer customer, Code type) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(ALL, 0);
		map.put(ONE_MONTH, 0);
		map.put(THREE_MONTH, 0);
		map.put(SIX_MONTH, 0);
		map.put(ONE_YEAR, 0);
		map.put(ONE_YEAR_AGO, 0);

		List<Map<String, Object>> list = customerNotifyDao.groupByDate(
				customer.getId(), type.getId());
		for (Map<String, Object> m : list) {
			map.put(Integer.valueOf(m.get("time").toString()),
					Integer.valueOf(m.get("count").toString()));
		}
		map.put(THREE_MONTH, map.get(THREE_MONTH) + map.get(ONE_MONTH));
		map.put(SIX_MONTH, map.get(SIX_MONTH) + map.get(THREE_MONTH));
		map.put(ONE_YEAR, map.get(ONE_YEAR) + map.get(SIX_MONTH));
		map.put(ALL, map.get(ONE_YEAR) + map.get(ONE_YEAR_AGO));
		return map;
	}

	@Override
	public void setupCustomerAgent(Customer customer, BigDecimal discount,
			boolean isAgent, Employee employee) {
		BigDecimal setDiscount = BigDecimal.ONE;
		Long channelId = Channel.CHANNEL_EC;
		if (isAgent) {
			setDiscount = discount;
			channelId = Channel.CHANNEL_AGENT;
		}
		Channel channel = new Channel(channelId);
		customer.setChannel(channel);
		customer.setDiscount(setDiscount);
		UserStatusLog statusLog = new UserStatusLog();
		statusLog.setUser(customer);
		statusLog.setDiscount(setDiscount);
		statusLog.setChannel(channel);
		statusLog.setUpdateTime(new Date());
		statusLog.setOperator(employee);
		customerDao.update(customer);
		userStatusLogDao.save(statusLog);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<CustomerCashApply> findCustomerCashApply(
			Map<String, Object> parameters, Pagination pagination) {
		return customerCashApplyDao.findCustomerCashApply(parameters,
				pagination, (short) 0);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public CustomerCashApply getCashApplyByCustomerAndId(Customer customer,
			Long id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CUSTOMER, customer);
		parameters.put("id", id);
		List<CustomerCashApply> customerCashApplys = customerCashApplyDao
				.findCustomerCashApply(parameters, null, (short) 0);
		return customerCashApplys == null || customerCashApplys.size() <= 0 ? null
				: customerCashApplys.get(0);
	}

	public void save(CustomerCashApply customerCashApply, User user)
			throws CustomerAccountException, CustomerCashApplyException {
		if (customerCashApply.getMoney() == null
				|| customerCashApply.getMoney().compareTo(BigDecimal.ZERO) <= 0) {
			throw new CustomerCashApplyException(customerCashApply,
					"申请退款金额不得小于0");
		}
		Customer customer = customerCashApply.getCustomer();
		CustomerAccount account = customer.getAccount();
		BigDecimal currentFrozen = account.getFrozen().add(
				customerCashApply.getMoney());
		if (currentFrozen.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomerAccountException(account, "暂存款冻结款金额不得小于0");
		}
		account.setFrozen(currentFrozen);
		BigDecimal currentBalance = account.getBalance().subtract(
				customerCashApply.getMoney());
		if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomerAccountException(account, "暂存款金额不得小于0");
		}
		account.setBalance(currentBalance);
		customerCashApply.setCreateTime(new Date());
		customerCashApply.setStatus(new Code(Code.CUSTOMER_CASH_STATUS_NEW));
		CashApplyUpdateLog updateLog = new CashApplyUpdateLog();
		customerCashApply.addUpdateLog(updateLog, user, new Code(
				Code.CUSTOMER_CASH_STATUS_NEW), null);
		customerAccountDao.update(account);
		customerCashApplyDao.save(customerCashApply);
	}

	public CustomerCashApply getCashApply(Long id) {
		return customerCashApplyDao.get(id);
	}

	/**
	 * @param customerCashApply
	 * @param customer
	 * @throws CustomerAccountException
	 */
	public void cancelCashApplyByCustomer(CustomerCashApply customerCashApply,
			Customer customer) throws CustomerAccountException,
			CustomerCashApplyException {
		if (!customerCashApply.canCancelByCustomer()) {
			throw new CustomerCashApplyException(customerCashApply, "该状态不能被取消");
		}
		cancelCashApply(customerCashApply, customer);
	}

	/**
	 * @param customerCashApply
	 * @param user
	 * @throws CustomerAccountException
	 */
	public void cancelCashApply(CustomerCashApply customerCashApply, User user)
			throws CustomerAccountException, CustomerCashApplyException {
		if (!customerCashApply.canCancelByEmployee()) {
			throw new CustomerCashApplyException(customerCashApply, "该状态不能被取消");
		}
		customerCashApply.setStatus(new Code(Code.CUSTOMER_CASH_STATUS_CANCEL));
		CustomerAccount account = customerCashApply.getCustomer().getAccount();
		BigDecimal currentBalance = account.getBalance().add(
				customerCashApply.getMoney());
		if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomerAccountException(account, "暂存款余额不得小于0");
		}
		account.setBalance(currentBalance);
		BigDecimal currentFrozen = account.getFrozen().subtract(
				customerCashApply.getMoney());
		if (currentFrozen.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomerAccountException(account, "冻结款不得小于0");
		}
		account.setFrozen(currentFrozen);
		CashApplyUpdateLog updateLog = new CashApplyUpdateLog();
		customerCashApply.addUpdateLog(updateLog, user, new Code(
				Code.CUSTOMER_CASH_STATUS_CANCEL), null);
		customerAccountDao.update(account);
		customerCashApplyDao.update(customerCashApply);
	}

	/**
	 * @param customerCashApply
	 * @param employee
	 * @throws CustomerAccountException
	 */
	public void updateProcessingCashApply(CustomerCashApply customerCashApply,
			Employee employee) throws CustomerCashApplyException {
		if (!Code.CUSTOMER_CASH_STATUS_NEW.equals(customerCashApply.getStatus()
				.getId())) {
			throw new CustomerCashApplyException(customerCashApply, "申请退款id为"
					+ customerCashApply.getId() + "状态不能处理");
		}
		customerCashApply.setStatus(new Code(
				Code.CUSTOMER_CASH_STATUS_PROCESSING));
		CashApplyUpdateLog updateLog = new CashApplyUpdateLog();
		customerCashApply.addUpdateLog(updateLog, employee, new Code(
				Code.CUSTOMER_CASH_STATUS_PROCESSING), null);
		customerCashApplyDao.update(customerCashApply);
	}

	/**
	 * @param customerCashApply
	 * @param employee
	 * @param tradeNo
	 * @throws CustomerAccountException
	 */
	public void updateSuccessCashApply(CustomerCashApply customerCashApply,
			Employee employee, String tradeNo, String remark)
			throws CustomerAccountException, CustomerCashApplyException {
		if (!Code.CUSTOMER_CASH_STATUS_PROCESSING.equals(customerCashApply
				.getStatus().getId())) {
			throw new CustomerCashApplyException(customerCashApply, "申请退款id为"
					+ customerCashApply.getId() + "状态不能回填");
		}
		customerCashApply
				.setStatus(new Code(Code.CUSTOMER_CASH_STATUS_SUCCESS));
		CustomerAccount account = customerCashApply.getCustomer().getAccount();
		BigDecimal currentFrozen = account.getFrozen().subtract(
				customerCashApply.getMoney());
		if (currentFrozen.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomerAccountException(account, "冻结款金额不得小于0");
		}
		account.setFrozen(currentFrozen);
		CustomerAccountDetail detail = new CustomerAccountDetail();
		detail.setAmount(customerCashApply.getMoney().multiply(
				new BigDecimal(-1)));
		detail.setOperator(employee);
		detail.setType(new Code(Code.CUSTOMER_ACCOUNT_USE_TYPE_CASH));
		detail.setUseTime(new Date());
		detail.setBalance(account.getBalance());
		account.addAccountDetail(detail);
		customerAccountDao.update(account);
		CashApplyUpdateLog updateLog = new CashApplyUpdateLog();
		updateLog.setTradeNo(tradeNo);
		customerCashApply.addUpdateLog(updateLog, employee, new Code(
				Code.CUSTOMER_CASH_STATUS_SUCCESS), remark);
		customerCashApplyDao.update(customerCashApply);
	}

	public void saveOrUpdateCustomerPayee(CustomerPayee customerPayee) {
		customerPayeeDao.saveOrUpdate(customerPayee);
	}

	public void modifyCashApply(CustomerCashApply customerCashApply, User user)
			throws CustomerCashApplyException {
		if (!Code.CUSTOMER_CASH_STATUS_NEW.equals(customerCashApply.getStatus()
				.getId())) {
			throw new CustomerCashApplyException(customerCashApply, "申请退款id为"
					+ customerCashApply.getId() + "状态不能修改");
		}
		CashApplyUpdateLog updateLog = new CashApplyUpdateLog();
		customerCashApply.addUpdateLog(updateLog, user, new Code(
				Code.CUSTOMER_CASH_STATUS_NEW), null);
		customerCashApplyDao.update(customerCashApply);
	}

	@Override
	public List<UserStatusLog> findStatusLog(Customer customer, int size) {
		return userStatusLogDao.find(customer, size);
	}

	private void removeCookie() {
		CookieUtils.removeCookie(WebContext.currentRequest(),
				WebContext.currentResponse(), HEAD_SHOW);
		Cookie[] cookies = WebContext.currentRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (HEAD_SHOW.equals(cookie.getName())) {
					Cookie deleteCookie = new Cookie(HEAD_SHOW, null);
					deleteCookie.setMaxAge(0);
					deleteCookie.setPath(DEFAULT_PATH);
					deleteCookie.setDomain(DEFAULT_DOMAIN);
					WebContext.currentResponse().addCookie(deleteCookie);
				}
			}
		}
	}

	@Override
	public List<CustomerBought> findBought(Customer customer, Pagination pagination) {
		return this.findBought(customer, null, new HashMap<String, Object>(), (short)0, pagination);
	}
	@Override
	public long findBoughtCount(Customer customer) {
		return findBoughtCount(customer, null,new HashMap<String, Object>());
	}

	@Override
	public long nameOrEmailIsDouble(String key) {
		long num = customerDao.nameOrEmailIsDouble(key,key);
		return num;
	}


	public void sendUserMergeMail(Customer customer) {
		String subject = "文轩网：用户合并";
		String templateName = Constant.MAIL_MERGE_PASSWORD;

		String email = customer.getEmail();
		long time = System.currentTimeMillis();
		String token = MD5Utils.encryptWithKey(time + email);
		String url = "https://passport.winxuan.com/userMergeMail?";
		String encode = "UTF-8";
		try {
			url += "time=" + time + "&email="
					+ URLEncoder.encode(email, encode) + "&token="
					+ URLEncoder.encode(token, encode);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持UTF-8编码", e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("link", url);
		map.put(CUSTOMER, customer);
		mailService.sendMail(email, subject, templateName, map);
	}
	
	
	@Override
	public void bindingAccount(Customer customer,String username,String password) {
		
		//新增第三方账号
		Customer newCustomer = customer.createThirdCustomer();
		//将第三方登陆账号变为文轩网用户:解决原用户的账号关联无法查询问题
		customer.setName(username);
		customer.setEmail(username);
		customer.setPassword(MD5Custom.encrypt(password));
		customer.setSource(new Code(Code.USER_SOURCE_EC_FRONT));
		customerDao.update(customer);
		customerDao.save(newCustomer);
		CustomerThirdParty customerThirdParty = null;
		if(customerDao.getByThirdPartyId(customer.getId())!=null){
			customerThirdParty = customerDao.getByThirdPartyId(customer.getId());
			customerThirdParty.setWinxuan(customer);
		}else{
			customerThirdParty = new CustomerThirdParty();
			customerThirdParty.setThirdparty(newCustomer);
			customerThirdParty.setWinxuan(customer);
		}
		customerDao.saveCustomerThirdParty(customerThirdParty);
	}
	
	@Override
	public CustomerThirdParty getByThirdPartyId(Long thirdPartyId) {
		return customerDao.getByThirdPartyId(thirdPartyId);
	}
	
	@Override
	public CustomerThirdParty getByThirdPartyOutId(String outId) {
		return customerDao.getByThirdPartyOutId(outId);
	}

	@Override
	public void saveorupdateCustomerIp(CustomerIP customerIP) {
		customerDao.saveorupdate(customerIP);
	}

	@Override
	public List<CustomerIP> getCustomerIPByCustomer(Map<String,Object> params) {
		return customerDao.getCustomerIPByCustomer(params,new Pagination(1),(short)0);
	}

}
