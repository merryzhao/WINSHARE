package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.promotion.PromotionData;
import com.winxuan.ec.model.promotion.PromotionDataTree;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-18 下午2:09:50 --!
 * @description:
 ******************************** 
 */
public interface PromotionDataDao {

    @Save
    void save(PromotionData promotionData);
    
    @Save
    void save(PromotionDataTree promotionDataTree);

    @Delete
    void delete(PromotionData promotionData);

    @Query(sqlQuery = true, value = "delete from promotion_data", executeUpdate = true)
    void clearPromotionData();
    
    @Query(sqlQuery = true, value = "delete from promotion_data_tree where id <> 1", executeUpdate = true)
    void clearPromotionDataTree();
    
    @Query("from PromotionDataTree")
    List<PromotionDataTree> findPromotionDataTree();
    
    @Query("from PromotionDataTree pdt where pdt.id = 1")
    PromotionDataTree root();

    @Query("from PromotionDataTree pdt where pdt.name = ?")
    List<PromotionDataTree> getByName(String name);

    @Query("from PromotionData p")
    @Conditions({ @Condition("p.venueGroup  =:venueGroup"), @Condition("p.group  =:group"),
            @Condition("p.tree  =:tree") })
    @OrderBys({ @OrderBy("p.index desc"), @OrderBy("p.index asc") })
    List<PromotionData> find(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination,
                             @Order short orderIndex);

}
