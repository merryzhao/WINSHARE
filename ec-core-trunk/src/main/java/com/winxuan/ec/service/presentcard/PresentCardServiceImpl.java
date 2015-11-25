/*
 * @(#)PresentCardServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.presentcard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.PresentCardDao;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.presentcard.PresentCardDealLog;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.support.presentcard.PresentCardUtils;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * @author  qiaoyao
 * @version 1.0,2011-8-29
 */

@Service("presentCardService")
@Transactional(rollbackFor = Exception.class)
public class PresentCardServiceImpl implements PresentCardService,Serializable{
	
	private static final long serialVersionUID = -6608437824008822372L;

	private static final String STATUS_MESSAGE =",状态为: ";
	
	@InjectDao
	private PresentCardDao presentCardDao;
	
	@Autowired
	private CodeService codeService;
	
	@Override
	public void update(PresentCard presentCard) {
		presentCard.setVerifyCode(presentCard.generateVerifyCode());
		presentCardDao.update(presentCard);
	}
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PresentCard get(String id) {
		return presentCardDao.get(id);
	}

	@Override
	public List<PresentCard> create(Long type, int quantity, Employee operator) {
		List<PresentCard> list = new ArrayList<PresentCard>();
		for (int i = 0; i < quantity; i++) {
			PresentCard presentCard = new PresentCard();
			presentCard.setPassword(PresentCardUtils.generatePassword());
			presentCard.setCreateDate(new Date());
			presentCard.setType(codeService.get(type));
			presentCard.setStatus(codeService.get(Code.PRESENT_CARD_STATUS_NEW));
			presentCard.setVerifyCode("");
			presentCardDao.save(presentCard);
			//由于最开始id为空，所以在保存之后再更新一下verifyCode
			presentCard.setVerifyCode(PresentCardUtils.generateVerifyCode(presentCard));
			//添加状态日志
			presentCard.addStatusLog(operator);
			list.add(presentCard);
		}
		return list;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> findForPrinting(int quantity) throws PresentCardException{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", Code.PRESENT_CARD_TYPE_PHYSICAL); 
		parameters.put("status", Code.PRESENT_CARD_STATUS_NEW);
		List<PresentCard> list =  presentCardDao.find(parameters, new Pagination(quantity), (short)1);
		if(list.size() < quantity){
			throw new PresentCardException("实物礼品卡不足打印数量");
		}
		return list;
	}

	@Override
	public List<PresentCard> print(String[] idArray, Employee employee) throws PresentCardException {
		List<PresentCard> list = new ArrayList<PresentCard>();
		for(String id : idArray){
			PresentCard presentCard = getWithVerify(id);
			if(!Code.PRESENT_CARD_TYPE_PHYSICAL.equals(presentCard.getType().getId())){
				throw new PresentCardException(presentCard.getId() + ":不是实物卡");
			}
			if (!Code.PRESENT_CARD_STATUS_NEW.equals(presentCard.getStatus().getId())) {
				throw new PresentCardException(presentCard.getId()	+ STATUS_MESSAGE + presentCard.getStatus().getName() + "已不是新建");
			}
			presentCard.setStatus(codeService.get(Code.PRESENT_CARD_STATUS_PRINT));
			presentCard.addStatusLog(employee);
			update(presentCard);
			list.add(presentCard);
		}
		return list;
	}

	@Override
	public List<PresentCard> store(String[] idArray, Employee employee) throws PresentCardException {
		List<PresentCard> list = new ArrayList<PresentCard>();
		for(String id : idArray){
			PresentCard presentCard = getWithVerify(id);
			if (!Code.PRESENT_CARD_STATUS_PRINT.equals(presentCard.getStatus().getId())) {
				throw new PresentCardException(presentCard.getId()
						+ STATUS_MESSAGE + presentCard.getStatus().getName() + "不是印刷");
			}
			presentCard.setStatus(codeService.get(Code.PRESENT_CARD_STATUS_STORAGE));
			presentCard.addStatusLog(employee);
			update(presentCard);
			list.add(presentCard);
		}
		return list;
	}

	@Override
	public List<PresentCard> logout(List<PresentCard> presentCardList, Employee employee) throws PresentCardException {
		List<PresentCard> list = new ArrayList<PresentCard>();
		for (PresentCard presentCard : presentCardList) {
			if (presentCard.getBalance()!= null && !presentCard.getBalance().equals(presentCard.getDenomination())) {
				throw new PresentCardException(presentCard.getId() + ",已使用，不能注销");
			}
			presentCard.setStatus(codeService.get(Code.PRESENT_CARD_STATUS_OFF));
			presentCard.addStatusLog(employee);
			update(presentCard);
			list.add(presentCard);
		}
		return list;
		
		
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> findForSending(int quantity, Long type)
			throws PresentCardException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("type", type);
		if (Code.PRESENT_CARD_TYPE_ELECTRONIC.equals(type)) {
			parameters.put("status", Code.PRESENT_CARD_STATUS_NEW);
		} else if (Code.PRESENT_CARD_TYPE_PHYSICAL.equals(type)) {
			parameters.put("status", Code.PRESENT_CARD_STATUS_STORAGE);
		} else {
			throw new PresentCardException("未知的卡片类型");
		}
		List<PresentCard> list = presentCardDao.find(parameters,new Pagination(quantity), (short) 1);
		if(list != null && quantity == list.size()){
			return list;
		}else{
			throw new PresentCardException("卡片数量不足");
		}
	}


	@Override
	public void modifyPassword(String id, String oldPassword,
			String newPassword, Customer customer) throws PresentCardException {
		get(id,oldPassword);
		modifyPassword(id, newPassword, customer);
	}

	@Override
	public void modifyPassword(String id, String newPassword, User operator) throws PresentCardException{
		PresentCard presentCard = getWithVerify(id);
		byte[] old = presentCard.getPassword();
		presentCard.setPassword(PresentCardUtils.generatePassword(newPassword));
		presentCard.setLatestModifyPasswordTime(new Date());
		update(presentCard);
		presentCard.addModifyPasswordLog(operator, old);
	}

	@Override
	public PresentCard login(String id, String password) throws PresentCardException {
		return get(id, password);
	}


	@Override
	public void delay(String id, Date newDate, User operator) throws PresentCardException  {
		PresentCard presentCard = getWithVerify(id);
		if(presentCard.getEndDate().compareTo(newDate) >= 0){
			throw new PresentCardException("新设置有效期不能小于当前有效期");
		}
		presentCard.setEndDate(newDate);
		update(presentCard);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> find(Map<String, Object> parameters,
			Pagination pagination) {
		return presentCardDao.find(parameters, pagination, (short)1);
	}


	@Override
	public void send(PresentCard presentCard, BigDecimal denomination,
			Order order, Employee operator) throws PresentCardException {
		if(presentCard == null){
			throw new PresentCardException("礼品卡不存在");
		}
		if (Code.PRESENT_CARD_TYPE_ELECTRONIC.equals(presentCard.getType().getId())) {
			if(!Code.PRESENT_CARD_STATUS_NEW.equals(presentCard.getStatus().getId())){
				throw new PresentCardException("电子卡：" + presentCard.getId()
						+ STATUS_MESSAGE + presentCard.getStatus().getName() + "不是新建");
			}
		}else if (Code.PRESENT_CARD_TYPE_PHYSICAL.equals(presentCard.getType().getId())){
			if(!Code.PRESENT_CARD_STATUS_STORAGE.equals(presentCard.getStatus().getId())){
				throw new PresentCardException(presentCard.getId()
						+ STATUS_MESSAGE + presentCard.getStatus().getName() + "不是入库");
			}
		}else{
			throw new PresentCardException("未知的卡片类型");
		}
		presentCard.setDenomination(denomination);
		presentCard.setBalance(presentCard.getDenomination());
		presentCard.setStatus(new Code(Code.PRESENT_CARD_STATUS_SENT));
		presentCard.setOrder(order);
		presentCard.addStatusLog(operator);
		presentCard.addDealLog(operator, denomination, order);
		update(presentCard);
		
	}
	

	@Override
	public void use(PresentCard presentCard, Order order, BigDecimal money)
			throws PresentCardException {
		if(money.compareTo(BigDecimal.ZERO) < 0){
			if(!presentCard.canUse(order.getCustomer().getId())){
				throw new PresentCardException(presentCard.getId() +": 不能使用");
			}
			BigDecimal balance = presentCard.getBalance().add(money);
			if( balance.compareTo(BigDecimal.ZERO) < 0  ){
				throw new PresentCardException(presentCard.getId() +": 余额不足");
			}
			if (!Code.PRESENT_CARD_STATUS_USE.equals(presentCard.getStatus().getId())){
				presentCard.setStatus(new Code(Code.PRESENT_CARD_STATUS_USE));
				presentCard.addStatusLog(order.getCustomer());
			}
				
		}
		presentCard.setBalance(presentCard.getBalance().add(money));
		presentCard.addDealLog(order.getCustomer(), money, order);
		update(presentCard);
	}


	@Override
	public void activate(List<PresentCard> presentCardList, Employee operator)
			throws PresentCardException {
		for(PresentCard presentCard : presentCardList){
			if (Code.PRESENT_CARD_STATUS_ACTIVATE.equals(presentCard.getStatus().getId())){
				continue;
			}
			if (!Code.PRESENT_CARD_STATUS_SENT.equals(presentCard.getStatus().getId())){
				throw new PresentCardException(presentCard.getId()+ STATUS_MESSAGE + presentCard.getStatus().getName() + "不是发出");
			}
			Calendar now = Calendar.getInstance();
			final int  period = 3;
			now.set(Calendar.YEAR, now.get(Calendar.YEAR) + period);
			presentCard.setStatus(new Code(Code.PRESENT_CARD_STATUS_ACTIVATE));
			presentCard.setEndDate(now.getTime());
			update(presentCard);
		}
		
	}
	
	private PresentCard get(String id, String password) throws PresentCardException {
		PresentCard presentCard = getWithVerify(id);
		if (!presentCard.getProclaimPassword().equalsIgnoreCase(password)) {
			throw new PresentCardException("密码输入错误");
		}
		return presentCard;
	}
	

	private PresentCard getWithVerify(String id) throws PresentCardException {
		PresentCard presentCard = get(id);
		if (presentCard == null) {
			throw new PresentCardException(id + "输入错误!");
		}
		return presentCard;
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentCard> findEffectivePresentCardByCustomer(
			Long customerId,Pagination pagination) {
		Map<String, Object> parameters= new HashMap<String, Object>();
		parameters.put("statusList",new Long[]{Code.PRESENT_CARD_STATUS_ACTIVATE,Code.PRESENT_CARD_STATUS_USE});
		parameters.put("customerId", customerId);
		parameters.put("balanceBegin", BigDecimal.ZERO);
		return presentCardDao.find(parameters, pagination, (short)2);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PresentCard get(String id, String password, Long customerId) throws PresentCardException {
		PresentCard presentCard = getWithVerify(id);
		if(presentCard.canUse(customerId) ){
			if(presentCard.getCustomer() != null ){
				return presentCard;
			}else if(presentCard.getProclaimPassword().equalsIgnoreCase(password)){
				return presentCard;
			}
			throw new PresentCardException(id+":密码错误");
		}else{
			throw new PresentCardException(id+":不能使用");
		}
	}


	@Override
	public void bind(String id, String password, Long customerId)
			throws PresentCardException {
		PresentCard presentCard = get(id, password);
		if(presentCard.getCustomer() != null && !presentCard.getCustomer().getId().equals(customerId)){
			throw new PresentCardException("绑定失败，该礼品卡已绑定");
		}	
		else{
			Long staus = presentCard.getStatus().getId();
			if (Code.PRESENT_CARD_STATUS_ACTIVATE.equals(staus) || Code.PRESENT_CARD_STATUS_USE.equals(staus) ){
				presentCard.setCustomer(new Customer(customerId));
				presentCard.setBindTime(new Date());
			}else{
				throw new PresentCardException("绑定失败，该礼品卡状态不正确");
			}
			
		
		}
		update(presentCard);
	}

	@Override
	public void unBind(String id, Long customerId) throws PresentCardException {
		PresentCard presentCard = getWithVerify(id);
		if(presentCard.getCustomer() != null && !presentCard.getCustomer().getId().equals(customerId)){
			throw new PresentCardException("解除绑定失败，该礼品卡不属于当前用户");
		}else{
			presentCard.setCustomer(null);
		}
		update(presentCard);
	}

	@Override
	public List<PresentCardDealLog> findDealLogList(Map<String,Object> map,
			Pagination pagination) {
		return 	presentCardDao.findDealLogList(map,pagination,(short) 0);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal getTotalBalanceByCustomer(Customer customer) {
		BigDecimal totalBalance = presentCardDao
				.getTotalBalanceByCustomer(customer.getId());
		return totalBalance == null ? BigDecimal.ZERO : totalBalance;
	}

}
