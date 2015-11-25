package com.winxuan.ec.model.product;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品合并.
 * User: juqkai
 * Date: 13-4-27
 * Time: 下午4:02
 */
@Table(name = "product_merge_task")
@Entity
public class ProductMergeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "productid")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "productsaleid")
    private ProductSale productSale;

    @Column
    private String isbn;

    @Column(name = "createtime")
    private Date createDate;

    @Column(name = "lasttime")
    private Date lastTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private Code status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private Employee user;

    @Column
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salestatus")
    private Code saleStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSale getProductSale() {
        return productSale;
    }

    public void setProductSale(ProductSale productSale) {
        this.productSale = productSale;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Code getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Code saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getIsbn() {
        return isbn;

    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Code getStatus() {
        return status;
    }

    public void setStatus(Code status) {
        this.status = status;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }
}
