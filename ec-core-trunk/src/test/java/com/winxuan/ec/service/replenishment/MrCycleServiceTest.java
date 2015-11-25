package com.winxuan.ec.service.replenishment;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.ec.service.BaseTest;

/**
 * 补货周期管理serviceTest
 * @author 杨心怡
 *
 */
public class MrCycleServiceTest extends BaseTest{
	
	@Test 
	public void getTest() {
		MrCycle mrCycle = services.mrCycleService.get(2L);
		assertEquals("MC1202011", mrCycle.getMcCategory().getId());
	}
	
	@Test
	public void testFindMrCycle() {
		String mcCategoryId = "MC1202011"; 
		List<MrCycle> mrCycles = services.mrCycleService.findByMC(mcCategoryId);
		assertEquals(mcCategoryId, mrCycles.get(0).getMcCategory().getId());
	}
	
	@Test
	@Rollback(false)
	public void testSaveData() throws IOException {
		InputStream is = new FileInputStream("D:\\mrcycle.xls");
		services.mrCycleService.saveData(is);
		flush();
		List<MrCycle> results = services.mrCycleService.getAll(null);
		assertEquals(785, results.size());
	}
}
