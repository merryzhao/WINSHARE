package com.winxuan.ec.model.channel;

/**
 * @description: 超退明细
 * 
 * @createtime: 2014-1-15 上午11:05:19
 * 
 * @author zenghua
 * 
 * @version 1.0
 */

public class SalesOverRefund {
	private Long id;
	private Long productsale;
	private Long uploadRecord;
	private Integer status;
	private String channelproduct;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getProductsale() {
		return productsale;
	}

	public void setProductsale(Long productsale) {
		this.productsale = productsale;
	}

	public Long getUploadRecord() {
		return uploadRecord;
	}

	public void setUploadRecord(Long uploadRecord) {
		this.uploadRecord = uploadRecord;
	}

	public String getChannelproduct() {
		return channelproduct;
	}

	public void setChannelproduct(String channelproduct) {
		this.channelproduct = channelproduct;
	}

}
