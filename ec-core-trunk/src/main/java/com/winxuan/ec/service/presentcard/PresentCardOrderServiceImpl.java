/*
 * @(#)PresentCardOrderServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.presentcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.presentcard.PresentCardUtils;
import com.winxuan.ec.util.Constant;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-9-7
 */
@Service("presentCardOrderService")
public class PresentCardOrderServiceImpl implements PresentCardOrderService{


	
	private static final String SUBJECT = "文轩网提醒：礼品卡已经发放";
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PresentCardService presentCardService;
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	private Order getOrderWithVerify(String orderId) throws PresentCardException {
		Order order = orderService.get(orderId);
		if(order == null){
			throw new PresentCardException(orderId + "不存在!");
		}
		return order;
	}
	
	@Override
	public void sendPresentCard(String orderId, Employee operator) throws PresentCardException {
		Order order = getOrderWithVerify(orderId);
		if(Code.ORDER_PROCESS_STATUS_WAITING_PICKING.equals(order.getProcessStatus().getId())){
			order.setProcessStatus(new Code(Code.ORDER_PROCESS_STATUS_PICKING));
		}
		if (!Code.ORDER_PROCESS_STATUS_PICKING.equals(order.getProcessStatus().getId())) {
			throw new PresentCardException(orderId + ",状态为:"
					+ order.getProcessStatus().getName() + ",不是正在配货");
		}
		sendPresentCard(order,operator);
		try {
			orderService.delivery(order, null, null, operator);
		} catch (Exception e) {
			throw new PresentCardException(e.getMessage(),e);
		} 	
		
	}

	@Override
	public void logoutReissue(String orderId, Employee operator) throws PresentCardException {
		Order order = getOrderWithVerify(orderId);
		if(!order.isDeliveried()){
			throw new PresentCardException(orderId + ",状态为:"+order.getProcessStatus().getName()+",不是已发货");
		}
		presentCardService.logout(findByOrderId(orderId), operator);
		sendPresentCard(order,operator);
		
	}

	@Override
	public void activateByMember(String orderId, String sign) throws PresentCardException {
		Order order = getOrderWithVerify(orderId);
		if (DigestUtils.md5Hex(orderId + order.getCustomer().getId()).equals(sign)) {
			presentCardService.activate(findByOrderIdForActivate(orderId), null);
		}else{
			throw new PresentCardException("签名错误");
		}
	}

	@Override
	public void activateByEmployee(String orderId, Employee operator)	throws PresentCardException {
		Order order = getOrderWithVerify(orderId);
		if(!order.isDeliveried()){
			throw new PresentCardException(orderId + ",状态为:"+order.getProcessStatus().getName()+",不是已发货");
		}
		presentCardService.activate(findByOrderIdForActivate(orderId), operator);
	}
	
	@Override
	public void activateByEmployeeNew(String orderId, List<String> presentCardIds, Employee operator) throws PresentCardException {
		List<PresentCard> presentCardsList = new ArrayList<PresentCard>();
		Order order = getOrderWithVerify(orderId);
		if(!order.isDeliveried()){
			throw new PresentCardException(orderId + ",状态为:"+order.getProcessStatus().getName()+",不是已发货");
		}
		if(presentCardIds == null || presentCardIds.isEmpty() || presentCardIds.size() == 0){
			throw new PresentCardException("未选中任何待激活的礼品卡！");
		}
		for (String presentCardId : presentCardIds){
			PresentCard presentCard = presentCardService.get(presentCardId);
			presentCardsList.add(presentCard);
		}
		presentCardService.activate(presentCardsList, operator);
	}
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> findByOrderId(String orderId){
		Map<String, Object> parameters= new HashMap<String, Object>();
		parameters.put("statusList", new Long[]{Code.PRESENT_CARD_STATUS_SENT,Code.PRESENT_CARD_STATUS_ACTIVATE,
				Code.PRESENT_CARD_STATUS_OFF,Code.PRESENT_CARD_STATUS_USE});
		parameters.put("orderId", orderId);
		return presentCardService.find(parameters, null);
	}
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> findByOrderIdForActivate(String orderId){
		Map<String, Object> parameters= new HashMap<String, Object>();
		parameters.put("status", Code.PRESENT_CARD_STATUS_SENT);
		parameters.put("orderId", orderId);
		return presentCardService.find(parameters, null);
	}
	
	private void sendPresentCard(Order order, Employee operator) throws PresentCardException{
		int electronticCount = 0;
		int physicalCount = 0;
		List<OrderItem> electronicItemList = new ArrayList<OrderItem>();
		List<OrderItem> physicalItemList = new ArrayList<OrderItem>();
		for(OrderItem orderItem : order.getItemList()){
			Product product = orderItem.getProductSale().getProduct();
			if(PresentCardUtils.electronicCategory.equals(product.getCategory().getId())){
				electronicItemList.add(orderItem);
				electronticCount = electronticCount  + orderItem.getPurchaseQuantity();
			}else if(PresentCardUtils.physicalCategory.equals(product.getCategory().getId())){
				physicalItemList.add(orderItem);
				physicalCount = physicalCount  + orderItem.getPurchaseQuantity();
			}
			orderItem.setDeliveryQuantity(orderItem.getPurchaseQuantity());
		}
		if(electronicItemList.isEmpty() && physicalItemList.isEmpty()){
			throw  new PresentCardException(order.getId() +"不包含礼品卡商品"); 
		}
		if(!electronicItemList.isEmpty()){
			List<PresentCard> electronicFindList= presentCardService.findForSending(electronticCount,Code.PRESENT_CARD_TYPE_ELECTRONIC);
			List<PresentCard> result = new ArrayList<PresentCard>(); 
			List<String> passwords = new ArrayList<String>(); 
			for(OrderItem oi : electronicItemList){
				Product product = oi.getProductSale().getProduct();
				for(int i = 0 ; i < oi.getPurchaseQuantity(); i++){
					presentCardService.send(electronicFindList.get(0), product.getListPrice(), order,operator);
					result.add(electronicFindList.get(0));
					passwords.add(electronicFindList.get(0).getProclaimPassword());
					electronicFindList.remove(0);
				}
			}
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("presentCards", result);
			model.put("passwords", passwords);
			order.getCustomer().getDisplayName();
			model.put("order", order);
			model.put("sign", DigestUtils.md5Hex(order.getId() + order.getCustomer().getId()));
			
			mailService.sendMail(order.getConsignee().getEmail(), SUBJECT, Constant.MAIL_PRESENTCARD_SENDED, model);
		}
		
		if(!physicalItemList.isEmpty()){
			List<PresentCard> physicalFindList= presentCardService.findForSending(physicalCount,Code.PRESENT_CARD_TYPE_PHYSICAL);
			for(OrderItem oi : physicalItemList){
				Product product = oi.getProductSale().getProduct();
				for(int i = 0 ; i < oi.getPurchaseQuantity(); i++){
					presentCardService.send(physicalFindList.get(0), product.getListPrice(), order,operator);
					physicalFindList.remove(0);
				}
			}
		}
	
	
		
	}
}
