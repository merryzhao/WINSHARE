package com.winxuan.ec.service.search;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SearchListRulesDao;
import com.winxuan.ec.model.search.SearchListRules;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 搜索规则
 * @author 赵芮
 *
 * 上午11:23:56
 */
@Service("searchListRulesService")
@Transactional(rollbackFor = Exception.class)
public class SearchListRulesServiceImpl implements SearchListRulesService {
	@InjectDao
	private SearchListRulesDao rulesDao;

	@Override
	public SearchListRules get(int id) {
		return rulesDao.get(id);
	}

	@Override
	public void save(SearchListRules rule) {
	}

	@Override
	public void update(SearchListRules rule) {
		this.rulesDao.update(rule);
	}


	@Override
	public List<SearchListRules> findAll() {
		return rulesDao.findAll();
	}

	@Override
	public void saveorupdate(SearchListRules rule) {
		this.rulesDao.saveorupdate(rule);
	}

	
	

}
