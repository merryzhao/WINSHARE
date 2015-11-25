package com.winxuan.ec.support.promotion;

import java.math.BigDecimal;

import com.winxuan.ec.model.code.Code;

/**
 * @author yuhu
 * @version 1.0,2011-11-25下午05:59:20
 */
public class PromotionPrice {

	/**
	 * 活动类型
	 */
	private Long promType;
	
	/**
	 * 促销活动所规定的订单最小金额
	 * 改促销活动指 ：
	 * 1、已经满足的促销活动
	 * 2、当前订单不满足任何促销活动时，离当前订单最近的促销活动
	 */
	private BigDecimal minMoney;
	/**
	 * 促销活动所规定的节省或赠送金额
	 *  改促销活动指 ：
	 * 1、已经满足的促销活动
	 * 2、当前订单不满足任何促销活动时，离当前订单最近的促销活动
	 */
	private BigDecimal amount;
	
	/**
	 * 以满足的促销活动所节省或赠送金额
	 * 当saveMoney=-1时表示运费全面
	 */
	private BigDecimal saveMoney;
	
	/**
	 * 再需要购买**金额后可参与优惠更高的优惠活动
	 */
	private BigDecimal needMoney;
	
	/**
	 * 再购买needMoney金额后可节省或赠送 的金额
	 */
	private BigDecimal moreSaveMoney;
	
	private Code prom;


	public PromotionPrice() {
		super();
	}

	public PromotionPrice(Long promType) {
		super();
		this.promType = promType;
	}

	public PromotionPrice(Long promType, BigDecimal minMoney,BigDecimal amount,
			BigDecimal saveMoney, BigDecimal needMoney, BigDecimal moreSaveMoney) {
		super();
		this.promType = promType;
		this.minMoney = minMoney;
		this.amount = amount;
		this.saveMoney = saveMoney;
		this.needMoney = needMoney;
		this.moreSaveMoney = moreSaveMoney;
	}

	public Long getPromType() {
		return promType;
	}

	public void setPromType(Long promType) {
		this.promType = promType;
	}

	public BigDecimal getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(BigDecimal minMoney) {
		this.minMoney = minMoney;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(BigDecimal saveMoney) {
		this.saveMoney = saveMoney;
	}

	public BigDecimal getNeedMoney() {
		return needMoney;
	}

	public void setNeedMoney(BigDecimal needMoney) {
		this.needMoney = needMoney;
	}

	public BigDecimal getMoreSaveMoney() {
		return moreSaveMoney;
	}

	public void setMoreSaveMoney(BigDecimal moreSaveMoney) {
		this.moreSaveMoney = moreSaveMoney;
	}
	
	
	public Code getProm() {
		return prom;
	}

	public void setProm(Code prom) {
		this.prom = prom;
	}
}
