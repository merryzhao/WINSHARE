package com.winxuan.ec.task.service.monitor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.model.monitor.MonitorTask;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.framework.sms.SMSMessage;
import com.winxuan.framework.sms.SMSService;


/**
 * 监控服务实现类
 * @author heyadong
 * @version 1.0, 2012-10-22 上午11:15:58
 */
@Service("ecMonitorService")
@Transactional(rollbackFor=Exception.class)
public class EcMonitorServiceImpl implements EcMonitorService {
    
    private static final Logger LOG = Logger.getLogger(EcMonitorServiceImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateEcCore;
    
    @Autowired
    private SMSService smsService;
    @Autowired
    private MailService mailService;
    
    
    @Override
    public boolean execute() {
       String sql = "SELECT id,name,monitor_sql, monitor_event_error, mobiles, emails, message FROM monitor_task WHERE status=? AND start <= ? AND end >= ? AND next < ? ORDER BY next LIMIT 1";
       Date now = new Date();
       
       List<Map<String,Object>> list = jdbcTemplateEcCore.queryForList(sql, MonitorTask.TASK_STATUS_ENABLE, now, now , now);
       if(!list.isEmpty()) {
           for (Map<String,Object> map : list){
                   Long taskId = Long.valueOf(map.get("id").toString());
                   String monitorSql = map.get("monitor_sql").toString();
                   Long code = jdbcTemplateEcCore.queryForLong(monitorSql);
                   if (code != 0) {
                       String eventErrorSql = map.get("monitor_event_error").toString();
                       jdbcTemplateEcCore.execute(eventErrorSql);
                       String message = String.format("[%s]%s, [code:%s]",map.get("name"),map.get("message"), code );
                       if (map.get("mobiles") != null) {
                           String mobiles = map.get("mobiles").toString();
                           if (mobiles.length() != 0) {
                               LOG.info(String.format("监控短信发送: %s", mobiles));
                               SMSMessage msg = new SMSMessage();
                               msg.setPhones(mobiles.split(";"));
                               msg.setText(message);
                               smsService.sendMessage(msg);
                           }
                       }
                       if (map.get("emails") != null){
                           String emails = map.get("emails").toString();
                           if (emails.length() != 0){
    
                               Map<String, Object> model = new HashMap<String,Object>();
                               model.put("message", message);
                               for (String e : emails.split(";")) {
    
                                   mailService.sendMail(e, "文轩监控提醒", "task_monitor.ftl", model);
                               }
                               
                               LOG.info(String.format("监控邮件发送: %s", emails));
                           }
                       }
                   }
                   jdbcTemplateEcCore.update("UPDATE monitor_task SET next = ADDDATE(NOW(),INTERVAL frequency MINUTE) WHERE id = ? ", taskId);
           }
           return true;
       }
       
       return false;
    }


   
}
