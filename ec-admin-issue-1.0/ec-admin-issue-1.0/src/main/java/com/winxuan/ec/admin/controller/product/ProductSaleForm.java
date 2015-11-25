/*
 * @(#)ProductSaleForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 * 
 */
package com.winxuan.ec.admin.controller.product;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleStock;

/**
 * description
 * 
 *@author chenlong
 *@version 1.0,2011-8-17
 */
public class ProductSaleForm {
		private ProductSale productSale;
		
		private String num;
		
		private String discount;
		
		private Set<ProductSaleStock> productSaleStocks = new HashSet<ProductSaleStock>(); 
		
		private Long supplyType;
		
		public ProductSale getProductSale() {
			return productSale;
		}
		
		public void setProductSale(ProductSale productSale) {
			this.productSale = productSale;
		}
		
		public String getNum() {
			return num;
		}
	
		public void setNum(String num) {
			this.num = num;
		}
		
		public String getDiscount() {
			return discount;
		}
		
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		
		public Set<ProductSaleStock> getProductSaleStocks() {
			return productSaleStocks;
		}

		public void setProductSaleStocks(Set<ProductSaleStock> productSaleStocks) {
			this.productSaleStocks = productSaleStocks;
		}

		public Long getSupplyType() {
			return supplyType;
		}

		public void setSupplyType(Long supplyType) {
			this.supplyType = supplyType;
		}

		public String getStocks(){
			StringBuffer sb = new StringBuffer();
			if(CollectionUtils.isNotEmpty(productSaleStocks)){
				for(ProductSaleStock pss : productSaleStocks){
					sb.append("["+pss.getDc().getName()).append("]").append("-");
					if(pss.getDc().getId().equals(Code.DC_8A19)){
						sb.append(pss.getVirtualAvalibleQuantity()).append("|");
					}else{
						if(productSale.getSupplyType().getId().equals(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL)){
							sb.append(pss.getActualAvalibleQuantity()).append("|");
						}else if(productSale.getSupplyType().getId().equals(Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING)){
							sb.append(pss.getVirtualAvalibleQuantity()).append("|");
						}
					}
				}
				return sb.substring(0, sb.length()-1);
			}
			return null;
		}
		
		public int getCanSaleQuantity(){
			if(this.getSupplyType() == null){
				return this.getProductSale().getAvalibleQuantity();
			}else{
				if(Code.ORDER_SALE_TYPE_RAPID.equals(this.getSupplyType())){
					if(CollectionUtils.isNotEmpty(productSaleStocks)){
						for(ProductSaleStock saleStock : productSaleStocks){
							if(Code.DC_8A19.equals(saleStock.getDc().getId())){
								return saleStock.getVirtualAvalibleQuantity();
							}
						}
					}
				}
				return 0;
			}
		}
}
