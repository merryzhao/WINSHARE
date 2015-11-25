package com.winxuan.ec.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-9-3 上午9:39:38 --!
 * @description:存放各种瞬态属性.
 ******************************** 
 */
public class ProductTransient {

	public static final int DEFAULT_COMPLEX_QUANTITY = 1;

	private static final int DEFAULT_COMPLEX_INDEX = 0;

	private boolean flag;

	private int purchasedQuantity;

	private int purchasedQuantityAll;

	private String imageUrl;

	private String remark;
	
    private BigDecimal avgRank;
    
    private long rankCount;

	// 套装书的dc信息
	private List<Code> complexDcList;
	//商品操作用户
	private Employee employee;


	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * 套装书的dc信息
	 * 
	 * @return
	 */
	public List<Code> getComplexDcList() {
		return complexDcList;
	}
	
	
	public List<Code> getDefaultComplexDcList(ProductSale complex){
	    if(CollectionUtils.isNotEmpty(this.getComplexDcList())){
	        return this.getComplexDcList();
	    }
//	    boolean hasDc = CollectionUtils.isNotEmpty(complex.getProductSaleStocks());
//        if (hasDc) {
//            Set<ProductSaleStock> stocks = complex.getProductSaleStocks();
//            for (ProductSaleStock productSaleStock : stocks) {
//                complex.getDefaultTransient().addComplexDc(productSaleStock.getDc());
//            }
//        }
        return null;
	}

	public void setComplexDcList(List<Code> complexDcList) {
		this.complexDcList = complexDcList;
	}

	public Code getDefaultComplexDc() {
		if (CollectionUtils.isEmpty(this.complexDcList)) {
			return null;
		}
		return this.getComplexDcList().get(DEFAULT_COMPLEX_INDEX);
	}

	public void addComplexDc(Code dc) {
		if (CollectionUtils.isEmpty(this.complexDcList)) {
			this.complexDcList = new ArrayList<Code>();
		}
		this.complexDcList.add(dc);
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getPurchasedQuantity() {
		return purchasedQuantity;
	}

	public void setPurchasedQuantity(int purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}

	public int getPurchasedQuantityAll() {
		return purchasedQuantityAll;
	}

	public void setPurchasedQuantityAll(int purchasedQuantityAll) {
		this.purchasedQuantityAll = purchasedQuantityAll;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAvgRank() {
		return avgRank;
	}

	public void setAvgRank(BigDecimal avgRank) {
		this.avgRank = avgRank;
	}

	public long getRankCount() {
		return rankCount;
	}

	public void setRankCount(long rankCount) {
		this.rankCount = rankCount;
	}
	
}
