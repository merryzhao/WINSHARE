package com.winxuan.ec.task.model.ec;
/**
 * description
 * @author  lean bian
 * @version 1.0,2013-09-26
 */
public class EcProductWareHouse {
	private Long id;
	
	private String outerId;
	
	private String location;
	
	private int stock;
	
	private int isChanged;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(int isChanged) {
		this.isChanged = isChanged;
	}
}
