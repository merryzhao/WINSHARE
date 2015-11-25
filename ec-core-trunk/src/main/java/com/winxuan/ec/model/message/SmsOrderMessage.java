package com.winxuan.ec.model.message;

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
 * 订单短信实体
 * @author heyadong
 * @version 1.0, 2013-1-15 上午10:08:11
 */
@Entity
@Table(name="sms_order_message")
public class SmsOrderMessage {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    //备注
    @Column
    private String remark;
    //短信类型
    @ManyToOne
    @JoinColumn(name="type")
    private Code type;
    
    //渠道ID,以;;分隔,如: ;123;235;
    @Column
    private String channels;
    //支付类型ID,以;;分隔,如: ;123;235;
    @Column
    private String paytype;
    
    @Column
    private int kindgreat = 0;
    @Column
    private int kindless =Integer.MAX_VALUE;
    //发货类型ID,以;;分隔,如: ;123;235;
    @Column(name="deliverytype")
    private String deliveryType;
    //消息内容 
    @Column
    private String message;
    @ManyToOne
    @JoinColumn(name="employee")
    private Employee employee;
    
    @Column
    private boolean enable = true;
   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Code getType() {
        return type;
    }
    public void setType(Code type) {
        this.type = type;
    }
    public String getChannels() {
        return channels;
    }
    public void setChannels(String channels) {
        this.channels = channels;
    }
    public String getPaytype() {
        return paytype;
    }
    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
    public int getKindgreat() {
        return kindgreat;
    }
    public void setKindgreat(int kindgreat) {
        this.kindgreat = kindgreat;
    }
    public int getKindless() {
        return kindless;
    }
    public void setKindless(int kindless) {
        this.kindless = kindless;
    }
    public String getDeliveryType() {
        return deliveryType;
    }
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
