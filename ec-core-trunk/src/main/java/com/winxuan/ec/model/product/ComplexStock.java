package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Map;

import com.winxuan.ec.enums.StockOriginEnum;

/**
 * 
 * @author YangJun
 * @version 1.0, 2014
 *
 */
public class ComplexStock implements Serializable {
	private static final long serialVersionUID = 691319679670639808L;

	// 库存计算是否成功，当失败的时候，msg里面有失败原因
	private boolean succeed = true;

	// 可分配库存
	private int stock = 0;

	// 各DC分别可用量
	private Map<Long, Integer> dcStocks;

	// 活动类型,1固定数量锁定;2系数锁定;
	private Short activityType;

	// 活动库存量，activityType为非空时，此值才有实际意义
	private Integer activityStock;

	// 库存计算失败时的错误信息
	private String msg;

	private StockOriginEnum origin;

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Map<Long, Integer> getDcStocks() {
		return dcStocks;
	}

	public void setDcStocks(Map<Long, Integer> dcStocks) {
		this.dcStocks = dcStocks;
	}

	public Short getActivityType() {
		return activityType;
	}

	public void setActivityType(Short activityType) {
		this.activityType = activityType;
	}

	public Integer getActivityStock() {
		return activityStock;
	}

	public void setActivityStock(Integer activityStock) {
		this.activityStock = activityStock;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setOrigin(StockOriginEnum origin) {
		this.origin = origin;
	}

	/**
	 * 判断是否需要更新,当库存有活动的时候,只有收货和库存重新分配才需要更新,否则,不需要更新.
	 * @return 返回true更新,false不更新
	 */
	public boolean needUpdate() {
		if (!isSucceed()) {
			return false;
		}

		if (null != getActivityType() && null != getActivityStock()) {
			if (StockOriginEnum.STOCK_INIT.getOrigin() == this.origin.getOrigin()
					|| StockOriginEnum.RECEIVING.getOrigin() == this.origin.getOrigin()) {
				return true;
			}
		} else {
			return true;
		}

		return false;
	}

}
