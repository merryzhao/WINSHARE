package com.winxuan.ec.task.model.ebook;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电子书产品对象
 * @author luosh
 *
 */
@Entity
@Table(name = "PRODUCT")
public class EProduct {

	/** 商品ID */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID")
	private Long productId;

	/** 商品名称 */
	@Column(name = "PRODUCT_NAME")
	private String productName;

	/** 商品描述 */
	@Column(name = "PRODUCT_DESCRIPTION")
	private String productDescription;

	/** 实体类型 1:书 2:包月 5:方正 */
	@Column(name = "PRODUCT_TYPE")
	private Integer productType;

	/** 是否可用:1:上架；0下架。 */
	@Column(name = "IS_VALID")
	private Integer isValid;

	/** 商品链接,如果有商品链接跳转到商品链接,没有则跳转到商品详细页面 */
	@Column(name = "PRODUCT_LINK")
	private String productLink;

	/** 供应商商品分成比例 */
	@Column(name = "ASSIGN_PERCENT")
	private Double assignPercent;

	/** 商品封面 */
	@Column(name = "PRODUCT_COVER")
	private String productCover;

	/** 数据创建时间 */
	@Column(name = "CREATE_DATETIME")
	private Date createDatetime;

	/** 数据创建人 */
	@Column(name = "CREATE_BY")
	private String createBy;

	/** 数据更改时间 */
	@Column(name = "UPDATE_DATETIME")
	private Date updateDatetime;

	/** 数据更改人 */
	@Column(name = "UPDATE_BY")
	private String updateBy;

	/** 0:未删除 1:已删除 */
	@Column(name = "DELETE_FLAG")
	private Integer deleteFlag;

	/** 0:未冻结 1:已冻结 */
	@Column(name = "IS_FROZEN")
	private Integer isFrozen;

	/** 上架时间 */
	@Column(name = "VALID_DATETIME")
	private Date validDatetime;

	/** 上架人 */
	@Column(name = "VALID_ADMIN_USER_ID")
	private Long validAdminUserId;

	/** 下架时间 */
	@Column(name = "INVALID_DATETIME")
	private Date invalidDatetime;

	/** 下架人 */
	@Column(name = "INVALID_ADMIN_USER_ID")
	private Long invalidAdminUserId;

	/** 下架原因 */
	@Column(name = "INVALID_REASON")
	private Long invalidReason;

	/** 下架原因描述 */
	@Column(name = "INVALID_REASON_DESCRIPTION")
	private String invalidReasonDescription;

	/** 解冻时间 */
	@Column(name = "UNFROZEN_DATETIME")
	private Date unfrozenDatetime;

	/** 解冻人 */
	@Column(name = "UNFROZEN_ADMIN_USER_ID")
	private Long unfrozenAdminUserId;

	/** 冻结时间 */
	@Column(name = "FROZEN_DATETIME")
	private Date frozenDatetime;

	/** 冻结人 */
	@Column(name = "FROZEN_ADMIN_USER_ID")
	private Long frozenAdminUserId;

	/** 冻结原因 */
	@Column(name = "FROZEN_REASON")
	private Long frozenReason;

	/** 冻结原因描述 */
	@Column(name = "FROZEN_REASON_DESCRIPTION")
	private String frozenReasonDescription;

	/** 恢复删除时间 */
	@Column(name = "UNDELETE_DATETIME")
	private Date undeleteDatetime;

	/** 恢复删除人 */
	@Column(name = "UNDELETE_ADMIN_USER_ID")
	private Long undeleteAdminUserId;

	/** 删除时间 */
	@Column(name = "DELETE_DATETIME")
	private Date deleteDatetime;

	/** 删除人 */
	@Column(name = "DELETE_ADMIN_USER_ID")
	private Long deleteAdminUserId;

	/** 删除原因 */
	@Column(name = "DELETE_REASON")
	private Long deleteReason;

	/** 删除原因描述 */
	@Column(name = "DELETE_REASON_DESCRIPTION")
	private String deleteReasonDescription;

	/** 书的数量 */
	@Column(name = "BOOK_COUNT")
	private Integer bookCount;

	/** 销售数据 */
	@Column(name = "SELLCOUNT")
	private Long sellCount;

	/** 供应商产品原价 */
	@Column(name = "PRICE")
	private BigDecimal price;

	/** 九月网定价 */
	@Column(name = "SALE_PRICE")
	private BigDecimal salePrice;

	/** 支付给供应商的价格 */
	@Column(name = "PAYFOR_PRICE")
	private BigDecimal payforPrice;

	/** 定价时间 */
	@Column(name = "CREATEDATE")
	private Date createDate;

	/** 百分比，定价人员录入,自动生成销售价=纸质书价*百分比 */
	@Column(name = "PERCENT")
	private BigDecimal percent;

	/** 包分比 */
	@Column(name = "PACKAGE_PERCENT")
	private BigDecimal packagePercent;

	/** 定价人 */
	@Column(name = "SALES_MAN")
	private Long salesMan;

	/**
	 * 原价(最基础原价，不会修改)
	 */
	@Column(name = "COST_PRICE")
	private BigDecimal costPrice;

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getValidDatetime() {
		return validDatetime;
	}

	public void setValidDatetime(Date validDatetime) {
		this.validDatetime = validDatetime;
	}

	public Long getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(Long invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/** 实体类型 1:书 2:包月 5:方正 */

	public Integer getProductType() {
		return productType;
	}

	/** 实体类型 1:书 2:包月 5:方正 */
	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	/** 是否可用:1:上架；0下架。 */

	public Integer getIsValid() {
		return isValid;
	}

	/** 是否可用:1:上架；0下架。 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getProductLink() {
		return productLink;
	}

	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}

	public Double getAssignPercent() {
		return assignPercent;
	}

	public void setAssignPercent(Double assignPercent) {
		this.assignPercent = assignPercent;
	}

	public String getProductCover() {
		return productCover;
	}

	public void setProductCover(String productCover) {
		this.productCover = productCover;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getIsFrozen() {
		return isFrozen;
	}

	public void setIsFrozen(Integer isFrozen) {
		this.isFrozen = isFrozen;
	}

	public Long getValidAdminUserId() {
		return validAdminUserId;
	}

	public void setValidAdminUserId(Long validAdminUserId) {
		this.validAdminUserId = validAdminUserId;
	}

	public String getInvalidReasonDescription() {
		return invalidReasonDescription;
	}

	/** 下架原因描述 */
	public void setInvalidReasonDescription(String invalidReasonDescription) {
		this.invalidReasonDescription = invalidReasonDescription;
	}

	public Date getUnfrozenDatetime() {
		return unfrozenDatetime;
	}

	public void setUnfrozenDatetime(Date unfrozenDatetime) {
		this.unfrozenDatetime = unfrozenDatetime;
	}

	public Long getUnfrozenAdminUserId() {
		return unfrozenAdminUserId;
	}

	public void setUnfrozenAdminUserId(Long unfrozenAdminUserId) {
		this.unfrozenAdminUserId = unfrozenAdminUserId;
	}

	public Date getFrozenDatetime() {
		return frozenDatetime;
	}

	public void setFrozenDatetime(Date frozenDatetime) {
		this.frozenDatetime = frozenDatetime;
	}

	public Long getFrozenAdminUserId() {
		return frozenAdminUserId;
	}

	public void setFrozenAdminUserId(Long frozenAdminUserId) {
		this.frozenAdminUserId = frozenAdminUserId;
	}

	public Long getFrozenReason() {
		return frozenReason;
	}

	public void setFrozenReason(Long frozenReason) {
		this.frozenReason = frozenReason;
	}

	public String getFrozenReasonDescription() {
		return frozenReasonDescription;
	}

	public void setFrozenReasonDescription(String frozenReasonDescription) {
		this.frozenReasonDescription = frozenReasonDescription;
	}

	public Date getUndeleteDatetime() {
		return undeleteDatetime;
	}

	public void setUndeleteDatetime(Date undeleteDatetime) {
		this.undeleteDatetime = undeleteDatetime;
	}

	public Long getUndeleteAdminUserId() {
		return undeleteAdminUserId;
	}

	public void setUndeleteAdminUserId(Long undeleteAdminUserId) {
		this.undeleteAdminUserId = undeleteAdminUserId;
	}

	public Date getDeleteDatetime() {
		return deleteDatetime;
	}

	public void setDeleteDatetime(Date deleteDatetime) {
		this.deleteDatetime = deleteDatetime;
	}

	public Long getDeleteAdminUserId() {
		return deleteAdminUserId;
	}

	public void setDeleteAdminUserId(Long deleteAdminUserId) {
		this.deleteAdminUserId = deleteAdminUserId;
	}

	public Long getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(Long deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getDeleteReasonDescription() {
		return deleteReasonDescription;
	}

	public void setDeleteReasonDescription(String deleteReasonDescription) {
		this.deleteReasonDescription = deleteReasonDescription;
	}

	public Date getInvalidDatetime() {
		return invalidDatetime;
	}

	public void setInvalidDatetime(Date invalidDatetime) {
		this.invalidDatetime = invalidDatetime;
	}

	public Long getInvalidAdminUserId() {
		return invalidAdminUserId;
	}

	public void setInvalidAdminUserId(Long invalidAdminUserId) {
		this.invalidAdminUserId = invalidAdminUserId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getPayforPrice() {
		return payforPrice;
	}

	public void setPayforPrice(BigDecimal payforPrice) {
		this.payforPrice = payforPrice;
	}

	public Long getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(Long salesMan) {
		this.salesMan = salesMan;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getPackagePercent() {
		return packagePercent;
	}

	public void setPackagePercent(BigDecimal packagePercent) {
		this.packagePercent = packagePercent;
	}

	public Long getSellCount() {
		return sellCount;
	}

	public void setSellCount(Long sellCount) {
		this.sellCount = sellCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

}
