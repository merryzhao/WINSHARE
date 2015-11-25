package com.winxuan.eleven;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winxuan.eleven.service.DateLogService;

/**
 * 
 * @author Other
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:/com/winxuan/eleven/applicationContext.xml")
public class DateLogUtil {
	
	@Autowired
	private DateLogService dateLogService;
	
	
	@Test
	public void test121(){
		dateLogService.createXLS();
	}

}
