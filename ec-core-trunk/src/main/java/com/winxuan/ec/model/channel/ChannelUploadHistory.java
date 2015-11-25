package com.winxuan.ec.model.channel;

import java.io.Serializable;
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

/**
 * 渠道上传历史记录
 * @author heyadong
 * @version 1.0, 2012-9-6 下午02:00:24
 */
@Table(name="channel_upload_history")
@Entity
public class ChannelUploadHistory implements Serializable{
    
    private static final long serialVersionUID = -6860569240280082563L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="operator")
    private Employee employee;
    
    @Column
    private Date uploadtime;
    
    @Column
    private long total;
    
    @Column
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
}
