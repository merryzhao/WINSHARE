package com.winxuan.ec.model.refund;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
/**
 * 支付退款日志
 * @author youwen
 *
 */
@Entity
@Table(name = "refund_failed_log")
public class RefundLog {
	
	private static final Integer ERROR = 0;
	private static final Integer INFO = 1;
	private static final Integer WARN = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="refund")
	private String refundId;
	@Column
	private Date createtime = new Date();
	@Column
	private String message;
	@Column
	private Integer type;
	@Column
	private String payment;
	@Column
	private String host;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public void info(String id,String message,String payment){
		this.createRefundFailed(id,message,payment);
		this.type = this.INFO;	
	}
	
	public void error(String id,String message,String payment){
		this.createRefundFailed(id,message,payment);
		this.type = this.ERROR;
	}

	public void warn(String id,String message,String payment){
		this.createRefundFailed(id,message,payment);
		this.type = this.WARN;
	}
	
	public void createRefundFailed(String id,String message,String payment){
		if(StringUtils.isEmpty(this.refundId)||StringUtils.isNotEmpty(id)){
			this.refundId = id;
		}
		this.message = message;
		this.payment = payment;
		try {
			this.host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			this.host = e.toString();
		}
	}
	
}
