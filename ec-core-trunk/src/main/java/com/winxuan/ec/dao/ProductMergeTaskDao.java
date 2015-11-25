package com.winxuan.ec.dao;

import com.winxuan.ec.model.product.ProductMergeTask;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * User: admin
 * Date: 13-4-27
 * Time: 下午4:09
 */
public interface ProductMergeTaskDao {

    /**
     * 商品合并任务列表
     *
     * @param parameters
     * @param pagination
     * @return
     */
    @Query("from ProductMergeTask p")
    @Conditions({
            @Condition("p.status.id = :status"),
            @Condition("p.id > :id")
            ,@Condition("p.saleStatus.id = :salestatus")
    })
    @OrderBys({
            @OrderBy("p.id")
    })
    List<ProductMergeTask> listProductMergeTask(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination);

    @Query("from ProductMergeTask p")
    @Conditions({
            @Condition("p.product.id = :productid"),
            @Condition("p.productSale.id = :productsaleid")
    })
    ProductMergeTask fetchProductMergeTask(@ParameterMap Map<String, Object> parameters);



    @Update
    void update(ProductMergeTask task);
    
    @Save
    void save(ProductMergeTask task);
    
}
