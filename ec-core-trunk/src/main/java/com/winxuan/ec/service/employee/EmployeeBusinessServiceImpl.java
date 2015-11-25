package com.winxuan.ec.service.employee;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.EmployeeBusinessControlDao;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.employee.EmployeeBusinessControl;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.readwritesplitting.Read;
/**
 * 
 * @author  bianlin
 */
@Service("employeeBusinessService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeBusinessServiceImpl implements EmployeeBusinessService, Serializable{
	
	private static final long serialVersionUID = -4356992311406786467L;
	
	private static final int VALID_EMPLOYE_EBUSINESS_CONTROL = 1;
	
	@InjectDao
	private EmployeeBusinessControlDao employeeBusinessControlDao;
	
	public void update(EmployeeBusinessControl employeeBusinessControl){
		employeeBusinessControlDao.update(employeeBusinessControl);
	}
	
	public void save(EmployeeBusinessControl employeeBusinessControl){
		employeeBusinessControlDao.save(employeeBusinessControl);
	}
	
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EmployeeBusinessControl get(Long id){
		return employeeBusinessControlDao.get(id);
	}
	@Override
	public EmployeeBusinessControl get(Employee employee, Code business){
		return employeeBusinessControlDao.getEmployeeBusinessControlByBusiness(employee, business, VALID_EMPLOYE_EBUSINESS_CONTROL);
	}
	
}
