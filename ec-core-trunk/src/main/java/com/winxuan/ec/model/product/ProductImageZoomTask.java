package com.winxuan.ec.model.product;

import com.winxuan.ec.model.code.Code;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 商品图片任务
 * User: juqkai(juqkai@gmail.com)
 * Date: 13-5-9
 * Time: 下午3:31
 */
@Entity
@Table(name = "product_image_zoom_task")
public class ProductImageZoomTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @ManyToOne()
    @JoinColumn(name = "product")
    private Product product;
    @Column
    private String src;
    @ManyToOne()
    @JoinColumn(name = "status")
    private Code status;

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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Code getStatus() {
        return status;
    }

    public void setStatus(Code status) {
        this.status = status;
    }
}
