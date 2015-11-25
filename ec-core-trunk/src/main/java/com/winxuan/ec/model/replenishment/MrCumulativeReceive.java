/**
 * 
 */
package com.winxuan.ec.model.replenishment;

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

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;

/**
 * @author monica
 *
 */
@Entity
@Table(name = "mr_product_cumulative_receive")
public class MrCumulativeReceive {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;
	
	@Column(name="receive")
	private int receive;
	
	@Column(name="updatetime")
	private Date updateTime;
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	
	public void setProductSale(ProductSale productSale){
		this.productSale = productSale;
	}
	public ProductSale getProductSale(){
		return productSale;
	}
	
	public void setDc(Code dc){
		this.dc = dc;
	}
	public Code getDc(){
		return dc;
	}
	
	public void setReceive(int receive){
		this.receive = receive;
	}
	public int getReceive(){
		return receive;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	
}
