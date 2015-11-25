package com.winxuan.ec.task.model.erp;

import java.util.Date;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-8-5 下午5:28:03 --!
 * @description: 增量更新的库存实体
 ******************************** 
 */
public class ErpProductStock {

	private long merchId;

	private int stock;

	private String dc;

	private Date crateTime;

	private Date updateTime;

	

	public long getMerchId() {
		return merchId;
	}

	public void setMerchId(long merchId) {
		this.merchId = merchId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
