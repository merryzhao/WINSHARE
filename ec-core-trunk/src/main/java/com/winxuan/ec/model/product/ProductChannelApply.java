package com.winxuan.ec.model.product;

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

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;

/**
 * 商品参与的促销
 * @author  yuhu
 * @version 1.0,2011-9-29
 */
@Entity
@Table(name = "product_channel_apply")
public class ProductChannelApply implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_productsale")
	private ProductSale productSale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="_channel")
	private Channel channel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="state")
	private Code state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="type")
	private Code type;
	
	@Column(name = "createdate")
	private Date createDate;
	
	@Column(name = "auditdate")
	private Date auditDate;

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

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Code getState() {
		return state;
	}

	public void setState(Code state) {
		this.state = state;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	
}
