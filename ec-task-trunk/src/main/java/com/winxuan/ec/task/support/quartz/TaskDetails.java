package com.winxuan.ec.task.support.quartz;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;

public class TaskDetails implements Serializable{
	
	private static final long serialVersionUID = -19276675611894916L;
	
	private Map<String, JobDetail> jobDetails = new HashMap<String, JobDetail>();

	public Map<String, JobDetail> getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(Map<String, JobDetail> jobDetails) {
		this.jobDetails = jobDetails;
	}
	
	public void addJobDetail(String name,JobDetail jobDetail){
		if(!StringUtils.isEmpty(name) && jobDetail != null){
			jobDetails.put(name, jobDetail);
		}
	}
	
	public JobDetail getJobDetail(String key){
		if(jobDetails!=null){
			return jobDetails.get(key);			
		}else{
			return null;
		}
	}
	
	

}
