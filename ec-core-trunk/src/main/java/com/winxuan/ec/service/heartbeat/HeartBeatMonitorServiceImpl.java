package com.winxuan.ec.service.heartbeat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.HeartBeatMonitorDao;
import com.winxuan.ec.model.heartbeat.Beat;
import com.winxuan.ec.model.heartbeat.BeatTemp;
import com.winxuan.framework.cache.CacheManager;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.sms.SMSMessage;
import com.winxuan.framework.sms.SMSService;

/**
 * 
 * @author HideHai
 * 
 */
@Transactional(propagation = Propagation.REQUIRED)
public class HeartBeatMonitorServiceImpl implements HeartBeatMonitorService,
		InitializingBean {

	private static final long serialVersionUID = 4729764522285266888L;

	/**
	 * notifyCount为发送短消息的次数
	 */
	HashMap<String, Integer> notifyCount = new HashMap<String, Integer>();

	@InjectDao
	HeartBeatMonitorDao heartBeatMonitorDao;

	@Autowired
	private SMSService smsService;
	
	@Autowired
	private CacheManager cacheManager;

	private JdbcTemplate jdbcTemplate;

	@Override
	// 要求每隔60s取一次数据，并发送一条短信
	// 当已经发送的短信条数与对应应用的短信阈值相等时，则停止发送短信
	public void afterPropertiesSet() throws Exception {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						buildNotifyCount();
						update();
						operation();
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.getMessage();
					}
				}
			}
		});
		thread.start();
	}
	
	/**
	 * afterPropertiesSet()里面的主方法
	 */
	public void operation(){
		List<BeatTemp> beats = getTarget();
		if(beats.size() != 0){
			for (BeatTemp beatTemp : beats) {
				if (beatTemp.getAvailable()) {
					if (notifyCount.get(getCacheKey(beatTemp)) < beatTemp
							.getThreshold()) {
						int temp = notifyCount
								.get(getCacheKey(beatTemp));
						/**
						 * 若电话号码为空，则不发送短信
						 */
						if(beatTemp.getPhone() != null){
							this.send(beatTemp); 
							notifyCount.put(getCacheKey(beatTemp),
									temp + 1);
						}
					}
				}
			}
		}
	}

	@Override
	/**
	 * 返回某个应用的已经发送的短信次数
	 */
	public int getMessageSend(Beat beat) {
		int messageSend = notifyCount.get(beat.getAppkey() + ": "
				+ beat.getHostname());
		return messageSend;
	}

	/**
	 * 经过处理后的应用的已发送的短信次数调整为0
	 */
	private void update() {
		String sql = "SELECT * FROM heartbeat ht "
				+ "WHERE (CURRENT_TIMESTAMP() - INTERVAL ht.timeout MINUTE) <= ht.updatetime and ht.available = 1";
		List<BeatTemp> beats = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BeatTemp>(BeatTemp.class));
		if(beats.size() != 0){
			for (BeatTemp beatTemp : beats) {
				if (notifyCount.get(getCacheKey(beatTemp)).intValue() != 0) {
					notifyCount.put(getCacheKey(beatTemp),0);
				}
			}
		}
	}

	/**
	 * 获取适合发送短信条件的心跳列表
	 */
	private List<BeatTemp> getTarget() {
		String mSql = "SELECT * FROM heartbeat ht "
				+ "WHERE (CURRENT_TIMESTAMP() - INTERVAL ht.timeout MINUTE) > ht.updatetime and ht.available = 1";
		return jdbcTemplate.query(mSql, new BeanPropertyRowMapper<BeatTemp>(
				BeatTemp.class));
	}
	
	/**
	 * notifyCount维护
	 */
	private void buildNotifyCount(){
		Short sort = 0;
		List<Beat> total = getAll(sort);
		for(Beat beat : total){
//			if (notifyCount.get(beat.getAppkey() + ": " + beat.getHostname()) == null){
//				notifyCount.put((beat.getAppkey() + ": " + beat.getHostname()), 0);
//			}
			if (!notifyCount.keySet().contains(beat.getAppkey() + ": " + beat.getHostname())){
				notifyCount.put((beat.getAppkey() + ": " + beat.getHostname()), 0);
			}
		}
		cacheManager.put("notifyCount", notifyCount, Integer.MAX_VALUE);
	}

	@Override
	/**
	 * 获取满足发送短消息条件的心跳记录
	 */
	public List<Beat> findDangerHeatBeat() {
		// TODO Auto-generated method stub
		return this.heartBeatMonitorDao.findDangerHeartBeat();
	}

	/**
	 * 发送短消息
	 * 
	 * @param beat
	 */
	private void send(BeatTemp beatTemp) {
		SMSMessage message = new SMSMessage();
		Date date = new Date();
		String app = beatTemp.getApp();
		String hostName = beatTemp.getHostname();
		message.setDate(date);
		message.setPhones(beatTemp.getPhone());
		message.setText("名称为" + app + "的应用在名为" + hostName + "的主机上出现故障!");
		this.smsService.sendMessage(message);
	}

	@Override
	public List<Beat> getAll(Short sort) {
		// TODO Auto-generated method stub
		return this.heartBeatMonitorDao.getAllBeat(sort);
	}
	
	

	@Override
	public Beat get(String appkey, String hostname) {
		// TODO Auto-generated method stub
		return this.heartBeatMonitorDao.getBeat(appkey, hostname);
	}

	@Override
	public void updateBeatInfo(Beat beat) {
		// TODO Auto-generated method stub
		this.heartBeatMonitorDao.updateBeatInfo(beat);
	}
	
	

	@Override
	/**
	 * 启用或者禁用
	 */
	public void audit(Beat beat) {
		// TODO Auto-generated method stub
		if (beat.getAvailable()) {
			beat.setAvailable(false);
		} else {
			beat.setAvailable(true);
		}
		this.heartBeatMonitorDao.updateBeatInfo(beat);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 获取心跳的key和hostname作为缓存的主键
	 * 
	 * @return
	 */
	public String getCacheKey(BeatTemp beatTemp) {
		return (beatTemp.getAppkey() + ": " + beatTemp.getHostname());
	}
}
