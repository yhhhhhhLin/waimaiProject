package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.entity.SetmealDish;

import java.util.List;

/**
* @author lin
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service
* @createDate 2023-03-29 20:04:50
*/
public interface SetmealDishService extends IService<SetmealDish> {

    boolean saveSetMealDish(SetmealDish dishes);

    List<SetmealDish> getSetMealAndDishBySetMealId(long id);
}
