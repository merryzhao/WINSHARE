/*
 * @(#)EncodeUrlTag.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.web.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.winxuan.framework.util.StringUtils;

/**
 * URL编码转换
 * @author  HideHai
 * @version 1.0,2011-10-29
 */
public class EncodeStringTag extends BodyTagSupport{

	private static final long serialVersionUID = 4309564571772595178L;
	private static final Log LOG = LogFactory.getLog(SubStringTag.class);

	private String type = "UTF-8";

	private String content;

	@Override
	public int doStartTag() throws JspException {
		JspWriter writer = this.pageContext.getOut();
		if(!StringUtils.isNullOrEmpty(content)){
			try {
				if("UTF-8".equals(type)){
					type  = "UTF-8";
				}else if("GBK".equals(type)){
					type  = "GBK";
				}
				if(!StringUtils.isNullOrEmpty(type)){
					writer.write(java.net.URLEncoder.encode(content, type));
				}else{
					writer.write(content);
				}
			} catch (UnsupportedEncodingException e) {
				LOG.error(e.getMessage(), e);
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		this.release();
		return EVAL_PAGE;
	}

	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}




}

