package com.winxuan.ec.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleBundle;
import com.winxuan.ec.model.product.ProductSaleBundleLog;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;

/**
 * 
 * @author juqkai(juqkai@gmail.com)
 */
public interface ProductSaleBundleService {
    /**
     * 获取搭配商品
     * @param id
     * @return
     */
    ProductSaleBundle get(Long id);
    /**
     * 保存搭配商品
     * @param bundleSale
     */
    void save(ProductSaleBundle bundleSale);
    /**
     * 批量保存搭配商品
     * @param user 操作用户
     * @param master 主商品列表 
     * @param products 子商品
     */
    void save(Employee user, List<ProductSale> master,Map<ProductSale, BigDecimal> products);
    /**
     * 编辑搭配商品
     * @param master 主商品
     * @param psbs 子商品及子商品优惠价
     * @param user 操作用户
     */
    void edit(ProductSale master, Map<ProductSale, BigDecimal> psbs,  User user);
    /**
     * 修改搭配商品
     * @param bundleSale
     */
    void update(ProductSaleBundle bundleSale);
    /**
     * 删除
     * @param psbid
     * @param user
     */
    void del(Long psbid, User user);
    /**
     * 保存操作日志
     * @param log
     */
    void saveLog(ProductSaleBundleLog log);
    /**
     * 根据主商品显示操作日志
     * @param masterId
     * @return
     */
    List<ProductSaleBundleLog> listLogForMaster(Long masterId);
}
