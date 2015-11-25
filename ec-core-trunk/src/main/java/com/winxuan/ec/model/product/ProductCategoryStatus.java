package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author yuhu
 * @version 1.0,2012-4-12下午04:06:47
 */
@Entity
@Table(name = "product_category_status")
public class ProductCategoryStatus  implements Serializable {
	
	public static final int STATUS_HAND = 3;
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "product")
	@GeneratedValue(generator = "productFK")
	@GenericGenerator(name = "productFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "product"))
	private Long id;
	
	@OneToOne(mappedBy = "categoryStatus", targetEntity = Product.class, fetch = FetchType.LAZY)
	private Product product;
	
	@Column
	private int status;
	
	@Column(name="maxdate")
	private Date maxDate;
	
	@Column(name="isnew")
	private boolean newCategory;

	public ProductCategoryStatus() {
		super();
	}

	public ProductCategoryStatus(Product product, int status, Date maxDate,
			boolean isNewCategory) {
		super();
		this.product = product;
		this.status = status;
		this.maxDate = maxDate;
		this.newCategory = isNewCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public boolean isNewCategory() {
		return newCategory;
	}

	public void setNewCategory(boolean newCategory) {
		this.newCategory = newCategory;
	}
}
