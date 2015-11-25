/*
 * @(#)ProductSaleStock.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.model.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-7-25
 */
@Entity
@Table(name = "product_sale_stock")
public class ProductSaleStock implements Serializable {
    private static final long serialVersionUID = 5470096896530077828L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productsale")
    private ProductSale productSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dc")
    private Code dc;

    @Column
    private int stock;

    @Column
    private int virtual;

    @Column
    private int sales;
    
    @Column
    private int incorrect;

    public ProductSaleStock() {
        super();
    }

    public ProductSaleStock(ProductSale productSale, Code dc) {
        super();
        this.productSale = productSale;
        this.dc = dc;
    }

    public ProductSaleStock(ProductSale productSale, Code dc, int stock) {
        this(productSale, dc);
        this.stock = stock < MagicNumber.ZERO ? MagicNumber.ZERO : stock;
    }

    public ProductSaleStock(ProductSale productSale, Code dc, int stock, int virtual) {
        this(productSale, dc, stock);
        this.virtual = virtual;
    }

    public ProductSaleStock(ProductSale productSale, Code dc, int stock, int virtual, int sales) {
        this(productSale, dc, stock, virtual);
        this.sales = sales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductSale getProductSale() {
        return productSale;
    }

    public void setProductSale(ProductSale productSale) {
        this.productSale = productSale;
    }

    public Code getDc() {
        return dc;
    }

    public void setDc(Code dc) {
        this.dc = dc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock < MagicNumber.ZERO ? MagicNumber.ZERO : stock;
    }

    public int getVirtual() {
        return virtual;
    }

    public void setVirtual(int virtual) {
        this.virtual = virtual < MagicNumber.ZERO ? MagicNumber.ZERO : virtual;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales < MagicNumber.ZERO ? MagicNumber.ZERO : sales;
    }
    
    public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

    /**
     * 修改库存，+增加，-减少
     * 
     * @param quantity
     * @return
     */
    public void updateStock(int quantity) {
        this.setStock(this.stock + quantity);
    }

    /**
     * 修改虚拟库存，+增加，-减少
     * 
     * @param quantity
     * @return
     */
    public void updateVirtual(int quantity) {
        this.setVirtual(this.virtual + quantity);
    }

    /**
     * 修改库存占用，+增加，-减少
     * 
     * @param quantity
     * @return
     */
    public void updateSales(int quantity) {
        this.setSales(this.sales + quantity);
    }

    /**
     * 获取实物可用量，实物库存 - 占用
     */
    public int getActualAvalibleQuantity() {
        int avalible = this.stock - this.sales;
        avalible = avalible < MagicNumber.ZERO ? MagicNumber.ZERO : avalible;
        return avalible;
    }

    /**
     * 获取虚拟可用量，虚拟库存 - 占用
     */
    public int getVirtualAvalibleQuantity() {
        int avalible = this.virtual - this.sales;
        avalible = avalible < MagicNumber.ZERO ? MagicNumber.ZERO : avalible;
        return avalible;
    }
    
    /**
     * 是否存在不准确库存
     * true:存在  false:不存在
     * @return
     */
    public boolean existIncorrectStock() {
    	return getIncorrect() > 0;
    }

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductSaleStock [productSale=");
        builder.append(productSale.getId());
        builder.append(", dc=");
        builder.append(dc);
        builder.append(", stock=");
        builder.append(stock);
        builder.append(", virtual=");
        builder.append(virtual);
        builder.append(", sales=");
        builder.append(sales);
        builder.append("]");
        builder.append(this.hashCode());
        return builder.toString();
    }

}
