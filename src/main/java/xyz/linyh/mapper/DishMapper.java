package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.DTO.DishPageDTO;
import xyz.linyh.entity.Dish;

import java.util.List;

/**
* @author lin
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2023-03-26 16:56:53
* @Entity xyz.linyh.entity.Dish
*/
public interface DishMapper extends BaseMapper<Dish> {

    int getDishCount(@Param("name") String name);

    List<Dish> getAllDishByPage(@Param("offset") int offset,@Param("pageSize") int pageSize, @Param("name")String name);

    Dish getDishById(@Param("dishId") Long dishId);

    boolean deleteDishById(@Param("dishId") Long dishId);

    Dish getDishByName(@Param("dishName") String DishName);

    Boolean updateDish(@Param("dish") Dish dish);

    Boolean updateDishMapper(@Param("dish") Dish dish);

    boolean saveDish(@Param("dish") Dish dish);
}




