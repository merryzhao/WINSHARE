package com.winxuan.ec.dao;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.employee.EmployeeBusinessControl;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
/**
 *
 * @author bianlin
 *
 */
public interface EmployeeBusinessControlDao {
	@Get
	EmployeeBusinessControl get(Long id);
	
	@Save
	void save(EmployeeBusinessControl employeeBusinessControl);

	@Update
	void update(EmployeeBusinessControl employeeBusinessControl);
	
	@Query("from EmployeeBusinessControl eb where eb.employee=? and eb.business=? and eb.status=?")
	EmployeeBusinessControl getEmployeeBusinessControlByBusiness(Employee employee, Code business, int status);
	
}
