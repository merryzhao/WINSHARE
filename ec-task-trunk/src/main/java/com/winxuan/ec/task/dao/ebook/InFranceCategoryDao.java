package com.winxuan.ec.task.dao.ebook;

import java.util.List;

import com.winxuan.ec.task.model.ebook.InFranceBookCategory;
import com.winxuan.ec.task.model.ebook.InFranceCategory;

/**
 * 中图法分类与图书分类的对应关系DAO接口 
 * @author luosh
 *
 */
public interface InFranceCategoryDao {
	InFranceCategory get(Long id);

	List<InFranceCategory> findByCode(String code);
	
	List<InFranceBookCategory> findByInFranceCategory(Long id);
}
