package com.winxuan.ec.model.shop;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.category.Category;

/**
 * @author yuhu
 * @version 1.0,2011-10-20下午01:38:25
 */
@Entity
@Table(name="shop_usualcategory")
public class ShopUsualCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3600595889795026539L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shop")
	private Shop shop;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category")
	private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}
