package com.winxuan.ec.service.authority.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.exception.AuthorityException;
import com.winxuan.ec.model.authority.ResourceGroup;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.security.MD5Custom;

/**
 * 权限用户处理实现类
 * @author sunflower
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	EmployeeService employeeService;
	

	@Override
	public void addUser(Employee employee,Employee operator) throws AuthorityException {

		employeeCheck(employee);
		employee.setCreator(operator.getId());
		employee.setUpdater(operator.getId());
		Date date = new Date();
		employee.setUpdateTime(date);
		employee.setRegisterTime(date);
		employee.setPassword(MD5Custom.encrypt(employee.getPassword()));
		employee.setSource(new Code(Code.USER_SOURCE_EC_CONSOLE));
		employee.setAvailable(true);
		employeeService.addEmployee(employee);
		// TODO 发邮件
	}

	private void employeeCheck(Employee employee) throws AuthorityException {
		
		if(employeeService.getByName(employee.getName()) != null){
			throw new AuthorityException(employee, "该账号已经存在");
		}
		if(employeeService.getByEmail(employee.getEmail()) != null){
			throw new AuthorityException(employee, "该邮箱已经存在");
		}
	}

	@Override
	public void modifyUser(Employee employee,Employee operator) throws AuthorityException {
		Employee employeeByEmail = employeeService.getByEmail(employee.getEmail());
		if(employeeByEmail!=null){
			if(employeeByEmail.getId().compareTo(employee.getId()) != 0){
				throw new AuthorityException(employee, "该邮箱已经存在");
			}
		}
		employee.setUpdater(operator.getId());
		employee.setUpdateTime(new Date());
		employeeService.update(employee);
	}

	@Override
	public void deleteUser(Employee employee) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyPassword(Employee employee) {
		
		employee.setPassword(MD5Custom.encrypt(employee.getPassword()));
		employee.setUpdateTime(new Date());
		employee.setUpdater(employee.getId());
		employeeService.update(employee);
	}

	@Override
	public void resetPassword(Employee employee, Employee operator) throws AuthorityException {
		Employee employeeForReset = employeeService.getByName(employee.getName());
		if(employeeForReset == null){
			throw new AuthorityException(employee, "该用户账号不存在");
		}
		employeeForReset.setPassword(MD5Custom.encrypt(employee.getPassword()));
		employeeForReset.setUpdateTime(new Date());
		employeeForReset.setUpdater(operator.getId());
		employeeService.update(employeeForReset);
	}

	@Override
	public List<Employee> queryUsers(Pagination pagination, Long groupId) {
		
		return employeeService.queryEmployees(pagination,groupId);
	}

	@Override
	public Employee queryUserById(Long employeeId) {
		
		return employeeService.get(employeeId);
	}

	@Override
	public void addGroupsToEmployee(List<ResourceGroup> resourceGroups,
			Employee employee) {
		
		if(resourceGroups == null || resourceGroups.size() == 0){
			return;
		}
		employee.setResourceGroups(null);
		for(ResourceGroup resourceGroup : resourceGroups){
			employee.addResourceGroup(resourceGroup);
		}
		employeeService.merge(employee);
	}

	@Override
	public Employee enableOrDisable(Employee employee) {
		
		return employeeService.enableOrDisable(employee);
	}


}
