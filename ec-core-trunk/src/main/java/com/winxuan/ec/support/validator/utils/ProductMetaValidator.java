package com.winxuan.ec.support.validator.utils;

import java.util.Set;

import com.winxuan.ec.exception.ProductMetaException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaEnum;
import com.winxuan.framework.util.StringUtils;

/**
 * @author yuhu
 * @version 1.0,2011-10-24下午02:18:10
 */
public class ProductMetaValidator {

	/**
	 * 验证productMeta的value是否符合当前meta的规则
	 * @param meta meta中的字段值必须完整，value字段标示该meta的值
	 * @return
	 * @throws ProductMetaException
	 */
	public static boolean isValid(ProductMeta meta) throws ProductMetaException{
		if(meta == null || !meta.isAvailable()){
			return false;
		}
		if (!meta.isAllowNull() && StringUtils.isNullOrEmpty(meta.getValue())) {
			throw new ProductMetaException(meta.getName() + "属性不能为空！");
		}
		if (!StringUtils.isNullOrEmpty(meta.getValue())
				&& meta.getLength() < meta.getValue().length()) {
			throw new ProductMetaException(meta.getName() + "属性长度不能超过"
					+ meta.getLength());
		}
		if (!StringUtils.isNullOrEmpty(meta.getValue())
				&& Code.FIELD_TYPE_NUMBER.equals(meta.getType().getId())
				&& !meta.getValue().matches("[0-9]+")) {
			throw new ProductMetaException(meta.getName() + "属性只能为数字类型！");
		}
		if (!StringUtils.isNullOrEmpty(meta.getValue())
				&& Code.FIELD_TYPE_ENUM.equals(meta.getType().getId())) {
			Set<ProductMetaEnum> enums = meta.getEnumList();
			boolean isEnum = false;
			for (ProductMetaEnum metaEnum : enums) {
				if (metaEnum.getValue().equals(meta.getValue())) {
					isEnum = true;
					break;
				}
			}
			 if(!isEnum){
				 throw new ProductMetaException(meta.getName()+"属性只能选择给定的值！");
			 }
		}
		
		return true;
	}
}
