/**
 * 
 */
package com.winxuan.ec.model.replenishment;

import java.util.Date;

/**
 * @author monica
 * 定义临时类
 */
public class MrCumulativeReceiveTemp {

	private Long productSale;
	private String place;
	private int changeqty;
	private Date updateTime;
	private Long type;
	
	public void setProductSale(Long productSale){
		this.productSale = productSale;
	}
	public Long getProductSale(){
		return productSale;
	}
	
	public void setPlace(String place){
		this.place = place;
	}
	public String getPlace(){
		return place;
	}
	
	public void setChangeqty(int changeqty){
		this.changeqty = changeqty;
	}
	public int getChangeqty(){
		return changeqty;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setType(Long type){
		this.type = type;
	}
	public Long getType(){
		return type;
	}
	
}
