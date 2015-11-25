package com.winxuan.ec.model.order;

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

import com.winxuan.ec.model.user.User;

/**
 * 复制订单日志
 * 
 * @author juqkai(juqkai@gmail.com)
 */
@Entity
@Table(name = "order_clone_log")
public class OrderCloneLog implements Serializable{
    private static final long serialVersionUID = 9209927125220479382L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_order")
    private Order source;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dest_order")
    private Order dest;
    
    @Column(name = "clonetime")
    private Date cloneTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_user")
    private User operator;
    
    @Column(name="remark")
    private String remark = "";
    
    public OrderCloneLog() {}
    
    public OrderCloneLog(User operator, Order source, Order dest) {
        this.operator = operator;
        this.source = source;
        this.dest = dest;
        cloneTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getSource() {
        return source;
    }

    public void setSource(Order source) {
        this.source = source;
    }

    public Order getDest() {
        return dest;
    }

    public void setDest(Order dest) {
        this.dest = dest;
    }

    public Date getCloneTime() {
        return cloneTime;
    }

    public void setCloneTime(Date cloneTime) {
        this.cloneTime = cloneTime;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
