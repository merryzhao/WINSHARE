package com.winxuan.ec.task.service.monitor;

/**
 * 监控服务服务
 * @author heyadong
 * @version 1.0, 2012-10-22 上午11:14:07
 */
public interface EcMonitorService {
    /**
     * 执行监控服务
     * @return true: 还有服务尚未完成,   false:所有监控任务执行完成
     */
    public boolean execute();
}
