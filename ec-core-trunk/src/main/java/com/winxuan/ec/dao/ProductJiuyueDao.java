package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;

/**
 * 9月映射表.
 * User: juqkai
 * Date: 13-4-28
 * Time: 下午3:00
 */
public interface ProductJiuyueDao {
    @Query("from ProductJiuyue p")
    @Conditions({
            @Condition("p.product.id = :productid"),
            @Condition("p.productSale.id = :productsaleid"),
            @Condition("p.jiuyueProductid= :jiuyueProductid")
    })
    ProductJiuyue fetctProductJiuyue(@ParameterMap Map<String, Object> parameters);

    @Update
    void update(ProductJiuyue jiuyue);
    
    @Save
    void save(ProductJiuyue jiuyue);
    
    /**
     * 查询关联关系
     */
	@Query("select p9 from ProductJiuyue p9 where p9.jiuyueBookid = ?")
	ProductJiuyue selectMatchup(Long bookid);
	 
	/**
     * 根据ISBN查询查询
     */
	@Query("select p from Product p  where p.barcode = ? and sort = 11001 and complex = 0 ")
	List<Product> queryProductForISBN(String isbn);
	
    
}
