/*
 * @(#)PresentCardOrder.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.presentcard;

import java.util.List;

import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.user.Employee;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */
public interface PresentCardOrderService {
	
	
	/**
	 * 发卡
	 * @param orderId
	 * @param operator
	 * @throws PresentCardException
	 */
	void sendPresentCard(String orderId, Employee operator)throws PresentCardException;
	
	
	/**
	 * 注销补发
	 * @param orderId
	 * @param operator
	 * @throws PresentCardException
	 */
	void logoutReissue(String orderId,Employee operator) throws PresentCardException;
	
	/**
	 * 激活礼品卡订单
	 * @param orderId
	 * @param sign
	 * @throws PresentCardOrderException
	 */
	void activateByMember(String orderId,String sign) throws PresentCardException;
	
	
	/**
	 * 激活订单
	 * 		更新该订单状态为激活，创建订单状态日志, 更新对应的卡状态为激活，创建卡状态日志
	 *      礼品卡订单礼品卡类型为电子卡，激活人员为空
	 * @param orderId
	 * @param operator
	 * @throws PresentCardException
	 */
	void activateByEmployee(String orderId,Employee operator) throws PresentCardException;
	
	/**
	 * 激活指定订单下的某些特定的礼品卡,新添加
	 */
	void activateByEmployeeNew(String orderId, List<String> presentCardIds, Employee operator) throws PresentCardException;
	
	
	List<PresentCard> findByOrderId(String orderId);
	

}
