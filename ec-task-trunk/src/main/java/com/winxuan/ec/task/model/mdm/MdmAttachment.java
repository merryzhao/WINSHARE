package com.winxuan.ec.task.model.mdm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * description 主数据图片
 * 
 * @author YangJun
 * @version 1.0,2011-11-14
 */
@Entity
@Table(name="mdm_attachment")
public class MdmAttachment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 988353185298942137L;

	@Id
	@Column(name="attachmentid")
	private Long attachmentId;

	@Column(name="merchid")
	private Long merchId;

	@Column(name="attachmenttype")
	private String attachmentType;

	@Column(name="filename")
	private String fileName;

	@Column(name="fileexttype")
	private String fileExtType;

	@Column(name="description")
	private String description;

	@Column(name="content")
	private byte[] content;

	@Column(name="creator")
	private String creator;

	@Column(name="createtime")
	private Date createTime;

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Long getMerchId() {
		return merchId;
	}

	public void setMerchId(Long merchId) {
		this.merchId = merchId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtType() {
		return fileExtType;
	}

	public void setFileExtType(String fileExtType) {
		this.fileExtType = fileExtType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
