/*
 * @(#)ErpDeliveryArea.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.task.model.erp;

import java.io.Serializable;

/**
 * @description
 * 
 * @author YangJun
 * @version 1.0, 2013-8-15
 */
public class ErpDeliveryArea implements Serializable {
	private static final long serialVersionUID = 1485806941438422609L;

	private String pqid;
	private String psfs;
	private String cysid;
	private String type;
	private String psfw;
	private String dc;
	private String mark;

	public String getPqid() {
		return pqid;
	}

	public void setPqid(String pqid) {
		this.pqid = pqid;
	}

	public String getPsfs() {
		return psfs;
	}

	public void setPsfs(String psfs) {
		this.psfs = psfs;
	}

	public String getCysid() {
		return cysid;
	}

	public void setCysid(String cysid) {
		this.cysid = cysid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPsfw() {
		return psfw;
	}

	public void setPsfw(String psfw) {
		this.psfw = psfw;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getLogMsg(){
		StringBuffer sb = new StringBuffer();
		sb.append("[pqid:").append(this.pqid).append(", ");
		sb.append("psfs:").append(this.psfs).append(", ");
		sb.append("cysid:").append(this.cysid).append(", ");
		sb.append("type:").append(this.type).append(", ");
		sb.append("psfw:").append(this.psfw).append(", ");
		sb.append("ccdid:").append(this.dc).append(", ");
		sb.append("mark:").append(this.mark).append("]");
		return sb.toString();
	}
}
