package com.winxuan.ec.service.monitor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.MonitorTaskDao;
import com.winxuan.ec.model.monitor.MonitorTask;
import com.winxuan.ec.model.monitor.ProductSaleMonitor;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;


/**
 * 监控服务
 * @author heyadong
 * @version 1.0, 2012-10-18 下午02:33:21
 */
@Service("monitorService")
@Transactional(rollbackFor=Exception.class)
public class MonitorServiceImpl implements MonitorService{

    @InjectDao
    private MonitorTaskDao monitorTaskDao;
    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void enable(Long taskId, Employee operator) {
        MonitorTask task = monitorTaskDao.get(taskId);
        task.setStatus(MonitorTask.TASK_STATUS_ENABLE);
        task.setLastOperator(operator);
        task.setLastOperatorTime(new Date());
        monitorTaskDao.update(task);
    }

    @Override
    public void unable(Long taskId, Employee operator) {
        MonitorTask task = monitorTaskDao.get(taskId);
        task.setStatus(MonitorTask.TASK_STATUS_UNABLE);
        task.setLastOperator(operator);
        task.setLastOperatorTime(new Date());
        monitorTaskDao.update(task);
    }

    @Override
    public void remove(Long taskId, Employee operator) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addProductSaleMonitor(MonitorTask task, ProductSaleMonitor params,
            Workbook workbook, Employee employee) {
        task.setType(MonitorTask.TASK_TYPE_PRODUCTSALE);
        task.setCreator(employee);
        Calendar next = Calendar.getInstance();
        next.setTime(new Date());
        next.add(Calendar.MINUTE, task.getFrequency());
        task.setNext(next.getTime());
        
        if(task.getStart() == null){
            task.setStart(new Date());
        }
        
        if (task.getEnd() == null) {
            Calendar max = Calendar.getInstance();
            max.setTime(new Date());
            max.add(Calendar.YEAR, MagicNumber.THOUSAND);
        }
        
        
        monitorTaskDao.save(task);
        Long taskId = task.getId();
        //添加关联商品
        Sheet sheet = workbook.getSheet(0);
        int len = sheet.getRows();
        for (int row = 0; row < len; row++) {
            Cell cell = sheet.getCell(0, row);
            String content = cell.getContents();
            if (content != null && content.matches("\\d+")) {
                Long productSale = Long.valueOf(content);
                jdbcTemplate.update("INSERT INTO monitor_task_productsale(task,productsale) VALUES(?,?)",taskId, productSale);
            }
        }
        
        
        StringBuilder monitorSql = new StringBuilder();
        StringBuilder eventError = new StringBuilder();
        StringBuilder eventDelete = new StringBuilder();
        //删除事件.SQL,做资源回收
        eventDelete.append(String.format("DELETE FROM monitor_productsale_log WHERE task = %s;", taskId));
        eventDelete.append(String.format("DELETE FROM monitor_task_productsale WHERE task = %s;", taskId));
        eventDelete.append(String.format("DELETE FROM monitor_task WHERE id =%s", taskId));
        
        
        //监控查询SQL
        monitorSql.append("SELECT COUNT(*) FROM monitor_task_productsale mtp,product_sale ps WHERE mtp.productsale = ps.id AND task=" + taskId);
        
        //插入日志表        
        eventError.append("INSERT INTO monitor_productsale_log(task,productsale,date)");
        eventError.append(" SELECT " +taskId+ ",productsale,NOW() FROM monitor_task_productsale mtp,product_sale ps");
        eventError.append(" WHERE  mtp.task =  " +taskId+ " AND ps.id = mtp.productsale");
        
        if (params.getStockLess() != null) {
            monitorSql.append(" AND ps.stockquantity < ps.salequantity + " + params.getStockLess());
            eventError.append(" AND ps.stockquantity < ps.salequantity + " + params.getStockLess());
        }
        task.setMonitorSql(monitorSql.toString() + ";");
        task.setEventErrorSql(eventError.toString() + ";");
        task.setEventDeleteSql(eventDelete.toString() + ";");
        
        
        
        monitorTaskDao.update(task);
    }

    @Override
    public List<MonitorTask> query(Map<String, Object> params,
            Pagination pagination) {
        
        return monitorTaskDao.query(params, pagination);
    }

}
