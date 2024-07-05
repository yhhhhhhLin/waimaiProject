package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.entity.Employee;
import xyz.linyh.mapper.EmployeeMapper;
import xyz.linyh.service.EmployeeService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lin
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2023-03-25 19:51:08
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public Employee getEmployee(String userName) {
        Employee employee = employeeMapper.getEmployee(userName);
        return employee;
    }

    @Override
    public AllEmployeeVO getAllEmployeeByPage(int currentPage, int pageSize, String name) {
        AllEmployeeVO allEmployeeVO = new AllEmployeeVO();
//        查询所有符合条件的所有员工数量
        int count = employeeMapper.getEmployeeCount(name);
        allEmployeeVO.setTotal(count);
//        查询在这一页中的所有员工
        int offset = (currentPage-1)*pageSize;
        List<Employee> employees= employeeMapper.getAllEmployeeByPage(offset,pageSize,name);
        allEmployeeVO.setEmployees(employees);
        allEmployeeVO.setPages(currentPage);
        allEmployeeVO.setSize(pageSize);
        return allEmployeeVO;
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        if(employee.getUsername()!=null) {
//        修改基础信息
            boolean b = employeeMapper.updateEmployee(employee);
            return b;
        }else {
//        修改用户状态
            Boolean b = employeeMapper.updateEmployeeStatus(employee);
            return b;
        }

    }

    @Override
    public boolean saveEmployee(Employee employee) {
        Boolean b = employeeMapper.saveEmployee(employee);
        if(!b) {
            return false;
        }
        return true;
    }
}




