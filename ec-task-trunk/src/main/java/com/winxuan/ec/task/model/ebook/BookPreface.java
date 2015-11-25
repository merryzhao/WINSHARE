package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图书的序
 * 
 * @author luosh
 * 
 */
@Entity
@Table(name = "T_BOOK_PREFACE")
public class BookPreface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

	/**
	 * 图书扩展信息类型:1前言 2序 3后记
	 */
	private Integer refType;
	/**
	 * 参照ID(图书ID)
	 */
	private Long refBookId;
	/**
	 * 标题
	 */
	private String refName;

	/**
	 * 内容
	 */
	private String content;
	/**
	 * 序作者
	 */
	private String author;

	private Date createDate;
	
	private String createBy;
	
	private Date updateDate;
	
	private String updateBy;

	/**
	 * 0:未删除；1：删除
	 */
	private Integer deleteFlag;
	
	/**
	 * 序备注
	 */
	private String remark;
	
	/**
	 * 图书ID
	 */
	private Long bookId;

	/**
	 * 序号
	 */
	private Integer quence;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	/** type 1前言 2序 3后记 */
	@Column(name = "REF_TYPE")
	public Integer getRefType() {
		return refType;
	}

	/** type 1前言 2序 3后记 */
	public void setRefType(Integer refType) {
		this.refType = refType;
	}

	/**
	 * 标题
	 * 
	 * @return
	 */
	@Column(name = "REF_NAME")
	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_BY")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "UPDATE_BY")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "DELETE_FLAG")
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "BOOK_ID")
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "REF_BOOK_ID")
	public Long getRefBookId() {
		return refBookId;
	}

	public void setRefBookId(Long refBookId) {
		this.refBookId = refBookId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuence() {
		return quence;
	}

	public void setQuence(Integer quence) {
		this.quence = quence;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
