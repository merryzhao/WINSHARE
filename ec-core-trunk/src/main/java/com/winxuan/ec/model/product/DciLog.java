package com.winxuan.ec.model.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author cl
 *
 */
@Entity
@Table(name="dci_log")
public class DciLog {
	public static final int SRC_ONCLICK =1;
	public static final int SRC_MOVE =1;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "bookId")
	private Long bookId;
	@Column(name = "time")
	private Date time;
	@Column(name = "facke_time")
	private Date fakeTime;
	@Column(name = "src")
	private int src;//1:点击； 2：移动
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getFakeTime() {
		return fakeTime;
	}
	public void setFakeTime(Date fakeTime) {
		this.fakeTime = fakeTime;
	}
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	
	
	

}
