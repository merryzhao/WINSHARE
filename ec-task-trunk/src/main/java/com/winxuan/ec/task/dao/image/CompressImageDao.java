package com.winxuan.ec.task.dao.image;

import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-14
 */
public interface CompressImageDao {
	@Query("from Product p where p.merchId=?")
	Product getProduct(Long merchId);

	@Query("from ProductImage p")
	@Conditions({
		@Condition("p.product = :product"),
		@Condition("p.type = :type")
	})
	ProductImage getProductImage(@ParameterMap Map<String, Object> parameters);

	@Query(executeUpdate=true, sqlQuery=true, value="delete from product_image where product=? and type=?")
	void deleteProductImage(long productId, short type);

	@Query(executeUpdate=true, sqlQuery=true, value="update mdm_attachment set description=? where attachmentid=?")
	void updateMDMImage(String flag1, long id);
	
	@Query(executeUpdate=true, sqlQuery=true, value="update mdm_attachment set description=? where description='222'")
	void updateMDMImage(String flag);
	
	@Query(executeUpdate=true, sqlQuery=true, value="update product set hasimage=? where id=?")
	void updateProductImage(short flag, long productId);
	
	@Query(executeUpdate=true, sqlQuery=true, value="delete from mdm_attachment where attachmentid=?")
	void deleteMDMImage(long id);
	
	@Query(executeUpdate=true, sqlQuery=true, value="delete from mdm_attachment where attachmentid in (?)")
	void deleteMDMImage(String ids);
}