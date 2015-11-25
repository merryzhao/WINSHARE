package com.winxuan.ec.admin.controller.promotion;

import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.promotion.PromotionData;
import com.winxuan.ec.model.promotion.PromotionDataTree;
import com.winxuan.ec.service.promotion.PromotionDataService;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-25 上午11:05:37  --!
 * @description:
 ********************************
 */
@Component("promotionDataConvert")
public class PromotionDataConvert {

    @Autowired
    PromotionDataService promotionDataService;


    public JSONObject convert(PromotionDataTree pdt) throws Exception {
        Set<PromotionData> pdVoList = pdt.getPromotionDataList();
        JSONObject root = new JSONObject();
        root.put("title", pdt.getName());
        JSONArray array = new JSONArray();
        if (CollectionUtils.isNotEmpty(pdVoList)) {
            for (PromotionData promotionData : pdVoList) {
                PromotionDataVo vo = new PromotionDataVo();
                BeanUtils.copyProperties(vo, promotionData);
                vo.setSalePrice(promotionData.getSalePrice().setScale(2) + "");
                vo.setListPrice(promotionData.getListPrice().setScale(2) + "");
                array.add(vo);
            }
        }
        root.put("books", array);
        Set<PromotionDataTree> childList = pdt.getChildren();
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(childList)) {
            for (PromotionDataTree promotionDataTree : childList) {
                jsonArray.add(this.convert(promotionDataTree));
            }
        }
        root.put("bookList", jsonArray);
        return root;

    }
}
