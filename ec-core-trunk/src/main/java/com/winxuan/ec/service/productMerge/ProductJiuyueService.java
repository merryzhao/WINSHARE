package com.winxuan.ec.service.productMerge;

import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.ec.model.product.ProductSale;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-28
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public interface ProductJiuyueService {
    /**
     * 得到九月对应关系
     * @param jiu
     * @return
     */
    ProductJiuyue fetchProductJiuyue(ProductSale jiu);

    /**
     * 根据9yue网id得到文轩网商品
     * @param id
     * @return
     */
    ProductJiuyue getProductSaleBySeptember(Long id);
    
    void update(ProductJiuyue jiuyue);

    /**
     * 得到中图
     * @param jiu
     * @return
     */
     String fetchJiuyueMiddleImg(ProductSale jiu);
    /**
     * 得到小图
     * @param jiu
     * @return
     */
    String fetchJiuyueSmallImg(ProductSale jiu);
    /**
     * 得到大图
     * @param jiu
     * @return
     */
    String fetchJiuyueBigImg(ProductSale jiu) ;
}
