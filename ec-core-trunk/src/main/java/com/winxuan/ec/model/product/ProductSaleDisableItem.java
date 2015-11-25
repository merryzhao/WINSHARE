package com.winxuan.ec.model.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * @description: TODO
 * 
 * @createTime: 2013年12月10日 下午11:03:37
 * 
 * @author zenghua
 * 
 * @version 1.0
 */
@Entity
@Table(name = "product_sale_disable_item")
public class ProductSaleDisableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8140620162585159399L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_sale_disable_record")
	private ProductSaleDisableRecord productSaleDisableRecord;

	@ManyToOne
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	@ManyToOne
	@JoinColumn(name = "status")
	private Code status;

	@Column(name = "is_sync")
	private boolean synced;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSaleDisableRecord getProductSaleDisableRecord() {
		return productSaleDisableRecord;
	}

	public void setProductSaleDisableRecord(
			ProductSaleDisableRecord productSaleDisableRecord) {
		this.productSaleDisableRecord = productSaleDisableRecord;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}

}
