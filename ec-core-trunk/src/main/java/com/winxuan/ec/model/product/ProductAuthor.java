/**
 * 
 */
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
 * @author monica
 * 从主数据同步责任人数据到product_author表
 */
@Entity
@Table(name = "product_author")
public class ProductAuthor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid")
	public Product productId;
	
	@Column(name = "responsibilityid")
	public Long responsibilityId;
	
	@Column(name = "identity")
	public String identity;
	
	@Column(name = "fullname")
	public String fullname;
	
	@Column(name = "sequencenum")
	public int sequencenum;
	
	@Column(name = "createtime")
	public Date createTime;
	
	@Column(name = "updatetime")
	public Date updateTime;
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	
	public void setProductId(Product productId){
		this.productId = productId;
	}
	public Product getProductId(){
		return productId;
	}
	
	public void setResponsibilityId(Long responsibilityId){
		this.responsibilityId = responsibilityId;
	}
	public Long getResponsibilityId(){
		return responsibilityId;
	}
	
	public void setIdentity(String identity){
		this.identity = identity;
	}
	public String getIdentity(){
		return identity;
	}
	
	public void setFullname(String fullname){
		this.fullname = fullname;
	}
	public String getFullName(){
		return fullname;
	}
	
	public void setSequencenum(int sequencenum){
		this.sequencenum = sequencenum;
	}
	public int getSequencenum(){
		return sequencenum;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
}
