package com.winxuan.ec.model.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
/**
 * 帐单流水号
 * @author heyadong
 *
 */
@Table(name="bill")
@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name="billaccount")
	private BillAccount billAccount;
	
	
	@OneToMany(targetEntity=Channel.class)
	@JoinTable(name = "bill_channel", joinColumns = {@JoinColumn(name = "bill")}, inverseJoinColumns = {@JoinColumn(name = "channel")})
	private List<Channel> channels;
	
	@Column
	private String list;
	@Column
	private String invoice;

	

	@Column
	private BigDecimal allotment = BigDecimal.ZERO;
	@Column
	private BigDecimal settlement = BigDecimal.ZERO;
	@Column
	private BigDecimal refound = BigDecimal.ZERO;

	@Column
	private BigDecimal balance = BigDecimal.ZERO;
	
	@Column(name="lastbalance")
	private BigDecimal lastBalance = BigDecimal.ZERO;
	
	@Column
	private Date updatetime;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="status")
	private Code status;

	
	
	@Column
	private Date createtime;
	@Column
	private Date endtime;
	
	@ManyToOne
	@JoinColumn(name="lastemployee")
	private Employee lastEmployee;
	
	@OneToMany(targetEntity=BillItemLog.class, mappedBy="bill")
	private List<BillItemLog> logs;
	
	@OneToMany(targetEntity=BillReceiptRecord.class, mappedBy="bill")
	private List<BillReceiptRecord> billReceiptRecords;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public BigDecimal getAllotment() {
		return allotment;
	}

	public void setAllotment(BigDecimal allotment) {
		this.allotment = allotment;
	}

	public BigDecimal getSettlement() {
		return settlement;
	}

	public void setSettlement(BigDecimal settlement) {
		this.settlement = settlement;
	}

	public BigDecimal getRefound() {
		return refound;
	}

	public void setRefound(BigDecimal refound) {
		this.refound = refound;
	}


	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}
	
	public BigDecimal getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(BigDecimal lastBalance) {
		this.lastBalance = lastBalance;
	}
	
	public Employee getLastEmployee() {
		return lastEmployee;
	}

	public void setLastEmployee(Employee lastEmployee) {
		this.lastEmployee = lastEmployee;
	}
	
	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	/**
	 * 增加分配金额
	 * @param money
	 */
	public void addAllotment(BigDecimal money) {
		this.allotment = this.allotment.add(money); 
	}
	
	/**
	 * 增加结算金额
	 * @param money
	 */
	public void addSettlement(BigDecimal money) {
		this.settlement = this.settlement.add(money); 
	}
	
	/**
	 * 增加退款金额
	 * @param money
	 */
	public void addRefound(BigDecimal money) {
		this.refound = this.refound.add(money); 
	}
	
	
	/**
	 * 增加余额
	 * @param money
	 */
	public void addBalance(BigDecimal money) {
		this.balance = this.balance.add(money); 
	}
	public List<BillItemLog> getLogs() {
		return logs;
	}

	public void setLogs(List<BillItemLog> logs) {
		this.logs = logs;
	}
	
	public BillAccount getBillAccount() {
		return billAccount;
	}

	public void setBillAccount(BillAccount billAccount) {
		this.billAccount = billAccount;
	}
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public boolean containChannel(Channel channel){
		if(channel == null || this.getChannels() == null || this.getChannels().isEmpty()){
			return false;
		}
		for (Channel c : this.getChannels()){
			if (c.getId().equals(channel.getId())){
				return true;
			}
		}
		return false;
	}

	public List<BillReceiptRecord> getBillReceiptRecords() {
		return billReceiptRecords;
	}

	public void setBillReceiptRecords(List<BillReceiptRecord> billReceiptRecords) {
		this.billReceiptRecords = billReceiptRecords;
	}
}
