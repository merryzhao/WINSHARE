package com.winxuan.ec.service.cache;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.util.Constant;

/**
 * 缓存监听器
 * @author sunflower
 *
 */
public class CacheEmailListener implements MemcachedClientStateListener{


	private static final String SUBJECT_START = "memcached client-start";
	private static final String SUBJECT_SHUTDOWN = "memcached client-shutdown";
	private static final String SUBJECT_CONNECTED = "memcached client-connected";
	private static final String SUBJECT_DISCONNECTED = "memcached client-disconnected";
	private static final String SUBJECT_EXCEPTION = "memcached client-exception";
	
	private String startEmail;
	private String shutDownEmail;
	private String connectedEmail;
	private String disconnectedEmail;
	private String exceptionEmail;
	@Autowired
	private MailService mailService;
	
	
	
	@Override
	public void onStarted(MemcachedClient memcachedClient) {
		
		if(startEmail != null){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("message", "memcached client 已经启动");
			mailService.sendMail(startEmail, SUBJECT_START, Constant.MAIL_MEMCACHED_LISTENER, model);
		}
	}

	@Override
	public void onShutDown(MemcachedClient memcachedClient) {

		if(shutDownEmail != null){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("message", "memcached client 已经关闭");
			mailService.sendMail(shutDownEmail, SUBJECT_SHUTDOWN, Constant.MAIL_MEMCACHED_LISTENER, model);
		}
	}

	@Override
	public void onConnected(MemcachedClient memcachedClient,
			InetSocketAddress inetSocketAddress) {
		if(connectedEmail != null){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("message", "ip为："+inetSocketAddress.getAddress().getHostAddress()+",端口为："+inetSocketAddress.getPort()+"已经建立连接");
			mailService.sendMail(connectedEmail, SUBJECT_CONNECTED, Constant.MAIL_MEMCACHED_LISTENER, model);
		}
	}

	@Override
	public void onDisconnected(MemcachedClient memcachedClient,
			InetSocketAddress inetSocketAddress) {
		if(disconnectedEmail != null){
			Map<String, Object> model = new HashMap<String, Object>();
			
			model.put("message", "ip为："+inetSocketAddress.getAddress().getHostAddress()+",端口为："+inetSocketAddress.getPort()+"连接失败");
			mailService.sendMail(disconnectedEmail, SUBJECT_DISCONNECTED, Constant.MAIL_MEMCACHED_LISTENER, model);
		}
	}

	@Override
	public void onException(MemcachedClient memcachedClient, Throwable throwable) {
		
		if(exceptionEmail != null){
			Map<String, Object> model = new HashMap<String, Object>();
			StackTraceElement[] stes = throwable.getStackTrace();
			StringBuffer sb = new StringBuffer();
			if(stes.length>0){
				for(int i=0;i<stes.length;i++){
					sb.append(stes[i].toString()).append('\r').append('\n');
				}
			}
			model.put("message", throwable.getMessage()+sb.toString());
			mailService.sendMail(exceptionEmail, SUBJECT_EXCEPTION, Constant.MAIL_MEMCACHED_LISTENER, model);
		}
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public String getStartEmail() {
		return startEmail;
	}

	public void setStartEmail(String startEmail) {
		this.startEmail = startEmail;
	}

	public String getShutDownEmail() {
		return shutDownEmail;
	}

	public void setShutDownEmail(String shutDownEmail) {
		this.shutDownEmail = shutDownEmail;
	}

	public String getConnectedEmail() {
		return connectedEmail;
	}

	public void setConnectedEmail(String connectedEmail) {
		this.connectedEmail = connectedEmail;
	}

	public String getDisconnectedEmail() {
		return disconnectedEmail;
	}

	public void setDisconnectedEmail(String disconnectedEmail) {
		this.disconnectedEmail = disconnectedEmail;
	}

	public String getExceptionEmail() {
		return exceptionEmail;
	}

	public void setExceptionEmail(String exceptionEmail) {
		this.exceptionEmail = exceptionEmail;
	}

}
