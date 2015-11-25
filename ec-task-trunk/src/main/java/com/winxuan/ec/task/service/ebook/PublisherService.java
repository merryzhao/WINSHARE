package com.winxuan.ec.task.service.ebook;

import com.winxuan.ec.task.model.ebook.Publisher;

/**
 * 
 * @author luosh
 *
 */
public interface PublisherService {

	Publisher getPublisherByCode(String publisherCode);

	Publisher getPublisherByName(String publisher);

}
