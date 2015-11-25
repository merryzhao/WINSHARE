package com.winxuan.ec.service.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.PromotionDataDao;
import com.winxuan.ec.model.promotion.PromotionData;
import com.winxuan.ec.model.promotion.PromotionDataTree;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-20 上午9:44:42 --!
 * @description:
 ******************************** 
 */
@Service("promotionDataService")
@Transactional(rollbackFor = Exception.class)
public class PromotionDataServiceImpl implements PromotionDataService {

    private static final int DEFALUT_PAGESIZE = 10000;

    private static final short DEFAULT_SORT = 1;

    @InjectDao
    private PromotionDataDao promotionDataDao;

    @Override
    public void save(PromotionData promotionData) {
        promotionDataDao.save(promotionData);

    }

    @Override
    public void delete(PromotionData promotionData) {
        promotionDataDao.delete(promotionData);

    }

    @Override
    public List<PromotionData> find(Map<String, Object> parameters, Pagination pagination, short orderIndex) {
        return promotionDataDao.find(parameters, pagination, orderIndex);
    }

    @Override
    public List<PromotionData> find(String venueGroup, String group) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("venueGroup", venueGroup);
        parameters.put("group", group);
        return this.find(parameters, new Pagination(DEFALUT_PAGESIZE), DEFAULT_SORT);
    }

    @Override
    public List<PromotionData> venueGroupChild(String venueGroup) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("venueGroup", venueGroup);
        return this.find(parameters, new Pagination(DEFALUT_PAGESIZE), DEFAULT_SORT);
    }

    @Override
    public PromotionData groupParent(String group) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("group", group);
        List<PromotionData> result = this.find(parameters, new Pagination(DEFALUT_PAGESIZE), DEFAULT_SORT);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public void clearAll() {
        this.promotionDataDao.clearPromotionData();
        this.promotionDataDao.clearPromotionDataTree();
    }

    @Override
    public PromotionDataTree getRoot() {
        return this.promotionDataDao.root();
    }

    @Override
    public PromotionDataTree getByName(String name) {
        return this.promotionDataDao.getByName(name).get(0);
    }

    @Override
    public void save(PromotionDataTree promotionDataTree) {
        this.promotionDataDao.save(promotionDataTree);

    }

    @Override
    public List<PromotionDataTree> findPromotionDataTree() {
        return this.promotionDataDao.findPromotionDataTree();
    }

    public Map<Long, String> promotionDataTreeMap() {
        Map<Long, String> map = new HashMap<Long, String>();
        List<PromotionDataTree> result = this.findPromotionDataTree();
        for (PromotionDataTree promotionDataTree : result) {
            map.put(promotionDataTree.getId(), promotionDataTree.getName());
        }
        return map;

    }

}
