package com.winxuan.ec.model.monitor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 监控任务.
 * @author heyadong
 * @version 1.0, 2012-10-18 下午12:44:11
 */
@Entity
@Table(name="monitor_task")
public class MonitorTask {
    
    public static final int TASK_STATUS_ENABLE = 1;
    
    public static final int TASK_STATUS_UNABLE = 0;
    
    public static final int TASK_TYPE_PRODUCTSALE = 1;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="creator")
    private Employee creator;
    
    @Column
    private String name;
    
    @Column
    private String description;

    //任务类型
    @Column
    private int type;
  

    //监控频率, 分钟为单位
    @Column
    private int frequency = MagicNumber.THIRTY;
    
  
    @Column
    private Date next;
    @Column
    private Date start;
    @Column
    private Date end;

    @Column
    private int status = TASK_STATUS_ENABLE;
    
    //监控任务执行的SQL.返回0为正常;  非0则不正常; 由定制监控服务设置
    @Column(name="monitor_sql")
    private String monitorSql;
    
    //监控任务返回非0 执行该SQL   由定制监控服务设置
    @Column(name="monitor_event_error")
    private String eventErrorSql;
    
    //监控任务被删除执行的SQL    由定制监控服务设置
    @Column(name="monitor_event_delete")
    private String eventDeleteSql;
    
    //监控异常后,提示消息
    @Column
    private String message;
    
    //手机号，可设置多个.以";" 分隔
    @Column
    private String mobiles;
    //email,可设置多个. 以;分隔
    @Column
    private String emails;
    
    @Column
    private Date createtime;

    @ManyToOne
    @JoinColumn(name="lastoperator")
    private Employee lastOperator;
    
    @Column(name="lastoperatortime")
    private Date lastOperatorTime;
    
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    public Date getNext() {
        return next;
    }

    public void setNext(Date next) {
        this.next = next;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMonitorSql() {
        return monitorSql;
    }

    public void setMonitorSql(String monitorSql) {
        this.monitorSql = monitorSql;
    }

    public String getEventErrorSql() {
        return eventErrorSql;
    }

    public void setEventErrorSql(String eventErrorSql) {
        this.eventErrorSql = eventErrorSql;
    }

    public String getEventDeleteSql() {
        return eventDeleteSql;
    }

    public void setEventDeleteSql(String eventDeleteSql) {
        this.eventDeleteSql = eventDeleteSql;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    
    public Employee getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(Employee lastOperator) {
        this.lastOperator = lastOperator;
    }
    
    public Date getLastOperatorTime() {
        return lastOperatorTime;
    }

    public void setLastOperatorTime(Date lastOperatorTime) {
        this.lastOperatorTime = lastOperatorTime; 
    }
    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
