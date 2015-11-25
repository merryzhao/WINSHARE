package com.winxuan.ec.model.search.dic;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * 词典类
 * @author sunflower
 *
 */
@Entity
@Table(name = "search_dic")
public class SearchDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1054083752060568783L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "word")
	private String word;

	@Column(name = "is_audit")
	private boolean isAudit;
	
	@Column(name = "is_delete")
	private boolean isDelete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source")
	private Code source;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_by")
	private Employee auditBy;

	@Column(name = "audit_time")
	private Date auditTime;

	@Column(name = "create_time")
	private Date createTime;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "search_synonym_relation", joinColumns = { @JoinColumn(name = "synonym1") }, inverseJoinColumns = { @JoinColumn(name = "synonym2") })
	private Set<SearchDictionary> synonym2s = new HashSet<SearchDictionary>();

	@ManyToMany(mappedBy = "synonym2s", fetch = FetchType.LAZY)
	private Set<SearchDictionary> synonym1s = new HashSet<SearchDictionary>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private SearchDictionary parent;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = SearchDictionary.class)
	@OrderBy("id")
	private Set<SearchDictionary> children = new HashSet<SearchDictionary>();

	public Set<SearchDictionary> getSynonym2s() {
		return synonym2s;
	}

	public void setSynonym2s(Set<SearchDictionary> synonym2s) {
		this.synonym2s = synonym2s;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Set<SearchDictionary> getSynonym1s() {
		return synonym1s;
	}

	public void setSynonym1s(Set<SearchDictionary> synonym1s) {
		this.synonym1s = synonym1s;
	}

	public void addSynonym2(SearchDictionary dic) {
		if (synonym2s == null) {
			synonym2s = new HashSet<SearchDictionary>();
		}
		synonym2s.add(dic);
	}
	
	public void removeSynonym2(SearchDictionary dic) {
		if (synonym2s != null) {
			synonym2s.remove(dic);
		}
	}

	public void addSynonym1(SearchDictionary dic) {
		if (synonym1s == null) {
			synonym1s = new HashSet<SearchDictionary>();
		}
		synonym1s.add(dic);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public boolean isAudit() {
		return isAudit;
	}

	public void setAudit(boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Code getSource() {
		return source;
	}

	public void setSource(Code source) {
		this.source = source;
	}

	public Employee getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(Employee auditBy) {
		this.auditBy = auditBy;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SearchDictionary)) {
			return false;
		}
		SearchDictionary other = (SearchDictionary) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	public SearchDictionary getParent() {
		return parent;
	}

	public void setParent(SearchDictionary parent) {
		this.parent = parent;
	}

	public Set<SearchDictionary> getChildren() {
		return children;
	}

	public void setChildren(Set<SearchDictionary> children) {
		this.children = children;
	}
	
	public void addChild(SearchDictionary dic){
		if(children == null){
			children = new HashSet<SearchDictionary>();
		}
		children.add(dic);
	}
	
}
