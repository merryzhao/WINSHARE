/*
 * @(#)SubStringTag.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.framework.util.StringUtils;

/**
 * 字符串截取
 * @author  HideHai
 * @version 1.0,2011-10-29
 */
public class SubStringTag extends BodyTagSupport{

	private static final long serialVersionUID = -5374717657877967187L;
	private static final Log LOG = LogFactory.getLog(SubStringTag.class);

	private String content;

	private int length;

	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = this.pageContext.getOut();
		StringBuffer html = new StringBuffer();
		if(!StringUtils.isNullOrEmpty(content) && length >= 0){
			int strLen = content.length();
			if(length >= strLen){
				html.append(content);
			}else{
				html.append(content.substring(0, length));
				html.append("...");
			}
		}
		try {
			writer.write(html.toString());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		this.release();
		return EVAL_PAGE;
	}

	@Override
	public BodyContent getBodyContent() {
		return super.getBodyContent();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}

