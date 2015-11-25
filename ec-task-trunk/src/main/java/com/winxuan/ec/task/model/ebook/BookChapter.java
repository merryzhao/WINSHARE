package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: 书章节
 * 
 * @author luosh
 */
@Entity
@Table(name = "BOOK_CHAPTER")
public class BookChapter implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 章节标题
	 */
	private String chapterTitle;
	/**
	 * 书
	 */
	private Long bookId;
	/**
	 * 开始页号
	 */
	private Integer beginPage;
	/**
	 * 结束页号
	 */
	private Integer endPage;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 章节顺序
	 */
	private Integer chapterSequence;
	/**
	 * 父章节ID
	 */
	private Long parentChapterId;
	/**
	 * 章节索引
	 */
	private String chapterIndex;
	/**
	 * 锚点
	 */
	private Integer anchor;
	private String createBy;
	private Date createDatetime;
	private String updateBy;
	private Date updateDatetime;
	/**
	 * 0未删除,1已删除
	 */
	private Integer deleteFlag;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOK_CHAPTER_ID")
	public Long getId() {
		return id;
	}

	/**
	 * 章节标题
	 */
	@Column(name = "CHAPTER_TITLE")
	public String getChapterTitle() {
		return this.chapterTitle;
	}

	/**
	 * 章节标题
	 */
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	@Column(name = "BOOK_ID")
	public Long getBookId() {
		return this.bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	/**
	 * 开始页号
	 */
	@Column(name = "BEGIN_PAGE")
	public Integer getBeginPage() {
		return this.beginPage;
	}

	/**
	 * 开始页号
	 */
	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}

	/**
	 * 结束页号
	 */
	@Column(name = "END_PAGE")
	public Integer getEndPage() {
		return this.endPage;
	}

	/**
	 * 结束页号
	 */
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}

	/**
	 * 级别
	 */
	@Column(name = "LEVEL")
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 章节顺序
	 */
	@Column(name = "CHAPTER_SEQUENCE")
	public Integer getChapterSequence() {
		return this.chapterSequence;
	}

	/**
	 * 章节顺序
	 */
	public void setChapterSequence(Integer chapterSequence) {
		this.chapterSequence = chapterSequence;
	}

	/**
	 * 父章节ID
	 */
	@Column(name = "PARENT_CHAPTER_ID")
	public Long getParentChapterId() {
		return this.parentChapterId;
	}

	/**
	 * 父章节ID
	 */
	public void setParentChapterId(Long parentChapterId) {
		this.parentChapterId = parentChapterId;
	}

	/**
	 * 章节索引
	 */
	@Column(name = "CHAPTER_INDEX")
	public String getChapterIndex() {
		return this.chapterIndex;
	}

	/**
	 * 章节索引
	 */
	public void setChapterIndex(String chapterIndex) {
		this.chapterIndex = chapterIndex;
	}

	/**
	 * 锚点
	 */
	@Column(name = "ANCHOR")
	public Integer getAnchor() {
		return this.anchor;
	}

	/**
	 * 锚点
	 */
	public void setAnchor(Integer anchor) {
		this.anchor = anchor;
	}

	@Column(name = "CREATE_BY")
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "CREATE_DATETIME")
	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "UPDATE_BY")
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "UPDATE_DATETIME")
	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	/**
	 * 0未删除,1已删除
	 */
	@Column(name = "DELETE_FLAG")
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * 0未删除,1已删除
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
