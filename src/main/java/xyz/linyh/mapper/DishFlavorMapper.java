package xyz.linyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.linyh.entity.DishFlavor;

import java.util.List;

/**
* @author lin
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2023-03-26 23:02:12
* @Entity xyz.linyh.entity.DishFlavor
*/
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

    boolean saveDishFlavor(@Param("flavor") DishFlavor flavor);

    Boolean updateDishFlavor(@Param("flavor") DishFlavor flavor);

    List<DishFlavor> getDishFlavorByDishId(@Param("dishId") Long dishId);

    Boolean deleteDishFlavorById(@Param("id") Long id);
}




