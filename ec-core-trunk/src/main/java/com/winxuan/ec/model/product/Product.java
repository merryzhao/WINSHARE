package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.tag.TagItem;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.AuthorStringTokenizer;

/**
 * 商品基础信息
 * 
 * @author liuan
 * @version 1.0,2011-7-5
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 5906870990150566255L;
    private static final Logger LOG = LoggerFactory.getLogger(Product.class);
    private static final short PHYSICAL=2;//实物套装
    private static final short VIRTUAL=1;//虚拟套装
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String barcode;

    @Column(name = "merchid")
    private Long merchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sort")
    private Code sort;

    @Column
    private String manufacturer;

    @Column(name = "productiondate")
    private Date productionDate;

    @Column
    private String author;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "category")
    // private Category category;

    @Column(name = "listprice")
    private BigDecimal listPrice;

    @Column(name = "managecategory")
    private String manageCategory;

    @Column(name = "workcategory")
    private String workCategory;

    @Column(name = "mccategory")
    private String mcCategory;

    @Column
    private String vendor;

    @Column(name = "islock")
    private boolean lock;

    @Column(name = "createtime")
    private Date createTime;

    @Column(name = "updatetime")
    private Date updateTime;

    @Column
    private short complex;

    @Column
    private boolean virtual;

    @Column(name = "hasimage")
    private boolean hasImage;

    @Column
    private boolean status;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @Access(value = AccessType.FIELD)
    private ProductCategoryStatus categoryStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", targetEntity = ProductSale.class)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductSale> saleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", targetEntity = ProductVersionItem.class)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductVersionItem> versionItemList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", targetEntity = ProductDescription.class)
    @OrderBy("index")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductDescription> descriptionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", targetEntity = ProductImage.class)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductImage> imageList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", targetEntity = ProductExtend.class)
    @OrderBy("index")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductExtend> extendList;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TagItem.class)
    @JoinColumn(name = "id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TagItem> tagItemList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Category.class)
    @JoinTable(name = "product_category", joinColumns = {@JoinColumn(name = "product")}, inverseJoinColumns = {@JoinColumn(name = "category")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 商品与分类多对多关系,默认返回.第1个分类
     * 
     * @return
     */
    @Deprecated
    public Category getCategory() {
        return this.getCategories().isEmpty() ? null : this.getCategories().get(0);
    }
    
    public Long getSecondLevelCategoryId(){
    	Category category = getCategory();
    	boolean flag = true;
    	while(flag){
    		if(category.getParent() != null){
    			if((category.getParent().getId() == 1L || category.getParent().getId() == 7786L || category.getParent().getId() == 7787L)){
    				return category.getId();
    			}else{
    				category = category.getParent();
    			}
    		}else{
    			flag = false;
    		}
    	}
    	return null;
    }

    /**
     * 商品与分类多对多关系,默认设置.第1个分类
     * 
     * @param category
     */
    @Deprecated
    public void setCategory(Category category) {
        if (this.getCategories() == null) {
            this.setCategories(new ArrayList<Category>());
        }

        if (this.getCategories().isEmpty()) {
            this.getCategories().add(category);
        } else {
            this.getCategories().set(0, category);
        }

    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getWorkCategory() {
        return workCategory;
    }

    public void setWorkCategory(String workCategory) {
        this.workCategory = workCategory;
    }

    public String getManageCategory() {
        return manageCategory;
    }

    public void setManageCategory(String manageCategory) {
        this.manageCategory = manageCategory;
    }

    public String getMcCategory() {
        return mcCategory;
    }

    public void setMcCategory(String mcCategory) {
        this.mcCategory = mcCategory;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<ProductSale> getSaleList() {
        return saleList;
    }

    public void setSaleList(Set<ProductSale> saleList) {
        this.saleList = saleList;
    }

    public Set<ProductDescription> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(Set<ProductDescription> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public Set<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(Set<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public Set<ProductExtend> getExtendList() {
        return extendList;
    }

    public void setExtendList(Set<ProductExtend> extendList) {
        this.extendList = extendList;
    }

    public Set<TagItem> getTagItemList() {
        return tagItemList;
    }

    public void setTagItemList(Set<TagItem> tagItemList) {
        this.tagItemList = tagItemList;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public Set<ProductVersionItem> getVersionItemList() {
        return versionItemList;
    }

    public void setVersionItemList(Set<ProductVersionItem> versionItemList) {
        this.versionItemList = versionItemList;
    }

    public Long getMerchId() {
        return merchId;
    }

    public void setMerchId(Long merchId) {
        this.merchId = merchId;
    }

    public Code getSort() {
        return sort;
    }

    public void setSort(Code sort) {
        this.sort = sort;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ProductCategoryStatus getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(ProductCategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public String getUrl() {
    	ProductSale productSale = this.getDefaultPaperProductSale();
    	if(productSale == null){
    		productSale = this.getDefaultEbookProductSale();
    	}
        return productSale.getUrl();
    }

    /**
     * 原图
     * 
     * @return
     */
    public String getImageUrlForOriginal() {
        return getImageUrlByType(ProductImage.TYPE_ORIGINAL);
    }

    /**
     * 小图
     * 
     * @return
     */
    @Deprecated
    public String getSmallImageUrl() {
        List<ProductImage> smallImageList = getImageListByType(ProductImage.TYPE_SMALL);
        if (smallImageList == null || smallImageList.isEmpty()) {
            return ProductImage.DEFAULT_IMAGE_URL;
        }
        return smallImageList.get(0).getUrl();
    }

    /**
     * 中图
     * 
     * @return
     */
    @Deprecated
    public String getMediumImageUrl() {
        List<ProductImage> mediumImageList = getImageListByType(ProductImage.TYPE_MEDIUM);
        if (mediumImageList == null || mediumImageList.isEmpty()) {
            return ProductImage.DEFAULT_IMAGE_URL;
        }
        return mediumImageList.get(0).getUrl();
    }

    /**
     * 大图
     * 
     * @return
     */
    @Deprecated
    public String getLargeImageUrl() {
        List<ProductImage> largeImageList = getImageListByType(ProductImage.TYPE_LARGE);
        if (largeImageList == null || largeImageList.isEmpty()) {
            return null;
        }
        return largeImageList.get(0).getUrl();
    }

    /**
     * 55*55的图片
     * 
     * @return
     */
    public String getImageUrlFor55px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_55);
    }

    /**
     * 110*110的图片
     * 
     * @return
     */
    public String getImageUrlFor110px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_110);
    }

    /**
     * 130*130的图片
     * 
     * @return
     */
    public String getImageUrlFor130px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_130);
    }

    /**
     * 160*160的图片
     * 
     * @return
     */
    public String getImageUrlFor160px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_160);
    }

    /**
     * 200*200的图片
     * 
     * @return
     */
    public String getImageUrlFor200px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_200);
    }

    /**
     * 350*350的图片
     * 
     * @return
     */
    public String getImageUrlFor350px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_350);
    }
    /**
     * 600*600的图片
     * 
     * @return
     */
    public String getImageUrlFor600px() {
        return getImageUrlByType(ProductImage.TYPE_SIZE_600);
    }

    public List<ProductImage> getImageListByType(short type) {
        if (imageList == null || imageList.isEmpty()) {
            return null;
        }
        List<ProductImage> result = null;
        for (ProductImage image : imageList) {
            if (image.getType() == type) {
                if (result == null) {
                    result = new ArrayList<ProductImage>();
                }
                result.add(image);
            }
        }
        return result;
    }

    public String getImageUrlByType(short type) {
        ProductImage img = getImageByType(type);
        if (img == null) {
            return ProductImage.DEFAULT_IMAGE_URL;
        }
        return img.getUrl();
    }

    /**
     * 根据类型取到对应的图片
     * 
     * @param type
     * @return
     */
    public ProductImage getImageByType(short type) {
        if (imageList == null || imageList.isEmpty()) {
            return null;
        }
        for (ProductImage image : imageList) {
            if (image.getType() == type) {
                return image;
            }
        }
        return null;
    }

    /**
     * 添加一条详情
     * 
     * @param description
     */
    public void addDescription(ProductDescription description) {
        if (descriptionList == null) {
            descriptionList = new HashSet<ProductDescription>();
        }
        descriptionList.add(description);
    }

    /**
     * 添加一条扩展信息
     * 
     * @param extend
     */
    public void addExtend(ProductExtend extend) {
        if (extendList == null) {
            extendList = new HashSet<ProductExtend>();
        }
        extendList.add(extend);
    }

    public ProductDescription getDescriptionByMeta(Long meta) {
        if (descriptionList == null || descriptionList.isEmpty()) {
            return null;
        }
        ProductDescription productDescription = null;
        for (ProductDescription productDescription2 : descriptionList) {
            if (productDescription2.getProductMeta().getId().compareTo(meta) == 0) {
                productDescription = productDescription2;
            }
        }
        return productDescription;
    }

    public ProductSale getDefaultProductSale() {
        for (Iterator<ProductSale> iterator = saleList.iterator(); iterator.hasNext();) {
            ProductSale productSale = iterator.next();
            return productSale;
        }
        return null;
    }
    
    /**
     * 获取默认的纸质书
     * @return
     */
    public ProductSale getDefaultPaperProductSale() {
        for (Iterator<ProductSale> iterator = saleList.iterator(); iterator.hasNext();) {
            ProductSale productSale = iterator.next();
            if(!Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(productSale.getStorageType().getId())){
            	  return productSale;
            }
        }
        return null;
    }
    
    /**
     * 获取默认的电子书
     * @return
     */
    public ProductSale getDefaultEbookProductSale() {
        for (Iterator<ProductSale> iterator = saleList.iterator(); iterator.hasNext();) {
            ProductSale productSale = iterator.next();
            if(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK.equals(productSale.getStorageType().getId())){
            	  return productSale;
            }
        }
        return null;
    }

    public String[] getAuthors() {
        return StringUtils.isBlank(author) ? null : AuthorStringTokenizer.getAuthors(author);
    }

    public String getAuthorUrl() {
        if (isAuthorEmpty()) {
            return author;
        }
         return AuthorStringTokenizer.getAuthorWithUrl(author, createTime);
    }
    
    /**
     * 获取著作者、译者、主编、其他著作者、编者信息
     * 1.最多只显示两类信息
     * 2.字段优先显示的优先级，从左到右依次降低
     * @return
     */
    public List<Map<String, String>> getFinalAuthorInfo(){
    	List<Map<String, String>> finalAuthorInfoSet = new ArrayList<Map<String, String>>(0);
    	
    	if(!isAuthorEmpty()){
    		finalAuthorInfoSet.add(getRightFieldInfo("作 者", getAuthorInfo(), "著".charAt(0)));
    	}
    		
    	if(!isBookTranslatorInfoEmpty()){
    		finalAuthorInfoSet.add(getRightFieldInfo("译 者", getBookTranslatorInfo(), "译".charAt(0)));
    		if(finalAuthorInfoSet.size() == 2){
    			return finalAuthorInfoSet; 
    		}
    	}
    	
    	if(!isBookChiefEditorInfoEmpty()){
    		finalAuthorInfoSet.add(getRightFieldInfo("主 编", getBookChiefEditorInfoEmpty(), "编".charAt(0)));
    		if(finalAuthorInfoSet.size() == 2){
    			return finalAuthorInfoSet; 
    		}
    	}
    	
    	if(!isBookOtherEditorInfoEmpty()){
    		finalAuthorInfoSet.add(getRightFieldInfo("其他著作者", getBookOtherEditorInfo(), "著".charAt(0)));
    		if(finalAuthorInfoSet.size() == 2){
    			return finalAuthorInfoSet;
    		}
    	}
    		
    	if(!isBookEditorInfoEmpty()){
    		finalAuthorInfoSet.add(getRightFieldInfo("编  者", getBookEditorInfo(), "编".charAt(0)));
    	}
    	
    	return finalAuthorInfoSet;
    }

	
	
    /**
     * 
     * @param a
     *            整个a标签<a href="" target="_blank"></a>
     * @return
     */
    public String getAuthorsATag(final String a) {
        String returnAuthor = this.author;
        String[] authors = getAuthors();
        if (authors == null) {
            return a;
        }
        for (String author : authors) {
            String aReplace;
            try {
                aReplace = a.replaceFirst("href=\"\"",
                                          "href=\"http://search.winxuan.com/search?author="
                                                  + URLEncoder.encode(author, "UTF-8")
                                                  + "\"").replaceFirst("></a>",
                                                                       ">" + author + "</a>");
                returnAuthor = returnAuthor.replaceAll(author, aReplace);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return returnAuthor;
    }

    public String getDefaultAuthorsATag() {
        final String a = "<a href=\"\" target=\"_blank\"></a>";
        return getAuthorsATag(a);
    }

    public boolean isComplex() {
    	return (complex == VIRTUAL || complex==PHYSICAL);
    }
    public boolean isPhysicalComplex(){
    	return complex==PHYSICAL;
    }

	public short getComplex() {
		return complex;
	}

	public void setComplex(Short complex) {
		this.complex = complex;
	}

	public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getProductName() {
        return name.replaceFirst("ZD", "")
                   .replaceFirst("ZY", "")
                   .replaceFirst("ZP", "")
                   .replaceFirst("DL", "")
                   .replaceFirst("zd", "")
                   .replaceFirst("zy", "")
                   .replaceFirst("zp", "")
                   .replaceFirst("dl", "");
    }

    /**
     * 删除扩展属性
     * 
     * @param extend
     */
    public void removeProductExtend(ProductExtend extend) {
        if (!CollectionUtils.isEmpty(extendList)) {
            extendList.remove(extend);
        }
    }

    /**
     * 删除详情描述
     * 
     * @param description
     */
    public void removeProductDescription(ProductDescription description) {
        if (!CollectionUtils.isEmpty(descriptionList)) {
            descriptionList.remove(description);
        }
    }

    /**
     * 根据ProductMeta获取扩展属性
     * 
     * @param extendName
     * @return
     */
    public ProductExtend getProductExtend(ProductMeta meta) {
        if (!CollectionUtils.isEmpty(extendList) && meta != null && meta.getId() != null) {
            for (ProductExtend pe : extendList) {
                if (pe.getProductMeta().getId().equals(meta.getId())) {
                    return pe;
                }
            }
        }
        return null;
    }
    
    
    /**
     * 根据ProductMeta Id获取扩展属性
     * 
     * @param metaId
     * @return
     */
    public ProductExtend getProductExtend(Long id){
    	 if (!CollectionUtils.isEmpty(extendList)) {
             for (ProductExtend pe : extendList) {
                 if (id.equals(pe.getProductMeta().getId())) {
                     return pe;
                 }
             }
         }
    	 return null;
    }
    

    /**
     * 根据ProductMeta获取描述
     * 
     * @param meta
     * @return
     */
    public ProductDescription getProductDescription(ProductMeta meta) {
        if (!CollectionUtils.isEmpty(descriptionList) && meta != null && meta.getId() != null) {
            for (ProductDescription pd : descriptionList) {
                if (pd.getProductMeta().getId().equals(meta.getId())) {
                    return pd;
                }
            }
        }
        return null;
    }

    public String getCateogryString() {
        List<Category> list = getCategories();
        if (list != null) {
            StringBuffer buffer = new StringBuffer();
            for (Category category : list) {
                buffer.append(category.getName());
                buffer.append("\r\n");
            }
            return buffer.toString();
        }
        return String.valueOf("没有分类");
    }

    /**
     * 得到商品品牌字段
     * 
     * @returnw
     */
    public String getProductMeteOfBrand() {
        Set<ProductExtend> extendList = getExtendList();
        if (extendList != null) {
            for (ProductExtend extend : extendList) {
                if (extend.getProductMeta()
                          .getId()
                          .equals(Long.valueOf(MagicNumber.FIVE * MagicNumber.NINE))) {
                    return extend.getValue();
                }
            }
        }
        return String.valueOf("");
    }
    
    /**
     * 获取作者的字段信息
     * @return
     */
    private String getAuthorInfo(){
    	String authorInfo = AuthorStringTokenizer.getAuthorWithUrl(author, createTime);
    	
    	StringBuilder sb = new StringBuilder("");
    	
    	if(!isAuthorCountryInfoEmpty()){
    		sb.append("[" + getExpectFieldFromProductExtends(ProductMeta.META_BOOK_COUNTRY) + "]");
    	}
    	
    	if(!isBookDynastyInfoEmpty()){
    		sb.append("[" + getExpectFieldFromProductExtends(ProductMeta.META_BOOK_DYNASTY) + "]");
    	}
    	
    	return sb.append(authorInfo).toString(); 
    }

    /**
     * 获取译者的字段信息
     */
    private String getBookTranslatorInfo(){ 
    	String translatorInfo = getExpectFieldFromProductExtends(ProductMeta.META_BOOK_TRANSLATOR);
   		return AuthorStringTokenizer.getAuthorWithUrl(translatorInfo, createTime);
    }
    
    /**
     * 获取主编的字段信息
     */
    private String getBookChiefEditorInfoEmpty(){
    	String chiefEditorInfo = getExpectFieldFromProductExtends(ProductMeta.META_BOOK_CHIEF_EDITOR);
    	return AuthorStringTokenizer.getAuthorWithUrl(chiefEditorInfo, createTime);
    }
    
    /**
     * 获取其他编辑的字段信息
     */
    private String getBookOtherEditorInfo(){
    	String otherEditorInfo = getExpectFieldFromProductExtends(ProductMeta.META_BOOK_OTHER_EDITOR);
    	return AuthorStringTokenizer.getAuthorWithUrl(otherEditorInfo, createTime);
    }
    
    /**
     * 获取编者的字段信息
     */
    private String getBookEditorInfo(){
    	String editorInfo = getExpectFieldFromProductExtends(ProductMeta.META_BOOK_EDITOR);
    	return AuthorStringTokenizer.getAuthorWithUrl(editorInfo, createTime);
    }
    
    private Map<String, String> getRightFieldInfo(String key, String value, char valueTail) {
		Map<String, String> tmpMap = new HashMap();
		tmpMap.put(key, isContainSpecailChar(value, valueTail) ? value : value + " " + valueTail);
		return tmpMap;
	}
    
    private boolean isContainSpecailChar(String str, char specailChar){
    	if(str.length() == 0){
    		return false;
    	}
    	if(str.charAt(str.length() - 1) == specailChar){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 判断作者字段是否为空
     * @return
     */
    private boolean isAuthorEmpty(){
    	if("无".equals(author)){
    		return true;
    	}
    	return StringUtils.isBlank(author) ? true : false;
    }

	private boolean isProductExtendsContainExpectField(int expectFieldMetaId) {
		for(ProductExtend extend : extendList){
    		if(extend.getProductMeta().getId() == expectFieldMetaId 
    				&& !"无".equals(extend.getValue()) 
    				&& !"NULL".equals(extend.getValue())
    				&& !extend.getValue().trim().isEmpty()){
    			return true;
    		}
    	}
		return false;
	}
    
	private String getExpectFieldFromProductExtends(int expectFieldMetaId){
		for(ProductExtend extend : extendList){
			if(extend.getProductMeta().getId() == expectFieldMetaId){
    			return extend.getValue();
    		}
		}
		return "";
	}
	
	/**
	 * 判断国籍是否为空
	 * @return
	 */
	private boolean isAuthorCountryInfoEmpty(){
    	return isProductExtendsContainExpectField(ProductMeta.META_BOOK_COUNTRY) ? false : true;
    }
	
	/**
	 * 判断朝代是否为空
	 * @return
	 */
	private boolean isBookDynastyInfoEmpty(){
		return isProductExtendsContainExpectField(ProductMeta.META_BOOK_DYNASTY) ? false : true;
	}
    
	/**
	 * 判断译者是否为空
	 * @return
	 */
	private boolean isBookTranslatorInfoEmpty(){
		return isProductExtendsContainExpectField(ProductMeta.META_BOOK_TRANSLATOR) ? false : true;
	}
	
	/**
	 * 判断主编是否为空
	 * @return
	 */
	private boolean isBookChiefEditorInfoEmpty(){
		return isProductExtendsContainExpectField(ProductMeta.META_BOOK_CHIEF_EDITOR) ? false : true;
	}
    
	/**
	 * 判断其他编者是否为空
	 * @return
	 */
	private boolean isBookOtherEditorInfoEmpty(){
		return isProductExtendsContainExpectField(ProductMeta.META_BOOK_OTHER_EDITOR) ? false : true;
	}
	
	/**
	 * 判断编者是否为空
	 * @return
	 */
	private boolean isBookEditorInfoEmpty(){
		return isProductExtendsContainExpectField(ProductMeta.META_BOOK_EDITOR) ? false : true;
	}
	
	/**
	 * 判断商品类型是否为自有套装
	 * @return
	 */
	public boolean getProductExtendMeta143Value(){
		if(!CollectionUtils.isEmpty(extendList)){
			for(ProductExtend productExtend : extendList){
				if(Long.valueOf(143).equals(productExtend.getProductMeta().getId()) && "1".equals(productExtend.getValue())){
					return true;
				}
			}
		}
		return false;
	}
}
