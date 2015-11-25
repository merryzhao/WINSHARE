/*
 * @(#)ProductSale.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.cm.Content;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.customer.CustomerQuestion;
import com.winxuan.ec.model.promotion.Promotion;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.shop.ShopCategory;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 销售商品
 * 
 * @author liuan
 * @version 1.0,2011-7-7
 */
@Entity
@Table(name = "product_sale")
public class ProductSale implements Serializable, Content {

    /**
     * 套装商品初始库存数量
     */
    public static final int COMPLEX_BASE_STOCK = -5;
    
    /**
     * 商品销售分级-A+
     */
    public static final String PRODUCT_SALE_GRADE_A_PLUS = "A+";
    
    /**
     * 商品销售分级-A
     */
    public static final String PRODUCT_SALE_GRADE_A = "A";

    /**
     * 商品销售分级-B+
     */
    public static final String PRODUCT_SALE_GRADE_B_PLUS = "B+";
    
    /**
     * 商品销售分级-B
     */
    public static final String PRODUCT_SALE_GRADE_B = "B";
    
    /**
     * 商品销售分级-B-
     */
    public static final String PRODUCT_SALE_GRADE_B_MINUS = "B-";
    
    /**
     * 商品销售分级-C+
     */
    public static final String PRODUCT_SALE_GRADE_C_PLUS = "C+";
    
    /**
     * 商品销售分级-C
     */
    public static final String PRODUCT_SALE_GRADE_C = "C";
    
    /**
     * 商品销售分级-C-
     */
    public static final String PRODUCT_SALE_GRADE_C_MINUS = "C-";
    
    /**
     * 商品销售分级-D
     */
    public static final String PRODUCT_SALE_GRADE_D = "D";
    
    /**
     * 商品销售分级-N
     */
    public static final String PRODUCT_SALE_GRADE_N = "N";

    /**
     * 商品上架的最低折扣
     */
    public static final BigDecimal MIN_DISCOUNT = BigDecimal.valueOf(0.2);

    private static final long serialVersionUID = -5742295724397880091L;

    private static final String E_BOOK = "E"; //电子书标识，用于99click统计
    
    private static final String PRINTED_BOOK = "T"; //纸书标识，用于99click统计
    
    private static final String NOT_BOOK = ""; //非图书标识，用于99click统计


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop")
    private Shop shop;

    @Column(name = "saleprice")
    private BigDecimal salePrice;

    @Column(name = "sellname")
    private String sellName;

    @Column
    private String subheading;

    @Column(name = "basicprice")
    private BigDecimal basicPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salestatus")
    private Code saleStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditstatus")
    private Code auditStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplytype")
    private Code supplyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storagetype")
    private Code storageType;

    @Column(name = "stockquantity")
    private int stockQuantity;

    @Column(name = "salequantity")
    private int saleQuantity;

    @Column(name = "outerid")
    private String outerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location")
    private Area location;

    @Column(name = "promotionprice")
    private BigDecimal promotionPrice;

    /**
     * 供应商
     */
    @Column(name = "vendor")
    private String vendor;

    /**
     * 促销中单个用户可购买量
     */
    @Column(name = "restrictquantity")
    private int restrictQuantity;

    /**
     * 促销总量
     */

    @Column(name = "promotionstotal")
    private int promotionsTotal;

    @Column(name = "promstarttime")
    private Date promotionStartTime;

    @Column(name = "promendtime")
    private Date promotionEndTime;

    @Column(name = "hasshopcategory")
    private int hasShopCategory;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column(name = "promvalue")
    private String promValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion")
    private Promotion promotion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductWarehouse.class)
    private Set<ProductWarehouse> stockList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = CustomerComment.class)
    @OrderBy("commentTime desc")
    private Set<CustomerComment> commentionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = CustomerQuestion.class)
    @OrderBy("askTime desc")
    private Set<CustomerQuestion> questionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductSaleRank.class)
    @OrderBy("rankTime desc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductSaleRank> rankList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master", targetEntity = ProductSaleBundle.class)
    @OrderBy("id desc")
    private Set<ProductSaleBundle> bundles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductManageGrade.class)
    private Set<ProductManageGrade> manageGrades;

    // 有没有搭配销售的商品
    @Column(name = "bundle")
    private boolean hasBundle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductRecommendation.class)
    @OrderBy("preference desc")
    private Set<ProductRecommendation> recommendationList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductBookingLog.class)
    private Set<ProductBookingLog> bookingLogs;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private ProductSalePerformance performance;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private ProductBooking booking;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductEbookMapping.class)
    private Set<ProductEbookMapping> septemberList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "product_sale_shopcategory",
            joinColumns = { @JoinColumn(name = "productsale", updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "shopcategory", updatable = false) })
    private Set<ShopCategory> shopCategoryList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_complex",
            joinColumns = { @JoinColumn(name = "complex") },
            inverseJoinColumns = { @JoinColumn(name = "item") })
    private Set<ProductSale> complexItemList;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_complex",
            joinColumns = { @JoinColumn(name = "item") },
            inverseJoinColumns = { @JoinColumn(name = "complex") })
    private Set<ProductSale> complexMasterList;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "master")
//    private ProductSale complexMaster;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSale", targetEntity = ProductSaleStock.class)
    @OrderBy("id desc")
    private Set<ProductSaleStock> productSaleStocks;

    /**
     * all transient objects puts here
     */
    @Transient
    private transient ProductTransient productTransient;
    
    

    public ProductSale() {

    }

    public ProductSale(Long id) {
        this.id = id;
    }

    public ProductTransient getProductTransient() {
        return productTransient;
    }

    public ProductTransient getDefaultTransient() {
        if (productTransient == null) {
            productTransient = new ProductTransient();
        }
        return productTransient;
    }

    public void setProductTransient(ProductTransient productTransient) {
        this.productTransient = productTransient;
    }

    public Set<ProductEbookMapping> getSeptemberList() {
        return septemberList;
    }

    public void setSeptemberList(Set<ProductEbookMapping> septemberList) {
        this.septemberList = septemberList;
    }

    /**
     * 添加一个店铺分类
     * 
     * @param shopCategory
     */
    public void addShopCategory(ShopCategory shopCategory) {
        if (shopCategoryList == null) {
            this.shopCategoryList = new HashSet<ShopCategory>();
        }
        shopCategoryList.add(shopCategory);
    }

    /**
     * @return the stockList
     */
    public Set<ProductWarehouse> getStockList() {
        return stockList;
    }

    /**
     * @param stockList
     *            the stockList to set
     */
    public void setStockList(Set<ProductWarehouse> stockList) {
        this.stockList = stockList;
    }

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

    public String getRemark() {
        return this.getDefaultTransient().getRemark();
    }

    public void setRemark(String remark) {
        this.getDefaultTransient().setRemark(remark);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getSellName() {
        if (StringUtils.isBlank(this.sellName)) {
            return product.getName();
        }
        return sellName;
    }

    public void setSellName(String sellName) {
        this.sellName = sellName;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public int getRestrictQuantity() {
        return restrictQuantity;
    }

    public void setRestrictQuantity(int restrictQuantity) {
        this.restrictQuantity = restrictQuantity;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public int getPromotionsTotal() {
        return promotionsTotal;
    }

    public void setPromotionsTotal(int promotionsTotal) {
        this.promotionsTotal = promotionsTotal;
    }

    public Date getPromotionStartTime() {
        return promotionStartTime;
    }

    public Set<ProductSaleBundle> getBundles() {
        return bundles;
    }

    public void setBundles(Set<ProductSaleBundle> bundles) {
        this.bundles = bundles;
    }

    public void setPromotionStartTime(Date promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
    }

    public Date getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(Date promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public Code getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(Code supplyType) {
        this.supplyType = supplyType;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Code getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Code auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Code getStorageType() {
        return storageType;
    }

    public void setStorageType(Code storageType) {
        this.storageType = storageType;
    }

    public Area getLocation() {
        return location;
    }

    public void setLocation(Area location) {
        this.location = location;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = MagicNumber.ZERO > stockQuantity ? MagicNumber.ZERO : stockQuantity;
	}

    public int getSaleQuantity() {
        return saleQuantity;
    }

	public void setSaleQuantity(int saleQuantity) {
		this.saleQuantity = MagicNumber.ZERO > saleQuantity ? MagicNumber.ZERO : saleQuantity;
	}

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public boolean isFlag() {
        return this.getDefaultTransient().isFlag();
    }

    public void setFlag(boolean flag) {
        this.getDefaultTransient().setFlag(flag);
    }

    /**
     * 获得商品的可用量。等于库存数量-销售占用数量
     * 
     * @return
     */
    public int getAvalibleQuantity() {
        int avalible = stockQuantity - saleQuantity;
        avalible = avalible < MagicNumber.ZERO ? MagicNumber.ZERO : avalible;
        return avalible;
    }

    /**
     * 可以订购的数量 当该商品参与限购活动的时候，该商品最多只能购买限制数量
     * 
     * @return
     */
    public int getCanPurchaseQuantity() {
        int avalible = getAvalibleQuantity();
        int purchasedQuantityAll = this.getDefaultTransient().getPurchasedQuantityAll();
        int purchasedQuantity = this.getDefaultTransient().getPurchasedQuantity();
        int lastPromotionsTotal = promotionsTotal - purchasedQuantityAll;
        int lastAnUserRestrictQuantity = restrictQuantity - purchasedQuantity;

        if (isHasPromotion() && lastPromotionsTotal > 0) {
            avalible = avalible > lastPromotionsTotal ? lastPromotionsTotal : avalible;
        }

        if (isHasPromotion() && lastAnUserRestrictQuantity > 0) {
            avalible = avalible > lastAnUserRestrictQuantity ? lastAnUserRestrictQuantity : avalible;
        }

        return avalible;
    }

    public boolean canSale() {
        return saleStatus.getId().equals(Code.PRODUCT_SALE_STATUS_ONSHELF) && getAvalibleQuantity() > 0
                && auditStatus.getId().equals(Code.PRODUCT_AUDIT_STATUS_PASS);
    }

    public boolean canDisplay() {
        return saleStatus.getId().equals(Code.PRODUCT_SALE_STATUS_ONSHELF)
                || saleStatus.getId().equals(Code.PRODUCT_SALE_STATUS_OFFSHELF);
    }

    public boolean getCanSale() {
        return canSale();
    }

    /**
     * 是否显示 停用和删除状态不显示
     * 
     * @return
     */
    public boolean isShow() {
        return !getSaleStatus().getId().equals(Code.PRODUCT_SALE_STATUS_EC_STOP)
                && !getSaleStatus().getId().equals(Code.PRODUCT_SALE_STATUS_SAP_STOP)
                && !getSaleStatus().getId().equals(Code.PRODUCT_SALE_STATUS_DELETED) && getShop().canSale();
    }

    public BigDecimal getDiscount() {
        BigDecimal listPrice = product.getListPrice();
        if (listPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getEffectivePrice().divide(listPrice, 2, RoundingMode.HALF_UP);
    }

    public Integer getIntegerDiscount() {
        return getDiscount().multiply(MagicNumber.BD_100).setScale(0, BigDecimal.ROUND_CEILING).intValue();
    }

    public Set<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(Set<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }

    public String getName() {
        return this.getEffectiveName();
    }

    public String getUrl() {
        return "http://www.winxuan.com/product/" + getId();
    }

    public ProductSalePerformance getPerformance() {
        try {
            performance.getId();
            return performance;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加商品预售日志
     * 
     * @param log
     */
    public void addBookingLog(ProductBookingLog log) {
        if (bookingLogs == null) {
            bookingLogs = new HashSet<ProductBookingLog>();
        }
        bookingLogs.add(log);
    }

    public void setPerformance(ProductSalePerformance performance) {
        this.performance = performance;
    }

    public ProductBooking getBooking() {
        try {
            booking.getId();
            return booking;
        } catch (Exception e) {
            return null;
        }
    }

    public void setBooking(ProductBooking booking) {
        this.booking = booking;
    }

    public Set<CustomerComment> getCommentionList() {
        return commentionList;
    }

    public void setCommentionList(Set<CustomerComment> commentionList) {
        this.commentionList = commentionList;
    }

    public Set<CustomerQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<CustomerQuestion> questionList) {
        this.questionList = questionList;
    }

    public Set<ProductSaleRank> getRankList() {
        return rankList;
    }

    public void setRankList(Set<ProductSaleRank> rankList) {
        this.rankList = rankList;
    }

    public int getRankCountByOneStar() {
        return getScoreCount(ProductSaleRank.STAR_ONE);
    }

    public int getRankCountByTwoStar() {
        return getScoreCount(ProductSaleRank.STAR_TWO);
    }

    public int getRankCountByThreeStar() {
        return getScoreCount(ProductSaleRank.STAR_THREE);
    }

    public int getRankCountByFourStar() {
        return getScoreCount(ProductSaleRank.STAR_FOUR);
    }

    public int getRankCountByFiveStar() {
        return getScoreCount(ProductSaleRank.STAR_FIVE);
    }

    public BigDecimal getShareByOneStar() {
        return getShareByScore(ProductSaleRank.STAR_ONE);
    }

    public BigDecimal getShareByTwoStar() {
        return getShareByScore(ProductSaleRank.STAR_TWO);
    }

    public BigDecimal getShareByThreeStar() {
        return getShareByScore(ProductSaleRank.STAR_THREE);
    }

    public BigDecimal getShareByFourStar() {
        return getShareByScore(ProductSaleRank.STAR_FOUR);
    }

    public BigDecimal getShareByFiveStar() {
        return getShareByScore(ProductSaleRank.STAR_FIVE);
    }

    private BigDecimal getShareByScore(short star) {
        if (rankList != null && !rankList.isEmpty()) {
            int count = rankList.size();
            return count == 0 ? BigDecimal.ZERO : new BigDecimal(getScoreCount(star)).divide(new BigDecimal(count), 2,
                    RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Deprecated
    private int getScoreCount(short star) {
        int count = 0;
        if (rankList != null && !rankList.isEmpty()) {
            for (ProductSaleRank productSaleRank : rankList) {
                if (productSaleRank.getRank() == star) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public BigDecimal getAvgStarScore() {
        return getAverageRank();
    }

    public int getHasShopCategory() {
        return hasShopCategory;
    }

    public void setHasShopCategory(int hasShopCategory) {
        this.hasShopCategory = hasShopCategory;
    }

    public boolean getNeedFreeFee() {
        return getEffectivePrice().compareTo(new BigDecimal("38")) >= 0 ? true : false;
    }

    public String getPromValue() {
        return promValue;
    }

    public void setPromValue(String promValue) {
        this.promValue = promValue;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public Code getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Code saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImageUrl() {
        if (this.getDefaultTransient().getImageUrl() == null) {
            String imageUrl = getProduct().getImageUrlFor200px();
            this.getDefaultTransient().setImageUrl(imageUrl);
        }
        return this.getDefaultTransient().getImageUrl();
    }

    public void setImageUrl(String imageUrl) {
        this.getDefaultTransient().setImageUrl(imageUrl);
    }

    public BigDecimal getListPrice() {
        return getProduct().getListPrice();
    }

    public String getAuthor() {
        return getProduct().getAuthor();
    }

    public Set<ProductSale> getComplexItemList() {
        return complexItemList;
    }

    public void setComplexItemList(Set<ProductSale> complexItemList) {
        this.complexItemList = complexItemList;
    }

    public Set<ProductSale> getComplexMasterList() {
		return complexMasterList;
	}

	public void setComplexMasterList(Set<ProductSale> complexMasterList) {
		this.complexMasterList = complexMasterList;
	}

	public Set<ProductRecommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(Set<ProductRecommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public boolean isHasBundle() {
        return hasBundle;
    }

    public void setHasBundle(boolean hasBundle) {
        this.hasBundle = hasBundle;
    }

    public ProductSale getComplexMaster() {
		if(CollectionUtils.isNotEmpty(getComplexMasterList())) {
			return getComplexMasterList().iterator().next();
		}
		return null;
	}
    
    public void addComplexItem(ProductSale item) {
        if (complexItemList == null) {
            complexItemList = new HashSet<ProductSale>();
        }
//        item.setComplexMaster(this);
        complexItemList.add(item);
    }

    public void removeComplexItem(ProductSale item) {
        if (complexItemList != null) {
            complexItemList.remove(item);
        }
//        item.setComplexMaster(null);
    }

	/**
     * 拷贝参数中销售商品的信息至当前销售商品<br/>
     * 拷贝的字段属性包括:<br/>
     * productSale.product.sort<br/>
     * productSale.product.mcCategory<br/>
     * productSale.product.workCategory<br/>
     * productSale.product.manageCategory<br/>
     * productSale.shop<br/>
     * productSale.auditStatus<br/>
     * productSale.supplyType<br/>
     * productSale.storageType<br/>
     * productSale.location<br/>
     * 
     * @param productSale1
     * @param productSale2
     */
    public void copy(ProductSale productSale) {
        Product product = productSale.getProduct();
        getProduct().setSort(product.getSort());
        getProduct().setMcCategory(product.getMcCategory());
        getProduct().setWorkCategory(product.getWorkCategory());
        getProduct().setManageCategory(product.getManageCategory());
        setShop(productSale.getShop());
        setAuditStatus(productSale.getAuditStatus());
        setSupplyType(productSale.getSupplyType());
        setStorageType(productSale.getStorageType());
        setLocation(productSale.getLocation());
        setOuterId(productSale.getOuterId());
    }

    /**
     * 是否有促销活动
     * 
     * @return
     */
    public boolean isHasPromotion() {
        Date now = new Date();
        if (promotionPrice == null || promotionStartTime == null || promotionEndTime == null) {
            return false;
        }
        int purchasedQuantityAll = this.getDefaultTransient().getPurchasedQuantityAll();
        int purchasedQuantity = this.getDefaultTransient().getPurchasedQuantity();
        if (salePrice.compareTo(promotionPrice) != 0 && promotionPrice.compareTo(BigDecimal.ZERO) > 0
                && promotionStartTime.before(now) && promotionEndTime.after(now)
                && (promotionsTotal <= 0 || promotionsTotal > purchasedQuantityAll)
                && (restrictQuantity <= 0 || restrictQuantity > purchasedQuantity)) {
            return true;
        }
        return false;
    }

    public boolean getHasPromotion() {
        return isHasPromotion();
    }

    /**
     * 获取商品当前所在的促销活动结束时间的毫秒数、促销价跟服务器的毫秒数
     * 
     * @return 第一个为long当前时间毫秒数，第二个为促销活动结束时间的毫秒，第三个为促销价,第四个为促销开始时间毫秒数
     */
    public Object[] getCurrentPromotion() {
        if (this.promotionPrice != null) {
            return new Object[] { System.currentTimeMillis(), promotionEndTime.getTime(), promotionPrice,
                    promotionStartTime.getTime() };
        }
        return new Object[MagicNumber.FOUR];
    }

    /**
     * 获取商品当前的有效价格
     * 
     * @return
     */
    public BigDecimal getEffectivePrice() {
        if (isHasPromotion()) {
            return promotionPrice;
        }
        return salePrice;
    }

    /**
     * 金卡价格
     * 
     * @return
     */
    public BigDecimal getGoldPrice() {
        BigDecimal price = getEffectivePrice();
        Long sort = getProduct().getSort().getId();
        if (sort.equals(Code.PRODUCT_SORT_BOOK) || sort.equals(Code.PRODUCT_SORT_VIDEO)) {
            if (canDiscountByCustomerGrade()) {
                return price.multiply(MagicNumber.GRADE_GOLD_DISCOUNT).setScale(1, BigDecimal.ROUND_HALF_UP)
                        .setScale(2);
            }
        }

        return price;
    }

    /**
     * 银卡价格
     * 
     * @return
     */
    public BigDecimal getSilverPrice() {
        BigDecimal price = getEffectivePrice();
        Long sort = getProduct().getSort().getId();
        if (sort.equals(Code.PRODUCT_SORT_BOOK) || sort.equals(Code.PRODUCT_SORT_VIDEO)) {
            if (canDiscountByCustomerGrade()) {
                return price.multiply(MagicNumber.GRADE_SILVER_DISCOUNT).setScale(1, BigDecimal.ROUND_HALF_UP)
                        .setScale(2);
            }
        }
        return price;
    }

    /**
     * 是否可以根据会员等级再打折
     * 
     * @return
     */
    private boolean canDiscountByCustomerGrade() {
        return getDiscount().compareTo(MagicNumber.SALE_PRICE_DISCOUNT) > 0 ? true : false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProductSale)) {
            return false;
        }
        ProductSale other = (ProductSale) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    /**
     * 预售商品
     * 
     * @return
     */
    public boolean isPreSale() {
        return Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING.equals(supplyType.getId());
    }

    /**
     * 是否是预售上架的商品
     * 
     * @return
     */
    public boolean getPreSaleCanBuy() {
        return isPreSale() && Code.PRODUCT_SALE_STATUS_ONSHELF.equals(this.getSaleStatus().getId());
    }

    /**
     * 是否是下架状态预售商品
     * 
     * @return
     */
    public boolean getPreSaleHasOffShelf() {
        if (booking == null) {
            return false;
        }
        return isPreSale() && Code.PRODUCT_SALE_STATUS_OFFSHELF.equals(this.getSaleStatus().getId());
    }

    /**
     * 在下架状态是否显示预售信息
     * 
     * @return
     */
    public boolean getPreSaleHasShow() {
        if (getPreSaleHasOffShelf()) {
            return booking.getEndDate().after(new Date());
        }
        return false;
    }

    /**
     * 检测当前商品是否已经设置了grade管理分类
     * 
     * @param grade
     * @return
     */
    public boolean hasManageGrade(Code grade) {
        if (grade == null || grade.getId() == null) {
            return true;
        }
        if (!CollectionUtils.isEmpty(manageGrades)) {
            for (ProductManageGrade mg : manageGrades) {
                if (grade.getId().equals(mg.getGrade().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否被停用
     * 
     * @return
     */
    public boolean isStopped() {
        if (getSaleStatus() == null) {
            return false;
        }
        if (Code.PRODUCT_SALE_STATUS_EC_STOP.equals(getSaleStatus().getId())
                || Code.PRODUCT_SALE_STATUS_SAP_STOP.equals(getSaleStatus().getId())) {
            return true;
        }
        return false;
    }

    /**
     * 是否被删除
     * 
     * @return
     */
    public boolean isDeleted() {
        if (getSaleStatus() != null && Code.PRODUCT_SALE_STATUS_DELETED.equals(getSaleStatus().getId())) {
            return true;
        }
        return false;
    }

    /**
     * 获取商品重量信息
     * 
     * @return
     */
    public int getWeight() {
        ProductMeta meta = new ProductMeta();
        meta.setId(ProductMeta.META_WEIGHT);
        if (getProduct() != null) {
            ProductExtend pe = getProduct().getProductExtend(meta);
            if (pe != null) {
            	return NumberUtils.toInt(pe.getValue(), MagicNumber.ZERO);
            }
        }
        return 0;
    }

    public Set<ProductManageGrade> getManageGrades() {
        return manageGrades;
    }

    public void setManageGrades(Set<ProductManageGrade> manageGrades) {
        this.manageGrades = manageGrades;
    }

    public Set<ProductBookingLog> getBookingLogs() {
        return bookingLogs;
    }

    public void setBookingLogs(Set<ProductBookingLog> bookingLogs) {
        this.bookingLogs = bookingLogs;
    }

    public int getPurchasedQuantity() {
        return this.getDefaultTransient().getPurchasedQuantity();
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.getDefaultTransient().setPurchasedQuantity(purchasedQuantity);
    }

    public int getPurchasedQuantityAll() {
        return this.getDefaultTransient().getPurchasedQuantityAll();
    }

    public void setPurchasedQuantityAll(int purchasedQuantityAll) {
        this.getDefaultTransient().setPurchasedQuantityAll(purchasedQuantityAll);
    }

	/**
     * 获取商品简介
     * 
     * @return
     */
    public ProductDescription getProductIntroduce() {
        return this.product.getDescriptionByMeta(Long.valueOf(MagicNumber.TEN + ""));

    }


    @Override
    public String toString() {
        return "ProductSale [getId()=" + getId() + ", getAvalibleQuantity()=" + getAvalibleQuantity() + ", getName()="
                + getName() + ", getSaleStatus()=" + getSaleStatus() + "]";
    }

    /**
     * api图片信息
     * 
     * @return
     */
    public String getDefaultImageUrl() {
        if (this.getImageUrl() == null) {
            this.setImageUrl(this.getProduct().getImageUrlFor160px());
        }
        return this.getImageUrl();
    }

    public void setDefaultImageUrl(int imageType) {
        Set<ProductImage> imageList = this.getProduct().getImageList();
        boolean isHit = false;// 是否命中
        for (ProductImage productImage : imageList) {
            if ((int) productImage.getType() == imageType) {
                this.setImageUrl(productImage.getUrl());
                isHit = true;
                break;
            }
        }
        if (!isHit) {
            this.setImageUrl(this.getProduct().getImageUrlFor160px());
        }
    }

    public Set<ProductSaleStock> getProductSaleStocks() {
        return productSaleStocks;
    }

    public void setProductSaleStocks(Set<ProductSaleStock> productSaleStocks) {
        this.productSaleStocks = productSaleStocks;
    }

    /**
     * 获取电子书 本身就是电子书,直接返回
     * 
     * @return
     */
    public ProductSale getEBook() {
        if (Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(this.getStorageType().getId())) {
            return this;
        }
        Set<ProductSale> set = this.getProduct().getSaleList();
        for (ProductSale ps : set) {
            if (Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(ps.getStorageType().getId())) {
                return ps;
            }
        }
        return null;
	}
	
	/**
	 * 用于对页面渲染,外部使用
	 * @return
	 */
	public String getEffectiveName() {
		if (StringUtils.isBlank(this.sellName)) {
			return product.getName();
		}
		return this.sellName;
	}
	
	
	/**
	 * 获取主编推荐信息
	 * @return
	 */
	public ProductDescription getProductEditorRecommendInfo(){
		ProductDescription tmpProductDescription = this.product.getDescriptionByMeta(ProductMeta.META_EDITOR_RECOMMENDATION);
		if(tmpProductDescription != null){
			String descriptionContent = tmpProductDescription.getContent().replaceAll("<br/>", "").replaceAll("<br />", "")
				.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;"," ").replaceAll("&nbsp;&nbsp;"," ").replaceAll("&nbsp;","");
			tmpProductDescription.setContent(descriptionContent);
		}		
		return tmpProductDescription;
	}

    /**
     * 获取纸质书 本身就是纸质书,直接返回
     * 
     * @return
     */
    public ProductSale getPaperBook() {
        if (!Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(this.getStorageType().getId())) {
            return this;
        }
        Set<ProductSale> set = this.getProduct().getSaleList();
        for (ProductSale ps : set) {
            if (!Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(ps.getStorageType().getId())) {
                return ps;
            }
        }
        return null;
    }

    /**
     * 获取商品平均评分
     */
    @Deprecated
    public BigDecimal getAverageRank() {
        if (rankList == null || rankList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        int count = rankList.size();
        return (new BigDecimal(getRankCountByOneStar()).multiply(new BigDecimal(ProductSaleRank.STAR_ONE)).add(
                new BigDecimal(getRankCountByTwoStar()).multiply(new BigDecimal(ProductSaleRank.STAR_TWO)))
                .add(new BigDecimal(getRankCountByThreeStar()).multiply(new BigDecimal(ProductSaleRank.STAR_THREE))
                        .add(new BigDecimal(getRankCountByFourStar()).multiply(
                                new BigDecimal(ProductSaleRank.STAR_FOUR)).add(
                                new BigDecimal(getRankCountByFiveStar()).multiply(new BigDecimal(
                                        ProductSaleRank.STAR_FIVE)))))).divide(new BigDecimal(count), 1,
                RoundingMode.HALF_UP);
    }

    /**
     * 获取电子书页数
     * 
     * @return
     */
    public ProductExtend getEbookPageCount() {
        return this.getProduct().getProductExtend(ProductMeta.META_EBOOK_PAGE_COUNT);
    }

    /**
     * 获取电子书文件大小
     * 
     * @return
     */
    public ProductExtend getEbookFileSize() {
        return this.getProduct().getProductExtend(ProductMeta.META_EBOOK_PAGE_COUNT);
    }

    /**
     * 获取九月电子书对应关系
     * 
     * @return
     */
    public ProductEbookMapping getProductSeptember() {
        Set<ProductEbookMapping> productSeptember = this.getSeptemberList();
        if (CollectionUtils.isEmpty(productSeptember)) {
            return null;
        }

        if (productSeptember.size() > 1) {
            return null;
        }
        return productSeptember.iterator().next();
    }

    public void addStock(ProductSaleStock productSaleStock) {
        if (CollectionUtils.isEmpty(this.productSaleStocks)) {
            this.productSaleStocks = new HashSet<ProductSaleStock>();
        }
        this.productSaleStocks.add(productSaleStock);
    }

    public void removeStock(ProductSaleStock productSaleStock) {
        if (CollectionUtils.isEmpty(this.productSaleStocks)) {
            return;
        }
        this.productSaleStocks.remove(productSaleStock);
        productSaleStock.setProductSale(null);
    }

    /**
     * 该商品在指定dc下的库存信息
     * 
     * @param code
     *            DC编码
     * @return
     */
    public ProductSaleStock getStockByDc(Code dc) {
        if (CollectionUtils.isNotEmpty(this.productSaleStocks)) {
            for (ProductSaleStock productSaleStock : this.productSaleStocks) {
                if (productSaleStock.getDc().getId().equals(dc.getId())) {
                    return productSaleStock;
                }
            }
        }
        return null;
    }

    /**
     * 统计用的区别区别纸书还是电子书
     * 
     * @return
     */
    public String getBookStorageType() {
        if (this.getProduct().getSort().getId().equals(Code.PRODUCT_SORT_BOOK)) {
            if (this.getStorageType().getId().equals(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK)) {
                return E_BOOK;
            } else {
                return PRINTED_BOOK;
            }
        } else {
            return NOT_BOOK;
        }
    }
    
    /**
     * 判断商品是否为套装书 2014-3-4
     * @return
     */
    public boolean isComplex() {
		Set<ProductSale> complexItems = this.getComplexItemList();
		return this.getProduct().isComplex() && complexItems != null
				&& !complexItems.isEmpty();
	}
    
}