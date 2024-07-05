package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.DTO.DishPageDTO;
import xyz.linyh.VO.AllDishVO;
import xyz.linyh.entity.Dish;

/**
* @author lin
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2023-03-26 16:56:53
*/
public interface DishService extends IService<Dish> {

    AllDishVO getDishByPage(int page,int pageSize, String name);

    Dish getDishById(Long dishId);

    boolean deleteDishById(Long dishId);

    Dish getDishByName(String DishName);

    boolean updateDish(Dish dish);

    Boolean updateDishStatus(Dish dish);

    long saveDish(Dish dish);
}
