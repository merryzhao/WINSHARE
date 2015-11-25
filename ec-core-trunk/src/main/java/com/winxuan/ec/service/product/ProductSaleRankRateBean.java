/**
 * 
 */
package com.winxuan.ec.service.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author zhousl
 *
 * 2013-10-28
 */
public class ProductSaleRankRateBean {

	private static final int ONE_STAR = 1;
	
	private static final int TWO_STAR = 2;
	
	private static final int THREE_STAR = 3;
	
	private static final int FOUR_STAR = 4;
	
	private static final int FIVE_STAR = 5;
	
	private List<ProductSaleRankInfo> rankInfos;

	private long totalCount;
	
	/**
	 * @param rankInfos
	 */
	public ProductSaleRankRateBean(List<ProductSaleRankInfo> rankInfos) {
		super();
		this.rankInfos = rankInfos;
		this.totalCount = 0;
		if(CollectionUtils.isNotEmpty(rankInfos)){
			for(ProductSaleRankInfo rankInfo : rankInfos){
				totalCount += rankInfo.getStarCount();
			}
		}
	}

	
	public List<ProductSaleRankInfo> getRankInfos() {
		return rankInfos;
	}

	public void setRankInfos(List<ProductSaleRankInfo> rankInfos) {
		this.rankInfos = rankInfos;
	}
	
	public BigDecimal getshareByOneStar(){
		return getshareByStar(ONE_STAR);
	}
	
	public BigDecimal getshareByTwoStar(){
		return getshareByStar(TWO_STAR);
	}
	
	public BigDecimal getshareByThreeStar(){
		return getshareByStar(THREE_STAR);
	}
	
	public BigDecimal getshareByFourStar(){
		return getshareByStar(FOUR_STAR);
	}
	
	public BigDecimal getshareByFiveStar(){
		return getshareByStar(FIVE_STAR);
	}
	
	public long getRankCountByOneStar(){
		return getRankCountByStar(ONE_STAR);
	}

	public long getRankCountByTwoStar(){
		return getRankCountByStar(TWO_STAR);
	}
	
	public long getRankCountByThreeStar(){
		return getRankCountByStar(THREE_STAR);
	}
	
	public long getRankCountByFourStar(){
		return getRankCountByStar(FOUR_STAR);
	}
	
	public long getRankCountByFiveStar(){
		return getRankCountByStar(FIVE_STAR);
	}
	
	private BigDecimal getshareByStar(int star){
		if(CollectionUtils.isNotEmpty(this.rankInfos)){
			for(ProductSaleRankInfo rankInfo : rankInfos){
				if(rankInfo.getRank() == star){
					return new BigDecimal(rankInfo.getStarCount())
						.divide(new BigDecimal(this.totalCount), 2, RoundingMode.HALF_UP);
				}
			}
		}
		return BigDecimal.ZERO;
	}
	
	private long getRankCountByStar(int star){
		if(CollectionUtils.isNotEmpty(rankInfos)){
			for(ProductSaleRankInfo rankInfo : rankInfos){
				if(rankInfo.getRank() == star){
					return rankInfo.getStarCount();
				}
			}
		}
		return 0;
	}
}
