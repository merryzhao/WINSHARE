/**
 * 
 */
package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author john
 *
 */
public interface ProductSaleDao {

	@Query("from ProductSale ps where ps.id = ?")
	ProductSale findProductSale(Long productSaleId);
	
	@Query("from ProductSale p")
	@Conditions({
		@Condition("p.id in:productSaleIds"),
		@Condition("p.outerId in:outerIds")
	})
	List<ProductSale> findProductSales(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination);

	@Query(value="select ps.id, ps.salestatus, if(ps.supplytype=13101, pss.stock, pss.virtual) stock, pss.sales, if(psi.id is not null, 'false', 'true') as stockstatus," +
			" ps.supplytype, if(p.complex=1, 'true', 'false') as complex " + 
			" from product_sale ps left join product_sale_incorrectstock psi on ps.id=psi.productsale and psi.changed=0 and psi.dc=:dcId, product p, product_sale_stock pss " + 
			" where ps.product=p.id and ps.id=pss.productsale and ps.id in :productSaleIds and pss.dc=:dcId", sqlQuery=true)
	List<Map<String, Object>> getProductSaleStockByDc(@Parameter("productSaleIds")List<Long> productSaleIds, @Parameter("dcId")Long dcId);
	
	@Query("from ProductSale ps")
	@Conditions({
		@Condition("ps.product=:product")
	})
	ProductSale getPsByP(@Parameter("product") Product product);
}
