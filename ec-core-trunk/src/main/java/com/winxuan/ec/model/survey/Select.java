package com.winxuan.ec.model.survey;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "survey_select")
public class Select implements Content {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895868466139501394L;
	
	private static final int DIRECTION_VERTICAL = 1;//竖向
	private static final int DIRECTION_HORIZONTAL = 2;//橫向

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String title;

	@Column
	private String type;

	@Column(name = "max_select")
	private int maxSelect;

	@Column(name = "min_select")
	private int minSelect;
	
	@Column
	private int direction;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "select", targetEntity = SelectValue.class)
	@OrderBy("index")
	private Set<SelectValue> values;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMaxSelect() {
		return maxSelect;
	}

	public void setMaxSelect(int maxSelect) {
		this.maxSelect = maxSelect;
	}

	public int getMinSelect() {
		return minSelect;
	}

	public void setMinSelect(int minSelect) {
		this.minSelect = minSelect;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<SelectValue> getValues() {
		return values;
	}

	public void setValues(Set<SelectValue> values) {
		this.values = values;
	}

	public void add(SelectValue sv) {
		if (values == null) {
			values = new HashSet<SelectValue>();
		}
		values.add(sv);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void remove(SelectValue sv) {
		if (values == null || values.isEmpty()) {
			return;
		}
		values.remove(sv);
	}

}
