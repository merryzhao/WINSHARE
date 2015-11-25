package com.winxuan.ec.service.productMerge;

import com.winxuan.ec.model.product.ProductMerge;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;

import java.util.List;
import java.util.Map;

/**
 * 商品合并
 * User: juqkai(juqkai@gmail.com)
 * Date: 13-4-27
 * Time: 上午11:28
 */
public interface ProductMergeService {
    /**
     * 获取合并商品项
     * @return 合并项, Key为主商品, Value主商品对应的文轩商品列表
     */
    Map<ProductSale, List<ProductSale>> fetchMergeItem();

    /**
     * 合并商品
     * @param obj
     */
    void mergeItem(Employee employee, ProductMerge productMerge);
    
    /**
     * 取消操作
     * @param employee
     * @param jiuyueProductSale
     */
    void cancelOperate(Employee employee,ProductSale  jiuyueProductSale);

}
