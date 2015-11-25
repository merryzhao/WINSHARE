package com.winxuan.ec.task.model.quartz;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="QRTZ_TRIGGERS")
public class TaskTrigger implements Serializable {

	private static final long serialVersionUID = 947532773443528161L;
	
	public static final Map<String,String> status = new HashMap<String,String>();
	static{
		status.put("ACQUIRED", "运行");
		status.put("ACQUIREDQUENE", "运行队列");
		status.put("PAUSED", "暂停");
		status.put("WAITING", "等待");
		status.put("ERROR", "错误");	
		status.put("BLOCKED","阻塞");
		status.put("COMPLETE","执行中");
	}

	@Id
	@Column(name = "TRIGGER_NAME")
	private String	triggerName;
	@Column(name = "TRIGGER_GROUP")
	private String	triggerGroup;
	@Column(name = "JOB_NAME")
	private String	jobName;
	@Column(name = "JOB_GROUP")
	private String	jobGroup;
	@Column(name = "DESCRIPTION")
	private String	description;
	@Column(name = "NEXT_FIRE_TIME")
	private long nextFireTime;
	@Column(name = "PREV_FIRE_TIME")
	private long prevFireTime;
	@Column(name = "TRIGGER_STATE")
	private String state;


	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public long getPrevFireTime() {
		return prevFireTime;
	}
	public void setPrevFireTime(long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getPrevFireDate(){
		Date date = new Date(prevFireTime);
		return date;
	}
	public Date getNextFireDate(){
		Date date = new Date(nextFireTime);
		return date;
	}
	public String getStatusName(){
		String name = status.get(state);
		return name == null ? "未知" : name;
	}
}
