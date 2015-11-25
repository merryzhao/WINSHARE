/*
 * @(#)Fragment.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.cm;

import java.util.ArrayList;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.product.ProductSale;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-10-26
 */
@Entity
@Table(name = "cm_fragment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fragment {

	public static final short TYPE_PRODUCT = 1;

	public static final short TYPE_NEWS = 2;

	public static final short TYPE_LINK = 3;

	public static final short TYPE_TEXT = 4;
	
	public static final short TYPE_RANDOM = 5;

	public static final short RULE_MANUAL = 1;

	public static final short RULE_AUTO_PROMOTION = 4;

	public static final short RULE_AUTO_PROMOTION_WAIT = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 */
	@Column
	private String name;

	/**
	 */
	@Column
	private short type;

	@Column
	private short rule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private Category category;

	/**
	 */
	@Column
	private String description;

	@Column(name = "jspfile")
	private String jspFile;

	/**
	 */
	@Column(name = "page")
	private String page;

	/**
	 */
	@Column(name = "index_")
	private Long index;

	/**
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "fragment", targetEntity = Element.class)
	@OrderBy("sort")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Element> elements;

	@Column
	private int quantity;

	@Column(name = "imagetype")
	private int imageType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "fragment", targetEntity = CmsConfig.class)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<CmsConfig> cmsCofig;

	public Fragment() {

	}

	public Fragment(String page, long index) {
		this.page = page;
		this.index = index;
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

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getRule() {
		return rule;
	}

	public void setRule(short rule) {
		this.rule = rule;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Element> getElements() {
		return elements;
	}

	public List<Content> getContentList() {
		return getAllOrPartContentList(false);
	}
	
	/**
	 * 这个方法与上面的方法差别很小，可通过传递参数的形式优化减少代码，但不知还有哪些地方引用了上面的函数，
	 * 暂时不更改，以后优化
	 * @return
	 */
	public List<Content> getAllOrPartContentList(boolean allFlag){
		List<Content> contentList = new ArrayList<Content>();

		if (CollectionUtils.isEmpty(elements)) {
			return null;
		}
		for (Element element : elements) {
			Content content = element.getContent();
			if (content instanceof ProductSale) {
				ProductSale ps = (ProductSale) content;
				ps.setImageUrl(ps.getProduct().getImageUrlByType(
						(short) imageType));
				contentList.add(ps);
			} else {
				contentList.add(content);
			}				
			if(!allFlag && contentList.size() == quantity) {
				break;
			}
		}
		return contentList;		
	}

	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getJspFile() {
		return jspFile;
	}

	public void setJspFile(String jspFile) {
		this.jspFile = jspFile;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public Set<CmsConfig> getCmsCofig() {
		return cmsCofig;
	}

	public void setCmsCofig(Set<CmsConfig> cmsCofig) {
		this.cmsCofig = cmsCofig;
	}
	
	
	public void addCmsConfig(CmsConfig cmsCofig){
		if(this.cmsCofig==null){
			this.cmsCofig = new HashSet<CmsConfig>();			
		}
		this.cmsCofig.add(cmsCofig);
		
	}


}
