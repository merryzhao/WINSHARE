package com.winxuan.ec.model.order;

import java.io.Serializable;
import java.util.Date;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.winxuan.ec.model.code.Code;

/**
 * 订单初始化表
 * @author heshuai
 *
 */
@Entity
@Table(name="order_init")
public class OrderInit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "_order")
	@GeneratedValue(generator = "orderFK")
	@GenericGenerator(name = "orderFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
	private Code status;
	
	@Column(name = "createtime")
    private Date createTime;	

	public OrderInit(Order order ){
		setOrder(order);
		setStatus(new Code(Code.ORDER_INIT_WAITING));
		setCreateTime(new Date());
	}
	public OrderInit(){
		
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
	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

}
