package com.winxuan.ec.model.survey;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
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
@Table(name = "survey")
public class Survey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895868466139501394L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String description;

	@Column
	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "survey", targetEntity = Item.class)
	@OrderBy("index")
	private Set<Item> items;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "survey", targetEntity = Answer.class)
	private Set<Answer> answers;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "survey", targetEntity = Release.class)
	private Set<Release> releases;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Item> getItems() {
		if (items == null) {
			items = new HashSet<Item>();
		}
		return items;
	}

	public int getLastItemIndex() {
		if (items == null) {
			return 0;
		}
		int max = 0;
		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			int index = item.getIndex();
			if (index >= max) {
				max = index;
			}
		}
		return max;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {

		if (items == null) {
			items = new HashSet<Item>();
		}
		items.add(item);
	}

	public void removeItem(Item item) {

		if (items == null) {
			return;
		}
		items.remove(item);
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Set<Release> getReleases() {
		return releases;
	}

	public void setReleases(Set<Release> releases) {
		this.releases = releases;
	}
	public String getReleaseWithSeg(){
		if(releases == null){
			return "";
		}
		Iterator<Release> it = releases.iterator();
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		while(it.hasNext()){
			if(first){
				sb.append(it.next().getUrl());
				first = false;
			}else{
				sb.append(";").append(it.next().getUrl());
			}
		}
		return sb.toString();
	}
}
