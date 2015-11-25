/*
 * @(#)Promotion.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.promotion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.xwork.time.DateUtils;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.order.OrderPromotion;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.promotion.PromotionPrice;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.StringUtils;

/**
 * 促销基础信息
 * 
 * @author yuhu
 * @version 1.0,2011-9-13
 */
@Entity
@Table(name = "promotion")
public class Promotion implements Serializable {

	/**
	 * 商品类型活动
	 */
	public static final Integer OVERTYPE_PRODUCT = 1;
	/**
	 * 订单类型活动
	 */
	public static final Integer OVERTYPE_ORDER = 2;
	/**
	 * 注册送券活动
	 */
	public static final Integer OVERTYPE_REGISTER = 3;
	/**
	 * 商品和订单类型活动
	 */
	public static final Integer OVERTYPE_PRODUCT_ORDER = 4;

	public static final String COMMA = ",";

	/**
	 * 订单类型的促销活动类型
	 */
	public static final Long[] ORDER_TYPES = new Long[] {
			Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT,
			Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE,
			Code.PROMOTION_TYPE_ORDER_SEND_PRESENT};
	
	public static final Map<Long,Integer> PRIORITY_MAP = new HashMap<Long,Integer>();
	
	static{
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE, MagicNumber.ZERO);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_ORDER_SEND_PRESENT, MagicNumber.ONE);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT, MagicNumber.TWO);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE, MagicNumber.THREE);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT, MagicNumber.FOUR);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_CATEGORY_AMOUNT, MagicNumber.FIVE);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT, MagicNumber.SIX);
		PRIORITY_MAP.put(Code.PROMOTION_TYPE_PRODUCT_AMOUNT, MagicNumber.SEVEN);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String title;

	@Column
	private String advert;

	@Column(name = "advertimage")
	private String advertImage;
	
	@Column(name = "adverturl")
	private String advertUrl;

	@Column(name = "effectivetime")
	private Integer effectiveTime = MagicNumber.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

	@Column
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status")
	private Code status;

	@Column(name = "startdate")
	private Date startDate;

	@Column(name = "enddate")
	private Date endDate;

	@Column
	private String shops;

	@Column
	private boolean replication = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manner")
	private Code manner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "effectivestate")
	private Code effectiveState;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createuser")
	private Employee createUser;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor")
	private Employee assessor;

	@Column(name = "assesstime")
	private Date assessTime;

	@OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, targetEntity = PromotionOrderRule.class)
	private Set<PromotionOrderRule> orderRules;
	
	@OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, targetEntity = PromotionRegisterRule.class)
	private Set<PromotionRegisterRule> registerRules;

	@OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, targetEntity = PromotionProductRule.class)
	private Set<PromotionProductRule> productRules;

	@OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, targetEntity = PromotionLog.class)
	private Set<PromotionLog> logs;
	
	@Transient
	private String tempAdvert;

	/**
	 * 获取该活动应用的所有卖家Id
	 * 
	 * @return
	 */
	public String[] getShopIds() {
		if (StringUtils.isNullOrEmpty(shops)) {
			return null;
		}
		return shops.split(COMMA);
	}

	/**
	 * 获取促销活动中的productType类型商品及其数量
	 * 
	 * @param productType
	 *            表示主商品或赠品
	 * @return
	 */
	public Map<ProductSale, Integer> getProduct(Code productType) {
		if (!Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(type.getId())
				|| CollectionUtils.isEmpty(productRules)) {
			return null;
		}
		Map<ProductSale, Integer> productSales = new HashMap<ProductSale, Integer>();
		for (PromotionProductRule rule : productRules) {
			if (productType.getId().equals(rule.getProductType().getId())) {
				productSales.put(rule.getProductSale(), rule.getProductNum());
			}
		}

		return productSales;
	}

	/**
	 * 获取活动中的区域
	 * 
	 * @return
	 */
	public List<Area> getAreas() {
		if (orderRules == null || orderRules.isEmpty()) {
			return null;
		}
		List<Area> areas = new ArrayList<Area>();
		for (PromotionOrderRule rule : orderRules) {
			if (rule.getArea() != null) {
				areas.add(rule.getArea());
			}
		}
		return areas;
	}

	/**
	 * 添加一条促销日志
	 * 
	 * @param log
	 */
	public void addLog(PromotionLog log) {
		if (logs == null) {
			logs = new HashSet<PromotionLog>();
		}
		logs.add(log);
	}

	/**
	 * 添加一条productRule
	 * 
	 * @param rule
	 */
	public void addProductRule(PromotionProductRule rule) {
		if (productRules == null) {
			productRules = new HashSet<PromotionProductRule>();
		}
		productRules.add(rule);
	}

	/**
	 * 添加一条orderRule
	 * 
	 * @param rule
	 */
	public void addOrderRule(PromotionOrderRule rule) {
		if (orderRules == null) {
			orderRules = new HashSet<PromotionOrderRule>();
		}
		orderRules.add(rule);
	}

	/**
	 * 获取当前活动是属于订单类型的还是商品类型的
	 * 
	 * @return
	 */
	public Integer getOverType() {
		if (this.getType() == null) {
			return null;
		}
		boolean isCategoryAmount = Code.PROMOTION_TYPE_CATEGORY_AMOUNT.equals(getType().getId());
		boolean isProductAmount = Code.PROMOTION_TYPE_PRODUCT_AMOUNT.equals(getType().getId());
		boolean isProductSendProduct = Code.PROMOTION_TYPE_PRODUCT_SEND_PRODUCT.equals(getType().getId());
		boolean isOrderSaveAmount = Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT.equals(getType().getId());
		boolean isOrderSaveDeliveryFee = Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(getType().getId());
		boolean isOrderSendPresent = Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(getType().getId());
		boolean isRegisterSendPresent = Code.PROMOTION_TYPE_REGISTER_SEND_PRESENT.equals(getType().getId());
		boolean isProductSaveAmount = Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT.equals(getType().getId());
		if (isCategoryAmount || isProductAmount || isProductSendProduct) {
			return OVERTYPE_PRODUCT;
		} else if (isOrderSaveAmount || isOrderSaveDeliveryFee || isOrderSendPresent) {
			return OVERTYPE_ORDER;
		} else if (isRegisterSendPresent){
			return OVERTYPE_REGISTER;
		} else if (isProductSaveAmount){
			return OVERTYPE_PRODUCT_ORDER;
		} else{
			return null;
		}
	}

	/**
	 * 检查卖家是否符合当前活动
	 * 
	 * @param shop
	 * @return
	 */
	public boolean checkShopAvailable(Shop shop) {
		if (this.getShopIds() == null) {
			return true;
		}
		if (shop == null) {
			return false;
		}
		return checkContainsShop(shop);
	}

	/**
	 * 检查当前活动是否包含shop
	 * 
	 * @param shop
	 * @return
	 */
	public boolean checkContainsShop(Shop shop) {
		String[] shopIds = getShopIds();
		if (shopIds == null) {
			return false;
		}
		for (int i = 0; i < shopIds.length; i++) {
			if (shop.getId().equals(Long.valueOf(shopIds[i]))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断area是否符合该活动
	 * 
	 * @param area
	 * @return
	 */
	public boolean checkAreaAvailable(Area area) {
		if (CollectionUtils.isEmpty(this.getAreas())) {
			return true;
		}
		if (area == null) {
			return false;
		}
		return checkContainsArea(area);
	}

	/**
	 * 检查当前活动是否包含area
	 * 
	 * @param area
	 * @return
	 */
	public boolean checkContainsArea(Area area) {
		if (CollectionUtils.isEmpty(this.getAreas())) {
			return false;
		}
		for (Area a : this.getAreas()) {
			if (area.getId().equals(a.getId()) || area.isGrandChild(a)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 检查当前活动是否包含productSale
	 * 
	 * @param area
	 * @return
	 */
	public boolean checkContainsProductSale(ProductSale productSale) {
		if (CollectionUtils.isEmpty(this.getProductRules())) {
			return false;
		}
		for (PromotionProductRule pr:this.getProductRules()) {
			if (pr.getProductSale().getId().equals(productSale.getId())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 设置订单买商品减免运费活动
	 * @param order
	 */
	public void setupProductSaveDeliveryfeePromotion(Order order){
		if (!replication && order.checkPromotion(this)) {
			return;
		}
		if(Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE.equals(this.getType().getId())
				&& order.getDeliveryFee().compareTo(BigDecimal.ZERO)>0){
			Area orderArea = order.getConsignee().getOrderArea();
			if(canSaveDeliveryFee(orderArea,order.getProductSales())){
				OrderPromotion op = new OrderPromotion(this);
				op.setSaveMoney(order.getDeliveryFee());
				order.setDeliveryFee(BigDecimal.ZERO);
				order.addPromotion(op);
			}
		}
	}
	
	/**
	 * 区域和商品是否满足买商品免运费活动
	 * @param area
	 * @param productSales
	 * @return
	 */
	public boolean canSaveDeliveryFee(Area area, List<ProductSale> productSales) {
		if (Code.PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE.equals(this.getType()
				.getId()) && area.isMainlandChina()) {
			for (ProductSale ps : productSales) {
				if (!CollectionUtils.isEmpty(productRules)) {
					for (PromotionProductRule rule : productRules) {
						if (ps.equals(rule.getProductSale())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 根据item中的商品给订单order添加赠品，并设置买赠促销活动
	 * 
	 * @param order
	 * @param item
	 */
	public void setupSendProductPromotion(Order order, OrderItem item) {
		if (!replication && order.checkPromotion(this)) {
			return;
		}
		Map<ProductSale, Integer> mainProduct = this.getProduct(new Code(Code.PROMOTION_PRODUCT_TYPE_MAIN));
		if (mainProduct != null && !mainProduct.isEmpty()) {
			Integer needNum = mainProduct.get(item.getProductSale());
			if (needNum != null && needNum <= item.getPurchaseQuantity()) {
				Map<ProductSale, Integer> gifts = this.getProduct(new Code(Code.PROMOTION_PRODUCT_TYPE_GIFT));
				if (gifts != null && !gifts.isEmpty()) {
					for (ProductSale gift : gifts.keySet()) {
						if (gift.getAvalibleQuantity() <= 0) {
							break;
						}
						int giftNum = 0;
						if(!replication){
							giftNum = gifts.get(gift);
						}else{
							giftNum = gifts.get(gift)*(item.getPurchaseQuantity()/mainProduct.get(item.getProductSale()));
						}
						giftNum = giftNum > gift
								.getAvalibleQuantity() ? gift
								.getAvalibleQuantity() : giftNum;
						if (order.getItem(gift) == null) {
							OrderItem oi = new OrderItem();
							oi.setOrder(order);
							oi.setProductSale(gift);
							oi.setShop(gift.getShop());
							oi.setListPrice(gift.getProduct().getListPrice());
							oi.setSalePrice(BigDecimal.ZERO);
							oi.setPurchaseQuantity(giftNum);
							order.addItem(oi);
						} else {
							OrderItem oi = order.getItem(gift);
							if (oi.getSalePrice().compareTo(BigDecimal.ZERO) == 0) { // 为赠品则调整赠品数量
								oi.setPurchaseQuantity(oi.getPurchaseQuantity() + giftNum);
							}
						}
					}
					order.addPromotion(new OrderPromotion(this));
				}
			}
		}

	}

	/**
	 * 给订单order设置满省活动
	 * 
	 * @param order
	 */
	public void setupOrderSaveAmountPromotion(Order order) {
		if (Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT
				.equals(this.getType().getId())
				&& orderRules != null
				&& !orderRules.isEmpty()) {
			PromotionPrice pp = getPriceForOrderSaveAmountPromotion(order.getActualMoney());
			BigDecimal saveMoney = pp.getSaveMoney();
			if(saveMoney.compareTo(BigDecimal.ZERO)>0){
				order.setSaveMoney(order.getSaveMoney().add(saveMoney));
				OrderPromotion orderPromotion = new OrderPromotion(this, saveMoney,
						null, null);
				order.addPromotion(orderPromotion);
			}
		}
	}
	
	/**
	 * 给订单order设置购买指定商品满省活动
	 * 
	 * @param order
	 */
	public void setupProductSaveAmountPromotion(Order order) {
		if (Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT
				.equals(this.getType().getId())
				&& !CollectionUtils.isEmpty(orderRules)
				&& !CollectionUtils.isEmpty(productRules)) {
			BigDecimal totalSalePrice = BigDecimal.ZERO;
			BigDecimal otherSalePrice = BigDecimal.ZERO;
			for(OrderItem item:order.getItemList()){
				if(checkContainsProductSale(item.getProductSale())){
					totalSalePrice = totalSalePrice.add(item.getSalePrice().multiply(new BigDecimal(item.getPurchaseQuantity())));
				}else{
					otherSalePrice = otherSalePrice.add(item.getSalePrice().multiply(new BigDecimal(item.getPurchaseQuantity())));
				}
			}
			BigDecimal excludeMoney = order.getSalePrice().subtract(order.getActualMoney());
			excludeMoney = excludeMoney.subtract(otherSalePrice).compareTo(BigDecimal.ZERO) > 0 ? excludeMoney.subtract(otherSalePrice) : BigDecimal.ZERO;
			PromotionPrice promotionPrice = getPriceForOrderSaveAmountPromotion(totalSalePrice.subtract(excludeMoney));
			promotionPrice.setPromType(Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT);
			BigDecimal saveMoney = promotionPrice.getSaveMoney();
			if(saveMoney.compareTo(BigDecimal.ZERO)>0){
				order.setSaveMoney(order.getSaveMoney().add(saveMoney));
				OrderPromotion orderPromotion = new OrderPromotion(this, saveMoney,
						null, null);
				order.addPromotion(orderPromotion);
			}
		}
	}

	/**
	 * 给订单order设置满送券活动
	 * 
	 * @param order
	 */
	public void setupOrderSendPresentPromotion(Order order) {
		if (Code.PROMOTION_TYPE_ORDER_SEND_PRESENT.equals(this.getType()
				.getId()) && orderRules != null && !orderRules.isEmpty()) {
			if (Code.PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL.equals(getManner()
					.getId())) {
				for (PromotionOrderRule orderRule : this.getOrderRules()) {
					Integer divideNum = order
							.getActualMoney()
							.divide(orderRule.getMinAmount(), 0,
									RoundingMode.DOWN).intValue();
					if(divideNum<1){
						return;
					}
					OrderPromotion orderPromotion = new OrderPromotion(this,
							orderRule.getPresentBatch().getValue(), orderRule.getPresentBatch(), 
							divideNum * orderRule.getPresentNum());
					order.addPromotion(orderPromotion);
				}
			} else if (Code.PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT
					.equals(getManner().getId())) {
				BigDecimal minMoney = BigDecimal.ZERO;
				for (PromotionOrderRule rule : this.getOrderRules()) {
					if (order.getActualMoney().compareTo(rule.getMinAmount()) >= 0
							&& rule.getMinAmount().compareTo(minMoney) > 0) {
						minMoney = rule.getMinAmount();
					}
				}
				for (PromotionOrderRule orderRule : this.getOrderRules()) {
					if (minMoney.compareTo(orderRule.getMinAmount()) == 0) {
						OrderPromotion orderPromotion = new OrderPromotion(this, 
								orderRule.getPresentBatch().getValue(), orderRule.getPresentBatch(),
								orderRule.getPresentNum());
						order.addPromotion(orderPromotion);
					}
				}
			}
		}
	}

	/**
	 * 给订单order设置运费减免活动活动
	 * 
	 * @param order
	 */
	public void setupOrderSaveDeliveryFeePromotion(Order order) {
		if (Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE.equals(this.getType()
				.getId())) {
			Area orderArea = order.getConsignee().getOrderArea();
			PromotionPrice pp = getPriceForDeliveryFeePromotion(orderArea,order.getActualMoney());
			if(pp!=null && pp.getSaveMoney().compareTo(BigDecimal.ZERO) != 0){
				BigDecimal saveMoney = pp.getSaveMoney();
				OrderPromotion orderPromotion = new OrderPromotion(this);
				if(saveMoney.compareTo(BigDecimal.ZERO)<0){
					orderPromotion.setSaveMoney(order.getDeliveryFee());
					order.setDeliveryFee(BigDecimal.ZERO);
				}else if(saveMoney.compareTo(BigDecimal.ZERO)>0){
					BigDecimal newDeliveryFee = order.getDeliveryFee().subtract(saveMoney) ;
					newDeliveryFee = newDeliveryFee.compareTo(BigDecimal.ZERO) > 0 ? newDeliveryFee : BigDecimal.ZERO ;
					BigDecimal savedFee = order.getDeliveryFee().subtract(newDeliveryFee);
					if(savedFee.compareTo(BigDecimal.ZERO) > 0){
						orderPromotion.setSaveMoney(savedFee);
					}
					order.setDeliveryFee(newDeliveryFee);
				}
				if(orderPromotion.getSaveMoney() != null 
						&& orderPromotion.getSaveMoney().compareTo(BigDecimal.ZERO)>0){
					order.addPromotion(orderPromotion);
				}
			}
		}
	}

	/**
	 * 获取最后支付时间
	 * 
	 * @return
	 */
	public Date getMaxPayTime() {
		if (effectiveTime != null && effectiveTime > 0) {
			return new Date(this.getEndDate().getTime()
					+ this.getEffectiveTime() * MagicNumber.ONE_HOUR);
		} else {
			return DateUtils.addYears(new Date(), MagicNumber.FIVE);
		}
	}
	
	/**
	 * 根据salePrice返回参与订单满省活动的节省金额、参加下一级活动需要金额阀值、参加下级活动可获得的优惠金额
	 * 
	 * @param salePrice
	 * @return 
	 */
	public PromotionPrice getPriceForOrderSaveAmountPromotion(BigDecimal salePrice){
		BigDecimal ruleMoney = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal saveMoney = BigDecimal.ZERO;
		BigDecimal moreSaveMoney = BigDecimal.ZERO;
		BigDecimal needMoney = BigDecimal.ZERO;
		if (Code.PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL.equals(this.getManner().getId())) {
			PromotionOrderRule orderRule = orderRules.iterator().next();
			BigDecimal divideNum = salePrice.divide(orderRule.getMinAmount(), 0, RoundingMode.DOWN);
			saveMoney = divideNum.multiply(orderRule.getAmount());
			ruleMoney = divideNum.multiply(orderRule.getMinAmount());
			needMoney = (divideNum.add(BigDecimal.ONE)).multiply(
					orderRule.getMinAmount()).subtract(salePrice);
			moreSaveMoney = (divideNum.add(BigDecimal.ONE)).multiply(orderRule.getAmount());
		} else if (Code.PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT.equals(this.getManner().getId())) {
			for (PromotionOrderRule rule : orderRules) {
				if (salePrice.compareTo(rule.getMinAmount()) >= 0) {
					if (rule.getAmount().compareTo(saveMoney) > 0) {
						saveMoney = rule.getAmount();
						ruleMoney = rule.getMinAmount();
					}
				} else if (needMoney.compareTo(rule.getMinAmount().subtract(salePrice)) > 0
						|| needMoney.compareTo(BigDecimal.ZERO) == 0) {
					needMoney = rule.getMinAmount().subtract(salePrice);
					moreSaveMoney = rule.getAmount();
				}
			}
		}
		amount = saveMoney.compareTo(BigDecimal.ZERO)==0?moreSaveMoney:saveMoney;
		ruleMoney = ruleMoney.compareTo(BigDecimal.ZERO)==0?salePrice.add(needMoney):ruleMoney;
		return  new PromotionPrice(Code.PROMOTION_TYPE_ORDER_SAVE_AMOUNT,ruleMoney,amount,saveMoney,needMoney,moreSaveMoney);
	}
	
	/**
	 * 根据itemList返回参与购买指定商品满省活动的节省金额、参加下一级活动需要金额阀值、参加下级活动可获得的优惠金额
	 * 
	 * @param itemList
	 * @return 
	 */
	public PromotionPrice getPriceForOrderSaveAmountByProductPromotion(List<ShoppingcartItem> itemList,BigDecimal excludeMoney){
		BigDecimal totalSalePrice = BigDecimal.ZERO;
		BigDecimal otherSalePrice = BigDecimal.ZERO;
		excludeMoney = excludeMoney==null ? BigDecimal.ZERO : excludeMoney;
		if(!CollectionUtils.isEmpty(itemList)){
			for(ShoppingcartItem item:itemList){
				if(checkContainsProductSale(item.getProductSale())){
					totalSalePrice = totalSalePrice.add(item.getSalePrice().multiply(new BigDecimal(item.getQuantity())));
					item.setPloy(true);
				}else{
					otherSalePrice = otherSalePrice.add(item.getSalePrice().multiply(new BigDecimal(item.getQuantity())));
				}
			}
		}
		excludeMoney = excludeMoney.subtract(otherSalePrice).compareTo(BigDecimal.ZERO) > 0 ? excludeMoney.subtract(otherSalePrice) : BigDecimal.ZERO;
		PromotionPrice promotionPrice = getPriceForOrderSaveAmountPromotion(totalSalePrice.subtract(excludeMoney));
		promotionPrice.setPromType(Code.PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT);
		return promotionPrice;
	}
	
	/**
	 * 根据salePrice返回参与订单满送券活动的赠送礼券金额、再支付金额、再支付金额后可赠送礼券金额
	 * 
	 * @param salePrice
	 * @return 
	 */
	public PromotionPrice getPriceForSendPresentPromotion(BigDecimal salePrice){
		BigDecimal ruleMoney = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal sendMoney = BigDecimal.ZERO;
		BigDecimal moreSendMoney = BigDecimal.ZERO;
		BigDecimal needMoney = BigDecimal.ZERO;
		if(Code.PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL.equals(this.getManner().getId())){
			PromotionOrderRule rule = orderRules.iterator().next();
			BigDecimal divideNum = salePrice.divide(rule.getMinAmount(), 0, RoundingMode.DOWN);
			sendMoney = sendMoney.add(divideNum.multiply(rule.getAmount()));
			ruleMoney = divideNum.multiply(rule.getMinAmount());
			needMoney = (divideNum.add(BigDecimal.ONE)).multiply(rule.getMinAmount()).subtract(salePrice);
			moreSendMoney = moreSendMoney.add((divideNum.add(BigDecimal.ONE)).multiply(rule.getAmount()));
		}else if(Code.PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT.equals(this.getManner().getId())){
			BigDecimal minMoney = BigDecimal.ZERO;
			BigDecimal moreMoney = BigDecimal.ZERO;
			for(PromotionOrderRule rule : orderRules){
				if(salePrice.compareTo(rule.getMinAmount())>=0 && rule.getMinAmount().compareTo(minMoney)>0){
					minMoney = rule.getMinAmount();
				} else if (salePrice.compareTo(rule.getMinAmount()) < 0
						&& (moreMoney.compareTo(BigDecimal.ZERO) == 0 
								|| rule.getMinAmount().compareTo(moreMoney) < 0)) {
					moreMoney = rule.getMinAmount();
				}
			}
			for(PromotionOrderRule rule : orderRules){
				if(minMoney.compareTo(rule.getMinAmount()) == 0){
					sendMoney = sendMoney.add(rule.getAmount());
					ruleMoney = rule.getMinAmount();
				}else if(moreMoney.compareTo(rule.getMinAmount()) == 0){
					needMoney = moreMoney.subtract(salePrice);
					moreSendMoney = moreSendMoney.add(rule.getAmount());
				}
			}
		}
		amount = sendMoney.compareTo(BigDecimal.ZERO)==0?moreSendMoney:sendMoney;
		ruleMoney = ruleMoney.compareTo(BigDecimal.ZERO)==0?salePrice.add(needMoney):ruleMoney;
		return new PromotionPrice(Code.PROMOTION_TYPE_ORDER_SEND_PRESENT,ruleMoney,amount,sendMoney,needMoney,moreSendMoney);
	}
	
	/**
	 * 根据salePrice返回参与运费减免活动的运费减免金额、再支付金额、再支付金额后可减免运费金额
	 * @param area
	 * @param salePrice
	 * @return 
	 */
	public PromotionPrice getPriceForDeliveryFeePromotion(Area area ,BigDecimal salePrice){
		BigDecimal ruleMoney = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal deliveryFee = BigDecimal.ZERO;
		BigDecimal needMoney = BigDecimal.ZERO;
		BigDecimal moreDeliveryFee = BigDecimal.ZERO;
		for (PromotionOrderRule rule : orderRules) {
			if (area == null 
					|| area.getId().equals(rule.getArea().getId())
					|| area.isGrandChild(rule.getArea())) {
				if (salePrice.compareTo(rule.getMinAmount()) >= 0) {
					if (rule.getRemitDeliveryFee()) {
						deliveryFee = MagicNumber.BD_NEGATIVE_1;
					} else {
						deliveryFee = rule.getDeliveryFee();
					}
					ruleMoney = rule.getMinAmount();
				} else {
					needMoney = rule.getMinAmount().subtract(salePrice);
					if (rule.getRemitDeliveryFee()) {
						moreDeliveryFee = MagicNumber.BD_NEGATIVE_1;
					} else {
						moreDeliveryFee = rule.getDeliveryFee();
					}
				}
				break;
			}
		}
		if(deliveryFee.compareTo(BigDecimal.ZERO)==0 && needMoney.compareTo(BigDecimal.ZERO) == 0){
			return null;
		}
		amount = deliveryFee.compareTo(BigDecimal.ZERO)==0?moreDeliveryFee:deliveryFee;
		ruleMoney = ruleMoney.compareTo(BigDecimal.ZERO)==0?salePrice.add(needMoney):ruleMoney;
		return new PromotionPrice(Code.PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE,ruleMoney,amount,deliveryFee,needMoney,moreDeliveryFee);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getAdvertImage() {
		return advertImage;
	}

	public void setAdvertImage(String advertImage) {
		this.advertImage = advertImage;
	}

	public Integer getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Integer effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getAdvertUrl() {
		return advertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getShops() {
		return shops;
	}

	public void setShops(String shops) {
		this.shops = shops;
	}

	public boolean isReplication() {
		return replication;
	}

	public void setReplication(boolean replication) {
		this.replication = replication;
	}

	public Code getManner() {
		return manner;
	}

	public void setManner(Code manner) {
		this.manner = manner;
	}

	public Code getEffectiveState() {
		return effectiveState;
	}

	public void setEffectiveState(Code effectiveState) {
		this.effectiveState = effectiveState;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Employee getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Employee getAssessor() {
		return assessor;
	}

	public void setAssessor(Employee assessor) {
		this.assessor = assessor;
	}

	public Date getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}

	public Set<PromotionOrderRule> getOrderRules() {
		return orderRules;
	}

	public void setOrderRules(Set<PromotionOrderRule> orderRules) {
		this.orderRules = orderRules;
	}

	public Set<PromotionProductRule> getProductRules() {
		return productRules;
	}

	public void setProductRules(Set<PromotionProductRule> productRules) {
		this.productRules = productRules;
	}

	public Set<PromotionRegisterRule> getRegisterRules() {
		return registerRules;
	}

	public void setRegisterRules(Set<PromotionRegisterRule> registerRules) {
		this.registerRules = registerRules;
	}
	
	public Set<PromotionLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<PromotionLog> logs) {
		this.logs = logs;
	}

	public String getTempAdvert() {
		return tempAdvert;
	}

	public void setTempAdvert(String tempAdvert) {
		this.tempAdvert = tempAdvert;
	}

}
