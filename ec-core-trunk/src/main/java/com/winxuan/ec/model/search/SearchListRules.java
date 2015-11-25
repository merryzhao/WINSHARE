package com.winxuan.ec.model.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 搜索规则
 * @author 赵芮
 *
 * 上午10:43:57
 */
@Entity
@Table(name = "search_list_rules")
public class SearchListRules {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "ct")
	private int ct;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "available")
	private Short available;
	
	@Column(name = "boost")
	private BigDecimal boost;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private SearchListRules parent;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = SearchListRules.class)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<SearchListRules> children;

	
	@Column(name="description")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
	
		this.id = id;
	}

	public int getCt() {
		return ct;
	}

	public void setCt(int ct) {
		this.ct = ct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getAvailable() {
		return available;
	}

	public void setAvailable(Short available) {
		this.available = available;
	}

	public BigDecimal getBoost() {
		return boost;
	}

	public void setBoost(BigDecimal boost) {
		this.boost = boost;
	}

	public SearchListRules getParent() {
		return parent;
	}

	public void setParent(SearchListRules parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChildren(Set<SearchListRules> children) {
		this.children = children;
	}

	public Set<SearchListRules> getChildren() {
		return children;
	}
	public List<SearchListRules> getValidChildren() {
		Set<SearchListRules> childrens = this.getChildren();
		ArrayList<SearchListRules> result = null;
		if (childrens != null && childrens.size() > 0) {
			result = new ArrayList<SearchListRules>();
			for (SearchListRules rules : childrens) {
				if (rules.available != null) {
					result.add(rules);
				}
			}
		}
		return result;
	}
}
