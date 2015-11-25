package com.winxuan.ec.model.product;

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

/**
 * 库存不准确商品
 * 每天更新前一天缺货订单的商品AND库存小于10
 * 在库存同步时，如果到货数量大于此库存则标记已处理，可上架
 * 未处理过的记录不进行上架
 * @author HideHai
 */
@Entity
@Table(name="product_incorrect_warehouse")
public class ProductSaleIncorrectWarehouse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	@Column(name="createtime")
	private Date createTime;
	
	@Column(name="ischanged")
	private boolean change;
	
	@Column
	private Integer stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
}
