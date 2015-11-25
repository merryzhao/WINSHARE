package com.winxuan.ec.model.channel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;


/**
 * 销售上传记录
 * @author heyadong
 *
 */
@Entity
@Table(name="sales_upload_record")
public class ChannelSalesUploadRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=Channel.class)
	@JoinColumn(name="channel")
	private Channel channel;
	
	@ManyToOne(targetEntity=Employee.class)
	@JoinColumn(name="uploader")
	private Employee uploader;
	
	@Column(name="uploadtime")
	private Date uploadtime;
	
	@ManyToOne(targetEntity=Code.class)
	@JoinColumn(name="status")
	private Code status;
	
	@Column(name="remark")
	private String remark;
	@Column(name="sysmsg")
	private String sysmsg;
	
	//上传销售码洋
	@Column(name="deliverylistprice")
	private BigDecimal deliveryListprice = BigDecimal.ZERO;
	//退货码洋
	@Column(name="refundlistprice")
	private BigDecimal refundListprice = BigDecimal.ZERO;
	
	
	//下传到SAP 销售码洋
	@Column(name="sapdeliverylistprice")
	private BigDecimal sapDeliveryListprice = BigDecimal.ZERO;
	
	//上传到SAP 退货码洋
	@Column(name="saprefundlistprice")
	private BigDecimal sapRefundListprice  = BigDecimal.ZERO;



	@OneToMany(targetEntity=ChannelSalesRecordStatusLog.class,cascade=CascadeType.ALL,mappedBy="uploadrecord", fetch=FetchType.LAZY)
	private List<ChannelSalesRecordStatusLog> statusLogs;
	

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Employee getUploader() {
		return uploader;
	}

	public void setUploader(Employee uploader) {
		this.uploader = uploader;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ChannelSalesRecordStatusLog> getStatusLogs() {
		return statusLogs;
	}

	public void setStatusLogs(List<ChannelSalesRecordStatusLog> statusLogs) {
		this.statusLogs = statusLogs;
	}
	public String getSysmsg() {
		return sysmsg;
	}

	public void setSysmsg(String sysmsg) {
		this.sysmsg = sysmsg;
	}
	
	public boolean isAllowDelete(){
		Long status = this.getStatus().getId();
		return Code.CHANNELSALES_SUCESSFUL.equals(status) || Code.CHANNELSALES_FAILD.equals(status);
	}

	public boolean isAllowRollback(){
		Long status = this.getStatus().getId();
		return Code.CHANNELSALES_UPLOAD_SAP_SUB_DONE.equals(status)
			|| Code.CHANNELSALES_UPLOAD_SAP_DONE.equals(status);
	}
	public void appendStatusLog(Employee updater, Code status){
		if (this.getStatusLogs() == null) {
			this.setStatusLogs(new ArrayList<ChannelSalesRecordStatusLog>());
		}
		ChannelSalesRecordStatusLog log = new ChannelSalesRecordStatusLog();
		log.setStatus(status);
		log.setUpdater(updater);
		log.setUpdatetime(new Date());
		log.setUploadrecord(this);
		this.status = status;
		this.getStatusLogs().add(log);
	}


	public BigDecimal getDeliveryListprice() {
		return deliveryListprice;
	}

	public void setDeliveryListprice(BigDecimal deliveryListprice) {
		this.deliveryListprice = deliveryListprice;
	}

	public BigDecimal getRefundListprice() {
		return refundListprice;
	}

	public void setRefundListprice(BigDecimal refundListprice) {
		this.refundListprice = refundListprice;
	}

	public BigDecimal getSapDeliveryListprice() {
		return sapDeliveryListprice;
	}

	public void setSapDeliveryListprice(BigDecimal sapDeliveryListprice) {
		this.sapDeliveryListprice = sapDeliveryListprice;
	}

	
	public BigDecimal getSapRefundListprice() {
		return sapRefundListprice;
	}

	public void setSapRefundListprice(BigDecimal sapRefundListprice) {
		this.sapRefundListprice = sapRefundListprice;
	}
	
	
}
