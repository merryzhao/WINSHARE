package com.winxuan.ec.task.service.ebook;

import java.util.List;

import com.winxuan.ec.task.model.ebook.InFranceBookCategory;
import com.winxuan.ec.task.model.ebook.InFranceCategory;

/**
 * 
 * @author luosh
 *
 */
public interface InFranceCategoryService {
	
	InFranceCategory get(Long id);

	InFranceCategory findByCode(String code);

	List<InFranceBookCategory> findByInFranceCategory(Long id);
}
