package com.winxuan.ec.model.returnorder;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @description:
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2015年1月16日 下午1:26:28
 */
@Entity
@Table(name = "returnorder_package_item")
public class ReturnOrderPackageItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnorder_package_id")
	private ReturnOrderPackage returnOrderPackage;
	
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "listprice")
	private BigDecimal listprice;
	
	@Column(name="eccode")
	private String eccode;
	
	@Column(name="outerid")
	private String outerid;
	
	@Column(name = "manufacturer")
	private String manufacturer;
	
	@Column(name = "batch")
	private String batch;
	
	@Column(name = "s_isbn")
	private String sIsbn;

	public ReturnOrderPackageItem() {
		super();
	}

	public ReturnOrderPackageItem(String isbn, int quantity, String name,
			BigDecimal listprice, String eccode) {
		super();
		this.isbn = isbn;
		this.quantity = quantity;
		this.name = name;
		this.listprice = listprice;
		this.eccode = eccode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrderPackage getReturnOrderPackage() {
		return returnOrderPackage;
	}

	public void setReturnOrderPackage(ReturnOrderPackage returnOrderPackage) {
		this.returnOrderPackage = returnOrderPackage;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getListprice() {
		return listprice;
	}

	public void setListprice(BigDecimal listprice) {
		this.listprice = listprice;
	}

	public String getEccode() {
		return eccode;
	}

	public void setEccode(String eccode) {
		this.eccode = eccode;
	}

	public String getOuterid() {
		return outerid;
	}

	public void setOuterid(String outerid) {
		this.outerid = outerid;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSIsbn() {
		return sIsbn;
	}

	public void setSIsbn(String sIsbn) {
		this.sIsbn = sIsbn;
	}
	
}
