/*
 * @(#)Node.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.product;

import java.io.Serializable;

/**
 * description
 * 
 * @author wangbiao
 * @version 1.0 date 2015-3-26
 */
public class Node implements Serializable {

	private static final long serialVersionUID = -2688420548495876900L;

	private Long id;
	
	private Long pId;
	
	private String name;
	
	private Boolean open;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + "]";
	}

}
