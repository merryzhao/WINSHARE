package com.winxuan.ec.model.survey;

import java.io.Serializable;
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

/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "survey_select_value")
public class SelectValue implements Serializable {
	
	public static final int TYPE_INPUT = 1;
	public static final int TYPE_NO_INPUT = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895868466139501394L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_select")
	private Select select;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "value", targetEntity = Answer.class)
	private Set<Answer> answers;

	@Column(name = "_value")
	private String value;

	@Column
	private int type;

	@Column(name = "max_input")
	private int maxInput;

	@Column(name = "min_input")
	private int minInput;

	@Column(name = "_index")
	private int index;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Select getSelect() {
		return select;
	}

	public void setSelect(Select select) {
		this.select = select;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMaxInput() {
		return maxInput;
	}

	public void setMaxInput(int maxInput) {
		this.maxInput = maxInput;
	}

	public int getMinInput() {
		return minInput;
	}

	public void setMinInput(int minInput) {
		this.minInput = minInput;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

}
