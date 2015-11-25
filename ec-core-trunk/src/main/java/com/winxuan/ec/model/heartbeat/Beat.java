/**
 * 
 */
package com.winxuan.ec.model.heartbeat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author monica
 *
 */
@Entity
@Table(name = "heartbeat")
public class Beat implements Serializable{


	private static final long serialVersionUID = 7209530475103126855L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * 应用名称
	 */
	@Column(name = "app")
	private String app;
	
	/**
	 * 应用键
	 */
	@Column(name = "appkey")
	private String appkey;
	
	/**
	 * 应用所在的主机名称
	 */
	@Column(name = "hostname")
	private String hostname;
	
	/**
	 * 发送短消息的次数
	 */
	@Column(name = "threshold")
	private int threshold;
	
	/**
	 * 已经发送的短信次数
	 */
	@Transient
	private int messagesend;
	
	/**
	 * 超时时间
	 */
	@Column(name = "timeout")
	private int timeout;
	
	/**
	 * 更新时间
	 */
	@Column(name = "updatetime")
	private Date updatetime;
	
	/**
	 * 电话号码
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 启用或禁用
	 * @return
	 */
	@Column(name = "available")
	private boolean available;
	
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getThreshold(){
		return threshold;
	}
	public void setThreshold(int threshold){
		this.threshold = threshold;
	}
	public int getTimeout(){
		return timeout;
	}
	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public boolean getAvailable(){
		return available;
	}
	public void setAvailable(boolean available){
		this.available = available;
	}
	@Transient
	public int getMessagesend(){
		return messagesend;
	}
	public void setMessagesend(int messagesend){
		this.messagesend = messagesend;
	}
}
