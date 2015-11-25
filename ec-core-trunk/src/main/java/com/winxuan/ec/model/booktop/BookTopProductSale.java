package com.winxuan.ec.model.booktop;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.product.ProductSale;

/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "book_top_product_sale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BookTopProductSale implements Serializable {

	public static final int TIME_TYPE_WEEK = 1;// 近一周
	public static final int TOP_TYPE_ONE_MONTH = 2;// 近一月
	public static final int TOP_TYPE_THREE_MONTH = 3;// 近三月
	public static final int TOP_TYPE_ONE_YEAR = 4;// 头一年
	public static final int TOP_TYPE_THIS_YEAR = 5;// 今年

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 榜单分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private BookTopCategory bookTopCategory;

	/**
	 * 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productsale;

	/**
	 * 销量
	 */
	@Column
	private int sale;

	/**
	 * 时间类型
	 */
	@Column(name = "time_type")
	private int timeType;

	@Transient
	private long commentCount;
	
	@Transient
	private BigDecimal avgRank;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookTopCategory getCategory() {
		return bookTopCategory;
	}

	public void setCategory(BookTopCategory bookTopCategory) {
		this.bookTopCategory = bookTopCategory;
	}

	public ProductSale getProductsale() {
		return productsale;
	}

	public void setProductsale(ProductSale productsale) {
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

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public BigDecimal getAvgRank() {
		return avgRank;
	}

	public void setAvgRank(BigDecimal avgRank) {
		this.avgRank = avgRank;
	}
	
}
