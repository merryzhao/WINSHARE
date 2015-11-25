package com.winxuan.ec.model.shop;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 
 * @author xuan jiu dong
 * 
 */
@Entity
@Table(name = "shop_column")
public class ShopColumn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659100775512604833L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_shop")
	private Shop shop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopcategory")
	private ShopCategory shopcategory;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shopColumn", targetEntity = ShopColumnItem.class)
	@OrderBy("index")
	private Set<ShopColumnItem> shopColumnItems;

	@Column
	private long productnum;
	
	@Column(name="index_")
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ShopCategory getShopcategory() {
		return shopcategory;
	}

	public void setShopcategory(ShopCategory shopcategory) {
		this.shopcategory = shopcategory;
	}

	public Set<ShopColumnItem> getShopColumnItems() {
		for (Iterator<ShopColumnItem> it = this.shopColumnItems.iterator(); it
				.hasNext();) {
			ProductSale ps = it.next().getProductSale();
			if (ps != null) {
				if (!ps.canDisplay()) {
					it.remove();
				}
			}
		}
		return shopColumnItems;
	}

	public void addShopColumnItem(ShopColumnItem item) {
		
		if(shopColumnItems == null){
			shopColumnItems = new HashSet<ShopColumnItem>();
		}
		shopColumnItems.add(item);
	}
	
	public void setShopColumnItems(Set<ShopColumnItem> shopColumnItems) {
		this.shopColumnItems = shopColumnItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProductnum() {
		return productnum;
	}

	public void setProductnum(long productnum) {
		this.productnum = productnum;
	}

}
