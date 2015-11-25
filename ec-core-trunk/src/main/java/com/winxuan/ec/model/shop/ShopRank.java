package com.winxuan.ec.model.shop;

import java.math.BigInteger;

/**
 * @author  周斯礼
 * @version 2013-1-17
 */

public class ShopRank {

	private Long shopId;
	private Double sumRank;
	private BigInteger rankCount;
	private Double averageRank;
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Double getSumRank() {
		return sumRank;
	}
	public void setSumRank(Double sumRank) {
		this.sumRank = sumRank;
	}
	public BigInteger getRankCount() {
		return rankCount;
	}
	public void setRankCount(BigInteger rankCount) {
		this.rankCount = rankCount;
	}
	public Double getAverageRank() {
		return averageRank;
	}
	public void setAverageRank(Double averageRank) {
		this.averageRank = averageRank;
	}
	
}


