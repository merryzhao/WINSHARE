package com.winxuan.ec.model.replenishment;

import java.math.BigDecimal;

/**
 * 智能模型--当天发货记录临时类
 * @author yangxinyi
 *
 */
public class MrDeliveryRecordCurday {
	private Long mrProductItem;
	private Long productSale;
	private int availableQuantity;
	private String mcCategory;
	private Integer saleCycle;
	private Integer replenishmentCycle;
	private Integer transitCycle;
	private String grade;
	private Integer fixQuantity;
	private BigDecimal fixBoundTop;
	private BigDecimal fixBoundBottom;
	private BigDecimal fixRatio;
	private Integer fixDefaultQuantity;
	private Long mcType;
	private Long mcModel;
	private Long dc;
	private BigDecimal listPrice;
	
	public Long getMrProductItem() {
		return mrProductItem;
	}
	public void setMrProductItem(Long mrProductItem) {
		this.mrProductItem = mrProductItem;
	}
	public Long getProductSale() {
		return productSale;
	}
	public void setProductSale(Long productSale) {
		this.productSale = productSale;
	}
	public String getMcCategory() {
		return mcCategory;
	}
	public void setMcCategory(String mcCategory) {
		this.mcCategory = mcCategory;
	}
	public Integer getSaleCycle() {
		return saleCycle;
	}
	public void setSaleCycle(Integer saleCycle) {
		this.saleCycle = saleCycle;
	}
	public Integer getReplenishmentCycle() {
		return replenishmentCycle;
	}
	public void setReplenishmentCycle(Integer replenishmentCycle) {
		this.replenishmentCycle = replenishmentCycle;
	}
	public Integer getTransitCycle() {
		return transitCycle;
	}
	public void setTransitCycle(Integer transitCycle) {
		this.transitCycle = transitCycle;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Integer getFixQuantity() {
		return fixQuantity;
	}
	public void setFixQuantity(Integer fixQuantity) {
		this.fixQuantity = fixQuantity;
	}
	public Integer getFixDefaultQuantity() {
		return fixDefaultQuantity;
	}
	public void setFixDefaultQuantity(Integer fixDefaultQuantity) {
		this.fixDefaultQuantity = fixDefaultQuantity;
	}
	public Long getMcType() {
		return mcType;
	}
	public void setMcType(Long mcType) {
		this.mcType = mcType;
	}
	public BigDecimal getListPrice() {
		return listPrice;
	}
	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public BigDecimal getFixBoundTop() {
		return fixBoundTop;
	}
	public void setFixBoundTop(BigDecimal fixBoundTop) {
		this.fixBoundTop = fixBoundTop;
	}
	public BigDecimal getFixBoundBottom() {
		return fixBoundBottom;
	}
	public void setFixBoundBottom(BigDecimal fixBoundBottom) {
		this.fixBoundBottom = fixBoundBottom;
	}
	public BigDecimal getFixRatio() {
		return fixRatio;
	}
	public void setFixRatio(BigDecimal fixRatio) {
		this.fixRatio = fixRatio;
	}
	public Long getMcModel() {
		return mcModel;
	}
	public void setMcModel(Long mcModel) {
		this.mcModel = mcModel;
	}
	public Long getDc() {
		return dc;
	}
	public void setDc(Long dc) {
		this.dc = dc;
	}

}
