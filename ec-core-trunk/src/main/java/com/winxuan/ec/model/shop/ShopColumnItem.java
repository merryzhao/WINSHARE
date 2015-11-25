package com.winxuan.ec.model.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.product.ProductSale;

/**
 * @author yuhu
 * @version 1.0,2011-11-3下午04:34:12
 */
@Entity
@Table(name = "shop_columnitem")
public class ShopColumnItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopcolumn")
	private ShopColumn shopColumn;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale",nullable=true)
	private ProductSale productSale;
	
	@Column
	private String img;
	
	@Column
	private String href;
	
	@Column(name="index_")
	private Integer index;

	@Column
	private boolean available;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ShopColumn getShopColumn() {
		return shopColumn;
	}

	public void setShopColumn(ShopColumn shopColumn) {
		this.shopColumn = shopColumn;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public String getImgUrl(){
		return "http://static.winxuancdn.com/upload/shopColumn/"+img;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
