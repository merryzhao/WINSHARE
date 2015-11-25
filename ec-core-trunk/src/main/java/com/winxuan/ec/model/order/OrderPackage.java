package com.winxuan.ec.model.order;

import java.util.Set;

import com.winxuan.ec.model.delivery.DeliveryCompany;
import com.winxuan.ec.model.product.DeliveryProductSale;


/**
 * 订单发货打包. 批量发货
 * @author heyadong
 * @version 1.0, 2012-11-16 上午11:14:09
 */
public class OrderPackage {
    //订单
    private Order order;
    //发货商品
    private Set<DeliveryProductSale> productSaleSet;
  
    //快递公司
    private DeliveryCompany deliveryCompany;
    //快递编码
    private String deliveryCode;
  
    
    
   
 
    public String getDeliveryCode() {
        return deliveryCode;
    }
    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
  
    public DeliveryCompany getDeliveryCompany() {
        return deliveryCompany;
    }
    public void setDeliveryCompany(DeliveryCompany deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }
    
    public Set<DeliveryProductSale> getProductSaleSet() {
        return productSaleSet;
    }
    public void setProductSaleSet(Set<DeliveryProductSale> productSaleSet) {
        this.productSaleSet = productSaleSet;
    }
    
    /**
     * 根据SAP CODE 查看是发货清单是否有商品
     * @param sapCode
     * @return
     */
    public boolean hasProductSale(String sapCode) {
        if (productSaleSet != null && sapCode != null) {
            for (DeliveryProductSale dp : productSaleSet) {
                if (sapCode.equals(dp.getSapCode())){
                    return true;
                }
            }
        }
        return false;
    }
}
