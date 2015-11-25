package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * @description: TODO
 * 
 * @createTime: 2013年12月10日 下午11:10:02
 * 
 * @author zenghua
 * 
 * @version 1.0
 */
@Entity
@Table(name = "product_sale_disable_record")
public class ProductSaleDisableRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2641269832744504177L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date createTime;
	private Date updateTime;
	@ManyToOne
	@JoinColumn(name = "user")
	private Employee uploader;
	@ManyToOne
	@JoinColumn(name = "status")
	private Code status;

	private String comment;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productSaleDisableRecord", targetEntity = ProductSaleDisableItem.class)
    @OrderBy("id")
    private List<ProductSaleDisableItem> itemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Employee getUploader() {
		return uploader;
	}

	public void setUploader(Employee uploader) {
		this.uploader = uploader;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ProductSaleDisableItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<ProductSaleDisableItem> itemList) {
		this.itemList = itemList;
	}
	
}
