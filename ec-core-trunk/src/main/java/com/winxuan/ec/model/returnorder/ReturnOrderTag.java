package com.winxuan.ec.model.returnorder;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.code.Code;

/**
 * @description:退货订单标签
 * @Copyright: 四川文轩在线电子商务有限公司
 * @author: liming0
 * @version: 1.0
 * @date: 2014年9月2日 上午10:01:49
 */
@Entity
@Table(name = "returnorder_tag")
public class ReturnOrderTag implements Serializable {

	private static final long serialVersionUID = 3270988277604413572L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returnorderid")
	private ReturnOrder returnOrder;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag")
	private Code tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public Code getTag() {
		return tag;
	}

	public void setTag(Code tag) {
		this.tag = tag;
	}
	
}
