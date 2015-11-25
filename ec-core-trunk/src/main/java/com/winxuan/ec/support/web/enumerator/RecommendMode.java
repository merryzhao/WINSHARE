/*
 * @(#)ProductRecommendMode.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.enumerator;

import com.winxuan.ec.model.product.ProductRecommendation;
import com.winxuan.ec.model.search.SearchRecommendation;

/**
 * description
 * @author  huangyixiang
 * @version 2011-11-15
 */
public enum RecommendMode {

	
	//根据购买记录推荐商品
	BUY(ProductRecommendation.MODE_BUY,RecommendMode.PRODUCT_BASE_TABLE,RecommendMode.PRODUCT_TABLE,RecommendMode.PRODUCT_ITEM_ID_NAME),
	//根据浏览历史推荐商品
	VISIT(ProductRecommendation.MODE_VIEW,RecommendMode.PRODUCT_BASE_TABLE,RecommendMode.PRODUCT_TABLE,RecommendMode.PRODUCT_ITEM_ID_NAME),
	//相关搜索
	SEARCH(SearchRecommendation.MODE_SEARCH,RecommendMode.SEARCH_BASE_TABLE,RecommendMode.SEARCH_TABLE,RecommendMode.SEARCH_ITEM_ID_NAME);
	
	public static final int TEMP_MODE = 100;//临时mode
	
	private static final String PRODUCT_BASE_TABLE= "product_recommend_base_data";
	private static final String PRODUCT_TABLE= "product_recommendation";
	private static final String PRODUCT_ITEM_ID_NAME = "commodityid";
	
	private static final String SEARCH_BASE_TABLE = "search_recommend_base_data";
	private static final String SEARCH_TABLE = "search_recommendation";
	private static final String SEARCH_ITEM_ID_NAME = "item";
	
	private short mode;
	private String baseTable;
	private String recommendTable;
	private String itemIdName;
	
	private RecommendMode(short mode, String baseTable, String recommendTable,String itemIdName){
		this.mode = mode;
		this.baseTable = baseTable;
		this.recommendTable = recommendTable;
		this.itemIdName = itemIdName;
	}
	
	public short getMode() {
		return mode;
	}

	public void setMode(short mode) {
		this.mode = mode;
	}

	public String getBaseTable() {
		return baseTable;
	}

	public void setBaseTable(String baseTable) {
		this.baseTable = baseTable;
	}

	public String getRecommendTable() {
		return recommendTable;
	}

	public void setRecommendTable(String recommendTable) {
		this.recommendTable = recommendTable;
	}

	public String getItemIdName() {
		return itemIdName;
	}

	public void setItemIdName(String itemIdName) {
		this.itemIdName = itemIdName;
	}
	
}
