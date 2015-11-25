package com.winxuan.ec.task.job.channel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 外部结算单元测试
 * 
 * @author wenchx
 * @version 1.0, 2014-7-28 上午10:22:46
 */

public class ChannelSalesToSapJobTest {
	
	public static void main(String[] arg) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:conf/applicationContext.xml");
		ChannelSalesToSapJob channelSalesToSapJob = applicationContext.getBean(
				"channelSalesToSapJob", ChannelSalesToSapJob.class);
		channelSalesToSapJob.start();  
	}
}
