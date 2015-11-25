package com.winxuan.ec.task.model.ebook;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 九月网电子书变更表
 * @author luosh
 *
 */
@Entity
@Table(name="T_BOOK_CHANGE")
public class EbookChange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "BOOK_ID")
	private Long bookID;
	@Column(name = "PRODUCT_ID")
	private Long productId;
	@Column(name = "AUTHOR")
	private String author;
	@Column(name = "BOOK_NAME")
	private String bookName;
	@Column(name = "ISBN")
	private String isbn;
	@Column(name = "PUBLISHER_NAME")
	private String publisherName;
	@Column(name = "PUBLISH_DATE")
	private Date publishDate;
	@Column(name = "PUBLISH_VERSION")
	private String publishVersion;
	@Column(name = "PRINTED_COUNT")
	private Integer printedCount;
	@Column(name = "PRINTED_DATE")
	private Date printedDate;
	@Column(name = "WORD_COUNT")
	private String wordCount;
	@Column(name = "PAGE_COUNT")
	private Integer pageCount;
	@Column(name = "PRINTED_QUANTITY")
	private Integer printedQuantity;
	@Column(name = "FOLIO")
	private String folio;
	@Column(name = "PAPER_MATERIAL")
	private String paperMaterial;
	@Column(name = "INTRODUCTION")
	private String introduction;
	@Column(name = "AUTHOR_INTRODUCTION")
	private String authorIntroduction;
	@Column(name = "EDITOR_COMMENT")
	private String editorComment;
	@Column(name = "TABLE_OF_CONTENTS")
	private String tableOfContents;
	@Column(name = "PAPER_PRICE")
	private BigDecimal paperPrice;
	@Column(name = "PREVIEW_PAGE_RANGE")
	private String previewPageRange;
	@Column(name = "HAS_EPUB")
	private Integer hasEpub;
	@Column(name = "IS_FREE")
	private Integer isFree;
	@Column(name = "VENDOR_ID")
	private Long vendorId;
	@Column(name = "CREATE_DATETIME")
	private Date createDatetime;
	@Column(name = "UPDATE_DATETIME")
	private Date updateDatetime;
	@Column(name = "UPDATE_BY")
	private String updateBy;
	@Column(name = "FILE_SIZE")
	private Long fileSize;
	@Column(name = "LIBRARY_CATEGORY_CODE")
	private String libraryCategoryCode;
	@Column(name = "PUBLISHER_CODE")
	private String publisherCode;
	@Column(name = "HAS_COVER")
	private Integer hasCover;
	@Column(name = "STORE_FLAG")
	private Integer storeFlag;
	@Column(name = "BAMBOOK_Enc_FLAG")
	private Integer bambookEncFlag;
	@Column(name = "EBOOK_Enc_FLAG")
	private Integer ebookEncFlag; 
	@Column(name = "WIN_FLAG")
	private Integer winFlag;
	@Column(name = "ELIB_FLAG")
	private Integer elibFlag;
	@Column(name = "SALE_PRICE")
	private BigDecimal salePrice;
	@Column(name="CATEGORY")
	private Long category;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookID() {
		return bookID;
	}
	public void setBookID(Long bookID) {
		this.bookID = bookID;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishVersion() {
		return publishVersion;
	}
	public void setPublishVersion(String publishVersion) {
		this.publishVersion = publishVersion;
	}
	public Integer getPrintedCount() {
		return printedCount;
	}
	public void setPrintedCount(Integer printedCount) {
		this.printedCount = printedCount;
	}
	public Date getPrintedDate() {
		return printedDate;
	}
	public void setPrintedDate(Date printedDate) {
		this.printedDate = printedDate;
	}
	public String getWordCount() {
		return wordCount;
	}
	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPrintedQuantity() {
		return printedQuantity;
	}
	public void setPrintedQuantity(Integer printedQuantity) {
		this.printedQuantity = printedQuantity;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getPaperMaterial() {
		return paperMaterial;
	}
	public void setPaperMaterial(String paperMaterial) {
		this.paperMaterial = paperMaterial;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAuthorIntroduction() {
		return authorIntroduction;
	}
	public void setAuthorIntroduction(String authorIntroduction) {
		this.authorIntroduction = authorIntroduction;
	}
	public String getEditorComment() {
		return editorComment;
	}
	public void setEditorComment(String editorComment) {
		this.editorComment = editorComment;
	}
	public String getTableOfContents() {
		return tableOfContents;
	}
	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}
	public BigDecimal getPaperPrice() {
		return paperPrice;
	}
	public void setPaperPrice(BigDecimal paperPrice) {
		this.paperPrice = paperPrice;
	}
	public String getPreviewPageRange() {
		return previewPageRange;
	}
	public void setPreviewPageRange(String previewPageRange) {
		this.previewPageRange = previewPageRange;
	}
	public Integer getHasEpub() {
		return hasEpub;
	}
	public void setHasEpub(Integer hasEpub) {
		this.hasEpub = hasEpub;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getLibraryCategoryCode() {
		return libraryCategoryCode;
	}
	public void setLibraryCategoryCode(String libraryCategoryCode) {
		this.libraryCategoryCode = libraryCategoryCode;
	}
	public String getPublisherCode() {
		return publisherCode;
	}
	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}
	public Integer getHasCover() {
		return hasCover;
	}
	public void setHasCover(Integer hasCover) {
		this.hasCover = hasCover;
	}
	public Integer getStoreFlag() {
		return storeFlag;
	}
	public void setStoreFlag(Integer storeFlag) {
		this.storeFlag = storeFlag;
	}
	public Integer getBambookEncFlag() {
		return bambookEncFlag;
	}
	public void setBambookEncFlag(Integer bambookEncFlag) {
		this.bambookEncFlag = bambookEncFlag;
	}
	public Integer getEbookEncFlag() {
		return ebookEncFlag;
	}
	public void setEbookEncFlag(Integer ebookEncFlag) {
		this.ebookEncFlag = ebookEncFlag;
	}
	public Integer getWinFlag() {
		return winFlag;
	}
	public void setWinFlag(Integer winFlag) {
		this.winFlag = winFlag;
	}
	public Integer getElibFlag() {
		return elibFlag;
	}
	public void setElibFlag(Integer elibFlag) {
		this.elibFlag = elibFlag;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}

	
}
