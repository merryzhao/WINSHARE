package com.winxuan.ec.model.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 搭配销售商品
 * @author juqkai(juqkai@gmail.com)
 */
@Entity
@Table(name="product_sale_bundle")
public class ProductSaleBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //主商品
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="master")
    private ProductSale master;
    //商品
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productSale")
    private ProductSale productSale;
    //优惠价
    @Column(name="savemoney")
    private BigDecimal saveMoney;
    //状态
    @Column(name="status")
    private Boolean status;
    
    public ProductSaleBundle(){}
    public ProductSaleBundle(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public ProductSale getProductSale() {
        return productSale;
    }
    public void setProductSale(ProductSale productSale) {
        this.productSale = productSale;
    }
    public BigDecimal getSaveMoney() {
        return saveMoney;
    }
    public void setSaveMoney(BigDecimal saveMoney) {
        this.saveMoney = saveMoney;
    }
    public ProductSale getMaster() {
        return master;
    }
    public void setMaster(ProductSale master) {
        this.master = master;
    }
    
}
