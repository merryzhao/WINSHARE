package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;

/**
 * 订单：物流中心
 * @author yangxinyi
 *
 */
@Entity
@Table(name = "order_distribution_center")
public class OrderDistributionCenter implements Serializable {

	private static final long serialVersionUID = -4153417254729595351L;

	@Id
	@Column(name = "_order")
	@GeneratedValue(generator = "orderFK")
	@GenericGenerator(name = "orderFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
	private String id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="_order")
	private Order order;

	@Column
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dcoriginal")
	private Code dcOriginal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dcdest")
	private Code dcDest;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strategy")
	private Code strategy;
	
	/**
	 * 集货仓
	 */
	@Transient
	private List<DcPriority> dps ;
	
	/**
	 * 是否需要集货
	 */
	@Transient
	private boolean isNeedColl = true ;
	
	/**
	 * 是否能集货
	 */
	@Transient
	private boolean isCanColl = true;

	public OrderDistributionCenter() {
		super();
	}

	public OrderDistributionCenter(Order order, Code dcOriginal, Code dcDest) {
		super();
		this.order = order;
		this.dcOriginal = dcOriginal;
		this.dcDest = dcDest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Code getDcOriginal() {
		return dcOriginal;
	}

	public void setDcOriginal(Code dcOriginal) {
		this.dcOriginal = dcOriginal;
	}

	public Code getDcDest() {
		return dcDest;
	}

	public void setDcDest(Code dcDest) {
		this.dcDest = dcDest;
	}

	public Code getStrategy() {
		return strategy;
	}

	public void setStrategy(Code strategy) {
		this.strategy = strategy;
	}

	public List<DcPriority> getDps() {
		return dps;
	}

	public void setDps(List<DcPriority> dps) {
		this.dps = dps;
	}

	public boolean isNeedColl() {
		return isNeedColl;
	}

	public void setNeedColl(boolean isNeedColl) {
		this.isNeedColl = isNeedColl;
	}

	public boolean isCanColl() {
		return isCanColl;
	}

	public void setCanColl(boolean isCanColl) {
		this.isCanColl = isCanColl;
	}
}