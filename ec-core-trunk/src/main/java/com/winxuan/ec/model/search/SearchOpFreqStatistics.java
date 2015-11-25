package com.winxuan.ec.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.product.ProductSale;

/**
 * 搜索结果点击频率统计信息
 * @author sunflower
 *
 */
@Entity
@Table(name = "search_op_freq_statistics")
public class SearchOpFreqStatistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 516146379857179773L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "op_type")
	private Long opType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale1")
	private ProductSale productsale1;
	
	@Column(name = "freq1")
	private Long freq1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale2")
	private ProductSale productsale2;
	
	@Column(name = "freq2")
	private Long freq2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale3")
	private ProductSale productsale3;
	
	@Column(name = "freq3")
	private Long freq3;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale4")
	private ProductSale productsale4;
	
	@Column(name = "freq4")
	private Long freq4;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale5")
	private ProductSale productsale5;
	
	@Column(name = "freq5")
	private Long freq5;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getOpType() {
		return opType;
	}

	public void setOpType(Long opType) {
		this.opType = opType;
	}

	public ProductSale getProductsale1() {
		return productsale1;
	}

	public void setProductsale1(ProductSale productsale1) {
		this.productsale1 = productsale1;
	}

	public Long getFreq1() {
		return freq1;
	}

	public void setFreq1(Long freq1) {
		this.freq1 = freq1;
	}

	public ProductSale getProductsale2() {
		return productsale2;
	}

	public void setProductsale2(ProductSale productsale2) {
		this.productsale2 = productsale2;
	}

	public Long getFreq2() {
		return freq2;
	}

	public void setFreq2(Long freq2) {
		this.freq2 = freq2;
	}

	public ProductSale getProductsale3() {
		return productsale3;
	}

	public void setProductsale3(ProductSale productsale3) {
		this.productsale3 = productsale3;
	}

	public Long getFreq3() {
		return freq3;
	}

	public void setFreq3(Long freq3) {
		this.freq3 = freq3;
	}

	public ProductSale getProductsale4() {
		return productsale4;
	}

	public void setProductsale4(ProductSale productsale4) {
		this.productsale4 = productsale4;
	}

	public Long getFreq4() {
		return freq4;
	}

	public void setFreq4(Long freq4) {
		this.freq4 = freq4;
	}

	public ProductSale getProductsale5() {
		return productsale5;
	}

	public void setProductsale5(ProductSale productsale5) {
		this.productsale5 = productsale5;
	}

	public Long getFreq5() {
		return freq5;
	}

	public void setFreq5(Long freq5) {
		this.freq5 = freq5;
	}


}
