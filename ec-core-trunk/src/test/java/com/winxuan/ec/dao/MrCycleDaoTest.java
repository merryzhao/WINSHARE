package com.winxuan.ec.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.winxuan.ec.model.category.McCategory;
import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 补货周期管理DaoTest
 * @author 杨心怡
 *
 */
public class MrCycleDaoTest extends BaseTest{
	@InjectDao
	MrCycleDao mrCycleDao;
	
	@Test
	public void testMrCycleSave() {
		McCategory mcCategory = new McCategory();
		mcCategory.setId("CB100");
		MrCycle mrCycle = new MrCycle();
		mrCycle.setMcCategory(mcCategory);
		mrCycle.setCreateTime(new Date());
		mrCycle.setSaleCycle(15);
		mrCycle.setTransitCycle(20);
		mrCycle.setReplenishmentCycle(30);
		mrCycleDao.save(mrCycle);
		flush();
		assertEquals("3", mrCycle.getId().toString());
	}
}
