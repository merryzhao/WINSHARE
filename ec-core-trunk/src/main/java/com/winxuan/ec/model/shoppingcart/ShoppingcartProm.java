package com.winxuan.ec.model.shoppingcart;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.support.promotion.PromotionPrice;

/**
 * @author yuhu
 * @version 1.0,2011-11-4下午04:35:51
 */
public class ShoppingcartProm {

	private Long shopId;
	
	private Long supplyTypeCode;
	
	private PromotionPrice promotionPrice;
	
	private Code supplyType;
	
	private Promotion promotion;
	
	
	public ShoppingcartProm() {
		super();
	}

	
	public ShoppingcartProm(Long shopId, Long supplyTypeCode,PromotionPrice pp) {
		super();
		this.shopId = shopId;
		this.supplyTypeCode = supplyTypeCode;
		this.promotionPrice = pp;
	}
	
	public ShoppingcartProm(Long shopId, Long supplyTypeCode,PromotionPrice pp , Promotion promotion) {
		super();
		this.shopId = shopId;
		this.supplyTypeCode = supplyTypeCode;
		this.promotionPrice = pp;
		this.promotion = promotion;
	}
	
	

	public Code getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(Code supplyType) {
		this.supplyType = supplyType;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSupplyTypeCode() {
		return supplyTypeCode;
	}

	public void setSupplyTypeCode(Long supplyTypeCode) {
		this.supplyTypeCode = supplyTypeCode;
	}

	public PromotionPrice getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(PromotionPrice promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

}
