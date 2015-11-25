package com.winxuan.ec.service.replenishment;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.winxuan.ec.model.replenishment.MrMcType;
import com.winxuan.ec.service.BaseTest;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author yangxinyi
 *
 */
public class MrMcTypeServiceTest extends BaseTest{
	
	@Test
	@Rollback(false)
	public void testImportExecl() throws IOException {
		InputStream is = new FileInputStream("D:\\mrmctype.xls");
		services.mrMcTypeService.saveData(is);
		flush();
		List<MrMcType> results = services.mrMcTypeService.getAll();
		assertEquals(785, results.size());
	}
	
//	@Test 
	public void testGetAll() {
		List<MrMcType> results = services.mrMcTypeService.getAll();
		assertEquals(785, results.size());
	}
	
//	@Test
	public void testGetByPage() {
		Pagination page = new Pagination(200);
		page.setCurrentPage(4);
		List<MrMcType> pageResults = services.mrMcTypeService.getByPage(page);
		assertEquals(200, pageResults.size());
	}
	
//	@Test
	public void testFindByMcCategory() {
		MrMcType mrMcType = services.mrMcTypeService.findByMcCategory("MC1201031");
		assertEquals(2326, mrMcType.getId().intValue());
	}
	
//	@Test
	public void testGetSafeStockAmount(){
		
		int amount1 = services.mrMcTypeService.getSafeStockAmount("MC110605",new BigDecimal("36"));
		assertEquals(8,amount1);
		
		int amount2 = services.mrMcTypeService.getSafeStockAmount("MC110605", new BigDecimal("236"));
		assertEquals(4,amount2);
		
		int amount3 = services.mrMcTypeService.getSafeStockAmount("MC150107", new BigDecimal("13.9"));
		assertEquals(5,amount3);
		
		int amount4 = services.mrMcTypeService.getSafeStockAmount("MC150107", new BigDecimal("150"));
		assertEquals(2,amount4);
	
	}
}
