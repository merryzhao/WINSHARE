package com.winxuan.ec.service.monitor;

import java.util.List;
import java.util.Map;

import jxl.Workbook;

import com.winxuan.ec.model.monitor.MonitorTask;
import com.winxuan.ec.model.monitor.ProductSaleMonitor;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.pagination.Pagination;

/**
 * 监控服务
 * @author heyadong
 * @version 1.0, 2012-10-18 下午12:43:34
 */
public interface MonitorService {
    
    
    /**
     * 启用任务
     * @param taskId
     */
    void enable(Long taskId, Employee operator);
    /**
     * 禁用任务
     * @param taskId
     */
    void unable(Long taskId, Employee operator);
    /**
     * 删除任务
     * @param taskId
     */
    void remove(Long taskId, Employee operator);
    
    void addProductSaleMonitor(MonitorTask task, ProductSaleMonitor params ,Workbook workbook ,Employee employee);
    
    /**
     * 查询
     * @param params
     * @param pagination
     * @return
     */
    List<MonitorTask> query(@ParameterMap Map<String, Object> params,@Page Pagination pagination);
}
