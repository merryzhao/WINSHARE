package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-17
 */
@Table(name="product_sale_rank")
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSaleRank implements Serializable{
	
	public static final short STAR_NO = 0;
	public static final short STAR_ONE = 1;
	public static final short STAR_TWO = 2;
	public static final short STAR_THREE = 3;
	public static final short STAR_FOUR = 4;
	public static final short STAR_FIVE = 5;
	
	private static final long serialVersionUID = -3891386632239421168L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="rank")
	private int rank;
	
	@Column(name="ranktime")
	private Date rankTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	
//	@OneToOne(mappedBy="rank",targetEntity=CustomerComment.class)
//	private CustomerComment comment;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Date getRankTime() {
		return rankTime;
	}

	public void setRankTime(Date rankTime) {
		this.rankTime = rankTime;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

//	public CustomerComment getComment() {
//		return comment;
//	}
//
//	public void setComment(CustomerComment comment) {
//		this.comment = comment;
//	}
	
}
