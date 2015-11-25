package com.winxuan.ec.admin.controller.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 商品批量调价表单
 *
 * @author YangJun
 * @version 1.0,2011-12-9
 */
public class ProductPriceUpdateForm {
	/**
	 * 调价商品的最低折扣
	 */
	public static final BigDecimal MIN_DISCOUNT = BigDecimal.valueOf(0.5);
	private Long id;
	private String name;
	private String barcode;
	private BigDecimal listPrice;
	private BigDecimal saleprice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	
	public BigDecimal getDiscount() {
		return saleprice.divide(listPrice, 2, RoundingMode.HALF_UP);
	}

	public boolean getIsWarning(){
		if(getDiscount().compareTo(MIN_DISCOUNT) < 0 || getDiscount().compareTo(BigDecimal.ONE) > 0){
			return true;
		}
		return false;
	}
}
