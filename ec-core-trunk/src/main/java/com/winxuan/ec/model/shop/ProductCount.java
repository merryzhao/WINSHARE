package com.winxuan.ec.model.shop;

/**
 * 产品上架数量和产品总数
 * 
 * @author xuan jiu dong
 * 
 */
public class ProductCount {

	/**
	 * 指定商品分类下面的产品数量
	 */
	private Long productCount;

	/**
	 * 以上架的产品数量
	 */
	private Long onshelfCount;

	public ProductCount() {

	}

	public ProductCount(Long onshelfCount, Long productCount) {
		this.onshelfCount = onshelfCount;
		this.productCount = productCount;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public Long getOnshelfCount() {
		return onshelfCount;
	}

	public void setOnshelfCount(Long onshelfCount) {
		this.onshelfCount = onshelfCount;
	}
}
