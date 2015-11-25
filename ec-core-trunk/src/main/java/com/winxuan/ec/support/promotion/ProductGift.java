package com.winxuan.ec.support.promotion;

import com.winxuan.ec.model.product.ProductSale;

/**
 * @author yuhu
 * @version 1.0,2011-11-27上午10:07:02
 */
public class ProductGift {

	private ProductSale gift;
	
	private Integer sendNum;
	
	public ProductGift() {
		super();
	}

	public ProductGift(ProductSale gift, Integer sendNum) {
		super();
		this.gift = gift;
		this.sendNum = sendNum;
	}

	public ProductSale getGift() {
		return gift;
	}

	public void setGift(ProductSale gift) {
		this.gift = gift;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	
	public Long getGiftId(){
		if(gift != null){
			return gift.getId();
		}
		return null;
	}
	
	public String getGiftName(){
		if(gift != null){
			return gift.getName();
		}
		return null;
	}
}
