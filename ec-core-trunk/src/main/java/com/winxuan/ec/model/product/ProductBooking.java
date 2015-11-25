/*
 * @(#)ProductBooking.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Calendar;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * @author  liuan
 * @version 1.0,2011-10-25
 */
@Entity
@Table(name = "product_booking")
public class ProductBooking implements Serializable{

	private static final long serialVersionUID = -507914118774629040L;

	@Id
	@Column(name = "productsale")
	@GeneratedValue(generator = "productsaleFK")
	@GenericGenerator(name = "productsaleFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "productSale"))
	private Long id;
	
	@OneToOne(mappedBy = "booking", targetEntity = ProductSale.class, fetch = FetchType.LAZY)
	private ProductSale productSale;
	
	@Column(name="stockquantity")
	private int stockQuantity;
	
	@Column(name="startdate")
	private Date startDate;
	
	@Column(name="enddate")
	private Date endDate;
	
	@Column(name="_ignore")
	private Integer ignore;
	
	@Column
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="employee")
	private Employee employee;
	
	@Column(name="createdate")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;

	@Column(name = "arrivaldate")
	private Date arrivaldate;
	
	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public Date getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(Date arrivaldate) {
		this.arrivaldate = arrivaldate;
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

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIgnore() {
		return ignore;
	}

	public void setIgnore(Integer ignore) {
		this.ignore = ignore;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public String getPresellDescription(){
		String result = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.endDate);
		int date = calendar.get(calendar.DATE);
		if(date<=MagicNumber.TEN&&date>=MagicNumber.ONE){
			result = "上旬";
		}else if(date<=MagicNumber.TWENTY_ONE&&date>=MagicNumber.ELEVEN){
			result = ("中旬");
		}else if(date<=MagicNumber.THIRTY_ONE&&date>=MagicNumber.TWENTY_TWO){
			result = "下旬";
		}
		return calendar.get(calendar.YEAR)+"年"+(calendar.get(calendar.MONTH)+1)+"月"+result;
		
	}
	
	public ProductBooking copySelf(){
	    ProductBooking pb = new ProductBooking();
	    pb.setArrivaldate(this.getArrivaldate());
	    pb.setCreateDate(this.getCreateDate());
	    pb.setDc(this.getDc());
	    pb.setDescription(this.getDescription());
	    pb.setEmployee(this.getEmployee());
	    pb.setEndDate(this.getEndDate());
	    pb.setIgnore(this.getIgnore());
	    pb.setStartDate(this.getStartDate());
	    pb.setStockQuantity(this.getStockQuantity());
	    return pb;
	    
	}
	
}
