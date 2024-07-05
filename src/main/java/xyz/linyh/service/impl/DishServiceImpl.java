package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import xyz.linyh.DTO.DishPageDTO;
import xyz.linyh.Exception.MyException;
import xyz.linyh.VO.AllDishVO;
import xyz.linyh.VO.AllEmployeeVO;
import xyz.linyh.entity.Dish;
import xyz.linyh.entity.Employee;
import xyz.linyh.mapper.DishMapper;
import xyz.linyh.service.DishService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lin
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2023-03-26 16:56:53
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Resource
    private DishMapper dishMapper;

    /**
     * 自定义查询菜品
     * @param
     * @return
     */
    @Override
    public AllDishVO getDishByPage(int page,int pageSize, String name) {
        AllDishVO allDishVO = new AllDishVO();
//        查询所有符合条件的所有菜品数量
        int count = dishMapper.getDishCount(name);
        allDishVO.setTotal(count);
//        查询在这一页中的所有菜品
        int offset = (page-1)*pageSize;
        List<Dish> dishes= dishMapper.getAllDishByPage(offset,pageSize,name);
        allDishVO.setDishes(dishes);
        allDishVO.setPages(page);
        allDishVO.setSize(pageSize);
        return allDishVO;
    }

    @Override
    public Dish getDishById(Long dishId) {
        Dish dish = dishMapper.getDishById(dishId);
        return dish;
    }

    @Override
    public boolean deleteDishById(Long dishId) {
        boolean b = dishMapper.deleteDishById(dishId);
        return b;
    }

    @Override
    public Dish getDishByName(String DishName) {
        Dish one = dishMapper.getDishByName(DishName);
        return one;
    }

    @Override
    public boolean updateDish(Dish dish) {
        Boolean b = dishMapper.updateDish(dish);
        return b;
    }

    @Override
    public Boolean updateDishStatus(Dish dish) {
        Boolean b = dishMapper.updateDishMapper(dish);
        return b;
    }

    @Override
    public long saveDish(Dish dish) {
        boolean b = dishMapper.saveDish(dish);
        if(b){
            Dish one = dishMapper.getDishByName(dish.getName());
            return one.getId();
        }
        throw new MyException("错误");
    }


}




