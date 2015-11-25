package com.winxuan.ec.task.model.ec;

import java.util.Date;

/**
 * Ec-商品分类状态.
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
public class EcProductCategoryStatus {
	/**
	 * 直接匹配卓越分类
	 */
	public static final int CATEGORY_STATUS_AMAZON = 1;
	/**
	 * MC关联卓越分类
	 */
	public static final int CATEGORY_STATUS_MC = 2;
	/**
	 * 未匹配的分类
	 */
	public static final int CATEGORY_STATUS_UNIDENTIFIED = 5;
	
	public static final int NEW_TRUE = 1;
	public static final int NEW_FALSE = 0;
	
	
	//商品ID
	private Long product;
	//状态
	private int status;
	//同步最大限期
	private Date maxdate;
	//是否为新品
	private int isnew;
	//isbn- 关联.
	private String isbn;
	
	public Long getProduct() {
		return product;
	}
	public void setProduct(Long product) {
		this.product = product;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getMaxdate() {
		return maxdate;
	}
	public void setMaxdate(Date maxdate) {
		this.maxdate = maxdate;
	}
	public int getIsnew() {
		return isnew;
	}
	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
