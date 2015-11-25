/**
 * 
 */
package com.winxuan.ec.model.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author monica
 * 短信日志记录
 */
@Entity
@Table(name = "sms_message_log")
public class SmsMessageLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "createtime")
	private Date createTime;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "business")
	private Long business;
	
	@Column(name = "operator")
	private String operator;
	
	@Column(name = "status")
	private boolean status;
	
	@Column(name = "error")
	private String error;
	
	@Column(name = "master")
	private String master;
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setBusiness(Long business){
		this.business = business;
	}
	
	public Long getBusiness(){
		return business;
	}
	
	public void setOperator(String operator){
		this.operator = operator;
	}
	
	public String getOperator(){
		return operator;
	}
	
	public void setStatus(boolean status){
		this.status = status;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void setError(String error){
		this.error = error;
	}
	
	public String getError(){
		return error;
	}
	
	public void setMaster(String master){
		this.master = master;
	}
	
	public String getMaster(){
		return master;
	}
}
