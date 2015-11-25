package com.winxuan.ec.task.model.ebook;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  电子书
 * @author luosh
 *
 */
@Entity
@Table(name = "BOOK")
public class Book implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 图书ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOK_ID")
	private Long id;// BOOK_ID
	/**
	 * 书名
	 */
	@Column(name = "BOOK_NAME")
	private String bookName;// BOOK_NAME
	/**
	 * 作者
	 */
	@Column(name = "AUTHOR")
	private String author;// AUTHOR
	/**
	 * 出版社ID
	 */
	@Column(name = "PUBLISHER_ID")
	private Long publisherID;// PUBLISHER_ID
	/**
	 * 出版社名称
	 */
	@Column(name = "PUBLISHER_NAME")
	private String publisherName;// PUBLISHER_NAME
	/**
	 * 出版日期
	 */
	@Column(name = "PUBLISH_DATE")
	private Date publishDate;// PUBLISH_DATE
	/**
	 * 版次
	 */
	@Column(name = "PUBLISH_VERSION")
	private String publishVersion;// PUBLISH_VERSION
	/**
	 * 印次
	 */
	@Column(name = "PRINTED_COUNT")
	private Integer printedCount;// PRINTED_COUNT
	/**
	 * 印刷时间
	 */
	@Column(name = "PRINTED_DATE")
	private Date printedDate;// PRINTED_DATE
	/**
	 * ISBN
	 */
	@Column(name = "ISBN")
	private String isbn;//
	/**
	 * 字数
	 */
	@Column(name = "WORD_COUNT")
	private String wordCount;// WORD_COUNT
	/**
	 * 实际页数
	 */
	@Column(name = "FACT_PAGE_COUNT")
	private Integer factPageCount;// FACT_PAGE_COUNT
	/**
	 * 电子书页数
	 */
	private Integer pageCount;// PAGE_COUNT
	/**
	 * 章节数量
	 */
	@Column(name = "CHAPTER_COUNT")
	private Integer chapterCount;// CHAPTER_COUNT
	/**
	 * 印刷数量
	 */
	@Column(name = "PRINTED_QUANTITY")
	private Integer printedQuantity;// PRINTED_QUANTITY
	/**
	 * 开本
	 */
	@Column(name = "FOLIO")
	private String folio;// FOLIO
	/**
	 * 纸张
	 */
	@Column(name = "PAPER_MATERIAL")
	private String paperMaterial;// PAPER_MATERIAL
	/**
	 * 包装
	 */
	@Column(name = "PACK")
	private String pack;// PACK
	/**
	 * 简介
	 */
	@Column(name = "INTRODUCTION")
	private String introduction;// INTRODUCTION
	/**
	 * 作者简介
	 */
	@Column(name = "AUTHOR_INTRODUCTION")
	private String authorIntroduction;// AUTHOR_INTRODUCTION
	/**
	 * 编辑评论
	 */
	@Column(name = "EDITOR_COMMENT")
	private String editorComment;// EDITOR_COMMENT
	/**
	 * 目录
	 */
	@Column(name = "TABLE_OF_CONTENTS")
	private String tableOfContents;// TABLE_OF_CONTENTS
	/**
	 * 标签
	 */
	@Column(name = "TAGS")
	private String tags;// TAGS
	/**
	 * 纸质书价格
	 */
	@Column(name = "PAPER_PRICE")
	private BigDecimal paperPrice;// PAPER_PRICE
	/**
	 * 申请与审核,1 未提交申请,2 已提交申请,3 审核未通过,4 审核通过
	 */
	@Column(name = "FLAG")
	private Integer flag;// FLAG
	/**
	 * 未通过审核原因
	 */
	@Column(name = "UNPASS_REASON")
	private String unpassReason;// UNPASS_REASON
	/**
	 * 转换文件
	 */
	@Column(name = "CONVERT_FILE")
	private String convertFile;// CONVERT_FILE
	/**
	 * 试读页范围
	 */
	@Column(name = "PREVIEW_PAGE_RANGE")
	private String previewPageRange;// PREVIEW_PAGE_RANGE
	/**
	 * 是否可以包月
	 */
	@Column(name = "IS_CAN_PAY_MONTHLY")
	private Integer isCanPayMonthly;// IS_CAN_PAY_MONTHLY
	/**
	 * 是否免费 1:免费；0付费
	 */
	@Column(name = "IS_FREE")
	private Integer isFree;// IS_FREE
	/**
	 * 是否受最高限价限制
	 */
	@Column(name = "IS_LIMIT_HIGHEST_PRICE")
	private Integer isLimitHighestPrice;// IS_LIMIT_HIGHEST_PRICE
	/**
	 * 纸质书ID
	 */
	@Column(name = "PAPER_BOOK_ID")
	private String paperBookID;// PAPER_BOOK_ID
	/**
	 * 授权开始日期
	 */
	@Column(name = "LICENCE_START_DATE")
	private Date licenceStartDate;// LICENCE_START_DATE
	/**
	 * 授权结束日期
	 */
	@Column(name = "LICENCE_END_DATE")
	private Date licenceEndDate;// LICENCE_END_DATE
	/**
	 * 是否专有授权
	 */
	@Column(name = "IS_SPECIAL_LICENCE")
	private Integer isSpecialLicence;// IS_SPECIAL_LICENCE
	/**
	 * 预计提交日期
	 */
	@Column(name = "EXPECT_SUBMIT_DATE")
	private Date expectSubmitDate;// EXPECT_SUBMIT_DATE
	/**
	 * 供应商ID
	 */
	@Column(name = "VENDOR_ID")
	private Long vendorId;// VENDOR_ID
	@Column(name = "CREATE_DATETIME")
	private Date createDateTime;// CREATE_DATETIME
	@Column(name = "CREATE_BY")
	private String createBy;// CREATE_BY
	@Column(name = "UPDATE_DATETIME")
	private Date updateDateTime;// UPDATE_DATETIME
	@Column(name = "UPDATE_BY")
	private String updateBy;// UPDATE_BY
	/**
	 * 0未删除,1已删除
	 */
	@Column(name = "DELETE_FLAG")
	private Integer deleteFlag;// DELETE_FLAG
	/**
	 * 主数据MC分类
	 */
	@Column(name = "MCCODE")
	private String mcCode;// MCCODE
	/**
	 * 丛书名
	 */
	@Column(name = "BOOK_SERIES")
	private String bookSeries;// BOOK_SERIES
	/**
	 * 文件大小,单位：字节
	 */
	@Column(name = "FILE_SIZE")
	private Long fileSize;// FILE_SIZE
	/**
	 * 书籍来源,0上传,1单本书部分章节合并,2多本书部分章节合并
	 */
	@Column(name = "BOOK_SOURCE")
	private Integer bookSource;// BOOK_SOURCE
	/**
	 * 针对于来源为单本书部分章节合并记录的BOOK_ID
	 */
	@Column(name = "ORIGINAL_BOOK_ID")
	private Long originalBookID;// ORIGINAL_BOOK_ID
	/**
	 * 针对于来源为上传记录是否有章节合并书
	 */
	@Column(name = "IS_HAS_CHAPTER_BOOK")
	private Integer isHasChapterBook;// IS_HAS_CHAPTER_BOOK

	/**
	 * 商品表ID
	 */
	@Column(name = "PRODUCT_ID")
	private Long productId;

	/**
	 * 顶
	 */
	@Column(name = "UP_COUNT")
	private Long upCount;

	/**
	 * 踩
	 */
	@Column(name = "DOWN_COUNT")
	private Long downCount;

	/**
	 * 评论条数
	 */
	@Column(name = "COMMENT_COUNT")
	private Long commentCount;

	/** 供应商图书ID号 */
	@Column(name = "VENDOR_BOOK_ID", length = 30)
	private String vendorBookId;

	/** 中图法类别,目前方正图书采用这此类别,20111214Y新增 */
	@Column(name = "LIBRARY_CATEGORY_CODE", length = 20)
	private String libraryBookCateogry;

	/**
	 * 是否有epub的书：0：没有；1：有
	 */
	@Column(name = "HAS_EPUB")
	private Integer hasEpub;

	/** 版别代码 */
	@Column(name = "PUBLISHER_CODE")
	private String publisherCode;
	/** 想看次数 */
	@Column(name = "COLLECTION_NUM")
	private Integer collectionNum;
	/** 想看次数调整值 */
	@Column(name = "ADJUST_VALUE")
	private Integer adjustValue;
	/** 评分 */
	@Column(name = "SCORE")
	private Float score;
	/** 图书分类 */
	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	/**
	 * 浏览次数
	 */
	@Column(name = "BROWSE_COUNT")
	private Integer browseCount;
	/**
	 * 评分次数
	 */
	@Column(name = "SCORE_COUNT")
	private Integer scoreCount;
	/**
	 * 分享次数
	 */
	@Column(name = "SHARE_COUNT")
	private Integer shareCount;

	/**
	 * 权重值
	 */
	@Column(name = "HEIGHT_SCORE")
	private Integer heightScore;

	/**
	 * 显示标记:名人,名书,独家,推荐,经典,绝版,推广,新书排名,首发
	 */
	@Column(name = "SHOW_MARK")
	private String showMark;

	/** 封面显示标记 */
	@Column(name = "COVER_SHOW_MARK")
	private String coverShowMark;
	/**
	 * 腰封推荐
	 */
	@Column(name = "GIRDLE_RECOMMEND")
	private String girdleRecommend;

	/**
	 * 阅读对象
	 */
	@Column(name = "READ_OBJECT")
	private String readObject;

	/** 媒体推荐 */
	@Column(name = "MEDIA_RECOMMEND")
	private String mediaRecommend;

	/** 推荐人 */
	@Column(name = "RECOMMENDER")
	private String recommender;

	/**
	 * 人工权重值(1-200)
	 */
	@Column(name = "THIRD_HEIGHT_SCORE")
	private Integer thirdHeightScore;

	/**
	 * 已读次数
	 */
	@Column(name = "HASREAD_COUNT")
	private Integer hasreadCount;
	/** 是否有封面 1为有 0为没有 */
	@Column(name = "HAS_COVER")
	private Integer hasCover;
	@Column(name = "IS_ORIGINAL")
	private Integer isOriginal;
	/** 是否为扫描书 0：非 1：是 2013-5-13 Hakeny add */
	@Column(name = "IS_SCAN")
	private Integer isscan;
	@Transient
	private String picturePath;

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	/**
	 * 图书ID
	 */

	public Long getId() {
		return id;
	}

	/**
	 * 图书ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 书名
	 */

	public String getBookName() {
		return bookName;
	}

	/**
	 * 书名
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * 作者
	 */

	public String getAuthor() {
		return author;
	}

	/**
	 * 作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 出版社ID
	 */

	public Long getPublisherID() {
		return publisherID;
	}

	/**
	 * 出版社ID
	 */
	public void setPublisherID(Long publisherID) {
		this.publisherID = publisherID;
	}

	/**
	 * 出版社名称
	 */

	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * 出版社名称
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	/**
	 * 出版日期
	 */

	public Date getPublishDate() {
		return publishDate;
	}

	/**
	 * 出版日期
	 */
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * 版次
	 */

	public String getPublishVersion() {
		return publishVersion;
	}

	/**
	 * 版次
	 */
	public void setPublishVersion(String publishVersion) {
		this.publishVersion = publishVersion;
	}

	/**
	 * 印次
	 */

	public Integer getPrintedCount() {
		return printedCount;
	}

	/**
	 * 印次
	 */
	public void setPrintedCount(Integer printedCount) {
		this.printedCount = printedCount;
	}

	/**
	 * 印刷时间
	 */

	public Date getPrintedDate() {
		return printedDate;
	}

	/**
	 * 印刷时间
	 */
	public void setPrintedDate(Date printedDate) {
		this.printedDate = printedDate;
	}

	/**
	 * ISBN
	 */

	public String getIsbn() {
		return isbn;
	}

	/**
	 * ISBN
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * 字数
	 */

	public String getWordCount() {
		return wordCount;
	}

	/**
	 * 字数
	 */
	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}

	/**
	 * 实际页数
	 */

	public Integer getFactPageCount() {
		return factPageCount;
	}

	/**
	 * 实际页数
	 */
	public void setFactPageCount(Integer factPageCount) {
		this.factPageCount = factPageCount;
	}

	/**
	 * 电子书页数
	 */
	@Column(name = "PAGE_COUNT")
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * 电子书页数
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 章节数量
	 */

	public Integer getChapterCount() {
		return chapterCount;
	}

	/**
	 * 章节数量
	 */
	public void setChapterCount(Integer chapterCount) {
		this.chapterCount = chapterCount;
	}

	/**
	 * 印刷数量
	 */

	public Integer getPrintedQuantity() {
		return printedQuantity;
	}

	/**
	 * 印刷数量
	 */
	public void setPrintedQuantity(Integer printedQuantity) {
		this.printedQuantity = printedQuantity;
	}

	/**
	 * 开本
	 */

	public String getFolio() {
		return folio;
	}

	/**
	 * 开本
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * 纸张
	 */

	public String getPaperMaterial() {
		return paperMaterial;
	}

	/**
	 * 纸张
	 */
	public void setPaperMaterial(String paperMaterial) {
		this.paperMaterial = paperMaterial;
	}

	/**
	 * 包装
	 */

	public String getPack() {
		return pack;
	}

	/**
	 * 包装
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}

	/**
	 * 简介
	 */

	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 简介
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 作者简介
	 */

	public String getAuthorIntroduction() {
		return authorIntroduction;
	}

	/**
	 * 作者简介
	 */
	public void setAuthorIntroduction(String authorIntroduction) {
		this.authorIntroduction = authorIntroduction;
	}

	/**
	 * 编辑评论
	 */

	public String getEditorComment() {
		return editorComment;
	}

	/**
	 * 编辑评论
	 */
	public void setEditorComment(String editorComment) {
		this.editorComment = editorComment;
	}

	/**
	 * 目录
	 */

	public String getTableOfContents() {
		return tableOfContents;
	}

	/**
	 * 目录
	 */
	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	/**
	 * 标签
	 */

	public String getTags() {
		return tags;
	}

	/**
	 * 标签
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * 纸质书价格
	 */
	public BigDecimal getPaperPrice() {
		return paperPrice;
	}

	/**
	 * 纸质书价格
	 */
	public void setPaperPrice(BigDecimal paperPrice) {
		this.paperPrice = paperPrice;
	}

	/**
	 * 申请与审核,1 未提交申请,2 已提交申请,3 审核未通过,4 审核通过
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * 申请与审核,1 未提交申请,2 已提交申请,3 审核未通过,4 审核通过
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 未通过审核原因
	 */
	public String getUnpassReason() {
		return unpassReason;
	}

	/**
	 * 未通过审核原因
	 */
	public void setUnpassReason(String unpassReason) {
		this.unpassReason = unpassReason;
	}

	/**
	 * 转换文件
	 */

	public String getConvertFile() {
		return convertFile;
	}

	/**
	 * 转换文件
	 */
	public void setConvertFile(String convertFile) {
		this.convertFile = convertFile;
	}

	/**
	 * 试读页范围
	 */

	public String getPreviewPageRange() {
		return previewPageRange;
	}

	/**
	 * 试读页范围
	 */
	public void setPreviewPageRange(String previewPageRange) {
		this.previewPageRange = previewPageRange;
	}

	/**
	 * 是否可以包月
	 */

	public Integer getIsCanPayMonthly() {
		return isCanPayMonthly;
	}

	/**
	 * 是否可以包月
	 */
	public void setIsCanPayMonthly(Integer isCanPayMonthly) {
		this.isCanPayMonthly = isCanPayMonthly;
	}

	/**
	 * 是否免费
	 */

	public Integer getIsFree() {
		return isFree;
	}

	/**
	 * 是否免费 1免费 0不免费
	 */
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	/**
	 * 是否受最高限价限制
	 */

	public Integer getIsLimitHighestPrice() {
		return isLimitHighestPrice;
	}

	/**
	 * 是否受最高限价限制
	 */
	public void setIsLimitHighestPrice(Integer isLimitHighestPrice) {
		this.isLimitHighestPrice = isLimitHighestPrice;
	}

	/**
	 * 纸质书ID
	 */

	public String getPaperBookID() {
		return paperBookID;
	}

	/**
	 * 纸质书ID
	 */
	public void setPaperBookID(String paperBookID) {
		this.paperBookID = paperBookID;
	}

	/**
	 * 授权开始日期
	 */

	public Date getLicenceStartDate() {
		return licenceStartDate;
	}

	/**
	 * 授权开始日期
	 */
	public void setLicenceStartDate(Date licenceStartDate) {
		this.licenceStartDate = licenceStartDate;
	}

	/**
	 * 授权结束日期
	 */

	public Date getLicenceEndDate() {
		return licenceEndDate;
	}

	/**
	 * 授权结束日期
	 */
	public void setLicenceEndDate(Date licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	/**
	 * 是否专有授权
	 */

	public Integer getIsSpecialLicence() {
		return isSpecialLicence;
	}

	/**
	 * 是否专有授权
	 */
	public void setIsSpecialLicence(Integer isSpecialLicence) {
		this.isSpecialLicence = isSpecialLicence;
	}

	/**
	 * 预计提交日期
	 */

	public Date getExpectSubmitDate() {
		return expectSubmitDate;
	}

	/**
	 * 预计提交日期
	 */
	public void setExpectSubmitDate(Date expectSubmitDate) {
		this.expectSubmitDate = expectSubmitDate;
	}

	/**
	 * 供应商ID
	 */

	public Long getVendorId() {
		return vendorId;
	}

	/**
	 * 供应商ID
	 */
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 0未删除,1已删除
	 */

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * 0未删除,1已删除
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 丛书名
	 */

	public String getBookSeries() {
		return bookSeries;
	}

	/**
	 * 丛书名
	 */
	public void setBookSeries(String bookSeries) {
		this.bookSeries = bookSeries;
	}

	/**
	 * 文件大小,单位：字节
	 */

	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * 文件大小,单位：字节
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 书籍来源,0上传,1单本书部分章节合并,2多本书部分章节合并
	 */

	public Integer getBookSource() {
		return bookSource;
	}

	/**
	 * 书籍来源,0上传,1单本书部分章节合并,2多本书部分章节合并
	 */
	public void setBookSource(Integer bookSource) {
		this.bookSource = bookSource;
	}

	/**
	 * 针对于来源为单本书部分章节合并记录的BOOK_ID
	 */

	public Long getOriginalBookID() {
		return originalBookID;
	}

	/**
	 * 针对于来源为单本书部分章节合并记录的BOOK_ID
	 */
	public void setOriginalBookID(Long originalBookID) {
		this.originalBookID = originalBookID;
	}

	/**
	 * 针对于来源为上传记录是否有章节合并书
	 */

	public Integer getIsHasChapterBook() {
		return isHasChapterBook;
	}

	/**
	 * 针对于来源为上传记录是否有章节合并书
	 */
	public void setIsHasChapterBook(Integer isHasChapterBook) {
		this.isHasChapterBook = isHasChapterBook;
	}

	/**
	 * 主数据MC分类
	 */
	public String getMcCode() {
		return mcCode;
	}

	/**
	 * 主数据MC分类
	 */
	public void setMcCode(String mcCode) {
		this.mcCode = mcCode;
	}

	public Long getUpCount() {
		return upCount;
	}

	public void setUpCount(Long upCount) {
		this.upCount = upCount;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * 是否有epub的书：0：没有；1：有
	 */

	public Integer getHasEpub() {
		return hasEpub;
	}

	/**
	 * 是否有epub的书：0：没有；1：有
	 */
	public void setHasEpub(Integer hasEpub) {
		this.hasEpub = hasEpub;
	}

	public String getVendorBookId() {
		return vendorBookId;
	}

	public void setVendorBookId(String vendorBookId) {
		this.vendorBookId = vendorBookId;
	}

	public String getLibraryBookCateogry() {
		return libraryBookCateogry;
	}

	public void setLibraryBookCateogry(String libraryBookCateogry) {
		this.libraryBookCateogry = libraryBookCateogry;
	}

	/** 版别代码 */

	public String getPublisherCode() {
		return publisherCode;
	}

	/** 版别代码 */
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}

	/** 想看次数 */

	public Integer getCollectionNum() {
		return collectionNum;
	}

	/** 想看次数 */
	public void setCollectionNum(Integer collectionNum) {
		this.collectionNum = collectionNum;
	}

	/** 想看次数调整值 */

	public Integer getAdjustValue() {
		return adjustValue;
	}

	/** 想看次数调整值 */
	public void setAdjustValue(Integer adjustValue) {
		this.adjustValue = adjustValue;
	}

	/**
	 * 评分
	 * 
	 * @return
	 */

	public Float getScore() {
		return score;
	}

	/**
	 * 评分
	 * 
	 * @param score
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public Integer getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Integer scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	/**
	 * 商品表ID
	 */

	public Long getProductId() {
		return productId;
	}

	/**
	 * 商品表ID
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getHeightScore() {
		return heightScore;
	}

	public void setHeightScore(Integer heightScore) {
		this.heightScore = heightScore;
	}

	/**
	 * 显示标记:名人,名书,独家,推荐,经典,绝版,推广,新书排名,首发
	 * 
	 * @return
	 */

	public String getShowMark() {
		return showMark;
	}

	/**
	 * 显示标记:名人,名书,独家,推荐,经典,绝版,推广,新书排名,首发
	 * 
	 * @return
	 */
	public void setShowMark(String showMark) {
		this.showMark = showMark;
	}

	public String getGirdleRecommend() {
		return girdleRecommend;
	}

	public void setGirdleRecommend(String girdleRecommend) {
		this.girdleRecommend = girdleRecommend;
	}

	/**
	 * 阅读对象
	 */

	public String getReadObject() {
		return readObject;
	}

	/**
	 * 阅读对象
	 */
	public void setReadObject(String readObject) {
		this.readObject = readObject;
	}

	public String getMediaRecommend() {
		return mediaRecommend;
	}

	public void setMediaRecommend(String mediaRecommend) {
		this.mediaRecommend = mediaRecommend;
	}

	/** 推荐人 */

	public String getRecommender() {
		return recommender;
	}

	/** 推荐人 */
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	/** 封面显示标记 */

	public String getCoverShowMark() {
		return coverShowMark;
	}

	/** 封面显示标记 */
	public void setCoverShowMark(String coverShowMark) {
		this.coverShowMark = coverShowMark;
	}

	/**
	 * 人工权重值(1-200)
	 * 
	 * @return
	 */

	public Integer getThirdHeightScore() {
		return thirdHeightScore;
	}

	/**
	 * 人工权重值(1-200)
	 * 
	 * @param thirdHeightScore
	 */
	public void setThirdHeightScore(Integer thirdHeightScore) {
		this.thirdHeightScore = thirdHeightScore;
	}

	/**
	 * 已读次数
	 * 
	 * @return
	 */

	public Integer getHasreadCount() {
		return hasreadCount;
	}

	/**
	 * 已读次数
	 * 
	 * @param hasreadCount
	 */
	public void setHasreadCount(Integer hasreadCount) {
		this.hasreadCount = hasreadCount;
	}

	/** 是否有封面 1为有 0为没有 */

	public Integer getHasCover() {
		return hasCover;
	}

	/** 是否有封面 1为有 0为没有 */
	public void setHasCover(Integer hasCover) {
		this.hasCover = hasCover;
	}

	/** 是不是未出版图书 0不是 1是 */

	public Integer getIsOriginal() {
		return isOriginal;
	}

	/** 是不是未出版图书 0不是 1是 */
	public void setIsOriginal(Integer isOriginal) {
		this.isOriginal = isOriginal;
	}

	public Integer getIsscan() {
		return isscan;
	}

	public void setIsscan(Integer isscan) {
		this.isscan = isscan;
	}
}
