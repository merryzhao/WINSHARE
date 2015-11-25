package com.winxuan.ec.model.channel;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;

/**
 * 渠道销售商品关系
 * @author heyadong
 *
 */
@Entity
@Table(name="sales_channel_product")
public class ChannelSalesProduct {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@Column(name="channelproduct")
	private String channelProduct;
	
	@ManyToOne
	@JoinColumn(name="channel")
	private Channel channel;
	
	@ManyToOne
	@JoinColumn(name="productsale")
	private ProductSale productSale;
	@ManyToOne
	@JoinColumn(name="type")
	private Code type;
	
	@ManyToOne
	@JoinColumn(name="operator")
	private Employee operator;
	
	@Column(name="updatetime")
	private Date updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelProduct() {
		return channelProduct;
	}

	public void setChannelProduct(String channelProduct) {
		this.channelProduct = channelProduct;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ProductSale getProductSale() {
		return productSale;
	}

	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Employee getOperator() {
		return operator;
	}

	public void setOperator(Employee operator) {
		this.operator = operator;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Override
	public String toString(){
		return String.format("[%s],渠道商品ID:%s", channel.getName(), channelProduct);
	}
}
