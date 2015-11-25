/*
 * @(#)EmployeeServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.employee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.EmployeeDao;
import com.winxuan.ec.model.report.Grid;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.security.MD5Custom;
import com.winxuan.framework.validator.AuthenticationException;
import com.winxuan.framework.validator.Principal;
import com.winxuan.framework.validator.Verifier;
import com.winxuan.framework.validator.impl.PasswordVerifier;

/**
 * 后台用户逻辑实现
 * @author  HideHai
 * @version 1.0,2011-8-3
 */
@Service("employeeService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl implements EmployeeService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327492311406786467L;
	
	@InjectDao
	private EmployeeDao employeeDao;

    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Employee get(Long id) {
		return employeeDao.get(id);
	}
    
    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Employee getByName(String name) {
		return employeeDao.getByName(name);
	}

    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Employee getByEmail(String email) {
		return employeeDao.getByEmail(email);
	}
	
    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean nameIdExisted(String name) {
		return employeeDao.nameIsExisted(name);
	}

	public void update(Employee employee) {
		employeeDao.update(employee);
	}

    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Employee login(String name, String password) {
		password = MD5Custom.encrypt(password);
		return employeeDao.getByNameAndPassword(name, password);
	}

    @Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Principal get(Serializable id) {
		return get(new Long(id.toString()));
	}


	public Principal authenticate(Verifier verifier)
	throws AuthenticationException {
		Principal principal=null;
		if(verifier instanceof PasswordVerifier){
			PasswordVerifier verifierPass=(PasswordVerifier)verifier;
			principal = login(verifierPass.getUserName(), verifierPass.getPassword());
			if(principal != null){
				if(!(principal instanceof Employee)){
					throw new AuthenticationException("登录方式不正确");					
				}else{
					Employee employee = (Employee) principal;
					Date now = new Date(System.currentTimeMillis());
					employee.setLastLoginTime(now);
					update(employee);
				}
			}
		}
		return principal;
	}

	public void addFavoriteGrid(Employee employee, Grid grid) {
		employee.addFavoriteGrid(grid);
		employeeDao.update(employee);
	}

	public void removeFavoriteGrid(Employee employee, Grid grid) {
		employee.removeFavoriteGrid(grid);
		employeeDao.update(employee);
	}

	@Override
	public void addEmployee(Employee employee) {
		
		employeeDao.save(employee);
	}

	@Override
	public List<Employee> queryEmployees(Pagination pagination, Long groupId) {
		
		return employeeDao.queryEmployees(pagination,groupId);
	}

	@Override
	public List<Employee> queryEmployeesByquery(Pagination pagination, String userName) {
		
		return employeeDao.queryEmployeesByquery(pagination,userName);
	}
	
	@Override
	public List<String> queryByName(String userName) {
		
		userName = "%"+userName+"%";
		
		return employeeDao.queryByName(userName);
	}

	@Override
	public void merge(Employee employee) {
		
		employeeDao.merge(employee);
	}

	@Override
	public Employee enableOrDisable(Employee employee) {
		
		if(employee.isAvailable()){
			
			employee.setAvailable(User.DISABLE);
		}else{
			
			employee.setAvailable(User.ENABLE);
		}
		
		employeeDao.update(employee);
		
		return employee;
	}
	
	

}

