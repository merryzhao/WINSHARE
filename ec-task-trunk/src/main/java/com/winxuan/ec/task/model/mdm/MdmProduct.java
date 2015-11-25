package com.winxuan.ec.task.model.mdm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 主数据商品实体
 * @ MdmProduct.java 
 * HideHai
 */
public class MdmProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 394985559649047418L;
	
	/**
	 * 商品id
	 */
	private Integer id ;
	/**
	 * 主数据商品ID
	 */
	private Integer merchId;
	/**
	 * SAP自编码
	 */
	private String sapCode;
	/**
	 * 商品类型
	 */
	private String merchType;
	/**
	 * 经营分类
	 */
	private String workcategory;
	/**
	 * 管理分类
	 */
	private String managecategory;
	/**
	 * MC分类
	 */
	private String mccategory;
	/**
	 * EC销售分类
	 */
	private Integer category;
	/**
	 * 商品名称
	 */
	private String bookName;
	/**
	 * 条形码
	 */
	private String isbn;
	/**
	 * 制造商
	 */
	private String publisher;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 商品定价
	 */
	private BigDecimal price;
	/**
	 * 供应商编号
	 */
	private String normalVendorId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 折扣
	 */
	private BigDecimal discount;
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	/**
	 * 开本
	 */
	private String sizeFormat;
	/**
	 * 页数
	 */
	private Integer pageNum;
	/**
	 * 字数
	 */
	private Integer wordNumber;
	/**
	 * 装帧
	 */
	private String bindingFormat;
	/**
	 * 本版版次
	 */
	private String thisEdition;
	/**
	 * 印次
	 */
	private String printingTimes;
	/**
	 * 本版年月
	 */
	private String thisEditionYearMonth;
	/**
	 * 本次印刷年月
	 */
	private String thisPrintingYearMonth;
	/**
	 * 碟片数
	 */
    private Integer discQuantity;
    /**
     * 片长
     */
    private String playtime;
    /**
     * 语种
     */
    private String language;
    /**
     * 主演
     */
    private String starring;
	/**
	 * 译者
	 */
	private String translator;
	/**
	 * 副标题
	 */
	private String subHeading;
	/**
	 * 丛书名
	 */
	private String seriesname;
	/**
	 * 停用标识
	 */
	private String unusable;
	/**
	 * 商品目录
	 */
	private String catalog;
	/**
	 * 编辑推荐
	 */
	private String editorRecommend;
	/**
	 * 内容简介
	 */
	private String contentsAbstract;
	/**
	 * 作者介绍
	 */
	private String authorIntroduction;
	/**
	 * 书摘
	 */
	private String delicacyContents;
	
	public String getSapCode() {
		return sapCode;
	}
	public Integer getMerchId() {
		return merchId;
	}
	public void setMerchId(Integer merchId) {
		this.merchId = merchId;
	}
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}
	public String getMerchType() {
		return merchType;
	}
	public void setMerchType(String merchType) {
		this.merchType = merchType;
	}
	public String getWorkcategory() {
		return workcategory;
	}
	public void setWorkcategory(String workcategory) {
		this.workcategory = workcategory;
	}
	public String getManagecategory() {
		return managecategory;
	}
	public void setManagecategory(String managecategory) {
		this.managecategory = managecategory;
	}
	public String getMccategory() {
		return mccategory;
	}
	public void setMccategory(String mccategory) {
		this.mccategory = mccategory;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getNormalVendorId() {
		return normalVendorId;
	}
	public void setNormalVendorId(String normalVendorId) {
		this.normalVendorId = normalVendorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getSizeFormat() {
		return sizeFormat;
	}
	public void setSizeFormat(String sizeFormat) {
		this.sizeFormat = sizeFormat;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getWordNumber() {
		return wordNumber;
	}
	public void setWordNumber(Integer wordNumber) {
		this.wordNumber = wordNumber;
	}
	public String getBindingFormat() {
		return bindingFormat;
	}
	public void setBindingFormat(String bindingFormat) {
		this.bindingFormat = bindingFormat;
	}
	public String getThisEdition() {
		return thisEdition;
	}
	public void setThisEdition(String thisEdition) {
		this.thisEdition = thisEdition;
	}
	public String getPrintingTimes() {
		return printingTimes;
	}
	public void setPrintingTimes(String printingTimes) {
		this.printingTimes = printingTimes;
	}
	public String getThisEditionYearMonth() {
		return thisEditionYearMonth;
	}
	public void setThisEditionYearMonth(String thisEditionYearMonth) {
		this.thisEditionYearMonth = thisEditionYearMonth;
	}
	public String getThisPrintingYearMonth() {
		return thisPrintingYearMonth;
	}
	public void setThisPrintingYearMonth(String thisPrintingYearMonth) {
		this.thisPrintingYearMonth = thisPrintingYearMonth;
	}
	public String getTranslator() {
		return translator;
	}
	public void setTranslator(String translator) {
		this.translator = translator;
	}
	public String getSubHeading() {
		return subHeading;
	}
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}
	public String getSeriesname() {
		return seriesname;
	}
	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}
	public String getUnusable() {
		return unusable;
	}
	public void setUnusable(String unusable) {
		this.unusable = unusable;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getEditorRecommend() {
		return editorRecommend;
	}
	public void setEditorRecommend(String editorRecommend) {
		this.editorRecommend = editorRecommend;
	}
	public String getContentsAbstract() {
		return contentsAbstract;
	}
	public void setContentsAbstract(String contentsAbstract) {
		this.contentsAbstract = contentsAbstract;
	}
	public String getAuthorIntroduction() {
		return authorIntroduction;
	}
	public void setAuthorIntroduction(String authorIntroduction) {
		this.authorIntroduction = authorIntroduction;
	}
	public String getDelicacyContents() {
		return delicacyContents;
	}
	public void setDelicacyContents(String delicacyContents) {
		this.delicacyContents = delicacyContents;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	

	public Integer getDiscQuantity() {
		return discQuantity;
	}
	public void setDiscQuantity(Integer discQuantity) {
		this.discQuantity = discQuantity;
	}
	public String getPlaytime() {
		return playtime;
	}
	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStarring() {
		return starring;
	}
	public void setStarring(String starring) {
		this.starring = starring;
	}
	
}
