package com.winxuan.ec.task.dao.ebook;

import com.winxuan.ec.task.model.ebook.Publisher;

/**
 *  电子收出版社DAO
 * @author luosh
 *
 */
public interface PublisherDao {
	
	Publisher getPublisherByCode(String publisherCode);
	
	Publisher getPublisherByName(String publisherName);

}
