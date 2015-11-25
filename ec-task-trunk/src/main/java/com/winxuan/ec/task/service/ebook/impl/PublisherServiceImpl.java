package com.winxuan.ec.task.service.ebook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.winxuan.ec.task.dao.ebook.PublisherDao;
import com.winxuan.ec.task.model.ebook.Publisher;
import com.winxuan.ec.task.service.ebook.PublisherService;
/**
 * 出版社
 * @author luosh
 *
 */
@Service("epublisherService")
public class PublisherServiceImpl implements PublisherService{
	@Autowired
	PublisherDao epublisherDao;
	@Override
	public Publisher getPublisherByCode(String publisherCode) {
		return epublisherDao.getPublisherByCode(publisherCode);
	}
	@Override
	public Publisher getPublisherByName(String publisherName) {
		return epublisherDao.getPublisherByName(publisherName);
	}

}
