package com.winxuan.ec.admin.controller.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.search.SearchHistoryHot;
import com.winxuan.ec.model.search.dic.SearchDictionary;
import com.winxuan.ec.model.search.dic.SearchTaskConfig;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.search.SearchHistoryHotService;
import com.winxuan.ec.service.search.dic.DictionaryFetchService;
import com.winxuan.ec.service.search.dic.DictionaryService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 词典管理
 * 
 * @author sunflower
 * 
 */
@Controller
@RequestMapping("/dic")
public class DictionaryController {

	private static final int MAX_PAGE_SIZE = 50;
	private static final int SEARCH_HOT_MAX_SIZE = 3;
	
	
	private static final String PAGINATION = "pagination";
	private static final String WORDS = "words";
	private static final String QUERYWORD = "queryWord";

	
	
	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	DictionaryFetchService dictionaryFetchService;
	
	@Autowired
	SearchHistoryHotService searchHistoryHotService;
	
	

	@RequestMapping(value = "/configList", method = RequestMethod.GET)
	public ModelAndView configList(@MyInject Pagination pagination) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/configList");
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<SearchTaskConfig> configs = dictionaryFetchService.queryTaskConfig(pagination);
		modelAndView.addObject("pagination", pagination);
		modelAndView.addObject("configs", configs);
		return modelAndView;
	}

	/**
	 * 词典列表页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "queryWord", required = false) String queryWord,
			@MyInject Pagination pagination) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/list");
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<SearchDictionary> words = null;
		if(queryWord == null || "".equals(queryWord.trim())){
			words = dictionaryService
			.queryDictionary(pagination);
		}else{
			words = dictionaryService
			.queryDictionary(pagination,queryWord);
		}
		modelAndView.addObject(PAGINATION, pagination);
		modelAndView.addObject(WORDS, words);
		modelAndView.addObject(QUERYWORD, queryWord);
		return modelAndView;
	}
	

	/**
	 * 添加词语
	 * 
	 * @param word
	 * @param employee
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/addWord", method = RequestMethod.POST)
	public ModelAndView addWord(
			@RequestParam(value = "word", required = true) String word,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/addWord");

		try {
			dictionaryService.addWord(word, employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/addConfig", method = RequestMethod.POST)
	public ModelAndView addConfig(
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "analyseScript", required = true) String analyseScript,
			@RequestParam(value = "charsetFormat", required = true) String charsetFormat,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/addConfig");

		try {
			dictionaryFetchService.addConfig(address,analyseScript,charsetFormat,employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/manageWord", method = RequestMethod.POST)
	public ModelAndView manageWord(
			@RequestParam(value = "word", required = true) String word,
			@RequestParam(value = "synonymWord", required = true) String synonymWord,
			@RequestParam(value = "recommendWord", required = true) String recommendWord,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/manageWord");

		try {
			dictionaryService.manageWord(word,synonymWord,recommendWord, employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 未审核词典列表
	 * 
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/auditList", method = RequestMethod.GET)
	public ModelAndView listAudit(@MyInject Pagination pagination,
			@RequestParam(value = "queryWord", required = false) String queryWord) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/auditList");
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<SearchDictionary> words = null;
		if(queryWord == null || "".equals(queryWord.trim())){
			words = dictionaryService
			.queryAuditDictionary(pagination);
		}else{
			words = dictionaryService
			.queryAuditDictionary(pagination,queryWord);
		}

		modelAndView.addObject(PAGINATION, pagination);
		modelAndView.addObject(WORDS, words);
		modelAndView.addObject(QUERYWORD, queryWord);
		return modelAndView;
	}
	
	/**
	 * 未审核通过词语列表
	 * @param pagination
	 * @param queryWord
	 * @return
	 */
	@RequestMapping(value = "/unauditList", method = RequestMethod.GET)
	public ModelAndView listUnaudit(@MyInject Pagination pagination,
			@RequestParam(value = "queryWord", required = false) String queryWord) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/unauditList");
		pagination.setPageSize(MAX_PAGE_SIZE);
		List<SearchDictionary> words = null;
		if(queryWord == null || "".equals(queryWord.trim())){
			words = dictionaryService
			.queryUnauditDictionary(pagination);
		}else{
			words = dictionaryService
			.queryUnauditDictionary(pagination,queryWord);
		}

		modelAndView.addObject(PAGINATION, pagination);
		modelAndView.addObject(WORDS, words);
		modelAndView.addObject(QUERYWORD, queryWord);
		return modelAndView;
	}

	/**
	 * 审核通过
	 * 
	 * @param id
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public ModelAndView audit(
			@RequestParam(value = "id", required = true) long id,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/audit");
		try {
			dictionaryService.audit(id, employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	/**
	 * 批量审核通过
	 * 
	 * @param ids
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/auditBatch", method = RequestMethod.POST)
	public ModelAndView auditBatch(
			@RequestParam(value = "ids", required = true) String ids,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/auditBatch");
		try {
			String[] data = ids.split(",");
			for(String id : data){
				if(!"".equals(id)){
					dictionaryService.audit(Long.valueOf(id).longValue(), employee);
				}
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 审核不通过
	 * 
	 * @param id
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/unaudited", method = RequestMethod.POST)
	public ModelAndView unaudited(
			@RequestParam(value = "id", required = true) long id,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/unaudited");
		try {
			dictionaryService.unaudited(id, employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	/**
	 * 批量审核不通过
	 * 
	 * @param ids
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/unauditedBatch", method = RequestMethod.POST)
	public ModelAndView unauditedBatch(
			@RequestParam(value = "ids", required = true) String ids,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/unauditedBatch");
		try {
			String[] data = ids.split(",");
			for(String id : data){
				if(!"".equals(id)){
					dictionaryService.unaudited(Long.valueOf(id).longValue(), employee);
				}
				
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 删除已经审核的词语
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam(value = "id", required = true) long id) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/del");
		try {
			dictionaryService.delete(id);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 修改词典
	 * 
	 * @param id
	 * @param word
	 * @param employee
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/editWord", method = RequestMethod.POST)
	public ModelAndView editWord(
			@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "word", required = true) String word,
			@MyInject Employee employee) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/editWord");
		try {
			dictionaryService.editWord(id, word, employee);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}



	/**
	 * 抓取同义词
	 * 
	 * @param word
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/grabSynonymWord", method = RequestMethod.POST)
	public ModelAndView grabSynonymWord(
			@RequestParam(value = "word", required = true) String word) {

		ModelAndView modelAndView = new ModelAndView(
				"/search/dic/grabSynonymWord");
		try {
			String synonym = dictionaryFetchService.grabSynonymWord(word);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
			modelAndView.addObject("synonym", synonym);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	/**
	 * 抓取词语信息包括（同义词和推荐词）
	 * @param word
	 * @return
	 */
	@RequestMapping(value = "/grabInfo", method = RequestMethod.POST)
	public ModelAndView grabInfo(
			@RequestParam(value = "word", required = true) String word) {

		ModelAndView modelAndView = new ModelAndView(
				"/search/dic/grabInfo");
		try {
			SearchDictionary dic = dictionaryService.querySearchDictionaryByWord(word);
			if(dic==null || dic.getSynonym2s().size() == 0){
				String synonymWord = dictionaryFetchService.grabSynonymWord(word);
				modelAndView.addObject("synonymWord", synonymWord);
			}else{
				modelAndView.addObject("synonymWord", getWordWithComma(dic.getSynonym2s()));
				modelAndView.addObject("recommendWord", getWordWithComma(dic.getChildren()));
			}
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	private String getWordWithComma(Set<SearchDictionary> set){
		
		Iterator<SearchDictionary> it = set.iterator();
		StringBuffer sb = new StringBuffer();
		boolean isfirst = true;
		while(it.hasNext()){
			SearchDictionary d = it.next();
			if(isfirst){
				sb.append(d.getWord());
				isfirst = false;
			}else{
				sb.append(",").append(d.getWord());
			}
		}
		return sb.toString();
	}

	
	@RequestMapping(value = "/configDel", method = RequestMethod.POST)
	public ModelAndView configDel(
			@RequestParam(value = "id", required = true) long id) {

		ModelAndView modelAndView = new ModelAndView("/search/dic/configDel");
		try {
			dictionaryFetchService.delete(id);
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_SUCCESS);
		} catch (Exception e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,
					e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/hot", method = RequestMethod.GET)
	public ModelAndView showUserHot(){
	    ModelAndView view = new ModelAndView("/search/dic/hotList");
	    List<SearchHistoryHot> hots = new ArrayList<SearchHistoryHot>(SEARCH_HOT_MAX_SIZE);
	    for(int i = 0; i < SEARCH_HOT_MAX_SIZE; i ++){
	        hots.add(null);
	    }
	    List<SearchHistoryHot> h = searchHistoryHotService.listUserHot();
	    for(SearchHistoryHot hot : h){
	        if(hot.getIndex() > SEARCH_HOT_MAX_SIZE){
	            continue;
	        }
	        hots.set(hot.getIndex() - 1, hot);
	    }
	    view.addObject("hots", hots);
	    
	    List<SearchHistoryHot> syshot = searchHistoryHotService.listSearchHot();
	    view.addObject("syshots", syshot);
	    return view;
	}
	
	@RequestMapping(value="/hot", method = RequestMethod.POST)
	public ModelAndView updateUserHot(String keyword, String href, Integer index){
	    ModelAndView view = new ModelAndView("search/dic/hotUpdate");
	    
	    SearchHistoryHot hot = new SearchHistoryHot();
	    hot.setIndex(index);
	    hot.setKeyword(keyword);
	    hot.setHref("".equals(href) ? null : href);
	    try{
	        if(keyword == null || "".equals(keyword)){
	            searchHistoryHotService.delForIndex(index);
	        } else {
	            hot.setMustshow(true);
	            searchHistoryHotService.save(hot);
	        }
	    } catch(Exception e){
	        view.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
	        return view;
	    }
	    view.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
	    return view;
	}

}
