package com.winxuan.ec.task.support.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author luosh
 *
 */
public class ToolsUtil {
	private static final Log LOG = LogFactory.getLog(ToolsUtil.class);
	/**
	 * 根据图书ID获取图书封面图片路径区间
	 * 
	 * @param bookId
	 *            图书ID
	 * @return 路径区间
	 */
	public static String getRangDirName(String bookId) {
		String result = "1-10000";
		try {
			int i1 = Integer.parseInt(bookId);
			int min = i1 / 10000 * 10000 + 1;
			int max = (i1 / 10000 + 1) * 10000;
			result = min + "-" + max;
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		return result;
	}
}
