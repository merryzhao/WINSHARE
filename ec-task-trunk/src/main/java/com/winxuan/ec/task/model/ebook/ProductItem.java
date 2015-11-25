package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电子书产品明细
 * @author luosh
 *
 */
@javax.persistence.Entity
@Table(name="PRODUCT_ITEM")
public class ProductItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PRODUCT_ITEM_ID")
	private Long productItemID;
	@Column(name="PRODUCT_ID")
	private Long productID;
	@Column(name="ENTITY_ID")
	private Long entityID;
	@Column(name="ENTITY_TYPE")
	private Integer entityType;
	@Column(name="CREATE_DATETIME")
	private Date createDatetime;
	@Column(name="CREATE_BY")
	private String createBy;
	@Column(name="UPDATE_DATETIME")
	private Date updateDatetime;
	@Column(name="UPDATE_BY")
	private String updateBy;
	@Column(name="DELETE_FLAG")
	private Integer deleteFlag;
	
	
	public Long getProductItemID() {
		return productItemID;
	}
	public void setProductItemID(Long productItemID) {
		this.productItemID = productItemID;
	}
	
	public Long getProductID() {
		return productID;
	}
	public void setProductID(Long productID) {
		this.productID = productID;
	}
	
	public Long getEntityID() {
		return entityID;
	}
	public void setEntityID(Long entityID) {
		this.entityID = entityID;
	}
	
	public Integer getEntityType() {
		return entityType;
	}
	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}
	
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}