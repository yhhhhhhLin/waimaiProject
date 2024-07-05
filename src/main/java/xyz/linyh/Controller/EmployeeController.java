package xyz.linyh.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import xyz.linyh.Result.R;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.entity.Employee;
import xyz.linyh.service.EmployeeService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R employeeLogin(@RequestBody Employee employee,
                           HttpServletRequest request){
        log.info(employee.getPassword());
        //先获取密码和对密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        log.info(password);

//        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Employee::getUsername,employee.getUsername());
//        Employee one = employeeService.getOne(wrapper);
        Employee emp = employeeService.getEmployee(employee.getUsername());
        if(emp==null){
            log.info("用户不存在");
            return R.error("用户不存在!");
        }else if(!emp.getPassword().equals(password)){
            log.info("密码不正确");
            return R.error("密码不正确!");
        }else if(emp.getStatus()==0){
            log.info("用户被封禁");
            return R.error("用户被封禁!");
        }else{
            log.info("登录成功");
            System.out.println(emp.getId());
            request.getSession().setAttribute("uId",emp.getId());
            return R.success(emp);
        }
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        request.getSession().removeAttribute("uId");
        return R.success("退出成功");
    }


    /**
     * 查询所有员工信息
     * @param
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R page( int currentPage, int pageSize, String name){
        log.info("name为={}",name);
        AllEmployeeVO page = employeeService.getAllEmployeeByPage(currentPage,pageSize,name);
        System.out.println(page);
        return R.success(page);
    }

    @GetMapping("/{id}")
    public R getEmployeeById(@PathVariable long id){
        Employee byId = employeeService.getById(id);

        return R.success(byId);
    }

    /**
     * 添加员工
     * @return
     */
    @PostMapping
    public R addEmployee(@RequestBody Employee employee,
                         HttpServletRequest request){
        long updateId = (long) request.getSession().getAttribute("uId");
        log.info("员工信息为={}",employee);

//        默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        log.info("员工信息为={}",employee);
        //判断账号是否存在
        Employee one = employeeService.getEmployee(employee.getUsername());
        if(one!=null){
            return R.error("账号已经存在");
        }
//        添加用户
        employee.setCreateUser(updateId);
        employee.setUpdateUser(updateId);
        employee.setCreateTime(new Date());
        employee.setUpdateTime(new Date());
        boolean b = employeeService.saveEmployee(employee);
        return R.success("添加成功");
    }

    /**
     * 修改employee
     * @return
     */
    @PutMapping
    public R editEmployee(HttpServletRequest request,@RequestBody Employee employee){
        Long updateId = (Long)request.getSession().getAttribute("uId");
        //判断账号是否存在
        Employee one = employeeService.getEmployee(employee.getUsername());
        if(one!=null && !one.getUsername().equals(employee.getUsername())){
            return R.error("账号已经存在");
        }

        employee.setUpdateTime(new Date());
        employee.setUpdateUser(updateId);
        Boolean b = employeeService.updateEmployee(employee);
        employeeService.updateById(employee);


        return R.success("修改成功");
    }
}
