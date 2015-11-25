package com.winxuan.ec.service.promotion;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.promotion.PromotionData;
import com.winxuan.ec.model.promotion.PromotionDataTree;
import com.winxuan.framework.pagination.Pagination;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-18 下午2:26:12 --!
 * @description:
 ******************************** 
 */
public interface PromotionDataService {

    void save(PromotionData promotionData);

    void save(PromotionDataTree promotionDataTree);

    void delete(PromotionData promotionData);

    PromotionDataTree getByName(String name);

    /**
     * 清除所有数据
     */
    void clearAll();

    /**
     * 获取所有的会场数据,数据量不大
     * 
     * @return
     */
    List<PromotionDataTree> findPromotionDataTree();

    Map<Long, String> promotionDataTreeMap();

    List<PromotionData> find(Map<String, Object> parameters, Pagination pagination, short orderIndex);

    /**
     * 根据分组查询
     * 
     * @param venueGroup
     * @param group
     * @return
     */
    List<PromotionData> find(String venueGroup, String group);

    /**
     * 得到主会场下面的分会场或者分组
     * 
     * @param venueGroup
     * @return
     */
    List<PromotionData> venueGroupChild(String venueGroup);

    /**
     * 得到分会场的上面的主会场
     * 
     * @param group
     * @return
     */
    PromotionData groupParent(String group);

    /**
     * 获取根节点
     * 
     * @return
     */
    PromotionDataTree getRoot();

}
