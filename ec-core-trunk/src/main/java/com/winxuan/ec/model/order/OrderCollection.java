/**
 * 
 */
package com.winxuan.ec.model.order;

import java.io.Serializable;

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
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-8-6
 */
@Entity
@Table(name = "order_collection")
public class OrderCollection implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7894925722893293278L;

	@Id
	@Column(name = "_order")
	@GeneratedValue(generator = "orderFK")
	@GenericGenerator(name = "orderFK", strategy = "foreign", parameters = @Parameter(name = "property", value = "order"))
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "_order")
	private Order order;
	
	private boolean collect;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strategy")
	private Code strategy;

	public OrderCollection (){
		
	}
    public OrderCollection (Order order,boolean collect){
		this.order = order;
		this.collect = collect;
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public Code getStrategy() {
		return strategy;
	}
	public void setStrategy(Code strategy) {
		this.strategy = strategy;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
