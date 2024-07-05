package xyz.linyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.linyh.entity.DishFlavor;

import java.util.List;

/**
* @author lin
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2023-03-26 23:02:12
*/
public interface DishFlavorService extends IService<DishFlavor> {

    Boolean saveDishFlavor(DishFlavor flavor);

    Boolean updateDishFlavor(DishFlavor flavor);

    boolean saveOrUpdateDishFlavor(List<DishFlavor> flavors, Long dishId);

    List<DishFlavor> getDishFlavorByDishId(long id);
}
