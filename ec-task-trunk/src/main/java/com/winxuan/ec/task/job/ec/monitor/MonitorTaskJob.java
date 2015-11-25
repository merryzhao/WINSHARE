package com.winxuan.ec.task.job.ec.monitor;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.monitor.EcMonitorService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 监控任务JOB
 * @author heyadong
 * @version 1.0, 2012-10-22 上午11:37:42
 */
@Component("monitorTaskJob")
public class MonitorTaskJob implements TaskAware,Serializable {
    
  
    private static final long serialVersionUID = -1631394979379657650L;

    private static final Logger LOG = Logger.getLogger(MonitorTaskJob.class);
    
    @Autowired
    private EcMonitorService ecMonitorService;
    
    @Override
    public String getName() {
        
        return "monitorTaskJob";
    }

    @Override
    public String getDescription() {
      
        return "监控任务JOB";
    }

    @Override
    public String getGroup() {
        return TaskAware.GROUP_EC_CORE;
    }

    @Override
    public void start() {
       LOG.info("监控任务JOB 开始");
       Long time = System.currentTimeMillis();
       while(ecMonitorService.execute());
       LOG.info(String.format("监控任务JOB 完成, 用时:%s", (System.currentTimeMillis() - time)));
    }

}
