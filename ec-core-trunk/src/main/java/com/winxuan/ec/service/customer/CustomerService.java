/*
 * @(#)CustomerService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.customer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.CustomerCashApplyException;
import com.winxuan.ec.exception.CustomerExtendIsNullException;
import com.winxuan.ec.exception.CustomerPointsException;
import com.winxuan.ec.exception.RegisterEmailDuplicateException;
import com.winxuan.ec.exception.RegisterNameDuplicateException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.customer.CustomerAccountDetail;
import com.winxuan.ec.model.customer.CustomerAddress;
import com.winxuan.ec.model.customer.CustomerBought;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.customer.CustomerComment;
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
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.model.user.UserStatusLog;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.validator.AuthenticationProvider;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-11
 */
public interface CustomerService extends AuthenticationProvider {
	/**
	 * 根据用户id获得用户
	 * 
	 * @param id
	 *            用户id
	 * @return 存在返回Customer，不存在返回null
	 */
	Customer get(Long id);

	/**
	 * 根据用户名获得用户，用户来源来ec前台
	 * 
	 * @param name
	 *            用户名
	 * @return 存在返回Customer，不存在返回null
	 */
	Customer getByName(String name);

	/**
	 * 根据用户来源和用户名取得用户
	 * @param source
	 * @param name
	 * @return
	 */
	Customer getByName(Code source, String name);
	
	/**
	 * 根据用户邮箱获得用户，用户来源来ec前台
	 * 
	 * @param email
	 *            用户邮箱
	 * @return 存在返回Customer，不存在返回null
	 */
	Customer getByEmail(String email);
	
	/**
	 * 根据用户来源和邮箱获得用户
	 * @param source
	 * @param email
	 * @return
	 */
	Customer getByEmail(Code source, String email);
	
	/**
	 * 根据用户名或者邮箱获得用户
	 * @param key
	 * @return
	 */
	Customer getByNameOrEmail(String key);

	/**
	 * 根据用户名和密码登录 传入密码后经过md5产生摘要，再与name一起调用dao查找数据库
	 * 登录成功后设置cusomter.lastLoginTime为当前时间
	 * 
	 * @param name
	 *            用户名
	 * @param pasword
	 *            密码明文
	 * @return 登录成功返回Customer，登录失败返回null
	 */
	Customer login(String name, String password);
	
	/**
	 * 根据用户名和密码登录 传入密码后经过md5产生摘要，再与name一起调用dao查找数据库
	 * 登录成功后设置cusomter.lastLoginTime为当前时间
	 * 
	 * @param name
	 *            用户名
	 * @param pasword
	 *            密码明文
	 * @return 登录成功返回Customer，登录失败返回null
	 */
     Customer login9yue(String name, String password);

	/**
	 * 检查用户名是否已存在，默认来源ec前台
	 * 
	 * @param name
	 *            用戶名
	 * @return 已存在返回true，不存在返回false
	 */
	boolean nameIsExisted(String name);
	
	/**
	 * 检查用户来源下注册名是否存在
	 * @param source
	 * @param name
	 * @return
	 */
	boolean nameIsExisted(Code source, String name);
	/**
	 * 检查邮箱是否已存在，默认来源为ec前台
	 * @param email
	 * @return
	 */
	boolean emailIsExisted(String email);
	
	/**
	 * 检查某个用户来源下邮箱是否重复
	 * @param source
	 * @param email
	 * @return
	 */
	boolean emailIsExisted(Code source, String email);

	/**
	 * 注册用户 需设置customer.lastLoginTime和customer.registerTime为当前时间
	 * 需设置customer.account，其中account.balance为0，account.lastUseTime为当前时间
	 * 注册会判断40100,40001 两种来源.同一个用户名和email,在两种来源中只能存在一个.
	 * 
	 * @param customer
	 *            用戶
	 * @throws RegisterNameDuplicateException
	 *             如果用户名重复抛出此异常
	 */
	void register(Customer customer) throws RegisterNameDuplicateException,
			RegisterEmailDuplicateException;

	/**
	 * 修改用户信息
	 * 
	 * @param customer
	 *            用户
	 */
	void update(Customer customer);

	/**
	 * 重置密码<br/>
	 * 
	 * 
	 * @param email
	 *            注册时的邮箱
	 * @param newPassword
	 * 			     明文密码 
	 * @return 
	 */
	boolean resetPassword(String email , String newPassword);

	/**
	 * 使用用户暂存款<br/>
	 * 更新customerAccount.balance和customerAccount.lastUseTime，
	 * 增加一条customerAccountDetail
	 * 
	 * @param customer
	 *            用户
	 * @param money
	 *            使用金额（有正负之分，参考com.winxuan.ec.model.customer.
	 *            CustomerAccountDetail）
	 * @param type
	 *            使用类型，code9000
	 * @param order
	 *            参与使用的订单 如果是充值或者暂存款提现，不传入订单参数
	 * @param operator
	 *            暂存款使用的操作者
	 * @throws CustomerAccountException
	 *             使用暂存款金额超出暂存款余额时或冻结款超出，抛出此异常
	 */
	void useAccount(Customer customer, BigDecimal money, Order order,
			User operator, Code type) throws CustomerAccountException;

	/**
	 * 账户充值<br/>
	 * 调用useAccount()方法，然后保存credential
	 * 
	 * @param customer
	 *            用户
	 * @param money
	 *            金额
	 * @param credential
	 *            支付凭据
	 */
	void chargeAccount(Customer customer, BigDecimal money,
			PaymentCredential credential);

	/**
	 * 保存或新增用户地址模板
	 * 
	 * @param address
	 *            地址模板
	 */
	void saveAddress(CustomerAddress address);

	/**
	 * 更新用户地址模板
	 * 
	 * @param address
	 */
	void updateAddress(CustomerAddress address);

	/**
	 * 更新用户默认地址
	 * 
	 * @param address
	 */
	void updateAddressUsual(CustomerAddress address, boolean isUsual);

	/**
	 * 通过编号获取会员常用地址
	 * 
	 * @param id
	 * @return
	 */
	CustomerAddress getCustomerAddress(Long id);

	/**
	 * 通过编号和用户获取会员常用地址
	 * 
	 * @param id
	 * @param customer
	 * @return
	 */
	CustomerAddress getCustomerAddress(Long id, Customer customer);

	/**
	 * 删除会员常用地址
	 * 
	 * @param id
	 */
	void deleteAddress(CustomerAddress customerAddress);

	/**
	 * 查询暂存款使用明细
	 * 
	 * @param parameters
	 * <br/>
	 *            orderId:订单号<br/>
	 *            customerName:注册名<br/>
	 *            startDate:开始时间<br/>
	 *            endDate:结束时间<br/>
	 * 
	 * @param pagination
	 * @return
	 */
	List<CustomerAccountDetail> findAccountDetail(
			Map<String, Object> parameters, Pagination pagination);
	
	/**
	 * 查询某用户的一张订单的暂存款使用明细
	 * @param customer
	 * @param order
	 * @return
	 */
	List<CustomerAccountDetail> findAccountDetail(CustomerAccount account, Order order);

	/**
	 * 查询用户收藏商品
	 * 
	 * @param customer
	 *            用户<br/>
	 * @param sort
	 *            商品类别<br/>
	 * @param tag
	 *            标签<br/>
	 * @param flag
	 *            是否是查询未设置标签<br/>
	 * @param index
	 *            排序：0收藏时间降序，1价格升序，2折扣升序<br/>
	 * @param pagination
	 * @return
	 */
	List<CustomerFavorite> findFavorite(Customer customer, Code sort,
			CustomerFavoriteTag tag, Boolean flag, short index,
			Pagination pagination);
	
	/**
	 * 按收藏时间倒叙查询用户收藏的图书商品（上架的）
	 * 
	 * @param customer
	 *            用户
	 * @param size
	 *            查询数量
	 * @return
	 */
	List<CustomerFavorite> findFavorite(Customer customer, int size);

	/**
	 * 查询用户收藏夹中新到货商品（从下架变为上架）
	 * 
	 * @param customer
	 * @return
	 */
	long countOfNewFavorite(Customer customer);

	/**
	 * 查询用户收藏夹中降价商品
	 * 
	 * @param customer
	 * @return
	 */
	long countOfReductionFavorate(Customer customer);

	/**
	 * 添加商品到收藏夹<br/>
	 * 如果已存在，则更新收藏时间为当前时间
	 * 
	 * @param customer
	 * @param productSale
	 * @return 不存在添加返回true，已存在更新收藏时间返回false
	 */
	boolean addToFavorite(Customer customer, ProductSale productSale);

	/**
	 * 收藏夹中移除商品，同时清除与标签对应关系
	 * 
	 * @param customer
	 * @param productSale
	 * @return 移除成功返回true，如果不存在返回false
	 */
	boolean removeFromFavorite(Customer customer, ProductSale productSale);

	/**
	 * 获得一个收藏
	 * 
	 * @param favoriteId
	 * @return
	 */
	CustomerFavorite getFavorite(Long favoriteId);

	/**
	 * 获得一个收藏夹标签
	 * 
	 * @param tagId
	 * @return
	 */
	CustomerFavoriteTag getFavoriteTag(Long tagId);

	/**
	 * 获得一个收藏夹标签
	 * 
	 * @param
	 * @return
	 */
	CustomerFavoriteTag getFavoriteTagByTagName(Customer customer,
			String tagName);

	/**
	 * 创建一个标签
	 * 
	 * @param customer
	 * @param tagName
	 * @return 创建成功返回true，已存在返回false
	 */
	boolean createFavoriteTag(Customer customer, String tagName);

	/**
	 * 更新收藏夹标签
	 */
	boolean updateFavoriteTag(Customer customer, Long tagId, String tagName);

	/**
	 * 删除收藏夹标签，同时清除与收藏商品对应关系
	 * 
	 * @param tag
	 */
	boolean deleteFavoriteTag(Customer customer, Long tagId);

	/**
	 * 向收藏商品添加标签
	 * 
	 * @param favorite
	 * @param tagString
	 *            以空格分隔的标签字符串
	 */
	void addTagToFavorite(CustomerFavorite favorite, String tagString);

	/**
	 * 根据商品获得向用户推荐的收藏夹标签名称
	 * 
	 * @param customer
	 * @param productSale
	 * @param size
	 *            推荐数量
	 * @return
	 */
	Set<String> findRecommendFavoriteTagNames(Customer customer,
			ProductSale productSale, int size);

	/**
	 * 根据用户获得收藏标签
	 * 
	 * @param customer
	 * @return 收藏标签:对应收藏商品数量
	 */
	Map<CustomerFavoriteTag, Integer> findFavoriteTag(Customer customer);

	/**
	 * 根据用户和商品取得收藏
	 * 
	 * @param customer
	 * @param productSale
	 * @return
	 */
	CustomerFavorite getFavorite(Customer customer, ProductSale productSale);

	/**
	 * 添加订单中商品到用户已购商品列表<br/>
	 * 依次添加order.itemList.[\d].productSale到用户已购商品列表<br/>
	 * 如果已存在，则更新订单、数量、购买价格、购买时间
	 * 
	 * @param order
	 */
	void addToBoughtList(Order order);

	/**
	 * 查询用户已购商品
	 * 
	 * @param customer
	 *            用户
	 * @param productSort
	 *            商品类别，可不传入
	 * @param parameters
	 *            productName:商品名称 startDate:开始时间 endDate:结束时间
	 * @param orderBy
	 *            排序方式:0购买时间降序、1购买时间升序、2价格降序、3价格升序、4折扣降序、5折扣升序
	 * @param pagination
	 *            分页
	 * @return
	 */
	List<CustomerBought> findBought(Customer customer, Code productSort,
			Map<String, Object> parameters, short orderBy, Pagination pagination);
	/**
	 * 查询用户已购商品 重载方法
	 * @param customer
	 * @param pagination
	 * @return
	 */
	List<CustomerBought> findBought(Customer customer, Pagination pagination);
	
	/**
	 * 查询易购商品数量
	 * @param customer
	 * @param productSort
	 * @param parameters
	 * @return
	 */
	long findBoughtCount(Customer customer, Code productSort,Map<String, Object> parameters);

	/**
	 * 用户对商品点击“我喜欢”
	 * 
	 * @param customer
	 * @param productSale
	 * @return 如果已点击过，更新时间，返回false，否则增加一条记录，返回true
	 */
	boolean dig(Customer customer, String client,String session,ProductSale productSale);

	/**
	 * 查询会员<br/>
	 * name:用户名<br/>
	 * registerTimeStart:最早注册时间<br/>
	 * registerTimeEnd:最晚注册时间<br/>
	 * lastLoginTimeStart:上次登录的最早时间<br/>
	 * lastLoginTimeEnd:上次登录的最晚时间<br/>
	 * lastTradeTimeStart:上次交易的最早时间<br/>
	 * lastTradeTimeEnd:上次交易的最晚时间<br/>
	 * channel:所属渠道<br/>
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<Customer> find(Map<String, Object> parameters, Pagination pagination);

	/**
	 * 记录用户访问商品历史<br/>
	 * 如果已访问过，更新访问时间
	 * 
	 * @param visited
	 */
	void visit(CustomerVisited visited);

	/**
	 * 取得用户访问历史
	 * 
	 * @param client
	 *            客户端cookie client值
	 * @param maxResults
	 *            最大数量
	 * @return
	 */
	List<ProductSale> findVisitedList(String client, int maxResults);

	/**
	 * 清除用户的访问记录
	 * 
	 * @param client
	 */
	void cleanVisitedList(String client);

	/**
	 * 获取用户的评论数量
	 * 
	 * @param customer
	 * @return
	 */
	long getCommentCount(Customer customer);

	/**
	 * 获取用户的评论集合
	 * 
	 * @param customer
	 * @return
	 */
	List<CustomerComment> findComments(Customer customer,
			Pagination pagination, Short sort);
	
	/**
	 * 获取用户的评论集合
	 * 
	 * @param customer
	 * @return
	 */
	List<CustomerComment> findComments(Map<String,Object> parameters,
			Pagination pagination, Short sort);

	/**
	 * 获取用户提问集合
	 * 
	 * @param customer
	 * @param pagination
	 * @param sort
	 * @return
	 */
	List<CustomerQuestion> findQuestions(Customer customer,
			Pagination pagination, Short sort);

	/**
	 * 获取用户评论回复
	 * 
	 * @param customer
	 * @param pagination
	 * @param sort
	 * @return
	 */
	List<CustomerQuestionReply> findQuestionReplies(User replier,
			Pagination pagination, Short sort);

	/**
	 * 获取用户有用的评论
	 * 
	 * @param customer
	 * @param pagination
	 * @param sort
	 * @return
	 */
	List<CustomerComment> findUsefulComments(Customer customer,
			Pagination pagination, Short sort);

	/**
	 * 获取用户提问数量
	 * 
	 * @param customer
	 * @return
	 */
	long getQuestionCount(Customer customer);

	/**
	 * 获取用户提问回复数量
	 * 
	 * @param customer
	 * @return
	 */
	long getQuestionReplyCount(Customer customer);

	/**
	 * 获取用户有用/无用数
	 * 
	 * @param customer
	 * @return [0]有用数 [1]无用数
	 */
	long[] getCommentTypeCount(Customer customer);


	/**
	 * 保存或更新用户的更多信息
	 * 
	 * @param customerExtension
	 */
	void saveOrUpdateCustomerExtension(CustomerExtension customerExtension);
	
	void saveOrUpdateCustomerExtend(CustomerExtend customerExtend);

	/**
	 * 发送验证邮件给用户
	 * 
	 * @param customer
	 * 
	 */
	boolean sendEmailValidate(Customer customer);

	/**
	 * 激活邮箱
	 * 
	 * @param email
	 * @param date激活码生成的时间
	 * @param key
	 * @param version激活码的版本号
	 * @return 返回0失败，1成功，2激活码失效
	 */
	short activeEmail(String email, Date date, String key, short version);


	/**
	 * 客户使用积分兑换礼券
	 * 
	 * @param customer
	 *            用户
	 * @param points
	 *            消耗的积分数量
	 * @throws CustomerPointsException
	 *             积分不足抛出此异常
	 */
	void subPointsByExchangePresent(Customer customer, int points)
			throws CustomerPointsException;

	/**
	 * 查询用户积分明细
	 * 
	 * @param customer
	 *            用户
	 * @param startDate
	 *            查询的开始日期
	 * @param endDate
	 *            查询的结束日期
	 * @return
	 */
	List<CustomerPoints> findPoints(Customer customer, Date startDate,
			Date endDate, Pagination pagination);
	
	/**
	 * 新增一个通知
	 * @param customerNotify
	 */
	void addNotify(CustomerNotify customerNotify);
	
	/**
	 * 查询通知
	 * @param customer
	 * @param productSale
	 * @param type 类型：降价 or 货到
	 * @return
	 */
	CustomerNotify getNotify(Customer customer, ProductSale productSale, Code type);
	
	/**
	 * 查询商品到货通知
	 * 
	 * @param customer
	 * @param parameters
	 * @param order
	 * 			排序
	 * @param pagination
	 * @return
	 */
	List<CustomerNotify> findArrival(Map<String, Object> parameters,
			short order, Pagination pagination);
	
	/**
	 * 查询商品降价通知
	 * 
	 * @param customer
	 * @param parameters
	 * @param order
	 * 			排序
	 * @param pagination
	 * @return
	 */
	List<CustomerNotify> findPriceReduce(Map<String, Object> parameters,
			short order, Pagination pagination);
	
	/**
	 * 删除商品通知
	 * @param customer 
	 * @param productSale
	 * @param type 类型：降价 or 货到
	 * @return
	 */
	boolean removeNotify(Customer customer, ProductSale productSale,Code type);
	
	/**
	 * 按日期查询商品通知数量
	 * @param customer
	 * @param type 类型：降价 or 货到
	 * @return
	 */
	Map<Integer,Integer> countNotifyByDate(Customer customer,Code type);
	
	/**
	 * 修改通知是否已经通知
	 * @param customerNotify
	 * @param isNotified 是否已经通知
	 */
	void updateNotify(CustomerNotify customerNotify , boolean isNotified);
	
	/**
	 * 设置用户为代理或者非代理<br/>
	 * 设置为代理时，将用户所属渠道设置为Channel.CHANNEL_AGENT（代理渠道），同时设置代理折扣为参数传入的discount<br/>
	 * 设置为非代理时，将用户所属渠道设置为Channel.CHANNEL_EC（直销普通渠道），同时忽略传入参数discount，将customer.discount设置为1
	 * @param customer 用户
	 * @param discount 代理折扣，isAgent为false时可不传此参数
	 * @param isAgent 是否为代理
	 */
	void setupCustomerAgent(Customer customer, BigDecimal discount,
			boolean isAgent,Employee employee);
	
	/**
	 * 得到退款列表
	 * @param map
	 * @param pagination
	 * @return
	 */
	List<CustomerCashApply> findCustomerCashApply(Map<String, Object> parameters,Pagination  pagination);

	/**
	 * 保存退款 申请暂存款提现时将CustomerCashApply里的money增加到到frozen，减少balance
	 * 
	 * @param customerCashApply
	 * @param user
	 */
	void save(CustomerCashApply customerCashApply, User user)
			throws CustomerAccountException, CustomerCashApplyException;
	
	/**
	 * @param id
	 * @return
	 */
	CustomerCashApply getCashApply(Long id); 
	/**
	 * 根据customer和id获得退款申请
	 * @param customer
	 * @param id
	 * @return
	 */
	CustomerCashApply getCashApplyByCustomerAndId(Customer customer,Long id);
	
	/**
	 * 更新提现申请为取消 撤销提现申请时将CustomerCashApply里的money增加到balance，
	 * CustomerCashApply里的money减少到frozen,记录一条提现申请日志
	 * 
	 * @param customerCashApply
	 * @param user
	 */
	void cancelCashApply(CustomerCashApply customerCashApply, User user)
			throws CustomerAccountException, CustomerCashApplyException;

	/**
	 * 用户取消
	 */
	void cancelCashApplyByCustomer(CustomerCashApply customerCashApply,
			Customer customer) throws CustomerAccountException,
			CustomerCashApplyException;
	
	/**
	 * 
	 * 更新提现申请为处理中 将状态更新为处理中，记录一条暂存款申请日志
	 * 
	 * @param customerCashApply
	 * @param employee
	 */
	void updateProcessingCashApply(CustomerCashApply customerCashApply,
			Employee employee) throws CustomerCashApplyException;
	
	/**
	 * 更新提现申请已退款 将CustomerCashApply里的money减少到frozen,添加一条暂存款明细记录,添加暂存款申请日志
	 * 
	 * @param customerCashApply
	 * @param employee
	 * @param tradeNo
	 * @param remark
	 * @throws CustomerAccountException
	 */
	void updateSuccessCashApply(CustomerCashApply customerCashApply,
			Employee employee, String tradeNo, String remark)
			throws CustomerAccountException, CustomerCashApplyException;
	/**
	 * 在退款申请状态为待处理修改申请退款
	 * @param customerCashApply
	 * @param employee
	 * @throws CustomerCashApplyException
	 */
	 void modifyCashApply(CustomerCashApply customerCashApply,
			User user) throws CustomerCashApplyException;

    /**
      * 
      * @param customerPayee
      */
     void saveOrUpdateCustomerPayee(CustomerPayee customerPayee);
     
     /**
      * 批量修改通知
      * @param customerNotifies
      * @param isNotified
      */
     void updateNotifies(List<CustomerNotify> customerNotifies , boolean isNotified);
     
     /**
      * 发送密码找回邮件
      * @param customer
      */
     void sendResetPasswordMail(Customer customer);
     
     /**
      * 发送密码合并用户
      * @param customer
      */
     void sendUserMergeMail(Customer customer);
     
     /**
      * 查询用户状态日志
     * @param customer
     * @param size
     * @return
     */
    List<UserStatusLog> findStatusLog(Customer customer,int size);

    /**
     * 验证支付密码
     * @param customer
     * @param payPassword
     * @return
     * @throws CustomerExtendIsNullException 
     */
    boolean validatePayPassword(Customer customer, String payPassword) throws CustomerExtendIsNullException;

    /**
	 * 查询易购商品数量 重载
	 * @param customer
	 * @return
	 */
	long findBoughtCount(Customer customer);
	
	/**
	 * 判断用户名或者邮箱是不是重复
	 */
	long nameOrEmailIsDouble(String key);


	/**
	 * 重置密码重载方法<br/>
	 * 
	 * 
	 * @param email
	 *            注册时的邮箱
	 * @param newPassword
	 * 			     明文密码 
	 * @param userSource 用户来源
	 * @return 
	 */
	boolean resetPassword(String email, String password, Code userSource);
	
	
	
	/**
	 * 第三方账号绑定文轩网账号
	 * 新增账号source（40001），用户名，密码
	 * @param customer
	 * @param username
	 * @param password
	 */
	void bindingAccount(Customer customer,String username,String password);
	
	
	/**
	 * 通过第三方账号id获取文轩账户关联
	 * @param thirdPartyId
	 * @return CustomerThirdParty
	 */
	CustomerThirdParty getByThirdPartyId(Long thirdPartyId);
	
	/**
	 * 通过第三方账号name获取文轩账户关联
	 * @param outId
	 * @return CustomerThirdParty
	 */
	CustomerThirdParty getByThirdPartyOutId(String outId);
	
	/**
	 * 保存用户常用ip地址
	 * 用户 customer,ip,区域area
	 * @param customerIP
	 */
	void saveorupdateCustomerIp(CustomerIP customerIP);
	
	/**
	 * 通过用户查询常用ip地址
	 * @param customer
	 * @return
	 */
	List<CustomerIP> getCustomerIPByCustomer(Map<String,Object> params);

}
