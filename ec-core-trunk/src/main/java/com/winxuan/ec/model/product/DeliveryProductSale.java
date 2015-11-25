package com.winxuan.ec.model.product;

/**
 * 发货商品
 * @author heyadong
 * @version 1.0, 2012-11-16 上午11:52:38
 */
public class DeliveryProductSale {
    //发货商品SAP CODE
    private String sapCode;
    
    //发货数量 
    private int deliveryQuantity;
    
    public String getSapCode() {
        return sapCode;
    }
    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }
  
    public int getDeliveryQuantity() {
        return deliveryQuantity;
    }
    public void setDeliveryQuantity(int deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }
}
