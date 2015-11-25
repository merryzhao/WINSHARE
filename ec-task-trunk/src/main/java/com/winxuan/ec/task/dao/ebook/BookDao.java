package com.winxuan.ec.task.dao.ebook;

import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BookChapter;
import com.winxuan.ec.task.model.ebook.BookPreface;

/**
 * 电子书信息DAO
 * @author luosh
 *
 */
public interface BookDao {
	
	Book get(Long bookId);
	void save(Book book);
	void save(BookPreface bookPreface);
	void save(BookChapter bookChapter);
	
}
