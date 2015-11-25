package com.winxuan.ec.model.booktop;

import java.io.Serializable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.category.Category;

/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "book_top_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookTopCategory implements Serializable {

	public static final int TOP_TYPE_SALE = 0;// 销售榜单
	public static final int TOP_TYPE_NEW = 1;// 新书榜单
	public static final Long CATEGORY_BOOK = 1L;// 图书分类

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private Category category;

	/**
	 * 父分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private BookTopCategory parent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = BookTopCategory.class)
	@OrderBy("num")
	private Set<BookTopCategory> children;

	/**
	 * 该分类下一周动销数
	 */
	@Column
	private int num;

	/**
	 * 榜单url地址
	 */
	@Column
	private String url;

	/**
	 * 榜单类型
	 */
	@Column(name = "top_type")
	private int topType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public BookTopCategory getParent() {
		return parent;
	}

	public void setParent(BookTopCategory parent) {
		this.parent = parent;
	}

	public Set<BookTopCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<BookTopCategory> children) {
		this.children = children;
	}

}
