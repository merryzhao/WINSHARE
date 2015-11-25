package com.winxuan.ec.model.shop;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.tree.Tree;


/**
 * 
 * @author xuan jiu dong
 *
 */
@Entity
@Table(name = "shopcategory")
public class ShopCategory extends Tree<ShopCategory>  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户独立节点,店铺类别树初始化用
	 */
	private static final String INDEPENDENT_NODE = "independent node";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@Column(name = "maintainid")
	private Long maintainId;

	@Column
	private String name;

	@Column(name = "imgurl")
	private String imgUrl;
	
	@Column
	private boolean available;
	
	@Column(name="productnum")
	private Long productNum;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent")
	private ShopCategory parent;
	
	@Column(name="index_")
	private int index;

	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, mappedBy="parent",targetEntity=ShopCategory.class)
	@OrderBy("index")
	private Set<ShopCategory> children;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="shop")
	private Shop shop;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "product_sale_shopcategory", joinColumns = { @JoinColumn(name = "shopcategory", updatable = false) }
		, inverseJoinColumns = { @JoinColumn(name = "productsale", updatable = false) }) 
	private Set<ProductSale> productSaleList;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = ProductMeta.class)
	@JoinTable(name = "shopcategory_meta", joinColumns = { @JoinColumn(name = "shopcategory") }, inverseJoinColumns = { @JoinColumn(name = "meta") })
	@IndexColumn(name = "_index", base = 0)
	private List<ProductMeta> productMetaList;
	
	

	public ShopCategory() {
		super();
	}
	
	
	public ShopCategory(Long id) {
		super();
		this.id=id;
	}
	
	/**
	 * 获取用户分类的根节点
	 * @return
	 */
	public static ShopCategory getShopRootCategory(){
		ShopCategory c = new ShopCategory();
		c.setName(INDEPENDENT_NODE);
		c.setProductNum(0L);
		return c;
	}

	public void setProductSaleList(Set<ProductSale> productSaleList) {
		this.productSaleList = productSaleList;
	}

	public Set<ProductSale> getProductSaleList() {
		return productSaleList;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(Long maintainId) {
		this.maintainId = maintainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 获得该分类及父类的名字以>隔开，出去根父类
	 * @return
	 */
	public String getAllName(){
		List<ShopCategory> list = this.getParentList();
		StringBuffer sb = new StringBuffer();
			for(int i=1;i<list.size();i++){
				sb.append(list.get(i).getName()+">");
			}
		sb.append(this.getName());
		return sb.toString();
	}
	

	/**
	 * 是否有下级节点
	 */
	@Override
	public boolean isRoot() {
		return this.getChildren() == null;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public ShopCategory getParent() {
		
		return this.parent;
	}

	@Override
	public void setParent(ShopCategory parent) {
		this.parent = parent;
		
	}

	@Override
	public Set<ShopCategory> getChildren() {
		return this.children;
	}

	@Override
	public void setChildren(Set<ShopCategory> children) {
		this.children = children;
	}

	public Long getProductNum() {
		return productNum;
	}

	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}

	
	public List<ProductMeta> getProductMetaList() {
		return productMetaList;
	}


	public void setProductMetaList(List<ProductMeta> productMetaList) {
		this.productMetaList = productMetaList;
	}

}
