package com.winxuan.ec.exception;

import java.io.Serializable;

import com.winxuan.ec.model.product.ProductManageGrade;

/**
 * @author yuhu
 * @version 1.0,2012-5-8上午11:49:34
 */
public class ProductSaleManageGradeException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ProductSaleManageGradeException(Serializable businessObject,
			String message) {
		super(businessObject, message);
	}
	
	public ProductManageGrade getManageGrade(){
		return (ProductManageGrade) getBusinessObject();
	}
}
