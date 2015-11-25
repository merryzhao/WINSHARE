package com.winxuan.ec.task.model.booktop;


/**
 * 渠道评论
 * 
 * @author sunflower
 * 
 */
public class BookTopProductSale {

	public static final int TIME_TYPE_WEEK = 1;// 近一周
	public static final int TIME_TYPE_ONE_MONTH = 2;// 近一月
	public static final int TIME_TYPE_THREE_MONTH = 3;// 近三月
	public static final int TIME_TYPE_ONE_YEAR = 4;// 头一年
	public static final int TIME_TYPE_TWO_YEAR = 5;// 头二年

	/**
	 * 分类id
	 */
	private Long category;

	/**
	 * 商品
	 */
	private Long productsale;

	/**
	 * 销量
	 */
	private int sale;

	/**
	 * 时间类型
	 */
	private int timeType;

	/**
	 * 榜单类型
	 */
	private int topType;

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getProductsale() {
		return productsale;
	}

	public void setProductsale(Long productsale) {
		this.productsale = productsale;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public int getTopType() {
		return topType;
	}

	public void setTopType(int topType) {
		this.topType = topType;
	}

}
