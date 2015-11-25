/*
 * @(#)ErpDeliveryCompany.java
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
public class ErpDeliveryCompany implements Serializable {
	private static final long serialVersionUID = -6948327148241728133L;

	private String id;
	private String name;
	private String zt;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogMsg(){
		StringBuffer sb = new StringBuffer();
		sb.append("[id:").append(this.id).append(", ");
		sb.append("name:").append(this.name).append(", ");
		sb.append("zt:").append(this.zt).append(", ");
		sb.append("type:").append(this.type).append("]");
		return sb.toString();
	}
}
