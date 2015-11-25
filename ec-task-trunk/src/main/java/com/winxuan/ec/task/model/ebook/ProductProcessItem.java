package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: this is ProductProcessItem bean
 * 
 * @author luosh
 */
@Entity
@Table(name = "PRODUCT_PROCESS_ITEM")
public class ProductProcessItem implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "ADMIN_USER_ID")
	private Long adminUserId;
	// 用户类型,1后台管理用户,2供应商后台管理用户
	@Column(name = "USER_TYPE")
	private Integer userType;
	@Column(name = "PRODUCT_ID")
	private Long productId;
	@Column(name = "BOOK_ID")
	private Long bookId;
	/**
	 * 操作标志,0上传文件,1编辑信息,2已提交申请审核,3审核未通过, 4审核通过,5上架,6下架,7冻结,8解除冻结,9删除,10恢复删除
	 */
	@Column(name = "FLAG")
	private Integer flag;
	// 操作标志描述
	@Column(name = "FLAG_DESCRIPTION")
	private String flagDescription;
	@Column(name = "CREATE_DATETIME")
	private Date createDatetime;
	@Column(name = "CREATE_BY")
	private String createBy;
	@Column(name = "UPDATE_DATETIME")
	private Date updateDatetime;
	@Column(name = "UPDATE_BY")
	private String updateBy;
	// 0未删除,1已删除
	@Column(name = "DELETE_FLAG")
	private Integer deleteFlag;
	// 系统码表
	@Column(name = "SYSTEM_CODE_ID")
	private Long systemCodeId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getAdminUserId() {
		return this.adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**
	 * 用户类型,1后台管理用户,2供应商后台管理用户
	 * 
	 * @return
	 */

	public Integer getUserType() {
		return this.userType;
	}

	/**
	 * 用户类型,1后台管理用户,2供应商后台管理用户
	 * 
	 * @param userType
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBookId() {
		return this.bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	/**
	 * 操作标志,0上传文件,1编辑信息,2已提交申请审核,3审核未通过, 4审核通过,5上架,6下架,7冻结,8解除冻结,9删除,10恢复删除
	 * 
	 * @return
	 */
	public Integer getFlag() {
		return this.flag;
	}

	/**
	 * 操作标志,0上传文件,1编辑信息,2已提交申请审核,3审核未通过,4审核通过, 5上架,6下架,7冻结,8解除冻结,9删除,10恢复删除
	 * 
	 * @param flag
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 操作标志描述
	 * 
	 * @return
	 */
	public String getFlagDescription() {
		return this.flagDescription;
	}

	/**
	 * 操作标志描述
	 * 
	 * @param flagDescription
	 */
	public void setFlagDescription(String flagDescription) {
		this.flagDescription = flagDescription;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 0未删除,1已删除
	 * 
	 * @return
	 */
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * 0未删除,1已删除
	 * 
	 * @param deleteFlag
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 系统码表
	 * 
	 * @return
	 */
	public Long getSystemCodeId() {
		return this.systemCodeId;
	}

	/**
	 * 系统码表
	 */
	public void setSystemCodeId(Long systemCodeId) {
		this.systemCodeId = systemCodeId;
	}

}
