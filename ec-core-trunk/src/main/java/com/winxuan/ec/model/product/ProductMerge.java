package com.winxuan.ec.model.product;


/**
 * ****************************** 
 * @author:cast911
 * @lastupdateTime:2013-4-28 上午9:58:12  --!
 * 
 ********************************
 */
public class ProductMerge {

	/**
	 * 文轩商品
	 */
	private ProductSale wxProduct;

	/**
	 * 合并商品
	 */
	private ProductSale mergeProduct;

	/**
	 * 是否合并
	 */
	private boolean isMerge;
	
	/**
	 * 是否忽略
	 */
	private boolean isIgnore;

	public ProductSale getWxProduct() {
		return wxProduct;
	}

	public void setWxProduct(ProductSale wxProduct) {
		this.wxProduct = wxProduct;
	}
	
	public ProductSale getMergeProduct() {
		return mergeProduct;
	}

	public void setMergeProduct(ProductSale mergeProduct) {
		this.mergeProduct = mergeProduct;
	}

	public boolean isMerge() {
		return isMerge;
	}

	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}

	public boolean isIgnore() {
		return isIgnore;
	}

	public void setIgnore(boolean isIgnore) {
		this.isIgnore = isIgnore;
	}
	
	

}
