package com.winxuan.ec.task.service.ebook.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.ebook.InFranceCategoryDao;
import com.winxuan.ec.task.model.ebook.InFranceBookCategory;
import com.winxuan.ec.task.model.ebook.InFranceCategory;
import com.winxuan.ec.task.service.ebook.InFranceCategoryService;

/**
 * 
 * @author luosh
 *
 */
@Service("inFranceCategoryService")
public class InFranceCategoryServiceImpl implements InFranceCategoryService{
	@Autowired
	private InFranceCategoryDao inFranceCategoryDao;
	public InFranceCategory get(Long id){
		return inFranceCategoryDao.get(id);
	}
	@Override
	public InFranceCategory findByCode(String code) {
		List<InFranceCategory> list = inFranceCategoryDao.findByCode(code);
		return list == null || list.size() == 0?null:list.get(0);
	}
	@Override
	public List<InFranceBookCategory> findByInFranceCategory( Long id) {
		return inFranceCategoryDao.findByInFranceCategory(id);
	}
}
