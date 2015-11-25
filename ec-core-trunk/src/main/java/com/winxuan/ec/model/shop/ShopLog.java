package com.winxuan.ec.model.shop;

import java.io.Serializable;
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
import com.winxuan.ec.model.user.User;

/**
 * description
 * 
 * @author yuhu
 * @version 1.0,2011-9-28
 */

@Entity
@Table(name="shoplog")
public class ShopLog implements Serializable{

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
	@JoinColumn(name="state")
	private Code state;
	
	@Column(name="updatedate")
	private Date updateDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="updateuser")
	private User updateUser;
	

	public ShopLog() {
		super();
	}
	
	public ShopLog(Shop shop,User updateUser) {
		super();
		this.shop = shop;
		this.updateUser=updateUser;
		this.updateDate = new Date();
		this.state = shop.getState();
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

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}
	
}
