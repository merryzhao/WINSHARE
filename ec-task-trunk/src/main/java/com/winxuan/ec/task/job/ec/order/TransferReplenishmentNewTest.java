package com.winxuan.ec.task.job.ec.order;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 补货单元测试
 * @author wenchx
 * @version 1.0, 2014-7-28 上午10:22:46
 */
 
public class TransferReplenishmentNewTest   {
	/*@Autowired
	private InnerSettleService innerSettleService;
	@Test
	public void test() {
		innerSettleService.getAllOrderItem();
	}*/
	public static void main(String[] arg){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:conf/applicationContext.xml");
		TransferReplenishmentNew transferReplenishmentNew=applicationContext.getBean("transferReplenishmentNew",TransferReplenishmentNew.class);
		transferReplenishmentNew.start();
	}
}
