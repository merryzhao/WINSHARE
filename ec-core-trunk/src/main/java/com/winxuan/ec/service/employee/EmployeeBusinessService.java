package com.winxuan.ec.service.employee;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.employee.EmployeeBusinessControl;
import com.winxuan.ec.model.user.Employee;
/**
 * 
 * @author  bianlin
 */
public interface EmployeeBusinessService{
	EmployeeBusinessControl get(Long id);
	
	void update(EmployeeBusinessControl employeeBusinessControl);
	
	void save(EmployeeBusinessControl employeeBusinessControl);
	
	EmployeeBusinessControl get(Employee employee, Code business);
}
