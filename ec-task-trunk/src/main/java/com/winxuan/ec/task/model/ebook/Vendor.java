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
 * 供应商表
 * @author Administrator
 *
 */
@Entity
@Table(name = "VENDOR")
public class Vendor {
	private Long vendorID;
	/**
	 * 供应商名称
	 */
	private String vendorName;
	private String vendorCode;
	private String vendorIntroduction;
	private String vendorAddress1;
	private String vendorContact1;
	private String vendorTelephone1;
	private String vendorEmail1;
	private String vendorContact2;
	private String vendorTelephone2;
	private String vendorEmail2;
	private String vendorBank;
	private String vendorBankAccount;
	private int vendorPublicType;
	private BigDecimal assignPercent;
	private String updateBy;
	private Date createDatetime;
	private String createBy;
	private Date updateDatetime;
	private int deleteFlag;
	private String simpleName;//VENDOR_SIMPLE_NAME

	
	public Vendor(){
		
	}
	
	public Vendor(Long vendorID,String vendorName){
		this.vendorID=vendorID;
		this.vendorName=vendorName;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VENDOR_ID")
	public Long getVendorID() {
		return vendorID;
	}

	public void setVendorID(Long vendorID) {
		this.vendorID = vendorID;
	}

	@Column(name = "VENDOR_NAME")
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name = "VENDOR_CODE")
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name = "VENDOR_INTRODUCTION")
	public String getVendorIntroduction() {
		return vendorIntroduction;
	}

	public void setVendorIntroduction(String vendorIntroduction) {
		this.vendorIntroduction = vendorIntroduction;
	}

	@Column(name = "VENDOR_ADDRESS1")
	public String getVendorAddress1() {
		return vendorAddress1;
	}

	public void setVendorAddress1(String vendorAddress1) {
		this.vendorAddress1 = vendorAddress1;
	}

	@Column(name = "VENDOR_CONTACT1")
	public String getVendorContact1() {
		return vendorContact1;
	}

	public void setVendorContact1(String vendorContact1) {
		this.vendorContact1 = vendorContact1;
	}

	@Column(name = "VENDOR_TELEPHONE1")
	public String getVendorTelephone1() {
		return vendorTelephone1;
	}

	public void setVendorTelephone1(String vendorTelephone1) {
		this.vendorTelephone1 = vendorTelephone1;
	}

	@Column(name = "VENDOR_EMAIL1")
	public String getVendorEmail1() {
		return vendorEmail1;
	}

	public void setVendorEmail1(String vendorEmail1) {
		this.vendorEmail1 = vendorEmail1;
	}

	@Column(name = "VENDOR_CONTACT2")
	public String getVendorContact2() {
		return vendorContact2;
	}

	public void setVendorContact2(String vendorContact2) {
		this.vendorContact2 = vendorContact2;
	}

	@Column(name = "VENDOR_TELEPHONE2")
	public String getVendorTelephone2() {
		return vendorTelephone2;
	}

	public void setVendorTelephone2(String vendorTelephone2) {
		this.vendorTelephone2 = vendorTelephone2;
	}

	@Column(name = "VENDOR_EMAIL2")
	public String getVendorEmail2() {
		return vendorEmail2;
	}

	public void setVendorEmail2(String vendorEmail2) {
		this.vendorEmail2 = vendorEmail2;
	}

	@Column(name = "VENDOR_BANK")
	public String getVendorBank() {
		return vendorBank;
	}

	public void setVendorBank(String vendorBank) {
		this.vendorBank = vendorBank;
	}

	@Column(name = "VENDOR_BANK_ACCOUNT")
	public String getVendorBankAccount() {
		return vendorBankAccount;
	}

	public void setVendorBankAccount(String vendorBankAccount) {
		this.vendorBankAccount = vendorBankAccount;
	}

	@Column(name = "VENDOR_PUBLIC_TYPE")
	public int getVendorPublicType() {
		return vendorPublicType;
	}

	public void setVendorPublicType(int vendorPublicType) {
		this.vendorPublicType = vendorPublicType;
	}

	@Column(name = "ASSIGN_PERCENT")
	public BigDecimal getAssignPercent() {
		return assignPercent;
	}

	public void setAssignPercent(BigDecimal assignPercent) {
		this.assignPercent = assignPercent;
	}

	@Column(name = "UPDATE_BY")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "CREATE_DATETIME")
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "DELETE_FLAG")
	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Column(name = "CREATE_BY")
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Column(name = "UPDATE_DATETIME")
	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	
	@Column(name = "VENDOR_SIMPLE_NAME")
	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
}
