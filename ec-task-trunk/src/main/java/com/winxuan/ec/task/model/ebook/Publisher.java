package com.winxuan.ec.task.model.ebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: this is Publisher bean
 * @author luosh
 */
@Entity
@Table(name = "PUBLISHER")
public class Publisher {

	private Long id;
	//出版社名称
	private String publisherName;
	//出版社代码
	private String publisherCode;
	//出版社全称
	private String publisherFullName;
	//出版社简介
	private String publisherIntroduction;
	//出版社地址
	private String publisherAddress;
	//出版社邮编
	private String publisherZipCode;
	//出版社电话
	private String publisherTelephone;
	//出版社传真
	private String publisherFax;
	//出版社邮箱
	private String publisherEmail;
	//出版社网址
	private String publisherWebSite;
	//出版社类型,1出版社,2个人
	private Integer publisherType;
	//
	private String createBy;
	//
	private Date createDatetime;
	//
	private String updateBy;
	//
	private Date updateDatetime;
	//
	private Integer deleteFlag;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PUBLISHER_ID")
	public Long getId() {
		return id;
	}

	@Column(name = "publisher_Name")
	public String getPublisherName() {
		return this.publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Column(name = "publisher_Code")
	public String getPublisherCode() {
		return this.publisherCode;
	}

	public void setPublisherCode(String publisherCode) {
		this.publisherCode = publisherCode;
	}

	@Column(name = "publisher_Full_Name")
	public String getPublisherFullName() {
		return this.publisherFullName;
	}

	public void setPublisherFullName(String publisherFullName) {
		this.publisherFullName = publisherFullName;
	}

	@Column(name = "publisher_Introduction")
	public String getPublisherIntroduction() {
		return this.publisherIntroduction;
	}

	public void setPublisherIntroduction(String publisherIntroduction) {
		this.publisherIntroduction = publisherIntroduction;
	}

	@Column(name = "publisher_Address")
	public String getPublisherAddress() {
		return this.publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	@Column(name = "publisher_Zip_Code")
	public String getPublisherZipCode() {
		return this.publisherZipCode;
	}

	public void setPublisherZipCode(String publisherZipCode) {
		this.publisherZipCode = publisherZipCode;
	}

	@Column(name = "publisher_Telephone")
	public String getPublisherTelephone() {
		return this.publisherTelephone;
	}

	public void setPublisherTelephone(String publisherTelephone) {
		this.publisherTelephone = publisherTelephone;
	}

	@Column(name = "publisher_Fax")
	public String getPublisherFax() {
		return this.publisherFax;
	}

	public void setPublisherFax(String publisherFax) {
		this.publisherFax = publisherFax;
	}

	@Column(name = "publisher_Email")
	public String getPublisherEmail() {
		return this.publisherEmail;
	}

	public void setPublisherEmail(String publisherEmail) {
		this.publisherEmail = publisherEmail;
	}

	@Column(name = "publisher_Web_Site")
	public String getPublisherWebSite() {
		return this.publisherWebSite;
	}

	public void setPublisherWebSite(String publisherWebSite) {
		this.publisherWebSite = publisherWebSite;
	}

	@Column(name = "publisher_Type")
	public Integer getPublisherType() {
		return this.publisherType;
	}

	public void setPublisherType(Integer publisherType) {
		this.publisherType = publisherType;
	}

	@Column(name = "create_By")
	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Column(name = "create_Datetime")
	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "update_By")
	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "update_Datetime")
	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "DELETE_FLAG")
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean equals(Object obj) {
		if (null == obj){
			return false;
		}
		if (!(obj instanceof Publisher)){
			return false;
		}else {
			final Publisher publisher = (Publisher) obj;
			if (null == this.getId() || null == publisher.getId()){
				return false;
			}else{
				return (this.getId() == publisher.getId());
			}
		}
	}

	public int hashCode() {
		return id.intValue();
	}
	

}
