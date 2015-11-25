/*
 * @(#)ProductQueryForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 商品信息查询表单验证类
 *
 * @author  
 * @version 1.0,2011-11-1
 */
public class ProductQueryForm {
	//供应类型
	private List<String> supplyTypes;
	//平台商品主分类
	private Long category;
	// 编码类型
	private String coding;
	// 编码内容
	private String codingContent;
	// 商品名称关键字
	private String productName;
	// 卖家名称
	private String sellerName;
	// 作者
	private String productAuthor;
	// MC分类
	private String productMcCategory;
	// 上下架状态
	private Long status;
    //是否显示更多条件
	private boolean ismore;	
	//是否套装
	private int complex=-1;
	//是否有图
	private int picture=-1;
	//储配方式
	private Long storageType;
	//店铺ID
	private Long shopId;
	//出版社
	private String manufacturer;
	//出版开始时间
	private String productionStartDate;
	//出版结束时间
	private String productionEndDate;
	//库存量
	private Integer stockNumberMin;
	private Integer stockNumberMax;
	
	//码洋区间
	private BigDecimal listpriceMin;
	private BigDecimal listpriceMax;
	
	//折扣区间
	private BigDecimal discountMin;
	private BigDecimal discountMax;
	
	/**
	 * 查询条件是否可以查询
	 * @return
	 */
	public boolean canQuery(){
		if(category == null 
				&& StringUtils.isBlank(codingContent)
				&& StringUtils.isBlank(productName)
				&& StringUtils.isBlank(productMcCategory)
				&& StringUtils.isBlank(manufacturer)
				&& StringUtils.isBlank(productionStartDate)
				&& CollectionUtils.isEmpty(supplyTypes)
				&& complex == -1){
			return false;
		}
		return true;
	}
	
	public BigDecimal getListpriceMin() {
        return listpriceMin;
    }

    public void setListpriceMin(BigDecimal listpriceMin) {
        this.listpriceMin = listpriceMin;
    }

    public BigDecimal getListpriceMax() {
        return listpriceMax;
    }

    public void setListpriceMax(BigDecimal listpriceMax) {
        this.listpriceMax = listpriceMax;
    }

    public BigDecimal getDiscountMin() {
        return discountMin;
    }

    public void setDiscountMin(BigDecimal discountMin) {
        this.discountMin = discountMin;
    }

    public BigDecimal getDiscountMax() {
        return discountMax;
    }

    public void setDiscountMax(BigDecimal discountMax) {
        this.discountMax = discountMax;
    }

    public Integer getStockNumberMin() {
		return stockNumberMin;
	}

	public void setStockNumberMin(Integer stockNumberMin) {
		this.stockNumberMin = stockNumberMin;
	}

	public Integer getStockNumberMax() {
		return stockNumberMax;
	}

	public void setStockNumberMax(Integer stockNumberMax) {
		this.stockNumberMax = stockNumberMax;
	}
	
	public String getProductionStartDate() {
		return productionStartDate;
	}

	public void setProductionStartDate(String productionStartDate) {
		this.productionStartDate = productionStartDate;
	}

	public String getProductionEndDate() {
		return productionEndDate;
	}

	public void setProductionEndDate(String productionEndDate) {
		this.productionEndDate = productionEndDate;
	}
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Long getShopId() {
		if(shopId!=null){
			if(new Long(0).equals(shopId)){
				shopId=null;
			}
		}
		return shopId; 
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getStorageType() {
		if(storageType!=null){
			if(new Long(0).equals(storageType)){
				storageType=null;
			}
		}
		return storageType;
	}

	public void setStorageType(Long storageType) {
		this.storageType = storageType;
	}

	public List<Long> getLongSupplyTypes() {
		List<Long> longs=new ArrayList<Long>();
		if(supplyTypes==null){
			return null;
		}
  		for(String s:supplyTypes){ 
			if(!StringUtils.isBlank(s)){
				longs.add(Long.valueOf(s));
			}
		}
		return longs;
 	}
	
	public List<String> getSupplyTypes() {
		return supplyTypes;
 	}

	public void setSupplyTypes(List<String> supplyTypes) {
 		this.supplyTypes = supplyTypes;
	}

	public boolean isIsmore() {
		return ismore;
	}

	public void setIsmore(boolean ismore) {
		this.ismore = ismore;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding.trim();
	}

	public String getCodingContent() {
		return codingContent;
	}

	public void setCodingContent(String codingContent) {
		codingContent = codingContent.trim();
		if ("".equals(codingContent)) {
			this.codingContent = null;
		} else {
			this.codingContent = codingContent;
		}
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		productName = productName.trim();
		if ("".equals(productName)) {
			this.productName = null;
		} else {
			this.productName = productName;
		}
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		sellerName = sellerName.trim();
		if ("".equals(sellerName)) {
			this.sellerName = null;
		} else {
			this.sellerName = sellerName;
		}
	}

	public String getProductAuthor() {
		return productAuthor;
	}

	public void setProductAuthor(String productAuthor) {
		productAuthor = productAuthor.trim();
		if ("".equals(productAuthor)) {
			this.productAuthor = null;
		} else {
			this.productAuthor = productAuthor;
		}
	}

	public String getProductMcCategory() {
		return productMcCategory;
	}

	public void setProductMcCategory(String productMcCategory) {
		productMcCategory = productMcCategory.trim();
		if ("".equals(productMcCategory)) {
			this.productMcCategory = null;
		} else {
			this.productMcCategory = productMcCategory;
		}
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		if (status.intValue() == 0) {
			this.status = null;
		} else {
			this.status = status;
		}
	}
    //用于模糊查询
	public String getProductNameQuery() {
		if (productName != null) {
			return "%" + productName + "%";
		}
		return null;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getCategory() {
		return category;
	}

	public void setComplex(int complex) {
		this.complex = complex;
	}
	public int getComplex() {
		return complex;
	}
	public Boolean isBooleanComplex() {
		if (complex == -1){
			return null;
		}
		return (complex == 1 || complex == 2) ? true : false;
	}
	
	public short kindComplex(){
		boolean flag = false;
		if (this.isBooleanComplex() != null){
			flag = this.isBooleanComplex();
		}
		if (flag){
			complex = (short)complex;
		}
		return (short) complex;
	}
 
	public void setPicture(int picture) {
		this.picture = picture;
	}
	public int getPicture() {
		return  picture;
	}


	public Boolean getBooleanPicture() {
		if(picture==-1){
			return null;
		}
		return picture==1?true:false;	
	}

}
