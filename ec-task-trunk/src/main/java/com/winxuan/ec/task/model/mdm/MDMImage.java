package com.winxuan.ec.task.model.mdm;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-12-1
 */
public class MDMImage {
	private long attachmentid;
	private long merchid;
	private String attachmenttype;
	private byte[] content;

	public long getAttachmentid() {
		return attachmentid;
	}

	public void setAttachmentid(long attachmentid) {
		this.attachmentid = attachmentid;
	}

	public long getMerchid() {
		return merchid;
	}

	public void setMerchid(long merchid) {
		this.merchid = merchid;
	}

	public String getAttachmenttype() {
		return attachmenttype;
	}

	public void setAttachmenttype(String attachmenttype) {
		this.attachmenttype = attachmenttype;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
