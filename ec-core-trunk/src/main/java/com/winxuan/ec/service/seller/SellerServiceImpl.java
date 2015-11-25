/*
 * @(#)SellerServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.seller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.SellerDao;
import com.winxuan.ec.model.user.Seller;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.security.MD5Custom;
import com.winxuan.framework.validator.AuthenticationException;
import com.winxuan.framework.validator.Principal;
import com.winxuan.framework.validator.Verifier;
import com.winxuan.framework.validator.impl.PasswordVerifier;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-8-9
 */
@Service("sellerService")
@Transactional(rollbackFor = Exception.class)
public class SellerServiceImpl implements SellerService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4108817052058092368L;
	@InjectDao
	private SellerDao sellerDao;

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Seller get(Long id) {
		return sellerDao.get(id);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Seller> findSeller(Map<String, Object> params,
			Pagination pagination) {
		return sellerDao.find(params, pagination,(short)0);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Seller> find() {
		return sellerDao.find();
	}

	@Override
	public void update(Seller seller) {
		sellerDao.update(seller);
	}


	@Override
	public Seller getByName(String name) {
		return sellerDao.getByName(name);
	}

	@Override
	public Principal get(Serializable id) {
		return get(new Long(id.toString()));
	}

	@Override
	public Principal authenticate(Verifier verifier)
			throws AuthenticationException {
		Principal principal=null;
		if(verifier instanceof PasswordVerifier){
			PasswordVerifier verifierPass=(PasswordVerifier)verifier;
			principal = login(verifierPass.getUserName(), verifierPass.getPassword());
			if(principal != null){
				if(!(principal instanceof Seller)){
					throw new AuthenticationException("登录方式不正确");					
				}else{
					Seller seller = (Seller) principal;
					Date now = new Date();
					seller.setLastLoginTime(now);
					update(seller);
				}
			}
		}
		return principal;
	}

	@Override
	public Seller login(String name, String password) {
		password = MD5Custom.encrypt(password);
		return sellerDao.getByNameAndPassword(name, password);
	}

	@Override
	public boolean nameIdExisted(String name) {
		return sellerDao.nameIsExisted(name);
	}

}
