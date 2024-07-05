package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.entity.Employee;

/**
* @author lin
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-03-25 19:51:08
*/
public interface EmployeeService extends IService<Employee> {

    Employee getEmployee(String userName);

    AllEmployeeVO getAllEmployeeByPage(int currentPage, int pageSize, String name);

    Boolean updateEmployee(Employee employee);

    boolean saveEmployee(Employee employee);
}
