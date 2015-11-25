/*
 * @(#)PresentService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.present;


import java.math.BigDecimal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.winxuan.ec.exception.CustomerPointsException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.PresentExchangeException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.present.PresentExchange;
import com.winxuan.ec.model.present.PresentLog;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.pagination.Pagination;

/**
 * 礼券业务接口
 * @author  HideHai
 * @version 1.0,2011-8-30
 */
public interface  PresentService {


	/**
	 * 获取礼券
	 * @param id 礼券编号
	 * @return
	 */
	Present get(Long id);

	/**
	 * 更新礼券
	 * @param present
	 */
	void update(Present present);

	/**
	 *  获取礼券批次
	 * @param batchId
	 * @return
	 */
	PresentBatch getBatch(Long batchId);

	/**
	 * 创建礼券批次 关联批次的创建人
	 * @param presentBatch
	 * @param operator 创建者
	 * @return
	 */
	void createBatch(PresentBatch presentBatch,Employee operator);

	/**
	 * 更新礼券批次
	 * @param presentBatch
	 */
	void updateBatch(PresentBatch presentBatch);
	
	/**
	 * 审核礼券批次，如果批次是有数量限制,则根据礼券批次的设置生成对等数量的礼券
	 * 如果礼券没有限制生成数量，则不生成礼券.
	 * 通用券批次或者系统分发的礼券批次属于通用无限制礼券，在此步骤都不会生成礼券
	 * @param presentBatch
	 * @param operator
	 */
	void verifyBatch(PresentBatch presentBatch,Employee operator);
	
	/**
	 * 激活礼券
	 * 如果礼券所属是通用券，则生成一张新的礼券，激活发放给用户
	 * 如果礼券所属不是通用券，则根据礼券批次设置礼券的有效期，激活发放给用户
	 * @param code 礼券的激活码
	 * @param user 激活的用户
	 * @return
	 */
	Present  activePresent(String code,Customer customer) throws PresentException;
	
	/**
	 * 使用礼券
	 * 验证：
	 * 礼券是否属于当前订单.和用户
	 * 礼券是否在有效期内
	 * 礼券状态为已激活
	 * 礼券的使用类型是否一致，包括(是否参与活动/是否参与联盟)
	 * 更新礼券的使用金额为传入的支付金额，如果大于礼券面值，设为礼券面值
	 * 添加礼券的使用日志
	 * @param present
	 * @param order
	 * @param realPay
	 */
	void usePresent(Present present,Order order,BigDecimal realPay) throws PresentException;
	
	
	/**
	 * 支付礼券
	 * 礼券状态为 已使用
	 * 更改订单的礼券状态为 已支付
	 * 更改金额为订单实际使用的金额
	 * 记录礼券日志
	 * @param order
	 * @throws OrderStatusException
	 * @throws PresentException
	 */
	void payPresent(Present present,Order order,BigDecimal realPay) throws PresentException;
	
	/**
	 * 注销礼券
	 * 验证礼券状态为 已分发
	 * 更改礼券状态为 已注销
	 * 添加礼券日志
	 * @param present
	 * @param operator
	 * @throws PresentException 礼券状态不正确抛出
	 */
	void cancelPresent(Present present,Employee operator) throws PresentException;
	
	/**
	 * 补发礼券
	 * 更改礼券状态为已激活
	 * 保证礼券有效期为至少10天，有效期不足则后延有效期
	 * @param present
	 * @param order
	 * @throws PresentException
	 */
	void reissuePresent(Present present,Order order) throws PresentException;
	
	/**
	 *  退换货差额礼券补发
	 *  生成指定批次以及传入面值的礼券一张
	 *  激活礼券，关联用户，增加礼券日志
	 *  给客户账户发送通知邮件
	 * @param customer	需要补发的用户
	 * @param value		需要补发的金额
	 * @throws PresentException
	 */
	void reissuePresent(Customer customer,BigDecimal value) throws PresentException;
	
	/**
	 * 查找用户可以使用礼券列表
	 * 礼券状态为已激活
	 * 礼券未失效
	 * @param customer
	 * @return
	 */
	List<Present> findEffectivePresentByCustomer(Customer customer);
	
	/**
	 * 查找用户所有的礼券列表
	 * @param customer
	 * @return
	 */
	List<Present> findPresentByCustomer(Customer customer);
	
	/**
	 * 查找礼券
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<Present> find(Map<String, Object> parameters,Pagination pagination);
	
	/**
	 * 查询订单中使用的礼券
	 * @param order
	 * @return
	 */
	List<Present> findByOrder(Order order);
	
	/**
	 * 查找礼券批次
	 * @param parameters
	 * @param pagination
	 * @return
	 */
	List<PresentBatch> findBatch(Map<String, Object> parameters,Pagination pagination);
	/**
	 * 导出礼券，设置礼券批次可导出数量为0
	 * 生成excel文件并存放到附件目录
	 * 返回附件的路径
	 * @param presentBatch
	 * @return 
	 * @return  返回生成的礼券集合
	 */
	Future<String> generatePresent(PresentBatch presentBatch,Employee operator) throws PresentException ;
	
	/**
	 * 给用户账户发送/邮件发送礼券，更新批次的分发数量
	 * 
	 * 给用户账户发送礼券，并发送通知邮件
	 * 设置关联的用户，激活礼券
	 * 替换右键内容模板中的${present.generalCode}为礼券的激活码,调用邮件接口发送邮件.
	 * 用户领取礼券的数量不能超过批次设定的单个用户领取礼券数量的阀值
	 * 如果数量限制阀值或者单个用户领取数量阀值为0，则不检测
	 * 发送完成后更新礼券批次创建数量
	 * @param presentBatch 礼券批次对象
	 * @param customers	用户集合
	 * @param emailTemplate	邮件内容模板
	 */
	void sendPresent4Customer(PresentBatch presentBatch, List<Customer> customers,String emailTemplate) throws PresentException ;
	
	/**
	 * 非通用券批次通过邮件发送礼券
	 * 替换右键内容模板中的${present.generalCode}为礼券的激活码,调用邮件接口发送邮件.
	 * 发送的数量不可超过批次的数量限制阀值,
	 * @param presentBatch
	 * @param emails	邮件地址集合
	 * @param emailTemplate	邮件内容模板
	 */
	void sendPresent4Email(PresentBatch presentBatch, List<String> emails,String emailTemplate) throws PresentException ;
	
	/**
	 * 给用户账户发送礼券，更新批次的分发数量
	 * 
	 * 给用户账户发送礼券，并发送通知邮件
	 * 设置关联的用户，激活礼券
	 * 用户领取礼券的数量不能超过批次设定的单个用户领取礼券数量的阀值
	 * 如果数量限制阀值或者单个用户领取数量阀值为0，则不检测
	 * 发送完成后更新礼券批次创建数量
	 * @param presentBatch
	 * @param customers
	 * @param origin 礼券来源，不设置默认:文轩网赠送
	 * @param order 礼券来源订单，只有买赠券活动才设置
	 */
	void sendPresent4Customer(PresentBatch presentBatch,List<Customer> customers , Code origin ,Order order)throws PresentException;
	
	/**
	 * 取得需要审核的礼券批次数量
	 * @return
	 */
	long findNeedVerifyCount();
	
	/**
	 * 根据PresentBatch和 state查找数量
	 * @param batchId
	 * @param stateId
	 * @return
	 */
	long findPresentCount(PresentBatch presentBatch,Long state);
	
	/**
	 * 查找礼券日志
	 * @return
	 */
	List<PresentLog> findPresentLogByOrderId(String orderId);
	
	/**
	 * 查询批次中所有的礼券面值
	 * @return
	 */
	List<BigDecimal> findValue();
	
	/**
	 * 查找礼券批次
	 * @param parameters
 	 * @return
	 */
	List<PresentBatch> findBatch(Map<String, Object> parameters);
	
	/**
	 * 客户使用积分兑换礼券
	 * @param customer 用户
	 * @param value 兑换礼券的面值
	 * @param points 使用的积分数量
	 * @return
	 * @throws PresentExchangeException 如果兑换面值和礼券数量不匹配，抛出此异常
	 * @throws CustomerPointsException 如果用户积分不足，抛出此异常
	 */
	Present exchange(Customer customer, BigDecimal value, int points)
			throws PresentExchangeException, CustomerPointsException;
	
	/**
	 * 查询所有可兑换的礼券
	 * @return
	 */
	List<PresentExchange> findExchange();
	
	
	/**
	 * 查询指定id的可兑换礼券
	 * @param id
	 * @return
	 */
	PresentExchange findExchange(Long id);
	
	/**
	 * 返回当前用户和购物车项支持的礼券列表
	 * @param customer
	 * @param shoppingcart
	 * @return
	 */
	List<Present> findEffectivePresentByCustomerAndShoppingCart(Customer customer,List<ShoppingcartItem> shoppingcartItems);
	
	long isExistedByCode(String code);
}

