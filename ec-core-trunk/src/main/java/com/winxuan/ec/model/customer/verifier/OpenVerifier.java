/*
 * @(#)OpenVerifier.java
 *
 * Copyright 2012 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.customer.verifier;

import com.winxuan.ec.model.code.Code;
import com.winxuan.framework.validator.Verifier;

/**
 * 联合登录
 * 
 * @author liuan
 * @version 1.0,2012-2-24
 */
public class OpenVerifier implements Verifier {

	public static final short UNION_LOGIN = 1;
	public static final short FANLI_LOGIN = 2;
	private Code source;
	private String outerId;
	private String nickName;
	private String figureURL;
	private String headShow;
	private String mail;
	private short type;
	

	public OpenVerifier(Code source, String outerId) {
		this.source = source;
		this.outerId = outerId;
	}

	public Code getSource() {
		return source;
	}

	public void setSource(Code source) {
		this.source = source;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param figureURL
	 *            the figureURL to set
	 */
	public void setFigureURL(String figureURL) {
		this.figureURL = figureURL;
	}

	/**
	 * @return the figureURL
	 */
	public String getFigureURL() {
		return figureURL;
	}

	public String getHeadShow() {
		return headShow;
	}

	public void setHeadShow(String headShow) {
		this.headShow = headShow;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
}
