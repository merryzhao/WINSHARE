package com.winxuan.ec.service.heartbeat;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.winxuan.ec.model.heartbeat.Beat;

/**
 * @author HideHai
 *
 */
public class HeartBeatListenerServiceImpl implements MessageListener{
	
	private static final Log LOG = LogFactory.getLog(HeartBeatListenerServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void onMessage(Message message) {
		if(!(message instanceof TextMessage)){
			LOG.error("message type error!");
			return;
		}
		TextMessage heartBeatInfo = (TextMessage) message;
		try {
			String info = heartBeatInfo.getText();
			LOG.debug(info);
			JSONObject object = JSONObject.fromObject(info);
			process(object);
		} catch (JMSException e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	private void process(JSONObject jsonObject){
		Beat beat = new Beat();
		beat.setApp(jsonObject.getString("name"));
		beat.setAppkey(jsonObject.getString("key"));
		Date updatetime = new Date();
		beat.setUpdatetime(updatetime);
		beat.setHostname(jsonObject.getString("hostname"));
		checkBeat(beat);
	}
	
	
	private void checkBeat(Beat beat){
		String selectSql = "SELECT count(1) FROM heartbeat ht WHERE ht.appkey = ? AND ht.hostname = ? ";
		int count =  jdbcTemplate.queryForInt(selectSql, new Object[]{beat.getAppkey(),beat.getHostname()});
		if(count == 0){ //信息不存在，则添加
			addBeat(beat);
		}else{	//更新，校验通知
			updateBeat(beat);
		}
	}
	
	private void addBeat(Beat beat){
		String insertSql = "INSERT INTO heartbeat(app,appkey,hostname,updatetime,threshold,timeout,available) VALUES (?,?,?,?,3,15,false)";
		jdbcTemplate.update(insertSql, new Object[]{beat.getApp(),beat.getAppkey(),beat.getHostname(),beat.getUpdatetime()});
	}
	
	private void updateBeat(Beat beat){
		String updateSql = "UPDATE heartbeat SET updatetime=? WHERE appkey=? AND hostname=?";
		jdbcTemplate.update(updateSql, new Object[]{beat.getUpdatetime(),beat.getAppkey(),beat.getHostname()});
	}

}
