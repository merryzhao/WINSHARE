package com.winxuan.ec.task.dao.ebook;

import java.io.Serializable;
import java.util.List;

import com.winxuan.ec.task.model.ebook.EbookChange;

/**
 * 九月网电子书变更Dao
 * @author luosh
 *
 */
public interface EbookChangeDao extends Serializable{
	
	
	
	List<EbookChange> getEbooks();
	
	void saveEbook(int winFlag ,Long id);

	void save(EbookChange ebookChange);

	EbookChange get(Long bookId);
}
