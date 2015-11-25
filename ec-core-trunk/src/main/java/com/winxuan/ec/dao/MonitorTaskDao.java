package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.monitor.MonitorTask;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * 监控任务DAO
 * @author heyadong
 * @version 1.0, 2012-10-18 下午02:31:43
 */
public interface MonitorTaskDao {
    
    @Save
    void save(MonitorTask task);
    @Get
    MonitorTask get(Long id);
    
    @Update
    void update(MonitorTask task);
    
    @Query("SELECT m FROM MonitorTask m")
    List<MonitorTask> query(@ParameterMap Map<String, Object> params,@Page Pagination pagination);
}
