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

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * 
 * @author sunflower
 * 
 */
@Entity
@Table(name = "survey_item")
public class Item implements Serializable {
	
	public static final String TYPE_SEPARATOR = "S";
	public static final String TYPE_TEXT = "T";
	public static final String TYPE_SELECT = "C";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895868466139501394L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Any(metaColumn = @Column(name = "type"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "long", metaType = "string", metaValues = {
			@MetaValue(targetEntity = Separator.class, value = "S"),
			@MetaValue(targetEntity = SurveyText.class, value = "T"),
			@MetaValue(targetEntity = Select.class, value = "C") })
	@JoinColumn(name = "content")
	private Content content;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item", targetEntity = Answer.class)
	private Set<Answer> answers;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey")
	private Survey survey;

	@Column(name = "_index")
	private int index;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType(){
		if(content instanceof Separator){
			return TYPE_SEPARATOR;
		}else if(content instanceof SurveyText){
			return TYPE_TEXT;
		}else{
			return TYPE_SELECT;
		}
	}
	
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
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
