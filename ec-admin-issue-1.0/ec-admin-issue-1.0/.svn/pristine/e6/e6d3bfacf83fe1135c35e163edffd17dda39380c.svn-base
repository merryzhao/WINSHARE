/*
 * @(#)ProductSaleForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
/**
 *
 */
package com.winxuan.ec.admin.controller.product;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.shop.Shop;

/**
 * description
 *
 * @author df.rsy
 * @version 1.0, 2011-11-2
 */
public class ProEditForm {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //id
    private Long id;

    //销售名称
    @NotBlank(message = "销售名称不能为空")
    private String sellName;

    //促销短语
    private String promValue;

    //副标题
    private String subheading;

    //定价
    private BigDecimal listPrice;

    //储配方式
    private Long storageType;

    //上下架状态
    private Long saleStatus;

    //可用量
    private String activeQuantity;
    //库存量
    private String stockQuantity;

    //预售信息修改

    //预售描述
    private String bookDescription;

    //预售开始时间
    private String bookStartDate;

    //预售结束时间
    private String bookEndDate;

    //备注
    private String remark;

    private Long[] categories;

    public Long[] getCategories() {
        return categories;
    }

    public void setCategories(Long[] categories) {
        this.categories = categories;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSellName() {
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public String getPromValue() {
        return promValue;
    }

    public void setPromValue(String promValue) {
        this.promValue = promValue;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Long getStorageType() {
        return storageType;
    }

    public void setStorageType(Long storageType) {
        this.storageType = storageType;
    }

    public Long getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Long saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getActiveQuantity() {
        return activeQuantity;
    }

    public void setActiveQuantity(String activeQuantity) {
        this.activeQuantity = activeQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFormValue(ProductSale productSale) {
        if (productSale != null) {
            this.subheading = productSale.getSubheading();
            this.id = productSale.getId();
            this.sellName = productSale.getSellName();
            this.promValue = productSale.getPromValue();
            this.listPrice = productSale.getProduct().getListPrice();
            this.saleStatus = productSale.getSaleStatus().getId();
            this.stockQuantity = String.valueOf(productSale.getStockQuantity());
            this.storageType = productSale.getStorageType().getId();
            this.activeQuantity = String.valueOf(productSale.getStockQuantity() - productSale.getSaleQuantity());
            if (productSale.getBooking() != null) {
                this.bookEndDate = format.format(productSale.getBooking().getEndDate());
                this.bookStartDate = format.format(productSale.getBooking().getStartDate());
                this.bookDescription = productSale.getBooking().getDescription();
            }
        }
    }

    public ProductSale getProductSale(ProductSale productSale) throws ParseException {
    	if(productSale == null){
    		return null;
    	}
	
        productSale.setSubheading(subheading);
        productSale.setSellName(this.sellName);
        productSale.setPromValue(this.promValue);
        productSale.setStorageType(new Code(this.storageType));
        productSale.setUpdateTime(new Date());
        productSale.setRemark(remark);        
        
        if (productSale.getBooking() != null) {
            productSale.getBooking().setDescription(bookDescription);
            productSale.getBooking().setStartDate(format.parse(bookStartDate));
            productSale.getBooking().setEndDate(format.parse(bookEndDate));
            productSale.getBooking().setStockQuantity(Integer.valueOf(this.stockQuantity));
        }

        if (!StringUtils.isBlank(this.stockQuantity) && Shop.WINXUAN_SHOP != productSale.getShop().getId()) {
        	productSale.setStockQuantity(Integer.valueOf(this.stockQuantity));
        }
        
        return productSale;        
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookStartDate(String startDate) {
        this.bookStartDate = startDate;
    }

    public String getBookStartDate() {
        return bookStartDate;
    }

    public void setBookEndDate(String endDate) {
        this.bookEndDate = endDate;
    }

    public String getBookEndDate() {
        return bookEndDate;
    }
}
