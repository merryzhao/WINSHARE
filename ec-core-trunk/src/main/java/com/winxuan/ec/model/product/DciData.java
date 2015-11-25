package com.winxuan.ec.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author cl
 * 电子书专用
 *
 */
@Entity
@Table(name="dci_data")
public class DciData {
	
	@Id
	@Column(name = "bookid")//九月网电子书bookid
	private Long bookId;
	@Column(name="url")
	private String url;
	@Column(name="part_b")
	private String partB;
	@Column(name="part_a")
	private String partA;
	@Column(name="work_type")
	private String workType;
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPartB() {
		return partB;
	}
	public void setPartB(String partB) {
		this.partB = partB;
	}
	public String getPartA() {
		return partA;
	}
	public void setPartA(String partA) {
		this.partA = partA;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getDci() {
		return url.substring(url.lastIndexOf("DCI:"));
	}
	
	

}
