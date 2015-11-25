/*
 * @(#)ProductSaleCT.java
 *
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * description
 * @author  huangyixiang
 * @version 2012-3-31
 */
@Entity
@Table(name = "product_sale_amzn_ct")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSaleCT  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6142489405047690231L;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productsale")
	private ProductSalePerformanceOnShelf productSalePerformanceOnShelf;
	
	@Column
	private Integer ct1;

	@Column
	private Integer ct2;
	
	@Column
	private Integer ct3;

	@Column
	private Integer ct4;

	@Column
	private Integer ct5;
	
	@Column
	private Integer ct6;
	
	@Column
	private Integer ct7;
	
	@Column
	private Integer ct8;

	public Integer getCt1() {
		return ct1;
	}

	public void setCt1(Integer ct1) {
		this.ct1 = ct1;
	}

	public Integer getCt2() {
		return ct2;
	}

	public void setCt2(Integer ct2) {
		this.ct2 = ct2;
	}

	public Integer getCt3() {
		return ct3;
	}

	public void setCt3(Integer ct3) {
		this.ct3 = ct3;
	}

	public Integer getCt4() {
		return ct4;
	}

	public void setCt4(Integer ct4) {
		this.ct4 = ct4;
	}

	public Integer getCt5() {
		return ct5;
	}

	public void setCt5(Integer ct5) {
		this.ct5 = ct5;
	}

	public Integer getCt6() {
		return ct6;
	}

	public void setCt6(Integer ct6) {
		this.ct6 = ct6;
	}

	public Integer getCt7() {
		return ct7;
	}

	public void setCt7(Integer ct7) {
		this.ct7 = ct7;
	}

	public Integer getCt8() {
		return ct8;
	}

	public void setCt8(Integer ct8) {
		this.ct8 = ct8;
	}

	public ProductSalePerformanceOnShelf getProductSalePerformanceOnShelf() {
		return productSalePerformanceOnShelf;
	}

	public void setProductSalePerformanceOnShelf(
			ProductSalePerformanceOnShelf productSalePerformanceOnShelf) {
		this.productSalePerformanceOnShelf = productSalePerformanceOnShelf;
	}
	
}
