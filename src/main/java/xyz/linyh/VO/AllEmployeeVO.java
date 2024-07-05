package xyz.linyh.VO;

import lombok.Data;
import xyz.linyh.entity.Employee;

import java.util.List;

@Data
public class AllEmployeeVO {
    private List<Employee> employees;
    private int total;
    private int size;
    private int current;
    private int maxLimit;
    private int pages;
}
