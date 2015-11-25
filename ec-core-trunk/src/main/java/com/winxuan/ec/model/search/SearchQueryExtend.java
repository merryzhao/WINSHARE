package com.winxuan.ec.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 搜索结果点击频率统计信息
 * @author sunflower
 *
 */
@Entity
@Table(name = "search_query_extend")
public class SearchQueryExtend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 516146379857179773L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "query")
	private String query;
	
	@Column(name = "query_extend")
	private String queryExtend;
	
	@Column(name = "satisfy")
	private Long satisfy;
	
	@Column(name = "unsatisfy")
	private Long unsatisfy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryExtend() {
		return queryExtend;
	}

	public void setQueryExtend(String queryExtend) {
		this.queryExtend = queryExtend;
	}

	public Long getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(Long satisfy) {
		this.satisfy = satisfy;
	}

	public Long getUnsatisfy() {
		return unsatisfy;
	}

	public void setUnsatisfy(Long unsatisfy) {
		this.unsatisfy = unsatisfy;
	}
}
