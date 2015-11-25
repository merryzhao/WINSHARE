/*
 * @(#)Category.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.category;

import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaComparator;
import com.winxuan.ec.model.shop.ProShop;
import com.winxuan.ec.model.tree.Tree;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category extends Tree<Category> {

	public static final Long BOOK = 1L;

	public static final Long MEDIA = 7786L;

	public static final Long MALL = 7787L;

	public static final Long ALL_CATEGORY = 1000l;

	private static final long serialVersionUID = -4451565083939842872L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String alias;

	@Column
	private String code;

	@Column(name = "sort")
	private int index;

	@Column
	private boolean available;
	
	
	@Column(name="hasproduct")
    private boolean hasProduct;
	
    @Transient 
	private boolean defaultChecked;
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Category parent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = Category.class)
	@OrderBy("index")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Category> children;

	
	// @OneToMany(cascade=CascadeType.ALL,
	// mappedBy="category",targetEntity=McCategory.class)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "category_mccategory", joinColumns = { @JoinColumn(name = "category") }, inverseJoinColumns = { @JoinColumn(name = "mccategory") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<McCategory> mcCategory;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = ProductMeta.class)
	@JoinTable(name = "category_meta", joinColumns = { @JoinColumn(name = "category") }, inverseJoinColumns = { @JoinColumn(name = "meta") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ProductMeta> productMetaList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "category_proshop", joinColumns = { @JoinColumn(name = "category") }, inverseJoinColumns = { @JoinColumn(name = "proshop") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private ProShop proShop;
	


	public Category() {

	}

	public Category(Long id) {
		this.id = id;
	}

	/**
	 * 获取排过序的有效的productMeta列表 小字段排前面，大字段排后面
	 * 
	 * @return
	 */
	public List<ProductMeta> getOrderedProductMetaList() {
		if (productMetaList == null || productMetaList.isEmpty()) {
			return null;
		}
		List<ProductMeta> list = new ArrayList<ProductMeta>();
		List<ProductMeta> bigList = new ArrayList<ProductMeta>();
		for (ProductMeta meta : productMetaList) {
			if (meta.isAvailable()) {
				if (Code.FIELD_TYPE_TEXT.equals(meta.getType().getId())) {
					bigList.add(meta);
				} else {
					list.add(meta);
				}
			}
		}
		Collections.sort(list, new ProductMetaComparator());
		Collections.sort(bigList, new ProductMetaComparator());
		list.addAll(bigList);
		return list;
	}


	public ProShop getProShop() {
		return proShop;
	}

	public void setProShop(ProShop proShop) {
		this.proShop = proShop;
	}
	
	/**
	 * 获取分类名称，包含其上级分类 例如： 图书-小说-武侠
	 * 
	 * @return
	 */
	public String getAllName() {
		return getPresentName(this);
	}

	public String getAllCategoryName() {
		return getCategoryName(this);
	}

	public String getCategoryName(Category category) {
		if (category != null) {
			    Category parentCategory =category.getParent();
				String cName = getCategoryName(parentCategory);
				return cName == null ? getCategoryHtml(category) : cName
						+ " > " + getCategoryHtml(category);
		}
		return null;
	}

	
	public String getBreadCrumbForEtao(){
		return getCategoryNameForEtao(this);
	}
	
	public String getCategoryNameForEtao(Category category){
		if (category != null) {
		    Category parentCategory =category.getParent();
			String cName = getCategoryNameForEtao(parentCategory);
			return cName == null ? category.getName() : cName
					+ "/" + category.getName();
		}
		return null;
	}
	
	public String getCategoryHref(Category category) {
		ProShop proShop = category.getProShop();
		  if(proShop!=null&&proShop.isAvailable()){
			  return proShop.getUrl();
			  
		  }    
         return "http://list.winxuan.com/" + category.getId();		
	}

	public String getCategoryHtml(Category category) {
		if (category != null) {
			String cNameHtml = "<a target='_blank' class=\'link3\' href=\'"
					+ getCategoryHref(category) + "\'>"
					+ category.getName() + "</a>";
			return cNameHtml;
		}
		return null;
	}
	
	
	public String getCategoryHtml(){
		return this.getCategoryHtml(this);
	}
	
	public String getCategoryHref(){
		return this.getCategoryHref(this);
	}
	
	
	/**
	 * 当前分类是否包含商品
	 * 非实时, 延迟1天
	 * @return
	 */
	public boolean isHasProduct() {
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}
	/**
	 * 获取category的父分类名称
	 * 
	 * @param category
	 * @return
	 */
	private String getPresentName(Category category) {
		if (category != null) {		
			String pName = getPresentName(category.getParent());
			return pName == null ? category.getName() : pName + "-"	+ getCategoryHtml(category);
		}
		return null;
	}

	/**
	 * 检测当前分类是否是category的父分类
	 * 
	 * @param category
	 * @return
	 */
	public boolean checkParent(Category category) {
		if (category == null) {
			return false;
		}
		if (this.getId().equals(category.getId())) {
			return true;
		}
		if (category.getParent() != null
				&& this.getId().equals(category.getParent().getId())) {
			return true;
		} else {
			return checkParent(category.getParent());
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public Set<Category> getChildList() {
		return children;
	}
	
	public boolean getHasChildren(){
		return this.hasChildren();
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public Set<McCategory> getMcCategory() {
		return mcCategory;
	}

	public void setMcCategory(Set<McCategory> mcCategory) {
		this.mcCategory = mcCategory;
	}

	public Set<ProductMeta> getProductMetaList() {
		return productMetaList;
	}

	public void setProductMetaList(Set<ProductMeta> productMetaList) {
		this.productMetaList = productMetaList;
	}

	public void addChild(Category child) {
		child.setCode("unset");
		super.addChild(child);
	}

	public List<Category> getValidChildren() {
		Set<Category> childrens = this.getChildren();
		List<Category> result = null;
		if (childrens != null && childrens.size() > 0) {
			result = new ArrayList<Category>();
			for (Category category : childrens) {
				if (category.available) {
					result.add(category);
				}
			}
		}
		return result;
	}

	public String createCode() {
		List<Category> parentList = getParentList();
		if (parentList == null || parentList.isEmpty()) {
			return "";
		}
		StringBuffer codeBuffer = new StringBuffer();
		for (Category parent : parentList) {
			codeBuffer.append(parent.getId()).append(".");
		}
		if (id != null) {
			codeBuffer.append(id);
		}
		String code = codeBuffer.toString();
		this.code = code;
		return code;
	}

	/**
	 * 判断当前节点,是否为robot的长辈类
	 * 
	 * @param robotCategory
	 * @return
	 */
	public boolean isGrandParent(Category category) {
		if (category == null || category.getId() == null) {
			return false;
		}
		return this.getId().equals(category.getId()) ? true
						: isGrandParent(category.getParent());
	}

	/**
	 * 获取根结点,如 : [图书,百货,音像]
	 * 
	 * @return
	 */
	public Category root() {
		Category parent = this.getParent();
		if (parent == null) {
			return this;
		}
		while (true) {
			if (parent.getParent() == null) {
				return parent;
			}
			parent = parent.getParent();
		}
	}
	/**
	 * 逻辑显示,用于页面渲染
	 * @return
	 */
	public boolean getLogicDisplay(){
		return this.available && this.hasProduct;
	}

	@Override
	public String toString() {
		return this.getId() +" : " + this.name +" " ;
	}

	public void removeChild(Category category) {
		this.children.remove(category);
		
	}
	
	public boolean isDefaultChecked() {
		return defaultChecked;
	}

	public void setDefaultChecked(boolean defaultChecked) {
		this.defaultChecked = defaultChecked;
	}

	
}
