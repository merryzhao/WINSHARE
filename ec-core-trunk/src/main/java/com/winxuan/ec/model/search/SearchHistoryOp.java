package com.winxuan.ec.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 搜索歷史點擊
 * @author sunflower
 *
 */
@Entity
@Table(name = "Search_history_op")
public class SearchHistoryOp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1106841499982608163L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "cookieid")
	private String cookieId;
	
	@Column(name = "sessioncookieid")
	private String sessionCookieId;	
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "docnum")
	private int docnum;
	
	@Column(name = "op_type")
	private int opType;
	
	@Column(name = "productsale")
	private long productsale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public String getSessionCookieId() {
		return sessionCookieId;
	}

	public void setSessionCookieId(String sessionCookieId) {
		this.sessionCookieId = sessionCookieId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getDocnum() {
		return docnum;
	}

	public void setDocnum(int docnum) {
		this.docnum = docnum;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}

	public long getProductsale() {
		return productsale;
	}

	public void setProductsale(long productsale) {
		this.productsale = productsale;
	}
	
}
