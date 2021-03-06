/*
 * @(#)ProductSalePerformance.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-9-30
 */
@Entity
@Table(name = "product_sale_performance")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSalePerformance  implements Serializable{

	private static final long serialVersionUID = -5553931871512605388L;

	@Id
	@Column(name = "productsale")
	@GeneratedValue(generator = "productsaleFK")
	@GenericGenerator(name = "productsaleFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "productSale"))
	private Long id;
	
	@OneToOne(mappedBy = "performance", targetEntity = ProductSale.class, fetch = FetchType.LAZY)
	private ProductSale productSale;
	
	@Column
	private int totalSale;
	
	@Column
	private int monthSale;
	
	@Column
	private int weekSale;
	
	@Column
	private int totalVisit;
	
	@Column
	private int monthVisit;
	
	@Column
	private int weekVisit;
	
	@Column
	private int totalComment;
	
	@Column
	private int monthComment;
	
	@Column
	private int weekComment;
	
	@Column
	private int totalFavorite;
	
	@Column
	private int monthFavorite;
	
	@Column
	private int weekFavorite;
	
	@Column
	private int totalDigging;
	
	@Column
	private int monthDigging;
	
	@Column
	private int weekDigging;
	
	@Column
	private int rank;
	
	@Column
	private Date firstOnShelfTime;
	
	@Column
	private Date lastOnShelfTime;
	
	@Column
	private int firstStock;
	
	@Column
	private String ct1;

	@Column
	private String ct2;
	
	@Column
	private String ct3;

	@Column
	private String ct4;

	@Column
	private String ct5;

	@Column
	private BigDecimal discount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salestatus")
	private Code saleStatus;

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

	public int getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(int totalSale) {
		this.totalSale = totalSale;
	}

	public int getMonthSale() {
		return monthSale;
	}

	public void setMonthSale(int monthSale) {
		this.monthSale = monthSale;
	}

	public int getWeekSale() {
		return weekSale;
	}

	public void setWeekSale(int weekSale) {
		this.weekSale = weekSale;
	}

	public int getTotalVisit() {
		return totalVisit;
	}

	public void setTotalVisit(int totalVisit) {
		this.totalVisit = totalVisit;
	}

	public int getMonthVisit() {
		return monthVisit;
	}

	public void setMonthVisit(int monthVisit) {
		this.monthVisit = monthVisit;
	}

	public int getWeekVisit() {
		return weekVisit;
	}

	public void setWeekVisit(int weekVisit) {
		this.weekVisit = weekVisit;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public int getMonthComment() {
		return monthComment;
	}

	public void setMonthComment(int monthComment) {
		this.monthComment = monthComment;
	}

	public int getWeekComment() {
		return weekComment;
	}

	public void setWeekComment(int weekComment) {
		this.weekComment = weekComment;
	}

	public int getTotalFavorite() {
		return totalFavorite;
	}

	public void setTotalFavorite(int totalFavorite) {
		this.totalFavorite = totalFavorite;
	}

	public int getMonthFavorite() {
		return monthFavorite;
	}

	public void setMonthFavorite(int monthFavorite) {
		this.monthFavorite = monthFavorite;
	}

	public int getWeekFavorite() {
		return weekFavorite;
	}

	public void setWeekFavorite(int weekFavorite) {
		this.weekFavorite = weekFavorite;
	}

	public int getTotalDigging() {
		return totalDigging;
	}

	public void setTotalDigging(int totalDigging) {
		this.totalDigging = totalDigging;
	}

	public int getMonthDigging() {
		return monthDigging;
	}

	public void setMonthDigging(int monthDigging) {
		this.monthDigging = monthDigging;
	}

	public int getWeekDigging() {
		return weekDigging;
	}

	public void setWeekDigging(int weekDigging) {
		this.weekDigging = weekDigging;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Date getFirstOnShelfTime() {
		return firstOnShelfTime;
	}

	public void setFirstOnShelfTime(Date firstOnShelfTime) {
		this.firstOnShelfTime = firstOnShelfTime;
	}

	public Date getLastOnShelfTime() {
		return lastOnShelfTime;
	}

	public void setLastOnShelfTime(Date lastOnShelfTime) {
		this.lastOnShelfTime = lastOnShelfTime;
	}

	public int getFirstStock() {
		return firstStock;
	}

	public void setFirstStock(int firstStock) {
		this.firstStock = firstStock;
	}

	public String getCt1() {
		return ct1;
	}

	public void setCt1(String ct1) {
		this.ct1 = ct1;
	}

	public String getCt2() {
		return ct2;
	}

	public void setCt2(String ct2) {
		this.ct2 = ct2;
	}

	public String getCt3() {
		return ct3;
	}

	public void setCt3(String ct3) {
		this.ct3 = ct3;
	}

	public String getCt4() {
		return ct4;
	}

	public void setCt4(String ct4) {
		this.ct4 = ct4;
	}

	public String getCt5() {
		return ct5;
	}

	public void setCt5(String ct5) {
		this.ct5 = ct5;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Code getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Code saleStatus) {
		this.saleStatus = saleStatus;
	}
}
