package com.winxuan.eleven.model;

import java.math.BigDecimal;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-11-6 下午1:19:03  --!
 * @description:
 ********************************
 */
public class DoubleEleven {

    private int vId;

    private String vName;

    private int gId;

    private String gName;

    private int ecId;

    private String productName;

    private BigDecimal salePrice = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

    private String productUrl;

    private String favoriteLink;

    private String imagUrl;

    private int totalSales;

    private String introduction;

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public int getEcId() {
        return ecId;
    }

    public void setEcId(int ecId) {
        this.ecId = ecId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getFavoriteLink() {
        return favoriteLink;
    }

    public void setFavoriteLink(String favoriteLink) {
        this.favoriteLink = favoriteLink;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

}
