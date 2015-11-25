package com.winxuan.ec.model.promotion;

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

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-18 下午1:40:35 --!
 * @description:促销数据,该数据从excel导入,故没有和相关实体做关联映射,用于支持业务做各渠道做促销活动的页面
 ******************************** 
 */

@Entity
@Table(name = "promotion_data")
public class PromotionData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "outerid")
    private String outerId;

    @Column(name = "ecid")
    private Long ecId;

    @Column(name = "productname")
    private String productName;

    @Column(name = "listprice")
    private BigDecimal listPrice;

    @Column(name = "saleprice")
    private BigDecimal salePrice;

    @Column
    private BigDecimal discount;

    @Column(name = "producturl")
    private String productUrl;

    @Column(name = "buylink")
    private String buyLink;

    @Column(name = "favoritelink")
    private String favoriteLink;

    @Column(name = "weeksales")
    private Integer weekSales;

    @Column(name = "monthsales")
    private Integer monthSales;

    @Column(name = "totalsales")
    private Integer totalSales;

    @Column(name = "imgurl")
    private String imgUrl;

    @Column
    private String introduction;

    @Column(name = "_index")
    private Integer index;

    @Column
    private Integer inventory;

    @Column(name = "createtime")
    private Date createTime;

    @Column
    private Long operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venueTree")
    private PromotionDataTree venueTree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public Long getEcId() {
        return ecId;
    }

    public void setEcId(Long ecId) {
        this.ecId = ecId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getFavoriteLink() {
        return favoriteLink;
    }

    public void setFavoriteLink(String favoriteLink) {
        this.favoriteLink = favoriteLink;
    }

    public Integer getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(Integer weekSales) {
        this.weekSales = weekSales;
    }

    public Integer getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Integer monthSales) {
        this.monthSales = monthSales;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

    public PromotionDataTree getVenueTree() {
        return venueTree;
    }

    public void setVenueTree(PromotionDataTree venueTree) {
        this.venueTree = venueTree;
    }

}
