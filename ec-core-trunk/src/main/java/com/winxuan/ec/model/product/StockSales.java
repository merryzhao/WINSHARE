package com.winxuan.ec.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
 * 库存占用明细
 * @author heshuai
 *
 */
@Entity
@Table(name = "stock_sales")
public class StockSales implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3363368943731185386L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "productsale")
	private Long productSaleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="productsale_stock")
	private ProductSaleStock productSaleStock;	

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dc")
    private Code dc;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
	private Code type;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
	private Code status;

	@Column(name = "sales")
	private int sales;
	
	@Column(name = "createtime")
    private Date createTime;	
	
	@Column(name = "_order")
	private String orderId;

	public StockSales(){
		this.status = new Code(Code.STOCK_SALES_STSTUS_TAKE_UP);
		this.createTime = new Date();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductSaleId() {
		return productSaleId;
	}
	
	public void setProductSaleId(Long productSaleId) {
		this.productSaleId = productSaleId;
	}
	
	public ProductSaleStock getProductSaleStock() {
		return productSaleStock;
	}
	
	public void setProductSaleStock(ProductSaleStock productSaleStock) {
		this.productSaleStock = productSaleStock;
	}
	
	public Code getDc() {
		return dc;
	}

	public void setDc(Code dc) {
		this.dc = dc;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Code getStatus() {
		return status;
	}

	public void setStatus(Code status) {
		this.status = status;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
