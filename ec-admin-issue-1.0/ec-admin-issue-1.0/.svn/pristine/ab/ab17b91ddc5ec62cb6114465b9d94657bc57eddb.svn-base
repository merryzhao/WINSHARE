package com.winxuan.ec.admin.controller.cache;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.framework.cache.CacheManager;
/**
 * 
 * @author jiangyunjun
 *
 */
public class CacheDeleteControllerTest {
	
	private static final String SUCCESS = "success";

	private static final String MESSAGE = "message";

	private CacheDeleteController cdc = new CacheDeleteController();
	
	private CacheManager objectCacheManager;
	
	private CacheManager fragmentCacheManager;
	
	@Before
	public void setUp(){
		objectCacheManager = mock(CacheManager.class);
		fragmentCacheManager = mock(CacheManager.class);
		cdc.setObjectCacheManager(objectCacheManager);
		cdc.setFragmentCacheManager(fragmentCacheManager);
	}

	@Test
	public void testDeleteObjectCache() throws Exception {
		String objectCacheKeyPrefix = "test.model"; 
		when(objectCacheManager.remove(anyString())).thenReturn(true);
		long minId = 0L;
		long maxId = 1000L;
		ModelAndView mav = cdc.deleteObjectCache(objectCacheKeyPrefix,minId,maxId);
		assertModelAttributeValue(mav, MESSAGE, SUCCESS);
		verify(objectCacheManager,times(1001)).remove(anyString());
	}
	
	@Test
	public void testDeleteObjectCacheWhenMaxIdAndMinIdIllegal() throws Exception{
		String objectCacheKeyPrefix = "anykey";
		long minId = 1000L;
		long maxId = 1L;
		ModelAndView mav = cdc.deleteObjectCache(objectCacheKeyPrefix,minId,maxId);
		assertModelAttributeValue(mav, MESSAGE, "minId必须小于maxId");
		verify(objectCacheManager,times(0)).remove(anyString());
	}
	
	@Test
	public void testDeleteObjectCacheWhenKeyPrefixIsEmpty() throws Exception{
		String objectCacheKeyPrefix = "";
		long minId = 1;
		long maxId = 1000L;
		ModelAndView mav = cdc.deleteObjectCache(objectCacheKeyPrefix,minId,maxId);
		assertModelAttributeValue(mav, MESSAGE, "类名不能为空");
		verify(objectCacheManager,times(0)).remove(anyString());
	}
	
	@Test
	public void testDeleteFragmentCache() throws Exception{
		String fragmentKey = "testfragmentkey";
		when(fragmentCacheManager.remove(fragmentKey)).thenReturn(true);
		ModelAndView mav = cdc.deleteFragmentCache(fragmentKey);
		assertModelAttributeValue(mav, MESSAGE, SUCCESS);
		verify(fragmentCacheManager,times(1)).remove(fragmentKey);
	}
	
	@Test
	public void testDeleteFragmentCacheWhenFragmentKeyIsEmpty() throws Exception{
		String fragmentKey = "";
		ModelAndView mav = cdc.deleteFragmentCache(fragmentKey);
		assertModelAttributeValue(mav, MESSAGE, "片段缓存key不能为空");
		verify(fragmentCacheManager,times(0)).remove(fragmentKey);
	}

}
