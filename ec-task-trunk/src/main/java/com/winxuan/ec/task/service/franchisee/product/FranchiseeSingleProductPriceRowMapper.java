/**
 * 
 */
package com.winxuan.ec.task.service.franchisee.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.franchisee.model.product.FranchiseeSingleProductPrice;
import com.winxuan.franchisee.model.product.Product;
import com.winxuan.franchisee.model.user.Franchisee;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-6-11
 */
public class FranchiseeSingleProductPriceRowMapper implements RowMapper<FranchiseeSingleProductPrice> {

	@Override
	public FranchiseeSingleProductPrice mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		FranchiseeSingleProductPrice franchiseeSingleProductPrice = new FranchiseeSingleProductPrice();
		franchiseeSingleProductPrice.setFranchisee(new Franchisee(rs.getLong("franchisee")));
		franchiseeSingleProductPrice.setProduct(new Product(rs.getLong("product")));
		return franchiseeSingleProductPrice;
	}

}
