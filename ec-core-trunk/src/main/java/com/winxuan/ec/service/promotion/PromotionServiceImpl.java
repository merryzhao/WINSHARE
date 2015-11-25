/*
 * @(#)PromotionServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.service.promotion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.dao.PromotionDao;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.PromotionException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPromotion;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.promotion.PromotionLog;
import com.winxuan.ec.model.promotion.PromotionOrderRule;
import com.winxuan.ec.model.promotion.PromotionProductRule;
import com.winxuan.ec.model.promotion.PromotionRegisterRule;
import com.winxuan.ec.model.promotion.PromotionTag;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shoppingcart.Shoppingcart;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.shoppingcart.ShoppingcartProm;
import com.winxuan.ec.model.shoppingcart.ShoppingcartSeparator;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.support.promotion.PromotionPrice;
import com.winxuan.ec.support.promotion.PromotionValidateUtils;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;

/**
 * description
 * 
 * @author zhongsen
 * @version 1.0,2011-9-14
 */
@Service("promotionService")
@Transactional(rollbackFor = Exception.class)
public class PromotionServiceImpl implements PromotionService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//基本的订单免邮费促销活动--满38减运费
	private static final Long BASE_ORDER_SAVE_DELIVERYFEE_PROMOTION_ID = 624L;
	//全省免邮费 促销
	private static final Long ORDER_SAVE_DELIVERYFEE_PROMOTION_ID = 599L;
	Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);
	@InjectDao
	private PromotionDao promotionDao;
	@Autowired
	private PresentService presentService;
	@Autowired
	private OrderService orderService;

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Promotion get(Long id) {
		return promotionDao.get(id);
	}

	@Override
	public void save(Promotion promotion, Employee employee)
			throws PromotionException {
		if (promotion == null || employee == null) {
			throw new PromotionException(
					"promotion or employee can not be null!");
		}
		if (promotion.getType() == null) {
			throw new PromotionException("the promotion type can not be null!");
		}
		PromotionValidateUtils.validatePromotion(promotion, this);
		promotion.setStatus(new Code(Code.PROMOTION_STATUS_CREATE));
		promotion.setCreateUser(employee);
		promotion.setCreateTime(new Date());

		PromotionLog promotionLog = new PromotionLog(promotion, employee);
		promotion.addLog(promotionLog);

		promotionDao.save(promotion);
	}

	@Override
	public void update(Promotion promotion, Employee employee)
			throws PromotionException {
		if (promotion == null || employee == null) {
			throw new PromotionException(
					"promotion or employee can not be null!");
		}
		if (promotion.getType() == null) {
			throw new PromotionException("the promotion type can not be null!");
		}
		//特殊处理订单包邮，临时处理办法
		if(!isContainsPromotion(promotion))
		{
			PromotionValidateUtils.validatePromotion(promotion, this);
		}
		Promotion oldPromotion = promotionDao.get(promotion.getId());
		if (!oldPromotion.getType().equals(promotion.getType())) {
			throw new PromotionException(
					"the promotion type can not be changed!");
		}

		if (Promotion.OVERTYPE_PRODUCT.equals(promotion.getOverType())) {
			updateProductRules(promotion, oldPromotion);
		} else if (Promotion.OVERTYPE_ORDER.equals(promotion.getOverType())) {
			updateOrderRules(promotion, oldPromotion);
		} else if (Promotion.OVERTYPE_REGISTER.equals(promotion.getOverType())){
			updateRegisterRules(promotion, oldPromotion);
		} else if (Promotion.OVERTYPE_PRODUCT_ORDER.equals(promotion.getOverType())){
			updateProductRules(promotion, oldPromotion);
			updateOrderRules(promotion, oldPromotion);
		}
		if(Code.PROMOTION_STATUS_FAIL.equals(promotion.getStatus().getId())){
			oldPromotion.setStatus(new Code(Code.PROMOTION_STATUS_CREATE));
		}
		PromotionLog promotionLog = new PromotionLog(promotion, employee);
		promotion.addLog(promotionLog);
		oldPromotion.setAssessor(employee);
		oldPromotion.setAssessTime(new Date());
		promotionDao.update(oldPromotion);
		if (Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(promotion.getType().getId())
				&& (Code.PROMOTION_STATUS_PASS.equals(promotion.getStatus().getId()) 
						|| Code.PROMOTION_STATUS_STOP.equals(promotion.getStatus().getId()))) {
			promotionDao.flush();
			promotionDao.callUpdateProcedure(promotion.getId());
		}
	}

	@Override
	public void audit(Promotion promotion, Boolean isPass, Employee operator)
			throws PromotionException {
		Code oldState = promotion.getStatus();
		if (!Code.PROMOTION_STATUS_CREATE.equals(oldState.getId())) {
			throw new PromotionException("促销活动只能为已生成状态时才能审核！");
		}
		promotion.setStatus(isPass ? new Code(Code.PROMOTION_STATUS_PASS)
				: new Code(Code.PROMOTION_STATUS_FAIL));
		PromotionLog promotionLog = new PromotionLog(promotion, operator);
		promotion.addLog(promotionLog);
		promotionDao.update(promotion);
		if (Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(promotion.getType().getId())) {
			promotionDao.flush();
			promotionDao.callUpdateProcedure(promotion.getId());
		}
	}

	@Override
	public void stop(Promotion promotion, Employee operator)
			throws PromotionException {
		if (promotion.getStatus() == null) {
			throw new RuntimeException("promotion status can not be null");
		}
		if (!Code.PROMOTION_STATUS_PASS.equals(promotion.getStatus().getId())
				&& !Code.PROMOTION_STATUS_FAIL.equals(promotion.getStatus()
						.getId())) {
			throw new PromotionException("活动类型为审核通过或审核未通过状态才能停用!");
		}
		promotion.setStatus(new Code(Code.PROMOTION_STATUS_STOP));
		PromotionLog promotionLog = new PromotionLog(promotion, operator);
		promotion.addLog(promotionLog);
		promotionDao.update(promotion);
		if (Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(promotion.getType().getId())) {
			promotionDao.flush();
			promotionDao.callUpdateProcedure(promotion.getId());
		}
	}

	/**
	 * 检查活动的productRules是否为空
	 * 
	 * @param promotion
	 * @throws PromotionException
	 */
	private void checkProductRules(Promotion promotion)
			throws PromotionException {
		if (promotion.getProductRules() == null
				|| promotion.getProductRules().isEmpty()) {
			throw new PromotionException(
					"promotionPruductRules can not be null when promotion type is "
							+ promotion.getType().getId() + "L!");
		}
	}

	/**
	 * 更新 productRules
	 * 
	 * @param newPromotion
	 * @param oldPromotion
	 * @throws PromotionException
	 */
	private void updateProductRules(Promotion newPromotion,
			Promotion oldPromotion) throws PromotionException {
		checkProductRules(newPromotion);
		Set<PromotionProductRule> newProductRules = newPromotion
				.getProductRules();
		for (Iterator<PromotionProductRule> iterator = oldPromotion
				.getProductRules().iterator(); iterator.hasNext();) {
			PromotionProductRule oldRule = iterator.next();
			iterator.remove();
			promotionDao.delete(oldRule);
		}
		oldPromotion.setProductRules(newProductRules);
	}
	
	/**
	 * 是否包含特殊处理的促销活动
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isContainsPromotion(Promotion promotion){
		List promList = new ArrayList();
		promList.add(ORDER_SAVE_DELIVERYFEE_PROMOTION_ID);
		promList.add(BASE_ORDER_SAVE_DELIVERYFEE_PROMOTION_ID);
		if(promList.contains(promotion.getId()))
		{
			return true;
		}
		return false;
		
	}
	/**
	 * 跟新 orderRules
	 * 
	 * @param newPromotion
	 * @param oldPromotion
	 * @throws PromotionException
	 */
	private void updateOrderRules(Promotion newPromotion, Promotion oldPromotion)
			throws PromotionException {
		Set<PromotionOrderRule> newOrderRules = newPromotion.getOrderRules();
		if (CollectionUtils.isEmpty(newOrderRules) ) {
			throw new PromotionException(
					"The promotionOrderRule can not be null when update order type promotion!!");
		}
		for (Iterator<PromotionOrderRule> iterator = oldPromotion
				.getOrderRules().iterator(); iterator.hasNext();) {
			PromotionOrderRule oldRule = iterator.next();
			iterator.remove();
			promotionDao.delete(oldRule);
		}
		oldPromotion.setOrderRules(newOrderRules);
	}

	/**
	 * 更新registerRule
	 * 
	 * @param newPromotion
	 * @param oldPromotion
	 * @throws PromotionException
	 */
	private void updateRegisterRules(Promotion newPromotion, Promotion oldPromotion)
			throws PromotionException {
		Set<PromotionRegisterRule> newRegisterRules = newPromotion.getRegisterRules();
		if (CollectionUtils.isEmpty(newRegisterRules)) {
			throw new PromotionException(
					"The promotionRegisterRule can not be null when update register type promotion!!");
		}
		for (Iterator<PromotionRegisterRule> iterator = oldPromotion
				.getRegisterRules().iterator(); iterator.hasNext();) {
			PromotionRegisterRule registerRule = iterator.next();
			iterator.remove();
			promotionDao.delete(registerRule);
		}
		oldPromotion.setRegisterRules(newRegisterRules);
	}
	
	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long getNeedVerifyCount() {
		return promotionDao.getNeedVerifyCount();
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Promotion> find(Map<String, Object> parameters,
			Pagination pagination) {
		return promotionDao.find(parameters, pagination, (short) 0);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Code> findActivityType() {
		return promotionDao.findActivityTypes();
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Promotion> findEffectivePromotion(List<ProductSale> products)
			throws PromotionException {
		List<Promotion> promotions = findEffectiveOrderPromotion();
		if (CollectionUtils.isEmpty(products)) {
			return promotions;
		}
		Shop shop = null;
		for (ProductSale ps : products) {
			if (shop == null) {
				shop = ps.getShop();
			} else if (!shop.getId().equals(ps.getShop().getId())) {
				throw new PromotionException("传入的商品不能为不同卖家的商品！");
			}
		}
		List<Promotion> promotionList = findEffectiveProductPromotion(
				products.toArray(new ProductSale[] {}), new Long[] {
						Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT,
						Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE,
						Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT});
		if (promotionList == null) {
			promotionList = new ArrayList<Promotion>();
		}
		if (!CollectionUtils.isEmpty(promotions)) {
			for (Promotion promotion : promotions) {
				if (promotion.checkShopAvailable(shop)) {
					promotionList.add(promotion);
				}
			}
		}
		return promotionList;
	}
	
	@Override
	public void setupShoppingcartPromotion(Shoppingcart shoppingcart) {
		if(shoppingcart == null || CollectionUtils.isEmpty(shoppingcart.getItemList())){
			return;
		}
		Map<ProductSale,Integer> mainProductMap = null;
		Map<ProductSale,Integer> giftProductMap = null;
		Set<ProductSale> mainProducts = null;
		List<ShoppingcartItem> itemList = null;
		List<ProductSale> psList = null;
		List<ProductSale> gifts = null;
		int giftNum=0;
		
		Map<ShoppingcartSeparator, List<ShoppingcartItem>> shoppingcartGroup = shoppingcart.getGroupItems();
		Map<ShoppingcartSeparator, BigDecimal> shoppingcartPriceGroup = shoppingcart.getGroupSalePrice();
		List<Promotion> orderPromotions = findEffectiveOrderPromotion();
		BigDecimal totalSalePrice = null;
		ProductSale ps = null;
		boolean isPloy=true;
		// 代理用户不参与满省活动。 6 ，组织销售—代理
		if (shoppingcart.getCustomer() != null&&shoppingcart.getCustomer().getChannel()!=null&&Channel.CHANNEL_AGENT.equals(shoppingcart.getCustomer().getChannel().getId())) {
			isPloy=false;
		}
		for(ShoppingcartSeparator ss : shoppingcartGroup.keySet()){
			//setup product type promotions
			psList = new ArrayList<ProductSale>();
			itemList = shoppingcartGroup.get(ss);
			totalSalePrice = shoppingcartPriceGroup.get(ss);
			
			for(ShoppingcartItem sci : itemList){
				ps = sci.getProductSale();
				psList.add(ps);
				//设置 限购数量
				if(shoppingcart.getCustomer() != null){
					ps.setPurchasedQuantity(orderService.getPurchaseQuantityToday(
						shoppingcart.getCustomer(), ps));
					ps.setPurchasedQuantityAll(orderService.getPurchaseQuantityToday(ps));
				}
				if (sci.getQuantity() > ps.getAvalibleQuantity()
						&& ps.getCanPurchaseQuantity() == ps.getAvalibleQuantity()) {
					sci.setQuantity(ps.getAvalibleQuantity());
				} else if (sci.getQuantity() > ps.getCanPurchaseQuantity()) {
					sci.setQuantity(ps.getCanPurchaseQuantity());
				}
			}
			
			List<Promotion> promotions = findEffectiveProductPromotion(psList.toArray(new ProductSale[]{}),new Long[]{Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT});
			gifts = new ArrayList<ProductSale>();
			for(Promotion p : promotions){
				mainProductMap = p.getProduct(new Code(Code.PROMOTION_PRODUCT_TYPE_MAIN));
				giftProductMap = p.getProduct(new Code(Code.PROMOTION_PRODUCT_TYPE_GIFT));
				mainProducts = mainProductMap.keySet();
				for(ShoppingcartItem item : itemList){
					if(mainProducts.contains(item.getProductSale()) 
							&& mainProductMap.get(item.getProductSale()).compareTo(item.getQuantity())<=0){
						for(ProductSale gift : giftProductMap.keySet()){
							if(shoppingcart.getItem(gift) !=  null){
								break;
							}
							if(!p.isReplication()){
								giftNum = giftProductMap.get(gift);
							}else{
								giftNum = giftProductMap.get(gift)*(item.getQuantity()/mainProductMap.get(item.getProductSale()));
							}
							if((!gifts.contains(gift) || p.isReplication()) && gift.getAvalibleQuantity()>0){
								item.addGift(gift, gift.getAvalibleQuantity()>giftNum?giftNum:gift.getAvalibleQuantity());
								gifts.add(gift);
							}
						}
					}
				}
			}
			
			// setup product save amount when salePrice more than given price
			promotions = findEffectiveProductPromotion(psList.toArray(new ProductSale[]{}),new Long[]{Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT});
			if(!CollectionUtils.isEmpty(promotions)&&isPloy){
				Promotion p = promotions.get(0);
				PromotionPrice pp = p.getPriceForOrderSaveAmountByProductPromotion(itemList,null);
				if(pp != null ){
					ShoppingcartProm prom = new ShoppingcartProm(
							ss.getShop().getId(),
							ss.getSupplyType().getId(),
							pp,
							p);
					totalSalePrice = totalSalePrice.subtract(prom.getPromotionPrice().getSaveMoney());
					shoppingcart.add(prom);
				}
			}
			//setup order type promotions
			for(Promotion promotion : orderPromotions){
				if(!promotion.checkShopAvailable(ss.getShop())){
					continue;
				}
				if (Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT.equals(promotion.getType().getId())&&isPloy) {// 满省活动
					ShoppingcartProm prom = new ShoppingcartProm(
							ss.getShop().getId(),
							ss.getSupplyType().getId(),
							promotion.getPriceForOrderSaveAmountPromotion(totalSalePrice),
							promotion);
					totalSalePrice = totalSalePrice.subtract(prom.getPromotionPrice().getSaveMoney());
					shoppingcart.add(prom);
				}else if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(promotion.getType().getId())&&isPloy) {  //满送券活动
					ShoppingcartProm prom = new ShoppingcartProm(
							ss.getShop().getId(),
							ss.getSupplyType().getId(),
							promotion.getPriceForSendPresentPromotion(totalSalePrice),
							promotion);
					shoppingcart.add(prom);
				}
			}
		}
	}

	@Override
	public void setupOrderPromotion(Order order) {
		if (order == null || CollectionUtils.isEmpty(order.getItemList())) {
			return;
		}
		boolean isPloy = true;
		List<Long> presentIds = order.getUsedPresentId();
		// 代理用户不参与满省活动。 6 ，组织销售—代理
		if (order.getCustomer() != null&&order.getCustomer().getChannel()!=null&&Channel.CHANNEL_AGENT.equals(order.getCustomer().getChannel().getId())) {
			isPloy=false;
		}
		if (presentIds != null && !presentIds.isEmpty()) {
			Present present;
			for (Long pid : presentIds) {
				present = presentService.get(pid);
				if(present != null && !present.getBatch().isPloy()){
					isPloy = false; // 订单中使用了不能参与活动的礼券，则该订单不享受满省、满送优惠活动
					break;
				}
			}
		}
		List<Promotion> promotions = null;
		try {
			promotions = findEffectivePromotion(order.getProductSales());
		} catch (PromotionException e) {
			throw new RuntimeException(e);
		}
		if (promotions != null && !promotions.isEmpty()) {
			for (Promotion promotion : promotions) {
				if (Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT
						.equals(promotion.getType().getId()) && isPloy) {
					promotion.setupProductSaveAmountPromotion(order);
				}
			}
			for (Promotion promotion : promotions) {
				if (promotion.checkShopAvailable(order.getShop())) {
					if (Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT
							.equals(promotion.getType().getId()) && isPloy) {
						promotion.setupOrderSaveAmountPromotion(order);
					} else if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT
							.equals(promotion.getType().getId()) && isPloy) {
						promotion.setupOrderSendPresentPromotion(order);
					} else if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE
							.equals(promotion.getType().getId())) {
						//特殊处理代理不参加全场免邮费活动，624为基本免运费活动，要做全场面邮费请在这个id修改
						if((!isPloy&&BASE_ORDER_SAVE_DELIVERYFEE_PROMOTION_ID.equals(promotion.getId()))
								||isPloy)
						{
								promotion.setupOrderSaveDeliveryFeePromotion(order);
						}
					
					} else if (Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT
							.equals(promotion.getType().getId())&& isPloy) {
						List<OrderItem> oList = new ArrayList<OrderItem>();
						oList.addAll(order.getItemList());
						for(OrderItem oi : oList) {
							promotion.setupSendProductPromotion(order, oi);
						}
					} else if (Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE
							.equals(promotion.getType().getId())) {
						promotion.setupProductSaveDeliveryfeePromotion(order);
					}
				}
			}
		}
	}

	/**
	 * 查询当前生效的订单级促销活动
	 * 
	 * @return
	 */
	private List<Promotion> findEffectiveOrderPromotion() {
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("pStartDateOver", now);
		params.put("pEndDateBegin", now);
		params.put("pStatusId", new Long[] { Code.PROMOTION_STATUS_PASS });
		params.put("pTypeId", Promotion.ORDER_TYPES);
		return promotionDao.find(params,(short)0);
	}
	
	/**
	 * 查询当前生效的商品类型促销活动
	 * @param pss 商品
	 * @return
	 */
	private List<Promotion> findEffectiveProductPromotion(ProductSale[] pss,Long[] types){
		Map<String, Object> params = new HashMap<String, Object>();
		Date now = new Date();
		params.put("pStartDateOver", now);
		params.put("pEndDateBegin", now);
		params.put("pStatusId", new Long[] { Code.PROMOTION_STATUS_PASS });
		params.put("pTypeId", types);
		params.put("productSales", pss);
		return promotionDao.find(params,(short)0);
	}

	@Override
	public void orderPromotionEffect(Order order) throws PromotionException {
		if (CollectionUtils.isEmpty(order.getPromotionList())) {
			return;
		}
		for (OrderPromotion orderPromotion : order.getPromotionList()) {
			if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(orderPromotion
					.getType().getId())
					&& order.getProcessStatus()
							.getId()
							.equals(orderPromotion.getPromotion()
									.getEffectiveState().getId())) {
				List<Customer> customers = new ArrayList<Customer>();
				for (int i = 0; i < orderPromotion.getPresentNum(); i++) {
					// 代理用户不参与满送券活动。 6 ，组织销售—代理
					if (order.getCustomer() != null && order.getCustomer().getChannel().getId().longValue()==6) {
						continue;
					}
					customers.add(order.getCustomer());
				}
				try {
					presentService.sendPresent4Customer(
							orderPromotion.getPresentBatch(), customers, new Code(Code.PRESENT_ORIGIN_PROMOTION),order);
				} catch (PresentException e) {
					throw new PromotionException(e);
				}
			}
		}
	}

	@Override
	public List<Promotion> findEffectivePromotion(Long[] promotionTypes,
			Date startDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pStartDateOver", endDate);
		params.put("pEndDateBegin", startDate);
		params.put("pStatusId", new Long[] { Code.PROMOTION_STATUS_CREATE,
				Code.PROMOTION_STATUS_PASS, Code.PROMOTION_STATUS_FAIL });
		params.put("pTypeId", promotionTypes);
		return promotionDao.find(params,(short)0);
	}

	@Override
	public List<Promotion> find(Map<String, Object> parameters) {
		return promotionDao.find(parameters, (short) 0);
	}

	@Override
	public List<Promotion> findEffectivePromotion(ProductSale ps) {
		List<Promotion> pList = new ArrayList<Promotion>();
		if(ps.isHasPromotion()){
			pList.add(ps.getPromotion());
			return pList;
		}
		List<ProductSale> psList = new ArrayList<ProductSale>();
		psList.add(ps);
		try {
			List<Promotion> listTemp = findEffectivePromotion(psList);
			if(listTemp != null && !listTemp.isEmpty()){
				Promotion pTemp = null;
				for(Promotion p:listTemp){
					if(pTemp == null 
							|| Promotion.PRIORITY_MAP.get(pTemp.getType().getId())<Promotion.PRIORITY_MAP.get(p.getType().getId())){
						pTemp = p;
					}
				}
				pList.add(pTemp);
			}
		} catch (PromotionException e) {
			log.error(e.getMessage(),e);
		}
		return pList;
	}

	@Override
	public List<PromotionTag> findPromotionTag(Map<String, Object> parameters,Pagination pagination) {
		return promotionDao.findPromotionTag(parameters,pagination,(short)0);
	}

	@Override
	public PromotionTag getPromotionTag(Long id) {
		return promotionDao.getPromotionTag(id);
	}

	@Override
	public void saveorupdateTag(PromotionTag promotionTag) {
		 promotionDao.saveorupdate(promotionTag);
	}
}
