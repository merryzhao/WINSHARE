package com.winxuan.ec.model.product;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.User;

/**
 * 绑定销售日志
 * @author juqkai(juqkai@gmail.com)
 */
@Entity
@Table(name="product_sale_bundle_log")
public class ProductSaleBundleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //主商品
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="master")
    private ProductSale master;
    //捆绑商品
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productsale")
    private ProductSale productSale;
    //操作类型
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="operate")
    private Code operate;
    //优惠价格
    @Column(name="savemoney")
    private BigDecimal saveMoney;
    //操作用户
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="operator")
    private User operateUser;
    //操作时间
    @Column(name="operatetime")
    private Date operateDate;
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public ProductSale getProductSale() {
        return productSale;
    }
    public void setProductSale(ProductSale productSale) {
        this.productSale = productSale;
    }
    public Code getOperate() {
        return operate;
    }
    public void setOperate(Code operate) {
        this.operate = operate;
    }
    public User getOperateUser() {
        return operateUser;
    }
    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }
    public Date getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
}
