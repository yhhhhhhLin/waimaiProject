package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.entity.Employee;

import java.util.List;

/**
* @author lin
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-03-25 19:51:08
* @Entity xyz.linyh.entity.Employee
*/

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    Employee getEmployee(@Param("userName") String userName);

    int getEmployeeCount(@Param("name") String name);

    List<Employee> getAllEmployeeByPage(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("name") String name);

    boolean updateEmployee(@Param("employee") Employee employee);

    Boolean updateEmployeeStatus(@Param("employee")Employee employee);

    Boolean saveEmployee(@Param("employee") Employee employee);
}




