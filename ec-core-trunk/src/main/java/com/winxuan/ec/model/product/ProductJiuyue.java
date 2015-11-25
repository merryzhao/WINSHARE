package com.winxuan.ec.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 文轩九月对应表
 * User: juqkai
 * Date: 13-4-28
 * Time: 下午2:44
 */
@Entity
@Table(name="product_9yue")
public class ProductJiuyue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="9yue_bookid")
    private Long jiuyueBookid;

    @Column(name="9yue_productid")
    private Long jiuyueProductid;

    @Column(name="isbn")
    private String isbn;

    @ManyToOne()
    @JoinColumn(name = "productid")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "productsaleid")
    private ProductSale productSale;

    @Column(name="hasfangzheng")
    private boolean hasFangzheng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJiuyueBookid() {
        return jiuyueBookid;
    }

    public void setJiuyueBookid(Long jiuyueBookid) {
        this.jiuyueBookid = jiuyueBookid;
    }

    public Long getJiuyueProductid() {
        return jiuyueProductid;
    }

    public void setJiuyueProductid(Long jiuyueProductid) {
        this.jiuyueProductid = jiuyueProductid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public boolean isHasFangzheng() {
        return hasFangzheng;
    }

    public void setHasFangzheng(boolean hasFangzheng) {
        this.hasFangzheng = hasFangzheng;
    }
}
