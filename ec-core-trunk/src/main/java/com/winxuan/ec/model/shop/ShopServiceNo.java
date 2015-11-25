package com.winxuan.ec.model.shop;

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

import com.winxuan.ec.model.code.Code;

/**
 * description
 * 
 * @author rsy
 * @version 1.0,2011-9-28
 */

@Entity
@Table(name="shop_service")
public class ShopServiceNo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_shop")
	private Shop shop;
	
	@ManyToOne(fetch = FetchType.LAZY)
 	@JoinColumn(name="type")
	private Code type;
 	
	@Column(name="serviceno")
	private String serviceNo;
	
    public void setValue(Long id,Code type,String serviceNo,Shop shop){
    	this.id=id;
    	this.serviceNo=serviceNo;
    	this.shop=shop;
    	this.type=type;
    }

  	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	 
}
