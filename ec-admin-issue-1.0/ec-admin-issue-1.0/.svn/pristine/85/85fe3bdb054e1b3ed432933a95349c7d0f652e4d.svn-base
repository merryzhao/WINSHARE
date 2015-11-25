package com.winxuan.ec.admin.controller.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductManageGrade;
import com.winxuan.ec.model.product.ProductSale;

/**
 * 商品管理分级form
 *
 * @author yuhu
 * @version 1.0,2012-05-08
 */
public class ProductSaleManageGradeForm {
	@NotNull(message="商品id不能为空")
	private Long psId;
	@NotNull(message="管理等级不能为空")
	private Long grade;
	private String hotSellingStart;
	private String hotSellingEnd;
	private Integer purchaseDiscount;
	private String remark;
	
	public ProductManageGrade getManageGrade(ProductSale productSale,Code grade) throws ParseException{
		ProductManageGrade manageGrade = new ProductManageGrade();
		manageGrade.setProductSale(productSale);
		manageGrade.setGrade(grade);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtils.isBlank(hotSellingStart)){
			manageGrade.setHotSellingStart(sdf.parse(hotSellingStart));
		}
		if(!StringUtils.isBlank(hotSellingEnd)){
			manageGrade.setHotSellingEnd(sdf.parse(hotSellingEnd));
		}
		manageGrade.setPurchaseDiscount(purchaseDiscount);
		manageGrade.setRemark(remark);
		manageGrade.setOperateTime(new Date());
		return manageGrade;
	}
	
	public Long getPsId() {
		return psId;
	}
	public void setPsId(Long psId) {
		this.psId = psId;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public String getHotSellingStart() {
		return hotSellingStart;
	}

	public void setHotSellingStart(String hotSellingStart) {
		this.hotSellingStart = hotSellingStart;
	}

	public String getHotSellingEnd() {
		return hotSellingEnd;
	}

	public void setHotSellingEnd(String hotSellingEnd) {
		this.hotSellingEnd = hotSellingEnd;
	}

	public Integer getPurchaseDiscount() {
		return purchaseDiscount;
	}
	public void setPurchaseDiscount(Integer purchaseDiscount) {
		this.purchaseDiscount = purchaseDiscount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
