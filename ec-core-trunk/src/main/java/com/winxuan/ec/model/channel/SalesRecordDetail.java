package com.winxuan.ec.model.channel;

/**
 * @description: TODO
 * 
 * @createtime: 2013-12-17 下午5:43:57
 * 
 * @author zenghua
 * 
 * @version 1.0
 */

public class SalesRecordDetail {
	private Long id;
	private Integer status;
	private Long productsale;
	private String channelproduct;
	private Long uploadRecord;
	private String startdate;
	private String enddate;
	private Long channel;

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

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

}
