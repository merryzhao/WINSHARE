package com.winxuan.ec.task.model.robot;


/**
 * robot商品分类 变更日志
 * @author heyadong
 * @version 1.0, 2012-9-24 下午04:57:20
 */
public class RobotProductCategoryLog {
    
    /**
     * 已处理
     */
    public static final int STATUS_PROCESSED = 1;
    
    /**
     * 未处理
     */
    public static final int STATUS_UNPROCESSED = 0;
    
    private Long id;
    private Long productId;
    private String barcode;
    private String categories;
    private int status;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
  
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
