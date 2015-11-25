package com.winxuan.ec.task.job.ec.settle;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 当日结算单元测试
 * @author wenchx
 * @version 1.0, 2014-7-28 上午10:22:46
 */
 
public class SalesSettleJobTest   {
 
	public static void main(String[] arg){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:conf/applicationContext.xml");
		SalesSettleJob salesSettleJob=applicationContext.getBean("salesSettleJob",SalesSettleJob.class);
		salesSettleJob.start();
	}
}
