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

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-3 上午11:19:44 --!
 * @description:
 ******************************** 
 */

@Entity
@Table(name = "product_9yue")
public class ProductSeptember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1034066200324374040L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "9yue_bookid")
	private Long septemberBookId;

	@Column(name = "9yue_productid")
	private Long septemberProductId;

	@Column
	private String isbn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productsaleid")
	private ProductSale productSale;

	private Long hasfangzheng;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSeptemberBookId() {
		return septemberBookId;
	}

	public void setSeptemberBookId(Long septemberBookId) {
		this.septemberBookId = septemberBookId;
	}

	public Long getSeptemberProductId() {
		return septemberProductId;
	}

	public void setSeptemberProductId(Long septemberProductId) {
		this.septemberProductId = septemberProductId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getHasfangzheng() {
		return hasfangzheng;
	}

	public void setHasfangzheng(Long hasfangzheng) {
		this.hasfangzheng = hasfangzheng;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

}
