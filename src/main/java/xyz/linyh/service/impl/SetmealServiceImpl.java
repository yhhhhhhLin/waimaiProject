package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.VO.AllSetMealVO;
import xyz.linyh.entity.Employee;
import xyz.linyh.entity.Setmeal;
import xyz.linyh.mapper.SetmealMapper;
import xyz.linyh.service.SetmealService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lin
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2023-03-28 19:16:09
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    public AllSetMealVO getAllSetMealByPage(int page, int pageSize, String name) {
        AllSetMealVO allEmployeeVO = new AllSetMealVO();
//        查询所有符合条件的所有员工数量
        int count = setmealMapper.getSetMealCount(name);
        allEmployeeVO.setTotal(count);
//        查询在这一页中的所有员工
        int offset = (page-1)*pageSize;
        List<Setmeal> setMeals= setmealMapper.getAllSetMealByPage(offset,pageSize,name);
        allEmployeeVO.setSetmeals(setMeals);
        allEmployeeVO.setPages(page);
        allEmployeeVO.setSize(pageSize);
        return allEmployeeVO;
    }

    @Override
    public long saveSetMeal(Setmeal setmeal) {
        Boolean b = setmealMapper.saveSetMeal(setmeal);
        Setmeal setmeal1 = setmealMapper.getSetMealByName(setmeal.getName());
        return setmeal1.getId();
    }

    @Override
    public Setmeal getSetMealByName(String name) {
        Setmeal setMealByName = setmealMapper.getSetMealByName(name);
        return setMealByName;
    }

    @Override
    public Setmeal getSetMealById(long id) {
        Setmeal setmeal = setmealMapper.getSetMealById(id);
        return setmeal;
    }
}




