package com.winxuan.ec.model.channel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;

/**
 * 上传销售记录状态日志
 * @author heyadong
 *
 */
@Entity
@Table(name="sales_upload_record_status_log")
public class ChannelSalesRecordStatusLog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="uploadrecord")
	private ChannelSalesUploadRecord uploadrecord;
	
	

	@ManyToOne
	@JoinColumn(name="status")
	private Code status;
	
	@ManyToOne
	@JoinColumn(name="updater")
	private Employee updater;
	
	@Column(name="updatetime")
	private Date updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Employee getUpdater() {
		return updater;
	}

	public void setUpdater(Employee updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public ChannelSalesUploadRecord getUploadrecord() {
		return uploadrecord;
	}

	public void setUploadrecord(ChannelSalesUploadRecord uploadrecord) {
		this.uploadrecord = uploadrecord;
	}
}

