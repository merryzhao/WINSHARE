package com.winxuan.ec.exception;

import com.winxuan.ec.model.search.dic.SearchDictionary;

/**
 * 词典异常类
 * @author sunflower
 *
 */
public class DictionaryException extends BusinessException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7386700425969724549L;
	
	public DictionaryException(SearchDictionary dic, String message) {
		super(dic, message);
	}


}
