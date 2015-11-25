package com.winxuan.ec.model.channel;

/**
 * @description: TODO
 *
 * @createtime: 2013-11-27 下午1:46:04
 *
 * @author zenghua
 *
 * @version 1.0
 */

public class SalesRecordDto {
	private Long id;
	private Long productsale;
	private Long uploadRecord;
	private Integer status;
	
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
}
