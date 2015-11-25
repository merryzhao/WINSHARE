package com.winxuan.ec.exception;

import com.winxuan.ec.model.search.dic.SearchTaskConfig;

/**
 * 词典抓取配置信息异常类
 * 
 * @author sunflower
 * 
 */
public class SearchTaskConfigException extends BusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -486779749289576909L;

	public SearchTaskConfigException(SearchTaskConfig config, String message) {
		super(config, message);
	}

}
