package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对图书操作的日志
 * 
 * @author luosh
 */
@Entity
@Table(name = "BUSINESS_LOG")
public class BusinessLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 操作类型：1：入库 2:图书入库
	 */
	@Column(name = "PROGRAM_TYPE")
	private Integer programType;

	/**
	 * 图书ID
	 */
	@Column(name = "BUSINESS_ID")
	private String businessId;

	/**
	 * 操作类型标识：2:图书入库
	 */
	@Column(name = "PROGRAM_TAG")
	private Integer programTag;

	/**
	 * 相关关键字:如文件名
	 */
	@Column(name = "KEYWORD")
	private String keyword;

	/**
	 * 描述
	 */
	@Column(name = "DISCRIPTION")
	private String discription;

	/**
	 * 错误码
	 */
	@Column(name = "error_code")
	private String errorCode;

	/**
	 * 步骤码
	 */
	@Column(name = "step_code")
	private Integer stepCode;

	/**
	 * 结果状态:1:成功；2：失败
	 */
	@Column(name = "RESULT_STATUS")
	private Integer resultStatus;

	/**
	 * 进库时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	@Column(name = "STATUS_CODE")
	private Integer statusCode;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 操作类型：1：入库;2:图书入库
	 */

	public Integer getProgramType() {
		return this.programType;
	}

	/**
	 * 操作类型：1：入库;2:图书入库
	 */
	public void setProgramType(Integer programType) {
		this.programType = programType;
	}

	/**
	 * 操作类型标识：1：epub图书入库;2:图书入库
	 */

	public Integer getProgramTag() {
		return this.programTag;
	}

	/**
	 * 操作类型标识：1：epub图书入库;2:图书入库
	 */
	public void setProgramTag(Integer programTag) {
		this.programTag = programTag;
	}

	/**
	 * 相关关键字:如文件名
	 */

	public String getKeyword() {
		return this.keyword;
	}

	/**
	 * 相关关键字:如文件名
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 描述
	 */

	public String getDiscription() {
		return this.discription;
	}

	/**
	 * 描述
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}

	/**
	 * 结果状态:1:成功；2：失败
	 * 
	 * @return
	 */

	public Integer getResultStatus() {
		return this.resultStatus;
	}

	/**
	 * 结果状态:1:成功；2：失败
	 * 
	 * @param resultStatus
	 */
	public void setResultStatus(Integer resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
	 * 进库时间
	 */

	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 进库时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 业务ID
	 * 
	 * @return
	 */

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 错误码
	 * 
	 * @return
	 */

	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 错误码
	 * 
	 * @return
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 步骤码
	 * 
	 * @return
	 */

	public Integer getStepCode() {
		return stepCode;
	}

	/**
	 * 步骤码
	 * 
	 * @return
	 */
	public void setStepCode(Integer stepCode) {
		this.stepCode = stepCode;
	}

}
