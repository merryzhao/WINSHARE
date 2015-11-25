package com.winxuan.ec.task.model.booktop;


/**
 * 渠道评论
 * 
 * @author sunflower
 * 
 */
public class BookTopCategory {

	public static final int TOP_TYPE_SALE = 0;// 销售榜单
	public static final int TOP_TYPE_NEW = 1;// 新书榜单
	public static final long CATEGORY_BOOK = 1;
	
	private Long id;

	/**
	 * 分类id
	 */
	private Long category;

	/**
	 * 父分类id
	 */
	private Long parent;

	/**
	 * 该分类下一周动销数
	 */
	private int num;

	/**
	 * 榜单url地址
	 */
	private String url;

	/**
	 * 榜单类型
	 */
	private int topType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTopType() {
		return topType;
	}

	public void setTopType(int topType) {
		this.topType = topType;
	}

}
