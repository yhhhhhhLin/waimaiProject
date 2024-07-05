package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.entity.SetmealDish;

import java.util.List;

/**
* @author lin
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2023-03-29 20:04:50
* @Entity xyz.linyh.entity.SetmealDish
*/
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

    boolean saveSetMealDish(@Param("dishes") SetmealDish dishes);

    List<SetmealDish> getSetMealAndDishBySetMealId(@Param("setMealId") long id);
}




