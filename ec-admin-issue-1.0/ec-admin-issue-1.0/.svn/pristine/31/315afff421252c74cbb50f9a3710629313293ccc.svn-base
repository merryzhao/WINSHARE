/*
 * @(#)PaymentForm.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.excel;

import java.util.Arrays;

/**
 * 商品信息查询表单验证类
 *
 * @author wumaojie
 * @version 1.0,2011-8-5
 */
public class ExcelProductListForm {

	// 编码类型
	private String coding;
	// 编码内容
	private String codingContent;
	// 商品名称关键字
	private String productName;
	// 卖家名称
	private String sellerName;
	// 作者
	private String productAuthor;
	// MC分类
	private String productMcCategory;
	// 上下架状态
	private Long status;
    //是否显示更多条件
	private boolean ismore;
	//复选框值
	private Long[] export;
	
	public Long[] getExport() {
		return export;
	}

	public void setExport(Long[] export) {
		this.export = export;
	}

	public boolean isIsmore() {
		return ismore;
	}

	public void setIsmore(boolean ismore) {
		this.ismore = ismore;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding.trim();
	}

	public String getCodingContent() {
		return codingContent;
	}

	public void setCodingContent(String codingContent) {
		codingContent = codingContent.trim();
		if ("".equals(codingContent)) {
			this.codingContent = null;
		} else {
			this.codingContent = codingContent;
		}
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		productName = productName.trim();
		if ("".equals(productName)) {
			this.productName = null;
		} else {
			this.productName = productName;
		}
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		sellerName = sellerName.trim();
		if ("".equals(sellerName)) {
			this.sellerName = null;
		} else {
			this.sellerName = sellerName;
		}
	}

	public String getProductAuthor() {
		return productAuthor;
	}

	public void setProductAuthor(String productAuthor) {
		productAuthor = productAuthor.trim();
		if ("".equals(productAuthor)) {
			this.productAuthor = null;
		} else {
			this.productAuthor = productAuthor;
		}
	}

	public String getProductMcCategory() {
		return productMcCategory;
	}

	public void setProductMcCategory(String productMcCategory) {
		productMcCategory = productMcCategory.trim();
		if ("".equals(productMcCategory)) {
			this.productMcCategory = null;
		} else {
			this.productMcCategory = productMcCategory;
		}
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		if (status.intValue() == 0) {
			this.status = null;
		} else {
			this.status = status;
		}
	}
    //用于模糊查询
	public String getProductNameQuery() {
		if (productName != null) {
			return "%" + productName + "%";
		}
		return null;
	}
	
	// 返回Long
	public Long getCodingContentLong() {
		if (getCodingContent() != null) {
			return Long.valueOf(getCodingContent());
		}
		return null;
	}

	// 根据传入类型返回值
	public String getCodingContentStyleString(String style) {
		if (getCoding() != null) {
			if (getCoding().equals(style)) {
				return getCodingContent();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	// 根据传入类型返回值
	public Long getCodingContentStyleLong(String style) {
		if (getCoding() != null) {
			if (getCoding().equals(style)) {
				return getCodingContentLong();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "ExcelProductListForm [coding=" + coding + ", codingContent="
				+ codingContent + ", productName=" + productName
				+ ", sellerName=" + sellerName + ", productAuthor="
				+ productAuthor + ", productMcCategory=" + productMcCategory
				+ ", status=" + status + ", ismore=" + ismore + ", export="
				+ Arrays.toString(export) + "]";
	}
	
}
