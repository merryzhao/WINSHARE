package com.winxuan.ec.task.service.ebook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.ebook.VendorDao;
import com.winxuan.ec.task.model.ebook.Vendor;
import com.winxuan.ec.task.service.ebook.VendorService;

/**
 * 电子书供应商
 * @author luosh
 *
 */
@Service("vendorService")
public class VendorServiceImpl implements VendorService{
	@Autowired
	private VendorDao vendorDao;
	@Override
	public Vendor get(Long vid) {
		return vendorDao.get(vid);
	}

}
