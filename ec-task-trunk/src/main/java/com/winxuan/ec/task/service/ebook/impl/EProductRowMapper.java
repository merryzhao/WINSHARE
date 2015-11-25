package com.winxuan.ec.task.service.ebook.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.model.ebook.EProduct;

/**
 * 电子书商品结果map封装
 * @author luosh
 *
 */
public class EProductRowMapper implements RowMapper<EProduct>{

	@Override
	public EProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		EProduct product = new EProduct();
		
		product.setProductId(rs.getLong("PRODUCT_ID"));
		product.setProductName(rs.getString("PRODUCT_NAME").toString());
		product.setProductDescription(rs.getString("PRODUCT_DESCRIPTION"));
		product.setProductType(rs.getInt("PRODUCT_TYPE"));
		
		product.setIsValid(rs.getInt("IS_VALID"));
		product.setProductLink(rs.getString("PRODUCT_LINK"));
		product.setAssignPercent(rs.getDouble("ASSIGN_PERCENT"));
		product.setProductCover(rs.getString("PRODUCT_COVER"));
		product.setCreateDatetime(rs.getDate("CREATE_DATETIME"));
		product.setCreateBy(rs.getString("CREATE_BY"));
		product.setUpdateDatetime(rs.getDate("UPDATE_DATETIME"));
		product.setUpdateBy(rs.getString("UPDATE_BY"));
		product.setDeleteFlag(rs.getInt("DELETE_FLAG"));
		product.setIsFrozen(rs.getInt("IS_FROZEN"));
		product.setValidDatetime(rs.getDate("VALID_DATETIME"));
		product.setValidAdminUserId(rs.getLong("VALID_ADMIN_USER_ID"));
		product.setInvalidDatetime(rs.getDate("INVALID_DATETIME"));
		product.setInvalidAdminUserId(rs.getLong("INVALID_ADMIN_USER_ID"));
		product.setInvalidReason(rs.getLong("INVALID_REASON"));
		product.setInvalidReasonDescription(rs.getString("INVALID_REASON_DESCRIPTION"));
		product.setUnfrozenDatetime(rs.getDate("UNFROZEN_DATETIME"));
		product.setUnfrozenAdminUserId(rs.getLong("UNFROZEN_ADMIN_USER_ID"));
		product.setFrozenDatetime(rs.getDate("FROZEN_DATETIME"));
		product.setFrozenAdminUserId(rs.getLong("FROZEN_ADMIN_USER_ID"));
		product.setFrozenReason(rs.getLong("FROZEN_REASON"));
		product.setFrozenReasonDescription(rs.getString("FROZEN_REASON_DESCRIPTION"));
		product.setUndeleteDatetime(rs.getDate("UNDELETE_DATETIME"));
		product.setUndeleteAdminUserId(rs.getLong("UNDELETE_ADMIN_USER_ID"));
		product.setDeleteDatetime(rs.getDate("DELETE_DATETIME"));
		product.setDeleteAdminUserId(rs.getLong("DELETE_ADMIN_USER_ID"));
		product.setDeleteReason(rs.getLong("DELETE_REASON"));
		product.setDeleteReasonDescription(rs.getString("DELETE_REASON_DESCRIPTION"));
		product.setBookCount(rs.getInt("BOOK_COUNT"));
		product.setSellCount(rs.getLong("SELLCOUNT"));
		product.setPrice(rs.getBigDecimal("PRICE"));
		product.setPercent(rs.getBigDecimal("PERCENT"));
		product.setPayforPrice(rs.getBigDecimal("PAYFOR_PRICE"));
		product.setPackagePercent(rs.getBigDecimal("PACKAGE_PERCENT"));
		product.setCreateDate(rs.getDate("CREATEDATE"));
		product.setSalePrice(rs.getBigDecimal("SALE_PRICE"));
		product.setCostPrice(rs.getBigDecimal("COST_PRICE"));
		return product;
	}

}
