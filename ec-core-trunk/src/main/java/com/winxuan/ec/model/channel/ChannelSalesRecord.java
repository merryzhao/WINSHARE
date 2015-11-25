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

/**
 * 上传销售记录
 * @author heyadong
 *
 */
@Entity
@Table(name="sales_record")
public class ChannelSalesRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=ChannelSalesUploadRecord.class)
	@JoinColumn(name="uploadrecord")
	private ChannelSalesUploadRecord uploadrecord;
	
	@JoinColumn(name="startdate")
	private Date startdate;
	
	@JoinColumn(name="enddate")
	private Date enddate;
	
	@Column(name="channelproduct")
	private String channelProduct;
	
	@ManyToOne
	@JoinColumn(name="productsale")
	private ProductSale productSale;
	@Column
	private int sales = 0;
	@Column
	private int refund = 0;
	@ManyToOne
	@JoinColumn(name="status")
	private Code status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ChannelSalesUploadRecord getUploadrecord() {
		return uploadrecord;
	}
	public void setUploadrecord(ChannelSalesUploadRecord uploadrecord) {
		this.uploadrecord = uploadrecord;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getChannelProduct() {
		return channelProduct;
	}
	public void setChannelProduct(String channelProduct) {
		this.channelProduct = channelProduct;
	}
	public ProductSale getProductSale() {
		return productSale;
	}
	public void setProductSale(ProductSale productSale) {
		this.productSale = productSale;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getRefund() {
		return refund;
	}
	public void setRefund(int refund) {
		this.refund = refund;
	}
	
	public Code getStatus() {
		return status;
	}
	public void setStatus(Code status) {
		this.status = status;
	}
}
