/**
 * 
 */
package com.winxuan.ec.model.product;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Employee;

/**
 * @author zhousl
 * 快速分拨商品
 * 2013-9-3
 */
@Entity
@Table(name="product_sale_rapid")
public class ProductSaleRapid {
	
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	@JoinColumn(name="product_sale")
	private ProductSale productSale;

	/**
	 * 采购量
	 */
	@Column
	private int amount;
	
	@Column(name="createtime")
	private Date createTime;
	
	/**
	 * 戳，以标识最后记录时间
	 */
	@Column(name="ts")
	private Calendar timeStamp;
	
	@ManyToOne
	@JoinColumn(name="creator")
	private Employee creator;

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

}
