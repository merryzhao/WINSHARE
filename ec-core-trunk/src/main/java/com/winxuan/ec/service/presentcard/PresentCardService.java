/*
o * @(#)PresentCard.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.presentcard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.presentcard.PresentCardDealLog;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */
public interface PresentCardService {
	
	PresentCard get(String id);
	
	void update(PresentCard presentCard) ;
	
	/**
	 * 创建礼品卡
	 * 
	 * @param type 礼品卡类型 
	 * @param quantity 创建数量
	 * @param operator 操作人
	 * @return 礼品卡列表
	 */
	List<PresentCard> create(Long type, int quantity, Employee operator);
	
	
	/**
	 * 查询礼品卡
	 * @param params 查询参数 
	 * 		(卡生成的时间范围，卡状态(复选)，卡类型，卡号，面值)
	 * @return 礼品卡列表
	 */
	List<PresentCard> find(Map<String,Object> parameters, Pagination pagination) ;
	
	/**
	 * 查找可以交付印刷的礼品卡 (卡状态为"新建"，且是实物卡)
	 * @param quantity 数量
	 * @return 礼品卡列表
	 */
	List<PresentCard> findForPrinting(int quantity) throws PresentCardException;
	
	/**
	 * 印刷卡
	 * 		更新卡状态为印刷，创建卡状态日志
	 * @param presentCardList 礼品卡列表
	 * @return 礼品卡列表
	 * @throws PresentCardException 新建状态的实物卡数量小于输入数量
	 */
	List<PresentCard> print(String[] idArray, Employee employee) throws PresentCardException;
	
	/**
	 * 卡入库
	 * 		更新对应卡状态为入库，创建卡状态日志
	 * @param presentCards 礼品卡列表
	 * @return 礼品卡列表
	 * @throws PresentCardException 礼品卡状态不符
	 */
	List<PresentCard> store(String[] idArray, Employee employee) throws PresentCardException;
	
	/**
	 * 注销礼品卡
	 * 		更新卡状态为注销，创建卡状态日志
	 * @param presentCard
	 */
	List<PresentCard> logout(List<PresentCard> presentCardList, Employee employee) throws PresentCardException;
	
	
	
	
	/**
	 * 查找可以发出的礼品卡
	 * 如果是电子卡，则查找状态为新建的电子卡，如
	 * 果是实物卡，则查找状态为入库的实物卡，匹配出对应数量的卡片，返回匹配结果
	 * @param quantity 发卡数量
	 * @param type 礼品卡类型 
	 * @return  list 礼品卡列表
	 * @throws PresentCardException 备用卡片不足
	 */
	List<PresentCard> findForSending(int quantity, Long type) throws PresentCardException;
	

	/**
	 * 前台用户修改礼品卡密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @param customer
	 * @throws PresentCardException
	 */
	void modifyPassword(String id, String oldPassword, String newPassword, Customer customer) throws PresentCardException;
	
	/**
	 * 后台管理员修改礼品卡密码
	 * @param id
	 * @param newPassword
	 * @param employee
	 */
	void modifyPassword(String id, String newPassword, User operator) throws PresentCardException;
	
	/**
	 * 礼品卡延期
	 * @param id
	 * @param newDate
	 * @param operator
	 */
	void delay(String id, Date newDate, User operator) throws PresentCardException;
	
	/**
	 * 礼品卡登陆
	 * @param id
	 * @param password
	 * @return
	 * @throws PresentCardException 验证密码未通过
	 */
	PresentCard login(String id, String password) throws PresentCardException;
	
	
	
	/**
	 * 下单使用礼品卡
	 * @param presentCard
	 * @param order
	 * @param money 金额为正表示退款到礼品卡，金额为负表示使用礼品卡
	 * @throws PresentCardException
	 */
	void use(PresentCard presentCard,Order order,BigDecimal money) throws PresentCardException;
	
	
	/**
	 * 发卡
	 * @param presentCard
	 * @param denomination
	 * @param order
	 * @param operator
	 * @throws PresentCardException
	 */
	void send(PresentCard presentCard, BigDecimal denomination, Order order, Employee operator)
			throws PresentCardException;
	
	/**
	 * 激活
	 * @param presentCardList
	 * @param operator 客户激活时可以为空
	 * @throws PresentCardException
	 */
	void activate(List<PresentCard> presentCardList, Employee operator) throws PresentCardException;
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	List<PresentCard> findEffectivePresentCardByCustomer(Long customerId,Pagination pagination);
	
	/**
	 * 
	 * @param id
	 * @param password
	 * @param customer
	 * @throws PresentCardException
	 */
	PresentCard get(String id, String password, Long customerId) throws PresentCardException;
	
	
	/**
	 * 分页查询礼品卡交易日志
	 * @param map
	 * @param pagination
	 * @return
	 */
	List<PresentCardDealLog> findDealLogList(Map<String,Object> map,Pagination pagination);
	
	void bind(String id,String password, Long customerId) throws PresentCardException;
	
	void unBind(String id, Long customerId) throws PresentCardException;
	
	/**
	 * 取得该用户下有效的礼品卡余额合计
	 * @param customer
	 * @return
	 */
	BigDecimal getTotalBalanceByCustomer(Customer customer);
}
