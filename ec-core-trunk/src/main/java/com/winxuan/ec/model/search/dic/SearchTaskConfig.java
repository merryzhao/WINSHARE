package com.winxuan.ec.model.search.dic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * 任务配置类
 * @author sunflower
 *
 */
@Entity
@Table(name = "search_task_config")
public class SearchTaskConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519543866913603298L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "script")
	private String script;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="source")
	private Code source;
	
	@Column(name = "charset_name")
	private String charsetName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Code getSource() {
		return source;
	}

	public void setSource(Code source) {
		this.source = source;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
	
}
