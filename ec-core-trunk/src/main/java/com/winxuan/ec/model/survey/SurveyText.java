package com.winxuan.ec.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "survey_text")
public class SurveyText implements Content {
	
	public static final String TYPE_INPUT = "I";
	public static final String TYPE_TEXTAREA = "T";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895868466139501394L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private String type;
	
	@Column(name="max_length")
	private int maxLength;
	
	@Column(name="min_length")
	private int minLength;

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

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
