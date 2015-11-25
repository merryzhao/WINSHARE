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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;

/**
 * 渠道库存
 * 
 * @author liou
 * @version 2013-9-4:下午2:38:22
 * 
 */
@Entity
@Table(name = "stock_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockChannel implements Serializable {
	private static final long serialVersionUID = 9204958527515751675L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsale")
	private ProductSale productSale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel")
	private Channel channel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dc")
	private Code dc;

	@Column
	private int stock;

	@Column(name = "updatetime")
	private Date updateTime;

	public StockChannel() {
		super();
	}

	public StockChannel(ProductSale productSale, Channel channel, Code dc,
			int stock) {
		super();
		this.productSale = productSale;
		this.channel = channel;
		this.dc = dc;
		this.stock = stock;
		this.updateTime = new Date();
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
