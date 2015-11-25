/*
 * @(#)Rule.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.fragment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-22
 */
public enum Rule {
	
	//首页
	R_128((short)2,30L,60L,true,true,20,OrderBy.SELL_COUNT_MONTH),
	R_544((short)3,null,null,true,true,20,OrderBy.SELL_COUNT_MONTH),
	
	//图书频道首页
	R_362((short)6,null,null,true,true,20,OrderBy.VISIT_MONTH),
	R_363((short)7,null,null,true,true,20,OrderBy.FAVORITE_MONTH),
	R_364((short)8,null,null,true,true,20,OrderBy.DIGGING_MONTH),
	
	//音像频道首页
	R_443((short)9,60L,null,true,true,10,OrderBy.SELL_COUNT_MONTH),
	R_478((short)10,null,null,true,true,10,OrderBy.SELL_COUNT_MONTH),
	
	//百货频道首页
	R_510((short)11,null,null,true,null,null,OrderBy.VISIT_MONTH),
	
	//图书专业店
	R_602((short)12,30L,null,true,true,5,OrderBy.SELL_COUNT_MONTH),
	R_603((short)13,30L,90L,true,true,50,OrderBy.SELL_COUNT_MONTH),
	R_631((short)14,null,null,true,true,10,OrderBy.SELL_COUNT_MONTH),
	R_632((short)15,30L,90L,true,true,10,OrderBy.VISIT_MONTH),//临时
	R_633((short)16,null,null,true,true,10,OrderBy.FAVORITE_MONTH),
	R_608((short)17,null,null,true,true,10,OrderBy.FAVORITE_MONTH,true,null),
	R_609((short)18,null,null,null,null,null,OrderBy.FAVORITE_MONTH,null,new BigDecimal("0.5")),
	R_621((short)19,null,null,true,true,10,OrderBy.SELL_COUNT_MONTH), //专业店-主编推荐
	
	//音像二级首页
	R_804((short)20,90L,null,null,null,null,OrderBy.VISIT_MONTH),
	R_805((short)21,90L,null,null,null,null,OrderBy.ON_SHELF_TIME),
	R_851((short)22,90L,null,null,null,null,OrderBy.VISIT_MONTH),
	R_861((short)23,90L,null,null,null,null,OrderBy.SELL_COUNT_MONTH),
	R_807((short)24,null,null,null,null,null,OrderBy.VISIT_MONTH,null,new BigDecimal("0.65"))
	;
	
	public static Map<Short,Rule> rules;
	
	private short id;
	private String code;//分类代码
	private Long onShelfDate;//最近xx天上架
	private Long publishDate;//出版日期为xx天内
	private Boolean onShelf;//是否上架
	private Boolean hasImage;//是否有图片
	private Integer quantity;//库存大于xx
	private OrderBy order;//排序
	private int size; //条数
	private Boolean complex;//是否是套装书
	private BigDecimal discount;//折扣在几折以下
	
	private Rule(short id,
			Long onShelfDate,
			Long publishDate,
			Boolean onShelf,
			Boolean hasImage,
			Integer quantity,
			OrderBy order){
		this.id = id;
		this.onShelfDate = onShelfDate;
		this.publishDate = publishDate;
		this.onShelf = onShelf;
		this.hasImage = hasImage;
		this.quantity = quantity;
		this.order = order;
		
		addToMap(this);
	};
	private Rule(short id,
			Long onShelfDate,
			Long publishDate,
			Boolean onShelf,
			Boolean hasImage,
			Integer quantity,
			OrderBy order,
			Boolean complex,
			BigDecimal discount){
		this(id, onShelfDate, publishDate, onShelf, hasImage, quantity, order);
		this.complex = complex;
		this.discount = discount;
		
		addToMap(this);
	};
	
	private static void addToMap(Rule rule){
		if(rules == null){
			rules = new HashMap<Short, Rule>();
		}
		rules.put(rule.getId(),rule);
	}
	
	/**
	 * 
	 * @author Administrator
	 *
	 */
	public enum OrderBy {
		SELL_COUNT_MONTH((short)0),
		ON_SHELF_TIME((short)1),
		VISIT_MONTH((short)2),
		FAVORITE_MONTH((short)3),
		DIGGING_MONTH((short)4)
		;
		
		private short order;
		private OrderBy(short order){
			this.order = order;
		}
		public short getOrder() {
			return order;
		}
		public void setOrder(short order) {
			this.order = order;
		}
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOnShelfDate() {
		return onShelfDate;
	}

	public void setOnShelfDate(Long onShelfDate) {
		this.onShelfDate = onShelfDate;
	}

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

	public Boolean getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(Boolean onShelf) {
		this.onShelf = onShelf;
	}

	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderBy getOrder() {
		return order;
	}

	public void setOrder(OrderBy order) {
		this.order = order;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Boolean getComplex() {
		return complex;
	}

	public void setComplex(Boolean complex) {
		this.complex = complex;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	

}
