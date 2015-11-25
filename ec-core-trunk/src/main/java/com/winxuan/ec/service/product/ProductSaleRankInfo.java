/**
 * 
 */
package com.winxuan.ec.service.product;

/**
 * @author zhousl
 *
 * 2013-10-27
 */
public class ProductSaleRankInfo {

	private long starCount;
	
	private int rank;
	
	public ProductSaleRankInfo(int rank, long starCount){
		this.rank = rank;
		this.starCount = starCount;
	}

	public long getStarCount() {
		return starCount;
	}

	public void setStarCount(long starCount) {
		this.starCount = starCount;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	
}


