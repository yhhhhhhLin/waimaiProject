package xyz.linyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.linyh.entity.SetmealDish;
import xyz.linyh.mapper.SetmealDishMapper;
import xyz.linyh.service.SetmealDishService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lin
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2023-03-29 20:04:50
*/
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
    implements SetmealDishService{

    @Resource
    private SetmealDishMapper setmealDishMapper;

    @Override
    public boolean saveSetMealDish(SetmealDish dishes) {
        boolean b = setmealDishMapper.saveSetMealDish(dishes);
        return b;
    }

    @Override
    public List<SetmealDish> getSetMealAndDishBySetMealId(long id) {
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetMealAndDishBySetMealId(id);
        return setmealDishes;
    }
}




