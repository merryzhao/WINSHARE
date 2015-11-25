/*
 * @(#) ControllerConstant.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller;

/**
 * @author Min-Huang XXXX
 * @version 1.0,Jul 27, 2011
 */
public class ControllerConstant {
	
	/**
	 * ModelAndView中存放消息的key
	 */
	public static final String MESSAGE_KEY="message";
	
	/**
	 * ModelAndView中存放消息的key
	 */
	
	public static final String RESULT_KEY="result";
	
	/**
	 * ModelAndView中存放需要JSON序列化的对象Key
	 */
	
	public static final String JSON_OBJECT_KEY="JSON";
	
	/**
	 * 返回结果－表单参数错误
	 */
	public static final short RESULT_PARAMETER_ERROR=0;
	
	/**
	 * 返回结果－成功状态 
	 */
	public static final short RESULT_SUCCESS=1;
	
	/**
	 * 返回结果－服务器内部错误
	 */
	public static final short RESULT_INTERNAL_ERROR=2;
	
	/**
	 * 返回结果－警告
	 */
	public static final short RESULT_WARNING=3;
	
	
	//从productController提取出来
	public static final Long PRODUCTSALESTATUS = 13000L;
	public static final String PRODUCT = "product";
	public static final String STATUS = "status";
	public static final Long BH = 3L;
	public static final String PAGINATION = "pagination";

	public static final String SALESTATUS = "saleStatus";
	public static final String LISTURL = "/product/productList";
	public static final String JOSNRESULT = "/product/product";
	public static final String PRODUCTBARCODE = "productBarcode";
	public static final String SPLITCHAR = "\r\n";
	public static final String SPLITPOINT = ",";
	public static final String OUTERID = "outerId";
	public static final String OUTERIDS = "outerIds";
	public static final String PRODUCTSALES = "productSales";
	public static final String PRODUCTID = "productId";
	public static final String PRODUCTSALEID = "productSaleId";
	public static final String PRODUCTSALEIDS = "productSaleIds";
	public static final String PRODUCTSALE = "productSale";
	public static final String PRODUCTMETAENUMLIST = "/productMetaEnumList";
	public static final String METAID = "mataId";
	public static final String VALUE = "value";
	public static final String PRODUCTNAME = "sellName";
	public static final int PAGESIZE = 30;
	public static final int THREE = 3;
	public static final String MESSAGE = "message";
	public static final String MSG = "msg";

	public static final String REDIRECT_PRODUCT = "redirect:/product/";
	
}
