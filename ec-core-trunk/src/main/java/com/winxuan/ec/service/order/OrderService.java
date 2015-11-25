/*
 * @(#)OrderService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.order;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.exception.BatchDeliveryException;
import com.winxuan.ec.exception.CustomerAccountException;
import com.winxuan.ec.exception.OrderCloneException;
import com.winxuan.ec.exception.OrderDcMatchException;
import com.winxuan.ec.exception.OrderDeliveryException;
import com.winxuan.ec.exception.OrderInitException;
import com.winxuan.ec.exception.OrderPresellException;
import com.winxuan.ec.exception.OrderStatusException;
import com.winxuan.ec.exception.OrderStockException;
import com.winxuan.ec.exception.PaymentCredentialException;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.ReturnOrderException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerAccount;
import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderDeliverySplit;
import com.winxuan.ec.model.order.OrderExtend;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPackage;
import com.winxuan.ec.model.order.OrderPackges;
import com.winxuan.ec.model.order.OrderReceive;
import com.winxuan.ec.model.order.OrderTrack;
import com.winxuan.ec.model.payment.PaymentCredential;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.services.support.exception.RemoteBusinessException;
import com.winxuan.ec.exception.OrderException;

/**
 * description
 *  
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public interface OrderService {

	/**
	 * 下订单<br/>
	 * <p>
	 * 必须设置如下属性：<br/>
	 * <ol>
	 * <li>{@link Order#setCreator(User)}</li>
	 * <li>{@link Order#setDeliveryType(com.winxuan.ec.model.delivery.DeliveryType)}</li>
	 * <li>{@link Order#setCustomer(Customer)}</li>
	 * <li>{@link Order#setChannel(com.winxuan.ec.model.channel.Channel)}</li>
	 * <li>{@link Order#setConsignee(OrderConsignee)}</li>
	 * <li>{@link OrderConsignee#setConsignee(String)}</li>
	 * <li>{@link OrderConsignee#setPhone(String)} 或
	 * <li>{@link OrderConsignee#setMobile(String)}</li>
	 * <li>{@link OrderConsignee#setEmail(String)}</li>
	 * <li>{@link OrderConsignee#setCountry(com.winxuan.ec.model.area.Area)}</li>
	 * <li>{@link OrderConsignee#setProvince(com.winxuan.ec.model.area.Area)}</li>
	 * <li>{@link OrderConsignee#setCity(com.winxuan.ec.model.area.Area)}</li>
	 * <li>{@link OrderConsignee#setDistrict(com.winxuan.ec.model.area.Area)}</li>
	 * <li>{@link OrderConsignee#setAddress(String)}</li>
	 * <li>{@link OrderConsignee#setZipCode(String)}</li>
	 * <li>{@link Order#setItemList(java.util.Set)}</li>
	 * <li>{@link OrderItem#setProductSale(com.winxuan.ec.model.product.ProductSale)} </li>
	 * <li>{@link OrderItem#setListPrice(BigDecimal)}</li>
	 * <li>{@link OrderItem#setSalePrice(BigDecimal)}</li>
	 * <li>{@link OrderItem#setPurchaseQuantity(int)}</li>
	 * <li>{@link OrderItem#setShop(Shop)}</li>
	 * <li>{@link Order#setPaymentList(java.util.Set)}</li>
	 * <li>{@link OrderPayment#setPayment(com.winxuan.ec.model.payment.Payment)}</li>
	 * <li>{@link OrderPayment#setPayMoney(BigDecimal)}</li>
	 * <li>{@link OrderPayment#setCreateTime(Date)}</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 可选设置属性：<br/>
	 * <ol>
	 * <li>{@link Order#setDeliveryFee(BigDecimal)}</li>
	 * <li>{@link Order#setOuterId(String)}</li>
	 * <li>{@link Order#setSaveMoney(BigDecimal)}</li>
	 * <li>{@link Order#setConsignee(OrderConsignee)}
	 * <li>{@link OrderConsignee#setNeedInvoice(boolean)}</li>
	 * <li>{@link OrderConsignee#setInvoiceTitle(String)}</li>
	 * <li>{@link OrderConsignee#setOutOfStockOption(Code)}</li>
	 * <li>{@link OrderConsignee#setDeliveryOption(Code)}</li>
	 * <li>{@link Order#setInvoiceList(java.util.Set)}</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 处理逻辑为：<br/>
	 * <ol>
	 * <li>检查并设置订单促销信息{@link PromotionService#setupOrderPromotion(Order)}
	 * 。如果是运费优惠，调用{@link Order#setDeliveryFee(BigDecimal)}设置优惠运费, 如果是满省优惠，调用
	 * {@link Order#setSaveMoney(BigDecimal)}设置优惠金额，如果是满送优惠，调用
	 * {@link Order#addItem(OrderItem)}增加赠品项</li>
	 * <li>如果订单中含有套装商品{@link Product#isComplex()}，调用
	 * {@link OrderProductComplexUtils#splitProductComplex(Order)}进行拆分</li>
	 * <li>计算订单如下值：<br/>
	 * {@link Order#setLastProcessTime(Date)} 处理时间<br/>
	 * {@link Order#setPurchaseKind(int)} 品种数合计<br/>
	 * {@link Order#setPurchaseQuantity(int)} 商品数量合计<br/>
	 * {@link Order#setListPrice(BigDecimal)} 码洋合计<br/>
	 * {@link Order#setSalePrice(BigDecimal)} 实洋合计<br/>
	 * {@link Order#setStorageType(Code)} 取其中一个商品的储配方式 {@link ProductSale#getStorageType()}<br/>
	 * {@link Order#setSupplyType(Code)} 任意商品为新品预售时，设为新品预售，否则为正常销售 {@link ProductSale#getSupplyType()}<br/>
	 * {@link Order#setVirtual(boolean)} 取其中一个商品的对应值 {@link Product#isVirtual()}<br/>
	 * {@link Order#setShop(com.winxuan.ec.model.shop.Shop)} 取其中一个商品 {@link ProductSale#getShop()} <br/>
	 * {@link Order#setAdvanceMoney(BigDecimal)} 合计 {@link OrderPayment#getPayMoney()}且{@link OrderPayment#isPay()}为true的金额<br/>
	 * {@link Order#setPayType(Code)} 
	 * 如果已支付金额{@link Order#getAdvanceMoney()}小于应该支付金额{@link Order#getTotalPayMoney()}且任意{@link Payment#getType()}
	 * 为先货后款时 设为先货后款{@link Code#ORDER_PAY_TYPE_FIRST_DELIVERY}，否则设为先款后货 {@link Code#ORDER_PAY_TYPE_FIRST_PAY}<br/>
	 * {@link Order#setDeliveryFee(BigDecimal)} 如果为原来运费为null，判断订单所属店铺是否为文轩{@link Order#getShop()} {@link Shop#WINXUAN_SHOP}， 
	 * 如果是则调用
	 * {@link DeliveryService#getDeliveryFee(com.winxuan.ec.model.delivery.DeliveryType, com.winxuan.ec.model.area.Area, BigDecimal)}
	 * 设置运费， 如果不是文轩，调用{@link Shop#getDeliveryFee()}设置运费<br/>
	 * {@link Order#setProcessStatus(Code)} 如果已支付金额
	 * {@link Order#getAdvanceMoney()}等于应该支付金额{@link Order#getTotalPayMoney()}，
	 * 设为等待配货{@link Code#ORDER_PROCESS_STATUS_WAITING_PICKING}，如果小于设为已提交 {@link Code#ORDER_PROCESS_STATUS_NEW},如果大于，抛出异常。<br/>
	 * {@link Order#setPaymentStatus(Code)} 如果已支付金额
	 * {@link Order#getAdvanceMoney()}为0，设为未支付{@link Code#ORDER_PAY_STATUS_NONE}
	 * , 如果大于0但小于应该支付金额{@link Order#getTotalPayMoney()}，设为部分支付
	 * {@link Code#ORDER_PAY_STATUS_PART},如果等于，设为完全支付
	 * {@link Code#ORDER_PAY_STATUS_COMPLETED}<br/>
	 * {@link Order#setTransferResult(Code)} 设置为未传输  {@link Code#EC2ERP_STATUS_NONE}<br/>
	 * </li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * 其它相关处理： <br/>
	 * <ol>
	 * <li>如果满足促销，增加促销信息{@link Order#addPromotion(com.winxuan.ec.model.order.OrderPromotion)}</li>
	 * <li>增加订单处理日志 {@link Order#addStatusLog(Code, User)}</li>
	 * <li>如果订单为等待配货状态，增加商品库存占用率 {@link ProductService#updateProductQuantity(ProductSale, int, int)}</li>
	 * <li>如果使用暂存款，减少用户暂存款余额，记录账户日志 {@link CustomerService#useAccount(Customer, BigDecimal, Order, User, Code)}</li>
	 * <li>如果使用礼品卡，减少礼品卡余额，记录礼品卡日志 {@link PresentCardService#use(com.winxuan.ec.model.presentcard.PresentCard, Order, BigDecimal)}</li>
	 * <li>如果使用礼券，设置礼券使用金额和状态，记录礼券日志 {@link PresentService#usePresent(com.winxuan.ec.model.present.Present, Order, BigDecimal)}</li>
	 * <li>增加用户已购商品记录 {@link CustomerService#addToBoughtList(Order)}</li> 
	 * <li>向用户发送邮件</li>
	 * </ol>
	 * </p>
	 * 
	 * @param order
	 *            订单
	 * @throws OrderInitException
	 *             订单属性不完整或者错误
	 * @throws OrderStockException
	 *             订单所需商品可用量不足
	 * @throws CustomerAccountException
	 *             如果订单使用暂存款支付，暂存款使用异常
	 * @throws PresentException
	 *             如果订单使用礼券支付，礼券使用异常
	 * @throws PresentCardException
	 *             如果订单使用礼品卡支付，礼品卡使用异常
	 */
	void create(Order order) throws OrderInitException, OrderStockException,
			CustomerAccountException, PresentException, PresentCardException;

	/**
	 * 支付订单<br/>
	 * 依次使用支付凭证中的支付金额{@link PaymentCredential#getMoney()}支付订单：<br/>
	 * 查找对应的{@link OrderPayment#isPay()}为false的支付记录， 且支付凭证的支付方式
	 * {@link PaymentCredential#getPayment()}与订单支付方式
	 * {@link OrderPayment#getPayment()}一致或都属于网上支付类型， 如果支付金额能满足
	 * {@link OrderPayment#getPayMoney()}，则设置为已支付
	 * {@link OrderPayment#setPay(boolean)}以及相关属性， 同时增加订单已支付金额
	 * {@link Order#getAdvanceMoney()}，如果已支付金额等于订单需要支付的金额
	 * {@link Order#getTotalPayMoney()}， 则设置订单支付状态为完全支付
	 * {@link Order#setPaymentStatus(Code)}，同时设置订单处理状态为等待配货
	 * {@link Order#setProcessStatus(Code)}，增加订单处理日志， 锁定库存，发送通知邮件<br/>
	 * 如果支付凭证中的支付金额不能满足订单支付金额，则略过该订单。<br/>
	 * 依次支付完后，如果支付凭证中的支付金额还有剩余，则划入用户暂存款<br/>
	 * 保存paymentCredential
	 * 
	 * @param order
	 *            订单
	 * @param paymentCredential
	 *            支付凭据
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单不处于已提交状态或者处于已提交状态但不是先款后货类型，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 * @throws OrderStockException
	 *             如果订单商品可用量不足，抛出此异常
	 * @throws PaymentCredentialException 
	 * 			      如果支付凭证已经使用,抛出此异常           
	 * @throws ReturnOrderException 
	 */
	void pay(List<Order> orderList, PaymentCredential paymentCredential,
			User operator) throws OrderStatusException,
			CustomerAccountException, OrderStockException, PaymentCredentialException, ReturnOrderException;
	
	/**
	 * 前台暂存款支付，
	 * 如果能支付且支付后订单为完全支付，则设置订单支付状态为完全支付，处理状态为等待配货，
	 * 增加处理日志和更新暂存款支付的OrderPayment记录，锁定库存，发送邮件通知;<br/>
	 * 如果能支付且支付后订单为部分支付，则设置订单支付状态为部分支付，更新暂存款支付的OrderPayment记录,处理状态不变;<br/>
	 * 如果金额不足不能支付，略过改订单
	 * 
	 * @param orderList
	 *            订单列表
	 * @param customer
	 *            用户
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单不处于已提交状态或者处于已提交状态但不是先款后货类型，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 */
	void payByAccount(List<Order> orderList, Customer customer, User operator) 
	throws OrderStatusException, CustomerAccountException;
	
	
	/**
	 * 使用用户账户的暂存款批量支付订单<br/>
	 * 依次使用传入的暂存款金额支付订单：<br/>
	 * 如果能完全支付该订单，则设置订单支付状态为完全支付，处理状态为等待配货，
	 * 增加处理日志和暂存款支付的OrderPayment记录，锁定库存，发送邮件通知<br/>
	 * 如果金额不足不能完全支付,抛出异常
	 * 
	 * @param orderList
	 *            订单列表
	 * @param customerAccount
	 *            用户账户
	 * @param accountMoney
	 *            使用的暂存款
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单不处于已提交状态或者处于已提交状态但不是先款后货类型，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 * @throws OrderStockException
	 *             如果订单商品可用量不足，抛出此异常
	 */
	void pay(List<Order> orderList, CustomerAccount customerAccount,
			BigDecimal accountMoney, User operator)
			throws OrderStatusException, CustomerAccountException,
			OrderStockException;

	/**
	 * 审核订单<br/>
	 * 订单状态改为等待配货，锁定库存，发送邮件通知，增加处理日志
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单不处于已提交状态或者处于已提交状态但不是先货后款类型，抛出此异常
	 * @throws OrderStockException
	 *             如果订单商品可用量不足，抛出此异常
	 */
	void audit(Order order, Employee operator) throws OrderStatusException,
			OrderStockException;

	/**
	 * 作废订单
	 * 综合和专业渠道订单状态为8002、8003时可以作废
	 * @param order
	 * @param operator
	 * @throws OrderStatusException
	 */
	void nullify(Order order,Employee operator)throws OrderStatusException;
	
	/**
	 * 变更订单的配货状态
	 * @param orderid
	 * @param operator
	 */
	void picking(String orderid, Employee operator);
	
	/**
	 * 变更订单为等待拦截状态
	 * 远程调用dip服务
	 * @param order
	 * @param operator
	 */
	void erpIntercept(Order order,Employee operator) throws OrderStatusException,RemoteBusinessException;
	
	/**
	 * 取消订单<br/>
	 * 订单状态改为相应的取消状态，增加处理日志<br/>
	 * 检查order.paymentList，将已支付金额退回暂存款，礼券则恢复，礼品卡金额退回对应的礼品卡，记录相应的日志<br/>
	 * 发送通知邮件
	 * 
	 * @param order
	 *            订单
	 * @param cancelStatus
	 *            取消状态
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单参数中取消状态与订单状态不匹配，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 * @throws PresentCardException
	 *             如果礼品卡使用错误，抛出此异常
	 * @throws PresentException
	 *             如果礼券使用错误，抛出此异常
	 * @throws ReturnOrderException 
	 */
	void cancel(Order order, Code cancelStatus, User operator)
			throws OrderStatusException, CustomerAccountException,
			PresentCardException, PresentException, ReturnOrderException;

	/**
	 * 拦截取消<br/>
	 * @param order
	 * 			订单
	 * @param operator
	 * 			操作人
	 * @throws OrderException
	 */
	void interceptCancel(Order order, User operator) throws OrderException;
	
	/**
	 * 更新订单的ERP状态
	 * 
	 * @param order
	 * @param erpStatus
	 */
	void updateErpStatus(Order order, Code erpStatus);
	
	
	/**
	 * 运单号不同则更新订单的运单号
	 * @param order
	 * @param deliveryCode
	 */
	void updateDeliveryCode(Order order,DeliveryCompany company,String deliveryCode) throws OrderStatusException;;

	/**
	 * 下传订单到中启<br/>
	 * 订单状态为等待配货 订单状态改为正在配货状态，标记订单以下传中启，增加处理日志
	 * 
	 * @param order
	 *            订单
	 * @throws OrderStatusException
	 *             如果订单不处于等待配货状态，抛出此异常
	 */
	void transportNewOrder(Order order) throws OrderStatusException;

	/**
	 * 订单发货<br/>
	 * 订单状态修改为已发出，记录订单处理日志<br/>
	 * 设置order.deliveryListPrice和order.deliverySalePrice为实际发货的总码洋、总实洋<br/>
	 * 检查order.paymentList，将缺货部分的已支付金额退回暂存款，礼券恢复，礼品卡金额退回对应的礼品卡，
	 * 设置order.paymentList[\d].deliveryMoney，记录相应的日志<br/>
	 * 发送通知邮件
	 * 
	 * @param order
	 *            订单（设置order.itemLIst[\d].deliveryQuantity属性为发货数量，不能小于0，不能全部为0）
	 * @param deliveryCompany
	 *            配送公司
	 * @param deliveryCode
	 *            运单号
	 * @param operator
	 *            操作人
	 * @throws OrderStatusException
	 *             如果订单不处于正在配货状态，抛出此异常
	 * @throws OrderDeliveryException
	 *             如果订单发货数量小于0或者全部为0，抛出此异常
	 * @throws CustomerAccountException
	 *             如果暂存款使用错误，抛出此异常
	 * @throws PresentCardException
	 *             如果礼品卡使用错误，抛出此异常
	 * @throws PresentException
	 *             如果礼券使用错误，抛出此异常
	 * @throws ReturnOrderException 
	 */
	void delivery(Order order, DeliveryCompany deliveryCompany,
			String deliveryCode, User operator) throws OrderStatusException,
			OrderDeliveryException, CustomerAccountException,
			PresentCardException, PresentException, ReturnOrderException;

	void transportSapOrder(Order order) throws OrderStatusException;
	
	/**
	 * 订单确认收货<br/>
	 * 设置receive.createTime为当前时间<br/>
	 * 修改receive.order.processStatus为交易成功状态，增加日志<br/>
	 * 
	 * @param receive
	 *            确认收货对象
	 * @throws OrderStatusException
	 *             如果订单不处于已发货状态，抛出此异常
	 */
	void receive(OrderReceive receive) throws OrderStatusException;

	/**
	 * 修改订单<br/>
	 * 修改完成后增加orderUpdateLog
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作人
	 */
	void update(Order order, User operator);
	
	/**
	 * 确认收货
	 * @param newOrder
	 * @param operator
	 */
	void updateForConfirmGotGoods(Order newOrder, User operator);

	/**
	 * 得到订单
	 * 
	 * @param id
	 * @return
	 */
	Order get(String id);

	/**
	 * 查询订单
	 * 
	 * @param parameters
	 *            查询参数
	 * @param sort
	 *            排序方式
	 * @param pagination
	 *            分页
	 * @return 当前页订单列表
	 */
	List<Order> find(Map<String, Object> parameters, short sort,
			Pagination pagination);

	/**
	 * 查询某用户需确认收货的订单
	 * 
	 * @param customer
	 *            用户
	 * @param beginDate
	 *            指定的下单时间之后，如果不传入，忽略该参数
	 */
	List<Order> findNeedConfirmReceiveOrder(Customer customer, Date beginDate);

	/**
	 * 查询指定的某个发货时间的还未确认收货的订单
	 * 
	 * @param startDeliveryTime
	 * @param endDeliveryTime
	 * @param pagination
	 * @return
	 */
	List<Order> findNeedConfirmReceiveOrder(Date startDeliveryTime,
			Date endDeliveryTime, Pagination pagination,boolean needOuterId);

	/**
	 * 查询指定的某个时间点的订单
	 * 
	 * @param startCreateTime
	 * @param endCreateTime
	 * @param pagination
	 * @return
	 */
	List<Order> findNeedInviteCommentOrder(Date startCreateTime,
			Date endCreateTime, Pagination pagination);

	/**
	 * 增加订单跟踪
	 * 
	 * @param orderTrack
	 *            订单跟踪
	 */
	void addTrack(OrderTrack orderTrack);

	/**
	 * 查询订单跟踪
	 * 
	 * @param parameters
	 *            查询参数：<br/>
	 *            type：类型<br/>
	 *            startTrackDate：开始的订单跟踪创建日期<br/>
	 *            endTrackDate：结束的订单跟踪创建日期<br/>
	 *            startOrderDate：开始的下单日期<br/>
	 *            endOrderDate：结束的下单日期<br/>
	 *            startDeliveryDate：开始的发货日期<br/>
	 *            endDeliveryDate：结束的发货日期<br/>
	 *            employee：创建人<br/>
	 *            orderId：订单号<br/>
	 *            name：注册名<br/>
	 * @param sort
	 *            排序方式：<br/>
	 *            0订单跟踪创建时间降序 <br/>
	 *            1订单跟踪创建时间升序 <br/>
	 *            2订单下单时间降序 <br/>
	 *            3订单下单时间升序 <br/>
	 *            4订单发货时间降序<br/>
	 *            5订单发货时间升序<br/>
	 * 
	 * @param pagination
	 * @return
	 */
	List<OrderTrack> findTrack(Map<String, Object> parameters, short sort,
			Pagination pagination);

	/**
	 * 通过物流api获得订单的物流跟踪信息<br/>
	 * 当订单满足以下条件才会调用api获取物流信息，不满以下条件不做任何操作：<br/>
	 * 订单已发货，且发货时间不早于当前时间的两个月之前、不晚于昨天，订单对应的配送公司deliveryCompany.code不为空<br/>
	 * 如果获得物流跟踪信息成功，设置order.logisticsList
	 * 
	 * @param order
	 */
	void fetchLogisticsInfo(Order order);

	/**
	 * 取得未审核的订单总数
	 * 
	 * @return
	 */
	long getCountOfUnaudited();

	
	List<Order> findByChannelIdAndOuterId(long channelId, String outerId);

	/**
	 * 该用户是否存在该订单号
	 * 
	 * @param customer
	 * @param orderId
	 * @return
	 */
	boolean isExistByCustomerAndOrderId(Customer customer, String orderId);

	/**
	 * 得到订单项
	 * 
	 * @param id
	 * @return
	 */
	OrderItem getOrderItem(long id);
	
	/**
	 * 查询用户最近下的订单
	 * @param customer 用户
	 * @param size 数量
	 * @return
	 */
	List<Order> findByCustomer(Customer customer, int size);
	
	
	/**
	 * 查询订单的分包信息
	 * @param order
	 * @return
	 */
	List<OrderDeliverySplit> findDeliverySplit(Order order);
	
	/**
	 * 是否存在 外部订单号
	 * @param outerId outerid
	 * @return
	 */
	boolean isExistOuterId(String outerId);
	
	List<OrderItem> findOutOfStockItemList(Order order);

    
     
    List<Order> findForDeliveryCompany(Map<String, Object> parameters,Short sort,Pagination pagination);
    
    /**
     * 买家订单汇总查询
     * @param shop
     * @param param
     * @return
     */
    List<Object[]> findOrderCollectByShop(Shop shop,Map<String, Object> param);

    /**
     * 复制订单
     * @param user
     * @param oldOrder
     * @return
     * @throws Exception
     */
    Order copyOrder(User user, String oldOrder) throws OrderCloneException;
    
    /**
     * 保存订单扩展属性
     * @param orderExtend
     */
    void saveOrderExtend(OrderExtend orderExtend);
    
    List<Order> findOrderWithMeta(Map<String, Object> parameters, Short sort,@Page Pagination pagination);
    
    /**
     * 促销中单个用户当天已购买的数量
     * @param customer
     * @param ps
     * @return
     */
    int getPurchaseQuantityToday(Customer customer,ProductSale ps);
    
    /**
     * 促销商品当天已购买量
     * @param ps
     * @return
     */
    int getPurchaseQuantityToday(ProductSale ps);

    /**
     * 批量发货
     * @param packages 发货清单
     * @param operator
     * @throws BatchDeliveryException
     */
    void batchDelivery(List<OrderPackage> packages, Employee operator) throws BatchDeliveryException;
    
    /**
     * 取消订单预售状态
     * @param order 订单
     * @param employee 操作人
     * @throws OrderPresellException
     */
    void cancelPresell(Order order, Employee employee) throws OrderPresellException;
    
    /**
     * 查询订单状态预售状态的id
     * @return List<String>
     * @throws OrderPresellException
     */
    List<String> findBookingOrderId(int start,int size) throws OrderPresellException;
    
    /**
	   * 为订单项增加下单时的商品库存和占用量
	   * 使用商品在订单DC的库存和占用
	   * 如果商品是预售商品，则库存使用虚拟库存
	   * @param orderItem
	   */
    void increaseStockLockQuantity(OrderItem item);
    
    void saveOrderPackage(OrderPackges orderPackges);
    
    OrderPackges getOrderPackges(String orderid);
  
    void  initOrderStock(Order order)throws OrderStockException, OrderDcMatchException ;
    
    List<String> getNeedInitOrders(Pagination pagination);
    
    void updateStock(Order order);
    
    void initProductSaleStockVo(Order order) throws RemoteBusinessException;
    
    /**
     * 重新转单时，扩展订单信息
     * 如果操作人是业务人员则增加订单扩展信息，标记此订单为渠道可再次转单
	 * 如果操作人是系统，则不记录扩展信息。
     * @param order
     * @param employee
     */
	void extendOrder(Order order, Employee employee) throws OrderStatusException;
	
	/**
	 * 审核承运商，修改订单承运商信息
	 * @param order
	 * @param operator
	 * @throws OrderStatusException
	 */
	void auditShipperInfo(Order order, Employee operator) throws OrderStatusException;

}
