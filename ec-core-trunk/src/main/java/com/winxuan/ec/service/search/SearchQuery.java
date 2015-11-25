package com.winxuan.ec.service.search;

/**
 * 相關反饋
 * @author sunflower
 *
 */
public interface SearchQuery {

	/**
	 * 按照某种策略取得最新的搜索扩展<br>
	 * 判断该keyword时候存在，存在update，不存在insert<br>
	 * 返回最新的搜索扩展<br>
	 * @param keyword
	 * @param query
	 * @return
	 */
	String getQuery(String keyword,String query);
}
